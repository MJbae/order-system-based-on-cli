package kr.co._29cm.homework;

import kr.co._29cm.homework.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
