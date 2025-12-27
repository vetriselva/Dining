package com.vgoups.dining.seeder;

import com.vgoups.dining.contract.Seeder;
import com.vgoups.dining.entity.Role;
import com.vgoups.dining.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Order;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("dev")
@Order(1)
@RequiredArgsConstructor
public class RoleSeeder implements Seeder {

    private final RoleRepository roleRepository;

    @Override
    public void run() {
        if (roleRepository.count() > 0) return;

        Role admin = new Role();
        admin.setRoleName("Admin");
        admin.setStatus(Boolean.TRUE);

        Role staff = new Role();
        staff.setRoleName("Staff");
        staff.setStatus(Boolean.TRUE);

        roleRepository.saveAll(List.of(admin, staff));
    }
}
