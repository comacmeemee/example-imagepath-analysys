package com.example.imagepath_analysys.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class AnalysisForm {

    // 画像パス
    @NotBlank(message = "画像パスの入力は必須です")
    @Size(max = 1000, message = "1000文字以内で入力してください")
    private String path;
}
