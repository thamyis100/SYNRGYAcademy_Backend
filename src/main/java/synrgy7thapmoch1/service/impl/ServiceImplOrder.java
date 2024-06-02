package synrgy7thapmoch1.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import synrgy7thapmoch1.entity.Order;
import synrgy7thapmoch1.entity.UserOld;
import synrgy7thapmoch1.repository.OrderRepository;
import synrgy7thapmoch1.repository.UserRepositoryOld;
import synrgy7thapmoch1.service.OrderService;
import synrgy7thapmoch1.utils.Response;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceImplOrder implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(ServiceImplOrder.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepositoryOld userRepository;

    @Autowired
    private Response response;

    @Override
    public Map<String, Object> save(Order request) {
        try {
            UserOld managedUser = userRepository.findById(request.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            request.setUser(managedUser);

            if (response.chekNull(request.getOrder_time())) {
                return response.error("Order Time is required.", 402);
            }

            if (response.chekNull(request.getDestination_address())) {
                return response.error("Destination address is required.", 402);
            }

            if (response.chekNull(request.getCompleted())) {
                return response.error("Completed status is required.", 402);
            }

            Order savedOrder = orderRepository.save(request);
            return response.sukses(savedOrder);
        } catch (Exception e) {
            logger.error("Error saving order", e);
            return response.error("Failed to save order.", 500);
        }
    }

    @Override
    public Map edit(Order request) {
        try{
            if(request.getId() == null){
                return response.error("id_order is required.", 402);
            }

            Optional<Order> getOrder = orderRepository.findById(request.getId());
            if(getOrder.isEmpty()){
                return response.error("id_order not found", 404);
            }

            Order editOrder = getOrder.get();
            if (request.getOrder_time() != null) {
                editOrder.setOrder_time(request.getOrder_time());
            }
            if (request.getDestination_address()!= null) {
                editOrder.setDestination_address(request.getDestination_address());
            }
            if (request.getCompleted() != null) {
                editOrder.setCompleted(request.getCompleted());
            }
            if (request.getUser() != null) {
                editOrder.setUser(request.getUser());
            }

            return response.sukses(orderRepository.save(editOrder));
        } catch (Exception e) {
            logger.error("Error while editing order_id: {}", e.getMessage());
            return response.error("Failed to edit order_id", 500);
        }
    }

    @Override
    public Map delete(UUID id) {
        try {
            Optional<Order> orderOptional = orderRepository.findById(id);
            if (orderOptional.isPresent()) {
                orderRepository.delete(orderOptional.get());
                return response.sukses("Order deleted successfully");
            } else {
                return response.error("Order not found", 404);
            }
        } catch (Exception e) {
            logger.error("Error while deleting order_id: {}", e.getMessage());
            return response.error("Failed to delete order_id", 500);
        }
    }

    @Override
    public Map getById(UUID id) {
        try{
            Optional<Order> getOrder = orderRepository.findById(id);
            if(getOrder.isEmpty()){
                return response.error("id_order not found", 404);
            }
            return response.sukses(getOrder.get());
        } catch (Exception e) {
            logger.error("Error occurred while fetching order_id by ID: {}", e.getMessage());
            return response.error("Failed to fetch order_id by ID", 500);
        }
    }

    @Override
    public Map pagination(int page, int size) {
        try{
            Pageable pageable = PageRequest.of(page, size);
            Page<Order> listOrder = orderRepository.findAll(pageable);
            return response.sukses(listOrder);
        } catch (Exception e) {
            logger.error("Error occurred while paginating orders: {}", e.getMessage());
            return response.error("Failed to paginate orders", 500);
        }
    }
}

