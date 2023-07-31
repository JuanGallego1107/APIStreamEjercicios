package mapping.dtos;

import domain.models.Customer;
import domain.models.Product;

import java.time.LocalDate;
import java.util.List;

public record AppDto(Long identifier,
                     String status,
                     LocalDate orderDate,
                     LocalDate delivery,
                     List<Product> products,
                     Customer customer) {
}
