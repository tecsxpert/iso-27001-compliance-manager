package com.internship.tool.repository;

import com.internship.tool.entity.FileRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileRecord, Long> {
}
