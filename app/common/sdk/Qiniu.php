<?php
namespace app\common\sdk;

use think\facade\Config;
use Qiniu\Auth;
use Qiniu\Storage\BucketManager;

class Qiniu
{
    /**
     * @var Auth
     */
    public $auth;

    /**
     * @var BucketManager
     */
    public $bucketManager;

    /**
     * @var string
     */
    public $videoBucket;

    /**
     * @var string
     */
    public $baseUrl;

    /**
     * Base constructor.
     */
    public function __construct()
    {
        // 鉴权类
        $this->auth = new Auth(Config::get('sdk.qiniu.ak') , Config::get('sdk.qiniu.sk'));

        // 空间管理类
        $this->bucketManager = new BucketManager($this->auth);

        // 视频空间名
        $this->videoBucket = Config::get('sdk.qiniu.videoBucket');

        // 页面基础地址
        $this->baseUrl = Config::get('sdk.qiniu.baseUrl');
    }

    /**
     * 获取七牛云某空间下资源列表
     * @param $request
     */
    public function list()
    {
        // 要列取文件的公共前缀
        $prefix = '';
        // 上次列举返回的位置标记，作为本次列举的起点信息。
        $marker = '';
        // 本次列举的条目数
        $limit = 3;
        // 列举文件
        list($ret, $err) = $this->bucketManager->listFiles($this->videoBucket, $prefix, $marker, $limit);
        if ($err !== null) {
            return "error";
        } else {
            return $ret;
        }
    }

    /**
     * 获取七牛云私有空间资源链接
     * @param $arr
     */
    public function signedUrl($arr)
    {
        // 对链接进行签名
        $signedUrl = $this->auth->privateDownloadUrl($this->baseUrl);
        echo $signedUrl;
    }
}