package rest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import framework.Settings;
import org.json.JSONArray;
import rest.responseTypes.Posts;

import java.lang.reflect.Type;
import java.util.List;


public class PlaceholderApi extends BaseApi {
    Settings settings = new Settings();

    public void getPosts() {
        initRest("posts");
        setRequestMethod(REQUEST_METHOD.GET);
        setGetRequestProperties();
        getResponse();
    }

    public JSONArray getExpectedCorrectData() {
        return settings.getExpectedTestData("correctData");
    }

    public List<Posts> serializePosts(JSONArray array){
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Posts>>() {
        }.getType();
        return (List<Posts>) gson.fromJson(array.toString(), listType);
    }
}
