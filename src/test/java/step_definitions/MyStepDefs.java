package step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MyStepDefs {

    private WebDriver webDriver;
    @Given("Open chrome browser and start application")
    public void openChromeBrowserAndStartApplication() {
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
        webDriver.get("https://www.online-calculator.com/full-screen-calculator/");
    }

    @Then("I should be able to see {}")
    public void iShouldBeAbleToSee(String expected) throws IOException {
        webDriver.switchTo().frame("fullframe");
        WebElement element = webDriver.findElement(By.id("canvas"));
        File f = element.getScreenshotAs(OutputType.FILE);
//        Screenshot image = new AShot().takeScreenshot(webDriver, element);
        BufferedImage actualImage = ImageIO.read(f);
        FileUtils.copyFile(f, new File("src/test/resources/expected_" + expected + ".png"));
        BufferedImage expectedImage = ImageIO.read(new File("src/test/resources/" + getExpectedImages(expected)));
        ImageDiffer imgDiff = new ImageDiffer();
        ImageDiff diff = imgDiff.makeDiff(actualImage, actualImage);
        Assert.assertTrue(diff.hasDiff());
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
            case "2": return "expected_2.png";
            case "0": return "expected_0.png";
            default: return "";
        }
    }
}
