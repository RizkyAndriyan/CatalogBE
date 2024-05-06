package org.product.katalog.dto;

import lombok.Getter;
import lombok.Setter;
import org.product.katalog.entity.Spesifikasi;

import java.util.List;


@Getter @Setter
public class ProductDto{
    private Long id;
    private String name;
    private String photo;
    private Long minHarga;
    private Long maxHarga;
    private String kategori;
    private String brand;
    private String link;
    private List<SpesifikasiDto> spesifikasi;
}
