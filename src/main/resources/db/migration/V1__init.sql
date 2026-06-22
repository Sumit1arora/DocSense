-- src/main/resources/db/migration/V1__init.sql

-- Install pgvector extension (pre-installed in our Docker image)
CREATE EXTENSION IF NOT EXISTS vector;

-- Tenants: one row per API client
-- api_key_hash stores SHA-256 of the real key (never store plaintext keys)
-- api_key_prefix is shown to users for identification (e.g. "ds_Xk3mP9")
CREATE TABLE tenants (
                         id             UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
                         name           VARCHAR(255) NOT NULL,
                         api_key_hash   VARCHAR(64)  NOT NULL UNIQUE,
                         api_key_prefix VARCHAR(12)  NOT NULL,
                         is_active      BOOLEAN      NOT NULL DEFAULT true,
                         created_at     TIMESTAMP    NOT NULL DEFAULT now()
);

-- Document chunks: each PDF becomes N chunks, each chunk has an embedding
-- document_id references MongoDB's ObjectId (stored as string)
-- embedding vector(1536) stores the OpenAI float array
CREATE TABLE document_chunks (
                                 id           UUID     PRIMARY KEY DEFAULT gen_random_uuid(),
                                 document_id  VARCHAR(36)  NOT NULL,
                                 tenant_id    UUID         NOT NULL REFERENCES tenants(id),
                                 content      TEXT         NOT NULL,
                                 embedding    vector(1536),
                                 chunk_index  INTEGER      NOT NULL,
                                 created_at   TIMESTAMP    NOT NULL DEFAULT now()
);

-- Index for fast tenant-scoped lookups
CREATE INDEX idx_chunks_tenant   ON document_chunks(tenant_id);
CREATE INDEX idx_chunks_document ON document_chunks(document_id);

-- IVFFlat index for approximate nearest-neighbor vector search
-- lists=100 works well for up to ~1M vectors; increase for larger datasets
CREATE INDEX idx_chunks_embedding ON document_chunks
    USING ivfflat (embedding vector_cosine_ops)
    WITH (lists = 100);