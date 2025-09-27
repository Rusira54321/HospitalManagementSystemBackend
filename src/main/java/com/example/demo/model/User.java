package com.example.demo.model;

import jakarta.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name="users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false,unique = true)
    private String email;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private Doctor doctor;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private Patient patient;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private HospitalStaff hospitalStaff;

    @ManyToMany(
            fetch=FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User()
    {

    }

    public User(String username,String password,Set<Role> roles,String email)
    {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.email = email;
    }

    public void setDoctor(Doctor doctor)
    {
        this.doctor = doctor;
    }

    public void setPatient(Patient patient)
    {
        this.patient = patient;
    }

    public void setHospitalStaff(HospitalStaff hospitalStaff)
    {
        this.hospitalStaff = hospitalStaff;
    }
    public Doctor getDoctor()
    {
        return doctor;
    }
    public Patient getPatient()
    {
        return patient;
    }
    public HospitalStaff getHospitalStaff()
    {
        return hospitalStaff;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public String getEmail()
    {
        return  email;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public void setRoles(Set<Role> roles)
    {
        this.roles = roles;
    }
    public Long getId()
    {
        return id;
    }
    public Set<Role> getRoles()
    {
        return  roles;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
