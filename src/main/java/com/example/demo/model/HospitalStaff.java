package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "hospitalStaff")
public class HospitalStaff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeID;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hospital_id",nullable = false)
    private Hospital hospital;

    public HospitalStaff()
    {}

    public HospitalStaff(String firstName,String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setHospital(Hospital hospital)
    {
        this.hospital = hospital;
    }
    public Hospital hospital()
    {
        return hospital;
    }
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Long getEmployeeID()
    {
        return employeeID;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public User getUser()
    {
        return  user;
    }
}
