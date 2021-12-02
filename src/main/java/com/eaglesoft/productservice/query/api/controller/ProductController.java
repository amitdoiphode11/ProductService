package com.eaglesoft.productservice.query.api.controller;

import com.eaglesoft.productservice.command.api.model.ProductRestModel;
import com.eaglesoft.productservice.query.api.queries.GetProductQuery;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {


    private QueryGateway queryGateway;
    public ProductController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    public List<ProductRestModel> getAllProducts(){
        GetProductQuery getProductQuery = new GetProductQuery();
        List<ProductRestModel> productRestModels =
                queryGateway
                        .query(getProductQuery,
                                ResponseTypes.multipleInstancesOf(ProductRestModel.class)).join();


        return productRestModels;
    }
}
