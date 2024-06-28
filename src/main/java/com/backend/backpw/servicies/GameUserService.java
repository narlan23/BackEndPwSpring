package com.backend.backpw.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.backpw.dto.GameUserDTO;
import com.backend.backpw.entities.User;
import com.backend.backpw.repositories.UserRepository;

@Service
public class GameUserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public GameUserDTO findByUsername(String username) {
        User user = userRepository.findByName(username);
        return new GameUserDTO(user.getId(), user.getTruename(),user.getEmail());
    }

}
