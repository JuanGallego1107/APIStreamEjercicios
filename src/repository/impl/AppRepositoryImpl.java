package repository.impl;

import domain.models.Order;
import mapping.dtos.AppDto;
import mapping.mappers.AppMapper;
import repository.AppRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AppRepositoryImpl implements AppRepository {

    List<Order> orders;

    public AppRepositoryImpl() {
        orders = new ArrayList<>();
    }

    @Override
    public AppDto createOrder(AppDto order) {
        orders.add(AppMapper.mapFrom(order));
        return order;
    }

    @Override
    public AppDto updateOrder(AppDto order) {
        Order orderSaved = findById(order.identifier());
        orderSaved.setStatus(order.status());
        orderSaved.setOrderDate(order.orderDate());
        orderSaved.setDeliveryDate(order.delivery());
        orderSaved.setProducts(order.products());
        orderSaved.setCustomer(order.customer());
        updateOrderInList(orderSaved);
        return AppMapper.mapFrom(orderSaved);
    }

    private void updateOrderInList(Order order){
        orders.remove(order);
        orders.add(order);
    }

    @Override
    public String removeOrder(Long orderId) {
        orders.remove(findById(orderId));
        return "¡User deleted!"+orders.toString();
    }

    @Override
    public List<AppDto> getAllOrders() {
        return AppMapper.mapFrom(orders);
    }

    @Override
    public AppDto findOrderById(Long orderId) {
        return AppMapper.mapFrom(findById(orderId));
    }

    private Order findById(Long identifier) {
        for(Order order : orders){
            if(Objects.equals(order.getId(), identifier)){
                return order;
            }
        }
        throw new RuntimeException("Client not found");
    }
}
