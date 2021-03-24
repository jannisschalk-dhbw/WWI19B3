package de.dhbw.wwi19b3.webshop.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.dhbw.wwi19b3.webshop.entity.Item;
import de.dhbw.wwi19b3.webshop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemsController {

	private final ItemRepository itemRepository;

	@GetMapping
	public Iterable<Item> fetchAllItems() {
		return itemRepository.findAll();
	}

	@GetMapping("/{id}")
	public Optional<Item> fetchItemById(@PathVariable long id) {
		return itemRepository.findById(id);
	}

	@PostMapping("/new")
	public Item createNewItem(@RequestBody Item item) {
		return itemRepository.save(item);
	}

	@DeleteMapping("/{id}/delete")
	public void deleteItemById(@PathVariable long id) {
		itemRepository.deleteById(id);
	}

	@PutMapping("/{id}/update")
	public void updateItemById(@PathVariable long id, @RequestBody Item offer) {
		Optional<Item> persisted = itemRepository.findById(id);

		persisted.ifPresent(item -> updateItem(offer, item));
	}

	private void updateItem(Item offer, Item persisted) {
		persisted.setName(offer.getName());
		persisted.setPrice(offer.getPrice());
		itemRepository.save(persisted);
	}
}
