package synrgy7thapmoch4.Services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import synrgy7thapmoch4.Entity.Order;
import synrgy7thapmoch4.Entity.Order_Detail;
import synrgy7thapmoch4.Entity.Product;
import synrgy7thapmoch4.Repository.OrderDetailRepository;
import synrgy7thapmoch4.Repository.OrderRepository;
import synrgy7thapmoch4.Repository.ProductRepository;
import synrgy7thapmoch4.Services.OrderDetailService;
import synrgy7thapmoch4.utils.Response;

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
    public Map save(Order_Detail request) {
        if (request == null) {
            return response.error("Order_Detail request cannot be null.", 402);
        }
        if (request.getOrder_id() == null) {
            return response.error("Order is required.", 402);
        }
        if (request.getProduct_id() == null) {
            return response.error("Product is required.", 402);
        }
        if (request.getQuantity() <= 0) {
            return response.error("Quantity must be greater than zero.", 402);
        }

        // Ensure the Order and Product entities are managed
        Optional<Order> orderOpt = orderRepository.findById(request.getOrder_id().getId());
        if (!orderOpt.isPresent()) {
            return response.error("Order not found.", 404);
        }
        request.setOrder_id(orderOpt.get());

        Optional<Product> productOpt = productRepository.findById(request.getProduct_id().getId());
        if (!productOpt.isPresent()) {
            return response.error("Product not found.", 404);
        }
        request.setProduct_id(productOpt.get());

        // Calculate total price
        request.calculateTotalPrice();

        if (request.getTotal_price() <= 0) {
            return response.error("Total price must be greater than zero.", 402);
        }

        return response.sukses(orderDetailRepository.save(request));
    }
}
