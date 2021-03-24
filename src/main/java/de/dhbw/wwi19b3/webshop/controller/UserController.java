package de.dhbw.wwi19b3.webshop.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.dhbw.wwi19b3.webshop.entity.Item;
import de.dhbw.wwi19b3.webshop.entity.User;
import de.dhbw.wwi19b3.webshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserRepository userRepository;

	@GetMapping
	public Iterable<User> fetchAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/{id}")
	public Optional<User> fetchItemById(@PathVariable long id) {
		return userRepository.findById(id);
	}

	@PostMapping("/new")
	public User createNewItem(@RequestBody User user) {
		return userRepository.save(user);
	}

	@GetMapping("/{id}/favorites")
	public Set<Item> fetchUserFavorites(@PathVariable long id) {
		Optional<User> user = userRepository.findById(id);

		if(user.isPresent()) {
			return user.get().getFavorites();
		} else {
			return Collections.emptySet();
		}
//		return user.isPresent()
//				? user.get().getFavorites()
//				: Collections.emptySet();
	}

	@PostMapping("/{id}/favorites/add")
	public User addUserFavorite(@PathVariable long id, @RequestBody Item item) {
		Optional<User> result = userRepository.findById(id);
		if(!result.isPresent()) {
			return null;
		}

		User user = result.get();
		user.getFavorites().add(item);

		return userRepository.save(user);
	}

}
