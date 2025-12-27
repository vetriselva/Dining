package com.vgoups.dining.seeder;

import com.vgoups.dining.contract.Seeder;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("dev")
public class DatabaseSeeder {
    private final List<Seeder> seeders;

    public DatabaseSeeder(List<Seeder> seeders) {
        this.seeders = seeders;
    }

    public void seed() {
        seeders.forEach(Seeder::run);
    }
}
