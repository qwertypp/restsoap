package rest;

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
        Assert.assertEquals(getExpectedCorrectData(), getResponseOutput());
    }
}
