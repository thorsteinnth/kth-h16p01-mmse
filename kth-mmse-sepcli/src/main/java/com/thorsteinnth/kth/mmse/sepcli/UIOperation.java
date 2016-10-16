package com.thorsteinnth.kth.mmse.sepcli;

import java.util.ArrayList;

public class UIOperation
{
    private int number;
    private String description;
    private Command command;

    public UIOperation(int number, String description, Command command)
    {
        this.number = number;
        this.description = description;
        this.command = command;
    }

    public int getNumber()
    {
        return number;
    }

    public void executeCommand()
    {
        this.command.execute();
    }

    public String toDisplayString()
    {
        return Integer.toString(this.number) + ". " + this.description;
    }

    @Override
    public String toString()
    {
        return "UIOperation{" +
                "number=" + number +
                ", description='" + description + '\'' +
                '}';
    }

    public interface Command
    {
        void execute();
    }

    public static UIOperation getUIOperationWithNumberFromList
            (int number, ArrayList<UIOperation> operations)
    {
        for (UIOperation op : operations)
        {
            if (op.getNumber() == number)
                return op;
        }

        return null;
    }
}
