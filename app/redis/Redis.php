<?php
// +----------------------------------------------------------------------
// |Redis 服务类
// +----------------------------------------------------------------------
namespace app\redis;
use Predis\Client;
use think\facade\Config;

class Redis
{
    /**
     * @var Redis
     */
    public $redis;

    /**
     * Base constructor.
     */
    public function __construct()
    {
        $this->redis = new Client([
            'host' => Config::get('cache.stores.redis.host'),
            'port' => Config::get('cache.stores.redis.port'),
            'scheme' => Config::get('cache.stores.redis.scheme'),
            'cache' => Config::get('cache.stores.redis.cache'),
            'password' => Config::get('cache.stores.redis.password'),
        ]);
    }

    /**
     * Set/setex封装
     *
     * @describe 可直接传入数组,可设置过期时间
     * @param $key
     * @param $value
     * @param int $expire
     * @return null
     */
    public function RedisSet($key, $value, $expire = 0){
        if(!$key||!$value) return false;
        $value = is_array($value) ? json_encode($value) : $value;
        $expire > 0 ? $this->redis->setex($key, $expire, $value)
                    : $this->redis->set($key, $value);
    }

    /**
     * get封装
     *
     * @describe 如果传入的是数组,返回的也是数组,字符串同理
     * @param $key
     * @return string | array
     */
    public function RedisGet($key){
        $result = $this->redis->get($key);
        return is_null(json_decode($result)) ? $result : json_decode($result, true);
    }
}
