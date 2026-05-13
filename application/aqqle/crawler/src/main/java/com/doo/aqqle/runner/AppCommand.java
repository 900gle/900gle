package com.doo.aqqle.runner;

import com.doo.aqqle.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine.*;

import java.util.Map;
import java.util.concurrent.Callable;

@Component
@Command(name = "java -jar crawler.jar", mixinStandardHelpOptions = true,
        version = "1.0.0",
        description = "crawler"
)
@RequiredArgsConstructor
public class AppCommand implements Callable<Integer>, IExitCodeExceptionMapper {

    @Autowired
    private Map<String, AqqleCrawler> services;

    @ArgGroup(exclusive = true, multiplicity = "1", validate = false)
    Exclusive exclusive;
    @Parameters(index = "0", paramLabel = "crawler type", description = "value:[ T | Y | N]")
    private String type;

    @Override
    public int getExitCode(Throwable exception) {
        exception.printStackTrace();
        return 0;
    }

    @Override
    public Integer call() throws Exception {
        switch (type) {
            case "T":
                services.get("TmonService").execute();
                break;
            case "Y":
                services.get("YahooService").execute();
                break;
            case "YD":
                services.get("YahooDataService").execute();
                break;
            case "N":
                services.get("NaverService").execute();
                break;

            default:
        }
        return ExitCode.OK;
    }

    static class Exclusive {

        @Option(names = {"-t", "--type"}, required = true, description = "crwaling target value")
        private boolean isType;

    }
}
