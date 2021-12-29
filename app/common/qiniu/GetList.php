<?php

namespace app\common\qiniu;
use think\facade\Config;
use Qiniu\Auth;
use Qiniu\Storage\BucketManager;

class GetList
{

    /**
     * 获取七牛云某空间下资源列表
     * @param $request
     */
    public static function qiList()
    {
        $bucket = Config::get('sdk.qiniu.videoBucket');
        $auth = new Auth(Config::get('sdk.qiniu.ak') , Config::get('sdk.qiniu.sk'));
        $bucketManager = new BucketManager($auth);
        // 要列取文件的公共前缀
        $prefix = '';
        // 上次列举返回的位置标记，作为本次列举的起点信息。
        $marker = '';
        // 本次列举的条目数
        $limit = 3;
        // 列举文件
        list($ret, $err) = $bucketManager->listFiles($bucket, $prefix, $marker, $limit);
        if ($err !== null) {
            return var_dump($err);
        } else {
            if (array_key_exists('marker', $ret)) {
                echo $ret["marker"] . "\n";
            }
            return var_dump($ret['items']);
        }
    }
}