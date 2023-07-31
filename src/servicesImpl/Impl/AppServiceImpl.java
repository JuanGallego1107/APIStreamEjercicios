package servicesImpl.Impl;

import mapping.dtos.AppDto;
import repository.AppRepository;
import servicesImpl.AppService;

import java.util.List;

public class AppServiceImpl implements AppService {

    private AppRepository repository;

    public AppServiceImpl(AppRepository repository) {
        this.repository = repository;
    }

    @Override
    public AppDto createOrder(AppDto order) {
        return repository.createOrder(order);
    }

    @Override
    public AppDto updateOrder(AppDto order) {
        return repository.updateOrder(order);
    }

    @Override
    public String removeOrder(Long orderId) {
        return repository.removeOrder(orderId);
    }

    @Override
    public List<AppDto> getAllOrders() {
        return repository.getAllOrders();
    }
}
