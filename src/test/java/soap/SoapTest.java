package soap;

import org.testng.Assert;
import org.testng.annotations.Test;
import soapClient.AWSECommerceService;

import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

public class SoapTest {

    private AWSECommerceService awseCommerceService = new AWSECommerceService();

    @Test
    public void getServiceNameTest() {
        Assert.assertEquals(awseCommerceService.getServiceName().getLocalPart(),
                "AWSECommerceService");
    }

    @Test
    public void getFiveUniquePortsTest() {
        Set<String> ports = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            ports.add(awseCommerceService.getPorts().next().toString());
        }
        Assert.assertEquals(ports.size(), 5);
    }

    @Test
    public void getWsdlDocumentationTest() {
        AWSECommerceService awseCommerceService = new AWSECommerceService();
        Assert.assertEquals(awseCommerceService.getWSDLDocumentLocation().toString()
                , "http://webservices.amazon.com/AWSECommerceService/AWSECommerceService.wsdl?wsdl");
    }

    @Test
    public void getDefaultPortTest() {
        Assert.assertEquals(awseCommerceService.getWSDLDocumentLocation().getDefaultPort(),
                80);
    }

    @Test
    public void checkThatUriAndLocationAreTheSameTest() throws URISyntaxException {
        Assert.assertEquals(
                awseCommerceService.getWSDLDocumentLocation().toURI().toString(),
                awseCommerceService.getWSDLDocumentLocation().toString());

    }
}
