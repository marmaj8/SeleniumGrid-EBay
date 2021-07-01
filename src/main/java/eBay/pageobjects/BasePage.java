package eBay.pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    private static final String NON_NUMERIC_PRICE_PART = "[^\\d^.]";

    protected WebDriver driver;
    protected Logger log;

    public BasePage(WebDriver driver, String className) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
        log = LogManager.getLogger(className);
    }

    protected void selectFromSelectBoxByText(WebElement element, String text) {
        log.info("Trying to select option with text '" + text + "' from select box '" + element + "'");
        Select select = new Select(element);

        try {
            select.selectByVisibleText(text);
        } catch (Exception e) {
            log.error("Option cannot be selected");
            throw e;
        }
    }

    protected void selectFromSelectBoxByIndex(WebElement element, int index) {
        log.info("Trying to select option with index '" + index + "' from select box '" + element);
        Select select = new Select(element);

        try {
            select.selectByIndex(index);
        } catch (Exception e) {
            log.error("Option cannot be selected");
            throw e;
        }
    }

    protected void selectFromSelectBoxLastPosition(WebElement element) {
        log.info("Detecting of the last index of " + element);
        Select select = new Select(element);
        //selectFromSelectBoxByIndex(element, select.getOptions().size() - 1);
    }

    protected void waitForVisibilityOfElement(WebElement element, Duration time) {
        log.info("Wait " + time + " for visibility of " + element);
        WebDriverWait wait = new WebDriverWait(driver, time);
        wait.until(ExpectedConditions.visibilityOfAllElements(element));
    }

    protected void waitForChangingOfElement(WebElement element, Duration time, String textBefore) {
        log.info("Wait " + time + " for changing of " + element);
        WebDriverWait wait = new WebDriverWait(driver, time);
        try{
            wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(element, textBefore)));
        }
        catch (Exception ex){
            log.info("Element not changed");
        }
    }

    protected double getDoubleFromText(String text) {
        String number = text.replaceAll(NON_NUMERIC_PRICE_PART, "");
        return Double.parseDouble(number);
    }
}
