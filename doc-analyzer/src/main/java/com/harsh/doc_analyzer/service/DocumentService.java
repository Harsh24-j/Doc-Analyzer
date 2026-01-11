package com.harsh.doc_analyzer.service;

import com.harsh.doc_analyzer.model.DocumentMetadata;
import com.harsh.doc_analyzer.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
public class DocumentService {

    @Autowired
    private PdfService pdfService;

    @Autowired
    private AiService aiService;

    @Autowired
    private DocumentRepository documentRepository;

    public String analyzeDocument(MultipartFile file) {
    try {
        String text = pdfService.extractText(file);
        String summary = aiService.getSummary(text);
        
        // --- YE CHECK ADD KARO ---
        if (summary != null && !summary.startsWith("Error") && !summary.contains("failed")) {
            DocumentMetadata metadata = new DocumentMetadata();
            metadata.setFileName(file.getOriginalFilename());
            metadata.setSummary(summary);
            metadata.setUploadDate(LocalDateTime.now());
            documentRepository.save(metadata);
        }
        return summary;
    } catch (Exception e) {
        return "Error: " + e.getMessage();
    }
}
}
