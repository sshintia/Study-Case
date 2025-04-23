package com.example.case_study_shintia.controller;

import com.example.case_study_shintia.dto.StockDTO;
import com.example.case_study_shintia.model.Stock;
import com.example.case_study_shintia.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.io.IOException;

@RestController
@RequestMapping("/api/stocks")
public class StockController {
    private static final Logger logger = LogManager.getLogger(StockController.class);


    @Autowired
    private StockService stockService;

    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestParam("file") MultipartFile file, @RequestParam("stock") String stockJson) {
        try {
            StockDTO stockDTO = new ObjectMapper().readValue(stockJson, StockDTO.class);
            Stock stock = stockService.createStock(stockDTO, file);

            //Log request & response
            logger.info("Request to create stock: {}", stockDTO);
            logger.info("Response from create stock: {}", stock);


            return ResponseEntity.ok(stock);
        } catch (IOException e) {
            logger.error("Failed to parse stock JSON", e);
            return ResponseEntity.badRequest().build();
        }
        
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Long id) {
        Stock stock = stockService.getStockById(id);

        //Log request & response
        logger.info("Request to get stock by id: {}", id);
        logger.info("Response: {}", stock);

        return ResponseEntity.ok(stock);
    }

    @GetMapping
    public ResponseEntity<List<Stock>> listStock() {
        List<Stock> stocks = stockService.listStock();
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> detailStock(@PathVariable Long id) {
        return stockService.detailStock(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable Long id, @RequestBody StockDTO stockDTO) {
        Stock updatedStock = stockService.updateStock(id, stockDTO);
        return ResponseEntity.ok(updatedStock);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }



}
