package com.internship.tool.service;

import com.internship.tool.entity.ComplianceRecord;
import com.internship.tool.exception.ResourceNotFoundException;
import com.internship.tool.exception.ValidationException;
import com.internship.tool.repository.ComplianceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ComplianceService {

    private final ComplianceRepository repository;
    private final EmailService emailService;

    public ComplianceService(ComplianceRepository repository,
                             EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }

    // CREATE
    public ComplianceRecord createRecord(ComplianceRecord record) {
        validate(record);
        ComplianceRecord saved = repository.save(record);

        // Email (safe - won't crash)
        emailService.sendEmail(
                "test@gmail.com",
                "New Compliance Record Created",
                "Record created: " + saved.getTitle()
        );

        return saved;
    }

    // GET ALL (PAGINATION)
    public Page<ComplianceRecord> getAllRecords(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

    // GET BY ID
    public ComplianceRecord getRecordById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found"));
    }

    // UPDATE
    public ComplianceRecord updateRecord(Long id, ComplianceRecord updated) {
        ComplianceRecord existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found"));

        validate(updated);

        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setStatus(updated.getStatus());
        existing.setCategory(updated.getCategory());
        existing.setScore(updated.getScore());
        existing.setDueDate(updated.getDueDate());

        return repository.save(existing);
    }

    // DELETE
    public void deleteRecord(Long id) {
        ComplianceRecord existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found"));

        repository.delete(existing);
    }

    // VALIDATION
    private void validate(ComplianceRecord record) {
        if (record.getTitle() == null || record.getTitle().isEmpty()) {
            throw new ValidationException("Title is required");
        }
        if (record.getScore() == null || record.getScore() < 0) {
            throw new ValidationException("Invalid score");
        }
    }
}