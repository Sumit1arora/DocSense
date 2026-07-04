package com.sumit.docsensex.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChunkingService {

    private static final int CHUNK_SIZE = 512;
    private static final int OVERLAP = 80;
    private static final int MIN_LENGTH = 20;

    public List<String> chunk(String text) {
        if (text == null || text.isBlank()) {
            return List.of();
        }

        List<String> chunks = new ArrayList<>();
        int start = 0;
        int prevEnd = 0;

        while (start < text.length()) {
            int end = Math.min(start + CHUNK_SIZE, text.length());
            String chunk = text.substring(start, end).trim();
            String newContent = text.substring(prevEnd, end).trim();

            if (chunk.length() >= MIN_LENGTH && newContent.length() >= MIN_LENGTH) {
                chunks.add(chunk);
            }

            prevEnd = end;
            start = start + CHUNK_SIZE - OVERLAP;
        }

        return chunks;
    }
}