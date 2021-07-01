package eBay;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class SearchTest extends BaseTest {

    @DataProvider(name = "search-terms")
    public Object[][] searchTermsSet() {
        return new Object[][]
                {
                        {"mouse"},
                        {"radio"}
                };
    }

    @DataProvider(name = "price-filtering")
    public Object[][] priceFilteringSet() {
        return new Object[][]
                {
                        {0, 100, "mouse"},
                        {1000, 2000, "tv"}
                };
    }

    @Test(dataProvider = "search-terms")
    public void resultsHaveSameProductLinkInTitleAndPicture(String searchTerm) {
        homePage.open();
        mainLayoutPage.enterTextToSearch(searchTerm)
                .startSearching();

        List<String> titlesLinks = resultsPage.getResultLinksFromTitles();
        List<String> pictureLinks = resultsPage.getResultLinksFromPictures();

        for (int i = 0; i < titlesLinks.size(); i++) {
            softAssert.assertEquals(pictureLinks.get(i), titlesLinks.get(i));
        }

        softAssert.assertAll();
    }

    @Test(dataProvider = "price-filtering")
    public void filterByPriceAllResultsHavePriceInRange(double min, double max, String searchTerm) {
        homePage.open();
        mainLayoutPage.enterTextToSearch(searchTerm)
                .startSearching();

        resultsPage.setMaxPrice(max)
                .setMinimalPrice(min)
                .filterByPrice();
        List<Double> pricesMin = resultsPage.getMinPricesOfProducts();
        List<Double> pricesMax = resultsPage.getMaxPricesOfProducts();

        for (int i = 0; i < pricesMax.size(); i++) {
            double low = pricesMin.get(i);
            double hig = pricesMax.get(i);
            softAssert.assertFalse(
                    low > max || hig < min
            );
        }

        softAssert.assertAll();
    }
}
