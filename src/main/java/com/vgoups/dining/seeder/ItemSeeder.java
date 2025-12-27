package com.vgoups.dining.seeder;

import com.vgoups.dining.contract.Seeder;
import com.vgoups.dining.entity.Item;
import com.vgoups.dining.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class ItemSeeder implements Seeder {

    private final ItemRepository itemRepository;

    @Override
    public void run() {
        if (itemRepository.count() > 0) return;

        Item item = new Item();
        item.setName("Chicken Biryani");
        item.setDescription("Chicken Biryani");
        item.setStatus(Boolean.TRUE);

        Item item1 = new Item();
        item1.setName("Mutton Biryani");
        item1.setDescription("Mutton Biryani");
        item1.setStatus(Boolean.TRUE);

        itemRepository.saveAll(List.of(item, item1));
    }
}
