package com.pt.ua.app.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExchangeRateCache {
    
    private final Map<String, CacheEntry> cache;
    private int ttl;

    private static final Logger log = LoggerFactory.getLogger(ExchangeRateCache.class);

    public ExchangeRateCache(int ttl) {
        this.ttl = ttl;
        this.cache = new ConcurrentHashMap<>();
        new Thread(new CacheCleanupTask()).start();
    }

    public double get(String currency) {
        CacheEntry entry = cache.get(currency);
        if (entry != null && !entry.isExpired()) {
            return entry.getValue();
        }
        
        log.info(currency + " not found in cache or expired");

        return -1.0;
    }

    public void put(String currency, double value) {
        cache.put(currency, new CacheEntry(value, ttl));
        log.info(currency + " added to cache");
    }

    private class CacheCleanupTask implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(ttl * 1000L / 2);
                } 
                catch (InterruptedException e) {
                    log.info("Cleanup thread interrupted");
                    Thread.currentThread().interrupt();
                }

                for (Map.Entry<String, CacheEntry> entry : cache.entrySet()) {
                    if (entry.getValue().isExpired()) {
                        cache.remove(entry.getKey());
                    }
                }
            }
        }

    }
}
