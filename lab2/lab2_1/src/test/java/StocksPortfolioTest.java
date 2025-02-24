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

@ExtendWith(MockitoExtension.class)
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
        assertEquals(18.0, totalValue);
        // assert using Hamcrest
        assertThat(totalValue, is(18.0));

    }

}
