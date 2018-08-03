package com.revature.model;

import java.sql.Blob;
import java.sql.SQLException;

public class Topic {
	private String name;
	private String description;
	private BoardUser author;
	private int flags;
	private int id;
	private Blob image;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BoardUser getAuthor() {
		return author;
	}
	public void setAuthor(BoardUser author) {
		this.author = author;
	}
	public int getFlags() {
		return flags;
	}
	public void setFlags(int flags) {
		this.flags = flags;
	}
	
	public Topic(String name, String description, BoardUser author, int flags, int id) {
		super();
		this.name = name;
		this.description = description;
		this.author = author;
		this.flags = flags;
		this.id = id;
		this.image = null;
	}
	
	public Topic() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Topic(String name, String description, BoardUser author, int flags, int id, Blob image) {
		super();
		this.name = name;
		this.description = description;
		this.author = author;
		this.flags = flags;
		this.id = id;
		this.image = image;
	}
	
	public Blob getImage() {
		return image;
	}
	public void setImage(Blob image) {
		this.image = image;
	}
	
	
}
