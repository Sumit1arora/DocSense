package com.sumit.docsensex.service;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ChunkingServiceTest {

    private final ChunkingService service = new ChunkingService();

    @Test
    void splitsTextIntoChunksOfCorrectSize() {
        String text = "A".repeat(600);  // 600 chars → 2 chunks (512, then overlap-adjusted)
        List<String> chunks = service.chunk(text);
        assertThat(chunks).hasSize(2);
        assertThat(chunks.get(0).length()).isEqualTo(512);
    }

    @Test
    void overlapsChunks() {
        String text = "A".repeat(512) + "B".repeat(100); // 612 chars total
        List<String> chunks = service.chunk(text);
        // Second chunk starts at 512-80=432, includes last 80 A's + all B's
        assertThat(chunks.get(1)).startsWith("A".repeat(80));
    }

    @Test
    void skipsShortChunks() {
        String text = "A".repeat(512) + "   "; // trailing whitespace < 20 chars
        List<String> chunks = service.chunk(text);
        assertThat(chunks).hasSize(1);
    }

    @Test
    void returnsEmptyForBlankText() {
        List<String> chunks = service.chunk("   ");
        assertThat(chunks).isEmpty();
    }
}
