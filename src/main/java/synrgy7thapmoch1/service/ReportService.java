package synrgy7thapmoch1.service;

import com.example.challenge6.entity.OrderDetail;

import java.util.List;

public interface ReportService {
    List<OrderDetail> getOrderDetailsByMerchantName(String merchantName);
}
