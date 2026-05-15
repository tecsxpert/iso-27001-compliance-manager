package com.internship.tool.config;

import com.internship.tool.entity.ComplianceRecord;
import com.internship.tool.repository.ComplianceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final ComplianceRepository repository;

    public DataLoader(ComplianceRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {

        // ✅ Prevent duplicate seeding
        if (repository.count() > 0) {
            return;
        }

        List<ComplianceRecord> records = List.of(

                ComplianceRecord.builder()
                        .title("ISO Internal Audit")
                        .description("Quarterly compliance audit")
                        .status("OPEN")
                        .category("SECURITY")
                        .score(92)
                        .dueDate(LocalDateTime.now().plusDays(7))
                        .build(),

                ComplianceRecord.builder()
                        .title("Risk Assessment")
                        .description("Infrastructure risk analysis")
                        .status("IN_PROGRESS")
                        .category("RISK")
                        .score(76)
                        .dueDate(LocalDateTime.now().plusDays(14))
                        .build(),

                ComplianceRecord.builder()
                        .title("Access Control Review")
                        .description("User privilege validation")
                        .status("CLOSED")
                        .category("ACCESS")
                        .score(88)
                        .dueDate(LocalDateTime.now().plusDays(10))
                        .build()

        );

        // 🔥 Duplicate records to simulate 30 entries
        for (int i = 0; i < 10; i++) {
            repository.saveAll(records);
        }

        System.out.println("✅ Demo data seeded successfully");
    }
}
