# DocSense

A multi-tenant REST API for uploading PDF documents, processing them into vector embeddings, and asking questions using AI-generated answers with source citations.

## Tech Stack

* Java 17
* Spring Boot 3.x
* PostgreSQL + pgvector
* MongoDB
* Redis
* Docker
* OpenAI Embeddings & LLM

## Features

* 📄 PDF document upload and processing
* 🔎 Semantic search using vector embeddings
* 🤖 AI-powered question answering
* 📚 Answers with document source citations
* 🔐 API key authentication
* 👥 Multi-tenant architecture
* ⚡ Async document processing
* 🚦 Redis-based rate limiting
* 🔔 Optional webhooks for processing status

## Architecture

```text
Client
  ↓
Spring Boot REST API
  ↓
Document Processing → PDF → Chunks → Embeddings
  ↓
PostgreSQL + pgvector
  ↓
Semantic Search → LLM → Answer + Sources
```

## Setup

### 1. Start services

```bash
docker-compose up postgres mongodb redis -d
```

### 2. Configure OpenAI

```bash
export OPENAI_API_KEY=your_api_key
```

### 3. Run the application

```bash
./mvnw spring-boot:run
```

API runs at:

```text
http://localhost:8080/api/v1
```

## Basic Flow

1. Create a tenant and receive an API key.
2. Upload a PDF.
3. Wait for document processing to complete.
4. Ask questions about the uploaded documents.
5. Receive an AI-generated answer with relevant sources.


