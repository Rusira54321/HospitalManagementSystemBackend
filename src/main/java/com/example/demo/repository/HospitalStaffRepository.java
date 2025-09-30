package com.example.demo.repository;

import com.example.demo.model.HospitalStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalStaffRepository extends JpaRepository<HospitalStaff,Long> {
}
