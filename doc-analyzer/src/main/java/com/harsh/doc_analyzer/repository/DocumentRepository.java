package com.harsh.doc_analyzer.repository;

import com.harsh.doc_analyzer.model.DocumentMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for DocumentMetadata entity.
 * JpaRepository provides standard methods for CRUD operations.
 */
@Repository
public interface DocumentRepository extends JpaRepository<DocumentMetadata, Long> {
    // Aap yahan custom methods bhi add kar sakte hain, jaise:
    // List<DocumentMetadata> findByFileName(String fileName);
}