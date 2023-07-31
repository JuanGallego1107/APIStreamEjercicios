package repository;

import mapping.dtos.AppDto;

import java.util.List;

public interface AppRepository {
    AppDto createOrder(AppDto order);
    AppDto updateOrder(AppDto order);
    String removeOrder(Long orderId);
    List<AppDto> getAllOrders();
    AppDto findOrderById(Long orderId);
}
