package com.vgoups.dining.specification.item;

import com.vgoups.dining.entity.Item;
import org.springframework.data.jpa.domain.Specification;

public class ItemSpecification {

    public static Specification<Item> byName(String name) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(root.get("name"), "%"+name+"%");
    }
}
