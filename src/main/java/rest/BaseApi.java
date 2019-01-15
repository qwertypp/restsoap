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

import static framework.Log.logger;

public class BaseApi {
    private Settings settings = new Settings();
    private HttpsURLConnection connection;

    protected JSONObject jsonInput = new JSONObject();
    protected JSONArray jsonInputArray = new JSONArray();
    protected JSONArray jsonOutput = new JSONArray();

    private String contentTypeParam = "content-type";
    private String applicationJson = "application/json";
    private String contentTypeValueForm = "application/x-www-form-urlencoded";

    private String xApplicationParam = "X-Application";
    private String xAuthenticationParam = "X-Authentication";
    private String accept = "Accept";

    void initRest(String param) {
        init(settings.getProperty("rest.server"), param);
    }

    void addDataToJson(String key, String value) {
        logger.info("Adding to json param key " + key + " value " + value);
        jsonInput.put(key, value);
    }

    private void init(String methodUrl, String param) {
        jsonInput = new JSONObject();
        URL url = null;
        try {
            url = new URL(methodUrl + param);
            logger.info("Requesting API " + url);
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
        GET,
        PUT,
        DELETE,
        PATCH
    }

    void setRequestMethod(REQUEST_METHOD requestMethod) {
        try {
            connection.setRequestMethod(requestMethod.name());
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
    }

    private void setRequestProperty(String key, String value) {
        logger.info("Adding to request property key " + key + " value " + value);
        connection.setRequestProperty(key, value);
    }

    void push() {
        connection.setDoOutput(true);
        DataOutputStream wr = null;
        try {
            wr = new DataOutputStream(connection.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            wr.writeBytes(jsonInput.toString());
            wr.flush();
            wr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getResponse();
    }

    void getResponse() {
        logger.info("Getting output stream");
        BufferedReader in;
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

    void setPostRequestProperties() {
        setRequestMethod(REQUEST_METHOD.POST);
        setRequestProperty(contentTypeParam, applicationJson);
        setRequestProperty("Accept", applicationJson);
    }

    void setPutRequestProperties() {
        setRequestMethod(REQUEST_METHOD.PUT);
        setRequestProperty(contentTypeParam, applicationJson);
        setRequestProperty("Accept", applicationJson);
    }

    JSONArray getResponseOutput() {
        logger.info("Getting output");
        return jsonOutput;
    }

    public int getResultsNumber() {
        logger.info("Getting number of results");
        return jsonOutput == null ? 0 : jsonOutput.length();
    }

    public JSONObject getJsonInput() {
        logger.info("Getting Json input");
        return jsonInput;
    }

    int getResponseCode() {
        try {
            return connection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    void addDataToJson(String key, Object object) {
        logger.info("Adding to json key" + key + "object " + object.toString());
        jsonInput.put(key, object);
    }
}
