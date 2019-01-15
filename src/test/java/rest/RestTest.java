package rest;

import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.Test;
import rest.responseTypes.Posts;

public class RestTest extends PlaceholderApi {

    @Test
    public void getPostsResponseCodeTest() {
        getPosts();
        Assert.assertEquals(getResponseCode(), 200);
    }

    @Test()
    public void getPostsCorrectDataFromFileTest() {
        getPosts();
        JSONArray expectedResult = getResponseOutput();
        JSONArray actualResult = getExpectedCorrectData();

        Assert.assertEquals(expectedResult.toString(), actualResult.toString());
    }

    @Test()
    public void getPostsIncorrectDataFromFileTest() {
        getPosts();
        JSONArray expectedResult = getResponseOutput();
        JSONArray actualResult = getExpectedIncorrectData();

        Assert.assertEquals(expectedResult.toString(), actualResult.toString());
    }

    @Test
    public void postSerializePostsTest() {
        postPosts();
        Assert.assertEquals((int) serializePosts(getResponseOutput()).get(0).id, 101);
    }

    @Test
    public void postSerializeWrongPostsTest() {
        postPosts();
        Assert.assertEquals((int) serializeWrongPosts(getResponseOutput()).get(0).additionalData.get(0).id, 101);
    }

    @Test
    public void putTest() {
        putPosts(1, 1, "TestTitle", "TestBody");
        getPosts(1);
        Posts postResult = serializePosts(getResponseOutput()).get(0);

        Assert.assertEquals((int)postResult.id, 1);
        Assert.assertEquals((int)postResult.userId, 1);
        Assert.assertEquals(postResult.title, "TestTitle");
        Assert.assertEquals(postResult.body, "TestBody");
    }
}
