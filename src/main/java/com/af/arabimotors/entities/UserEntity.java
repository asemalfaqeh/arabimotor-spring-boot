package com.af.arabimotors.entities;

import java.beans.Transient;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, unique = true)
	private Long id;

	@Column(name = "email", nullable = false, unique = true, length = 50)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "fullname", nullable = false, length = 50)
	private String fullname;

	@Column(name = "enabled", nullable = false, columnDefinition = "boolean default true")
	private boolean enabled;

	@Column(name = "email_verify", nullable = true, columnDefinition = "boolean default false")
	private boolean isEmailVerified;
	
	@Column(name = "address", nullable = false, length = 100)
	private String address;

	@Column(name = "created_at", nullable = false)
	private String created_at;

	@Column(name = "is_primary",  columnDefinition = "boolean default false")
	private boolean is_primary;
    
	@Column(name = "email_verified_at", nullable = true)
	private String emailVerifiedAt;
	
	@Column(name = "phone", nullable = false, unique = true, length = 22)
	private String phone;
	
	@OneToOne(fetch  = FetchType.LAZY)
	@JoinColumn(name = "city_name", referencedColumnName = "id")
	private CityEntity city;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seller_type", referencedColumnName = "id")
	private SellerTypeEntity sellerTypeEntity;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "social_id", referencedColumnName = "id")
	private UserSocialEntity socialEntity;

	@Column(name = "user_photo", nullable = true)
	private String user_photo;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Set<RoleEntity> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleEntity> roles) {
		this.roles = roles;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public boolean isIs_primary() {
		return is_primary;
	}

	public void setIs_primary(boolean is_primary) {
		this.is_primary = is_primary;
	}

	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public CityEntity getCity() {
		return city;
	}

	public void setCity(CityEntity city) {
		this.city = city;
	}

	public SellerTypeEntity getSellerTypeEntity() {
		return sellerTypeEntity;
	}

	public void setSellerTypeEntity(SellerTypeEntity sellerTypeEntity) {
		this.sellerTypeEntity = sellerTypeEntity;
	}

	public String getUser_photo() {
		return user_photo;
	}

	public void setUser_photo(String user_photo) {
		this.user_photo = user_photo;
	}

	public boolean isEmailVerified() {
		return isEmailVerified;
	}

	public void setEmailVerified(boolean isEmailVerified) {
		this.isEmailVerified = isEmailVerified;
	}
	
	
    public String getEmailVerifiedAt() {
		return emailVerifiedAt;
	}

	public void setEmailVerifiedAt(String emailVerifiedAt) {
		this.emailVerifiedAt = emailVerifiedAt;
	}

	public String getPhotosImagePath(Long id, String user_photo) {
        return "/user-photos/" + id + "/" + user_photo;
    }

	public UserSocialEntity getSocialEntity() {
		return socialEntity;
	}

	public void setSocialEntity(UserSocialEntity socialEntity) {
		this.socialEntity = socialEntity;
	}

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", email=" + email + ", password=" + password + ", fullname=" + fullname
				+ ", enabled=" + enabled + ", isEmailVerified=" + isEmailVerified + ", address=" + address
				+ ", created_at=" + created_at + ", is_primary=" + is_primary + ", emailVerifiedAt=" + emailVerifiedAt
				+ ", phone=" + phone + ", city=" + city + ", sellerTypeEntity=" + sellerTypeEntity + ", user_photo="
				+ user_photo + ", roles=" + roles + "]";
	}
    

}
