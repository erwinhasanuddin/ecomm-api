package com.erwin.ecomm_api.service;

import com.erwin.ecomm_api.domain.Merchants;
import com.erwin.ecomm_api.domain.ProductCategory;
import com.erwin.ecomm_api.domain.ProductInventory;
import com.erwin.ecomm_api.domain.Products;
import com.erwin.ecomm_api.model.ProductsDTO;
import com.erwin.ecomm_api.repos.MerchantsRepository;
import com.erwin.ecomm_api.repos.ProductCategoryRepository;
import com.erwin.ecomm_api.repos.ProductInventoryRepository;
import com.erwin.ecomm_api.repos.ProductsRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class ProductsService {

    private final ProductsRepository productsRepository;
    private final MerchantsRepository merchantsRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductInventoryRepository productInventoryRepository;

    public ProductsService(final ProductsRepository productsRepository,
            final MerchantsRepository merchantsRepository,
            final ProductCategoryRepository productCategoryRepository,
            final ProductInventoryRepository productInventoryRepository) {
        this.productsRepository = productsRepository;
        this.merchantsRepository = merchantsRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.productInventoryRepository = productInventoryRepository;
    }

    public List<ProductsDTO> findAll() {
        return productsRepository.findAll()
                .stream()
                .map(products -> mapToDTO(products, new ProductsDTO()))
                .collect(Collectors.toList());
    }

    public ProductsDTO get(final Integer id) {
        return productsRepository.findById(id)
                .map(products -> mapToDTO(products, new ProductsDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final ProductsDTO productsDTO) {
        final Products products = new Products();
        mapToEntity(productsDTO, products);
        return productsRepository.save(products).getId();
    }

    public void update(final Integer id, final ProductsDTO productsDTO) {
        final Products products = productsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(productsDTO, products);
        productsRepository.save(products);
    }

    public void delete(final Integer id) {
        productsRepository.deleteById(id);
    }

    private ProductsDTO mapToDTO(final Products products, final ProductsDTO productsDTO) {
        productsDTO.setId(products.getId());
        productsDTO.setName(products.getName());
        //productsDTO.setMerchantId(products.getMerchantId());
        //productsDTO.setProductCategoryId(products.getProductCategoryId());
        //productsDTO.setProductInventoryId(products.getProductInventoryId());
        productsDTO.setPrice(products.getPrice());
        productsDTO.setStatus(products.getStatus());
        productsDTO.setTrackInventory(products.getTrackInventory());
        productsDTO.setAllowBackorders(products.getAllowBackorders());
        productsDTO.setMerchantId(products.getMerchants() == null ? null : products.getMerchants().getId());
        productsDTO.setProductCategoryId(products.getProductCategory() == null ? null : products.getProductCategory().getId());
        productsDTO.setProductInventoryId(products.getProductInventory() == null ? null : products.getProductInventory().getId());
        return productsDTO;
    }

    private Products mapToEntity(final ProductsDTO productsDTO, final Products products) {
        products.setName(productsDTO.getName());
        //products.setMerchantId(productsDTO.getMerchantId());
        //products.setProductCategoryId(productsDTO.getProductCategoryId());
        //products.setProductInventoryId(productsDTO.getProductInventoryId());
        products.setPrice(productsDTO.getPrice());
        products.setStatus(productsDTO.getStatus());
        products.setTrackInventory(productsDTO.getTrackInventory());
        products.setAllowBackorders(productsDTO.getAllowBackorders());
        if (productsDTO.getMerchantId() != null && (products.getMerchants() == null || !products.getMerchants().getId().equals(productsDTO.getMerchantId()))) {
            final Merchants merchants = merchantsRepository.findById(productsDTO.getMerchantId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "merchants not found"));
            products.setMerchants(merchants);
        }
        if (productsDTO.getProductCategoryId() != null && (products.getProductCategory() == null || !products.getProductCategory().getId().equals(productsDTO.getProductCategoryId()))) {
            final ProductCategory productCategory = productCategoryRepository.findById(productsDTO.getProductCategoryId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "productCategory not found"));
            products.setProductCategory(productCategory);
        }
        if (productsDTO.getProductInventoryId() != null && (products.getProductInventory() == null || !products.getProductInventory().getId().equals(productsDTO.getProductInventoryId()))) {
            final ProductInventory productInventory = productInventoryRepository.findById(productsDTO.getProductInventoryId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "productInventory not found"));
            products.setProductInventory(productInventory);
        }
        return products;
    }

}
