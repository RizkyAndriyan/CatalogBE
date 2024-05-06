package org.product.katalog.dto;


import lombok.Getter;
import lombok.Setter;
import org.product.katalog.entity.Product;

@Getter @Setter
public class SpesifikasiDto {
    private String namaSpesifikasi;
    private String dataSpesifikasi;
    private Product product;
}
