package org.product.katalog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity @Getter @Setter
public class Product{

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Spesifikasi> spesifikasi;

    private String name;
    private String photo;
    private Long minHarga;
    private Long maxHarga;
    private String Kategori;
    private String brand;
    private String link;

}
