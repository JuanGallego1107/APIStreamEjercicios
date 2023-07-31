package mapping.mappers;

import domain.models.Order;
import mapping.dtos.AppDto;

import java.util.List;

public class AppMapper {
    public static AppDto mapFrom(Order source) {
        return new AppDto(source.getId(),
                source.getStatus(),
                source.getOrderDate(),
                source.getDeliveryDate(),
                source.getProducts(),
                source.getCustomer());
    }

    public static Order mapFrom(AppDto source) {
        return new Order(source.identifier(),
                source.status(),
                source.orderDate(),
                source.delivery(),
                source.products(),
                source.customer());
    }

    public static List<AppDto> mapFrom(List<Order> source) {
        return source.parallelStream().map(e-> mapFrom(e)).toList();
    }
}

