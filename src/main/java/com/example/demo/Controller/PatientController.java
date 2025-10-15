package com.example.demo.Controller;

import com.example.demo.Interfaces.INotificationService;
import com.example.demo.model.*;
import com.example.demo.repository.AppoinmentRepository;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.HospitalRepository;
import com.example.demo.repository.PatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    private final DoctorRepository doctorRepository;
    private final AppoinmentRepository appoinmentRepository;
    private final PatientRepository patientRepository;
    private final HospitalRepository hospitalRepository;
    private final List<INotificationService> notoficationServices;
    public PatientController(DoctorRepository doctorRepository,AppoinmentRepository appoinmentRepository,
                             PatientRepository patientRepository,HospitalRepository hospitalRepository,
                             List<INotificationService> notificationServices)
    {
        this.appoinmentRepository = appoinmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.hospitalRepository = hospitalRepository;
        this.notoficationServices = notificationServices;
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

    @GetMapping("/bookAppointment")
    public ResponseEntity<?> BookingAppointment(@RequestParam String appointmentID,@RequestParam String patientID)
    {
        try {
            Optional<Appointment> appointment = appoinmentRepository.findById(Long.parseLong(appointmentID));
            if (appointment.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment is not found");
            }
            if (appointment.get().getStatus().toString().equals("BOOKED")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("The appointment is already booked");
            }
            Optional<Patient> patient = patientRepository.findById(Long.parseLong(patientID));
            if (patient.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient is not found");
            }
            Appointment updateAppointment = appointment.get();
            updateAppointment.setPatient(patient.get());
            updateAppointment.setStatus(Status.BOOKED);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy 'at' hh:mm a");
            String body = "Dear " + patient.get().getFirstName() + ",\n\n" +
                    "We are pleased to inform you that your appointment has been successfully booked.\n\n" +
                    "🩺 Doctor: " + appointment.get().getDoctor().getFirstName() + " " + appointment.get().getDoctor().getLastName() + "\n" +
                    "📅 Date & Time: " + appointment.get().getStartTime().format(formatter) + " - " +
                    appointment.get().getEndTime().format(DateTimeFormatter.ofPattern("hh:mm a")) + "\n" +
                    "🏥 Room: " + (appointment.get().getRoomLocation() != null && !appointment.get().getRoomLocation().isEmpty()
                    ? appointment.get().getRoomLocation()
                    : "Not Assigned") + "\n\n" +
                    "Please arrive 10 minutes early and bring any relevant documents or reports.\n\n" +
                    "Thank you for choosing our hospital!\n\n" +
                    "Best regards,\n" +
                    "Your Hospital Team";
            String subject = "✅ Appointment Confirmed: Your Booking with Dr. "
                    + appointment.get().getDoctor().getFirstName() + " "
                    + appointment.get().getDoctor().getLastName();
            updateAppointment.setBookedTime(LocalDateTime.now());
            appoinmentRepository.save(updateAppointment);
            for (INotificationService service : notoficationServices) {
                service.sendNotification(patient.get().getEmail(), subject, body);
            }
            return ResponseEntity.ok("Booking Successful");
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getPatient")
    public ResponseEntity<?> getPatientById(@RequestParam String username)
    {
        Optional<Patient> matchedPatient = patientRepository.findByUsername(username);
        if(matchedPatient.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient is not found");
        }
        return ResponseEntity.ok(matchedPatient.get());
    }

    @GetMapping("/getBookedAppointments")
    public ResponseEntity<?> getBookedAppointmentsByPatient(@RequestParam String patientId)
    {
        try {
            Optional<Patient> matchedPatient = patientRepository.findById(Long.parseLong(patientId));
            if (matchedPatient.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient is not found");
            }
            List<Appointment> matchedAppointments = appoinmentRepository.findByPatient(matchedPatient.get());
            if (matchedAppointments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Appointments for this patient");
            }
            List<Map<String, Object>> AppointmentList = new ArrayList<>();
            for (Appointment appointment : matchedAppointments) {
                Map<String, Object> appointmentObject = new HashMap<>();
                appointmentObject.put("id", appointment.getId());
                appointmentObject.put("doctorFullName", appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName());
                appointmentObject.put("startTime", appointment.getStartTime());
                appointmentObject.put("endTime", appointment.getEndTime());
                appointmentObject.put("roomLocation", appointment.getRoomLocation());
                appointmentObject.put("status", appointment.getStatus());
                appointmentObject.put("BookedAt",appointment.getBookedTime());
                Hospital hospital = hospitalRepository.findByDoctors(appointment.getDoctor());
                appointmentObject.put("HospitalName", hospital.getHospitalName());
                AppointmentList.add(appointmentObject);
            }
            return ResponseEntity.ok(AppointmentList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
