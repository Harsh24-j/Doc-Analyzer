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
            // 1. PDF se text nikalo
            String text = pdfService.extractText(file);
            
            // 2. Gemini AI se summary mangwao
            String summary = aiService.getSummary(text);
            
            // 3. Database mein save karne ke liye object banao
            DocumentMetadata metadata = new DocumentMetadata();
            metadata.setFileName(file.getOriginalFilename());
            metadata.setSummary(summary);
            metadata.setUploadDate(LocalDateTime.now());
            
            // 4. Finally Database mein SAVE karo
            documentRepository.save(metadata);
            
            return summary;
        } catch (Exception e) {
            return "Error analyzing document: " + e.getMessage();
        }
    }
}
