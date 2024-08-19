package com.verint.verint.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.verint.verint.model.Item;
import com.verint.verint.model.CheckoutForm;
import com.verint.verint.service.ItemService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RequestMapping("/api/items")
@RestController
public class ItemController {
    
    private final ItemService itemService;
    private final Map<String, CheckoutForm> orders = new ConcurrentHashMap<>();

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Item> getItems(@RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "size", defaultValue = "10") int size) {
        return itemService.getItems(page, size);
    }

    @PostMapping("/checkout")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<String> checkout(@Valid @RequestBody CheckoutForm form) {
        String orderId = "ORD-" + System.currentTimeMillis();
        CheckoutForm order = CheckoutForm.builder()
                                .orderId(orderId)
                                .itemId(form.itemId())
                                .fullName(form.fullName())
                                .address(form.address())
                                .email(form.email())
                                .phoneNumber(form.phoneNumber())
                                .creditCardNumber(form.creditCardNumber())
                                .build();
        orders.put(orderId, order);
        return Mono.just(orderId);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Mono<CheckoutForm>> getOrder(@PathVariable String orderId) {
        CheckoutForm order = orders.get(orderId);
        if (null != order) {
            return ResponseEntity.ok().body(Mono.justOrEmpty(order));
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
