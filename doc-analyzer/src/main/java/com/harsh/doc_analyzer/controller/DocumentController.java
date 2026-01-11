package com.harsh.doc_analyzer.controller;

import com.harsh.doc_analyzer.service.PdfService;
import com.harsh.doc_analyzer.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST Controller to handle document analysis requests.
 * Provides endpoints for uploading and processing PDF documents.
 */
@RestController
@RequestMapping("/api/docs")
public class DocumentController {

    @Autowired 
    private PdfService pdfService;
    
    @Autowired 
    private AiService aiService;

    /**
     * Endpoint to analyze an uploaded PDF document.
     * Extracts text and generates a summary using AI.
     * * @param file The PDF file provided by the user via multipart/form-data.
     * @return ResponseEntity containing the AI-generated summary or an error message.
     */
    @PostMapping("/analyze")
    public ResponseEntity<String> analyzeDocument(@RequestParam("file") MultipartFile file) {
        
        // Input Validation: Check if the uploaded file is empty
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Please select a valid file to upload.");
        }

        try {
            // Step 1: Extract text content from the PDF document
            String text = pdfService.extractText(file);
            
            // Step 2: Fetch the summary from AI service (Redis caching is integrated internally)
            String summary = aiService.getSummary(text);
            
            // Return the successful response with the generated summary
            return ResponseEntity.ok(summary);
            
        } catch (Exception e) {
            // Error Handling: Log and return internal server error details
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }
}