package org.product.katalog.resource;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.product.katalog.dto.ProductDto;
import org.product.katalog.dto.SpesifikasiDto;
import org.product.katalog.entity.Product;
import org.product.katalog.entity.Spesifikasi;
import org.product.katalog.repository.ProductRepository;
import org.product.katalog.repository.SpesifikasiRepository;
import org.product.katalog.service.ApiResponse;
import org.product.katalog.service.ProductService;

import java.util.*;

@Path("katalog")
@Slf4j
public class KatalogResource {

    @Inject
    EntityManager entityManager;

    @Inject
    ProductRepository productRepository;

    @Inject
    SpesifikasiRepository spesifikasiRepository;

    @Inject
    ProductService productService;


    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse<Map<Long, Map<String, Object>>> getAllData(){
            List<Product> productList = entityManager.createQuery("SELECT p FROM Product p", Product.class).getResultList();
            Map<Long, Map<String, Object>> dataMap = new LinkedHashMap<>();

            for (Product product : productList) {
                Map<String, Object> productData = new LinkedHashMap<>();
                productData.put("id", product.getId());
                productData.put("name", product.getName());
                productData.put("photo", product.getPhoto());
                productData.put("minHarga", product.getMinHarga());
                productData.put("maxHarga", product.getMaxHarga());
                productData.put("kategori", product.getKategori());
                productData.put("brand", product.getBrand());
                productData.put("link", product.getLink());

                for (Spesifikasi spesifikasi : product.getSpesifikasi()) {
                    Map<String, Object> spesifikasiData = new LinkedHashMap<>();
                    spesifikasiData.put("namaSpesifikasi", spesifikasi.getNamaSpesifikasi());
                    spesifikasiData.put("dataSpesifikasi", spesifikasi.getDataSpesifikasi());

                    productData.put("spesifikasi" + spesifikasi.getId(), spesifikasiData);
                }

                dataMap.put(product.getId(), productData);
            }
            return new ApiResponse<>("SUCESS", dataMap,"Data berhasil diambil");
    }



    @Path("{kategori}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<Long, Map<String, Object>> getDataById(@PathParam("kategori") String kategori){
        List<Product> productList = entityManager.createQuery("SELECT p FROM Product p WHERE p.Kategori = :kategori")
                .setParameter("kategori", kategori)
                .getResultList();
        Map<Long, Map<String, Object>> dataMap = new LinkedHashMap<>();

        for (Product product : productList){
            Map<String, Object> productData = new LinkedHashMap<>();
            productData.put("name", product.getName());
            productData.put("photo", product.getPhoto());
            productData.put("minHarga", product.getMinHarga());
            productData.put("maxHarga", product.getMaxHarga());
            productData.put("kategori", product.getKategori());
            productData.put("brand", product.getBrand());
            productData.put("link",product.getLink());


            for (Spesifikasi spesifikasi : product.getSpesifikasi()){
                Map<String, Object> spesifikasiData = new LinkedHashMap<>();
                spesifikasiData.put("namaSpesifikasi", spesifikasi.getNamaSpesifikasi());
                spesifikasiData.put("dataSpesifikasi", spesifikasi.getDataSpesifikasi());

                productData.put("spesifikasi" + spesifikasi.getId(), spesifikasiData);
            }
            dataMap.put(product.getId(), productData);
        }

        return dataMap;
    }



    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse<Product> addData(ProductDto dto){
        Product product = new Product();
        product.setName(dto.getName());
        product.setPhoto(dto.getPhoto());
        product.setMinHarga(dto.getMinHarga());
        product.setMaxHarga(dto.getMaxHarga());
        product.setKategori(dto.getKategori());
        product.setBrand(dto.getBrand());
        product.setLink(dto.getLink());

        List<Spesifikasi> spesifikasiList = new ArrayList<>();
        for (SpesifikasiDto spesifikasiDto : dto.getSpesifikasi()){
            Spesifikasi spesifikasi = new Spesifikasi();
            spesifikasi.setProduct(product);
            spesifikasi.setNamaSpesifikasi(spesifikasiDto.getNamaSpesifikasi());
            spesifikasi.setDataSpesifikasi(spesifikasiDto.getDataSpesifikasi());
            spesifikasiList.add(spesifikasi);
        }

        productRepository.persist(product);
        spesifikasiRepository.persist(spesifikasiList);
        return new ApiResponse<>("SUCESS",product,"Data telah ditambahkan");
    }



    @PUT
    @Path("/spesifikasi/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public ApiResponse updateData(@PathParam("id")Long id, ProductDto dto){
        Optional<Product> productOptional = productRepository.findByIdOptional(id);
        if (productOptional.isPresent()){
            Product product = productOptional.get();
            product.setName(dto.getName());
            product.setPhoto(dto.getPhoto());
            product.setMinHarga(dto.getMinHarga());
            product.setMaxHarga(dto.getMaxHarga());
            product.setKategori(dto.getKategori());
            product.setBrand(dto.getBrand());
            product.setLink(dto.getLink());

            List<Spesifikasi> spesifikasiList = new ArrayList<>();
            for (SpesifikasiDto spesifikasiDto : dto.getSpesifikasi()){
                Spesifikasi spesifikasi = new Spesifikasi();
                spesifikasi.setProduct(product);
                spesifikasi.setNamaSpesifikasi(spesifikasiDto.getNamaSpesifikasi());
                spesifikasi.setDataSpesifikasi(spesifikasiDto.getDataSpesifikasi());
                spesifikasiList.add(spesifikasi);
            }

            productRepository.persist(product);

            spesifikasiRepository.delete("product = ?1",productService.castingId(id));
            spesifikasiRepository.persist(spesifikasiList);

            return new ApiResponse("SUCESS","Data Berhasil Dirubah");
        }else {
            return new ApiResponse("FAILED","Data Gagal Dirubah");
        }
    }



    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public ApiResponse deleteData(@PathParam("id")Long id){
        Optional<Product> productOptional = productRepository.findByIdOptional(id);
        if (productOptional.isPresent()){
            productRepository.delete(productOptional.get());
            return new ApiResponse<>("SUCCESS","Data Telah Dihapus");
        }else{
            return new ApiResponse<>("FAILED","Data Gagal Dihapus");
        }
    }
}
