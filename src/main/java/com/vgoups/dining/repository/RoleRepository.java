package com.vgoups.dining.repository;

import com.vgoups.dining.entity.Role;
import com.vgoups.dining.specification.role.RoleSpecification;
import io.micrometer.common.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.Map;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
    public default Page<Role> findByCriteria(Map<String, String> criteria, Pageable pageable) {
        Specification<Role> spec = Specification.allOf();
        if(!StringUtils.isEmpty(criteria.get("name"))){
            spec = spec.and(RoleSpecification.byName(criteria.get("name")));
        }
        return findAll(spec, pageable);
    }

    public Boolean existsByRoleNameAndRoleIdNot(String name, long itemId);
}
