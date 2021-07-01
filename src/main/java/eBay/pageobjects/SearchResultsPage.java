package eBay.pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class SearchResultsPage extends BasePage {

    @FindBy(xpath = "//input[contains(@id,'textrange-5-textbox')]")
    WebElement priceMinTextBox;

    @FindBy(xpath = "//input[contains(@id,'textrange-9-textbox')]")
    WebElement priceMaxTextBox;

    @FindBy(xpath = "//div[contains(@id,'textrange')]//button")
    WebElement priceFilterButton;

    @FindBy(xpath = "//*[@id='x-refine__group__4']//ul/li[last()]//a")
    WebElement filterBuyNowButton;

    @FindAll(@FindBy(xpath = "//ul[contains(@class,'srp-results')]/li//a[@class='s-item__link']"))
    List<WebElement> resultLinksInName;

    @FindAll(@FindBy(xpath = "//ul[contains(@class,'srp-results')]/li//*[@class='s-item__image']//a[1]"))
    List<WebElement> resultLinksInPicture;

    @FindAll(@FindBy(xpath = "//ul[contains(@class,'srp-results')]//*[@class='s-item__price']"))
    List<WebElement> prices;

    public SearchResultsPage(WebDriver driver) {
        super(driver, SearchResultsPage.class.getSimpleName());
    }

    @Step("Set minimal price to '{0}'.")
    public SearchResultsPage setMinimalPrice(double price) {
        log.info("Entering into minimal price text box: '" + price + "'");
        priceMinTextBox.sendKeys(Double.toString(price));
        return this;
    }

    @Step("Set maximal price to '{0}'.")
    public SearchResultsPage setMaxPrice(double price) {
        log.info("Entering into maximal price text box: '" + price + "'");
        priceMaxTextBox.sendKeys(Double.toString(price));
        return this;
    }

    @Step("Filter by place")
    public SearchResultsPage filterByPrice() {
        log.info("Filtering by price");
        priceFilterButton.click();
        return this;
    }

    @Step("Filter by buy it now")
    public SearchResultsPage filterByBuyNow() {
        log.info("Filtering by buy it now");
        filterBuyNowButton.click();
        return this;
    }

    @Step("Enter product page for {0} result.")
    public SearchResultsPage enterProduct(int i) {
        log.info("Entering product page for " + i + " result");
        resultLinksInName.get(i)
                .click();
        return this;
    }

    @Step("Get products links from products titles")
    public List<String> getResultLinksFromTitles() {
        log.info("Getting products links from products titles");
        return resultLinksInName.stream()
                .map(element -> element.getAttribute("href")
                        .trim())
                .collect(Collectors.toList());
    }

    @Step("Get products links from products titles")
    public List<String> getResultLinksFromPictures() {
        log.info("Getting products links from products titles");
        return resultLinksInPicture.stream()
                .map(element -> element.getAttribute("href")
                        .trim())
                .collect(Collectors.toList());
    }

    @Step("Get minimal prices of listed products")
    public List<Double> getMinPricesOfProducts() {
        log.info("Getting minimal prices of listed products");
        return prices.stream()
                .map(price -> {
                    String text = price.getText();

                    if (text.contains("to")) {
                        text = text.split("to")[0];
                    }
                    return getDoubleFromText(text);
                })
                .collect(Collectors.toList());
    }

    @Step("Get maximal prices of listed products")
    public List<Double> getMaxPricesOfProducts() {
        log.info("Getting maximal prices of listed products");
        return prices.stream()
                .map(price -> {
                    String text = price.getText();

                    if (text.contains("to")) {
                        text = text.split("to")[1];
                    }
                    return getDoubleFromText(text);
                })
                .collect(Collectors.toList());
    }
}
