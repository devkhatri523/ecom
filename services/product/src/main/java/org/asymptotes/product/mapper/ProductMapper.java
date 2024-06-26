package org.asymptotes.product.mapper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.asymptotes.product.dto.ProductPurchaseResponse;
import org.asymptotes.product.dto.ProductRequest;
import org.asymptotes.product.dto.ProductResponse;
import org.asymptotes.product.product.Category;
import org.asymptotes.product.product.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public Product toProduct(@Valid ProductRequest request) {
        return Product.builder()
                .id(request.id())
                .name(request.name())
                .price(request.price())
                .availableQuantity(request.availableQuantity())
                .category(Category.builder()
                        .id(request.categoryId()).build())
                .build();
    }

    public ProductResponse tpProductResponse(Product product) {
       return  new ProductResponse(product.getId(),product.getName(),product.getDescription(),product.getAvailableQuantity()
       ,product.getPrice(),product.getCategory().getId(),product.getCategory().getName(),product.getCategory().getDescription());
    }

    public ProductPurchaseResponse toProductPurchaseResponse(Product product, double quantity) {

        return new ProductPurchaseResponse(product.getId(),product.getName(),product.getDescription(),product.getPrice(),quantity);
    }
}
