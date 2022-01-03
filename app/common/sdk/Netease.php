<?php
namespace app\common\sdk;
use app\BaseRequest;

class Netease
{
    /**
     * 返回音乐列表
     *
     * @return string
     */
    public static function list() {
        return BaseRequest::syncRequest('https://api.i-meto.com/meting/api', 'GET', [
            'server'    => 'netease',
            'type'      => 'playlist',
            'id'        => '6936632013',
        ], function ($res, $msg, $code, $header = []){
            return [$res, $msg, $code];
        });
    }
}