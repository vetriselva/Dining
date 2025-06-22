package com.vgoups.dining.controller;

import com.vgoups.dining.core.BaseController;
import com.vgoups.dining.dto.diningTable.CreateDiningTableRequest;
import com.vgoups.dining.dto.item.CreateItemRequest;
import com.vgoups.dining.dto.item.UpdateItemRequest;
import com.vgoups.dining.entity.DiningTable;
import com.vgoups.dining.entity.Item;
import com.vgoups.dining.mapper.DiningTableMapper;
import com.vgoups.dining.mapper.ItemMapper;
import com.vgoups.dining.repository.ItemRepository;
import com.vgoups.dining.util.pagination.ApiPaginationResponse;
import com.vgoups.dining.util.pagination.ApiResponse;
import com.vgoups.dining.util.pagination.PaginationConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
public class ItemController extends BaseController {

    private final ItemRepository itemRepository;

    @PostMapping("/list-by-filter")
    public ResponseEntity<ApiPaginationResponse<List<CreateItemRequest>>> listByFilter(
            @RequestParam Map<String, String> filters,
            @RequestParam(defaultValue = PaginationConstants.DEFAULT_PAGE + "") int page,
            @RequestParam(defaultValue = PaginationConstants.DEFAULT_PAGE_SIZE + "") int size,
            HttpServletRequest httpServletRequest
    ) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Item> items = itemRepository.findByCriteria(filters, pageable);
        String nextUrl = null;

        if(items.hasNext()){
            String baseUrl = httpServletRequest.getRequestURL().toString();
            nextUrl = baseUrl +"?page="+ (page +1) +"size="+size;
        }

        List<CreateItemRequest> result = items
                .map(ItemMapper::toDto).toList();

        return simplePagination(true,"Dining list", result, nextUrl, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Item>> create(@Valid @RequestBody CreateItemRequest request) {
        Item item = ItemMapper.toEntity(request);
        itemRepository.save(item);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response(false,"Created successfully", item));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse<Item>> update(
            @PathVariable Long id,
            @RequestBody UpdateItemRequest request
    ) {
        if (itemRepository.existsByNameAndIdNot(request.getName(), id)) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response(false,"Name already exists", null));

        }
        return itemRepository.findById(id)
                .map(entity -> {
                    Item updated = ItemMapper.updateEntity(request, entity);
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .body(response(true,"Item updated successfully", updated));

                }).orElseGet(() ->  ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(response(true,"Item not found", null)));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        Optional<Item> d = itemRepository.findById(id);
        if (d.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response(false, "Item not found", null));

        }
        d.get().setDeletedAt(LocalDateTime.now());
        itemRepository.save(d.get());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response(true, "Item deleted successfully", null));
    }


    @PutMapping("/{id}/active")
    public ResponseEntity<ApiResponse<Item>> activate(@PathVariable Long id) {
        return itemRepository.findById(id).map(item -> {
            item.setStatus(true);
            Item updated = itemRepository.save(item);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response(true, "Item activated successfully", updated));

        }).orElseGet(()-> ResponseEntity
                .status(HttpStatus.OK)
                .body(response(false, "Item not found", null)));

    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<Item>> deActivate(@PathVariable Long id) {
        return itemRepository.findById(id).map(item -> {
            item.setStatus(false);
            Item updated = itemRepository.save(item);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response(true, "Item activated successfully", updated));

        }).orElseGet(()-> ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response(false, "Item not found", null)));

    }
}
