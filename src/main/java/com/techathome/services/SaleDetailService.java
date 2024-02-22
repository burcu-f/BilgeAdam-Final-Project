package com.techathome.services;

import com.techathome.entities.SaleDetail;
import com.techathome.repository.SaleDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleDetailService {
    @Autowired
    private SaleDetailRepository saleDetailRepository;

    public List<SaleDetail> getAllSaleDetails() {
        return saleDetailRepository.findAll();
    }

    public SaleDetail getSaleDetailById(Integer saleDetailId) {
        return saleDetailRepository.findById(saleDetailId).orElse(null);
    }

    public void saveSaleDetail(SaleDetail saleDetail) {
        saleDetailRepository.save(saleDetail);
    }
}
