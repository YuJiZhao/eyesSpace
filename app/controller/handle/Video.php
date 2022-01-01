<?php
namespace app\controller\handle;
use app\BaseResponse;
use app\common\store\Log;
use think\facade\Config;
use think\Request;
use app\common\sdk\Qiniu;

class Video extends BaseResponse
{
    /**
     * 返回视频列表
     *
     * @return string
     */
    public function List(Request $request) {
        // 检验请求类型
        if(!$request->isPost())
            return redirect(Config::get('route.redirectPath'));
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