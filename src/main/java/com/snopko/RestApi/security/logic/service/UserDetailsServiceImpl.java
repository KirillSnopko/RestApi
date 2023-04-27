package com.snopko.RestApi.security.logic.service;

import com.snopko.RestApi.security.dao.entity.UserDao;
import com.snopko.RestApi.security.dao.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private IUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDao user = repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found!"));
        return new User(user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole().getName())));
    }
}
