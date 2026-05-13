package com.doo.aqqle.runner;


//import com.doo.aqqle.service.IndexerService;
//import com.doo.aqqle.service.InvestmentService;
//import com.doo.aqqle.service.TestIndexService;
import com.doo.aqqle.domain.Users;
import com.doo.aqqle.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import picocli.CommandLine.*;

import java.util.concurrent.Callable;

@Slf4j
@Component
@Command(name = "java -jar producer.jar", mixinStandardHelpOptions = true,
        version = "1.0.0",
        description = "producer")
@RequiredArgsConstructor
public class AppCommand implements Callable<Integer>, IExitCodeExceptionMapper {


    private final UserService userService;

    @ArgGroup(exclusive = true, multiplicity = "1", validate = false)
    Exclusive exclusive;
    @Parameters(index = "0", paramLabel = "indexer type", description = "indexer type [ S | D ]")
    private String type;

    @Override
    public Integer call() throws Exception {

        switch (type) {
            case "S":
                userService.postFinish(Users.builder().name("이두현").build());
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
        @Option(names = {"-t", "--producer type"}, required = true, description = "producer type value")
        private boolean isType;

    }
}