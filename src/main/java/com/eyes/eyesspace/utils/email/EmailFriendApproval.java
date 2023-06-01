package com.eyes.eyesspace.utils.email;

import com.eyes.eyesspace.persistent.po.FriendOperatePO;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.AllArgsConstructor;

/**
 * @author eyesYeager
 * @date 2023/6/1 14:15
 */
@AllArgsConstructor
public class EmailFriendApproval {
  private final String title;
  private final String subject;
  private final String name;

  private final String siteUrl;
  private final String avatar;
  private final String friendUrl;
  private final String introduce;
  private final FriendOperatePO friendOperatePO;
  private final String notes;

  public String getTemplate() {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String date = df.format(new Date());
    return  "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
        + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n"
        + "  <head>\n"
        + "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"
        + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n"
        + "    <title>" + title + subject + "</title>\n"
        + "  </head>\n"
        + "\n"
        + "  <body style=\"margin: 0; padding: 0;\">\n"
        + "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n"
        + "      <tr>\n"
        + "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"340\" style=\"border-collapse: collapse;\">\n"
        + "          <tr style=\"border-bottom: 2px solid rgb(39, 88, 113);\">\n"
        + "            <td style=\"font-size: 25px;color: rgb(39, 88, 113);\">" + title + subject + "</td>\n"
        + "          </tr>\n"
        + "        </table>\n"
        + "      </tr>\n"
        + "      <tr>\n"
        + "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"340\" style=\"border-collapse: collapse;\">\n"
        + "          <tr style=\"height: 20px;\"></tr>\n"
        + "          <tr><td>您的友链已通过审核，<a href=\"" + siteUrl + friendUrl + "\">查看地址</a></td></tr>\n"
        + "          <tr style=\"height: 10px;\"></tr>\n"
        + "          <tr><td>请核对相关信息：</td></tr>\n"
        + "          <tr><td><ul>\n"
        + "            <li>名称：" + friendOperatePO.getName() + "</li>\n"
        + "            <li>简介：" + friendOperatePO.getIntroduce() + "</li>\n"
        + "            <li>图片：<a href=\"" + friendOperatePO.getAvatar() + "\">图片地址</a></li>\n"
        + "            <li>网址：<a href=\"" + friendOperatePO.getAddress() + "\">网站地址</a></li>\n"
        + "          </ul></td></tr>\n"
        + "          <tr style=\"height: 10px;\"></tr>\n"
        + "          <tr><td>本站友链信息：</td></tr>\n"
        + "          <tr><td><ul>\n"
        + "            <li>名称：" + title + "</li>\n"
        + "            <li>简介：" + introduce + "</li>\n"
        + "            <li>图片：<a href=\"" + avatar + "\" target=\"_blank\">图片地址</a></li>\n"
        + "            <li>网址：" + siteUrl + "</li>\n"
        + "          </ul></td></tr>\n"
        + "          <tr style=\"height: 10px;\"></tr>\n"
        + "          <tr><td>备注：" + notes + "</td></tr>\n"
        + "          <tr style=\"height: 20px;\"></tr>\n"
        + "          <tr><td>如有问题，请直接回复本邮件，或者前往本站留言。很高兴成为您的网络邻居!</td></tr>\n"
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
