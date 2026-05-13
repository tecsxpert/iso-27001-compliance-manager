package com.internship.tool.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "compliance_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ComplianceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ Validation
    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;

    // ✅ Validation
    @NotBlank(message = "Description is required")
    @Column(length = 1000)
    private String description;

    // ✅ Validation
    @NotBlank(message = "Status is required")
    @Column(nullable = false)
    private String status; // OPEN, IN_PROGRESS, CLOSED

    // ✅ Validation
    @NotBlank(message = "Category is required")
    @Column(nullable = false)
    private String category;

    // ✅ Validation
    @NotNull(message = "Score is required")
    @Min(value = 0, message = "Score cannot be below 0")
    @Max(value = 100, message = "Score cannot exceed 100")
    @Column(nullable = false)
    private Integer score;

    // ✅ Validation
    @NotNull(message = "Due date is required")
    @Column(nullable = false)
    private LocalDateTime dueDate;

    // 🔹 Auditing Fields
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}