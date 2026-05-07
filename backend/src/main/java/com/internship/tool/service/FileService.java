package com.internship.tool.service;

import com.internship.tool.entity.FileRecord;
import com.internship.tool.repository.FileRepository;
import com.internship.tool.exception.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {

    private final FileRepository repository;

    private final String UPLOAD_DIR = "uploads/";

    public FileService(FileRepository repository) {
        this.repository = repository;
    }

    public FileRecord upload(MultipartFile file) throws IOException {

        // 🔴 Validate size (<10MB)
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new ValidationException("File size must be less than 10MB");
        }

        // 🔴 Validate type
        if (!file.getContentType().startsWith("image") &&
                !file.getContentType().equals("application/pdf")) {
            throw new ValidationException("Only images and PDF allowed");
        }

        // 🔥 Generate UUID filename
        String storedName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        File dest = new File(UPLOAD_DIR + storedName);
        dest.getParentFile().mkdirs();

        file.transferTo(dest);

        // Save metadata
        FileRecord record = FileRecord.builder()
                .originalName(file.getOriginalFilename())
                .storedName(storedName)
                .fileType(file.getContentType())
                .size(file.getSize())
                .build();

        return repository.save(record);
    }

    public File getFile(Long id) {
        FileRecord record = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));

        return new File(UPLOAD_DIR + record.getStoredName());
    }
}
