<?php
namespace app;
use app\common\store\Log;
use app\common\utils\Tools;
use think\Request;
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
     * todo: 无法成功发送请求
     */
    public static function syncRequest(string $url, string $method, Array $data, $callback, Array $head = []) {
        if ($url == '' || $method == '') return $callback('', 'param error', 400);
        try {
            $client = new Client([ 'timeout'  => 2.0 ]);
            $response = $client->request($method, $url, [ 'query' => $data ]);
        } catch (GuzzleException $e){
            $request = new Request();
            Log::error('瞳孔', Tools::ip(), '同步请求错误', $request->url(), $request->method(), $request->param(), $request, [
                'url: '     => $url,
                'method: '  => $method,
                'data: '    => Tools::arrToStr($data),
//                'msg: '     => serialize($e)
            ]);
            return $callback('', 'request error', 400);
        }
        $headInfo = [];
        if ($head != []) {
            foreach ($head as $key) {
                array_merge($headInfo, [ $key    => $response->getHeader($key) ]);
            }
        }
        return $callback($response->getBody(), $response->getReasonPhrase(), $response->getStatusCode(), $headInfo);
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