package com.example.case_study_shintia.dto;

import java.util.Map;

import lombok.Data;

public class StockDTO {
    private Long id;
    private String namaBarang;
    private int jumlahStokBarang;
    private String nomorSeriBarang;
    private String additionalInfo;
    private Map<String, Object> additionalData;
    private String gambarBarang;

    // Getters
    public String getNamaBarang() { return namaBarang; }
    public int getJumlahStokBarang() { return jumlahStokBarang; }
    public String getNomorSeriBarang() { return nomorSeriBarang; }
    public String getAdditionalInfo() { return additionalInfo; }
    public String getGambarBarang() { return gambarBarang; }
    public Map<String, Object> getAdditionalData() { return additionalData; }


}
