<?php
namespace app;
use app\common\store\Log;
use app\common\utils\Tools;
use think\facade\Config;
use think\facade\Request;
use think\Response;

/**
 * Class Base
 * @package app\controller
 *
 * todo: 将其改造成静态
 */
class BaseResponse
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
        //获取分页
        $this->page = (int)Request::param('page');

        //获取条数
        $this->pageSize = (int)Request::param('page_size', Config::get('app.page_size'));
    }

    /**
     * @param $data
     * @param string $msg
     * @param int $code
     * @param string $type
     * @return Response
     */
    public static function create($data, string $msg = '', int $code = 200, string $type = 'json') : Response
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
        Log::fail('瞳孔', Tools::ip(), '请求方法不存在', Request::url(), Request::method(), Request::param());
        return redirect(Config::get('route.redirectPath'));
    }
}