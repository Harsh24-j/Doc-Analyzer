package com.harsh.doc_analyzer.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class PdfService {

    /**
     * Extracts text content from the uploaded PDF file.
     * Includes validation to prevent NullPointerExceptions.
     */
    public String extractText(MultipartFile file) throws IOException {
        
        // --- VALIDATION START ---
        // Agar Postman mein 'file' select nahi ki, toh ye error throw karega
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is missing or empty! Please select a valid PDF in Postman.");
        }
        // --- VALIDATION END ---

        // Loads the PDF document from the input stream of the uploaded file
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            
            PDFTextStripper stripper = new PDFTextStripper();
            
            // Extracts the text content
            String extractedText = stripper.getText(document);
            
            // Safety check: Agar PDF scan image hai aur text nahi nikal paya
            if (extractedText == null || extractedText.trim().isEmpty()) {
                return "Warning: No readable text found in the PDF.";
            }
            
            return extractedText;
        } catch (IOException e) {
            throw new IOException("Error processing PDF file: " + e.getMessage());
        }
    }
}