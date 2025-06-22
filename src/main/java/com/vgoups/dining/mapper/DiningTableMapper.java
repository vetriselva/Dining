package com.vgoups.dining.mapper;

import com.vgoups.dining.dto.diningTable.CreateDiningTableRequest;
import com.vgoups.dining.dto.diningTable.UpdateDiningTableRequest;
import com.vgoups.dining.entity.DiningTable;

public class DiningTableMapper {

    public static DiningTable toEntity(CreateDiningTableRequest request) {
        DiningTable entity = new DiningTable();
        entity.setName(request.getName());
        entity.setMemberCount(request.getMemberCount());
        entity.setStatus(request.getStatus());
        return entity;
    }

    public static DiningTable updateEntity(DiningTable entity, UpdateDiningTableRequest request) {
        entity.setName(request.getName());
        entity.setMemberCount(request.getMemberCount());
        entity.setStatus(request.getStatus());
        return entity;
    }

    public static CreateDiningTableRequest toDto(DiningTable entity) {
        CreateDiningTableRequest request = new CreateDiningTableRequest();
        request.setName(entity.getName());
        request.setMemberCount(entity.getMemberCount());
        request.setStatus(entity.getStatus());
        return request;
    }

}
