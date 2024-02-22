package com.pt.ua;

import java.util.ArrayList;
import java.util.List;

public class StocksPortfolio {
    IStockMarketService stockmarket;
    List<Stock> stocks = new ArrayList<>();

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public double totalValue(){
        double total = 0;
        for (Stock stock : stocks) {
            total += stock.getQuantity() * stockmarket.lookUpPrice(stock.getLabel());
        }
        return total;
    }
}
