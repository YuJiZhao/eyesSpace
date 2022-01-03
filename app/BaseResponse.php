<?php
namespace app;
use app\common\store\Log;
use app\common\utils\Tools;
use think\facade\Config;
use think\Request;
use think\Response;

/**
 * Class Base
 * @package app\controller
 */
abstract class BaseResponse
{
    /**
     * @var int
     */
    protected $page;

    /**
     * @var int
     */
    protected $pageSize;

    /**
     * @var Request $request
     */

    /**
     * Base constructor.
     */
    public function __construct()
    {
        // 初始化请求类
        $request = new Request();

        //获取分页
        $this->page = (int)$request->param('page');

        //获取条数
        $this->pageSize = (int)$request->param('page_size', Config::get('app.page_size'));
    }

    /**
     * @param $data
     * @param string $msg
     * @param int $code
     * @param string $type
     * @return Response
     */
    protected function create($data, string $msg = '', int $code = 200, string $type = 'json') : Response
    {
        //标准api结构生成
        $result = [
            //状态码
            'code'  => $code,
            //消息
            'msg'   => $msg,
            //数据
            'data'  => $data
        ];

        //返回api接口
        return Response::create($result, $type);
    }

    /**
     * @param $name
     * @param $arguments
     * @return Response
     */
    public function __call($name, $arguments)
    {
        //404，方法不存在的错误
        Log::fail('瞳孔', Tools::ip(), '请求方法不存在', $this->request->url(), $this->request->method(), $this->request->param(), $this->request);
        return redirect(Config::get('route.redirectPath'));
    }
}