package com.thorsteinnth.kth.mmse.sep.cli;

import java.util.Scanner;

public class CliHandler
{
    public static String getInput(String messageToUser)
    {
        System.out.println(messageToUser);

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        /*String[] inputSplit = input.split(" ");

        System.out.println("ECHOING:");
        for (String i : inputSplit)
        {
            System.out.println(i);
        }

        return inputSplit;
        */

        return input;
    }
}
