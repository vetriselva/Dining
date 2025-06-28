package com.vgoups.dining.service;

import com.vgoups.dining.dto.diningTable.CreateDiningTableRequest;
import com.vgoups.dining.dto.diningTable.DiningResponse;
import com.vgoups.dining.dto.diningTable.UpdateDiningTableRequest;
import com.vgoups.dining.dto.user.UserResponse;
import com.vgoups.dining.entity.DiningTable;
import com.vgoups.dining.entity.User;
import com.vgoups.dining.mapper.DiningTableMapper;
import com.vgoups.dining.repository.DiningTableRepository;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DiningTableService {

    private final DiningTableRepository diningTableRepository;

    public Page<DiningTable> findByCriteria(Map<String, String> filters, Pageable pageable) {
        return diningTableRepository.findByCriteria(filters, pageable);
    }

    public DiningResponse save(CreateDiningTableRequest request) {
        DiningTable diningTable  = diningTableRepository.save(DiningTableMapper.toEntity(request));
        return DiningTableMapper.toResponse(diningTable);
    }

    public Boolean existsByNameAndDiningIdNot(String name, Long diningId) {
        return diningTableRepository.existsByNameAndDiningIdNot(name, diningId);
    }

    public DiningTable findDiningTableById(Long id) {
        return diningTableRepository.findById(id).orElse(null);
    }

    public DiningResponse updateById(DiningTable diningTable, UpdateDiningTableRequest request) {
        DiningTable updated = DiningTableMapper.updateEntity(diningTable, request);
        return DiningTableMapper.toResponse(updated);
    }

    public DiningResponse deleteById(DiningTable diningTable) {
        diningTable.setDeletedAt(LocalDateTime.now());
        diningTableRepository.save(diningTable);
        return DiningTableMapper.toResponse(diningTable);
    }

    public void delete(DiningTable diningTable) {
        diningTable.setDeletedAt(LocalDateTime.now());
        DiningTableMapper.toResponse(diningTableRepository.save(diningTable));
    }

    public DiningResponse activateOrInactive(DiningTable diningTable, Boolean activated) {
        diningTable.setStatus(activated);
        return DiningTableMapper.toResponse(diningTableRepository.save(diningTable));
    }
}

