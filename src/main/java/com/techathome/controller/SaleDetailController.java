package com.techathome.controller;

import com.techathome.entities.SaleDetail;
import com.techathome.services.SaleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sale-details")
public class SaleDetailController {
    @Autowired
    private SaleDetailService saleDetailService;

    @GetMapping("")
    public ResponseEntity<List<SaleDetail>> getAllSaleDetails() {
        List<SaleDetail> saleDetails = saleDetailService.getAllSaleDetails();
        return new ResponseEntity<>(saleDetails, HttpStatus.OK);
    }

    @GetMapping("/{saleDetailId}")
    public ResponseEntity<SaleDetail> getSaleDetailById(@PathVariable Integer saleDetailId) {
        SaleDetail saleDetail = saleDetailService.getSaleDetailById(saleDetailId);
        return new ResponseEntity<>(saleDetail, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Void> createSaleDetail(@RequestBody SaleDetail saleDetail) {
        saleDetailService.saveSaleDetail(saleDetail);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
