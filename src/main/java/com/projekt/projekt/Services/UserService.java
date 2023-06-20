package com.projekt.projekt.Services;


import com.projekt.projekt.Repositories.UserRepository;
import com.projekt.projekt.Validation.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow();
    }
    public void save(User user){
        userRepository.save(user);
    }
    public java.util.Optional<User> findByName(String name){
       return userRepository.findByUsername(name);
    }
    public java.util.Optional<User> findById(Long id){
        return userRepository.findById(id);
    }
    public ArrayList<User> getAllUsers(){
        return (ArrayList<User>) userRepository.findAll();
    }
    public ArrayList <String> getUserNotes(){
        ArrayList<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        roles.add("ROLE_ADMIN");
        return roles;
    }
}
