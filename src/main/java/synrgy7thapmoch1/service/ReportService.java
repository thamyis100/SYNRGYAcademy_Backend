package synrgy7thapmoch1.service;



import synrgy7thapmoch1.entity.OrderDetail;

import java.util.List;

public interface ReportService {
    List<OrderDetail> getOrderDetailsByMerchantName(String merchantName);
}
