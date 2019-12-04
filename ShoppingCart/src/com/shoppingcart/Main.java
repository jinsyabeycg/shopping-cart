package com.shoppingcart;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    static Map<Double, HashMap<Integer, Integer>> discountData = new HashMap<>();

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        HashMap<Integer, Integer> slab1 = new HashMap<>();
        slab1.put(0, 5000);
        HashMap<Integer, Integer> slab2 = new HashMap<>();
        slab2.put(5000, 10000);
        HashMap<Integer, Integer> slab3 = new HashMap<>();
        slab3.put(10000, 15000);
        discountData.put(0.0, slab1);
        discountData.put(5.0, slab2);
        discountData.put(1.0, slab3);

        int enteredValue = 0;

        while(enteredValue != 3){
            System.out.println("1. Enter a new slab");
            System.out.println("2. Get Billing amount for customer");
            System.out.println("3. Exit");
            enteredValue = scan.nextInt();
            if(enteredValue == 3)
                break;
            System.out.println(processOptions(enteredValue, scan));
        }

    }

    private static Object processOptions(int enteredValue, Scanner scan) {
        switch (enteredValue){
            case 1:
                String message = createSlab(scan);
                return message;

            case 2:
                double amount = getAmount(scan);
                return amount;
            default:
                return "Enter a valid input";

        }
    }

    private static double getAmount(Scanner scan) {
        System.out.println("Enter purchase amount: ");
        int purchase = scan.nextInt();

        System.out.println("Enter customer type: ");
        String type = scan.next();


        OptionalDouble discount = discountData.entrySet().stream().filter(data ->
                data.getValue().entrySet().stream().anyMatch(x ->
                        purchase >= x.getKey() && purchase < x.getValue())).collect(Collectors.toList()).stream().map(data -> data.getKey()).collect(Collectors.toList()).stream().mapToDouble(Double::doubleValue).max();


        double discountAmount = (Double.valueOf((double) discount.getAsDouble())/100) * purchase;
        return discountAmount == 0 ? purchase : (purchase - discountAmount);
    }

    private static String createSlab(Scanner scan) {
        Map<Integer, Integer> slab = new HashMap<>();
        System.out.println("Enter minimum amount: ");
        int min = scan.nextInt();
        System.out.println("Enter maximum amount: ");
        int max = scan.nextInt();
        System.out.println("Enter discount percentage: ");
        double discount = scan.nextDouble();
        if(min < max){
            slab.put(min, max);
            discountData.put(discount, (HashMap<Integer, Integer>) slab);
            return "Created a new Slab";
        }
        else {
            return "Enter valid input";
        }
    }
}
