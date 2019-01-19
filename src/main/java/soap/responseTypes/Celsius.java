package soap.responseTypes;

public class Celsius {
    public Body Body;
    public class Body{
        public CelsiusToFahrenheitResponse CelsiusToFahrenheitResponse;
        public class CelsiusToFahrenheitResponse {
            public String CelsiusToFahrenheitResult;
        }
    }


}
