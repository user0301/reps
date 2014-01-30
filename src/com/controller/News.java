package com.controller;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace="com.controller.Feed")
public class News {

	private ArrayList<Item> listOfItems;

	public News(ArrayList<Item> listOfItems) {
		super();
		this.listOfItems = listOfItems;
	}

	public ArrayList<Item> getListOfItems() {
		return listOfItems;
	}
	
	@XmlElement(name="item")
	public void setListOfItems(ArrayList<Item> listOfItems) {
		this.listOfItems = listOfItems;
	}

	@Override
	public String toString() {
		return "News [listOfItems=" + listOfItems + "]";
	}

	public News() {
		super();
	}
	
	
}
