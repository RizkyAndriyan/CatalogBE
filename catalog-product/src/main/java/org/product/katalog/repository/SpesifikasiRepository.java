package org.product.katalog.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.product.katalog.entity.Spesifikasi;

@ApplicationScoped
public class SpesifikasiRepository implements PanacheRepository<Spesifikasi> {
}
