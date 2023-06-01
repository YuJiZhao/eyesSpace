package com.eyes.eyesspace.utils.email;

import lombok.AllArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
public class EmailCommentNotice {
    private final String title;
    private final String name;

    private final String url;
    private final String content;

    public String getTemplate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());
        return  "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "  <head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "    <title>" + title + "</title>\n" +
                "  </head>\n" +
                "\n" +
                "  <body style=\"margin: 0; padding: 0;\">\n" +
                "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "      <tr>\n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"330\" style=\"border-collapse: collapse;\">\n" +
                "          <tr style=\"border-bottom: 2px solid rgb(39, 88, 113);\">\n" +
                "            <td style=\"font-size: 25px;color: rgb(39, 88, 113);\">" + title + "</td>\n" +
                "          </tr>\n" +
                "        </table>\n" +
                "      </tr>\n" +
                "\n" +
                "      <tr>\n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"330\" style=\"border-collapse: collapse;\">\n" +
                "            <tr style=\"height: 20px;\"></tr>\n" +
                "            <tr>\n" +
                "                <td>用户发表评论：" + content + "</td>\n" +
                "            </tr>\n" +
                "            <tr style=\"height: 20px;\"></tr>\n" +
                "            <tr>\n" +
                "                <td>详情参见：<a href=\" " + url + " \" style=\"text-decoration: none; color: rgb(39, 88, 113);\">评论地址</a></td>\n" +
                "            </tr>\n" +
                "          </table>\n" +
                "      </tr>\n" +
                "\n" +
                "      <tr>\n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"330\" style=\"border-collapse: collapse;\">\n" +
                "          <tr style=\"height: 150px;\"></tr>\n" +
                "          <tr>\n" +
                "            <td align=\"right\" style=\"height: 30px;\">" + name + "</td>\n" +
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
