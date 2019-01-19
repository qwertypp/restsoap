package soap.responseTypes;

import framework.Log;

public class Fahrenheit {
    public Body Body;
    public class Body{
        public FahrenheitToCelsiusResponse FahrenheitToCelsiusResponse;
        public class FahrenheitToCelsiusResponse {
            public String FahrenheitToCelsiusResult;
        }
    }

    public boolean equals(Fahrenheit fahrenheit){
        String expectedResult = this.Body.FahrenheitToCelsiusResponse.FahrenheitToCelsiusResult;
        String actualResult = fahrenheit.Body.FahrenheitToCelsiusResponse.FahrenheitToCelsiusResult;
        if (!expectedResult.equals(actualResult)){
            Log.logger.info("Objects are not equals\nExpected "+expectedResult
                    +"\nActual "+actualResult);
            return false;
        }
        return true;
    }

}
