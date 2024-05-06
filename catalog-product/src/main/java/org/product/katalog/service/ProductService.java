package org.product.katalog.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.product.katalog.dto.ProductDto;
import org.product.katalog.entity.Product;
import org.product.katalog.entity.Spesifikasi;
import org.product.katalog.repository.ProductRepository;
import org.product.katalog.repository.SpesifikasiRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ApplicationScoped @Slf4j
public class ProductService {

    public Product castingId (Long id){
        Product product = new Product();
        product.setId(id);
        return product;
    }
}

