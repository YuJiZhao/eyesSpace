<?php
namespace app\controller\handle;
use app\BaseResponse;
use think\facade\Config;
use think\Request;
use app\common\picture\IdentiCode as PictureIdentiCode;

class IdentiCode extends BaseResponse
{
    /**
     * 汉字验证码
     * @return resource
     */
    public static function ccode(Request $request) {
        if(!$request->isGet()) return redirect(Config::get('route.redirectPath'));
        return PictureIdentiCode::ccode();
    }

    /**
     * 数字字母验证码
     * @return resource
     */
    public static function lcode(Request $request) {
        if(!$request->isGet()) return redirect(Config::get('route.redirectPath'));
        return PictureIdentiCode::lcode();
    }

    /**
     * 第三方验证
     * @return resource
     */
}