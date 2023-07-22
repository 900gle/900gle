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

    @ArgGroup(exclusive = true, multiplicity = "1", validate = false)
    Exclusive exclusive;
    @Parameters(index = "0", paramLabel = "indexer type", description = "indexer type [ STATIC | DYNAMIC ]")
    private String type;


    @Override
    public int getExitCode(Throwable exception) {
        exception.printStackTrace();
        return 0;
    }

    @Override
    public Integer call() throws Exception {
        switch (type) {
            case "S":
                dynamicIndex.staticIndex();
                break;
            case "D":
                dynamicIndex.dynamicIndex();
                break;
        }
        return ExitCode.OK;
    }

    static class Exclusive {

        @Option(names = {"-t", "--indexer type"}, required = true, description = "indexer type value")
        private boolean isType;


    }
}
