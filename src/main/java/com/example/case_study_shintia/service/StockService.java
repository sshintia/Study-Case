package com.example.case_study_shintia.service;

import com.example.case_study_shintia.dto.StockDTO;
import com.example.case_study_shintia.model.Stock;
import com.example.case_study_shintia.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {
    private static final String IMAGE_DIRECTORY = "path/to/image/directory";

    @Autowired
    private StockRepository stockRepository;

    private void validateImageFile(MultipartFile file) {
        String mimeType = file.getContentType();
        if (!"image/jpeg".equals(mimeType) && !"image/png".equals(mimeType)) {
            throw new IllegalArgumentException("JPG and PNG only!");
        }
    }

    private String saveImage(MultipartFile file) throws IOException {
        Path imagePath = Paths.get(IMAGE_DIRECTORY, file.getOriginalFilename());
        Files.copy(file.getInputStream(), imagePath);
        return imagePath.toString();
    }

    public List<Stock> listStock() {
        return stockRepository.findAll();
    }

    public Stock getStockById(Long id) {
        return stockRepository.findById(id).orElseThrow(() -> new RuntimeException("Stock not found"));
    }

    public Stock createStock(StockDTO stockDTO, MultipartFile file) throws IOException {
        validateImageFile(file);

        String imagePath = saveImage(file);

        Stock stock = new Stock();
        // Set properties from DTO to entity
        stock.setNamaBarang(stockDTO.getNamaBarang());
        stock.setJumlahStokBarang(stockDTO.getJumlahStokBarang());
        stock.setNomorSeriBarang(stockDTO.getNomorSeriBarang());
        stock.setAdditionalInfo(stockDTO.getAdditionalInfo());
        stock.setGambarBarang(imagePath);
        stock.setCreatedAt(LocalDateTime.now());
        // Set createdBy, updatedAt, updatedBy as needed
        return stockRepository.save(stock);
    }

    public Stock updateStock(Long id, StockDTO stockDTO) {
        Stock stock = getStockById(id);
      
        stock.setNamaBarang(stockDTO.getNamaBarang());
        stock.setJumlahStokBarang(stockDTO.getJumlahStokBarang());
        stock.setNomorSeriBarang(stockDTO.getNomorSeriBarang());
        stock.setAdditionalInfo(stockDTO.getAdditionalInfo());
        stock.setGambarBarang(stockDTO.getGambarBarang());
        // Set updatedBy as needed
        return stockRepository.save(stock);
    }

    public Optional<Stock> detailStock(Long id) {
        return stockRepository.findById(id);
    }

    public void deleteStock(Long id) {
        stockRepository.deleteById(id);
    }

}
