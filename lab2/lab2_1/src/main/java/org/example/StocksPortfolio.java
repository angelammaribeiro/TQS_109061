package org.example;

import java.util.ArrayList;
import java.util.List;

public class StocksPortfolio implements  IStockMarketService{

    IStockMarketService stockmarket;

    List<Stock> stocks;

    public StocksPortfolio(IStockMarketService stockmarket) {
        this.stockmarket = stockmarket;
        this.stocks = new ArrayList<>();  // Initialize empty stock list
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }
    public double totalValue(){
        double total = 0;
        for (Stock stock : stocks) {
            total = total + (stock.getQuantity());
        }

        return total;
    }

    @Override
    public double lookUpPrice(String stock) {
        return 0;
    }

}
