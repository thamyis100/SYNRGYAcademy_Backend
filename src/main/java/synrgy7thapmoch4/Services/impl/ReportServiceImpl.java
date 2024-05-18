package synrgy7thapmoch4.Services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import synrgy7thapmoch4.Entity.Order_Detail;
import synrgy7thapmoch4.Repository.OrderDetailRepository;
import synrgy7thapmoch4.Services.ReportService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<Order_Detail> getOrderDetailsByMerchantName(String merchantName) {
        List<Order_Detail> allOrderDetails = orderDetailRepository.findAll();

        // Log the size and some elements of the retrieved list
        if (allOrderDetails == null || allOrderDetails.isEmpty()) {
            logger.warn("No order details found in the database.");
        } else {
            logger.info("Retrieved {} order details from the database.", allOrderDetails.size());
            logger.debug("Sample order details: {}", allOrderDetails.stream().limit(5).collect(Collectors.toList()));
        }

        // Filter the order details by merchant name
        List<Order_Detail> filteredOrderDetails = allOrderDetails.stream()
                .filter(od -> od.getProduct_id() != null
                        && od.getProduct_id().getMerchant_id() != null
                        && od.getProduct_id().getMerchant_id().getName().equals(merchantName))
                .collect(Collectors.toList());

        // Log the result of filtering
        if (filteredOrderDetails.isEmpty()) {
            logger.warn("No order details found for merchant: '{}'", merchantName);
        } else {
            logger.info("Filtered order details for merchant '{}': {}", merchantName, filteredOrderDetails);
        }

        return filteredOrderDetails;
    }
}
