package rest;

import framework.Settings;

class DataGetter {
    private Settings settings = new Settings();

    String getExpectedCorrectData() {
        return settings.getExpectedJsonTestData("correctData");
    }

    String getExpectedInCorrectData() {
        return settings.getExpectedJsonTestData("incorrectData");
    }
}
