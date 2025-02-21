import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("        WELCOME TO OUR RESTAURANT    ");
        System.out.println("=====================================\n");

        Menu m = new Menu();
        m.display();
        Order o = new Order();
        int option = 0;
        int totalAmount = 0;
        Scanner sc = new Scanner(System.in);

        System.out.println("\nServer: What would you like to order today?");

        while (option != 6) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1. Place an order");
            System.out.println("2. Add a new item to the menu");
            System.out.println("3. View your total bill");
            System.out.println("4. View the menu");
            System.out.println("5. Search for an item in the menu");
            System.out.println("6. Exit and complete the order");

            System.out.print("\nYour choice: ");
            option = sc.nextInt();
            sc.nextLine(); 

            switch (option) {
                case 1:
                    System.out.print("\nEnter the name of the item you would like to order: ");
                    String orderItem = sc.nextLine();
                    o.order(orderItem);
                    break;
                case 2:
                    System.out.println("\nPlease provide the new item name and its price:");
                    System.out.print("Item Name: ");
                    String newItem = sc.nextLine();
                    System.out.print("Price: ");
                    int price = sc.nextInt();
                    m.add_items(newItem, price);
                    System.out.println("Item successfully added to the menu.");
                    break;
                case 3:
                    totalAmount = o.sum();
                    System.out.println("\nTotal bill amount: \u20B9" + totalAmount);
                    break;
                case 4:
                    m.display();
                    break;
                case 5:
                    System.out.print("\nEnter the item name to search: ");
                    String searchItem = sc.nextLine();
                    if (m.search(searchItem) != -1) {
                        System.out.println("The item is available on our menu.");
                    } else {
                        System.out.println("Sorry, the item is not available.");
                    }
                    break;
                case 6:
                    System.out.println("\nThank you for dining with us. Have a great day!");
                    break;
                default:
                    System.out.println("Invalid option! Please choose a valid option from the menu.");
            }
        }
        sc.close();
    }
}

class Menu {
    private HashMap<String, Integer> menu = new HashMap<>();

    public Menu() {
        menu.put("biryani", 150);
        menu.put("fried rice", 100);
    }

    public int search(String name) {
        name = name.toLowerCase();
        return menu.getOrDefault(name, -1);
    }

    public void add_items(String name, int price) {
        name = name.toLowerCase();
        menu.put(name, price);
    }

    public void display() {
        System.out.println("\n=====================================");
        System.out.println("               MENU                 ");
        System.out.println("=====================================");
        System.out.printf("%-20s %-10s\n", "Item", "Price (\u20B9)");
        System.out.println("-------------------------------------");

        for (String item : menu.keySet()) {
            System.out.printf("%-20s \u20B9%-10d\n", item, menu.get(item));
        }

        System.out.println("=====================================");
    }
}

class Order extends Menu {
    private int total = 0;

    public void order(String name) {
        int price = search(name);
        if (price != -1) {
            total += price;
            System.out.println("Order placed successfully! Enjoy your " + name + ".");
        } else {
            System.out.println("Sorry, we don't have " + name + " on our menu.");
        }
    }

    public int sum() {
        return total;
    }
}
