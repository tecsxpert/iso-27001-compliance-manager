package com.internship.tool.service;

import com.internship.tool.entity.FileRecord;
import com.internship.tool.exception.ValidationException;
import com.internship.tool.repository.FileRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    private final FileRepository repository;

    private final String UPLOAD_DIR = "uploads/";

    public FileService(FileRepository repository) {
        this.repository = repository;
    }

    // ✅ Upload File
    public FileRecord upload(MultipartFile file) throws IOException {

        // 🔴 Check Empty File
        if (file == null || file.isEmpty()) {
            throw new ValidationException("Please select a file");
        }

        // 🔴 Validate File Size (<10MB)
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new ValidationException("File size must be less than 10MB");
        }

        // 🔴 Safe Content Type Validation
        String contentType = file.getContentType();

        if (contentType == null) {
            throw new ValidationException("File type is missing");
        }

        // 🔴 Allow Only Images + PDF
        if (!contentType.startsWith("image/")
                && !contentType.equals("application/pdf")) {

            throw new ValidationException(
                    "Only image and PDF files are allowed"
            );
        }

        // ✅ Generate UUID File Name
        String storedName =
                UUID.randomUUID() + "_" + file.getOriginalFilename();

        // ✅ Create Upload Directory
        File directory = new File(UPLOAD_DIR);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        // ✅ Save File
        File dest =
                new File(UPLOAD_DIR + storedName);

        file.transferTo(dest);

        // ✅ Save Metadata
        FileRecord record =
                FileRecord.builder()
                        .originalName(file.getOriginalFilename())
                        .storedName(storedName)
                        .fileType(contentType)
                        .size(file.getSize())
                        .build();

        return repository.save(record);
    }

    // ✅ Download File
    public Resource getFile(Long id) {

        try {

            FileRecord record =
                    repository.findById(id)
                            .orElseThrow(() ->
                                    new RuntimeException("File not found"));

            Path path =
                    Paths.get(UPLOAD_DIR)
                            .resolve(record.getStoredName());

            Resource resource =
                    new UrlResource(path.toUri());

            if (resource.exists()) {
                return resource;
            }

            throw new RuntimeException("File not found");

        } catch (MalformedURLException e) {

            throw new RuntimeException(
                    "Error reading file"
            );
        }
    }
}