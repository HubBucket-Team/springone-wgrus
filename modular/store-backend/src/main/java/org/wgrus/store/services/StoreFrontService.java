package org.wgrus.store.services;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.wgrus.Order;

@Service
public class StoreFrontService {
  public AtomicLong orderIdCounter;
  public MessagingTemplate messagingTemplate;

  public StoreFrontService() {
    this.orderIdCounter =  new AtomicLong(1);
  }
  
  @Autowired
  public void setOrderChannel(@Qualifier("orderChannel") MessageChannel orderChannel) {
    this.messagingTemplate = new MessagingTemplate(orderChannel);
  }

  public long placeOrder(String customerId, int quantity, String productId) {
    long orderId = orderIdCounter.getAndIncrement();
  	Order order = new Order();
  	order.setId(orderId);
  	order.setCustomerId(customerId);
  	order.setQuantity(quantity);
  	order.setProductId(productId);
  	messagingTemplate.send(MessageBuilder.withPayload(order).build());
    return orderId;
  }

  
}