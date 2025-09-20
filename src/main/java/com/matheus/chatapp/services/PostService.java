package com.matheus.chatapp.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matheus.chatapp.domain.Comment;
import com.matheus.chatapp.domain.Post;
import com.matheus.chatapp.domain.User;
import com.matheus.chatapp.dto.AuthorDTO;
import com.matheus.chatapp.dto.CommentDTO;
import com.matheus.chatapp.repository.CommentRepository;
import com.matheus.chatapp.repository.PostRepository;
import com.matheus.chatapp.repository.UserRepository;
import com.matheus.chatapp.services.exception.ObjectNotFoundException;

@Service
public class PostService {
	
	@Autowired
	private PostRepository repo;
	
	@Autowired
	private UserRepository repoUser;
	
	@Autowired
	private CommentRepository repoComment;
	
	public Post findById(String id) {
		Optional<Post> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}
	
	public List<Post> findByTitle(String text) {
		return repo.searchTitle(text);
	}
	
	public List<Post> fullSearch(String text, Date minDate, Date maxDate) {
		maxDate = new Date(maxDate.getTime() + 86399000);
		return repo.fullSearch(text, minDate, maxDate);
	}
	
	public Post insert(Post post) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));	
		post.setDate(sdf.parse(sdf.format(new Date())));
		
		User user = repoUser.findById(post.getAuthor().getId()).get();
		post.setAuthor(new AuthorDTO(user));
		
		return repo.insert(post);
	}
	
	public Comment insertComment(String id, Comment obj) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		obj.setDate(sdf.parse(sdf.format(new Date())));
		
		User user = repoUser.findById(obj.getAuthor().getId()).get();
		obj.setAuthor(new AuthorDTO(user));
		
		return repoComment.save(obj);
	}
	
	public Post insertComment(Post obj) {
		return repo.save(obj);
	}
	
	public void delete(String id) {
		repo.deleteById(id);
	}
	
	public void deleteComment(String id, String idComment) {
		repoComment.deleteById(idComment);
		Post post = repo.findById(id).get();
		for (int i = 0; i < post.getComments().size(); i++)
			if (post.getComments().get(i).getId().equals(idComment))
				post.getComments().remove(i);
		repo.save(post);
	}
	
	public Post update(Post obj) {
		Post newObj = repo.findById(obj.getId()).get();
		if (obj.getTitle() != null && obj.getTitle() != newObj.getTitle())
			newObj.setTitle(obj.getTitle());
		if (obj.getBody() != null && obj.getBody() != newObj.getBody())
			newObj.setBody(obj.getBody());
		return repo.save(newObj);
	}
	
	public Post updateComment(Post post, String idComment, Comment obj) {
		Comment c = repoComment.findById(idComment).get();
		c.setText(obj.getText());
		for (int i = 0; i < post.getComments().size(); i++)
			if (post.getComments().get(i).getId().equals(idComment))
				post.getComments().set(i, new CommentDTO(c));
		repoComment.save(c);
		return repo.save(post);
	}
	
	public Comment fromDTO(CommentDTO objDto) {
		return new Comment(null, objDto.getText(), null, objDto.getAuthor());
	}
}
