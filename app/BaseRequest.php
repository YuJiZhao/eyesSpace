<?php
namespace app;
use app\common\store\Log;
use app\common\utils\Tools;
use think\facade\Request;
use GuzzleHttp\Client;
use GuzzleHttp\Exception\GuzzleException;

class BaseRequest
{
    /**
     * 同步请求
     *
     * @param string $url
     * @param string $method
     * @param array $data
     * @param $callback
     * @param array $head
     *
     * todo: 返回数据只能处理成字符串
     */
    public static function syncRequest(string $url, string $method, Array $data, $callback, Array $head = []) {
        /**
         * @var $bus
         * @key string | array data
         * @key string msg
         * @key int code
         * @key array header
         * @key int size
         */
        $bus = [
            'data'      => '',
            'msg'       => '',
            'code'      => 0,
            'header'    => [],
            'size'      => 0
        ];
        if ($url == '' || $method == '') {
            $bus['msg'] = 'param error';
            $bus['code'] = 400;
            return $callback($bus);
        }
        try {
            $client = new Client([ 'timeout'  => 2.0 ]);
            $response = $client->request($method, $url, $data);
        } catch (GuzzleException $e){
            Log::error('瞳孔', Tools::ip(), '同步请求错误', Request::url(), Request::method(), Request::param(), [
                'url: '     => $url,
                'method: '  => $method,
                'data: '    => Tools::arrToStr($data),
//                'msg: '     => serialize($e)
            ]);
            $bus['msg'] = 'request error';
            $bus['code'] = 400;
            return $callback($bus);
        }
        $bus['data'] = $response->getBody()->getContents();
        $bus['msg'] = $response->getReasonPhrase();
        $bus['code'] = $response->getStatusCode();
        $bus['size'] = $response->getBody()->getSize();
        if ($head != []) {
            foreach ($head as $key) {
                array_merge($bus['header'], [ $key => $response->getHeader($key) ]);
            }
        }
        return $callback($bus);
    }

    /**
     * 异步请求
     */
    public static function asynRequest() {

    }

    /**
     * 并发请求
     */
    public static function promRequest() {

    }

    /**
     * 上传文件
     */
    public static function fileRequest() {

    }
}