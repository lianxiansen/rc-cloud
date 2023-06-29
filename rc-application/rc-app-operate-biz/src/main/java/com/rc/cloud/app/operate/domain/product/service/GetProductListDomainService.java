package com.rc.cloud.app.operate.domain.product.service;


import com.rc.cloud.app.operate.domain.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetProductListDomainService {
    @Autowired
    private ProductRepository productRepository;



}
