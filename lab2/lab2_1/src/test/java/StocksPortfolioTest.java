import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

import org.example.IStockMarketService;
import  org.example.StocksPortfolio;
import org.example.Stock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class StocksPortfolioTest {

    @Mock
    IStockMarketService iStockMarketService;

    @InjectMocks
    StocksPortfolio stocksPortfolio;

    @Test
    void getTotalValue() {
        // load the mock with the proper expectations
        when(iStockMarketService.lookUpPrice("TSLA")).thenReturn(4.0);
        when(iStockMarketService.lookUpPrice("NVDA")).thenReturn(2.5);
        when(iStockMarketService.lookUpPrice("AAPL")).thenReturn(150.0); // Unused in the test
        when(iStockMarketService.lookUpPrice("AMZN")).thenReturn(3000.0); // Unused in the test

        // create our stocks
        Stock tesla = new Stock("TSLA", 2);
        Stock nvidia = new Stock("NVDA", 4);

        // Execute the test (add the stocks to the portfolio)
        stocksPortfolio.addStock(tesla);
        stocksPortfolio.addStock(nvidia);
        // get the total value
        double totalValue = stocksPortfolio.totalValue();

        // verify the result (assert)
        // assert using JUnit5
        // assertEquals(18.0, totalValue);
        // assert using Hamcrest
        assertThat(totalValue, is(18.0));

    }

    @Test
    void mostValuableStocksTest(){

        when(iStockMarketService.lookUpPrice("TSLA")).thenReturn(4.0);
        when(iStockMarketService.lookUpPrice("NVDA")).thenReturn(2.5);
        when(iStockMarketService.lookUpPrice("AAPL")).thenReturn(150.0);

        Stock tesla = new Stock("TSLA", 2);
        Stock nvidia = new Stock("NVDA", 4);
        Stock aapl = new Stock("AAPL", 1);

        stocksPortfolio.addStock(tesla);
        stocksPortfolio.addStock(nvidia);
        stocksPortfolio.addStock(aapl);

        List<Stock> stocks = stocksPortfolio.mostValuableStocks(2);

        assertEquals(List.of(aapl, nvidia), stocks);
    }

    @Test
    void mostValuableStocksEmptyTest(){

        List<Stock> stocks = stocksPortfolio.mostValuableStocks(1);

        assertEquals(List.of(), stocks);
    }

    @Test
    void mostValuableStockExceedsMaxTest(){

        when(iStockMarketService.lookUpPrice("TSLA")).thenReturn(4.0);
        when(iStockMarketService.lookUpPrice("NVDA")).thenReturn(2.5);
        when(iStockMarketService.lookUpPrice("AAPL")).thenReturn(150.0);

        Stock tesla = new Stock("TSLA", 2);
        Stock nvidia = new Stock("NVDA", 4);
        Stock aapl = new Stock("AAPL", 1);

        stocksPortfolio.addStock(tesla);
        stocksPortfolio.addStock(nvidia);
        stocksPortfolio.addStock(aapl);

        List<Stock> stocks = stocksPortfolio.mostValuableStocks(4);

        assertEquals(List.of(aapl, nvidia, tesla), stocks);
    }

    @Test
    void mostValuableStocksDuplicateTest(){

        when(iStockMarketService.lookUpPrice("TSLA")).thenReturn(4.0);
        when(iStockMarketService.lookUpPrice("NVDA")).thenReturn(2.5);

        Stock tesla = new Stock("TSLA", 2);
        Stock tesla2 = new Stock("TSLA", 1);
        Stock nvidia = new Stock("NVDA", 4);

        stocksPortfolio.addStock(tesla);
        stocksPortfolio.addStock(nvidia);
        stocksPortfolio.addStock(tesla2);

        Stock testlaTotal = new Stock("TSLA", 3);

        List<Stock> stocks = stocksPortfolio.mostValuableStocks(1);

        assertEquals(List.of(testlaTotal), stocks);

    }
}
