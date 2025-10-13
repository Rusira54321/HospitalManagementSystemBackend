package com.example.demo.Controller;

import com.example.demo.model.*;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.HospitalRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

            if (doctors.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No doctors found in the database");
            }
            // 👇 Create a new list to hold doctor + hospital info
            List<Map<String, Object>> doctorList = new ArrayList<>();

            for (Doctor doctor : doctors) {
                Map<String, Object> doctorData = new HashMap<>();
                doctorData.put("doctorId", doctor.getId());
                doctorData.put("firstName", doctor.getFirstName());
                doctorData.put("lastName", doctor.getLastName());
                doctorData.put("specialization", doctor.getSpecialization());
                doctorData.put("username", doctor.getUsername());
                doctorData.put("email", doctor.getEmail());

                // 👇 Manually include hospital info
                Hospital hospital = doctor.getHospital();
                if (hospital != null) {
                    Map<String, Object> hospitalData = new HashMap<>();
                    hospitalData.put("hospitalId", hospital.getHospitalId());
                    hospitalData.put("hospitalName", hospital.getHospitalName());
                    hospitalData.put("hospitalLocation", hospital.getHospitalLocation());
                    hospitalData.put("hospitalType", hospital.getHospitalType().toString());
                    doctorData.put("hospital", hospitalData);
                }

                doctorList.add(doctorData);
            }
            return ResponseEntity.ok(doctorList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while fetching doctors: " + e.getMessage());
        }
    }


}
