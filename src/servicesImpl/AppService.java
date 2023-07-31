package servicesImpl;

import mapping.dtos.AppDto;
import java.util.List;
public interface AppService {
    AppDto createOrder(AppDto order);
    AppDto updateOrder(AppDto order);
    String removeOrder(Long orderId);
    List<AppDto> getAllOrders();
}
