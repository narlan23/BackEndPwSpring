package com.backend.backpw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.backpw.entities.User;

public interface UserRepository extends JpaRepository<User,Long> {
	
	User findByName(String name);
	User findByEmail(String email);

}
