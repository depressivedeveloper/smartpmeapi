package com.pmesmart.smartapi.service;

import java.util.Optional;

import com.pmesmart.smartapi.data.UserDetailsData;
import com.pmesmart.smartapi.model.User;
import com.pmesmart.smartapi.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImplementation implements UserDetailsService{

    private final UserRepository repository;

    public UserServiceImplementation(UserRepository repository){
        this.repository = repository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repository.findByUsername(username);

        if(user.isEmpty()){
            throw new UsernameNotFoundException("[] não encontado");
        }
        
        return new UserDetailsData(user);
    }
    
}
