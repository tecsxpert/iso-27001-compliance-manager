package com.internship.tool.service;

import com.internship.tool.entity.ComplianceRecord;
import com.internship.tool.exception.ResourceNotFoundException;
import com.internship.tool.repository.ComplianceRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ComplianceServiceTest {

    @Mock
    private ComplianceRepository repository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private ComplianceService service;

    private ComplianceRecord record;

    @BeforeEach
    void setup() {

        MockitoAnnotations.openMocks(this);

        record = ComplianceRecord.builder()
                .id(1L)
                .title("ISO Audit")
                .description("Security audit")
                .status("OPEN")
                .category("SECURITY")
                .score(90)
                .dueDate(LocalDateTime.now())
                .build();
    }

    @Test
    void testCreateRecord() {

        when(repository.save(any(ComplianceRecord.class)))
                .thenReturn(record);

        ComplianceRecord saved = service.createRecord(record);

        assertNotNull(saved);
        assertEquals("ISO Audit", saved.getTitle());

        verify(repository, times(1)).save(record);
    }

    @Test
    void testGetAllRecords() {

        Page<ComplianceRecord> page =
                new PageImpl<>(List.of(record));

        when(repository.findAll(any(PageRequest.class)))
                .thenReturn(page);

        Page<ComplianceRecord> result =
                service.getAllRecords(0, 10);

        assertEquals(1, result.getContent().size());
    }

    @Test
    void testGetRecordById() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(record));

        ComplianceRecord found =
                service.getRecordById(1L);

        assertEquals("ISO Audit", found.getTitle());
    }

    @Test
    void testRecordNotFound() {

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            service.getRecordById(1L);
        });
    }

    @Test
    void testDeleteRecord() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(record));

        service.deleteRecord(1L);

        verify(repository, times(1)).delete(record);
    }
}