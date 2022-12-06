# Test Demo Setup

**The Tools Needed**
- Download [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
- Download Web Driver, please download based on the browser version
    - [Chromedriver](https://chromedriver.chromium.org/downloads)
    - [Geckodriver](https://github.com/mozilla/geckodriver/releases)
- Download [JAVA] https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html

**Installation**
- Install Intellij IDEA
- Install JAVA
- Adding JAVA into our bash_profile or zsh_profile profile
    - Open Terminal
    - Type sudo nano ~/.bash_profile or ~./zsh_profile
    - Add PATH JAVA below
        - export JAVA_HOME=$(/usr/libexec/java_home -v 1.8.0_333)
        - export PATH="$PATH:$JAVA_HOME/bin"
- Close the bash_profile dont forget to save
- On terminal type source ~/.bash_profile

**Start New Project**
1. Create New Project using MAVEN
2. Install Plugin Cucumber and Gherkin
    - Go to Preferences
    - Go to Plugins
    - Searching for Cucumber and also Gherkin and perform install
3. Put all dependecies on your pom.xml 

    ```xml
            <?xml version="1.0" encoding="UTF-8"?>
            <project xmlns="http://maven.apache.org/POM/4.0.0"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
            <modelVersion>4.0.0</modelVersion>

            <groupId>org.example</groupId>
            <artifactId>TestDemo</artifactId>
            <version>1.0-SNAPSHOT</version>

            <properties>
                <maven.compiler.source>8</maven.compiler.source>
                <maven.compiler.target>8</maven.compiler.target>
                <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
            </properties>

            <dependencies>
                    <dependency>
                    <groupId>junit</groupId>
                    <artifactId>junit</artifactId>
                    <version>4.13.1</version>
                    <scope>test</scope>
                    </dependency>
                
                    <!-- https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java -->
                <dependency>
                    <groupId>org.seleniumhq.selenium</groupId>
                    <artifactId>selenium-java</artifactId>
                    <version>4.6.0</version>
                </dependency>
                
                <!-- https://mvnrepository.com/artifact/io.cucumber/cucumber-java -->
                <dependency>
                    <groupId>io.cucumber</groupId>
                    <artifactId>cucumber-java</artifactId>
                    <version>7.9.0</version>
                </dependency>
                
                <!-- https://mvnrepository.com/artifact/io.cucumber/cucumber-junit -->
                <dependency>
                    <groupId>io.cucumber</groupId>
                    <artifactId>cucumber-junit</artifactId>
                    <version>7.9.0</version>
                    <scope>test</scope>
                </dependency>
                
            </dependencies>
                
                <build>
                    <pluginManagement><!-- lock down plugins versions to avoid using Maven defaults (may be moved to parent pom) -->
                    <plugins>
                        <plugin>
                        <artifactId>maven-compiler-plugin</artifactId>
                        <version>3.8.0</version>
                        <configuration>
                        <source>1.8</source>
                        <target>1.8</target>
                            <encoding>UTF-8</encoding>          
                        </configuration>
                        </plugin>
                        <plugin>
                        <artifactId>maven-surefire-plugin</artifactId>
                        <version>2.22.1</version>
                        </plugin>
                        <plugin>
                        <artifactId>maven-project-info-reports-plugin</artifactId>
                        <version>3.0.0</version>
                        </plugin>
                    </plugins>
                    </pluginManagement>
                </build>
            </project>
    ```

4. Create new directory with name driver. Add put your webdriver inside the folder /driver/chromedirver or /driver/geckodriver
5. Create new directory called resources /src/test/resources/features and create feature file e.g youtubeMusic.feature and put on folder /src/test/resources/features
6. Put Gherkin style under file youtubeMusic.feature
    ```gherkin
        Feature: Youtube Music Search
            Scenario: TC005 - User should able to use search function
                Given I Open browser
                And Input the url on the address bar
                Then Click Search menu
                And Input text "Slipknot"
                Then Click enter
                Then User should find the artist based on the search
                Then User click the artist
                And User verified the page
    ```

7. Add new package "StepDef" and "TestRunner" on folder /src/test/java
8. Create java class "youtubeMusic" and put it into package /src/test/java/StepDef
    ```java
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
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div/yt-formatted-string)[1]")));
                    Hook.scenario.log("User verify the artist page");
                }
            }
    ```

9. Create java class "TestRunner" and put into /src/test/java/TestRunner
    ```java
                package TestRunner;

                import io.cucumber.junit.Cucumber;
                import io.cucumber.junit.CucumberOptions;
                import org.junit.runner.RunWith;

                @RunWith(Cucumber.class)
                @CucumberOptions(features ="src/test/resources/features",
                                glue = {"StepDef"},
                                plugin = {"pretty","html:target/cucumber.html"})
                public class TestRunner {
                }
    ```
10. Create java class "Hook" and put into /src/test/java/StepDef
    ```java
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
    ```

11. Go to terminal and type "mvn test" for run the script
12. You can check the report on /target/cucumber.json or go to /target/cucumber-html-reports/file-src-test-resources-features-youtubeMusic-feature.html. Open the html file via browser