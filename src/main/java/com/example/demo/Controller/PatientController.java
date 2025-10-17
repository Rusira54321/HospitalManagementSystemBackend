package com.example.demo.Controller;

import com.example.demo.model.Patient;
import com.example.demo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

import java.util.List;

@RestController
@RequestMapping("/api/patient")
@CrossOrigin(origins = "*")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    // Add new patient
    @PostMapping
    public ResponseEntity<?> addPatient(@RequestBody Patient patient) {
        try {
            Patient savedPatient = patientRepository.save(patient);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPatient);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving patient: " + e.getMessage());
        }
    }

    // ✅ 2. Get all patients
    @GetMapping
    public ResponseEntity<?> getAllPatients() {
        try {
            List<Patient> list = patientRepository.findAll();
            if (list.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No patients found.");
            }
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching patients: " + e.getMessage());
        }
    }

    // ✅ 3. Get patient by patientId
    @GetMapping("/{patientId}")
    public ResponseEntity<?> getPatientById(@PathVariable String patientId) {
        try {
            Optional<Patient> optionalPatient = patientRepository.findByPatientId(patientId);
            if (optionalPatient.isPresent()) {
                return ResponseEntity.ok(optionalPatient.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No patient found with ID: " + patientId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching patient: " + e.getMessage());
        }
    }
}
