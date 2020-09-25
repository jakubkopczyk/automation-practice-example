package com.jakubkopczyk.cucumber.framework.config;

import cucumber.api.Scenario;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScenarioSession {

    private Scenario scenario;

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void writeTextToReport(String s) {
        scenario.write(s);
    }

    public void writeTestVariableToReport(String key, String value) {
        scenario.write("\"" + key + "\"" + ": " + value);
    }
}