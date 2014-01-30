package com.controller;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name="feed")
public class Feed {

	private News n;

	public News getN() {
		return n;
	}

	@XmlElement(name="news")
	public void setN(News n) {
		this.n = n;
	}

	public Feed(News n) {
		super();
		this.n = n;
	}

	public Feed() {
		super();
	}

	@Override
	public String toString() {
		return "Feed [n=" + n + "]";
	}
	
}
