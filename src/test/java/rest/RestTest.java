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
        JSONArray expectedResult = jsonOutput;
        JSONArray actualResult = getExpectedCorrectData();

        Assert.assertEquals(expectedResult, actualResult);
    }
}
