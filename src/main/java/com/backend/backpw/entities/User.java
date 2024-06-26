package com.backend.backpw.entities;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="users")
public class User {
	
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="name")
    private String name;
	@Column(name="truename")
    private String truename;
	@Column(name="passwd")
    private String passwd;
	@Column(name="passwd2")
    private String passwd2;
	@Column(name="email")
    private String email;
    
    @Column(name = "creatime", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date creatime;


    
    public User() {
    	
    }



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getTruename() {
		return truename;
	}



	public void setTruename(String truename) {
		this.truename = truename;
	}



	public String getPasswd() {
		return passwd;
	}



	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}



	public String getPasswd2() {
		return passwd2;
	}



	public void setPasswd2(String passwd2) {
		this.passwd2 = passwd2;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public Date getCreatime() {
		return creatime;
	}



	public void setCreatime(Date creatime) {
		this.creatime = creatime;
	}



	
    

}
