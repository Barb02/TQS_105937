package com.pt.ua.app.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

import com.pt.ua.app.cache.ExchangeRateCache;

@Service
public class ExchangeService {
    
    private final ExchangeRateCache cache;
    private static final String URL = "https://api.freecurrencyapi.com/v1/latest?base_currency=EUR&apikey=";
    private static final String API_KEY = "fca_live_3JvrZIqML6p7ai9asXzfkXZlZKQ6f918mtVMgmfd";

    private static final Logger log = LoggerFactory.getLogger(ExchangeService.class);

    public ExchangeService(){
        this.cache = new ExchangeRateCache(60);
    }

    public double getExchangeRate(String currency) throws IOException, InterruptedException{
        double exchangeRate = cache.get(currency);
        if(exchangeRate != -1.0){
            log.info("Cache hit for currency " + currency);
            return exchangeRate;
        }
        else{
            log.info("Cache miss for currency " + currency);

            double newExchangeRate = fetchExchangeRate(currency);
            cache.put(currency, newExchangeRate);

            return newExchangeRate;
        }
    }

    private double fetchExchangeRate(String currency) throws IOException, InterruptedException{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL + API_KEY + "&currencies=" + currency))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject jsonResponse = new JSONObject(response.body());
        double exchangeRate = jsonResponse.getJSONObject("data").getDouble(currency);

        log.info(currency + " exchange rate fetched: " + exchangeRate);

        return exchangeRate;
    }
}
