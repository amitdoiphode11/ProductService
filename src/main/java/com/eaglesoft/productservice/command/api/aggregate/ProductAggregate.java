package com.eaglesoft.productservice.command.api.aggregate;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.eaglesoft.productservice.command.api.command.CreateProductCommand;
import com.eaglesoft.productservice.command.api.events.ProductCreatedEvents;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.beans.beancontext.BeanContext;
import java.math.BigDecimal;

@Aggregate
public class ProductAggregate {
    @AggregateIdentifier
    private String productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;

    public ProductAggregate() {
    }

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand) {
        ProductCreatedEvents productCreatedEvents = new ProductCreatedEvents();
        BeanUtils.copyProperties(createProductCommand, productCreatedEvents);

        AggregateLifecycle.apply(productCreatedEvents);
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvents productCreatedEvents) {
        this.quantity = productCreatedEvents.getQuantity();
        this.productId = productCreatedEvents.getProductId();
        this.name = productCreatedEvents.getName();
        this.price = productCreatedEvents.getPrice();
    }
}
