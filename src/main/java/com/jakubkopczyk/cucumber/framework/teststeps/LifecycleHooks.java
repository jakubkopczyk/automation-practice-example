package com.jakubkopczyk.cucumber.framework.teststeps;

import com.jakubkopczyk.cucumber.framework.config.PropertiesLoader;
import com.jakubkopczyk.cucumber.framework.config.ScenarioSession;
import com.jakubkopczyk.cucumber.framework.helpers.WebDriverWrapper;
import cucumber.api.PickleStepTestStep;
import cucumber.api.Scenario;
import cucumber.api.TestCase;
import cucumber.api.TestStep;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.BeforeStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class LifecycleHooks {

    private static final Logger logger = LoggerFactory.getLogger(LifecycleHooks.class);

    @Autowired
    private WebDriverWrapper driverWrapper;

    @Autowired
    private PropertiesLoader propertiesLoader;

    @Autowired
    private ScenarioSession scenarioSession;

    private int stepIteration;

    public LifecycleHooks(WebDriverWrapper driverWrapper, PropertiesLoader propertiesLoader, ScenarioSession scenarioSession) {
        this.driverWrapper = driverWrapper;
        this.propertiesLoader = propertiesLoader;
        this.scenarioSession = scenarioSession;
        logger.debug("Creating LifecycleHooks...");

    }

    @Before
    public void startup(Scenario scenario) {
        scenarioSession.setScenario(scenario);
        driverWrapper.maximizeBrowser();
        logger.debug("----------------------------------------------------------------------------------------");
        logger.debug("Before cucumber scenario: ********** {} **********", scenario.getName());
        System.out.println(scenario.getName() + " - START - " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));

        //driverWrapper.browseTo("/");

        stepIteration = 0;
    }

    @After
    public void teardown(Scenario scenario) {
        logger.debug("After cucumber scenario: ********** {} **********", scenario.getName());
        System.out.println(scenario.getName() + " - END - " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
        if (scenario.isFailed()) {
            System.out.println("FAILED");
        }

        // add screenshot to the scenario
        outputFinalScreenshot(scenario);

        // test cleanup
        driverWrapper.reset();

        // close extra tabs in the browser
        driverWrapper.closeTabs();
    }

    @BeforeStep
    public void logStepName(Scenario scenario) {
        try {
            Field testCaseField = scenario.getClass().getDeclaredField("testCase");
            testCaseField.setAccessible(true);
            TestCase testCase = (TestCase) testCaseField.get(scenario);

            Field testStepsField = testCase.getClass().getDeclaredField("testSteps");
            testStepsField.setAccessible(true);
            ArrayList<TestStep> testSteps = (ArrayList<TestStep>) testStepsField.get(testCase);

            logger.debug("| Step " + ++stepIteration + " - " + ((PickleStepTestStep) testSteps.get(stepIteration - 1)).getStepText());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.debug("LOG ERROR: Step name could not be returned");
        }
    }

    private void outputFinalScreenshot(Scenario scenario) {
        String takeScreenshots = propertiesLoader.getTakeScreenshots();
        switch (takeScreenshots) {
            case "onErrorOnly":
                if (scenario.isFailed()) {
                    takeScreenshot(scenario);
                }
                break;

            case "always":
                takeScreenshot(scenario);
                break;

            case "never":
                break;

            default:
                String message = "Unknown takeScreenshots setting: " + takeScreenshots;
                logger.error(message);
                throw new IllegalArgumentException(message);
        }
    }

    private void takeScreenshot(Scenario scenario) {
        byte[] screenshot = driverWrapper.takeScreenshot();
        if (screenshot != null) {
            scenario.embed(screenshot, "image/png");
        }
    }
}