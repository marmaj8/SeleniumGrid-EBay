package eBay.pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class AddedToCartPopUp extends BasePage {
    private static final long MAX_LOADING_TIME = 20;

    @FindBy(xpath = "//*[contains(@class,'viicon-close')]")
    WebElement closeButton;

    public AddedToCartPopUp(WebDriver driver) {
        super(driver, AddedToCartPopUp.class.getSimpleName());
    }

    @Step("Close pop up")
    public AddedToCartPopUp close() {
        waitForVisibilityOfElement(closeButton, Duration.ofSeconds(MAX_LOADING_TIME));
        log.info("Closing pop up");
        closeButton.click();
        return this;
    }
}
