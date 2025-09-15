package com.jeisson.catalog_api.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private String id;

    private String name;

    private String description;

    private BigDecimal price;

    private String category;

    private Integer stock;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean active;

    /**
     * Business method: checks if the product is available
     */
    public boolean isAvailable() {
        return active != null && active && stock != null && stock > 0;
    }

    /**
     * Business method: reduces stock quantity
     */
    public void reduceStock(int quantity) {
        if (stock != null && stock >= quantity) {
            this.stock -= quantity;
            this.updatedAt = LocalDateTime.now();
        } else {
            throw new IllegalStateException("Insufficient stock");
        }
    }

    /**
     * Business method: increases stock quantity
     */
    public void increaseStock(int quantity) {
        if (stock != null && quantity > 0) {
            this.stock += quantity;
            this.updatedAt = LocalDateTime.now();
        }
    }
}
