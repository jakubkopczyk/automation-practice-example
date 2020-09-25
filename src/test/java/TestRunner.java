import com.jakubkopczyk.cucumber.framework.config.PropertiesLoader;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@CucumberOptions(plugin = {
        "json:target/cucumber-report/cucumber-json.json", "html:target/cucumber-report/cucumber-html/",
        "pretty:target/cucumber-report/cucumber-pretty.txt", "junit:target/cucumber-report/cucumber-junit.xml", "usage:target/cucumber-report/cucumber-usage.json"},
        features = {"src/test/java/features"},
        glue = {"com/jakubkopczyk/cucumber/framework/teststeps"},
        tags = {"@socialMedia"})

public class TestRunner extends AbstractTestNGCucumberTests {
    private ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertiesLoader.class);
    private PropertiesLoader propertiesLoader = ctx.getBean(PropertiesLoader.class);

}