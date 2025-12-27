package com.vgoups.dining.config;

import com.vgoups.dining.seeder.DatabaseSeeder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Configuration
@Profile("dev")
public class SeederConfig {
    @Bean
    CommandLineRunner runSeeders(DatabaseSeeder seeder) {
        return args -> seeder.seed();
    }
}
