<?php
namespace app\controller\handle;
use app\BaseResponse;
use think\facade\Config;
use think\Request;
use app\common\sdk\Qiniu;

class Video extends BaseResponse
{
    /**
     * 返回视频列表
     * @return string|resource
     */
    public function List(Request $request) {
        if(!$request->isPost()) return redirect(Config::get('route.redirectPath'));
        $qiniu = new Qiniu();
        $list = $qiniu->list();
        if ($list == "error") return $this->create('', 'error', 404);
        return $this->create($list, 'susscss');
    }
}