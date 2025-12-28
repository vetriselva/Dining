package com.vgoups.dining.controller;

import com.vgoups.dining.core.BaseController;
import com.vgoups.dining.dto.diningTable.CreateDiningTableRequest;
import com.vgoups.dining.dto.diningTable.DiningResponse;
import com.vgoups.dining.dto.diningTable.UpdateDiningTableRequest;
import com.vgoups.dining.entity.DiningTable;
import com.vgoups.dining.mapper.DiningTableMapper;
import com.vgoups.dining.repository.DiningTableRepository;
import com.vgoups.dining.service.DiningTableService;
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
import java.util.List;
import java.util.Map;

@RequestMapping("api/dining-table")
@RestController
@RequiredArgsConstructor
public class DiningTableController extends BaseController {

    private final DiningTableRepository diningTableRepository;
    private final DiningTableService diningTableService;

    @PostMapping("/list-by-filter")
    public ResponseEntity<ApiPaginationResponse<List<DiningResponse>>> listByFilter(
            @RequestBody Map<String, String> filters,
            @RequestParam(defaultValue = PaginationConstants.DEFAULT_PAGE + "") int page,
            @RequestParam(defaultValue = PaginationConstants.DEFAULT_PAGE_SIZE + "") int size,
            HttpServletRequest httpServletRequest
    ) {

        Pageable pageable = PageRequest.of(page, size);

        Page<DiningTable> result = diningTableService.findByCriteria(filters, pageable);

        String nextUrl = null;
        if (result.hasNext()) {
            String baseUrl = httpServletRequest.getRequestURL().toString();
            nextUrl = baseUrl + "?page=" + (page + 1) + "&size=" + size;
        }

        List<DiningResponse> diningList = result
                .map(DiningTableMapper::toResponse).toList();

        return simplePagination(true, "Dining list", diningList, nextUrl, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<DiningResponse>> create(@Valid @RequestBody CreateDiningTableRequest request) {
        DiningResponse response = diningTableService.save(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response(true, "Created successfully", response));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse<DiningResponse>> updateById(
            @PathVariable Long id,
            @Valid @RequestBody UpdateDiningTableRequest request
    ) {

        if (diningTableService.existsByNameAndDiningIdNot(request.getName(), id)) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response(false, "Name already exists", null));

        }
        DiningTable diningTable = diningTableService.findDiningTableById(id);
        if (diningTable == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response(false, "Item not found", null));
        }
        DiningResponse response = diningTableService.updateById(diningTable, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response(true, "Dining table updated successfully", response));

    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable Long id) {
        DiningTable diningTable = diningTableService.findDiningTableById(id);
        if (diningTable == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response(false, "Item not found", null));
        }
        DiningResponse diningResponse = diningTableService.deleteById(diningTable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response(true, "Dining table deleted successfully", null));

    }

    @PutMapping("/{id}/active")
    public ResponseEntity<ApiResponse<DiningResponse>> activate(@PathVariable Long id) {
        DiningTable diningTable = diningTableService.findDiningTableById(id);
        if (diningTable == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response(false, "Item not found", null));
        }
        DiningResponse diningResponse = diningTableService.activateOrInactive(diningTable, true);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response(true, "Dining table activated successfully", null));

    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<DiningResponse>> deActivate(@PathVariable Long id) {
        DiningTable diningTable = diningTableService.findDiningTableById(id);
        if (diningTable == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response(false, "Item not found", null));
        }
        DiningResponse diningResponse = diningTableService.activateOrInactive(diningTable, false);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response(true, "Dining table activated successfully", null));

    }


}
