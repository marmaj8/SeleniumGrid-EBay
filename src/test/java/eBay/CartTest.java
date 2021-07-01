package eBay;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class CartTest extends BaseTest {

    @DataProvider(name = "products-in-cart-changes")
    public Object[][] productsInCartChanges() {
        return new Object[][]
                {
                        {"mouse", "book", 0},
                        {"radio", "book", 1}
                };
    }

    public void putProductToCart(String... products) {
        homePage.open();

        Arrays.stream(products)
                .forEach(product -> {
                    mainLayoutPage.enterTextToSearch(product)
                            .startSearching();
                    resultsPage.filterByBuyNow()
                            .enterProduct(0);
                    productPage.selectAllOptions()
                            .addToCart();
                    addedToCartPopUp.close();
                });
        mainLayoutPage.enterCart();
    }

    @Test(dataProvider = "products-in-cart-changes")
    public void changingAmountOfProductsInCartChangeTotalPrice(String searchTerm1, String searchTerm2, int product) {
        putProductToCart(searchTerm1, searchTerm2);

        List<Double> pricesBefore = cartPage.getAllProductPrices();

        cartPage.selectQuantityOfProduct(product);

        List<Double> pricesAfter = cartPage.getAllProductPrices();
        double totalPriceAfter = cartPage.getTotalPrice();
        double total = pricesAfter.stream()
                .mapToDouble(d -> d)
                .sum();
        double discount = cartPage.getDiscount();
        total += discount;

        for (int i = 0; i < pricesAfter.size(); i++) {
            if (i != product) {
                softAssert.assertEquals(pricesAfter.get(i), pricesBefore.get(i), PRICE_DELTA);
            }
        }

        Assert.assertEquals(totalPriceAfter, total, PRICE_DELTA);
        softAssert.assertAll();
    }

    @Test(dataProvider = "products-in-cart-changes")
    public void removingProductFromCartChangeTotalPrice(String searchTerm1, String searchTerm2, int productToRemove) {
        putProductToCart(searchTerm1, searchTerm2);

        List<Double> pricesBefore = cartPage.getAllProductPrices();

        cartPage.removeProduct(productToRemove);

        List<Double> pricesAfter = cartPage.getAllProductPrices();
        double totalPriceAfter = cartPage.getTotalPrice();
        double total = pricesAfter.stream()
                .mapToDouble(d -> d)
                .sum();
        double discount = cartPage.getDiscount();
        total += discount;

        int oldProductCounter = 0;
        for (int i = 0; i < pricesAfter.size(); i++) {
            if (i == productToRemove) {
                oldProductCounter++;
            }
            softAssert.assertEquals(pricesAfter.get(i), pricesBefore.get(oldProductCounter), PRICE_DELTA);
        }

        Assert.assertEquals(totalPriceAfter, total, PRICE_DELTA);
        softAssert.assertAll();
    }
}
