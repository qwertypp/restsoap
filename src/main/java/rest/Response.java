package rest;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.stream.Collectors;

class Response <T> {
    private int status;
    private String body;
    private Type responseType;

    Response(HttpResponse response) {
        this.status = response.getStatusLine().getStatusCode();
        try {
            this.body =  new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                    .lines()
                    .parallel().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int getStatus() {
        return status;
    }

    String getBody(){
        return body;
    }

    void setResponseType(Type responseType){
        this.responseType = responseType;
    }

    T getResponse(){
        Gson gson = new Gson();
        return gson.fromJson(getBody(), responseType);
    }
}
