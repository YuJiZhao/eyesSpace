<?php
namespace app\controller\handle;
use app\BaseResponse;
use app\common\picture\watermark;
use think\facade\Config;
use think\Request;

class Picture extends BaseResponse
{
    /**
     * 水印图
     * @return resource
     */
    public function watermark(Request $request) {
        if(!$request->isGet()) return redirect(Config::get('route.redirectPath'));
        return watermark::wm();
    }

    /**
     * 缩略图
     * @return resource
     */
    public function thumbnail(Request $request) {
        if(!$request->isGet()) return redirect(Config::get('route.redirectPath'));
        return 'hello';
    }

    /**
     * 签名加密图片
     * @return resource
     */
    public function encryption(Request $request) {
        if(!$request->isGet()) return redirect(Config::get('route.redirectPath'));
        return 'hello';
    }

    /**
     * 签名加密水印图
     * @return resource
     */
    public function wmEncrypt(Request $request) {
        if(!$request->isGet()) return redirect(Config::get('route.redirectPath'));
        return watermark::wm();
    }

    /**
     * 签名加密缩略图
     * @return resource
     */
    public function thumEncrypt(Request $request) {
        if(!$request->isGet()) return redirect(Config::get('route.redirectPath'));
        return 'hello';
    }
}