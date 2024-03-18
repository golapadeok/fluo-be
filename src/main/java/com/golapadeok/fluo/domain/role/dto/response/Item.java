package com.golapadeok.fluo.domain.role.dto.response;

import com.golapadeok.fluo.domain.role.domain.Credential;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class Item {

    private String name;
    private String description;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static List<Item> toItemList() {
        return Stream.of(Credential.values())
                .map(credential -> new Item(credential.getName(), credential.getDescription()))
                .toList();
    }
}
