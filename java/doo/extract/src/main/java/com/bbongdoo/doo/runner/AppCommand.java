package com.bbongdoo.doo.runner;

import com.bbongdoo.doo.service.DynamicIndex;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import picocli.CommandLine.*;

import java.io.IOException;
import java.util.concurrent.Callable;

@Component
@Command(name = "java -jar extract.jar", mixinStandardHelpOptions = true,
        version = "1.0.0",
        description = "extract"
)
@RequiredArgsConstructor
public class AppCommand implements Callable<Integer>, IExitCodeExceptionMapper {


    private final DynamicIndex dynamicIndex;

    @ArgGroup(exclusive = true, multiplicity = "3", validate = false)
    Exclusive exclusive;
    @Parameters(index = "0", paramLabel = "Last Name", description = "value:[L]")
    private String lastName;
    @Parameters(index = "1", paramLabel = "First Name", description = "value:[D]")
    private String firstName;
    @Parameters(index = "2", paramLabel = "Second Name", description = "value:[H]")
    private String secondName;

    @Override
    public int getExitCode(Throwable exception) {

        exception.printStackTrace();
        return 0;
    }


    @Override
    public Integer call() throws Exception {

        if (!lastName.equals("L")) {
            System.out.println("H 가 아님");
            throw new IOException();
        }

        if (!firstName.equals("D")) {
            System.out.println("H 가 아님");
            throw new IOException();
        }

        if (!secondName.equals("H")) {
            System.out.println("H 가 아님");
            throw new IOException();
        }
        dynamicIndex.startDynamic();

        return ExitCode.OK;
    }

    static class Exclusive {

        @Option(names = {"-l", "--LastName"}, required = true, description = "last name value")
        private boolean isLastName;

        @Option(names = {"-d", "--FirstName"}, required = true, description = "first name value")
        private boolean isFirstName;

        @Option(names = {"-h", "--SecondName"}, required = true, description = "second name value")
        private boolean isSecondName;
    }
}
