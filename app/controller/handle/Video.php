<?php
namespace app\controller\handle;
use app\BaseResponse;
use think\Request;
use app\common\sdk\Qiniu;

class Video extends BaseResponse
{
    /**
     * 返回视频列表
     *
     * @return string
     */
    public function list(Request $request) {
        // 初始化七牛云SDK对象
        $qiniu = new Qiniu();
        $list = $qiniu->list($request->post('marker'));
        // 返回结果
        if ($list == "error") {
            return $this->create('', 'error', 404);
        }
        $list["items"] = $qiniu->signedUrl($list["items"]);
        return $this->create($list, 'success');
    }
}