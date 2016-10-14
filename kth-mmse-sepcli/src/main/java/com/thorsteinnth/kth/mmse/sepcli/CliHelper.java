package com.thorsteinnth.kth.mmse.sepcli;

import java.util.ArrayList;
import java.util.Scanner;

public class CliHelper
{
    public static String getInput(String messageToUser)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println(messageToUser);
        return scanner.nextLine();
    }

    public static String getInput(String messageToUser, ArrayList<String> validInputs)
    {
        boolean haveValidInput = false;
        String input = "";

        while (!haveValidInput)
        {
            input = getInput(messageToUser);

            if (isValidInput(input, validInputs))
                haveValidInput = true;
            else
                System.out.println("Error: Invalid input");
        }

        return input;
    }

    private static boolean isValidInput(String input, ArrayList<String> validInputs)
    {
        for (String vi : validInputs)
        {
            if (vi.equals(input))
                return true;
        }

        return false;
    }

    public static void write(String message)
    {
        System.out.println(message);
    }

    public static void newLine()
    {
        System.out.println("");
    }
}
