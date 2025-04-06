package com.devsuperior.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devsuperior.demo.UserDetailsProjection;
import com.devsuperior.demo.entities.Role;
import com.devsuperior.demo.entities.User;
import com.devsuperior.demo.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     
        List<UserDetailsProjection> list = repository.searchUserAndRolesByEmail(username);
        if(list.isEmpty()) {
            throw new UsernameNotFoundException("Email not found");
        }
        User user = new User(list.get(0).getUsername(), list.get(0).getPassword());
        for (UserDetailsProjection projection : list) {
            user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
        }
        return user;
    }

}
