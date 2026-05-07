package com.internship.tool.repository;

import com.internship.tool.entity.ComplianceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplianceRepository extends JpaRepository<ComplianceRecord, Long> {
}