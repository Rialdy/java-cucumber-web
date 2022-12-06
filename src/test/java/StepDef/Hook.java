package StepDef;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hook {
    public static Scenario scenario;

    @Before
    public void beforeHook(Scenario scenario){
        System.out.println("Start. . . .");
        scenario.log("Start. . . .");
        Hook.scenario = scenario;
    }
    
    @After
    public void afterHook(){
        YoutubeMusic.driver.close();
        YoutubeMusic.driver.quit();
    }
}
