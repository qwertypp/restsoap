package rest;

import com.google.gson.reflect.TypeToken;
import framework.Settings;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import rest.responseTypes.Posts;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

class Client {
    private Settings settings = new Settings();
    private HttpClient client = HttpClients.custom().build();
    private String url = settings.getRestServerUrl();


    Response<ArrayList<Posts>> getUserPosts() {
        HttpUriRequest request = RequestBuilder.get()
                .setUri(url + "/posts")
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();
        return getResultArray(request);
    }

    Response<Posts> getUserPost(String id) {
        HttpUriRequest request = RequestBuilder.get()
                .setUri(url + "/posts/" + id)
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();
        return getResult(request);
    }

    Response<Posts> postUserPosts() {
        HttpUriRequest request = RequestBuilder.post()
                .setUri(url + "/posts")
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();
        return getResult(request);
    }

    Response<Posts> putUserPosts(String id, String userId, String title, String body) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("userId", userId);
        jsonObject.put("title", title);
        jsonObject.put("body", body);
        HttpUriRequest request = null;
        try {
            request = RequestBuilder.put()
                    .setUri(url + "/posts/" + id)
                    .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                    .setEntity(new StringEntity(jsonObject.toString()))
                    .build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return getResult(request);
    }

    private Response getResultArray(HttpUriRequest request) {
        try {
            HttpResponse response = client.execute(request);
            Response result = new Response<ArrayList<Posts>>(response);
            result.setResponseType(new TypeToken<ArrayList<Posts>>() {
            }.getType());
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Response getResult(HttpUriRequest request) {
        try {
            HttpResponse response = client.execute(request);
            Response result = new Response<Posts>(response);
            result.setResponseType(new TypeToken<Posts>() {
            }.getType());
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
