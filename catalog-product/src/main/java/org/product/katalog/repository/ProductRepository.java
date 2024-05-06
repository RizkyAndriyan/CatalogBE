package org.product.katalog.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.product.katalog.entity.Product;


@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {

}
