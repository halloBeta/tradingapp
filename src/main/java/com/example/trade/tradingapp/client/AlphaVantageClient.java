package com.example.trade.tradingapp.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.trade.tradingapp.dto.AlphaVantageQuoteResponse;

@Component
public class AlphaVantageClient {
    private static final Logger log = LoggerFactory.getLogger(AlphaVantageClient.class);
    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final String apiKey;

    public AlphaVantageClient(RestTemplate restTemplate,
                              @Value("${alphavantage.base-url}") String baseUrl,
                              @Value("${alphavantage.api-key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }

    public AlphaVantageQuoteResponse getQuote(String symbol) {
        log.info("Fetching quote for symbol: {}", symbol);
        String url = UriComponentsBuilder
                .fromUriString(baseUrl)
                .queryParam("function", "GLOBAL_QUOTE")
                .queryParam("symbol", symbol)
                .queryParam("apikey", apiKey)
                .toUriString();

        return restTemplate.getForObject(url, AlphaVantageQuoteResponse.class);
    }
}
