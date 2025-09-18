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
            Map<Integer, CartItem> cartItemList = cart.getCartItemList();

            Order order = new Order(cartItemList);

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
    public float amount = 0;
    private int id;
    public static int cartItemCount = 0;


    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        float priceOfIndividualProduct = product.getPrice();
        float totalAmount = priceOfIndividualProduct * quantity;
        this.amount = amount + totalAmount;
        cartItemCount++;
        this.setId(cartItemCount);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

class Cart {

    Map<Integer, CartItem> cartItemList = new LinkedHashMap<>();

    public void addCartItemToCart(CartItem cartItem) {
        cartItemList.put(cartItem.getId(), cartItem);
    }

    public void displayCart() {
        for (Integer cartItemListKey : cartItemList.keySet()) {
            CartItem cartItem = cartItemList.get(cartItemListKey);

            System.out.println("Items in a cart: "
                    + cartItem.quantity +
                    " " + cartItem.product.getName());
        }
    }

    public Map<Integer, CartItem> getCartItemList() {
        return cartItemList;
    }
}

class Order {
    public int orderId;
    public float amount;
    public OrderStatus status;
    public static int count = 0;

    public Order(Map<Integer, CartItem> cartItemList) {
        count++;
        for(Integer integer: cartItemList.keySet()){
            CartItem cartItem = cartItemList.get(integer);
            this.amount = cartItem.amount + this.amount;
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

