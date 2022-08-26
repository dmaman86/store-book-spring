package com.store.storebookspring.services;

import com.store.storebookspring.model.Role;
import com.store.storebookspring.model.User;
import com.store.storebookspring.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * User service DB
 */
@Service("userDetailsService")
@Slf4j
public class UserService implements UserDetailsService {

    /**
     * object to write/read with table user in db
     */
    @Autowired
    private UserRepository userRepository;

    /**
     *
     * @param username string
     * @return User object of spring
     * @throws UsernameNotFoundException if not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        User user = userRepository.findByUsername(username);

        if(user == null)
            throw new UsernameNotFoundException(username);

        var roles = new ArrayList<GrantedAuthority>();

        for(Role role: user.getRoles()){
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), roles);

    }

    /**
     *
     * @param username string
     * @return list of roles
     */
    public List<Role> getUserRoles(String username){
        return userRepository.findByUsername(username).getRoles();
    }

}