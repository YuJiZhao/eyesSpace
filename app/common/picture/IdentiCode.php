<?php
namespace app\common\picture;

class IdentiCode
{
    /**
     * 汉字验证码
     * @return resource
     */
    public static function ccode()
    {
        // 创建图片资源
        $img = imagecreatetruecolor(200, 50);
        // 背景色处理
        $bg_color = imagecolorallocate($img, 220, 220, 220);
        imagefill($img, 0, 0, $bg_color);
        // 获得随机文字
        $str = '验证码是一种区分用户是计算机还是人的公共全自动程序可以防止恶意破解密码刷票论坛灌水有效防止某个黑客对某一个特定注册用户用特定程序暴力破解方式进行不断的登陆尝试';
        // 获得字符串长度
        $len = strlen($str);  // 字节长度
        // 当前所有的内容都是中文，因此每个字必定占三个字节
        $c_len = $len / 3;
        // 随机取汉字
        $char1 = substr($str, mt_rand(0, $c_len - 1) * 3, 3);
        $char2 = substr($str, mt_rand(0, $c_len - 1) * 3, 3);
        $char3 = substr($str, mt_rand(0, $c_len - 1) * 3, 3);
        $char4 = substr($str, mt_rand(0, $c_len - 1) * 3, 3);
        // 选择字体
        $font = 'C:\Windows\Fonts\simhei.ttf';
        // 随机颜色
        $str_color1 = imagecolorallocate($img, mt_rand(0, 100), mt_rand(0, 100), mt_rand(0, 100));
        $str_color2 = imagecolorallocate($img, mt_rand(0, 100), mt_rand(0, 100), mt_rand(0, 100));
        $str_color3 = imagecolorallocate($img, mt_rand(0, 100), mt_rand(0, 100), mt_rand(0, 100));
        $str_color4 = imagecolorallocate($img, mt_rand(0, 100), mt_rand(0, 100), mt_rand(0, 100));
        // 写入文字
        imagettftext($img, mt_rand(20, 30), mt_rand(-45, 45), mt_rand(0, 30), mt_rand(30, 40), $str_color1, $font, $char1);
        imagettftext($img, mt_rand(20, 30), mt_rand(-45, 45), mt_rand(50, 70), mt_rand(30, 40), $str_color2, $font, $char2);
        imagettftext($img, mt_rand(20, 30), mt_rand(-45, 45), mt_rand(90, 120), mt_rand(30, 40), $str_color3, $font, $char3);
        imagettftext($img, mt_rand(20, 30), mt_rand(-45, 45), mt_rand(140, 170), mt_rand(30, 40), $str_color4, $font, $char4);
        // 设置干扰
        for ($i = 0; $i < 50; $i++) {  // 干扰点
            $dot_color = imagecolorallocate($img, mt_rand(150, 250), mt_rand(150, 250), mt_rand(150, 250)); // 设置点的颜色
            imagestring($img, mt_rand(1, 5), mt_rand(0, 200), mt_rand(1, 50), '*', $dot_color);
        }
        for ($i = 0; $i < 10; $i++) {  // 干扰线
            $line_color = imagecolorallocate($img, mt_rand(150, 250), mt_rand(150, 250), mt_rand(150, 250)); // 设置线的颜色
            imageline($img, mt_rand(0, 200), mt_rand(0, 50), mt_rand(0, 200), mt_rand(0, 50), $line_color);
        }
        header('Content-type:image/png');
        imagepng($img);
        imagedestroy($img);
    }

    /**
     * 数字字母验证码
     * @return resource
     */
    public static function lcode()
    {
        $image = imagecreate(200, 100);
        imagecolorallocate($image, 0, 0, 0);
        for ($i = 0; $i <= 9; $i++) {
            // 绘制随机的干扰线条
            imageline($image, rand(0, 200), rand(0, 100), rand(0, 200), rand(0, 100), imagecolorallocate($image, rand(127, 255), rand(127, 255), rand(127, 255)));
        }
        for ($i = 0; $i <= 100; $i++) {
            // 绘制随机的干扰点
            imagesetpixel($image, rand(0, 200), rand(0, 100), imagecolorallocate($image, rand(127, 255), rand(127, 255), rand(127, 255)));
        }
        // 验证码中所需要的字符
        $chars = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
        $str = '';
        for ($i = 0; $i < 4; $i++) {
            // 随机截取 $chars 中的任意一位字符；
            $str .= substr($chars, mt_rand(0, strlen($chars) - 1), 1);
        }
        $font = 'C:\Windows\Fonts\simhei.ttf';
        for ($i=0; $i < 4; $i++) {
            // 逐个绘制验证码中的字符
            imagettftext($image, rand(20, 38), rand(0, 60), $i*50+25, rand(30,70), imagecolorallocate($image, rand(127, 255), rand(127, 255), rand(127, 255)), $font, $str[$i]);
        }
        header('Content-type:image/jpeg');
        imagejpeg($image);
        imagedestroy($image);
    }
}
