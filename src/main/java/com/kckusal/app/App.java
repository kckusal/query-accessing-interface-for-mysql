package com.kckusal.app;

import java.util.Scanner;

/**
 * Main Driver App
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println("Enter your SQL query:\n");
        System.out.format("%5s", "");

        Scanner sc = new Scanner(System.in);

        //String sql = "SELECT * from `users`";
        String sql = sc.nextLine().trim();

        System.out.println("\nThe result is given below:\n");
        Database.executeQuery(sql);

        sc.close();
    }
}
