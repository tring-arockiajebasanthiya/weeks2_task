import java.util.*;
public class Main
{
    public static void main(String[] args)
    {
        Menu m = new Menu();
        m.display();
        Order o = new Order();
        int op = 0;
        int k = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Your order please:");
        while(op != 6)
        {
            System.out.println("Enter your options:(1.order items , 2.Add items , 3.Total 4.Display 5.Search items 6.Exit )\n");
            op = sc.nextInt();
            sc.nextLine();
            switch(op)
            {
                case 1:
                    System.out.println("Enter item name:");
                    String st= sc.nextLine();
                    o.order(st);
                    break;
                case 2 :
                    System.out.println("Enter item name and price");
                    String s= sc.nextLine();
                    int n =sc.nextInt();
                    m.add_items(s,n);
                    break;
                case 3 :
                    k = o.sum();
                    System.out.println("Total :" + k);
                    break;
                case 4:
                    m.display();
                    break;
                case 5:
                    System.out.println("Enter item name:");
                    String s1= sc.nextLine();
                    if(m.search(s1) != -1)
                    {
                        System.out.println("Item  found");
                    }
                    else{
                        System.out.println("Item not found");
                    }
                    break;
                case 6:
                    System.out.println("Thank you");
                    break;
                default:
                    System.out.println("Not found");
            }
        }
    }
} 
class Menu{
    private HashMap<String,Integer> menu =new HashMap<>();
    public Menu()
    {
        menu.put("biryani" , 150);
        menu.put("friedrice",100);
    }
    public int search(String name)
    {
        name = name.toLowerCase();
        if(menu.containsKey(name)) return menu.get(name);
        return -1;
    }
    public void add_items(String name , int n)
    {
        name = name.toLowerCase();
        menu.put(name , n);
    }
    public void display()
    {   System.out.println("Items" + "\t\t\t" + "Price");
        for(String i : menu.keySet())
        {
            System.out.println(i + "\t" + menu.get(i));
        }
    }
}
class Order extends Menu
{
    private int total = 0;
     public void order(String name)
     {
        int s = search(name);
        if( s!= -1)
        {
           total += s;
           System.out.println("Item ordered successfully");
        }
        else{
            System.out.println("Item not found");
        }

     }
     public int sum()
     {
        return total;
     }
}