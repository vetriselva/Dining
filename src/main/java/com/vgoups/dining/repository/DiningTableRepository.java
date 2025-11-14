package com.vgoups.dining.repository;

import com.vgoups.dining.entity.DiningTable;
import com.vgoups.dining.specification.diningTable.DiningTableSpecification;
import io.micrometer.common.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Map;
@Repository
public interface DiningTableRepository extends JpaRepository<DiningTable, Long>, JpaSpecificationExecutor<DiningTable> {
    public Boolean existsByName(String name);

    public boolean existsByNameAndDiningIdNot(String name, Long id);

    public default Page<DiningTable> findByCriteria(Map<String, String> criteria, Pageable pageable) {
        Specification<DiningTable> specs = Specification.allOf();

        if(!StringUtils.isEmpty(criteria.get("name"))){
             specs = specs.and(DiningTableSpecification.byName(criteria.get("name")));
        }

        if(!StringUtils.isEmpty(criteria.get("memberCount"))){
            specs = specs.and(DiningTableSpecification.byMemberCount(Long.valueOf(criteria.get("memberCount"))));
        }
        return findAll(specs, pageable);
    }


}
