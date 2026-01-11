package com.harsh.doc_analyzer.controller;

import com.harsh.doc_analyzer.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/docs")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    // --- YE NYA CODE ADD KARO (Health Check) ---
    // Jab koi browser mein link kholega, use ye dikhega (GET Request)
    @GetMapping("/analyze")
    public String healthCheck() {
        return "✅ Doc-Analyzer API is Live! Please use Postman to send a POST request with a PDF file to this URL.";
    }
    // -------------------------------------------

    @PostMapping("/analyze")
    public ResponseEntity<String> analyzeDocument(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(documentService.analyzeDocument(file));
    }
}