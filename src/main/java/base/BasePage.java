package base;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class BasePage {
    public WebDriverWait getWait(WebDriver driver, long timeOut) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeOut));
    }

    public void waitMillis(WebDriver driver, long millis) {
        long start = System.currentTimeMillis();
        WebDriverWait wait = getWait(driver, millis);
        wait.until(d -> System.currentTimeMillis() >= start + millis);
    }

    public WebElement waitForVisibilityOfElementLocated(WebDriver driver, By locator, long timeOut) {
        WebDriverWait wait = getWait(driver, timeOut);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForElementToBeClickable(WebDriver driver, By locator, long timeOut) {
        WebDriverWait wait = getWait(driver, timeOut);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void sendKeys(WebDriver driver, By locator, long timeOut, String text) {
        WebElement element = waitForElementToBeClickable(driver, locator, timeOut);
        Actions actions = new Actions(driver);
        
        String os = System.getProperty("os.name").toLowerCase();
        Keys modifierKey = os.contains("mac") ? Keys.COMMAND : Keys.CONTROL;
        
        actions.click(element)
               .keyDown(modifierKey)
               .sendKeys("a")
               .keyUp(modifierKey)
               .sendKeys(Keys.BACK_SPACE)
               .perform();
        if (!text.isEmpty()) {
            element.sendKeys(text);
        }
    }

    public void click(WebDriver driver, By locator, long timeOut) {
        WebElement element = waitForElementToBeClickable(driver, locator, timeOut);
        element.click();
    }

    public String getText(WebDriver driver, By locator, long timeOut) {
        WebElement element = waitForVisibilityOfElementLocated(driver, locator, timeOut);
        return element.getText();
    }

    public boolean isElementVisibility(WebDriver driver, By locator, long timeOut) {
        WebDriverWait wait = getWait(driver, timeOut);
        try {
            // Wait for the element to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isDisableButton(WebDriver driver, By locator, long timeOut) {
        WebDriverWait wait = getWait(driver, timeOut);
        try {
            // Wait for the element to be visible
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    public int getLengthCharacterInInputText(WebDriver driver, By locator, long timeOut) {
        WebElement element = waitForVisibilityOfElementLocated(driver, locator, timeOut);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String value = (String) js.executeScript("return arguments[0].value;", element);
        return value != null ? value.length() : 0;
    }

    public String getTextInInputText(WebDriver driver, By locator, long timeOut) {
        WebElement element = waitForVisibilityOfElementLocated(driver, locator, timeOut);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (String) js.executeScript("return arguments[0].value;", element);
    }
}