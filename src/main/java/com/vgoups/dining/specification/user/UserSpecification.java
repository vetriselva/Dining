package com.vgoups.dining.specification.user;

import com.vgoups.dining.entity.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> byUsername(String username) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("username"), "%"+username+"%");
    }

    public static Specification<User> byEmail(String email) {
       return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("email"), "%"+email+"%");
    }

    public static Specification<User> byRole(String roleName) {
        return (root, query, criteriaBuilder) -> {
            Join<String, String> roleJoin = root.join("roles", JoinType.LEFT);
            return criteriaBuilder.like(roleJoin.get("name"), "%" + roleName + "%");
        };
    }
}
