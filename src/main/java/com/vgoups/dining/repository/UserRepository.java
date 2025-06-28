package com.vgoups.dining.repository;

import com.vgoups.dining.entity.User;
import com.vgoups.dining.specification.user.UserSpecification;
import io.micrometer.common.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Map;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Boolean existsUserByEmail(String email);

    public default Page<User> findByCriteria(Map<String, String> criteria, Pageable pageable) {
        Specification<User> specs = Specification.allOf();
        if(!StringUtils.isEmpty(criteria.get("email"))){
            specs = specs.and(UserSpecification.byEmail(criteria.get("email")));
        }
        if(!StringUtils.isEmpty(criteria.get("name"))){
            specs = specs.and(UserSpecification.byUsername(criteria.get("name")));
        }

        if(!StringUtils.isEmpty(criteria.get("role"))){
            specs = specs.and(UserSpecification.byRole(criteria.get("role")));
        }
        return findAll(specs, pageable);
    }

    boolean existsUserByEmailAndIdNot(String email, Long id);
}
