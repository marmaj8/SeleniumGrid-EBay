package eBay.pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends BasePage {
    private static final long MAX_RECALCULATION_TIME = 2;

    @FindAll(@FindBy(xpath = "//button[@data-test-id='cart-remove-item']"))
    List<WebElement> removeProductButtons;

    @FindAll(@FindBy(xpath = "//*[contains(@class,'quantity')]//select"))
    List<WebElement> quantitySelectors;

    @FindAll(@FindBy(xpath = "//*[contains(@class,'item-price')]"))
    List<WebElement> productsPrices;

    @FindBy(xpath = "//*[@data-test-id='DISCOUNTS']")
    WebElement discount;

    @FindBy(xpath = "//*[@data-test-id='SUBTOTAL']")
    WebElement totalPrice;

    public CartPage(WebDriver driver) {
        super(driver, CartPage.class.getSimpleName());
    }

    @Step("Select quantity of {0} product to {1}")
    public CartPage selectQuantityOfProduct(int product) {
        log.info("Selecting maximal amount of " + product + " product");
        WebElement priceElement = productsPrices.get(product);
        String price = priceElement.getText();

        selectFromSelectBoxLastPosition(quantitySelectors.get(product));

        waitForChangingOfElement(priceElement, Duration.ofSeconds(MAX_RECALCULATION_TIME), price);
        return this;
    }

    @Step("Remove {0} product from cart")
    public CartPage removeProduct(int product) {
        log.info("Removing " + product + " product from cart");
        String price = totalPrice.getText();
        removeProductButtons.get(product)
                .click();
        waitForChangingOfElement(totalPrice, Duration.ofSeconds(MAX_RECALCULATION_TIME), price);
        return this;
    }

    @Step("Get discount")
    public double getDiscount() {
        try {
            log.info("Getting discount");
            return getDoubleFromText(discount.getText());
        } catch (Exception e) {
            log.info("No discount");
            return 0;
        }
    }

    @Step("Get total price")
    public double getTotalPrice() {
        log.info("Getting total price");
        return getDoubleFromText(totalPrice.getText());
    }

    public List<Double> getAllProductPrices() {
        return productsPrices.stream()
                .map(element -> getDoubleFromText(element.getText()))
                .collect(Collectors.toList());


    }
}
