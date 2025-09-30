package com.example.demo.model;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.Set;

@Entity
@Table(name = "doctors")
public class Doctor extends  User {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String specialization;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hospital_id",nullable = false)
    private Hospital hospital;

    public Doctor()
    {

    }
    public Doctor(String firstName, String lastName, String specialization, String username, String password, Set<Role> roles,String email)
    {
        super(username,password,email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
    }

    public void setHospital(Hospital hospital)
    {
        this.hospital = hospital;
    }
    public Hospital getHospital()
    {
        return hospital;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public void setSpecialization(String specialization)
    {
        this.specialization = specialization;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getSpecialization()
    {
        return  specialization;
    }


}
