package com.thorsteinnth.kth.mmse.sepcli.Controllers;

import com.thorsteinnth.kth.mmse.sepcli.CliHelper;
import com.thorsteinnth.kth.mmse.sepcli.UIOperation;

import java.util.ArrayList;

public abstract class BaseController
{
    public abstract void displayPage();

    public void displayUIOperations(ArrayList<UIOperation> operations, UIOperation.Command onError)
    {
        ArrayList<String> validInputs = new ArrayList<String>();

        CliHelper.write("Please select one of the following operations:");

        for (UIOperation op : operations)
        {
            CliHelper.write(op.toDisplayString());
            validInputs.add(Integer.toString(op.getNumber()));
        }

        String input = CliHelper.getInput("Select an operation", validInputs);

        UIOperation selectedOperation = UIOperation.getUIOperationWithNumberFromList(
                Integer.parseInt(input),
                operations
        );

        if (selectedOperation == null)
        {
            CliHelper.write("ERROR: Unknown operation selected");
            onError.execute();
        }
        else
        {
            selectedOperation.executeCommand();
        }
    }
}
