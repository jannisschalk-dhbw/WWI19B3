package de.dhbw.wwi19b3.webshop.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
	public User fetchUserById(@PathVariable long id) {

		Optional<User> result = userRepository.findById(id);
		User user = result.get();

		Link self = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).fetchUserById(user.getId())).withSelfRel();
		Link addFav = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).fetchUserFavorites(id))
				.withRel("addUserFavorite");
		user.add(self, addFav);

		return user;
	}

	@PostMapping("/new")
	public User createNewUser(@RequestBody User offer) {
		User user = userRepository.save(offer);

		Link self = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).fetchUserById(user.getId())).withSelfRel();
		Link userFav = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).fetchUserFavorites(user.getId()))
				.withRel("fetchUserFavorites");
		Link addUserFav =
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).addUserFavorite(user.getId(), null))
				.withRel("addUserFavorite");

		user.add(self, userFav, addUserFav);
		return user;
	}

	@GetMapping("/{id}/favorites")
	public Set<Item> fetchUserFavorites(@PathVariable long id) {
		Optional<User> user = userRepository.findById(id);

		return user.isPresent()
				? user.get().getFavorites()
				: Collections.emptySet();
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
