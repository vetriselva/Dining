package com.vgoups.dining.mapper;

import com.vgoups.dining.dto.item.CreateItemRequest;
import com.vgoups.dining.dto.item.UpdateItemRequest;
import com.vgoups.dining.entity.Item;

public class ItemMapper {
    public static Item toEntity(CreateItemRequest request) {
        Item item = new Item();
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setStatus(request.getStatus());
        return item;
    }

    public static CreateItemRequest toDto(Item entity) {
        CreateItemRequest request = new CreateItemRequest();
        request.setName(entity.getName());
        request.setDescription(entity.getDescription());
        request.setStatus(entity.getStatus());
        return request;
    }

    public static Item updateEntity(UpdateItemRequest request, Item entity) {
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        return entity;
    }
}
