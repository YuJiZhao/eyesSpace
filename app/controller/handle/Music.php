<?php
namespace app\controller\handle;
use app\BaseResponse;
use app\common\sdk\Netease;

class Music extends BaseResponse
{
    /**
     * 返回音乐列表
     */
    public function list() {
        $res = Netease::list();
        return $this->create($res[0], $res[1], $res[2]);
    }
}