package com.verint.verint.model;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record Item (
    int itemId,
    
    String imageUrl,

    @NotEmpty(message = "Name cannot be empty")
    String name,
    
    String shortDescription,

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")  
    Double price,

    @DecimalMin(value = "0.0", message = "Discount percentage must be greater than or equal to 0")
    @DecimalMax(value = "100.0", message = "Discount percentage must be less than or equal to 100")
    Double discountPercentage
) {}
