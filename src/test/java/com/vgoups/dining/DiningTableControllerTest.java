package com.vgoups.dining;

import com.vgoups.dining.controller.DiningTableController;
import com.vgoups.dining.dto.diningTable.CreateDiningTableRequest;
import com.vgoups.dining.dto.diningTable.UpdateDiningTableRequest;
import com.vgoups.dining.entity.DiningTable;
import com.vgoups.dining.mapper.DiningTableMapper;
import com.vgoups.dining.repository.DiningTableRepository;
import com.vgoups.dining.util.pagination.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiningTableControllerTest {

    @Mock
    private DiningTableRepository diningTableRepository;

    @InjectMocks
    private DiningTableController controller;

    private DiningTable sampleTable;

    @BeforeEach
    void setup() {
        sampleTable = new DiningTable();
        sampleTable.setDiningId(1L);
        sampleTable.setName("Table 1");
        sampleTable.setStatus(false);
        sampleTable.setMemberCount(4);
    }

    @Test
    void testCreateDiningTable() {
        CreateDiningTableRequest request = new CreateDiningTableRequest("Table 1", 4, true);
        DiningTable entity = DiningTableMapper.toEntity(request);

        when(diningTableRepository.save(any())).thenReturn(entity);

        ResponseEntity<ApiResponse<DiningTable>> response = controller.create(request);

        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getStatus());
        assertEquals("Created successfully", response.getBody().getMessage());
        verify(diningTableRepository, times(1)).save(any());
    }

    @Test
    void testUpdateDiningTable_Success() {
        UpdateDiningTableRequest request = new UpdateDiningTableRequest("Updated", 6, true);

        when(diningTableRepository.existsByNameAndDiningIdNot(request.getName(), 1L)).thenReturn(false);
        when(diningTableRepository.findById(1L)).thenReturn(Optional.of(sampleTable));

        ResponseEntity<ApiResponse<DiningTable>> response = controller.update(1L, request);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getStatus());
        assertEquals("Dining table updated successfully", response.getBody().getMessage());
    }

    @Test
    void testUpdateDiningTable_DuplicateName() {
        UpdateDiningTableRequest request = new UpdateDiningTableRequest("Table 1", 6, true);
        when(diningTableRepository.existsByNameAndDiningIdNot(request.getName(), 1L)).thenReturn(true);

        ResponseEntity<ApiResponse<DiningTable>> response = controller.update(1L, request);

        assertNotNull(response.getBody());
        assertFalse(response.getBody().getStatus());
        assertEquals("Name already exists", response.getBody().getMessage());
    }

    @Test
    void testDeleteDiningTable() {
        when(diningTableRepository.findById(1L)).thenReturn(Optional.of(sampleTable));

        ResponseEntity<ApiResponse<Void>> response = controller.delete(1L);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getStatus());
        verify(diningTableRepository, times(1)).save(any());
    }

    @Test
    void testActivateDiningTable() {
        when(diningTableRepository.findById(1L)).thenReturn(Optional.of(sampleTable));
        when(diningTableRepository.save(any())).thenReturn(sampleTable);

        ResponseEntity<ApiResponse<DiningTable>> response = controller.activate(1L);

        assertNotNull(response.getBody());
        assertTrue(response.getBody().getStatus());
        assertEquals("Dining table activated successfully", response.getBody().getMessage());
    }

    @Test
    void testDeactivateDiningTable_NotFound() {
        when(diningTableRepository.findById(2L)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse<DiningTable>> response = controller.deActivate(2L);

        assertNotNull(response.getBody());
        assertFalse(response.getBody().getStatus());
        assertEquals("Item not found", response.getBody().getMessage());
    }
}
