package com.vgoups.dining.repository;

import com.vgoups.dining.entity.Item;
import com.vgoups.dining.specification.item.ItemSpecification;
import io.micrometer.common.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Map;
@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {
    public default Page<Item> findByCriteria(Map<String, String> criteria, Pageable pageable) {
        Specification<Item> spec = Specification.allOf();
        if(!StringUtils.isEmpty(criteria.get("name"))){
            spec = spec.and(ItemSpecification.byName(criteria.get("name")));
        }
        return findAll(spec, pageable);
    }

    public Boolean existsByNameAndIdNot(String name, long itemId);

}
