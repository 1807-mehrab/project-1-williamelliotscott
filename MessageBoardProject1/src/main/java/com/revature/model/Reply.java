package com.revature.model;

public class Reply {
	private BoardUser author;
	private String text;
	private Topic topic;
	private int id;
	
	public BoardUser getAuthor() {
		return author;
	}
	public void setAuthor(BoardUser author) {
		this.author = author;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public Reply(BoardUser author, Topic topic, String text, int id) {
		super();
		this.author = author;
		this.topic = topic;
		this.text = text;
		this.id = id;
	}
	
	public Reply() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Topic getTopic() {
		return topic;
	}
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	
	
}
