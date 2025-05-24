package com.example.imagepath_analysys.service;

import com.example.imagepath_analysys.api.mock.model.AiAnalysisModel;
import com.example.imagepath_analysys.api.mock.model.EstimatedData;
import com.example.imagepath_analysys.entity.AiAnalysisLog;
import com.example.imagepath_analysys.exception.BusinessException;
import com.example.imagepath_analysys.repository.AiAnalysisLogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class AnalysisService {

    private static final String API_ENDPOINT = "http://localhost:8080";

    private static final Logger logger = LoggerFactory.getLogger(AnalysisService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AiAnalysisLogRepository repository;

    /**
     *  画像パスを解析APIに投げて、結果をDBに保存する
     * @param path 画像パス
     * @return AiAnalysisLog
     */
    public AiAnalysisLog callAnalysisApi(String path) throws BusinessException {

        // リクエスト日時
        LocalDateTime requestTime = LocalDateTime.now();

        // APIリクエスト
        ResponseEntity<String> json = restTemplate.postForEntity(API_ENDPOINT + "?image_path=" + path, null, String.class);
        String jsonStr = json.getBody();

        ObjectMapper mapper = new ObjectMapper();
        AiAnalysisModel model;
        try {
            model = mapper.readValue(jsonStr, AiAnalysisModel.class);
        } catch (JsonProcessingException e) {
            logger.error("JSONのパースでエラーが発生しました", e);
            throw new BusinessException(e.getMessage(), e);
        }

        // リクエスト日時
        LocalDateTime responseTime = LocalDateTime.now();

        AiAnalysisLog log = new AiAnalysisLog();
        log.setImagePath(path);
        log.setSuccess(model.getSuccess());
        log.setMessage(model.getMessage());
        log.setRequestTimestamp(requestTime);
        log.setResponseTimestamp(responseTime);

        EstimatedData data = model.getEstimatedData();
        if (data != null) {
            log.setClazz(data.getClazz());
            log.setConfidence(data.getConfidence());
        }

        repository.save(log);
        return log;
    }
}
