package com.matheus.chatapp.dto;

import java.io.Serializable;
import java.util.Date;

import com.matheus.chatapp.domain.Comment;

public class CommentDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String text;
	private Date date;
	private AuthorDTO author;
	
	public CommentDTO() {
	}

	public CommentDTO(Comment obj) {
		this.id = obj.getId();
		this.text = obj.getText();
		this.date = obj.getDate();
		this.author = obj.getAuthor();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public AuthorDTO getAuthor() {
		return author;
	}

	public void setAuthor(AuthorDTO author) {
		this.author = author;
	}
}
