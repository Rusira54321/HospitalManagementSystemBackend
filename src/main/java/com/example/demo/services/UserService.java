package com.example.demo.services;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository,RoleRepository roleRepository,PasswordEncoder passwordEncoder)
    {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public void saveUser(User user,String roleName)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByName("ROLE_"+roleName.toUpperCase());
        if(role==null)
        {
            role = new Role("ROLE_"+roleName.toUpperCase());
            roleRepository.save(role);
        }
        user.setRoles(new HashSet<>(Set.of(role)));
        userRepository.save(user);
    }
}
