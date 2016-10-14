package com.thorsteinnth.kth.mmse.sep.cli;

import java.util.Scanner;

public class CliHandler
{
    public static String getInput(String messageToUser)
    {
        System.out.println(messageToUser);

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        System.out.println("ECHOING: " + input);
        return input;
    }
}
