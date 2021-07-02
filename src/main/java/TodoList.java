import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class TodoList {

  private static List<String> todoList = new ArrayList();

  public static void main(String[] args) throws IOException {
    // step 1
    ClassLoader classLoader = TodoList.class.getClassLoader();
    File fileList = new File(classLoader.getResource("todo.txt").getFile());

    Scanner scanner = new Scanner(fileList);
    while(scanner.hasNextLine()) {
      todoList.add(scanner.nextLine());
    }
    System.out.println("\n--------");
    System.out.println("Our To Do list for today:");
    System.out.println(todoList);

    // step 2
    todoList.add(todoList.indexOf("buy groceries"),"go to the ATM");
    System.out.println("\n--------");
    System.out.println("We need money before we could buy smth");
    System.out.println(todoList);

    // step 3
    Map<String, String> taskMap = new HashMap<>();
    for(String task : todoList) {
      taskMap.put(task, "pending");
    }
    System.out.println("\n--------");
    System.out.println("Adding a status, using a HashMap");
    System.out.println(taskMap);

    // step 4
    taskMap.put("trim hedges", "complete");
    System.out.println("\n--------");
    System.out.println("Updating a status of 'trim hedges' to 'complete'");
    System.out.println(taskMap);

    // step 5
    Map<String, Integer> groceriesMap;

    File groceriesList = new File(classLoader.getResource("groceries.json").getFile());
    ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    groceriesMap = mapper.readValue(groceriesList, HashMap.class);

    System.out.println("\n--------");
    System.out.println("Reading groceries.json file and mapping it using HashMap");
    System.out.println(groceriesMap);

    // step 6
    groceriesMap.put("Loaf of Bread", 1);
    groceriesMap.put("Gallon of Milk", 1);

    System.out.println("\n--------");
    System.out.println("Adding bread and milk to the grocery list");
    System.out.println(groceriesMap);

    // step 7
    Map<String, Integer> pricesMap;
    File groceriesPrices = new File(TodoList.class.getResource("grocery_prices.json").getFile());
    pricesMap = mapper.readValue(groceriesPrices, HashMap.class);
    System.out.println("\n--------");
    System.out.println("Reading a grocery prices list in cents");
    System.out.println(pricesMap);

    // step 8
    int totalPrice= 0;
    // we use `.keySet()` to grab a set of the names of the groceries we want to buy
    // we specifically look at `groceriesMap` (NOT `priceMap`) so that we only get the ones we're trying to buy
    Set<String> groceriesToBuy = groceriesMap.keySet();
    System.out.println("\n--------");
    System.out.println("Groceries to buy: \n" + groceriesToBuy );
    System.out.println("\n--------");
    // we loop through each grocery we want to buy
    for (String item : groceriesToBuy){
      // get the quantity from groceriesMap
      int qty = groceriesMap.get(item);
      // get the price from priceMap
      int price = pricesMap.get(item);
      // we calculate the totalItemPrice by multiplying
      int totalItemPrice= qty * price;
      System.out.println("Price for " + item + " is: " + totalItemPrice);
      // we add the totalItemPrice to our entire price
      totalPrice += totalItemPrice;
    }
    System.out.println("\n--------");
    System.out.println("Total Price: " + totalPrice + " cents");
    // converting a price from cents to dollars
    Double priceInDollars = totalPrice / 100.00;
    System.out.println("\n--------");
    System.out.println("Total price in dollars: $" + priceInDollars);

      // step 9
//    taskMap.put("go to the ATM and withdraw " + totalPrice + " cents", "pending");
//    taskMap.put("go to the ATM and withdraw $" + priceInDollars, "pending");
    taskMap.put("go to the ATM and withdraw $" + priceInDollars, taskMap.get("go to the ATM"));
    System.out.println("\n--------");
    System.out.println("Duplicated ATM task");
    System.out.println(taskMap);
    taskMap.remove("go to the ATM");
    System.out.println("\n--------");
    System.out.println("Updated task list with money withdrawal");
    System.out.println(taskMap);
  }
}
