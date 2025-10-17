package com.example.demo.Controller;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointment")
@CrossOrigin(origins = "*")
public class AppointmentController {

    @Autowired
    private HospitalRepository hospitalRepo;

    @Autowired
    private DepartmentRepository departmentRepo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private PatientRepository patientRepository;

    @PostMapping
    public Appointment createAppointment(@RequestBody AppointmentRequest request) {

        // Get patient by patientId
        Patient patient = patientRepository.findByPatientId(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        // Get existing hospital
        Hospital hospital = hospitalRepo.findByName(request.getHospital());
        if (hospital == null) {
            throw new RuntimeException("Hospital not found");
        }

        // Get existing department
        Department department = departmentRepo.findByName(request.getDepartment());
        if (department == null) {
            throw new RuntimeException("Department not found");
        }

        // Create appointment
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setHospital(hospital);
        appointment.setDepartment(department);
        appointment.setAppointmentDate(request.getAppointmentDate()); // String type
        appointment.setAppointmentTime(request.getAppointmentTime()); // String type

        return appointmentRepo.save(appointment);
    }

}
