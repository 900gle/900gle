package com.doo.aqqle.service;

import com.doo.aqqle.vo.IndexerLogVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("AqqleLogger")
public class IndexerLoggerService {
    public void saveLog(IndexerLogVO indexerLogVO){
       log.info("indexer Log : {}", indexerLogVO.getType() );
    }
}
