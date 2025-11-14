package com.vgoups.dining.api.service;

import com.vgoups.dining.dto.diningTable.CreateDiningTableRequest;
import com.vgoups.dining.dto.diningTable.DiningResponse;
import com.vgoups.dining.entity.DiningTable;
import com.vgoups.dining.mapper.DiningTableMapper;
import com.vgoups.dining.repository.DiningTableRepository;
import com.vgoups.dining.service.DiningTableService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DiningTableServiceTests {

    @Mock
    private DiningTableRepository repository;

    @Mock
    private DiningTableMapper mapper;

    @InjectMocks
    private DiningTableService service;

    @Test
    public void DiningService_CreateDiningTable_ReturnDiningTable() {
        CreateDiningTableRequest request = new CreateDiningTableRequest();
        request.setName("DiningTable 1");
        request.setMemberCount(4);
        request.setStatus(true);

        DiningTable entity = new DiningTable();
        entity.setName("DiningTable 1");

        DiningResponse response = new DiningResponse();
        response.setName("DiningTable 1");


        try (MockedStatic<DiningTableMapper> mocked = Mockito.mockStatic(DiningTableMapper.class)) {
            mocked.when(() -> DiningTableMapper.toEntity(request)).thenReturn(entity);
            mocked.when(() -> DiningTableMapper.toResponse(entity)).thenReturn(response);

            DiningResponse result = service.save(request);

            Assertions.assertNull(result);
        }
    }
}
