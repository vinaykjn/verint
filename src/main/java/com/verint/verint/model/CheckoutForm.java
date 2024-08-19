package com.verint.verint.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record CheckoutForm (
    String orderId,
    Long itemId,

    @NotBlank(message = "Full Name is required")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Full Name must contain only letters and spaces")
    String fullName,

    @NotBlank(message = "Address is required")
    String address,

    @Email(message = "Invalid email address")
    @NotBlank(message = "Email is required")
    String email,

    @Pattern(regexp = "\\d{3}-\\d{3}-\\d{4}", message = "Phone number must be in the format xxx-xxx-xxxx")
    String phoneNumber,

    @Pattern(regexp = "\\d{19}", message = "Credit card number must be 19 digits long")
    String creditCardNumber
) {}