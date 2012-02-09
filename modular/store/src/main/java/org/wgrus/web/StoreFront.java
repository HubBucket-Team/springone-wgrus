package org.wgrus.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.wgrus.store.services.StoreFrontService;

/**
 * Handles order requests.
 */
@Controller
@RequestMapping(value="/")
public class StoreFront {

  @Autowired
	private StoreFrontService storeFrontService;

	@RequestMapping(method=RequestMethod.GET)
	public String displayForm() {
		return "store";
	}

	@RequestMapping(method=RequestMethod.POST)
	public String placeOrder(@RequestParam String customerId, @RequestParam int quantity, @RequestParam String productId, Map<String, Object> model) {
		long orderId = storeFrontService.placeOrder(customerId, quantity, productId);
		model.put("orderId", orderId);
		return "store";
	}

}
