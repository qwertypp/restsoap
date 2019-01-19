package soap;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import soap.responseTypes.Celsius;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import java.io.IOException;

class CelsiusToFahrenheit extends BaseApi {
    private String nameSpace = "myNamespace";
    private String soapEndpointUrl = "https://www.w3schools.com/xml/tempconvert.asmx";
    private String soapAction = "https://www.w3schools.com/xml/CelsiusToFahrenheit";

    void init(String celsiusNumber){
        try {
            initSoap(nameSpace, settings.getSoapServerUrl());
            SOAPElement celsiusToFahrenheit = addElementToBody("CelsiusToFahrenheit", nameSpace);
            SOAPElement celsius = addElementToBody(celsiusToFahrenheit, "Celsius", nameSpace);
            addTextNode(celsius, celsiusNumber);
            addHeader("SOAPAction", soapAction);
            push(soapEndpointUrl);

        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    Celsius serializeCelsius(){
        XmlMapper xmlMapper = new XmlMapper();
        try {
            return xmlMapper.readValue(soapResponse, Celsius.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
