package com.thorsteinnth.kth.mmse.sepcli;

import com.thorsteinnth.kth.mmse.sepcli.Test.AcceptanceTest.EventRequestAcptTest;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class CliHelper
{
    private static boolean isTestMode;

    public static boolean getIsTestMode()
    {
        return isTestMode;
    }

    public static void setIsTestMode(boolean testMode)
    {
        isTestMode = testMode;
    }

    public static String getInput(String messageToUser)
    {
        InputStream is;
        if(isTestMode)
            is = EventRequestAcptTest.getInputStream();
        else
            is = System.in;

        Scanner scanner;

        if(is != null)
        {
            scanner = new Scanner(is);
        }
        else
        {
            scanner = new Scanner(System.in);
        }

        write(messageToUser);
        String input = scanner.nextLine();

        return input;
    }

    public static String getInputEmptyStringBanned(String messageToUser)
    {
        boolean haveValidInput = false;
        String input = "";

        while (!haveValidInput)
        {
            input = getInput(messageToUser);

            if (!input.equals(""))
                haveValidInput = true;
            else
                write("Error: Invalid input - cannot be empty");
        }

        return input;
    }

    public static String getInputNumber(String messageToUser)
    {
        boolean haveValidInput = false;
        String input = "";

        while (!haveValidInput)
        {
            input = getInputEmptyStringBanned(messageToUser);

            if (isNumeric(input))
                haveValidInput = true;
            else
                write("Error: Invalid input - should be a number");
        }

        return input;
    }

    public static String getInputCurrency(String messageToUser)
    {
        boolean haveValidInput = false;
        String input = "";

        while (!haveValidInput)
        {
            input = getInputNumber(messageToUser);

            if (isValidCurrencyString(input))
                haveValidInput = true;
            else
                write("Error: Invalid input - input should be a number that represents money");
        }

        return input;
    }

    private static boolean isValidCurrencyString(String currencyString)
    {
        try
        {
            new BigDecimal(currencyString);
            return true;
        }
        catch (NumberFormatException ex)
        {
            return false;
        }
    }

    public static String getInputDate(String messageToUser)
    {
        // Date should be entered into the system on the form YYYY-MM-DD-HH-MM

        boolean haveValidInput = false;
        String input = "";

        while (!haveValidInput)
        {
            input = getInputEmptyStringBanned(messageToUser);

            if (isValidDateString(input))
                haveValidInput = true;
            else
                write("Error: Invalid input - should be a valid date on the form YYYY-MM-DD-HH-MM");
        }

        return input;
    }

    private static boolean isValidDateString(String dateString)
    {
        // YYYY-MM-DD-HH-MM

        try
        {
            String[] dateParts = dateString.split("-");
            int year = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]);
            int day = Integer.parseInt(dateParts[2]);
            int hour = Integer.parseInt(dateParts[3]);
            int minute = Integer.parseInt(dateParts[4]);

            GregorianCalendar gcal = new GregorianCalendar();
            gcal.setLenient(false);
            gcal.set(year, month, day, hour, minute);
            gcal.getTime(); // Should throw exception on wrong date

            return true;
        }
        catch (NumberFormatException ex)
        {
            return false;
        }
        catch (IllegalArgumentException ex)
        {
            return false;
        }
        catch (ArrayIndexOutOfBoundsException ex)
        {
            return false;
        }
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
                write("Error: Invalid input");
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

    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch (NumberFormatException nfe)
        {
            return false;
        }

        return true;
    }
}
