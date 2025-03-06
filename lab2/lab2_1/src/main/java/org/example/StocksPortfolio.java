package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class StocksPortfolio{

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
            total = total + (stock.getQuantity() * stockmarket.lookUpPrice(stock.getLabel()));
        }

        return total;
    }

    public List<Stock> mostValuableStocks(int topN) {
        // Calculate the value of each stock
        Map<Stock, Double> stockValues = new HashMap<>();
        for (Stock stock : stocks) {
            double price = stockmarket.lookUpPrice(stock.getLabel());
            double value = stock.getQuantity() * price;
            stockValues.put(stock, value);
        }

        // Sort stocks by their value in descending order
        List<Stock> sortedStocks = stockValues.entrySet().stream()
                .sorted(Map.Entry.<Stock, Double>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .toList();

        // Return the topN stocks
        return sortedStocks.stream()
                .limit(topN)
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StocksPortfolio that = (StocksPortfolio) o;
        return Objects.equals(stockmarket, that.stockmarket) && Objects.equals(stocks, that.stocks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockmarket, stocks);
    }
}
