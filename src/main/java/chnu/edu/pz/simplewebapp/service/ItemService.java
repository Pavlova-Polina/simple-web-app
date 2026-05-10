package chnu.edu.pz.simplewebapp.service;

import chnu.edu.pz.simplewebapp.model.Item;
import chnu.edu.pz.simplewebapp.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
    }

    public Item update(Long id, Item newItem) {
        Item item = findById(id);
        item.setName(newItem.getName());
        item.setPrice(newItem.getPrice());
        return itemRepository.save(item);
    }

    public void delete(Long id) {
        itemRepository.deleteById(id);
    }
}