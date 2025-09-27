package com.example.demo.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String FirstName;

    @Column(nullable = false)
    private String LastName;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String gender;

    @Column
    private String PhoneNumber;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medicalrecords_id")
    private MedicalRecords medicalRecords;


    public Patient()
    {

    }

    public Patient(String FirstName,String LastName,LocalDate dateOfBirth,String gender,User user,String PhoneNumber,MedicalRecords medicalRecords)
    {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.user = user;
        this.PhoneNumber = PhoneNumber;
        this.medicalRecords = medicalRecords;
    }

    public void setMedicalRecords(MedicalRecords medicalRecords)
    {
        this.medicalRecords = medicalRecords;
    }
    public MedicalRecords getMedicalRecords()
    {
        return  medicalRecords;
    }
    public void setPhoneNumber(String PhoneNumber)
    {
        this.PhoneNumber = PhoneNumber;
    }

    public  void setFirstName(String FirstName)
    {
        this.FirstName = FirstName;
    }

    public void setLastName(String LastName)
    {
        this.LastName = LastName;
    }

    public void setDateOfBirth(LocalDate dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }


    public void setUser(User user)
    {
        this.user = user;
    }

    public Long getId()
    {
        return id;
    }
    public String getFirstName()
    {
        return FirstName;
    }
    public String getLastName()
    {
        return LastName;
    }
    public LocalDate getDateOfBirth()
    {
        return dateOfBirth;
    }
    public String getGender()
    {
        return gender;
    }

    public User getUser()
    {
        return  user;
    }

    public String getPhoneNumber()
    {
        return PhoneNumber;
    }
}
