package com.controller;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;

@XmlRootElement(namespace="com.controller.News")
public class Item {
		
		private String id;
		
		private String title;
		
		private String description;
		
		private String cat_id;
		
		
		private String category;
		
		private Date publishDate;

		@JsonIgnore
		public Date getPublishDate() {
			return publishDate;
		}
		@XmlElement (name="publish_date")
		public void setPublishDate(Date publishDate) {
			this.publishDate = publishDate;
		}

		public String getId() {
			return id;
		}

		@XmlElement(name="id")
		public void setId(String id) {
			this.id = id;
		}


		public String getTitle() {
			return title;
		}

		@XmlElement(name="title")
		public void setTitle(String title) {
			this.title = title;
		}


		public String getDescription() {
			return description;
		}

		@XmlElement(name="description")
		public void setDescription(String description) {
			this.description = description;
		}


		public String getCat_id() {
			return cat_id;
		}

		@XmlElement(name="cat_id")
		public void setCat_id(String cat_id) {
			this.cat_id = cat_id;
		}


		public String getCategory() {
			return category;
		}

		@XmlElement(name="category")
		public void setCategory(String category) {
			this.category = category;
		}

		public Item(String id, String title, String description, String cat_id,
				String category) {
			super();
			this.id = id;
			this.title = title;
			this.description = description;
			this.cat_id = cat_id;
			this.category = category;
		}

		@Override
		public String toString() {
			return "News Item [id=" + id + ", title=" + title + ", description="
					+ description + ", cat_id=" + cat_id + ", category="
					+ category + "]";
		}

		public Item() {
			super();
		}
		
		
		
		
		
		
}
