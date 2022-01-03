<?php
namespace app\middleware;
use app\common\store\Log;
use app\common\utils\Tools;


class Check
{
    /**
     * 处理请求
     *
     * @param \think\Request $request
     * @param \Closure       $next
     * @return Response
     *
     * todo: 完成请求鉴权
     */
    public function handle($request, \Closure $next)
    {
        // 请求鉴权
        $info = $request->header();

        // 携带参数
        $request->isFail = false;
        $request->bus = [];

        $response = $next($request);

        // 写入日志
        if (!$request->isFail) {
            Log::success('瞳孔', Tools::ip(), $request->url(), $request->method(), $request->param());
        }
        return $response;
    }
}
