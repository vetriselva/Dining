package com.vgoups.dining.seeder;

import com.vgoups.dining.contract.Seeder;
import com.vgoups.dining.entity.User;
import com.vgoups.dining.repository.RoleRepository;
import com.vgoups.dining.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class UserSeeder implements Seeder {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run() {
        if (userRepository.count() > 0) return;

        User admin = new User();
        admin.setName("Admin");
        admin.setEmail("admin@admin.com");
        admin.setPassword(passwordEncoder.encode("Admin@123"));
        admin.setStatus(Boolean.TRUE);
        admin.setRoles(Set.of(roleRepository.findByRoleName("Admin")));

        User user1 = new User();
        user1.setName("Staff");
        user1.setEmail("staff@staff.com");
        user1.setPassword(passwordEncoder.encode("Staff@123"));
        user1.setStatus(Boolean.TRUE);
        user1.setRoles(Set.of(roleRepository.findByRoleName("Staff")));
        userRepository.saveAll(List.of(admin, user1));
    }
}
