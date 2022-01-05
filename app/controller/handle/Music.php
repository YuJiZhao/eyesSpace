<?php
namespace app\controller\handle;
use app\BaseResponse;
use app\common\sdk\Netease;

class Music
{
    /**
     * 返回音乐列表
     */
    public function list() {
        $res = Netease::list();
        header("Access-Control-Allow-Origin: *");
        return BaseResponse::create($res['data'], $res['msg'], $res['code']);
    }
}