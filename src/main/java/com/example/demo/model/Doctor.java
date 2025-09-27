package com.example.demo.model;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String specialization;

    @Column
    private String OfficeLocation;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hospital_id",nullable = false)
    private Hospital hospital;

    public Doctor()
    {

    }
    public Doctor(String firstName,String lastName,String specialization,String OfficeLocation,User user,Hospital hospital)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.OfficeLocation = OfficeLocation;
        this.user = user;
        this.hospital = hospital;
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

    public void setUser(User user)
    {
        this.user = user;
    }

    public void setSpecialization(String specialization)
    {
        this.specialization = specialization;
    }
    public void setOfficeLocation(String OfficeLocation)
    {
        this.OfficeLocation = OfficeLocation;
    }
    public Long getId()
    {
        return id;
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

    public String getOfficeLocation()
    {
        return OfficeLocation;
    }

    public User getUser()
    {
        return user;
    }
}
