import java.util.*;

enum MenuOption {
    PLACE_ORDER(1), ADD_ITEM(2), REMOVE_ITEM(3), VIEW_BILL(4), VIEW_MENU(5), SEARCH_ITEM(6), EXIT(7);

    private final int option;

    MenuOption(int option) {
        this.option = option;
    }

    public static MenuOption fromInt(int option) {
        for (MenuOption choice : values()) {
            if (choice.option == option) {
                return choice;
            }
        }
        return null;
    }
}

class Menu {
    private final Map<String, Integer> menuItems = new HashMap<>();

    public Menu() {
        menuItems.put("biryani", 150);
        menuItems.put("fried rice", 100);
    }

    public void display() {
        System.out.println("\n=====================================");
        System.out.println("               MENU                 ");
        System.out.println("=====================================");
        System.out.printf("%-20s %-10s\n", "Item", "Price");
        System.out.println("-------------------------------------");

        for (Map.Entry<String, Integer> entry : menuItems.entrySet()) {
            System.out.printf("%-20s %-10d\n", entry.getKey(), entry.getValue());
        }

        System.out.println("=====================================");
    }

    public void addItem(String name, int price) {
        if (!isValidName(name) || price <= 0) {
            System.out.println("Invalid input! Enter a valid item name (no numbers) and a valid price.");
            return;
        }
        menuItems.put(name.toLowerCase(), price);
        System.out.println("Item '" + name + "' added successfully!");
    }

    public void removeItem(String name) {
        if (!isValidName(name)) {
            System.out.println("Enter a valid item name (no numbers allowed).");
            return;
        }
        if (menuItems.remove(name.toLowerCase()) != null) {
            System.out.println("Item '" + name + "' removed from the menu.");
        } else {
            System.out.println("Item not found!");
        }
    }

    public int search(String name) {
        return menuItems.getOrDefault(name.toLowerCase(), -1);
    }

    public boolean itemExists(String name) {
        return menuItems.containsKey(name.toLowerCase());
    }

    private boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && !name.matches(".*\\d.*");
    }
}

class Order {
    private final List<String> orderList = new ArrayList<>();
    private int totalAmount = 0;
    private final Menu menu;

    public Order(Menu menu) {
        this.menu = menu;
    }

    public void placeOrder(String itemName) {
        if (!isValidName(itemName)) {
            System.out.println("Enter a valid item name (no numbers allowed).");
            return;
        }
        int price = menu.search(itemName);
        if (price != -1) {
            orderList.add(itemName);
            totalAmount += price;
            System.out.println("Order placed: " + itemName + " - " + price);
        } else {
            System.out.println("Sorry, " + itemName + " is not available.");
        }
    }

    public void removeOrderItem(String itemName) {
        if (!orderList.remove(itemName)) {
            System.out.println("Item not found in your order.");
            return;
        }
        int price = menu.search(itemName);
        totalAmount -= price;
        System.out.println("Item '" + itemName + "' removed from the order.");
    }

    public void viewOrder() {
        if (orderList.isEmpty()) {
            System.out.println("\nYour order is empty.");
            return;
        }

        System.out.println("\n========= Your Order =========");
        System.out.printf("%-20s %-10s\n", "Item", "Price");
        System.out.println("-------------------------------");

        for (String item : orderList) {
            System.out.printf("%-20s %-10d\n", item, menu.search(item));
        }
        System.out.println("-------------------------------");
        System.out.printf("%-20s %-10d\n", "Total Amount", totalAmount);
        System.out.println("===============================");
    }

    private boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && !name.matches(".*\\d.*");
    }
}

public class Restaurant_Management {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Menu menu = new Menu();
        Order order = new Order(menu);
        int option;

        while (true) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1. Place an order");
            System.out.println("2. Add a new item to the menu");
            System.out.println("3. Remove an item from the menu");
            System.out.println("4. View your total bill");
            System.out.println("5. View the menu");
            System.out.println("6. Search for an item in the menu");
            System.out.println("7. Exit and complete the order");

            System.out.print("\nYour choice: ");
            if (!sc.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number.");
                sc.next();
                continue;
            }
            option = sc.nextInt();
            sc.nextLine();

            MenuOption choice = MenuOption.fromInt(option);
            if (choice == null) {
                System.out.println("Invalid option! Please try again.");
                continue;
            }

            switch (choice) {
                case PLACE_ORDER:
                    System.out.print("\nEnter the item name: ");
                    String orderItem = sc.nextLine();
                    order.placeOrder(orderItem);
                    break;
                case ADD_ITEM:
                    System.out.print("\nEnter new item name: ");
                    String newItem = sc.nextLine();
                    if (!newItem.matches("^[a-zA-Z ]+$")) {
                        System.out.println("Invalid input! Enter a valid item name (no numbers allowed).");
                        break;
                    }
                    System.out.print("Enter price: ");
                    int price;
                    while (true) {
                        if (!sc.hasNextInt()) {
                            System.out.println("Invalid price! Please enter a valid number.");
                            sc.next();
                        } else {
                            price = sc.nextInt();
                            sc.nextLine();
                            break;
                        }
                    }

                    menu.addItem(newItem, price);
                    break;
                case REMOVE_ITEM:
                    System.out.print("\nEnter the item name to remove: ");
                    String removeItem = sc.nextLine();
                    menu.removeItem(removeItem);
                    break;
                case VIEW_BILL:
                    order.viewOrder();
                    break;
                case VIEW_MENU:
                    menu.display();
                    break;
                case SEARCH_ITEM:
                    System.out.print("\nEnter item name to search: ");
                    String searchItem = sc.nextLine();
                    if (!searchItem.matches("^[a-zA-Z ]+$")) {
                        System.out.println("Invalid input! Enter a valid item name (no numbers allowed).");
                        break;
                    }
                    if (menu.itemExists(searchItem)) {
                        System.out.println("The item is available on the menu.");
                    } else {
                        System.out.println("Sorry, the item is not available.");
                    }
                    break;
                case EXIT:
                    System.out.println("\nThank you for visiting! Have a great day!");
                    sc.close();
                    return;
            }
        }
    }
}
