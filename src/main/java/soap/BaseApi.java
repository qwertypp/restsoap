package soap;

import framework.Log;
import framework.Settings;

import javax.xml.soap.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

class BaseApi {

    private static SOAPEnvelope envelope;
    Settings settings = new Settings();
    private static MimeHeaders headers;
    private static SOAPMessage soapMessage;
    static String soapResponse;


    void push(String soapEndpointUrl) throws SOAPException {

        soapMessage.saveChanges();

        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            soapConnection.call(soapMessage, soapEndpointUrl).writeTo(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        soapResponse = new String(out.toByteArray());

        soapConnection.close();
    }

    void initSoap(String myNamespace,String myNamespaceURI) throws SOAPException {
        MessageFactory messageFactory = MessageFactory.newInstance();
        soapMessage = messageFactory.createMessage();

        SOAPPart soapPart = soapMessage.getSOAPPart();

        envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

        headers = soapMessage.getMimeHeaders();
    }

    SOAPElement addElementToBody(SOAPElement parentElement, String elementName, String namespace) throws SOAPException {
        if (parentElement == null) {
            Log.logger.info("Init body element " + elementName);
            SOAPBody soapBody = envelope.getBody();
            return soapBody.addChildElement(elementName, namespace);
        } else {
            Log.logger.info("Adding element " + elementName + "\nInside parent " + parentElement.getLocalName());
            return parentElement.addChildElement(elementName, namespace);
        }
    }

    SOAPElement addElementToBody(String elementName, String namespace) throws SOAPException {
        return addElementToBody(null, elementName, namespace);
    }

    void addTextNode(SOAPElement element, String text) throws SOAPException {
        element.addTextNode(text);
    }

    void addHeader(String key, String value) {
        Log.logger.info("Adding headers with key " + key + "\nValue " + value);
        headers.addHeader(key, value);
    }

}

