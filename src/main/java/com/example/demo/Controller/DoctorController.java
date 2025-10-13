package com.example.demo.Controller;

import com.example.demo.model.Appointment;
import com.example.demo.model.Doctor;
import com.example.demo.model.Hospital;
import com.example.demo.model.Status;
import com.example.demo.repository.AppoinmentRepository;
import com.example.demo.repository.DoctorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Documented;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController
{
    private final AppoinmentRepository appoinmentRepository;
    public DoctorController(AppoinmentRepository appoinmentRepository)
    {
        this.appoinmentRepository = appoinmentRepository;
    }

    @PostMapping("/addAppointment")
    public ResponseEntity<?> AddAppointments(Appointment appointment)
    {
        try {
            Doctor doctor = appointment.getDoctor();
            if (doctor == null || doctor.getId() == null) {
                return ResponseEntity.badRequest().body("Doctor information is required.");
            }
            LocalDateTime endtime = appointment.getEndTime();
            LocalDateTime startTime = appointment.getStartTime();
            Status status = appointment.getStatus();
            if (endtime == null || startTime == null || status == null) {
                return ResponseEntity.badRequest().body("ALL Fields are required");
            }
            List<Appointment> appointments = appoinmentRepository.findByDoctor(doctor);
            LocalDateTime newStart = appointment.getStartTime();
            LocalDateTime newEnd = appointment.getEndTime();
            for (Appointment existing : appointments) {
                LocalDateTime existingStart = existing.getStartTime();
                LocalDateTime existingEnd = existing.getEndTime();
                boolean overlaps = newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart);
                if (overlaps) {
                    return ResponseEntity.badRequest().body("This doctor already has an appointment during the selected time.");
                }
            }
            Appointment saved = appoinmentRepository.save(appointment);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        }catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/getAppointments")
    public ResponseEntity<?> getAppointments(Doctor doctor)
    {
        try {
            List<Appointment> appointments = appoinmentRepository.findByDoctor(doctor);
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}