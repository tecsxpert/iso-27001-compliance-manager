package com.internship.tool.controller;

import com.internship.tool.entity.ComplianceRecord;
import com.internship.tool.service.ComplianceService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/records")
public class ComplianceController {

    private final ComplianceService service;

    public ComplianceController(ComplianceService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    public ComplianceRecord create(@RequestBody ComplianceRecord record) {
        return service.createRecord(record);
    }

    // GET ALL (PAGINATION)
    @GetMapping
    public Page<ComplianceRecord> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return service.getAllRecords(page, size);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ComplianceRecord getById(@PathVariable Long id) {
        return service.getRecordById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ComplianceRecord update(@PathVariable Long id,
                                   @RequestBody ComplianceRecord record) {
        return service.updateRecord(id, record);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteRecord(id);
        return "Record deleted successfully";
    }
}