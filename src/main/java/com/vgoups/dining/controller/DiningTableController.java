package com.vgoups.dining.controller;

import com.vgoups.dining.dto.DiningTableDTO;
import com.vgoups.dining.entity.DiningTable;
import com.vgoups.dining.repository.DiningTableRepository;
import com.vgoups.dining.util.ApiResponse;
import com.vgoups.dining.util.PaginationConstants;
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
public class DiningTableController {

    private final DiningTableRepository diningTableRepository;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<DiningTableDTO>>> list(
            @RequestParam(defaultValue = PaginationConstants.DEFAULT_PAGE + "") int page,
            @RequestParam(defaultValue = PaginationConstants.DEFAULT_PAGE_SIZE +"") int pageZize,
            @RequestParam(required = false) String name,
            HttpServletRequest httpServletRequest
    ) {
        Pageable pageable = PageRequest.of(page, pageZize);
        Page<DiningTable> result = diningTableRepository.findByFilters(name, pageable);
        List<DiningTableDTO> DiningTableDTO = result.stream()
                .map(obj -> new DiningTableDTO(
                        obj.getName(),
                        obj.getMemberCount(),
                        obj.getStatus()
                )).toList();

        String hasNextUrl = null;
        if(result.hasNext()) {
            String baseUrl = httpServletRequest.getRequestURL().toString();
            hasNextUrl = baseUrl + "?page=" + (page + 1) + "&size="+pageZize;
        }

        ApiResponse<List<DiningTableDTO>> response = ApiResponse.<List<DiningTableDTO>>builder()
                .status(true)
                .statusCode(HttpStatus.OK.value())
                .data(DiningTableDTO)
                .nextUrl(hasNextUrl)
                .message("list of dining tables")
                .build();
        return ResponseEntity.ok(response);

    }

    @PostMapping("/list-by-filter")
    public ResponseEntity<ApiResponse<List<DiningTableDTO>>> listByFilter(@RequestBody Map<String, String> filters) {
        Pageable pageable = PageRequest.of(0,10);
        Page<DiningTable> result = diningTableRepository.findByCriteria(filters, pageable);
        List<DiningTableDTO> DiningTableDTO = result.stream()
                .map(obj -> new DiningTableDTO(
                        obj.getName(),
                        obj.getMemberCount(),
                        obj.getStatus()
                )).toList();
        ApiResponse<List<DiningTableDTO>> response = ApiResponse.<List<DiningTableDTO>>builder()
                .status(true)
                .statusCode(HttpStatus.OK.value())
                .data(DiningTableDTO)
                .message("list of dining tables")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<DiningTable> create(@Valid @RequestBody DiningTableDTO diningTableDTO) {
        DiningTable entity = new DiningTable();
        entity.setName(diningTableDTO.getName());
        entity.setMemberCount(diningTableDTO.getMemberCount());
        entity.setStatus(diningTableDTO.getStatus());
        DiningTable response = diningTableRepository.save(entity);
        return ResponseEntity.ok(response);
    }


}
