package rest;

import framework.Settings;
import org.json.JSONArray;
import org.json.JSONObject;


public class PlaceholderApi extends BaseApi {
    Settings settings = new Settings();
    public void getPosts() {
        initRest("posts");
        setRequestMethod(REQUEST_METHOD.GET);
    }


    public JSONArray getExpectedCorrectData() {
        return settings.getExpectedTestData("correctData");
    }
}
