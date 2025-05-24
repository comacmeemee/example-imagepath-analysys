package com.example.imagepath_analysys.controller;

import com.example.imagepath_analysys.entity.AiAnalysisLog;
import com.example.imagepath_analysys.form.AnalysisForm;
import com.example.imagepath_analysys.service.AnalysisService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/analysis")
public class AnalysisController {

    private static final Logger logger = LoggerFactory.getLogger(AnalysisController.class);

    @Autowired
    HttpServletRequest request;

    @Autowired
    AnalysisService analysisService;

    @GetMapping({"", "/"})
    public String input(Model model) {
        model.addAttribute("analysisForm", new AnalysisForm());
        return "analysis/index";
    }

    @PostMapping({"/submit", "/submit/"})
    public String submit(
            @ModelAttribute("analysisForm") @Valid AnalysisForm analysisForm,
            BindingResult result,
            Model model) {

        if (result.hasErrors())
            return "analysis/index";

        String path = analysisForm.getPath();

        logger.info("[start] : APIへのリクエストと保存処理 - path:{}", path);
        AiAnalysisLog log = analysisService.callAnalysisApi(path);
        logger.info("[end  ] : APIへのリクエストと保存処理 - result:{}", log.getMessage());

        model.addAttribute("result", log);
        return "analysis/index";
    }
}
