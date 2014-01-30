package com.controller;

import java.util.Comparator;

public class DateComparator implements Comparator<Item> {

	@Override
	public int compare(Item o1, Item o2) {
		if(o1.getPublishDate().after(o2.getPublishDate()))
			return -1;
			return 1;
	}

}
