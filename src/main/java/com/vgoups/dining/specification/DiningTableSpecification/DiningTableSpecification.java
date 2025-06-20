package com.vgoups.dining.specification.DiningTableSpecification;

import com.vgoups.dining.entity.DiningTable;
import org.springframework.data.jpa.domain.Specification;

public class DiningTableSpecification {

    public static Specification<DiningTable> byName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%"+name+"%");
    }

    public static Specification<DiningTable> byMemberCount(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("memberCount"), id);
    }
}
