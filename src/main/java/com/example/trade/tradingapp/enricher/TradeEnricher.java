package com.example.trade.tradingapp.enricher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.example.trade.tradingapp.client.AlphaVantageClient;
import com.example.trade.tradingapp.config.JmsConfig;
import com.example.trade.tradingapp.dto.AlphaVantageQuoteResponse;
import com.example.trade.tradingapp.model.Trade;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class TradeEnricher {
    private static final Logger log = LoggerFactory.getLogger(TradeEnricher.class);

    private final AlphaVantageClient alphaVantageClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TradeEnricher(AlphaVantageClient alphaVantageClient) {
        this.alphaVantageClient = alphaVantageClient;   
    }

    @JmsListener(destination = JmsConfig.TRADE_QUEUE)
    public void enrichTrade(String tradeJson) {
        try {
            // Deserialize JSON to Trade object
            Trade trade = objectMapper.readValue(tradeJson, Trade.class);
            AlphaVantageQuoteResponse response =
                    alphaVantageClient.getQuote(trade.getSymbol());

            if (response == null || response.getGlobalQuote() == null || response.getGlobalQuote().getPrice() == null) {
                log.error("No quote data received for symbol: " + trade.getSymbol());
                throw new IllegalStateException("No quote data received");
            }

            var quote = response.getGlobalQuote();
            log.info("Enriching trade for symbol: {}, {}", trade.getSymbol(), quote);

            // Enrich trade
            trade.setPrice(quote.getPrice());
            trade.setVolume(quote.getVolume());
            trade.setLatestTradingDay(quote.getLatestTradingDay());

            log.info(
                    "Enriched trade: user=" + trade.getUserId() +
                    ", symbol=" + trade.getSymbol() +
                    ", price=" + trade.getPrice() +
                    ", volume=" + trade.getVolume() +
                    ", tradingDay=" + trade.getLatestTradingDay()
            );

            // TODO: persist or forward enriched trade

        } catch (Exception ex) {
            log.error("Failed to enrich trade for : " + ex.getMessage());
        }
    }
}
