package com.backend.backpw.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.backpw.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	
	User findByName(String name);
	User findByEmail(String email);

}
