package com.example.demo.Controller;

import com.example.demo.model.Hospital;
import com.example.demo.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospital")
@CrossOrigin(origins = "*")
public class HospitalController {

    @Autowired
    private HospitalRepository hospitalRepository;

    // Add new hospital
    @PostMapping
    public ResponseEntity<?> addHospital(@RequestBody Hospital hospital) {
        try {
            Hospital savedHospital = hospitalRepository.save(hospital);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedHospital);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving hospital: " + e.getMessage());
        }
    }

    // Get all hospitals
    @GetMapping
    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

}
