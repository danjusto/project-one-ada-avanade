package com.ada_avanada.project_one.service;

import com.ada_avanada.project_one.dto.ProductDTO;
import com.ada_avanada.project_one.entity.Product;
import com.ada_avanada.project_one.repository.ProductRepository;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.SECONDS;

@Service
public class FeedDbService {
    private ProductRepository productRepository;
    public FeedDbService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public Boolean productTableEmpty() {
        return this.productRepository.findAll().isEmpty();
    }
    public void feedDb() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://dummyjson.com/products/search?q=phone"))
                .timeout(Duration.of(10, SECONDS))
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject json = new JSONObject(response.body());
        var listOfProducts = prepareListProducts(json);
        for (var product : listOfProducts) {
            this.productRepository.save(new Product(product));
        }
    }

    private List<ProductDTO> prepareListProducts (JSONObject json) {
        var listProducts = new ArrayList<ProductDTO>();
        for (int i = 0; i < json.getJSONArray("products").length(); i++) {
            var product = json.getJSONArray("products").getJSONObject(i);
            listProducts.add(new ProductDTO(
                    null,
                    product.getString("title"),
                    product.getString("description"),
                    product.getBigInteger("price").multiply(BigInteger.valueOf(100)),
                    product.getInt("stock"),
                    product.getString("brand"),
                    product.getString("category")));
        }
        return listProducts;
    }
}
