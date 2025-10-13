package com.example.demo.Controller;

import com.example.demo.model.*;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.HospitalRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    private final DoctorRepository doctorRepository;
    public PatientController(DoctorRepository doctorRepository)
    {
        this.doctorRepository = doctorRepository;
    }

    @GetMapping("/getDoctors")
    public ResponseEntity<?> getDoctors()
    {
        try {
            List<Doctor> doctors = doctorRepository.findAll();
            if(doctors.isEmpty())
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Hospitals in the Data base");
            }
            return ResponseEntity.ok(doctors);
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
