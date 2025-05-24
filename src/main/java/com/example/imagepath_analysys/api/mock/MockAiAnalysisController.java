package com.example.imagepath_analysys.api.mock;

import com.example.imagepath_analysys.api.mock.enums.ApiErrorCode;
import com.example.imagepath_analysys.api.mock.model.AiAnalysisModel;
import com.example.imagepath_analysys.api.mock.model.EstimatedData;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/")
public class MockAiAnalysisController {

    private static final Logger logger = LoggerFactory.getLogger(MockAiAnalysisController.class);

    @PostMapping
    public ResponseEntity<AiAnalysisModel> estimate(@RequestParam("image_path") @NotNull String path) {

        AiAnalysisModel response;

        // （画像解析処理を行う）
        // エラーパターン
//        response = new AiAnalysisModel(ApiErrorCode.E50012);
//        return ResponseEntity.ok(response);

        // 成功パターン
        Random rand = new Random();
        int clazz = rand.nextInt(3); // 0, 1, 2 のいずれか
        BigDecimal confidence = BigDecimal.valueOf(rand.nextDouble()).setScale(2, RoundingMode.HALF_UP);
        response = new AiAnalysisModel(new EstimatedData(clazz, confidence));

        return ResponseEntity.ok(response);
    }

}
