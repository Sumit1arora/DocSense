package com.sumit.docsensex.service;

import com.sumit.docsensex.config.OpenAiConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class EmbeddingService {

    private final RestTemplate restTemplate;
    private final OpenAiConfig openAiConfig;
    public EmbeddingService(@Qualifier("openAIRestTemplate") RestTemplate restTemplate, OpenAiConfig openAiConfig) {
        this.restTemplate = restTemplate;
        this.openAiConfig = openAiConfig;
    }

    public float[] embed(String text)
    {
        String url = openAiConfig.getBaseUrl() + "/v1/embeddings";

        Map<String,Object> requestBody = Map.of("model" , "text-embedding-3-small","input",text);

        Map<String,Object> response = restTemplate.postForObject(url, requestBody, Map.class);

        if (response == null)
        {
            throw new IllegalStateException("Empty response from OpenAi service");
        }
        List<Map<String, Object>> data = (List<Map<String, Object>>) response.get("data");
        List<Double> embeddingsDoubles = (List<Double>) data.get(0).get("embedding");
        float[] embeddings = new float[embeddingsDoubles.size()];
        for (int i = 0; i < embeddingsDoubles.size(); i++)
        {
            embeddings[i] = embeddingsDoubles.get(i).floatValue();

        }
        return embeddings;

    }
}
