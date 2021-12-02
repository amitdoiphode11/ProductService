package com.eaglesoft.productservice.command.api.events;

import com.eaglesoft.productservice.command.api.data.Product;
import com.eaglesoft.productservice.command.api.data.ProductRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product")
public class ProductEventHandler {

    private ProductRepository repository;

    public ProductEventHandler(ProductRepository repository) {
        this.repository = repository;
    }


    @EventSourcingHandler
    public void on(ProductCreatedEvents events){
        Product product = new Product();
        BeanUtils.copyProperties(events,product);
        repository.save(product);
    }

    @ExceptionHandler
    public void handle(Exception exception) throws Exception {
        throw exception;
    }
}
