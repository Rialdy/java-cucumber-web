package StepDef;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class YoutubeMusic {
    public static WebDriver driver;
    private static final String dir = System.getProperty("user.dir");
    private static WebDriverWait wait = null;

    public void initDriver(){
        System.setProperty("webdriver.chrome.driver", dir+ "/driver/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Given("I open browser")
     public void iOpenBrowser(){
        initDriver();
        Hook.scenario.log("Browser Launched");
     }

    @And("I go to youtube music page")
     public void inpubtTheUrlOnTheAddressBar(){
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.get("https://music.youtube.com/");
        Hook.scenario.log("YoutubeMusic page is opened");
     }

    @Then("I should be in youtube music page")
     public void clichSearchMenu(){
        boolean page = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@aria-selected='true']/*[text()='Home']"))).isDisplayed();
        if (page != true){
            throw new Error("Home page did not load correctly.");
        }
        Hook.scenario.log("Yotube Music Page is opened");
        }

    @Then("Click Search menu")
     public void clickSearchMenu(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ytmusic-search-box"))).click();
        Hook.scenario.log("Search Button is Clicked");
     }

    @And("Input text \"([^\"]*)\"$")
     public void inputText(String textString) throws InterruptedException{
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='input']"))).sendKeys(textString);   
        Hook.scenario.log("Text is inputted");
     }
    
    @Then("I click enter")
     public void iClickEnter(){
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='input']"))).sendKeys(Keys.ENTER);
       Hook.scenario.log("User Continue the Search");
     }
    
    @Then("User should get the correct result")
     public void getCorrectResult(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[@aria-label=\"Slipknot\"])[2]"))).isDisplayed();
        Hook.scenario.log("The Result is appear");
     }
    
    @Then("User click the artist") 
     public void clickTheArtist(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[@aria-label=\"Slipknot\"])[2]"))).click();
        Hook.scenario.log("User click the artist");
     }
    
    @Then("User verified the artist page")
     public void verifiedTheArtistPage(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div/yt-formatted-string)[1]"))).isDisplayed();
        Hook.scenario.log("User verify the artist page");
     }

     @Then("I click explore menu")
      public void clickExploreMenu(){
         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@tab-id='FEmusic_explore']"))).click();
         Hook.scenario.log("User click explore menu");
      }

     @Then("The explore page is opened")
      public void explorePageOpened(){
         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div/yt-formatted-string/a)[1]"))).isDisplayed();
         Hook.scenario.log("User verify xplore page");
      }
} 
    