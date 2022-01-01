<?php
namespace app\common\tools;


class Ip
{
    /**
     * 获取用户ip
     *
     * @return string
     */
    public static function ip()
    {
        $ip = FALSE;
        //客户端IP 或 NONE
        if (!empty($_SERVER["HTTP_CLIENT_IP"])) {
            $ip = $_SERVER["HTTP_CLIENT_IP"];
        }
        //多重代理服务器下的客户端真实IP地址（可能伪造）,如果没有使用代理，此字段为空
        if (!empty($_SERVER["HTTP_X_FORWARDED_FOR"])) {
            $ips = explode(",", $_SERVER["HTTP_X_FORWARDED_FOR"]);
            if ($ip) {
                array_unshift($ips, $ip);
                $ip = FALSE;
            }
            for ($i = 0; $i < count($ips); $i++) {
//                if (!eregi("^(10│172.16│192.168).", $ips[$i])) {
//                    $ip = $ips[$i];
//                    break;
//                }
            }
        }
        //客户端IP 或 (最后一个)代理服务器 IP
        return ($ip ? $ip : $_SERVER["REMOTE_ADDR"]);
    }
}