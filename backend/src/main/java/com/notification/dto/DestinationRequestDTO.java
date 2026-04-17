package com.notification.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DestinationRequestDTO {

    @NotBlank(message = "名称不能为空")
    private String name;

    @NotBlank(message = "URL不能为空")
    private String url;

    private String apiKey;

    private String headers;

    private Integer retryCount;
}
