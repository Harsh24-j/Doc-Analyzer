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
     * @param file The PDF file uploaded via Multipart request.
     * @return Extracted text as a String.
     * @throws IOException If there is an error during file reading.
     */
    public String extractText(MultipartFile file) throws IOException {
        
        // Loads the PDF document from the input stream of the uploaded file
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            
            PDFTextStripper stripper = new PDFTextStripper();
            
            // Extracts and returns the entire text content in String format
            return stripper.getText(document);
        }
    }
}