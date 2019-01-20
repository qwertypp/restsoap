package rest;

import org.testng.Assert;
import org.testng.annotations.Test;
import rest.responseTypes.Posts;

public class RestTest  {

    private Client client = new Client();
    private DataGetter dataGetter = new DataGetter();

    @Test
    public void getPostsResponseCodeTest() {
        int responseCode = client.getUserPosts().getStatus();
        Assert.assertEquals(responseCode, 200);
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
