package com.backend.backpw.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.backpw.entities.User;
import com.backend.backpw.repositories.UserRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public String registerUser(String login, String pass, String repass, String email) {
        if (login.isEmpty() || pass.isEmpty() || repass.isEmpty() || email.isEmpty()) {
            return "Todos os campos estão preenchidos incorretamente!";
        }

        if (email.contains("'")) {
            return "Formato de email inválido";
        }

        if (login.length() < 4 || login.length() > 10) {
            return "O login deve conter no mínimo 4 e no máximo 10 caracteres.";
        }

        User existingUserByName = userRepository.findByName(login);
        if (existingUserByName != null) {
            return "Login já está no banco de dados";
        }

        if (pass.length() < 4 || pass.length() > 10 || repass.length() < 4 || repass.length() > 10) {
            return "A senha e a repetição da senha devem conter no mínimo 4 e no máximo 10 caracteres.";
        }

        if (email.length() < 4 || email.length() > 25) {
            return "O e-mail deve conter no mínimo 4 e no máximo 25 caracteres";
        }

        User existingUserByEmail = userRepository.findByEmail(email);
        if (existingUserByEmail != null) {
            return "E-Mail já está no banco de dados.";
        }

        if (!pass.equals(repass)) {
            return "As senhas não coincidem";
        }

        String salt;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            salt = Base64.getEncoder().encodeToString(md.digest((login + pass).getBytes()));
        } catch (NoSuchAlgorithmException e) {
            return "Erro ao gerar o hash da senha";
        }

        User user = new User();
        user.setName(login);
        user.setPasswd(salt);
        user.setEmail(email);
        userRepository.save(user);

        return "Conta " + login + " registrada com sucesso :)";
    }
    
    public List<User> listarTodosUsuario(){
    	return userRepository.findAll();
    }
}
