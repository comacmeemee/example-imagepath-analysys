package com.example.imagepath_analysys.controller;

import com.example.imagepath_analysys.entity.AiAnalysisLog;
import com.example.imagepath_analysys.service.AnalysisService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AnalysisController.class)
class AnalysisControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnalysisService analysisService;

    @Test
    void testInput_正常系() throws Exception {
        mockMvc.perform(get("/analysis"))
                .andExpect(status().isOk())
                .andExpect(view().name("analysis/index"))
                .andExpect(model().attributeExists("analysisForm"));
    }

    @Test
    void testSubmit_正常系() throws Exception {
        String testPath = "/test/image.jpg";

        // モックデータ作成
        AiAnalysisLog mockLog = new AiAnalysisLog();
        mockLog.setSuccess(true);
        mockLog.setMessage("success");
        mockLog.setClazz(3);
        mockLog.setConfidence(BigDecimal.valueOf(0.95));

        when(analysisService.callAnalysisApi(testPath)).thenReturn(mockLog);

        mockMvc.perform(post("/analysis/submit")
                        .param("path", testPath)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("analysis/index"))
                .andExpect(model().attributeExists("result"))
                .andExpect(model().attributeExists("analysisForm"));
    }

    @Test
    void testSubmit_バリデーションエラー() throws Exception {
        // バリデーション違反（空パスなど）をテスト
        mockMvc.perform(post("/analysis/submit")
                        .param("path", "")  // 空文字でバリデーションエラーを誘発
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("analysis/index"))
                .andExpect(model().attributeHasFieldErrors("analysisForm", "path"));
    }
}