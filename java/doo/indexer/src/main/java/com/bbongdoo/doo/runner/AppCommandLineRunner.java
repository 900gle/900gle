package com.bbongdoo.doo.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Slf4j
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
    public void run(String... args) {
        exitCode = new CommandLine(appCommand, factory).setExitCodeExceptionMapper(appCommand)
            .execute(args);
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }
}
