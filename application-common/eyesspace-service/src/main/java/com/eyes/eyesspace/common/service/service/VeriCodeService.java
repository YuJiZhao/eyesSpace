package com.eyes.eyesspace.common.service.service;

import com.eyes.eyesspace.common.service.MqModel.LoginVeriModel;
import com.eyes.eyesspace.common.tool.context.ConfigContext;
import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.common.tool.context.MQContext;
import com.eyes.eyesspace.common.tool.utils.ImgUtils;
import com.eyes.eyesspace.common.service.utils.RedisUtils;
import com.eyes.eyesspace.common.service.utils.SecurityUtils;
import com.eyes.eyesspace.common.feign.module.dto.VeriCodeEmailCheckDto;
import com.eyes.eyesspace.common.feign.module.dto.VeriCodeEmailDto;
import com.eyes.eyesspace.common.feign.module.dto.VeriCodeImgCheckDto;
import com.eyes.eyesspace.common.feign.module.dto.VeriCodeImgDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;

@Service
public class VeriCodeService {
    private final RedisUtils redisUtils;
    private final RabbitTemplate rabbitTemplate;

    public VeriCodeService(RedisUtils redisUtils, RabbitTemplate rabbitTemplate) {
        this.redisUtils = redisUtils;
        this.rabbitTemplate = rabbitTemplate;
    }

    public VeriCodeImgDto getImgVeriCode() throws CustomException {
        String randomString = getRandomString(ConfigContext.getInt("IMG_CODE_DIGIT"));
        return new VeriCodeImgDto(
                ImgUtils.getBase64Img(randomString),
                SecurityUtils.OneWayHash(randomString.toLowerCase())
        );
    }

    public void getEmailVeriCode(VeriCodeEmailDto veriCodeEmailDto) throws CustomException {
        String randomString = getRandomString(ConfigContext.getInt("EMAIL_CODE_DIGIT"));
        // 将验证码存入redis
        if(!redisUtils.set(
                veriCodeEmailDto.getKey(),
                randomString,
                ConfigContext.getLong("EMAIL_CODE_VALIDITY")
        )) { throw new CustomException("验证码生成错误，多试几次看看"); }

        // 发送邮件
        rabbitTemplate.convertAndSend(MQContext.EMAIL_LOGIN_VERI.getQueue(), new LoginVeriModel(
                veriCodeEmailDto.getEmail(),
                veriCodeEmailDto.getSubject(),
                randomString
        ));
    }

    public void checkImgVeriCode(VeriCodeImgCheckDto veriCodeImgCheckDto) throws CustomException {
        if (!SecurityUtils.OneWayHash(veriCodeImgCheckDto.getCode().toLowerCase()).equals(veriCodeImgCheckDto.getKey())) {
            throw new CustomException("验证码错误");
        }
    }

    public void checkEmailVeriCode(VeriCodeEmailCheckDto veriCodeEmailCheckDto) throws CustomException {
        Object value = redisUtils.get(veriCodeEmailCheckDto.getKey());
        if(Objects.isNull(value)) {
            throw new CustomException("邮箱错误或验证码过期");
        }
        if(!value.toString().equalsIgnoreCase(veriCodeEmailCheckDto.getCode())) {
            throw new CustomException("验证码错误");
        }
        redisUtils.del(veriCodeEmailCheckDto.getKey());
    }

    /**
     * 生成指定位数随机字符串
     * @param length
     * @return
     */
    private static String getRandomString(int length) {
        String str = "abcdefghijkmnprstuvwxyABCDEFGHJKLMNPQRSTUVWXY23456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; i++){
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
