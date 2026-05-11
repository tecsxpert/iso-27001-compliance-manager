package com.internship.tool.controller;

import com.internship.tool.entity.ComplianceRecord;
import com.internship.tool.service.ComplianceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/records")
public class ComplianceController {

    private final ComplianceService service;

    public ComplianceController(ComplianceService service) {
        this.service = service;
    }

    // ✅ CREATE RECORD
    @Operation(summary = "Create compliance record")
    @ApiResponse(responseCode = "200",
            description = "Record created successfully")
    @PostMapping
    public ComplianceRecord create(
            @RequestBody ComplianceRecord record) {

        return service.createRecord(record);
    }

    // ✅ GET ALL RECORDS
    @Operation(summary = "Get all compliance records")
    @ApiResponse(responseCode = "200",
            description = "Records fetched successfully")
    @GetMapping
    public Page<ComplianceRecord> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return service.getAllRecords(page, size);
    }

    // ✅ GET RECORD BY ID
    @Operation(summary = "Get compliance record by ID")
    @ApiResponse(responseCode = "200",
            description = "Record found")
    @GetMapping("/{id}")
    public ComplianceRecord getById(@PathVariable Long id) {

        return service.getRecordById(id);
    }

    // ✅ UPDATE RECORD
    @Operation(summary = "Update compliance record")
    @ApiResponse(responseCode = "200",
            description = "Record updated successfully")
    @PutMapping("/{id}")
    public ComplianceRecord update(
            @PathVariable Long id,
            @RequestBody ComplianceRecord record) {

        return service.updateRecord(id, record);
    }

    // ✅ DELETE RECORD
    @Operation(summary = "Delete compliance record")
    @ApiResponse(responseCode = "200",
            description = "Record deleted successfully")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {

        service.deleteRecord(id);

        return "Record deleted successfully";
    }
}