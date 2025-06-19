package com.vgoups.dining.repository;

import com.vgoups.dining.entity.DiningTable;
import com.vgoups.dining.specification.DiningTableSpecification.DiningTableSpecification;
import io.micrometer.common.util.StringUtils;
import org.hibernate.annotations.Where;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
public interface DiningTableRepository extends JpaRepository<DiningTable, Long>, JpaSpecificationExecutor<DiningTable> {
    public Boolean existsByName(String name);

    public boolean existsByNameAndDiningIdNot(String name, Long id);

    @Query("SELECT d FROM DiningTable d WHERE " +
    "   LOWER(d.name) LIKE LOWER(CONCAT('%',:name,'%'))")
    public Page<DiningTable> findByFilters(@Param("name") String name, Pageable pageable);

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
