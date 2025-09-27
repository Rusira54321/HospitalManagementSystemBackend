package com.example.demo.Controller;


import com.example.demo.model.HospitalStaff;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController
{
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    public AuthController(RoleRepository roleRepository,PasswordEncoder passwordEncoder,UserRepository userRepository)
    {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostMapping("/register/hospitalStaff")
    public ResponseEntity<?> register(@RequestBody HospitalStaff hospitalStaff,@RequestParam String username,@RequestParam String password,@RequestParam String email
     , @RequestParam String role,@RequestParam String hospitalID)
    {
        Role existRole = roleRepository.findByName(role);
        if(existRole==null)
        {
            Role newrole = new Role(role);
            existRole = roleRepository.save(newrole);
        }
        Set<Role> roles = new HashSet<Role>();
        roles.add(existRole);
        String encodePassword = passwordEncoder.encode(password);
        User newuser = new User(username,encodePassword,roles,email);
        User createdUser = userRepository.save(newuser);
        if(createdUser==null)
        {

        }
    }

    @PostMapping("/register/doctor")
    public ResponseEntity<?> register()
    {

    }

    @PostMapping("/register/patient")
    public ResponseEntity<?> register()
    {

    }
}
