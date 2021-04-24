package com.af.arabimotors.model.request;

public class UserSocialRequest {

	private String facebook_link;
	private String instagram_link;
	private String twitter_link;
	private String linkedin_link;
	private Long id;
	public String getFacebook_link() {
		return facebook_link;
	}
	public void setFacebook_link(String facebook_link) {
		this.facebook_link = facebook_link;
	}
	public String getInstagram_link() {
		return instagram_link;
	}
	public void setInstagram_link(String instagram_link) {
		this.instagram_link = instagram_link;
	}
	public String getTwitter_link() {
		return twitter_link;
	}
	public void setTwitter_link(String twitter_link) {
		this.twitter_link = twitter_link;
	}
	public String getLinkedin_link() {
		return linkedin_link;
	}
	public void setLinkedin_link(String linkedin_link) {
		this.linkedin_link = linkedin_link;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	
	
	
	
}
