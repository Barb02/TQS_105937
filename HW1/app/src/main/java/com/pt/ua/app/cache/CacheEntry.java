package com.pt.ua.app.cache;

public class CacheEntry {
    private double value;
    private long expirationTime;

    public CacheEntry(double value, long ttl) {
        this.value = value;
        this.expirationTime = System.currentTimeMillis() + ttl * 1000L;
    }

    public double getValue() {
        return value;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expirationTime;
    }
}
