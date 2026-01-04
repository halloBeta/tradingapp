package com.example.trade.tradingapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.trade.tradingapp.config.JmsConfig;
import com.example.trade.tradingapp.model.Trade;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/trades")
public class TradeController {
    private static final Logger log = LoggerFactory.getLogger(TradeController.class);

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TradeController(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @PostMapping
    public String placeTrade(@Valid @RequestBody Trade trade) {
        log.info("Received trade: {}", trade);
        try {
            // Convert Trade to JSON string for JMS
            String tradeJson = objectMapper.writeValueAsString(trade);
        
            // Send trade to JMS queue
            jmsTemplate.convertAndSend(JmsConfig.TRADE_QUEUE, tradeJson);
        } catch (JsonProcessingException e) {
            log.error("Error sending trade to JMS queue: {}", e.getMessage());
            return "Failed to send trade to processing queue.";
        }
        return "Trade sent to processing queue!";
    }
}
