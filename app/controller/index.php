<?php
namespace app\controller;
use app\BaseRequest;
use app\BaseResponse;
class index
{
    public function index()
    {
        return BaseRequest::syncRequest('https://api.btstu.cn/yan/api.php', 'GET', [], function ($res){
            return BaseResponse::create($res['data'], $res['msg'], $res['code']);
        });
    }
}