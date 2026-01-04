package com.example.trade.tradingapp.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class Trade {

    // ===== User-provided fields =====

    @NotBlank
    private String userId;

    @NotBlank
    private String symbol;

    @NotBlank
    private String type; // BUY or SELL

    @Min(1)
    private int quantity;

    // ===== Enriched fields from Alpha Vantage =====

    /**
     * Latest traded price (05. price)
     */
    private BigDecimal price;

    /**
     * Trading volume (06. volume)
     */
    private Long volume;

    /**
     * Latest trading day (07. latest trading day)
     */
    private LocalDate latestTradingDay;

    // ===== Getters & Setters =====

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public LocalDate getLatestTradingDay() {
        return latestTradingDay;
    }

    public void setLatestTradingDay(LocalDate latestTradingDay) {
        this.latestTradingDay = latestTradingDay;
    }
}
