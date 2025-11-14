package com.vgoups.dining.api.repository;

import com.vgoups.dining.entity.DiningTable;
import com.vgoups.dining.repository.DiningTableRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class DiningTableRepositoryTests {

    @Autowired
    DiningTableRepository diningTableRepository;

    @Test
    public void DiningRepository_SaveAll_ReturnSaveDining() {

        //Arrange
        DiningTable diningTableEntity = new DiningTable();
        diningTableEntity.setName("Table A");
        diningTableEntity.setMemberCount(4);
        diningTableEntity.setStatus(Boolean.TRUE);
        diningTableEntity.setCreatedAt(LocalDateTime.now());

        //Act
        DiningTable diningTable = diningTableRepository.save(diningTableEntity);
        //Assert

        Assertions.assertNotNull(diningTable);
        Assertions.assertTrue(diningTable.getDiningId() > 0);
    }

    @Test
    public void DiningRepository_GetAll_DiningTable() {
        DiningTable diningTableEntity = new DiningTable();
        diningTableEntity.setName("Table A");
        diningTableEntity.setMemberCount(4);
        diningTableEntity.setStatus(Boolean.TRUE);
        diningTableEntity.setCreatedAt(LocalDateTime.now());

        DiningTable diningTableEntity1 = new DiningTable();
        diningTableEntity1.setName("Table A");
        diningTableEntity1.setMemberCount(4);
        diningTableEntity1.setStatus(Boolean.TRUE);
        diningTableEntity1.setCreatedAt(LocalDateTime.now());
        diningTableRepository.save(diningTableEntity);
        diningTableRepository.save(diningTableEntity1);

        //Act
        List<DiningTable> list = diningTableRepository.findAll();

        //Asset
        Assertions.assertNotNull(list);
        Assertions.assertEquals(2, list.size());

    }
}
