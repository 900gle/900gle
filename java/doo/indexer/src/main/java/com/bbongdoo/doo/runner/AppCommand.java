package com.bbongdoo.doo.runner;


import com.doo.aqqle.service.IndexerService;
import com.doo.aqqle.service.TestIndexService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import picocli.CommandLine.*;

import java.util.concurrent.Callable;

@Slf4j
@Component
@Command(name = "java -jar indexer.jar", mixinStandardHelpOptions = true,
        version = "1.0.0",
        description = "indexer")
@RequiredArgsConstructor
public class AppCommand implements Callable<Integer>, IExitCodeExceptionMapper {


    private final IndexerService indexerService;
    private final TestIndexService testIndexService;

    @ArgGroup(exclusive = true, multiplicity = "1", validate = false)
    Exclusive exclusive;
    @Parameters(index = "0", paramLabel = "indexer type", description = "indexer type [ S | D ]")
    private String type;

    @Override
    public Integer call() throws Exception {

        switch (type) {
            case "C":
                indexerService.index("ddd");
                break;
            case "D":
                indexerService.index(type);
                break;
            case "T":
                testIndexService.index();
                break;

        }
        return ExitCode.OK;
    }

    @Override
    public int getExitCode(Throwable exception) {

        exception.printStackTrace();
        return 0;
    }

    static class Exclusive {
        @Option(names = {"-t", "--indexer type"}, required = true, description = "indexer type value")
        private boolean isType;

    }
}