package com.internship.tool.scheduler;

import com.internship.tool.entity.ComplianceRecord;
import com.internship.tool.repository.ComplianceRepository;
import com.internship.tool.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReminderScheduler {

    @Autowired
    private ComplianceRepository repository;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 */1 * * * ?") // every 1 minute (for testing)
    public void checkOverdueRecords() {

        List<ComplianceRecord> records = repository.findAll();

        for (ComplianceRecord record : records) {
            if (record.getDueDate() != null &&
                    record.getDueDate().isBefore(LocalDateTime.now())) {

                emailService.sendEmail(
                        "test@gmail.com",
                        "Overdue Record Alert",
                        "Record overdue: " + record.getTitle()
                );
            }
        }
    }
}