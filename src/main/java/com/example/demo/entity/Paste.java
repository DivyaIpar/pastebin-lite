package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pastes")
public class Paste {
	
	    @Id
	    private String id;

	    private String content;
	    private Long createdAt;
	    private Long expiresAt;
	    private Integer maxViews;
	    private Integer views;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public Long getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(Long createdAt) {
			this.createdAt = createdAt;
		}
		public Long getExpiresAt() {
			return expiresAt;
		}
		public void setExpiresAt(Long expiresAt) {
			this.expiresAt = expiresAt;
		}
		public Integer getMaxViews() {
			return maxViews;
		}
		public void setMaxViews(Integer maxViews) {
			this.maxViews = maxViews;
		}
		public Integer getViews() {
			return views;
		}
		public void setViews(Integer views) {
			this.views = views;
		}
	    
	    

}
