package com.vgoups.dining.controller;

import com.vgoups.dining.core.BaseController;
import com.vgoups.dining.dto.CreateDiningTableRequest;
import com.vgoups.dining.dto.UpdateDiningTableRequest;
import com.vgoups.dining.entity.DiningTable;
import com.vgoups.dining.mapper.DiningTableMapper;
import com.vgoups.dining.repository.DiningTableRepository;
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

@RequestMapping("api/dining-table")
@RestController
@RequiredArgsConstructor
public class DiningTableController extends BaseController {

    private final DiningTableRepository diningTableRepository;

    @PostMapping("/list-by-filter")
    public ResponseEntity<ApiPaginationResponse<List<CreateDiningTableRequest>>> listByFilter(
            @RequestBody Map<String, String> filters,
            @RequestParam(defaultValue = PaginationConstants.DEFAULT_PAGE + "") int page,
            @RequestParam(defaultValue = PaginationConstants.DEFAULT_PAGE_SIZE +"") int size,
            HttpServletRequest httpServletRequest
    ) {

        Pageable pageable = PageRequest.of(page,size);

        Page<DiningTable> result = diningTableRepository.findByCriteria(filters, pageable);

        String nextUrl = null;
        if(result.hasNext()) {
            String baseUrl = httpServletRequest.getRequestURL().toString();
            nextUrl = baseUrl + "?page=" + (page + 1) + "&size="+size;
        }

        List<CreateDiningTableRequest> diningList = result
                .map(obj -> new CreateDiningTableRequest(
                        obj.getName(),
                        obj.getMemberCount(),
                        obj.getStatus()
                )).toList();

        return simplePagination(true,"Dining list", diningList, nextUrl, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<DiningTable>> create(@Valid @RequestBody CreateDiningTableRequest request) {
        DiningTable response = diningTableRepository.save(DiningTableMapper.toEntity(request));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response(false,"Created successfully", response));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse<DiningTable>> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateDiningTableRequest request
    ) {

        if (diningTableRepository.existsByNameAndDiningIdNot(request.getName(), id)) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response(false,"Name already exists", null));

        }

        return diningTableRepository.findById(id)
                .map(entity -> {
                    DiningTable updated = DiningTableMapper.updateEntity(entity, request);
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .body(response(true,"Dining table updated successfully", updated));

                }).orElseGet(() ->  ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response(true,"Item not found", null)));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        Optional<DiningTable> d = diningTableRepository.findById(id);
        if (d.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response(false, "Item not found", null));

        }
        d.get().setDeletedAt(LocalDateTime.now());
        diningTableRepository.save(d.get());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response(true, "Dining table deleted successfully", null));
    }


    @PutMapping("/{id}/active")
    public ResponseEntity<ApiResponse<DiningTable>> activate(@PathVariable Long id) {
        return diningTableRepository.findById(id).map(diningTable -> {
            diningTable.setStatus(true);
            DiningTable updated = diningTableRepository.save(diningTable);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response(true, "Dining table activated successfully", updated));

        }).orElseGet(()-> ResponseEntity
                .status(HttpStatus.OK)
                .body(response(false, "Item not found", null)));

    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<DiningTable>> deActivate(@PathVariable Long id) {
        return diningTableRepository.findById(id).map(diningTable -> {
            diningTable.setStatus(false);
            DiningTable updated = diningTableRepository.save(diningTable);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response(true, "Dining table activated successfully", updated));

        }).orElseGet(()-> ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response(false, "Item not found", null)));

    }

}
