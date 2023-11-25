package com.eyes.eyesspace.async.template;

import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.AllArgsConstructor;

/**
 * @author eyesYeager
 * @date 2023/6/1 16:04
 */
@AllArgsConstructor
public class EmailFriendInvalidTemplate {
  private final String title;
  private final String name;

  private final int waitTime;
  private final String siteName;
  private final String siteUrl;

  public String getTemplate() {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String date = df.format(new Date());
    return  "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
        + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n"
        + "  <head>\n"
        + "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"
        + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n"
        + "    <title>" + title + "</title>\n"
        + "  </head>\n"
        + "\n"
        + "  <body style=\"margin: 0; padding: 0;\">\n"
        + "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n"
        + "      <tr>\n"
        + "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"340\" style=\"border-collapse: collapse;\">\n"
        + "          <tr style=\"border-bottom: 2px solid rgb(39, 88, 113);\">\n"
        + "            <td style=\"font-size: 25px;color: rgb(39, 88, 113);\">" + title + "</td>\n"
        + "          </tr>\n"
        + "        </table>\n"
        + "      </tr>\n"
        + "      <tr>\n"
        + "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"340\" style=\"border-collapse: collapse;\">\n"
        + "          <tr style=\"height: 20px;\"></tr>\n"
        + "          <tr><td>经系统检测，您的网站 <a href=\"" + siteUrl + "\">" + siteName + "</a> 似乎已经无法访问。</td></tr>\n"
        + "          <tr style=\"height: 10px;\"></tr>\n"
        + "          <tr><td><ul>\n"
        + "            <li>如果是地址变更，请回复新地址</li>\n"
        + "            <li>如果是暂时失效，请回复大致恢复时间</li>\n"
        + "            <li>如果有其他原因，请回复原因</li>\n"
        + "            <li>如果是永久废弃，无需回复</li>\n"
        + "          </ul></td></tr>\n"
        + "          <tr><td>自邮件发出起 " + waitTime + " 小时内没有收到回复，则您的友链将被自动删除。</td></tr>\n"
        + "          <tr style=\"height: 10px;\"></tr>\n"
        + "          <tr><td>很期待继续成为您的网络邻居!</td></tr>\n"
        + "        </table>\n"
        + "      </tr>\n"
        + "      <tr>\n"
        + "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"340\" style=\"border-collapse: collapse;\">\n"
        + "          <tr style=\"height: 150px;\"></tr>\n"
        + "          <tr><td align=\"right\" style=\"height: 30px;\">" + name + "</td></tr>\n"
        + "          <tr><td align=\"right\">" + date + "</td></tr>\n"
        + "        </table>\n"
        + "      </tr>\n"
        + "    </table>\n"
        + "  </body>\n"
        + "</html>";
  }
}
