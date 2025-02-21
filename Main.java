import java.util.*;

enum MenuOption {
    PLACE_ORDER(1), ADD_ITEM(2), VIEW_BILL(3), VIEW_MENU(4), SEARCH_ITEM(5), EXIT(6);

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
        try {
            System.out.println("\n=====================================");
            System.out.println("               MENU                 ");
            System.out.println("=====================================");
            System.out.printf("%-20s %-10s\n", "Item", "Price");
            System.out.println("-------------------------------------");

            for (Map.Entry<String, Integer> entry : menuItems.entrySet()) {
                System.out.printf("%-20s %-10d\n", entry.getKey(), entry.getValue());
            }

            System.out.println("=====================================");
        } catch (Exception e) {
            System.out.println("Error displaying menu: " + e.getMessage());
        }
    }

    public void addItem(String name, int price) {
        try {
            if (name == null || name.isEmpty() || price <= 0) {
                throw new IllegalArgumentException("Invalid item name or price.");
            }
            menuItems.put(name.toLowerCase(), price);
            System.out.println("Item '" + name + "' added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding item: " + e.getMessage());
        }
    }

    public int search(String name) {
        try {
            return menuItems.getOrDefault(name.toLowerCase(), -1);
        } catch (Exception e) {
            System.out.println("Error searching item: " + e.getMessage());
            return -1;
        }
    }
}

class Order {
    private int totalAmount = 0;
    private final Menu menu;

    public Order(Menu menu) {
        this.menu = menu;
    }

    public void placeOrder(String itemName) {
        try {
            if (itemName == null || itemName.isEmpty()) {
                throw new IllegalArgumentException("Invalid item name.");
            }

            int price = menu.search(itemName);
            if (price != -1) {
                totalAmount += price;
                System.out.println("Order placed: " + itemName + " - " + price);
            } else {
                System.out.println("Sorry, " + itemName + " is not available.");
            }
        } catch (Exception e) {
            System.out.println("Error placing order: " + e.getMessage());
        }
    }

    public int getTotal() {
        return totalAmount;
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("        WELCOME TO OUR RESTAURANT    ");
        System.out.println("=====================================\n");

        Scanner sc = new Scanner(System.in);
        Menu menu = new Menu();
        Order order = new Order(menu);
        int option;

        while (true) {
            try {
                System.out.println("\nPlease choose an option:");
                System.out.println("1. Place an order");
                System.out.println("2. Add a new item to the menu");
                System.out.println("3. View your total bill");
                System.out.println("4. View the menu");
                System.out.println("5. Search for an item in the menu");
                System.out.println("6. Exit and complete the order");

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
                        System.out.print("Enter price: ");
                        if (!sc.hasNextInt()) {
                            System.out.println("Invalid price! Please enter a number.");
                            sc.next();
                            continue;
                        }
                        int price = sc.nextInt();
                        sc.nextLine();
                        menu.addItem(newItem, price);
                        break;
                    case VIEW_BILL:
                        System.out.println("\nTotal bill: " + order.getTotal());
                        break;
                    case VIEW_MENU:
                        menu.display();
                        break;
                    case SEARCH_ITEM:
                        System.out.print("\nEnter item name to search: ");
                        String searchItem = sc.nextLine();
                        if (menu.search(searchItem) != -1) {
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
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
                sc.nextLine();
            }
        }
    }
}
