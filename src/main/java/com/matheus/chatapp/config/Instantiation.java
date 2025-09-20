package com.matheus.chatapp.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.matheus.chatapp.domain.Comment;
import com.matheus.chatapp.domain.Post;
import com.matheus.chatapp.domain.User;
import com.matheus.chatapp.dto.AuthorDTO;
import com.matheus.chatapp.dto.CommentDTO;
import com.matheus.chatapp.repository.CommentRepository;
import com.matheus.chatapp.repository.PostRepository;
import com.matheus.chatapp.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userRepository.deleteAll();
		postRepository.deleteAll();
		commentRepository.deleteAll();
		
		User maria = new User(null, "Maria Eduarda", "maria@gmail.com");
		User pedro = new User(null, "Pedro Santos", "pedro@gmail.com");
		User joao = new User(null, "João Silva", "joao@gmail.com");
		
		userRepository.saveAll(Arrays.asList(maria, pedro, joao));
		
		Post post1 = new Post(null, sdf.parse("21/03/2018"), "Partiu viagem", "Vou viajar para São Paulo. Abraços!", new AuthorDTO(maria));
		Post post2 = new Post(null, sdf.parse("23/03/2018"), "Bom dia", "Acordei feliz hoje!", new AuthorDTO(maria));
		
		Comment c1 = new Comment(null, "Boa viagem", sdf.parse("21/03/2018"), new AuthorDTO(pedro));
		Comment c2 = new Comment(null, "Aproveite", sdf.parse("25/03/2018"), new AuthorDTO(joao));
		Comment c3 = new Comment(null, "Tenha um ótimo dia", sdf.parse("23/03/2018"), new AuthorDTO(pedro));
		
		commentRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		post1.getComments().addAll(Arrays.asList(new CommentDTO(c1), new CommentDTO(c2)));
		post2.getComments().addAll(Arrays.asList(new CommentDTO(c3)));
		
		postRepository.saveAll(Arrays.asList(post1, post2));
		
		maria.getPosts().addAll(Arrays.asList(post1, post2));
		userRepository.save(maria);
	}
}
