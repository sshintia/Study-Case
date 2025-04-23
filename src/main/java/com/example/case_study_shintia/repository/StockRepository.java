package com.example.case_study_shintia.repository;

import com.example.case_study_shintia.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {
    @Query(value = "SELECT * FROM stock", nativeQuery = true)
    List<Stock> findAllStocks();

    @Query(value = "SELECT * FROM stock WHERE id = ?1", nativeQuery = true)
    Stock findStockById(Long id);
}
