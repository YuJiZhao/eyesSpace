package com.eyes.eyesspace.service.backstage.monitor;

import com.eyes.eyesspace.common.service.MqModel.LoginVeriModel;
import com.eyes.eyesspace.service.backstage.template.EmailVeriCodeTemplate;
import com.eyes.eyesspace.service.backstage.utils.EmailUtils;
import com.eyes.eyesspace.common.tool.context.ConfigContext;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EmailMonitor {
    private final EmailUtils emailUtils;

    public EmailMonitor(EmailUtils emailUtils) {
        this.emailUtils = emailUtils;
    }

    @RabbitListener(queues = "email.loginVeri")
    public void sendLoginVeriEmail(LoginVeriModel loginVeriModel) {
        emailUtils.sendHtmlMail(
                loginVeriModel.getEmail(),
                loginVeriModel.getSubject(),
                new EmailVeriCodeTemplate(
                        ConfigContext.getString("APP_NAME_CN"),
                        ConfigContext.getString("APP_AUTHOR_CN"),
                        loginVeriModel.getRandomString(),
                        (int) (ConfigContext.getLong("EMAIL_CODE_VALIDITY") / 60)
                ).getTemplate()
        );
    }
}
