package com.thorsteinnth.kth.mmse.sep.cli;

import com.beust.jcommander.*;

public class SepCliApplication
{
    public static void main (String [] arguments)
    {
        System.out.println("Welcome to the SEP client.");

        //Settings settings = new Settings();
        //new JCommander(settings, arguments);

        CliHandler.getInput("GIMME SOME INPUT");
    }
}
