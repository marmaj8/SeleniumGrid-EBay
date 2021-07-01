package eBay.pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainLayoutPage extends BasePage {
    @FindBy(xpath = "//input[@id='gh-ac']")
    WebElement searchTextBox;

    @FindBy(xpath = "//input[@id='gh-btn']")
    WebElement searchButton;

    @FindBy(xpath = "//a[@class='gh-eb-li-a']")
    WebElement cartButton;

    public MainLayoutPage(WebDriver driver) {
        super(driver, MainLayoutPage.class.getSimpleName());
    }

    @Step("Enter into search text box: '{0}'.")
    public MainLayoutPage enterTextToSearch(String searchTerm) {
        log.info("Entering into search box: '" + searchTerm + "'");
        searchTextBox.sendKeys(searchTerm);
        return this;
    }

    @Step("Start searching")
    public MainLayoutPage startSearching() {
        log.info("Starting searching");
        searchButton.click();
        return this;
    }

    @Step("Enter cart")
    public MainLayoutPage enterCart() {
        log.info("Entering cart");
        cartButton.click();
        return this;
    }
}
