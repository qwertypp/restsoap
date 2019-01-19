package soap;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import soap.responseTypes.Fahrenheit;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import java.io.IOException;

class FahrenheitToCelsius extends BaseApi {
    private String nameSpace = "myNamespace";
    private String soapEndpointUrl = "https://www.w3schools.com/xml/tempconvert.asmx";
    private String soapAction = "https://www.w3schools.com/xml/FahrenheitToCelsius";

    void init(String fahrenheitNumber){
        try {
            initSoap(nameSpace, settings.getSoapServerUrl());
            SOAPElement FahrenheitToCelsius = addElementToBody("FahrenheitToCelsius", nameSpace);
            SOAPElement celsius = addElementToBody(FahrenheitToCelsius, "Fahrenheit", nameSpace);
            addTextNode(celsius, fahrenheitNumber);
            addHeader("SOAPAction", soapAction);
            push(soapEndpointUrl);

        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    Fahrenheit serializeFahrenheitResponse(){
        XmlMapper xmlMapper = new XmlMapper();
        try {
            return xmlMapper.readValue(soapResponse, Fahrenheit.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    Fahrenheit serializeFahrenheitCorrectData(){
        XmlMapper xmlMapper = new XmlMapper();
        Fahrenheit value = null;
        String xml = settings.getExpectedXmlTestDataAsString("fahrenheitCorrectData");
        try {
            value = xmlMapper.readValue(xml, Fahrenheit.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }
}
