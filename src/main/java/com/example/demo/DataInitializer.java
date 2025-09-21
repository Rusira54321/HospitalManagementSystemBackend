package com.example.demo;

import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;

@Component
public class DataInitializer {
    private final RoleRepository roleRepository;
    public DataInitializer(RoleRepository roleRepository)
    {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void initRoles()
    {
        String[] roleNames = {"ROLE_ADMIN","ROLE_DOCTOR","ROLE_PATIENT"};
        for(String name:roleNames)
        {
            if(roleRepository.findByName(name)==null)
            {
                Role role = new Role(name);
                roleRepository.save(role);
            }
        }
    }
}
