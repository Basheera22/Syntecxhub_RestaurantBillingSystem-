import java.util.*;

// Main class
public class ResturantBillingSystem {

    // Inner class for Menu Items
    static class MenuItem {
        private String name;
        private double price;

        public MenuItem(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() { return name; }
        public double getPrice() { return price; }
    }

    // Inner class for Restaurant Menu
    static class RestaurantMenu {
        private Map<String, MenuItem> menu = new HashMap<>();

        public void addItem(String name, double price) {
            menu.put(name.toLowerCase(), new MenuItem(name, price));
            System.out.println(name + " added to menu.");
        }

        public void removeItem(String name) {
            if (menu.remove(name.toLowerCase()) != null) {
                System.out.println(name + " removed from menu.");
            } else {
                System.out.println("Item not found in menu.");
            }
        }

        public MenuItem getItem(String name) {
            return menu.get(name.toLowerCase());
        }

        public void displayMenu() {
            System.out.println("\n--- MENU ---");
            for (MenuItem item : menu.values()) {
                System.out.printf("%s : ₹%.2f\n", item.getName(), item.getPrice());
            }
        }
    }

    // Inner class for Billing System
    static class BillingSystem {
        private RestaurantMenu menu;
        private Map<MenuItem, Integer> order = new LinkedHashMap<>();
        private static final double GST_RATE = 0.05; // 5% GST

        public BillingSystem(RestaurantMenu menu) {
            this.menu = menu;
        }

        public void takeOrder() {
            Scanner scanner = new Scanner(System.in);
            String choice;

            do {
                menu.displayMenu();
                System.out.print("Enter item name: ");
                String itemName = scanner.nextLine();
                MenuItem item = menu.getItem(itemName);

                if (item != null) {
                    System.out.print("Enter quantity: ");
                    int qty = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    order.put(item, order.getOrDefault(item, 0) + qty);
                } else {
                    System.out.println("Item not found.");
                }

                System.out.print("Add more items? (yes/no): ");
                choice = scanner.nextLine();
            } while (choice.equalsIgnoreCase("yes"));
        }

        public void printReceipt() {
            double subtotal = 0;
            System.out.println("\n--- RECEIPT ---");
            for (Map.Entry<MenuItem, Integer> entry : order.entrySet()) {
                MenuItem item = entry.getKey();
                int qty = entry.getValue();
                double total = item.getPrice() * qty;
                subtotal += total;
                System.out.printf("%s x%d = ₹%.2f\n", item.getName(), qty, total);
            }

            double gst = subtotal * GST_RATE;
            double grandTotal = subtotal + gst;

            System.out.printf("Subtotal: ₹%.2f\n", subtotal);
            System.out.printf("GST (5%%): ₹%.2f\n", gst);
            System.out.printf("Total: ₹%.2f\n", grandTotal);
        }
    }

    // Main method with menu-driven interface
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RestaurantMenu menu = new RestaurantMenu();
        menu.addItem("Burger", 120);
        menu.addItem("Pizza", 250);
        menu.addItem("Fries", 80);
        menu.addItem("Coke", 50);

        BillingSystem billing = new BillingSystem(menu);

        while (true) {
            System.out.println("\n--- Restaurant Billing System ---");
            System.out.println("1. Place an Order");
            System.out.println("2. Add Menu Item");
            System.out.println("3. Remove Menu Item");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (option) {
                case 1:
                    billing.takeOrder();
                    billing.printReceipt();
                    break;
                case 2:
                    System.out.print("Enter new item name: ");
                    String newItem = scanner.nextLine();
                    System.out.print("Enter price: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine();
                    menu.addItem(newItem, price);
                    break;
                case 3:
                    System.out.print("Enter item name to remove: ");
                    String removeItem = scanner.nextLine();
                    menu.removeItem(removeItem);
                    break;
                case 4:
                    System.out.println("Exiting... Thank you!");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

}
