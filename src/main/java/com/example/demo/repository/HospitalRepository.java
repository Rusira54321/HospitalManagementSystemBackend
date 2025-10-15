package com.example.demo.repository;

import com.example.demo.model.Doctor;
import com.example.demo.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital,Long>{
    Hospital findByHospitalId(Long id);
    Hospital findByDoctors(Doctor doctor);
}
