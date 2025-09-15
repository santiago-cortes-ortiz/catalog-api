package com.jeisson.catalog_api.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.jeisson.catalog_api.domain.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDocument {

    @Id
    private String id;

    @Indexed
    private String name;

    private String description;

    private BigDecimal price;

    @Indexed
    private String category;

    private Integer stock;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Indexed
    private Boolean active;


    public Product toDomain() {
        return Product.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .price(this.price)
                .category(this.category)
                .stock(this.stock)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .active(this.active)
                .build();
    }


    public static ProductDocument fromDomain(com.jeisson.catalog_api.domain.entity.Product product) {
        return ProductDocument.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())
                .stock(product.getStock())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .active(product.getActive())
                .build();
    }
}
