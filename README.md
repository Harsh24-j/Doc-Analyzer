# 📄 Doc-Analyzer: AI-Powered Document Summarizer

**Doc-Analyzer** is a production-ready Spring Boot application that automates the process of extracting and summarizing content from PDF documents using the **Google Gemini 1.5 Flash AI** model. It features a robust backend to handle large files, extract text, and store metadata in a **PostgreSQL** database.

### 🌐 Live Deployment

**API Endpoint:** [https://doc-analyzer-production-10c3.up.railway.app/api/docs/analyze](https://doc-analyzer-production-10c3.up.railway.app/api/docs/analyze)

---

## 🚩 Problem Statement

Reading through lengthy PDF documents (research papers, legal contracts, reports) is time-consuming and inefficient. Key challenges include:

* **Manual Effort:** Manually extracting key points from 50+ page documents.
* **Information Overload:** Difficulty in identifying the core message quickly.
* **Scalability:** Existing tools often struggle with memory management when processing large files in a cloud environment.

---

## ✅ The Solution

I built a scalable backend service that provides an end-to-end automated pipeline:

1. **Text Extraction:** Leverages **Apache PDFBox** to convert raw PDF data into structured text.
2. **AI Orchestration:** Integrated **Google Gemini API** (v1beta) to generate concise 5-point summaries.
3. **Resilient Architecture:** Implemented memory-efficient JVM settings (`-Xmx256m`) to prevent crashes on cloud containers like **Railway**.
4. **Persistent Storage:** Metadata and summaries are stored in a **PostgreSQL** database using JPA/Hibernate for future retrieval.

---

## 🛠️ Tech Stack

| Component | Technology |
| --- | --- |
| **Language** | Java 17 |
| **Framework** | Spring Boot 3.x |
| **AI Model** | Google Gemini 1.5 Flash |
| **Database** | PostgreSQL |
| **PDF Library** | Apache PDFBox |
| **Deployment** | Railway (Cloud) |
| **Build Tool** | Maven |

---

📂 Project Structure

doc-analyzer/
├── src/
│   ├── main/
│   │   ├── java/com/harsh/doc_analyzer/
│   │   │   ├── config/           # API and RestTemplate Configurations
│   │   │   │   ├── GeminiConfigProperties.java
│   │   │   │   └── RestTemplateConfig.java
│   │   │   ├── controller/       # REST API Endpoints
│   │   │   │   └── DocumentController.java
│   │   │   ├── model/            # JPA Entities (PostgreSQL Schema)
│   │   │   │   └── DocumentMetadata.java
│   │   │   ├── repository/       # Data Access Layer
│   │   │   │   └── DocumentRepository.java
│   │   │   └── service/          # Business Logic & AI Integration
│   │   │       ├── AiService.java
│   │   │       ├── PdfService.java
│   │   │       └── DocumentService.java
│   │   └── resources/
│   │       ├── application.properties # App Config & Environment Placeholders
│   │       └── static/           # (Optional) Frontend files
│   └── test/                     # Unit and Integration Tests
├── .gitignore                    # Prevents sensitive files from being pushed
├── pom.xml                       # Project Dependencies (Maven)
└── README.md                     # Project Documentation

---

## 🚀 Key Features & Optimizations

* **Environment Variable Security:** Sensitive API keys are managed via Railway environment variables, preventing exposure in the source code.
* **Dynamic Column Mapping:** Uses `@Lob` and `columnDefinition = "TEXT"` in Hibernate to handle large AI-generated responses without data truncation.
* **Graceful Error Handling:** Includes a validation layer to check for empty files and API connection issues, returning clear feedback instead of server crashes.
* **Optimized Memory:** Fine-tuned JVM heap memory settings to run efficiently on low-resource cloud instances.

---

## 🔮 Future Scope & Enhancements (Sudhaar)

To make this project even more powerful, I plan to implement:

* **OCR Support:** Integrating Tesseract OCR to read text from scanned PDF images (non-selectable text).
* **Multi-language Support:** Enabling summaries in Hindi, Spanish, and other regional languages.
* **User Authentication:** Adding Spring Security with JWT so users can maintain a history of their uploaded documents.
* **Chat-with-PDF:** Utilizing RAG (Retrieval-Augmented Generation) to allow users to ask specific questions about the document instead of just a summary.

---

## 📖 How to Test

1. **Method:** `POST`
2. **URL:** `/api/docs/analyze`
3. **Body:** `form-data`
4. **Key:** `file` (Select a PDF file)
5. **Status:** `200 OK`

---
