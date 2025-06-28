package com.vgoups.dining.specification.role;

import com.vgoups.dining.entity.Role;
import org.springframework.data.jpa.domain.Specification;

public class RoleSpecification {
    public static Specification<Role> byName(String name) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(root.get("name"), "%"+name+"%");
    }
}
