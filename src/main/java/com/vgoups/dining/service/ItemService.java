package com.vgoups.dining.service;

import com.vgoups.dining.dto.diningTable.CreateDiningTableRequest;
import com.vgoups.dining.dto.item.CreateItemRequest;
import com.vgoups.dining.dto.item.ItemResponse;
import com.vgoups.dining.dto.item.UpdateItemRequest;
import com.vgoups.dining.dto.role.RoleResponse;
import com.vgoups.dining.dto.role.UpdateRoleRequest;
import com.vgoups.dining.entity.DiningTable;
import com.vgoups.dining.entity.Item;
import com.vgoups.dining.entity.Role;
import com.vgoups.dining.mapper.DiningTableMapper;
import com.vgoups.dining.mapper.ItemMapper;
import com.vgoups.dining.mapper.RoleMapper;
import com.vgoups.dining.repository.DiningTableRepository;
import com.vgoups.dining.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Page<Item> findByCriteria(Map<String, String> filters, Pageable pageable) {
        return itemRepository.findByCriteria(filters, pageable);
    }

    public Item save(CreateItemRequest request) {
        return itemRepository.save(ItemMapper.toEntity(request));
    }

    public Boolean existsByNameAndIdNot(String name, Long id) {
        return itemRepository.existsByNameAndIdNot(name, id);
    }

    public Item findById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    public ItemResponse update(Item item, UpdateItemRequest request) {
        ItemMapper.updateEntity(request, item);
        return ItemMapper.toResponse(itemRepository.save(item));
    }

    public void delete(Item item) {
        item.setDeletedAt(LocalDateTime.now());
        ItemMapper.toResponse(itemRepository.save(item));
    }

    public ItemResponse activateOrInactive(Item item, Boolean activated) {
        item.setStatus(activated);
        return ItemMapper.toResponse(itemRepository.save(item));
    }

}
