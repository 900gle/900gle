package com.bbongdoo.doo.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
public class AppCommandLineRunner implements CommandLineRunner, ExitCodeGenerator {

    private final AppCommand appCommand;
    private final CommandLine.IFactory factory;

    private int exitCode;

    public AppCommandLineRunner(AppCommand appCommand, CommandLine.IFactory factory) {
        this.appCommand = appCommand;
        this.factory = factory;
    }

    @Override
    public void run(String... args) throws Exception {
        exitCode = new CommandLine(appCommand, factory).setExitCodeExceptionMapper(appCommand).execute(args);
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }
}
