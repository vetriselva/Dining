package com.vgoups.dining.mapper;

import com.vgoups.dining.dto.CreateDiningTableRequest;
import com.vgoups.dining.dto.UpdateDiningTableRequest;
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

}
