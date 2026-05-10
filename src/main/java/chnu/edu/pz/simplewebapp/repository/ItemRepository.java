package chnu.edu.pz.simplewebapp.repository;

import chnu.edu.pz.simplewebapp.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}