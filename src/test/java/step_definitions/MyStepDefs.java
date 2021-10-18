package step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.time.Duration;

public class MyStepDefs {

    private WebDriver webDriver;
    @Given("Open chrome browser and start application")
    public void openChromeBrowserAndStartApplication() {
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
        webDriver.get("https://www.online-calculator.com/full-screen-calculator/");
    }

    @Then("I should be able to see {}")
    public void iShouldBeAbleToSee(String expected) {
        webDriver.switchTo().frame("fullframe");
        WebElement element = webDriver.findElement(By.id("canvas"));
        File actualImage = element.getScreenshotAs(OutputType.FILE);
        new WebDriverWait(webDriver, Duration.ofSeconds(30)).until(driver -> actualImage.exists());
        File expectedImage = new File("src/test/resources/" + getExpectedImages(expected));
        Assert.assertTrue(compareImage(actualImage,expectedImage));
        webDriver.quit();
    }

    @When("I enter following values and press CE button {} and {} with operator {}")
    public void iEnterFollowingValuesAndPressCEButtonValueAndValueWithOperatorOperator(String value1, String value2, String operator) {
        Actions action = new Actions(webDriver);
        action.sendKeys(getKeys(value1)).build().perform();
        action.sendKeys(getKeys(operator)).build().perform();
        action.sendKeys(getKeys(value2)).build().perform();
        action.sendKeys(Keys.EQUALS).build().perform();
    }

    private Keys getKeys(String key) {
        switch (key) {
            case "0": return Keys.NUMPAD0;
            case "1": return Keys.NUMPAD1;
            case "2": return Keys.NUMPAD2;
            case "3": return Keys.NUMPAD3;
            case "4": return Keys.NUMPAD4;
            case "5": return Keys.NUMPAD5;
            case "6": return Keys.NUMPAD6;
            case "7": return Keys.NUMPAD7;
            case "8": return Keys.NUMPAD8;
            case "9": return Keys.NUMPAD9;
            case "/": return Keys.DIVIDE;
            case "+": return Keys.ADD;
            case "-": return Keys.SUBTRACT;
            case "x": return Keys.MULTIPLY;
            default: return Keys.EQUALS;
        }
    }

    private String getExpectedImages(String expected) {
        switch (expected) {
            case "1": return "expected_1.png";
            case "4": return "expected_4.png";
            case "0": return "expected_0.png";
            default: return "";
        }
    }

    private static boolean compareImage(File fileA, File fileB) {
        try {
            // take buffer data from botm image files //
            BufferedImage biA = ImageIO.read(fileA);
            DataBuffer dbA = biA.getData().getDataBuffer();
            int sizeA = dbA.getSize();
            BufferedImage biB = ImageIO.read(fileB);
            DataBuffer dbB = biB.getData().getDataBuffer();
            int sizeB = dbB.getSize();
            // compare data-buffer objects //
            if(sizeA == sizeB) {
                for(int i=0; i<sizeA; i++) {
                    if(dbA.getElem(i) != dbB.getElem(i)) {
                        return false;
                    }
                }
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e) {
            System.out.println("Failed to compare image files ...");
            return  false;
        }
    }
}
