package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "patient") // Keep table name simple
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String patientId;

    private String dob;
    private String lastVisit;
    private String medications;
    private String allergies;
    private String appointmentDate;
    private String appointmentTime;
    private String selectedDoctor;
    private String appointmentReason;

    // Default constructor
    public Patient() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public String getLastVisit() { return lastVisit; }
    public void setLastVisit(String lastVisit) { this.lastVisit = lastVisit; }

    public String getMedications() { return medications; }
    public void setMedications(String medications) { this.medications = medications; }

    public String getAllergies() { return allergies; }
    public void setAllergies(String allergies) { this.allergies = allergies; }

    public String getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(String appointmentDate) { this.appointmentDate = appointmentDate; }

    public String getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(String appointmentTime) { this.appointmentTime = appointmentTime; }

    public String getSelectedDoctor() { return selectedDoctor; }
    public void setSelectedDoctor(String selectedDoctor) { this.selectedDoctor = selectedDoctor; }

    public String getAppointmentReason() { return appointmentReason; }
    public void setAppointmentReason(String appointmentReason) { this.appointmentReason = appointmentReason; }
}
