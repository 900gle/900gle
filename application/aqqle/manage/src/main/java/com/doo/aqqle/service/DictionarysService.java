package com.doo.aqqle.service;


import com.doo.aqqle.domain.Dictionarys;
import com.doo.aqqle.domain.DictionarysRepository;
import com.doo.aqqle.dto.DictionaryRequest;
import com.doo.aqqle.dto.KeyWordRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DictionarysService {

    private final DictionarysRepository dictionarysRepository;
    private List<Dictionarys> dictionarys;


    @Transactional
    public void post(KeyWordRequest keyWordRequest) {
        dictionarys = new ArrayList<>();
        keyWordRequest.getWords().stream().forEach(x -> {
            dictionarys.add(new Dictionarys(x, keyWordRequest.getType(), "Y"));
        });
        dictionarysRepository.saveAll(dictionarys);
    }

    @Transactional
    public List<Dictionarys> get() {
        return dictionarysRepository.findAll();
    }

    @Transactional
    public List<Dictionarys> getKeywordsByUse(String use) {
        return dictionarysRepository.findAllByUseYn(use);
    }

    @Transactional
    public List<Dictionarys> getKeywordsByKeyword(String keyword) {
        return dictionarysRepository.findAllByWord(keyword);
    }

    @Transactional
    public boolean put(Long id, String useYn) {
        Dictionarys dictionarys = dictionarysRepository.findAllById(id);
        dictionarys.setUseYn(useYn);
        dictionarysRepository.save(dictionarys);
        return true;
    }


    public boolean deploy(DictionaryRequest dictionaryRequest) {
        List<Dictionarys> dictionarys = dictionarysRepository.findByType(dictionaryRequest.getType());

        String suffix = dictionaryRequest.getType().toLowerCase();
        String directoryPath = "/Users/doo/dictionary/" + suffix;
        createDirectory(directoryPath);
        String fileName = directoryPath + "/aqqle_" + suffix + ".dic";

        try (FileWriter writer = new FileWriter(fileName)) {
            for (Dictionarys dict : dictionarys) {
                writer.write(dict.getWord() + "\n");
            }
            log.info("DIC 파일 생성 완료: {}", fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        deploys(fileName);

        return true;
    }


    private void deploys(String fileName) {
        // 원본 파일 경로
        Path source = Paths.get(fileName);
        // 대상 파일 경로
        Path target = Paths.get("/Users/doo/docker/es8.8.1/elasticsearch/config/stopFilter.txt");
        try {
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("파일 복사 완료: " + target.toAbsolutePath());

            CommandExecutor("docker compose -f /Users/doo/docker/es8.8.1/docker-compose-es-kibana.yml down");
            CommandExecutor("docker compose -f /Users/doo/docker/es8.8.1/docker-compose-es-kibana.yml up -d --build");
            CommandExecutor("docker ps -a");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void createDirectory(String path) {
        try {
            Path directory = Paths.get(path);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
                log.info("디렉터리 생성 완료: {}" , path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void CommandExecutor(String command) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("sh", "-c", command);  // Linux/MacOS용 명령어 실행
            Process process = processBuilder.start();



            // 명령어 출력 읽기
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            int exitCode = process.waitFor();
            log.info("프로세스 종료 코드: {}" , exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
