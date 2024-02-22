import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pt.ua.IStockMarketService;
import com.pt.ua.Stock;
import com.pt.ua.StocksPortfolio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

@ExtendWith(MockitoExtension.class)
public class StocksPortfolioTest {

    @InjectMocks
    StocksPortfolio stocksPortfolio;

    @Mock
    IStockMarketService market;

    @Test
    public void testLookUpPrice(){
        // "expectations": diz o que o mock vai retornar quando a função for chamada
        when(market.lookUpPrice("IBM")).thenReturn(5.0);
        when(market.lookUpPrice("Google")).thenReturn(2.5);

        stocksPortfolio.addStock(new Stock("IBM", 2));
        stocksPortfolio.addStock(new Stock("Google", 2));

        double result = stocksPortfolio.totalValue();

        // Assert com JUnit
        Assertions.assertEquals(result, 15);
        // Assert com Hamcrest
        assertThat(result, equalTo(15.0));


        verify(market, times(2)).lookUpPrice(anyString());

    }

}