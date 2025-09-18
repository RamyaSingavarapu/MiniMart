package org.example;

import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Product selectedProduct = null;
        int selectedProductId;
        int selectedQuantity;
        Store store = new Store();
        System.out.println("Products available in the store: ");
        store.displayProducts();
        do {
            System.out.println("Enter a product Id and quantity: ");
            selectedProductId = scanner.nextInt();
            selectedQuantity = scanner.nextInt();
            selectedProduct = store.selectProductFromList(selectedProductId, selectedQuantity);
            if (selectedProduct == null) {
                System.out.println("Please try again with a smaller quantity.");
            }
        }while(selectedProduct == null);

            CartItem cartItem = new CartItem(selectedProduct, selectedQuantity);

            Cart cart = new Cart();
            cart.addCartItemToCart(cartItem);
            cart.displayCart();
            Map<Product, Integer> cartItemList = cart.getCartItemList();

            Order order = new Order();
            order.createNewOrder(cartItemList);

    }
}

class Product {
    private int id;
    private String name;
    private float price;
    private int quantity;

    public Product(int id, String name, float price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}

class Store {
    public static List<Product> productList;

    static {
        productList = new ArrayList<>();
        productList.add(new Product(0, "JackWolfSkin blaue jacke", 184f, 5));
        productList.add(new Product(1, "RalphLauren schwarzer hose", 99.99f,5));
        productList.add(new Product(2, "ChristianaBerg leichte knöchenlange jacke", 100f,5));
        productList.add(new Product(3, "Mammout rote wandere Jacke", 508f,5));
        productList.add(new Product(4, "CalvinKlein dunkel blaue jeans", 59.99f,5));
    }

    public void displayProducts() {
        for (Product product : productList) {
            System.out.println(product);
        }
    }

    public Product selectProductFromList(int id, int quantity) {
        try {
            int availableNoOfProducts = productList.get(id).getQuantity();
            if (availableNoOfProducts >= quantity) {
                int setNoOfAvailableProducts = availableNoOfProducts - quantity;
                productList.get(id).setQuantity(setNoOfAvailableProducts);
            } else {
                throw new IllegalArgumentException("Not enough stock! Available: "+ availableNoOfProducts);
            }
        } catch ( IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }

        return  productList.get(id);
    }

}

class CartItem {
    public Product product;
    public int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}

class Cart {

    Map<Product, Integer> cartItemList;

    public void addCartItemToCart(CartItem cartItem) {
        cartItemList = new LinkedHashMap<>();
        cartItemList.put(cartItem.product, cartItem.quantity);
    }

    public void displayCart() {
        for (Product product : cartItemList.keySet()) {
            int value = cartItemList.get(product);

            System.out.println("Items in a cart: "
                    + value +
                    " " + product.getName());
        }
    }

    public Map<Product, Integer> getCartItemList() {
        return cartItemList;
    }
}

class Order {
    public int orderId;
    public float amount;
    public OrderStatus status;
    int count = 0;

    public void createNewOrder(Map<Product, Integer> cartItemList){
        for(Product product : cartItemList.keySet()){
            int value = cartItemList.get(product);
            this.amount = value * product.getPrice();
            count++;
        }
        this.orderId = count;
        this.status = OrderStatus.PENDING;
        System.out.println("Your total cartworth: " + this.amount + " which has OrderId: " + orderId);
        System.out.println("Your Order is " + this.status);
    }

}

enum OrderStatus{
    PENDING,
    CONFIRMED,
    CANCELED
}

