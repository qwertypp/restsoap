package rest;

import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RestTest extends PlaceholderApi {

    @Test
    public void getPostsTest(){
        getPosts();
        Assert.assertEquals(getResponseCode(),200);
    }

    
    @Test()
    public void getPostsDataProviderTest(){
        getPosts();
        System.out.println(jsonOutput);
        JSONArray expectedResult = jsonOutput;
        JSONArray actualResult = getExpectedCorrectData();
        Assert.assertEquals(serializePosts(expectedResult), serializePosts(actualResult));
    }
}
