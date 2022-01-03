<?php
namespace app\common\store;
use app\common\utils\Tools;
use think\facade\Log as facadeLog;
use think\Request;

class Log
{
    /**
     * todo: Log获取不到isFail
     */

    /**
     * 合法请求
     *
     * @param string $operator
     * @param string $ip
     * @param string $path
     * @param string $method
     * @param array $para
     * return null
     */
    public static function success(
        string $operator,
        string $ip,
        string $path,
        string $method,
        array $para
    )
    {
        facadeLog::info([
            'operator: ' . $operator,
            'ip: ' . $ip,
            'path: ' . $path,
            'method: ' . $method,
            'para: ' . Tools::arrToStr($para),
        ]);
    }

    /**
     * 非法请求
     *
     * @param string $operator
     * @param string $ip
     * @param string $message
     * @param string $path
     * @param string $method
     * @param array $paras
     * @param Request $request
     * return null
     */
    public static function fail(
        string $operator,
        string $ip,
        string $message,
        string $path,
        string $method,
        array $paras,
        Request $request
    )
    {
        facadeLog::warning([
            'operator: ' . $operator,
            'ip: ' . $ip,
            'message: ' . $message,
            'path: ' . $path,
            'method: ' . $method,
            'paras: ' . Tools::arrToStr($paras)
        ]);
        $request->isFail = true;
    }

    /**
     * 请求出错
     *
     * @param string $operator
     * @param string $ip
     * @param string $message
     * @param string $path
     * @param string $method
     * @param array $paras
     * @param Request $request
     * @param array $other
     * return null
     */
    public static function error(
        string $operator,
        string $ip,
        string $message,
        string $path,
        string $method,
        array $paras,
        Request $request,
        array $other = []
    )
    {
        facadeLog::error([
            'operator: ' . $operator,
            'ip: ' . $ip,
            'message: ' . $message,
            'path: ' . $path,
            'method: ' . $method,
            'paras: ' . Tools::arrToStr($paras),
            'other: ' . implode(' & ', $other)
        ]);
        $request->isFail = true;
    }
}