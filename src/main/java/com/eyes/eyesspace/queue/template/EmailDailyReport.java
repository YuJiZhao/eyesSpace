package com.eyes.eyesspace.queue.template;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import lombok.AllArgsConstructor;

/**
 * @author eyesYeager
 * @date 2023/2/9 14:53
 */
@AllArgsConstructor
public class EmailDailyReport {
  private final String title;
  private final String logo;
  private final String name;

  private final Integer visitNum;
  private final Integer visitorNum;
  private final Integer commentNum;
  private final Integer leaveMsgNum;

  public String getTemplate() {
    // 昨天
    Date date = new Date();
    SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.add(Calendar.DAY_OF_MONTH, -1);
    String yesterday = dfDate.format(c.getTime());

    // 此刻
    SimpleDateFormat dfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String now = dfTime.format(date);

    return
        "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
            + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n"
            + "  <head>\n"
            + "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"
            + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n"
            + "    <title>" + title + "</title>\n"
            + "  </head>\n"
            + "  <body style=\"margin: 0; padding: 0;\">\n"
            + "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n"
            + "      <tr>\n"
            + "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"330\" style=\"border-collapse: collapse;\">\n"
            + "          <tr style=\"border-bottom: 2px solid rgb(39, 88, 113);\">\n"
            + "            <td style=\"font-size: 25px;color: rgb(39, 88, 113);\">" + title + "</td>\n"
            + "            <td></td>\n"
            + "            <td align=\"right\"><img style=\"border:none; outline:none; text-decoration:none; -ms-interpolation-mode: bicubic;\" width=\"50\" height=\"50\" src=\" " + logo + " \" alt=\"logo\"></td>\n"
            + "          </tr>\n"
            + "        </table>\n"
            + "      </tr>\n"
            + "\n"
            + "      <tr>\n"
            + "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"330\" style=\"border-collapse: collapse;\">\n"
            + "            <tr style=\"height: 20px;\"></tr>\n"
            + "            <tr>\n"
            + "              <td>" + yesterday + " 数据：</td>\n"
            + "            </tr>\n"
            + "            <tr style=\"height: 20px;\"></tr>\n"
            + "            <tr>\n"
            + "              <td>访问量：" + visitNum + "</td>\n"
            + "              <td>访问人数：" + visitorNum + "</td>\n"
            + "            </tr>\n"
            + "            <tr style=\"height: 10px;\"></tr>\n"
            + "            <tr>\n"
            + "              <td>评论数：" + commentNum + "</td>\n"
            + "              <td>留言数：" + leaveMsgNum + "</td>\n"
            + "            </tr>\n"
            + "          </table>\n"
            + "      </tr>\n"
            + "\n"
            + "      <tr>\n"
            + "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"330\" style=\"border-collapse: collapse;\">\n"
            + "          <tr style=\"height: 150px;\"></tr>\n"
            + "          <tr>\n"
            + "            <td align=\"right\" style=\"height: 30px;\">" + name + "</td>\n"
            + "          </tr>\n"
            + "          <tr>\n"
            + "            <td align=\"right\">" + now + "</td>\n"
            + "          </tr>\n"
            + "        </table>\n"
            + "      </tr>\n"
            + "    </table>\n"
            + "  </body>\n"
            + "</html>";
  }
}