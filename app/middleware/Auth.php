<?php
declare (strict_types = 1);

namespace app\middleware;

/**
 * 路由中间件
 *
 * @param \think\Request $request
 * @param \Closure       $next
 * @return Response
 *
 * todo: 完善路由中间件
 */
class Auth
{
    public function handle($request, \Closure $next)
    {
//        if ($request->param('id') == 10) {
//            echo '是管理员！';
//        }
        return $next($request);
    }
}