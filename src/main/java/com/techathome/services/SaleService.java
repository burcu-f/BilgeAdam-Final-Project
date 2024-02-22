package com.techathome.services;

import com.techathome.entities.Sale;
import com.techathome.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService {
    @Autowired
    private SaleRepository saleRepository;

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Sale getSaleById(Integer saleId) {
        return saleRepository.findById(saleId).orElse(null);
    }

    public void saveSale(Sale sale) {
        saleRepository.save(sale);
    }
}
