package com.verint.verint.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.verint.verint.model.Item;

import reactor.core.publisher.Flux;

@Service
public class ItemService {
    private final List<Item> items;

    public ItemService() {
        items = IntStream.range(1, 101)
                .mapToObj(i -> new Item(i, "http://verint.com/item" + i + ".jpg", "Item " + i, "Short description for item " + i, 100.00 + i, 10.00))
                .collect(Collectors.toList());
    }

    public Flux<Item> getItems(int page, int size) {
        return Flux.fromIterable(items.stream()
                .skip(page * size)
                .limit(size)
                .collect(Collectors.toList()));
    }
}
