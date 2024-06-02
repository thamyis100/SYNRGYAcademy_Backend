package synrgy7thapmoch1.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import synrgy7thapmoch1.entity.Order;
import synrgy7thapmoch1.entity.OrderDetail;
import synrgy7thapmoch1.entity.Product;
import synrgy7thapmoch1.repository.OrderDetailRepository;
import synrgy7thapmoch1.repository.OrderRepository;
import synrgy7thapmoch1.repository.ProductRepository;
import synrgy7thapmoch1.service.OrderDetailService;
import synrgy7thapmoch1.utils.Response;

import java.util.Map;
import java.util.Optional;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private Response response;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Map save(OrderDetail request) {
        if (request == null) {
            return response.error("Order_Detail request cannot be null.", 402);
        }
        if (request.getOrder() == null) {
            return response.error("Order is required.", 402);
        }
        if (request.getProduct() == null) {
            return response.error("Product is required.", 402);
        }
        if (request.getQuantity() <= 0) {
            return response.error("Quantity must be greater than zero.", 402);
        }

        // Ensure the Order and Product entities are managed
        Optional<Order> orderOpt = orderRepository.findById(request.getOrder().getId());
        if (!orderOpt.isPresent()) {
            return response.error("Order not found.", 404);
        }
        request.setOrder(orderOpt.get());

        Optional<Product> productOpt = productRepository.findById(request.getProduct().getId());
        if (!productOpt.isPresent()) {
            return response.error("Product not found.", 404);
        }
        request.setProduct(productOpt.get());

        // Calculate total price
        request.calculateTotalPrice();

        if (request.getTotal_price() <= 0) {
            return response.error("Total price must be greater than zero.", 402);
        }

        return response.sukses(orderDetailRepository.save(request));
    }
}
