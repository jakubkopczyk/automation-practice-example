package com.jakubkopczyk.cucumber.framework.teststeps;

import com.jakubkopczyk.cucumber.framework.config.PropertiesLoader;
import com.jakubkopczyk.cucumber.framework.config.ScenarioSession;
import com.jakubkopczyk.cucumber.framework.helpers.WebDriverWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseAnnotations {
    protected static final Logger logger = LoggerFactory.getLogger(BaseAnnotations.class);
    @Autowired
    protected WebDriverWrapper driverWrapper;
    @Autowired
    protected PropertiesLoader propertiesLoader;
    @Autowired
    protected ScenarioSession scenarioSession;
}
