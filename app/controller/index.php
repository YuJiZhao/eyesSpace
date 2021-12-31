<?php
namespace app\controller;
//use think\facade;
//use think\Db;

use think\facade\Db;

class index
{
    public function index()
    {
        return Db::table('jobs')->where('job_id', 'AD_ASST')->find();
//        return "index";
    }
}