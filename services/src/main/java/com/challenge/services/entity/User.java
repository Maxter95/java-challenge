package com.challenge.services.entity;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.challenge.services.entity.audit.DateAudit;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"}),
		@UniqueConstraint(columnNames = {"email"})})

public class User extends DateAudit {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user")
	private Long id;



	@NotBlank
	@Size(max = 15)
	private String username;

	@NaturalId
	@NotBlank
	@Size(max = 40)
	@Email
	private String email;

	@NotBlank
	@Size(max = 100)
	private String password;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();



	@Column(name = "salt_user")
	private String salt;

	@Column(name = "created_user")
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date created;

	@Column(name = "moviesList")
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	@JoinTable(name = "moviesList", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "id_movie"))
	private Set<Movies> moviesList = new HashSet<>();

	@Column(name = "updated_user")
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date updated;

	public User() {
		super();
	}

	public User( String username, String email, String password) {

		this.username = username;
		this.email = email;
		this.password = password;
	}

	public User(User user) {
		this.id = user.getId();

		this.email = user.getEmail();
		this.password = user.getPassword();
		this.salt = user.getSalt();
		this.created = user.getCreated();
		this.updated = user.getUpdated();
		this.roles = user.getRoles();
	}

	public User(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Movies> getMoviesList() {
		return moviesList;
	}

	public void setMoviesList(Set<Movies> moviesList) {
		this.moviesList = moviesList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
