import domain.models.Customer;
import domain.models.Order;
import domain.models.Product;
import mapping.dtos.AppDto;
import repository.AppRepository;
import repository.impl.AppRepositoryImpl;
import servicesImpl.AppService;
import servicesImpl.Impl.AppServiceImpl;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) {

        List<Product> products = getProductList();

        List<Customer> customers = getCustomerList();

        List<Order> orders = new ArrayList<>();
        orders.addAll(createOrder(products.subList(0, 3), customers.get(0)).subList(0, 1));
        orders.addAll(createOrder(products.subList(3, 4), customers.get(1)).subList(1, 2));
        orders.addAll(createOrder(products.subList(3, 5), customers.get(4)).subList(2, 3));
        orders.addAll(createOrder(products.subList(5, 7), customers.get(2)).subList(3, 4));
        orders.addAll(createOrder(products.subList(7, 9), customers.get(3)).subList(4, 5));
        orders.addAll(createOrder(products.subList(8, 10), customers.get(0)).subList(5, 6));
        orders.addAll(createOrder(products.subList(4, 6), customers.get(1)).subList(6, 7));


        //Punto1 :Lista para categoria "Libros" y precio mayor a 100
        List<Product> filteredBooks = products.stream()
                .filter(e -> e.getCategory().equalsIgnoreCase("Libros"))
                .filter(e -> e.getPrice() > 100)
                .toList();

        System.out.println("Esta es la lista de productos por categoria Libros y precio mayor a 100: ");
        System.out.println(filteredBooks);
        System.out.println("=====================================================");

        //Punto2 :Lista de pedidos que contengan productos de categoria "Bebé"
        List<Order> babys = orders.stream()
                .filter(e -> e.getProducts().stream()
                        .anyMatch(prod -> prod.getCategory().equalsIgnoreCase("Bebé")))
                .toList();
        System.out.println("Esta es la lista de pedidos que contengan algun producto de la categoria Bebé: ");
        System.out.println(babys);
        System.out.println("=====================================================");

        //Punto3 :Lista para categoria "Juguetes" y descuento de 10%
        List<Product> toys = products.stream()
                .filter(e -> e.getCategory().equalsIgnoreCase("Juguetes"))
                .peek(dis -> {
                    double discountedPrice = dis.getPrice() * 0.10;
                    dis.setPrice(dis.getPrice() - discountedPrice);
                })
                .toList();
        System.out.println("Esta es la lista de productos de la categoria Juguetes y se le aplicó un 10% de descuento: ");
        System.out.println(toys);
        System.out.println("=====================================================");

        /*Punto4 : Lista de productos pedidos por el cliente de tier 2 entre el 01
        de febrero de 2021 y el 01 de abril de 2021*/


        List<Product> filterTierDate = orders.stream()
                .filter(e -> e.getCustomer().getTier() == 2)
                .filter(e -> e.getOrderDate().isAfter(LocalDate.of(2021, 2, 1)))
                .filter(e -> e.getOrderDate().isBefore(LocalDate.of(2021, 4, 1)))
                .flatMap(e -> e.getProducts().stream())
                .distinct()
                .toList();

        System.out.println("Esta es la lista de productos pedidos por clientes de Tier 2 entre 2 fechas: ");
        System.out.println(filterTierDate);
        System.out.println("=====================================================");

        //Punto 5: Lista de los producto de categoria Libros mas baratos
        List<Product> listCheapest = products.stream()
                .filter(e -> e.getCategory().equalsIgnoreCase("Libros"))
                .min(Comparator.comparing(Product::getPrice)).stream().toList();

        System.out.println("Esta es la lista de productos de la categoria Libros mas baratos: ");
        System.out.println(listCheapest);
        System.out.println("=====================================================");

        //Punto 6: Lista de los 3 pedidos mas recientes (Por fecha de la orden)

        List<Order> list3Latest = orders.stream()
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .limit(3)
                .toList();

        System.out.println("Esta es la lista de los 3 pedidos mas recientes");
        System.out.println(list3Latest);
        System.out.println("=====================================================");

        //Punto 7: Suma total de los pedidos realizado en una fecha especifica

        List<Order> orderSpecificDate = orders.stream()
                .filter(e -> e.getOrderDate().getMonthValue() == 3)
                .filter(e -> e.getOrderDate().getYear() == 2022)
                .toList();

        double sumSpecificDate = orderSpecificDate.stream()
                .mapToDouble(e -> e.getProducts().stream().mapToDouble(Product::getPrice)
                        .sum())
                .sum();

        System.out.println("Esta es la suma de los pedidos hechos en el mes de Marzo en 2022:");
        System.out.println(sumSpecificDate);
        System.out.println("=====================================================");


        //Punto 8: Promedio de pago en los pedidos en una fecha especifica
        List<Order> listSpecificDate = orders.stream()
                .filter(e -> Objects.equals(e.getOrderDate(), LocalDate.of(2022, 3, 12)))
                .toList();

        double avgSpecificDate = listSpecificDate.stream()
                .mapToDouble(e -> e.getProducts().stream().mapToDouble(Product::getPrice).
                        average().orElse(0.0))
                .average().orElse(0.0);
        System.out.println("Este es el promedio de pago en los pedidos en la fecha 12/03/2022 :");
        System.out.println(avgSpecificDate);
        System.out.println("=====================================================");

        //Punto 9: Mapa de datos con registros de pedidos agrupados por cliente
        Map<Customer, List<Order>> ordersByTier = orders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer));

        System.out.println("Este es el mapa de datos con los pedidos agrupados por cliente: ");
        System.out.println(ordersByTier);
        System.out.println("=====================================================");

        //Punto 10: Producto mas caro por categoria
        Map<String, Optional<Product>> mostExpensive = products.stream()
                .collect(Collectors.groupingBy(Product::getCategory,Collectors.
                        maxBy(Comparator.comparing(Product::getPrice))));
        System.out.println(mostExpensive);
    }

    private static List<Product> getProductList(){

        Product product1 = new Product(1L,"Libro1","Libros",30.0);
        Product product2 = new Product(2L,"Libro2","Libros",120.0);
        Product product3 = new Product(3L,"Libro3","Libros",110.0);
        Product product4 = new Product(4L,"Fabula1","Fabula",30.0);
        Product product5 = new Product(5L,"Babero","Bebé",10.000);
        Product product6 = new Product(6L,"Mameluco","Bebé",25.000);
        Product product7 = new Product(7L,"Carro Hot Wheels","Juguetes",35.0);
        Product product8 = new Product(8L,"Max steel","Juguetes",43.0);
        Product product9 = new Product(9L,"Libro4","Libros",315.0);
        Product product10 = new Product(10L,"Libro5","Libros",45.0);

        return List.of(product1,product2,product3,product4,product5,product6,product7,product8,product9,product10);
    }

    private static List<Customer> getCustomerList(){

        Customer c1 = new Customer(1L, "Mario", 1);
        Customer c2 = new Customer(2L, "Juan", 2);
        Customer c3 = new Customer(3L, "Pablo", 3);
        Customer c4 = new Customer(4L, "Daniel",1);
        Customer c5 = new Customer(5L, "Camilo",2);

        return List.of(c1, c2, c3, c4, c5);
    }

    private static List<Order> createOrder(List<Product> products, Customer customer) {
        Order or1 = new Order(1L,"Processed",LocalDate.of(2021,2,15)
                ,LocalDate.now(),products,customer);
        Order or2 = new Order(2L,"Processed",LocalDate.of(2021,3,16)
                ,LocalDate.now(),products,customer);
        Order or3 = new Order(3L,"Processed",LocalDate.now(),LocalDate.now(),products,customer);
        Order or4 = new Order(4L,"Processed",LocalDate.of(2023,5,16)
                ,LocalDate.now(),products,customer);
        Order or5 = new Order(5L,"Processed",LocalDate.of(2023,6,26)
                ,LocalDate.now(),products,customer);
        Order or6 = new Order(6L,"Processed",LocalDate.of(2022,3,12)
                ,LocalDate.now(),products,customer);
        Order or7 = new Order(7L,"Processed",LocalDate.of(2022,3,12)
                ,LocalDate.now(),products,customer);

        return List.of(or1, or2, or3, or4, or5, or6,or7);
    }

}