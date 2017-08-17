package edu.learn.beans.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class UserAccount {

	@Id
	@GeneratedValue
	private Long id;

	private Double money = 600d;

	@OneToOne(fetch = FetchType.EAGER)
	private User user;

}
