package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String actor;        // who accessed (e.g., "kiosk_nurse_1" or username)
    private String patientUsername;
    private String action;
    private LocalDateTime timestamp;
    private String details;

    public AuditLog() {}

    public AuditLog(String actor, String patientUsername, String action, String details) {
        this.actor = actor;
        this.patientUsername = patientUsername;
        this.action = action;
        this.timestamp = LocalDateTime.now();
        this.details = details;
    }

    // getters / setters
    public Long getId() { return id; }
    public String getActor() { return actor; }
    public void setActor(String actor) { this.actor = actor; }
    public String getPatientUsername() { return patientUsername; }
    public void setPatientUsername(String patientUsername) { this.patientUsername = patientUsername; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
}
