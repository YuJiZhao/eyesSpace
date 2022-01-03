<?php
namespace app\controller;
use app\common\utils\Tools;
//use think\facade\Request;
use think\Request;
use app\common\store\Log;
use think\facade\Config;

class Error
{
    /**
     * 空控制器，系统找不到指定的控制器名称时定位到当前类
     *
     * @param $method
     * @param $args
     * @param Request $request
     * @return \think\response\Redirect
     */
    public function __call($method, $args, Request $request)
    {
        Log::fail('瞳孔', Tools::ip(), '非法路径', $request->url(), $request->method(), $request->param(), $request);
        return \redirect(Config::get('route.redirectPath'));
    }
}
