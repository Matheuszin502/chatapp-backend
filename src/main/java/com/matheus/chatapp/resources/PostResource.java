package com.matheus.chatapp.resources;

import java.net.URI;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.matheus.chatapp.domain.Comment;
import com.matheus.chatapp.domain.Post;
import com.matheus.chatapp.dto.CommentDTO;
import com.matheus.chatapp.resources.util.URL;
import com.matheus.chatapp.services.PostService;
import com.matheus.chatapp.services.UserService;

@RestController
@RequestMapping(value="/posts")
public class PostResource {
	
	@Autowired
	private PostService service;
	
	@Autowired
	private UserService serviceUser;
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Post> findById(@PathVariable String id) {
		Post obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping(value="/titlesearch")
	public ResponseEntity<List<Post>> findByTitle(@RequestParam(value="text", defaultValue="") String text) {
		text = URL.decodeParam(text);
		List<Post> list = service.findByTitle(text);
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value="/fullsearch")
	public ResponseEntity<List<Post>> fullSearch(
			@RequestParam(value="text", defaultValue="") String text, 
			@RequestParam(value="minDate", defaultValue="") String minDate, 
			@RequestParam(value="maxDate", defaultValue="") String maxDate) {
		text = URL.decodeParam(text);
		Date min = URL.convertDate(minDate, new Date(0L));
		Date max = URL.convertDate(maxDate, new Date());
		List<Post> list = service.fullSearch(text, min, max);
		return ResponseEntity.ok().body(list);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody Post post) throws Exception {
		post = service.insert(post);
		serviceUser.updatePosts(post.getAuthor().getId(), post);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PostMapping(value="/{id}")
	public ResponseEntity<Void> insertComment(@PathVariable String id, @RequestBody CommentDTO objDto) throws Exception{
		Comment obj = service.fromDTO(objDto);
		obj = service.insertComment(id, obj);
		
		Post post = service.findById(id);
		post.getComments().add(new CommentDTO(obj));
		service.insertComment(post);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idComment}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value="/{id}/{idComment}")
	public ResponseEntity<Void> deleteComment(@PathVariable String id, @PathVariable String idComment) {
		service.deleteComment(id, idComment);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Void> update(@RequestBody Post obj, @PathVariable String id) {
		obj.setId(id);
		service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value="/{id}/{idComment}")
	public ResponseEntity<Void> updateComment(@PathVariable String id, @PathVariable String idComment, @RequestBody CommentDTO objDto) {
		Comment obj = service.fromDTO(objDto);
		service.updateComment(service.findById(id), idComment, obj);
		
		return ResponseEntity.noContent().build();
	}
}
