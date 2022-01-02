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
     */
    public function handle($request, \Closure $next)
    {
        $info = $request->header();
        // 请求验证
        if ($request->param('name')) {

        }
        $response = $next($request);
        // 写入日志
        Log::success('瞳孔', Tools::ip(), $request->url(), $request->method(), $request->param());
        return $response;
    }
}
