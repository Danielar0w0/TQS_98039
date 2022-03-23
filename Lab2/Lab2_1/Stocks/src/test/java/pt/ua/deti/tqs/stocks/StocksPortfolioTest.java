package pt.ua.deti.tqs.stocks;

import org.hamcrest.MatcherAssert;
import org.hamcrest.number.IsCloseTo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StocksPortfolioTest {

    @Mock
    private IStockmarketService stockmarket;

    private List<Stock> stocks;
    private StocksPortfolio stocksPortfolio;

    @BeforeEach
    void setUp() {
        stocksPortfolio = new StocksPortfolio(stockmarket);
    }

    @Test
    void addStock() {

        String[] labels = {"Apple", "Sega"};
        int[] quantities = {30, 20};

        List<Stock> stocks = new ArrayList<>();
        for (int i = 0; i < 2; i++) {

            // Add stock
            Stock stock = new Stock(labels[i], quantities[i]);
            stocksPortfolio.addStock(stock);

            stocks.add(stock);
        }

        assertEquals(stocks, stocksPortfolio.getStocks());
        assertThat(stocksPortfolio.getStocks(), hasSize(2));
    }

    @Test
    void getTotalValue() {

        double[] prices = {100, 40};
        String[] labels = {"Apple", "Sega"};
        int[] quantities = {30, 20};

        double total = 0;
        for (int i = 0; i < 2; i++) {

            // Add stock
            Stock stock = new Stock(labels[i], quantities[i]);
            stocksPortfolio.addStock(stock);

            // Load the mock with the proper expectations
            Mockito.when(stockmarket.lookUpPrice(labels[i])).thenReturn(prices[i]);

            // Update real total
            total += prices[i] * quantities[i];
        }

        double result = stocksPortfolio.getTotalValue();

        // Compare with real total
        assertEquals(total, result);
        assertThat(result, IsCloseTo.closeTo(total, 0));

        Mockito.verify(stockmarket).lookUpPrice(labels[0]);
        Mockito.verify(stockmarket).lookUpPrice(labels[1]);
        Mockito.verify(stockmarket, Mockito.times(2)).lookUpPrice(Mockito.any(String.class));

    }
}