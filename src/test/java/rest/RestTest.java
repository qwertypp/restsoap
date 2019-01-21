package rest;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import rest.PostsObjects.Posts;

public class RestTest  {

    PostsClient client;
    DataGetter dataGetter;

    @BeforeMethod
    public void before(){
        client = new PostsClient();
        dataGetter = new DataGetter();
    }

    @Test
    public void getPostsResponseCodeTest() {
        Assert.assertEquals( client.getUserPosts().getStatus(), 200);
    }

    @Test()
    public void getPostsCorrectDataFromFileTest() {
        String response = client.getUserPosts().getBody();
        String expectedResult = dataGetter.getExpectedCorrectData();

        Assert.assertEquals(response, expectedResult);
    }

    @Test()
    public void getPostsIncorrectDataFromFileTest() {
        String response = client.getUserPosts().getBody();
        String expectedResult = dataGetter.getExpectedInCorrectData();

        Assert.assertEquals(response, expectedResult);
    }

    @Test
    public void postDeserializedPostsTest() {
        Posts response = client.postUserPosts().getResponse();
        Assert.assertEquals((int) response.id, 101);
    }

    @Test
    public void putPostsTest() {
        int responseCode = client.putUserPosts("1", "1", "TestTitle", "TestBody").getStatus();
        Assert.assertEquals(responseCode, 200);
        Posts response = client.getUserPost("1").getResponse();
        System.out.println(response.body);

        Assert.assertEquals((int)response.id, 1);
        Assert.assertEquals((int)response.userId, 1);
        Assert.assertEquals(response.title, "TestTitle");
        Assert.assertEquals(response.body, "TestBody");
    }
}
