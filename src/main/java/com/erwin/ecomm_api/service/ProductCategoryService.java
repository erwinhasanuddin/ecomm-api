package com.erwin.ecomm_api.service;

import com.erwin.ecomm_api.domain.ProductCategory;
import com.erwin.ecomm_api.model.ProductCategoryDTO;
import com.erwin.ecomm_api.repos.ProductCategoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryService(final ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public List<ProductCategoryDTO> findAll() {
        return productCategoryRepository.findAll()
                .stream()
                .map(productCategory -> mapToDTO(productCategory, new ProductCategoryDTO()))
                .collect(Collectors.toList());
    }

    public ProductCategoryDTO get(final Integer id) {
        return productCategoryRepository.findById(id)
                .map(productCategory -> mapToDTO(productCategory, new ProductCategoryDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final ProductCategoryDTO productCategoryDTO) {
        final ProductCategory productCategory = new ProductCategory();
        mapToEntity(productCategoryDTO, productCategory);
        return productCategoryRepository.save(productCategory).getId();
    }

    public void update(final Integer id, final ProductCategoryDTO productCategoryDTO) {
        final ProductCategory productCategory = productCategoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(productCategoryDTO, productCategory);
        productCategoryRepository.save(productCategory);
    }

    public void delete(final Integer id) {
        productCategoryRepository.deleteById(id);
    }

    private ProductCategoryDTO mapToDTO(final ProductCategory productCategory,
            final ProductCategoryDTO productCategoryDTO) {
        productCategoryDTO.setId(productCategory.getId());
        productCategoryDTO.setName(productCategory.getName());
        productCategoryDTO.setActiveStatus(productCategory.getActiveStatus());
        return productCategoryDTO;
    }

    private ProductCategory mapToEntity(final ProductCategoryDTO productCategoryDTO,
            final ProductCategory productCategory) {
        productCategory.setName(productCategoryDTO.getName());
        productCategory.setActiveStatus(productCategoryDTO.getActiveStatus());
        return productCategory;
    }

}
