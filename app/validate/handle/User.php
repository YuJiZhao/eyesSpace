<?php
namespace app\validate\handle;
use think\Validate;

class User extends Validate
{
    /**
     * 定义验证规则
     *
     * @var array
     */
    protected $rule = [
        'name'  => 'require|max:25',
        'age'   => 'number|between:1,120',
        'email' => 'email',
    ];

    /**
     * 定义错误信息
     *
     * @var array
     */
    protected $message = [
        'name.require' => '名称必须',
        'name.max'     => '名称最多不能超过25个字符',
        'age.number'   => '年龄必须是数字',
        'age.between'  => '年龄只能在1-120之间',
        'email'        => '邮箱格式错误',
    ];
}
