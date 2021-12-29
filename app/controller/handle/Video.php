<?php
namespace app\controller\handle;
use app\BaseResponse;
use think\Request;
use app\common\qiniu\GetList;

class Video extends BaseResponse
{
    public function index()
    {
        $src_image = 'https://img.php.cn/upload/article/202008/11/2020081116405151204.jpg';
        $image = imagecreatefromjpeg($src_image);
        header('Content-type:image/jpeg; charset:utf-8');
        imagejpeg($image);
        imagedestroy($image);
    }

    public function getList(Request $request) {
        if($request->isPost()){
            $list = GetList::qiList();
            // 未完待续
            return $list;
        }
    }
}