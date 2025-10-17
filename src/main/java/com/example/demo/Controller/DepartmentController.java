package com.example.demo.Controller;

import com.example.demo.model.Department;
import com.example.demo.model.Hospital;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
@CrossOrigin(origins = "*")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    // Add new department
    @PostMapping
    public ResponseEntity<?> addDepartment(@RequestBody Department department) {
        try {
            // Check if hospital exists
            if (department.getHospital() == null || department.getHospital().getId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Hospital ID must be provided for the department");
            }

            Hospital hospital = hospitalRepository.findById(department.getHospital().getId())
                    .orElseThrow(() -> new RuntimeException("Hospital not found"));

            department.setHospital(hospital);
            Department savedDept = departmentRepository.save(department);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDept);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving department: " + e.getMessage());
        }
    }

    // Get all departments
    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        return ResponseEntity.ok(departmentRepository.findAll());
    }

    // Get departments by hospital
    @GetMapping("/hospital/{hospitalId}")
    public ResponseEntity<?> getDepartmentsByHospital(@PathVariable Long hospitalId) {
        try {
            Hospital hospital = hospitalRepository.findById(hospitalId)
                    .orElseThrow(() -> new RuntimeException("Hospital not found"));
            List<Department> departments = hospital.getDepartments();
            return ResponseEntity.ok(departments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching departments: " + e.getMessage());
        }
    }
}
