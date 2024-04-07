package com.pt.ua.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import com.pt.ua.app.cache.ExchangeRateCache;

public class CacheTest {

    String usd;
    String gbp;
    String brl;
    double usdValue;
    double gbpValue;
    double brlValue;
    
    @BeforeEach
    public void setUp(){
        usd  = "USD";
        gbp  = "GBP";
        brl = "BRL";

        usdValue = 1.0841752568;
        gbpValue = 0.868432;
        brlValue = 5.30;
    }

    @Test
    void testCacheHit() {
        ExchangeRateCache cache = new ExchangeRateCache(10);
        cache.put(usd, usdValue);
        cache.put(gbp, gbpValue);
        cache.put(brl, brlValue);

        double usdCachedValue = cache.get(usd);
        double gbpCachedValue = cache.get(gbp);
        double brlCachedValue = cache.get(brl);

        assertThat(usdCachedValue).isEqualTo(usdValue);
        assertThat(gbpCachedValue).isEqualTo(gbpValue);
        assertThat(brlCachedValue).isEqualTo(brlValue);
    }

    @Test
    void testCacheMiss() {
        ExchangeRateCache cache = new ExchangeRateCache(10);
        cache.put(usd, usdValue);
        cache.put(gbp, gbpValue);

        double brlCachedValue = cache.get(brl);

        assertThat(brlCachedValue).isEqualTo(-1.0);
    }

    @Test
    void cacheMissAfterTTL() throws InterruptedException {
        ExchangeRateCache cache = new ExchangeRateCache(10);
        cache.put(usd, usdValue);
        cache.put(gbp, gbpValue);

        Thread.sleep(11* 1000L);

        double usdCachedValue = cache.get(usd);
        double gbpCachedValue = cache.get(gbp);

        assertThat(usdCachedValue).isEqualTo(-1.0);
        assertThat(gbpCachedValue).isEqualTo(-1.0);
    }
}
