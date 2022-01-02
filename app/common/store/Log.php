<?php
namespace app\common\store;
use app\common\utils\Tools;
use think\facade\Log as facadeLog;

class Log
{
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
     * return null
     */
    public static function fail(
        string $operator,
        string $ip,
        string $message,
        string $path,
        string $method,
        array $paras
    )
    {
        facadeLog::warning([
            'operator: ' . $operator,
            'ip: ' . $ip,
            'message: ' . $message,
            'path: ' . $path,
            'method: ' . $method,
            'paras: ' . Tools::arrToStr('&', $paras)
        ]);
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
    }
}