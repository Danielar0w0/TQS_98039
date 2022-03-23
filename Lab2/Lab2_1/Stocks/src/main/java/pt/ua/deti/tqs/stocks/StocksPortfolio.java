package pt.ua.deti.tqs.stocks;

import java.util.ArrayList;
import java.util.List;

public class StocksPortfolio {

    private List<Stock> stocks;
    private IStockmarketService stockmarket;

    public StocksPortfolio(IStockmarketService stockmarket) {
        this.stocks = new ArrayList<>();
        this.stockmarket = stockmarket;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public double getTotalValue() {

        double price; double total = 0;
        for (Stock s: stocks) {
            price = stockmarket.lookUpPrice(s.getLabel());
            total += (price * s.getQuantity());
        }
        return total;
    }
}
