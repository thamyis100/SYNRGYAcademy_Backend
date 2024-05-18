package synrgy7thapmoch4.Services;

import synrgy7thapmoch4.Entity.Order_Detail;

import java.util.List;

public interface ReportService {
    List<Order_Detail> getOrderDetailsByMerchantName(String merchantName);
}
