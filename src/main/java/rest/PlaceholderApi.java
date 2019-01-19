package rest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import framework.Settings;
import org.json.JSONArray;
import rest.responseTypes.Posts;
import rest.responseTypes.WrongPosts;

import java.lang.reflect.Type;
import java.util.List;

class PlaceholderApi extends BaseApi {
    private Settings settings = new Settings();

    void getPosts(Integer id) {
        if (id == null) initRest("posts");
        else initRest("posts/" + id);
        setRequestMethod(REQUEST_METHOD.GET);
        getResponse();
    }

    void getPosts() {
        getPosts(null);
    }

    void postPosts() {
        initRest("posts");
        setPostRequestProperties();
        push();
    }

    void putPosts(int id, int userId, String title, String body) {
        initRest("posts/" + id);
        setPutRequestProperties();
        addDataToJson("id", id);
        addDataToJson("userId", userId);
        if (title != null) addDataToJson("title", title);
        if (body != null) addDataToJson("body", body);
        push();
    }

    JSONArray getExpectedCorrectData() {
        return settings.getExpectedJsonTestData("correctData");
    }

    JSONArray getExpectedIncorrectData() {
        return settings.getExpectedJsonTestData("incorrectData");
    }

    List<Posts> serializePosts(JSONArray array) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Posts>>() {
        }.getType();
        return (List<Posts>) gson.fromJson(array.toString(), listType);
    }

    List<WrongPosts> serializeWrongPosts(JSONArray array) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<WrongPosts>>() {
        }.getType();
        return (List<WrongPosts>) gson.fromJson(array.toString(), listType);
    }
}
