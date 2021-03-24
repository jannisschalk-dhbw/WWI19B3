package de.dhbw.wwi19b3.webshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.dhbw.wwi19b3.webshop.entity.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

}
