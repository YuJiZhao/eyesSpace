<?php
use think\facade\Route;
use think\facade\Config;
use think\Request;
use app\common\store\Log;
use app\common\utils\Tools;
/**
 * handle路由
 */
Route::rule('/','/');
Route::get('music/list','handle.music/list');
Route::post('video/list','handle.video/list');
Route::get('video/freedom','handle.video/freedom');

/**
 * resources路由
 */

/**
 * Miss路由
 *
 * @param \think\Request $request
 */
Route::miss(function(Request $request) {
    Log::fail('瞳孔', Tools::ip(), 'Miss路由', $request->url(), $request->method(), $request->param(), $request);
    $request->isFail = true;
    return \redirect(Config::get('route.redirectPath'));
});