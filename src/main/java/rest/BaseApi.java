package rest;

import framework.Settings;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static framework.Log.logger;

public class BaseApi {
    Settings settings = new Settings();


    protected static JSONObject jsonInput = new JSONObject();
    public static JSONArray jsonInputArray = new JSONArray();
    protected static JSONArray jsonOutput = new JSONArray();
    protected static String formInput = "";


    protected String contentTypeParam = "content-type";
    protected String applicationJson = "application/json";
    protected String contentTypeValueForm = "application/x-www-form-urlencoded";

    protected String xApplicationParam = "X-Application";
    protected String xAuthenticationParam = "X-Authentication";
    protected String accept = "Accept";

    public int RESPONSE_OK = 200;
    public int RESPONSE_FORBIDDEN = 403;
    public int RESPONSE_NOT_FOUND = 404;
    public int RESPONSE_UNAUTHORIZED = 401;
    public int RESPONSE_BAD_REQUEST = 400;

    public String STATUS_SUCCESS = "SUCCESS";

    public String ERROR_NONE = "null";

    private String url;

    protected void setUrl(String url) {
        this.url = url;
    }

    protected HttpsURLConnection connection;

    protected void initRest(String param) {
        init(settings.getProperty("rest.server"), param);
    }

    protected void addDataToJson(String key, String value) {
        logger.info("Adding to json param key " + key + " value " + value);
        jsonInput.put(key, value);
    }

    private void init(String methodUrl, String param) {
        jsonInput = new JSONObject();
        URL url = null;
        try {
            url = new URL(methodUrl + param);
            logger.info("Requesting API "+url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            connection = (HttpsURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    enum REQUEST_METHOD {
        POST,
        GET
    }

    protected void setRequestMethod(REQUEST_METHOD requestMethod) {
        try {
            connection.setRequestMethod(requestMethod.name());
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
    }

    protected void setRequestProperty(String key, String value) {
        logger.info("Adding to request property key " + key + " value " + value);
        connection.setRequestProperty(key, value);
    }

    protected void push(boolean isArray) {
        connection.setDoOutput(true);
        DataOutputStream wr = null;
        try {
            wr = new DataOutputStream(connection.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (!isArray) wr.writeBytes(jsonInput.toString());
            else wr.writeBytes(jsonInputArray.toString());
            wr.flush();
            wr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getResponse();
    }

    protected void push() {
        push(false);
    }


    protected void getResponse() {
        logger.info("Getting output stream");
        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

        } catch (IOException e) {
            try {
                in = new BufferedReader(
                        new InputStreamReader(connection.getErrorStream()));
            } catch (NullPointerException n) {
                logger.info("Server returns 404");
                jsonOutput = null;
                return;
            }
        }
        try {
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            if (response.toString().indexOf("[") != 0) response.insert(0, "[").insert(response.length(), "]");
            try {
                jsonOutput = new JSONArray(response.toString());
            } catch (JSONException e) {
                logger.error(response);
                throw e;
            }

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void setPostRequestProperties(String token) {
        setRequestMethod(REQUEST_METHOD.POST);
        setRequestProperty(contentTypeParam, applicationJson);
        setRequestProperty("Accept", applicationJson);
        setRequestProperty(xAuthenticationParam, token);
        setRequestProperty("X-Country", "UA");

    }

    protected void setGetRequestProperties(String token) {
        setRequestMethod(REQUEST_METHOD.GET);
        setRequestProperty(contentTypeParam, applicationJson);
        setRequestProperty("Accept", applicationJson);
        setRequestProperty(xAuthenticationParam, token);
        setRequestProperty("X-Country", "UA");

    }

    public JSONArray getResponseOutput() {
        logger.info("Getting output");
        return jsonOutput;
    }

    public int getResultsNumber() {
        logger.info("Getting number of results");
        try {
            return jsonOutput.length();
        } catch (NullPointerException n) {
            return 0;
        }
    }

    public JSONObject getJsonInput() {
        logger.info("Getting Json input");
        return jsonInput;
    }

    public int getResponseCode() {
        try {
            return connection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    Object getDataFromOutput(int resultId, String key, String externalObject, int externalObjectId) {
        logger.info("Getting value from key " + key);
        JSONObject json;
        json = jsonOutput.getJSONObject(resultId);
        Object value;
        if (externalObject != null) {
            logger.info("Seeking in externalObject " + externalObject);
            JSONObject arr;
            try {
                arr = (JSONObject) json.get(externalObject);
            } catch (ClassCastException c) {
                JSONArray array = (JSONArray) json.get(externalObject);
                arr = (JSONObject) array.get(externalObjectId);
            }
            value = arr.get(key);
        } else {
            value = json.get(key);
        }
        logger.info("Value = " + value);
        return value;
    }

    Object getDataFromOutput(int resultId, String key, String externalObject) {
        return getDataFromOutput(resultId, key, externalObject, 0);
    }

    protected void addDataToJson(String key, Object object) {
        logger.info("Adding to json key" + key + "object " + object.toString());
        jsonInput.put(key, object);
    }

    protected void addDataToForm(String key, String value) {
        logger.info("Adding to form param key " + key + " value " + value);
        if (formInput.length() != 0) formInput += "&";
        formInput += key + "=" + value;
    }

    protected void pushForm() {
        connection.setDoOutput(true);
        DataOutputStream wr = null;
        try {
            wr = new DataOutputStream(connection.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            wr.write(formInput.getBytes(StandardCharsets.UTF_8));
            wr.flush();
            wr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getResponse();
    }
}
