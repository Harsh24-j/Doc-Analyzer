package com.harsh.doc_analyzer.controller;

import com.harsh.doc_analyzer.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/docs")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping("/analyze")
    public ResponseEntity<String> analyze(@RequestParam("file") MultipartFile file) {
        String result = documentService.analyzeDocument(file);
        return ResponseEntity.ok(result);
    }
}