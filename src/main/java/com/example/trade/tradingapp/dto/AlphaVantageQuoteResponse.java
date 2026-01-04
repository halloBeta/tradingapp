package com.example.trade.tradingapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AlphaVantageQuoteResponse {

    @JsonProperty("Global Quote")
    private GlobalQuote globalQuote;

    public GlobalQuote getGlobalQuote() {
        return globalQuote;
    }

    public static class GlobalQuote {

        @JsonProperty("01. symbol")
        private String symbol;

        @JsonProperty("05. price")
        private BigDecimal price;

        @JsonProperty("06. volume")
        private Long volume;

        @JsonProperty("07. latest trading day")
        private LocalDate latestTradingDay;

        public String getSymbol() {
            return symbol;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public Long getVolume() {
            return volume;
        }

        public LocalDate getLatestTradingDay() {
            return latestTradingDay;
        }
    }
}
