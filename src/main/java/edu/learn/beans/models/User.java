package edu.learn.beans.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.learn.util.LocalDateDeserializer;
import edu.learn.util.LocalDateSerializer;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class User implements UserDetails {

	@Id
	@GeneratedValue
	private Long id;
	private String email;
	private String name;
	@JsonIgnore
	private String password;
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate birthday;
	@ElementCollection(targetClass = Authority.class, fetch = FetchType.EAGER)
	@Enumerated(value = EnumType.STRING)
	private Set<Authority> authorities = new HashSet<>(Collections.singletonList(Authority.REGISTERED_USER));
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "user")
	private List<Booking> bookings;

	public User() {
	}

	public User(Long id, String email, String name, String password, LocalDate birthday) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.password = password;
		this.birthday = birthday;
	}

	public User(String email, String name, String password, LocalDate birthday) {
		this(null, email, name, password, birthday);
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void addAutority(Authority authority) {
		authorities.add(authority);
	}

	public void removeAutority(Authority authority) {
		authorities.remove(authority);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		User user = (User) o;

		if (!Objects.equals(id, user.id)) {
			return false;
		}
		if (email != null ? !email.equals(user.email) : user.email != null) {
			return false;
		}
		if (name != null ? !name.equals(user.name) : user.name != null) {
			return false;
		}
		return birthday != null ? birthday.equals(user.birthday) : user.birthday == null;

	}

	@Override
	public int hashCode() {
		long id = Optional.ofNullable(getId()).orElse(-1L);
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (email != null ? email.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "User{" +
			"id=" + id +
			", email='" + email + '\'' +
			", name='" + name + '\'' +
			", birthday=" + birthday +
			'}';
	}

	public User withId(long registeredId) {
		this.setId(registeredId);
		return this;
	}
}