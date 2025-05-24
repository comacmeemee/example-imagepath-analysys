package com.example.imagepath_analysys.service;

import com.example.imagepath_analysys.api.mock.model.AiAnalysisModel;
import com.example.imagepath_analysys.api.mock.model.EstimatedData;
import com.example.imagepath_analysys.entity.AiAnalysisLog;
import com.example.imagepath_analysys.exception.BusinessException;
import com.example.imagepath_analysys.repository.AiAnalysisLogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AnalysisServiceTest {

    @Autowired
    private AnalysisService analysisService;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private AiAnalysisLogRepository repository;

    @Test
    void testCallAnalysisApi_正常系() throws Exception {
        // Arrange
        String path = "test/image.jpg";
        EstimatedData data = new EstimatedData();
        data.setClazz(3);
        data.setConfidence(BigDecimal.valueOf(0.92));

        AiAnalysisModel model = new AiAnalysisModel();
        model.setSuccess(true);
        model.setMessage("OK");
        model.setEstimatedData(data);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(model);

        when(restTemplate.postForEntity(anyString(), isNull(), eq(String.class)))
                .thenReturn(new ResponseEntity<>(json, HttpStatus.OK));

        // Act
        AiAnalysisLog result = analysisService.callAnalysisApi(path);

        // Assert
        assertNotNull(result);
        assertEquals(3, result.getClazz());
        assertEquals(BigDecimal.valueOf(0.92), result.getConfidence());
        assertTrue(result.isSuccess());
        assertEquals("OK", result.getMessage());
        assertEquals(path, result.getImagePath());

        verify(repository, times(1)).save(any(AiAnalysisLog.class));
    }

    @Test
    void testCallAnalysisApi_異常系_JSON形式不正() {
        // Arrange
        String path = "invalid/image.jpg";
        String invalidJson = "not-a-json";

        when(restTemplate.postForEntity(anyString(), isNull(), eq(String.class)))
                .thenReturn(new ResponseEntity<>(invalidJson, HttpStatus.OK));

        // Act & Assert
        assertThrows(BusinessException.class, () -> {
            analysisService.callAnalysisApi(path);
        });

        verify(repository, never()).save(any());
    }
}

