import java.util.*;

public class PhoneDirectoryDSA {
    public static void main(String[] args) {
        PhoneDirectory directory = new PhoneDirectory();

        directory.insert("9876543210");
        directory.insert("9123456789");
        directory.insert("9555123456");

        System.out.println("Searching for 9123456789: " + directory.search("9123456789"));
        System.out.println("Searching for 9000000000: " + directory.search("9000000000"));

        System.out.println("Stored phone numbers in sorted order:");
        directory.displaySortedNumbers();
    }
}

class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    boolean isEndOfNumber;
}

class PhoneDirectory {
    private final TrieNode root;

    public PhoneDirectory() {
        root = new TrieNode();
    }

    
    public void insert(String phoneNumber) {
        TrieNode node = root;
        for (char digit : phoneNumber.toCharArray()) {
            node.children.putIfAbsent(digit, new TrieNode());
            node = node.children.get(digit);
        }
        node.isEndOfNumber = true;
    }
    public boolean search(String phoneNumber) {
        TrieNode node = root;
        for (char digit : phoneNumber.toCharArray()) {
            if (!node.children.containsKey(digit)) {
                return false;
            }
            node = node.children.get(digit);
        }
        return node.isEndOfNumber;
    }

    
    public void displaySortedNumbers(TrieNode node, String prefix) {
        if (node.isEndOfNumber) {
            System.out.println(prefix);
        }
        for (char digit : node.children.keySet()) {
            displaySortedNumbers(node.children.get(digit), prefix + digit);
        }
    }

    public void displaySortedNumbers() {
        displaySortedNumbers(root, "");
    }
}

