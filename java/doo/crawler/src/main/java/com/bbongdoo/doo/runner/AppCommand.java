package com.bbongdoo.doo.runner;

import com.bbongdoo.doo.service.CrawlerSeleniumService;
import com.bbongdoo.doo.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import picocli.CommandLine.*;

import java.io.IOException;
import java.util.concurrent.Callable;

@Component
@Command(name = "java -jar crawler.jar", mixinStandardHelpOptions = true,
        version = "1.0.0",
        description = "crawler"
)
@RequiredArgsConstructor
public class AppCommand implements Callable<Integer>, IExitCodeExceptionMapper {

    private final GoodsService goodsService;
    private final CrawlerSeleniumService crawlerSeleniumService;

    @ArgGroup(exclusive = true, multiplicity = "1", validate = false)
    Exclusive exclusive;
    @Parameters(index = "0", paramLabel = "crawler type", description = "value:[ I | T ]")
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
                goodsService.getData(type);
                break;
            case "I":
                crawlerSeleniumService.getData(type);
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
