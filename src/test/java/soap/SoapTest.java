package soap;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SoapTest extends BaseApi{

    private CelsiusToFahrenheit celsius = new CelsiusToFahrenheit();
    private FahrenheitToCelsius fahrenheit = new FahrenheitToCelsius();

    @Test
    public void getFahrenheitPositiveTest() {
        celsius.init("100");
        Assert.assertEquals( celsius.serializeCelsius().Body.CelsiusToFahrenheitResponse.CelsiusToFahrenheitResult,
                "212");
    }

    @Test
    public void getFahrenheitNegativeValueTest() {
        celsius.init("-50");
        Assert.assertEquals( celsius.serializeCelsius().Body.CelsiusToFahrenheitResponse.CelsiusToFahrenheitResult,
                "-58");
    }

    @Test
    public void getFahrenheitNegativeTest() {
        celsius.init("null");
        Assert.assertEquals( celsius.serializeCelsius().Body.CelsiusToFahrenheitResponse.CelsiusToFahrenheitResult,
                "Error");
    }

    @Test
    public void getCelsiusPositiveTest() {
        fahrenheit.init("100");
        Assert.assertEquals( fahrenheit.serializeFahrenheitResponse().Body.FahrenheitToCelsiusResponse.FahrenheitToCelsiusResult,
                "37.8");
    }

    @Test
    public void getCelsiusNegativeValueTest() {
        fahrenheit.init("-50");
        Assert.assertEquals( fahrenheit.serializeFahrenheitResponse().Body.FahrenheitToCelsiusResponse.FahrenheitToCelsiusResult,
                "-46");
    }

    @Test
    public void getCelsiusNegativeTest() {
        fahrenheit.init("");
        Assert.assertEquals( fahrenheit.serializeFahrenheitResponse().Body.FahrenheitToCelsiusResponse.FahrenheitToCelsiusResult,
                "Error");
    }

    @Test
    public void bothServicesTest(){
        fahrenheit.init("80");
        String celsiusResult = fahrenheit.serializeFahrenheitResponse().Body.FahrenheitToCelsiusResponse.FahrenheitToCelsiusResult;
        celsius.init(celsiusResult);
        Assert.assertEquals(celsius.serializeCelsius().Body.CelsiusToFahrenheitResponse.CelsiusToFahrenheitResult,
                "80");
    }

    @Test
    public void compareWithFilePositiveTest(){
        fahrenheit.init("80");

        Assert.assertTrue(fahrenheit.serializeFahrenheitCorrectData().equals(
                fahrenheit.serializeFahrenheitResponse()));
    }

    @Test
    public void compareWithFileNegativeTest(){
        fahrenheit.init("100");

        Assert.assertTrue(fahrenheit.serializeFahrenheitCorrectData().equals(
                fahrenheit.serializeFahrenheitResponse()));
    }
}
