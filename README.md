# Doc-Analyzer 📄

**AI-powered PDF document summarization service built with Java and Spring Boot.**

Doc-Analyzer extracts text from PDF files, sends the extracted content to Google Gemini for summarization, and stores document metadata and summaries in PostgreSQL.

## 🌐 Deployment

**API:** https://doc-analyzer-production-10c3.up.railway.app/api/docs/analyze

## ✨ What It Does

- Extracts PDF text using Apache PDFBox
- Generates concise summaries using Google Gemini 1.5 Flash
- Stores document metadata and summaries in PostgreSQL
- Validates uploaded files and handles processing/API failures gracefully
- Runs in a constrained cloud environment with JVM memory tuning

## 🏗️ Architecture

```text
PDF Upload
    |
    v
Spring Boot REST API
    |
    +----> PDFBox ------> Extracted Text
    |                         |
    |                         v
    |                    Gemini API
    |                         |
    v                         v
Document Service ------> Summary
    |
    v
PostgreSQL
```

## 🛠️ Tech Stack

| Component | Technology |
| --- | --- |
| Language | Java 17 |
| Framework | Spring Boot 3.x |
| AI | Google Gemini 1.5 Flash |
| Database | PostgreSQL |
| PDF Processing | Apache PDFBox |
| Persistence | JPA / Hibernate |
| Build Tool | Maven |
| Deployment | Railway |

## 🔧 Engineering Highlights

### Memory-efficient cloud deployment

The application is configured with a JVM heap limit (`-Xmx256m`) to operate reliably within a constrained cloud container and avoid out-of-memory failures during document processing.

### Secure configuration

API credentials are supplied through environment variables rather than being stored in source code. The repository also uses `.gitignore` to prevent local secrets and configuration files from being committed.

### Large text persistence

AI-generated summaries and extracted document content can be large, so the persistence layer uses PostgreSQL text storage through JPA/Hibernate rather than relying on a small fixed-length database column.

### Error handling and validation

The service validates uploaded documents and handles empty files and AI/API failures with controlled responses instead of allowing unhandled exceptions to terminate request processing.

## 📁 Project Structure

```text
doc-analyzer/
├── src/
│   ├── main/
│   │   ├── java/com/harsh/doc_analyzer/
│   │   │   ├── config/          # Gemini and HTTP client configuration
│   │   │   ├── controller/      # REST endpoints
│   │   │   ├── model/           # JPA entities
│   │   │   ├── repository/      # Data access layer
│   │   │   └── service/         # PDF processing, AI integration, business logic
│   │   └── resources/            # Application configuration
│   └── test/                     # Unit and integration tests
├── .gitignore
├── pom.xml
└── README.md
```

## 🚀 API Usage

**Endpoint**

`POST /api/docs/analyze`

**Request**

- Content-Type: `multipart/form-data`
- Form field: `file`
- Input: PDF document

**Example**

```bash
curl -X POST \
  https://doc-analyzer-production-10c3.up.railway.app/api/docs/analyze \
  -F "file=@sample.pdf"
```

## 🧪 Testing

The project includes unit and integration tests under `src/test`.

Before publishing additional performance or coverage figures, document the exact test command, coverage tool, test environment, and workload used so the results are reproducible.

## 🔐 Configuration

Set the required AI and database configuration through environment variables. Do not commit API keys, passwords, or other secrets to the repository.

## 🔮 Future Improvements

- OCR support for scanned PDFs
- Multi-language summaries
- Spring Security with JWT authentication
- Retrieval-Augmented Generation for chat with documents

## 👨‍💻 Author

**Harsh Shrivastava**

[GitHub](https://github.com/Harsh24-j) · [LinkedIn](https://linkedin.com/in/harshshrivastava24)
