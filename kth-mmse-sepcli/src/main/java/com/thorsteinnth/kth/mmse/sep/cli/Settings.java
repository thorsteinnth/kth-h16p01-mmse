package com.thorsteinnth.kth.mmse.sep.cli;

import com.beust.jcommander.Parameter;

public class Settings {

    @Parameter(names = "-url", description = "Server address", required = true)
    private String url;

    @Parameter(names = "-token", description = "Authentication token", required = true)
    private String authenticationToken;

    @Parameter(names = "-month", description = "Number of month (1-12) for timesheet", required = true)
    private Integer month;

    @Parameter(names = "-year", description = "Year", required = true)
    private Integer year;

}