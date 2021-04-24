package com.af.arabimotors.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "user_social")
public class UserSocialEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "facebook_link", nullable = true)
	private String facebook_link;
	
	@Column(name = "instagram_link", nullable = true)
	private String instagram_link;
	
	@Column(name = "twitter_link", nullable = true)
	private String twitter_link;
	
	@Column(name = "linkedin_link", nullable = true)
	private String linkedin_link;

	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
	private UserEntity userEntity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	@Override
	public String toString() {
		return "UserSocialEntity [id=" + id + ", facebook_link=" + facebook_link + ", instagram_link=" + instagram_link
				+ ", twitter_link=" + twitter_link + ", linkedin_link=" + linkedin_link + ", userEntity=" + userEntity
				+ "]";
	}
	
	
	
}
