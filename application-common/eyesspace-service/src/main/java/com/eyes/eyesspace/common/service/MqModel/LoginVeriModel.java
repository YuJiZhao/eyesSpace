package com.eyes.eyesspace.common.service.MqModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVeriModel {
    private String email;

    private String subject;

    private String randomString;
}
