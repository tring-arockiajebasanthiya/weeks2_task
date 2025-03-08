import java.util.*;

// Custom Exception for invalid inputs
class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}

// Custom Exception for item not found
class ItemNotFoundException extends Exception {
    public ItemNotFoundException(String message) {
        super(message);
    }
}

// Enum for menu options
enum MenuOption {
    PLACE_ORDER(1), ADD_ITEM(2), REMOVE_ITEM(3), VIEW_BILL(4), VIEW_MENU(5), SEARCH_ITEM(6), REMOVE_ALL_ITEM(7), EXIT(8);

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

// Base class
class Restaurant {
    protected boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && !name.matches(".*\\d.*");
    }
}

// Menu class inheriting from Restaurant
class Menu extends Restaurant {
    private final Map<String, Map<String, Integer>> menuItems = new HashMap<>();

    public Menu() {
        addItem("Biryani", "Main Course", 150);
        addItem("Fried Rice", "Main Course", 100);
    }

    public void display() {
        System.out.println("\n============= MENU =============");

        for (Map.Entry<String, Map<String, Integer>> categoryEntry : menuItems.entrySet()) {
            System.out.println("\nCategory: " + categoryEntry.getKey());
            System.out.printf("%-20s %-10s\n", "Item", "Price");
            System.out.println("--------------------------------");

            for (Map.Entry<String, Integer> entry : categoryEntry.getValue().entrySet()) {
                System.out.printf("%-20s %-10d\n", entry.getKey(), entry.getValue());
            }
        }
        System.out.println("================================");
    }

    public void addItem(String name, String category, int price) {
        try {
            if (!isValidName(name) || !isValidName(category) || price <= 0) {
                throw new InvalidInputException("Invalid item name, category, or price!");
            }
            menuItems.putIfAbsent(category, new HashMap<>());
            menuItems.get(category).put(name, price);
            System.out.println("Item '" + name + "' added successfully under '" + category + "'!");
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void removeItem(String name, String category) {
        try {
            if (!isValidName(name) || !isValidName(category)) {
                throw new InvalidInputException("Invalid item name or category.");
            }
            if (menuItems.containsKey(category) && menuItems.get(category).remove(name) != null) {
                System.out.println("Item '" + name + "' removed from category '" + category + "'.");
            } else {
                throw new ItemNotFoundException("Item not found in the menu!");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void removeAllItems() {
        menuItems.clear();
        System.out.println("All items have been removed from the menu.");
    }

    public int search(String name) {
        for (Map<String, Integer> category : menuItems.values()) {
            if (category.containsKey(name)) {
                return category.get(name);
            }
        }
        return -1;
    }
}

// Order class inheriting from Restaurant
class Order extends Restaurant {
    private final List<String> orderList = new ArrayList<>();
    private int totalAmount = 0;
    private final Menu menu;

    public Order(Menu menu) {
        this.menu = menu;
    }

    public void placeOrder(String itemName) {
        try {
            if (!isValidName(itemName)) {
                throw new InvalidInputException("Invalid item name. Numeric values are not allowed.");
            }
            int price = menu.search(itemName);
            if (price != -1) {
                orderList.add(itemName);
                totalAmount += price;
                System.out.println("Order placed: " + itemName + " - ₹" + price);
            } else {
                throw new ItemNotFoundException("Sorry, " + itemName + " is not available.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
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
            System.out.println("7. Remove all items from the menu");
            System.out.println("8. Exit");
            System.out.print("\nYour choice: ");

            try {
                if (!sc.hasNextInt()) {
                    throw new InvalidInputException("Invalid input! Please enter a number.");
                }
                option = sc.nextInt();
                sc.nextLine();

                MenuOption choice = MenuOption.fromInt(option);
                if (choice == null) {
                    throw new InvalidInputException("Invalid option! Please try again.");
                }

                switch (choice) {
                    case PLACE_ORDER:
                        System.out.print("\nEnter the item name: ");
                        String orderItem = sc.nextLine();
                        order.placeOrder(orderItem);
                        break;
                    case ADD_ITEM:
                        System.out.print("\nEnter item name: ");
                        String newItem = sc.nextLine();
                        System.out.print("Enter category: ");
                        String category = sc.nextLine();
                        System.out.print("Enter price: ");
                        int price = sc.nextInt();
                        sc.nextLine();
                        menu.addItem(newItem, category, price);
                        break;
                    case REMOVE_ITEM:
                        System.out.print("\nEnter the item name: ");
                        String removeItem = sc.nextLine();
                        System.out.print("Enter category: ");
                        String removeCategory = sc.nextLine();
                        menu.removeItem(removeItem, removeCategory);
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
                        System.out.println(menu.search(searchItem) != -1 ? "The item is available." : "Sorry, not available.");
                        break;
                    case REMOVE_ALL_ITEM:
                        menu.removeAllItems();
                        break;
                    case EXIT:
                        System.out.println("\nThank you for visiting! Have a good day.");
                        sc.close();
                        return;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                sc.nextLine();
            }
        }
    }
}
