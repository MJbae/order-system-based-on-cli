package kr.co._29cm.homework.application;

import kr.co._29cm.homework.application.interfaces.ItemService;
import kr.co._29cm.homework.infra.ItemRepository;
import kr.co._29cm.homework.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService implements ItemService<Item> {
    private final ItemRepository repository;

    public List<Item> loadAll() {
        return repository.findAll();
    }

    public Item loadOneBy(Long id) {
        return repository.findByIdForUpdate(id);
    }

    @Transactional
    public void save(Item item) {
        repository.save(item);
    }
}
