package org.springsource.samples.wgrus;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wgrus.Order;
import org.wgrus.store.services.StoreFrontService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:/*-logical-context.xml", "classpath*:/test-context.xml"})
public class WGrusLogicalIntegrationTest {

  @Autowired
  private StoreFrontService storeFrontService;

  private MongoTemplate mongoTemplate;

  @Autowired
  public void setMongoDbFactory(MongoDbFactory mongoDbFactory) {
    this.mongoTemplate = new MongoTemplate(mongoDbFactory);
  }

  
  @Test
  public void testSomething() throws InterruptedException {
    long orderId = storeFrontService.placeOrder("2", 10, "widget");
    System.out.println("OrderID=" + orderId);
    for (int i = 0; i < 10; i++) {
      Order order = mongoTemplate.findOne(new Query(), Order.class, "rejects");
      if (order != null) {
        System.out.println("Slept for : " + i);
        Assert.assertEquals("2", order.getCustomerId());
        return;
      }
      TimeUnit.SECONDS.sleep(1);
    }
    
    Assert.fail("rejected order not found");
  }
}
