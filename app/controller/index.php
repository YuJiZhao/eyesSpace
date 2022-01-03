<?php
namespace app\controller;
use app\BaseRequest;

class index extends BaseRequest
{
    public function index()
    {
        return BaseRequest::syncRequest('www.baidu.com', 'GET', [], function ($a, $b, $c, $d){
            return "hello";
        });
//        return 'index';
    }
}