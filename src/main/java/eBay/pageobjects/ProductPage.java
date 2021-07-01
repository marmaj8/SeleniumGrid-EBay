package eBay.pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProductPage extends BasePage{

    @FindBy(xpath = "//*[@id='atcRedesignId_btn']")
    WebElement addToCartButton;

    @FindBy(xpath = "//*[contains(@class,'nonActPanel')]//select")
    List<WebElement> productOptions;

    public ProductPage(WebDriver driver){
        super(driver, ProductPage.class.getSimpleName());
    }

    @Step("Add product to cart")
    public ProductPage addToCart(){
        log.info("Adding to cart");
        addToCartButton.click();
        return this;
    }

    @Step("Select all first options")
    public ProductPage selectAllOptions(){
        log.info("Checking options to select");
        if (productOptions.size() > 0){
            log.info("Selecting all first options");
            productOptions.forEach(element -> selectFromSelectBoxByIndex(element, 1));
        }
        else{
            log.info("No options to select");
        }
        return this;
    }
}
