package com.eyes.eyesspace.service.backstage.template;

import lombok.AllArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
public class EmailVeriCodeTemplate {
    private final String title;
    private final String name;
    private final String code;
    private final Integer expiration;

    public String getTemplate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "  <head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "    <title>" + title + "</title>\n" +
                "  </head>\n" +
                "  <body style=\"margin: 0; padding: 0;\">\n" +
                "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "      <tr>\n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"330\" style=\"border-collapse: collapse;\">\n" +
                "          <tr style=\"border-bottom: 2px solid rgb(39, 88, 113);\">\n" +
                "            <td style=\"font-size: 25px;color: rgb(39, 88, 113);\">" + title + "</td>\n" +
                "            <td></td>\n" +
                "            <td align=\"right\"><img style=\"border:none; outline:none; text-decoration:none; -ms-interpolation-mode: bicubic;\" width=\"50\" height=\"50\" src=\"https://img1.imgtp.com/2022/06/28/qnu0FiEi.png\" alt=\"logo\"></td>\n" +
                "          </tr>\n" +
                "        </table>\n" +
                "      </tr>\n" +
                "      <tr>\n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"330\" style=\"border-collapse: collapse;\">\n" +
                "          <tr style=\"height: 20px;\"></tr>\n" +
                "          <tr>\n" +
                "            <td>您正在尝试登录操作，验证码为：</td>\n" +
                "          </tr>\n" +
                "        </table>\n" +
                "      </tr>\n" +
                "      <tr>\n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"330\" style=\"border-collapse: collapse;\">\n" +
                "          <tr style=\"height: 100px;\">\n" +
                "            <td></td>\n" +
                "            <td style=\"width: 170px; font-size: 25px;\" align=\"center\">" + code + "</td>\n" +
                "            <td></td>\n" +
                "          </tr>\n" +
                "        </table>\n" +
                "      </tr>\n" +
                "      <tr>\n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"330\" style=\"border-collapse: collapse;\">\n" +
                "          <tr>\n" +
                "            <td>验证码 " + expiration + " 分钟内有效。请勿向他人泄漏。</td>\n" +
                "          </tr>\n" +
                "        </table>\n" +
                "      </tr>\n" +
                "      <tr>\n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"330\" style=\"border-collapse: collapse;\">\n" +
                "          <tr style=\"height: 150px;\"></tr>\n" +
                "          <tr>\n" +
                "            <td align=\"right\" style=\"height: 30px;\">" + name +"</td>\n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "            <td align=\"right\">" + date + "</td>\n" +
                "          </tr>\n" +
                "        </table>\n" +
                "      </tr>\n" +
                "    </table>\n" +
                "  </body>\n" +
                "</html>";
    }
}
