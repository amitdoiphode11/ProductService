package com.eaglesoft.productservice.query.api.projection;

import com.eaglesoft.productservice.command.api.data.Product;
import com.eaglesoft.productservice.command.api.data.ProductRepository;
import com.eaglesoft.productservice.command.api.model.ProductRestModel;
import com.eaglesoft.productservice.query.api.queries.GetProductQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductProjection {

    private ProductRepository repository;

    public ProductProjection(ProductRepository repository) {
        this.repository = repository;
    }


    @QueryHandler
    public List<ProductRestModel> handle(GetProductQuery getProductQuery) {
        List<Product> products = repository.findAll();

        List<ProductRestModel> productRestModels = products.stream().map(product ->
                ProductRestModel.builder()
                        .name(product.getName())
                        .price(product.getPrice())
                        .quantity(product.getQuantity())
                        .build()).collect(Collectors.toList());
        return productRestModels;
    }
}
