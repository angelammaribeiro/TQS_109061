package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Optional;

public class ProductFinderService {
    private ISimpleHttpClient httpClient;
    private ObjectMapper objectMapper = new ObjectMapper();

    public ProductFinderService(ISimpleHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Optional<Product> findProductDetails(int id) throws IOException {
        String url = "https://fakestoreapi.com/products/" + id;
        String jsonResponse = httpClient.doHttpGet(url);

        if (jsonResponse == null || jsonResponse.isEmpty()) {
            return Optional.empty();
        }

        try {
            Product product = objectMapper.readValue(jsonResponse, Product.class);
            return Optional.of(product);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}