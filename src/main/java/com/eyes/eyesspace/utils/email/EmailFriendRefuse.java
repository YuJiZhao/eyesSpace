package com.eyes.eyesspace.utils.email;

import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.AllArgsConstructor;

/**
 * @author eyesYeager
 * @date 2023/6/1 15:38
 */
@AllArgsConstructor
public class EmailFriendRefuse {
  private final String title;
  private final String name;

  private final String notes;

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
        + "          <tr><td>很遗憾本站暂时无法添加您的友链！</td></tr>\n"
        + "          <tr style=\"height: 20px;\"></tr>\n"
        + "          <tr><td>原因如下：" + notes + "</td></tr>\n"
        + "          <tr style=\"height: 20px;\"></tr>\n"
        + "          <tr><td>如有其他疑问，请直接回复本邮件，或者前往本站留言。很期待成为您的网络邻居!</td></tr>\n"
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
