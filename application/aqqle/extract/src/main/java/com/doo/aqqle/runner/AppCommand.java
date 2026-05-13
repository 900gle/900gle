package com.doo.aqqle.runner;


import com.doo.aqqle.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine.*;

import java.util.Map;
import java.util.concurrent.Callable;

@Slf4j
@Component
@Command(name = "java -jar extract.jar", mixinStandardHelpOptions = true,
        version = "1.0.0",
        description = "extract")
@RequiredArgsConstructor
public class AppCommand implements Callable<Integer>, IExitCodeExceptionMapper {

    @Autowired
    private Map<String, AqqleExtract> services;

    @ArgGroup(exclusive = true, multiplicity = "1", validate = false)
    Exclusive exclusive;
    @Parameters(index = "0", paramLabel = "indexer type", description = "indexer type [ S | D | M | I]")
    private String type;

    @Override
    public Integer call() throws Exception {

        AqqleExtract service = services.get(resolveServiceKey(type));

        if (service == null) {
            log.error("Invalid type '{}'. Supported types are: S, D, M, I, Y, YD.", type);
            return ExitCode.USAGE; // 잘못된 인자로 종료
        }

        try {
            service.execute();
            return ExitCode.OK;
        } catch (Exception e) {
            log.error("Error executing service for type '{}': {}", type, e.getMessage(), e);
            return ExitCode.SOFTWARE; // 실행 중 오류 발생
        }
    }

    @Override
    public int getExitCode(Throwable exception) {
        log.error("Unexpected error occurred: {}", exception.getMessage(), exception);
        return ExitCode.SOFTWARE;
    }

    private String resolveServiceKey(String type) {
        switch (type) {
            case "D":
                return "ExtractService";
            case "I":
                return "InvestmentService";
            case "M":
                return "MergeService";
            case "Y":
                return "YahooService";
            case "YD":
                return "YahooDataService";
            default:
                return null; // 잘못된 type 값 처리
        }
    }
    static class Exclusive {
        @Option(names = {"-t", "--indexer type"}, required = true, description = "indexer type value")
        private boolean isType;

    }
}