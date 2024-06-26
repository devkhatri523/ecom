package org.asymptotes.product.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.asymptotes.product.dto.ProductPurchaseRequest;
import org.asymptotes.product.dto.ProductPurchaseResponse;
import org.asymptotes.product.dto.ProductRequest;
import org.asymptotes.product.dto.ProductResponse;
import org.asymptotes.product.exception.ProductPurchaseException;
import org.asymptotes.product.mapper.ProductMapper;
import org.asymptotes.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public Integer createProduct(@Valid ProductRequest request) {
        var product = mapper.toProduct(request);
        return repository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
        var productIds = request.stream().map(ProductPurchaseRequest::productId).toList();
        var storedProducts = repository.findAllByIdInOrderById(productIds);
        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more product does not exsits ");
        }
        var storesRequest = request.stream().sorted(Comparator.comparing(ProductPurchaseRequest::productId)).toList();
        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();
        for (int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = storesRequest.get(i);
            if (product.getAvailableQuantity() < productRequest.quantity()) {
                throw new ProductPurchaseException("Insufficient stock quantity for product with ID::" + product.getId());
            }
            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            repository.save(product);
            purchasedProducts.add(mapper.toProductPurchaseResponse(product, productRequest.quantity()));
        }
        return purchasedProducts;


    }

    public ProductResponse findById(Integer productId) {
        return repository.findById(productId).map(mapper::tpProductResponse).orElseThrow(
                () -> new EntityNotFoundException("Product not found with ID::" + productId));
    }

    public List<ProductResponse> findAll() {
        return repository.findAll().stream().map(mapper::tpProductResponse).collect(Collectors.toList());
    }
}