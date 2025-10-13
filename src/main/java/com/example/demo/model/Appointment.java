package com.example.demo.model;

import jakarta.persistence.*;

import javax.print.Doc;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @ManyToOne(optional = true)
    private Patient patient;

    @ManyToOne(optional = false)
    private Doctor doctor;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    private Float price;

    public Appointment()
    {}
    public Appointment(LocalDateTime startTime,LocalDateTime endTime,Status status,
                       Float price,Doctor doctor,Patient patient)
    {
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.price = price;
        this.doctor = doctor;
        this.patient = patient;
    }

    public void setPrice(Float price)
    {
        this.price = price;
    }
    public Float getPrice()
    {
        return price;
    }
    public void setStartTime(LocalDateTime startTime)
    {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime)
    {
        this.endTime = endTime;
    }
    public void setPatient(Patient patient)
    {
        this.patient = patient;
    }
    public void setDoctor(Doctor doctor)
    {
        this.doctor = doctor;
    }
    public void setStatus(Status status)
    {
        this.status =status;
    }
    public LocalDateTime getStartTime()
    {
        return startTime;
    }
    public LocalDateTime getEndTime()
    {
        return endTime;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Status getStatus() {
        return status;
    }
}
