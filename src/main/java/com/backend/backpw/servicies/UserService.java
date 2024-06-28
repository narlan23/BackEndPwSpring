package com.backend.backpw.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.backend.backpw.dto.UserDTO;
import com.backend.backpw.entities.User;
import com.backend.backpw.repositories.UserRepository;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    public String registerUser(UserDTO userDTO) {
        String login = userDTO.getName();
        String pass = userDTO.getPasswd();
        String repass = userDTO.getPasswd2();
        String email = userDTO.getEmail();
        String truename = userDTO.getTruename();

        
        
        if (login.isEmpty() || pass.isEmpty() || repass.isEmpty() || email.isEmpty()) {
            return "Erro: Todos os campos devem ser preenchidos!";
        }

        if (email.contains("'")) {
            return "Erro: Formato de email inválido";
        }

        if (login.length() < 4 || login.length() > 10) {
            return "Erro: O login deve conter entre 4 e 10 caracteres.";
        }

        User existingUserByName = userRepository.findByName(login);
        if (existingUserByName != null) {
            return "Erro: Login já está no banco de dados";
        }

        if (pass.length() < 4 || pass.length() > 10 || repass.length() < 4 || repass.length() > 10) {
            return "Erro: A senha e a repetição da senha devem conter no mínimo 4 e no máximo 10 caracteres.";
        }

        if (email.length() < 4 || email.length() > 25) {
            return "Erro: O e-mail deve conter no mínimo 4 e no máximo 25 caracteres";
        }

        User existingUserByEmail = userRepository.findByEmail(email);
        if (existingUserByEmail != null) {
            return "Erro: E-Mail já está no banco de dados.";
        }

        if (!pass.equals(repass)) {
            return "Erro: As senhas não coincidem";
        }

        String salt;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            salt = Base64.getEncoder().encodeToString(md.digest((login + pass).getBytes()));
        } catch (NoSuchAlgorithmException e) {
            return "Erro: Erro ao gerar o hash da senha";
        }
        
        String hashedPassword = passwordEncoder.encode(pass);

        // Criar usuário e salvar no banco de dados
        User user = new User();
        user.setName(login);
        user.setTruename(truename);
        user.setPasswd(salt);
        user.setPasswd2(hashedPassword);
        user.setEmail(email);
        userRepository.save(user);

        return "Conta " + login + " registrada com sucesso :)";
    }
}
