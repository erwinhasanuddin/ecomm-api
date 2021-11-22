package com.erwin.ecomm_api.service;

import com.erwin.ecomm_api.domain.ProductInventory;
import com.erwin.ecomm_api.model.ProductInventoryDTO;
import com.erwin.ecomm_api.repos.ProductInventoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class ProductInventoryService {

    private final ProductInventoryRepository productInventoryRepository;

    public ProductInventoryService(final ProductInventoryRepository productInventoryRepository) {
        this.productInventoryRepository = productInventoryRepository;
    }

    public List<ProductInventoryDTO> findAll() {
        return productInventoryRepository.findAll()
                .stream()
                .map(productInventory -> mapToDTO(productInventory, new ProductInventoryDTO()))
                .collect(Collectors.toList());
    }

    public ProductInventoryDTO get(final Integer id) {
        return productInventoryRepository.findById(id)
                .map(productInventory -> mapToDTO(productInventory, new ProductInventoryDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final ProductInventoryDTO productInventoryDTO) {
        final ProductInventory productInventory = new ProductInventory();
        mapToEntity(productInventoryDTO, productInventory);
        return productInventoryRepository.save(productInventory).getId();
    }

    public void update(final Integer id, final ProductInventoryDTO productInventoryDTO) {
        final ProductInventory productInventory = productInventoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(productInventoryDTO, productInventory);
        productInventoryRepository.save(productInventory);
    }

    public void delete(final Integer id) {
        productInventoryRepository.deleteById(id);
    }

    private ProductInventoryDTO mapToDTO(final ProductInventory productInventory,
            final ProductInventoryDTO productInventoryDTO) {
        productInventoryDTO.setId(productInventory.getId());
        productInventoryDTO.setQuantity(productInventory.getQuantity());
        productInventoryDTO.setProductId(productInventory.getProductId());
        return productInventoryDTO;
    }

    private ProductInventory mapToEntity(final ProductInventoryDTO productInventoryDTO,
            final ProductInventory productInventory) {
        productInventory.setQuantity(productInventoryDTO.getQuantity());
        productInventory.setProductId(productInventoryDTO.getProductId());
        return productInventory;
    }

}
