package com.harsh.doc_analyzer.controller;

import com.harsh.doc_analyzer.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller class to handle API requests for document analysis.
 * Endpoint: /api/docs/analyze
 */
@RestController
@RequestMapping("/api/docs")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    /**
     * Handles PDF upload and triggers summarization logic.
     * @param file The PDF file from multipart form-data.
     * @return Summarized text or error message.
     */
    @PostMapping("/analyze")
    public ResponseEntity<String> analyze(@RequestParam("file") MultipartFile file) {
        
        // Validation: Check if file is empty
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error: Please select a file to upload.");
        }

        // Calling the service to perform extraction, AI summary, and DB save
        String result = documentService.analyzeDocument(file);

        // If the result contains an error message from AI/DB
        if (result.startsWith("Error") || result.contains("failed")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(result);
        }

        return ResponseEntity.ok(result);
    }
}