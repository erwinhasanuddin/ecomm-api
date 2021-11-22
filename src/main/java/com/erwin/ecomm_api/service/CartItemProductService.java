package com.erwin.ecomm_api.service;

import com.erwin.ecomm_api.domain.CartItem;
import com.erwin.ecomm_api.domain.CartItemProduct;
import com.erwin.ecomm_api.domain.Products;
import com.erwin.ecomm_api.model.CartItemProductDTO;
import com.erwin.ecomm_api.repos.CartItemProductRepository;
import com.erwin.ecomm_api.repos.CartItemRepository;
import com.erwin.ecomm_api.repos.ProductsRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class CartItemProductService {

    private final CartItemProductRepository cartItemProductRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductsRepository productsRepository;

    public CartItemProductService(final CartItemProductRepository cartItemProductRepository,
            final CartItemRepository cartItemRepository,
            final ProductsRepository productsRepository) {
        this.cartItemProductRepository = cartItemProductRepository;
        this.cartItemRepository = cartItemRepository;
        this.productsRepository = productsRepository;
    }

    public List<CartItemProductDTO> findAll() {
        return cartItemProductRepository.findAll()
                .stream()
                .map(cartItemProduct -> mapToDTO(cartItemProduct, new CartItemProductDTO()))
                .collect(Collectors.toList());
    }

    public CartItemProductDTO get(final Long id) {
        return cartItemProductRepository.findById(id)
                .map(cartItemProduct -> mapToDTO(cartItemProduct, new CartItemProductDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final CartItemProductDTO cartItemProductDTO) {
        final CartItemProduct cartItemProduct = new CartItemProduct();
        mapToEntity(cartItemProductDTO, cartItemProduct);
        return cartItemProductRepository.save(cartItemProduct).getId();
    }

    public void update(final Long id, final CartItemProductDTO cartItemProductDTO) {
        final CartItemProduct cartItemProduct = cartItemProductRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(cartItemProductDTO, cartItemProduct);
        cartItemProductRepository.save(cartItemProduct);
    }

    public void delete(final Long id) {
        cartItemProductRepository.deleteById(id);
    }

    private CartItemProductDTO mapToDTO(final CartItemProduct cartItemProduct,
            final CartItemProductDTO cartItemProductDTO) {
        cartItemProductDTO.setId(cartItemProduct.getId());
        cartItemProductDTO.setCartItemId(cartItemProduct.getCartItemId());
        cartItemProductDTO.setProductId(cartItemProduct.getProductId());
        cartItemProductDTO.setOneCartItemManyCartItemProduct(cartItemProduct.getOneCartItemManyCartItemProduct() == null ? null : cartItemProduct.getOneCartItemManyCartItemProduct().getId());
        cartItemProductDTO.setOneProductManyCartItemProduct(cartItemProduct.getOneProductManyCartItemProduct() == null ? null : cartItemProduct.getOneProductManyCartItemProduct().getId());
        return cartItemProductDTO;
    }

    private CartItemProduct mapToEntity(final CartItemProductDTO cartItemProductDTO,
            final CartItemProduct cartItemProduct) {
        cartItemProduct.setCartItemId(cartItemProductDTO.getCartItemId());
        cartItemProduct.setProductId(cartItemProductDTO.getProductId());
        if (cartItemProductDTO.getOneCartItemManyCartItemProduct() != null && (cartItemProduct.getOneCartItemManyCartItemProduct() == null || !cartItemProduct.getOneCartItemManyCartItemProduct().getId().equals(cartItemProductDTO.getOneCartItemManyCartItemProduct()))) {
            final CartItem oneCartItemManyCartItemProduct = cartItemRepository.findById(cartItemProductDTO.getOneCartItemManyCartItemProduct())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "oneCartItemManyCartItemProduct not found"));
            cartItemProduct.setOneCartItemManyCartItemProduct(oneCartItemManyCartItemProduct);
        }
        if (cartItemProductDTO.getOneProductManyCartItemProduct() != null && (cartItemProduct.getOneProductManyCartItemProduct() == null || !cartItemProduct.getOneProductManyCartItemProduct().getId().equals(cartItemProductDTO.getOneProductManyCartItemProduct()))) {
            final Products oneProductManyCartItemProduct = productsRepository.findById(cartItemProductDTO.getOneProductManyCartItemProduct())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "oneProductManyCartItemProduct not found"));
            cartItemProduct.setOneProductManyCartItemProduct(oneProductManyCartItemProduct);
        }
        return cartItemProduct;
    }

}
