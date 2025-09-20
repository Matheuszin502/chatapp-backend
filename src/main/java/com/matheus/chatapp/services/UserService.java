package com.matheus.chatapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matheus.chatapp.domain.Post;
import com.matheus.chatapp.domain.User;
import com.matheus.chatapp.dto.UserDTO;
import com.matheus.chatapp.repository.UserRepository;
import com.matheus.chatapp.services.exception.ObjectNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repo;
	
	public List<User> findAll() {
		return repo.findAll();
	}
	
	public User findById(String id) {
		Optional<User> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}
	
	public User insert(User obj) {
		return repo.insert(obj);
	}
	
	public void delete(String id) {
		findById(id);
		repo.deleteById(id);
	}
	
	public User update(User obj) {
		Optional<User> newObj = repo.findById(obj.getId());
		if (newObj.isEmpty())
			throw new ObjectNotFoundException("Objeto não encontrado");
		else {
			updateData(newObj.get(), obj);
			return repo.save(newObj.get());
		}
	}
	
	private void updateData(User newObj, User obj) {
		if (obj.getName() != null && obj.getName() != newObj.getName())
			newObj.setName(obj.getName());
		if (obj.getEmail() != null && obj.getEmail() != newObj.getEmail())
			newObj.setEmail(obj.getEmail());
	}
	
	public User updatePosts(String id, Post post) {
		User user = repo.findById(id).get();
		user.getPosts().add(post);
		return repo.save(user);
	}

	public User fromDTO(UserDTO objDto) {
		return new User(objDto.getId(), objDto.getName(), objDto.getEmail());
	}
}
