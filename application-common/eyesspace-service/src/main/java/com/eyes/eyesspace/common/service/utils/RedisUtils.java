package com.eyes.eyesspace.common.service.utils;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.common.service.bean.RedisBean;
import com.eyes.eyesspace.common.tool.utils.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
@Slf4j
public class RedisUtils {
    private final HttpServletRequest request;
    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    // 防止缓存穿透而设置的空值的过期时间
    private final Long CACHE_NULL_TTL = 2L;

    // 防止缓存穿透而设置的空值的过期时间的单位
    private final TimeUnit CACHE_UNIT = TimeUnit.MINUTES;

    // 互斥锁
    private final String LOCK_APP_KEY = "lock:app:";

    private static final ExecutorService CACHE_REBUILD_EXECUTOR = Executors.newFixedThreadPool(10);

    public RedisUtils(HttpServletRequest request, RedisTemplate<String, Object> redisTemplate, StringRedisTemplate stringRedisTemplate) {
        this.request = request;
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    // =============================common============================

    /**
     * 指定缓存失效时间
     * @param key  键
     * @param time 时间(秒)
     * @return boolean
     */
    public boolean expire(String key, long time) {
        if (time <= 0) return false;
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
        return true;
    }

    /**
     * 根据key 获取过期时间，单位为秒
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 根据key 获取过期时间，并指定时间单位
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public Long getExpire(String key, TimeUnit unit) {
        return redisTemplate.getExpire(key, unit);
    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 删除缓存
     * @param key 可以传一个值 或多个
     */
    public boolean del(String... key) {
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(Arrays.asList(key));
            }
            return true;
        }
        return false;
    }

    /**
     * 根据 key 前缀批量删除
     * @param prefix 前缀
     */
    public boolean DelByPrefix(String prefix) {
        Set<String> keys = redisTemplate.keys(prefix + "*");
        if(Objects.nonNull(keys)) {
            redisTemplate.delete(keys);
            return true;
        }
        return false;
    }

    // ============================String=============================

    /**
     * 普通缓存获取
     * @param key
     * @return
     */
    public Object get(String key) throws CustomException {
        try {
            return key == null ? null : redisTemplate.opsForValue().get(key);
        } catch (QueryTimeoutException e) {
            throw new CustomException("请求超时，请稍后重试", e.getMessage());
        }
    }

    /**
     * 普通缓存写入
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("缓存写入错误！key：{}，value：{}，错误信息：{}", key, value, e.getMessage());
            return false;
        }
    }

    /**
     * 普通缓存写入并设置过期时间
     * @param key
     * @param value
     * @param time  时间(秒) 如果time小于等于0，则设置不过期
     * @return
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            LogUtils.error(request, LogUtils.logMap("redis调用错误类型", "缓存写入错误！", "key", key, "value", value, "time", time, "errorMsg", e.toString()));
            return false;
        }
    }

    /**
     * 递增，默认+1
     * @param key
     * @return
     */
    public Long incr(String key) {
        return redisTemplate.opsForValue().increment(key, 1);
    }

    /**
     * 递增，自定义递增步长
     * @param key   键
     * @param step 步长
     * @return
     */
    public Long incr(String key, long step) {
        return redisTemplate.opsForValue().increment(key, step);
    }

    /**
     * 递减，默认-1
     * @param key
     * @return
     */
    public Long decr(String key) {
        return redisTemplate.opsForValue().increment(key, -1);
    }

    /**
     * 递减，默认-1
     * @param key
     * @param step 步长
     * @return
     */
    public Long decr(String key, long step) {
        return redisTemplate.opsForValue().increment(key, -step);
    }

    // ================================Map=================================

    /**
     * HashGet
     * @param key
     * @param item
     * @return
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     * @param key
     * @param map
     * @return
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashSet 并设置过期时间
     * @param key
     * @param map
     * @param time 如果小于等于0，则不设置过期时间，单位为秒
     * @return
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("HashSet写入错误！key：{}，map：{}，time：{}，错误信息：{}", key, map, time, e.getMessage());
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key
     * @param item
     * @param value
     * @return
     */
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            log.error("hset写入错误！key：{}，item：{}，value：{}，错误信息：{}", key, item, value, e.getMessage());
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据并设置过期时间,如果不存在将创建
     * @param key
     * @param item
     * @param value
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("hset写入错误！key：{}，item：{}，value：{}，time：{}，错误信息：{}", key, item, value, time, e.getMessage());
            return false;
        }
    }

    /**
     * 删除hash表中的值
     * @param key
     * @param item
     */
    public boolean hdel(String key, Object... item) {
        try {
            redisTemplate.opsForHash().delete(key, item);
            return true;
        } catch (Exception e) {
            log.error("hdel删除错误！key：{}，item：{}，错误信息：{}", key, item, e.getMessage());
            return false;
        }
    }

    /**
     * 判断hash表中是否有该项的值
     * @param key
     * @param item
     * @return
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     * @param key
     * @param item
     * @param step  步长
     * @return
     */
    public double hincr(String key, String item, double step) {
        return redisTemplate.opsForHash().increment(key, item, step);
    }

    /**
     * hash递减
     * @param key
     * @param item
     * @param step  步长
     * @return
     */
    public double hdecr(String key, String item, double step) {
        return redisTemplate.opsForHash().increment(key, item, -step);
    }

    // ============================set=============================

    /**
     * 根据key获取Set中的所有值
     * @param key
     * @return
     */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error("sGet获取错误！key：{}，错误信息：{}", key, e.getMessage());
            return null;
        }
    }

    /**
     * 查询指定set是否存在value
     * @param key
     * @param value
     * @return
     */
    public boolean sHasKey(String key, Object value) {
        try {
            return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, value));
        } catch (Exception e) {
            log.error("sHasKey查询错误！key：{}，value：{}，错误信息：{}", key, value, e.getMessage());
            return false;
        }
    }

    /**
     * 将set数据写入缓存
     *
     * @param key
     * @param values
     * @return 成功个数
     */
    public Long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            log.error("sSet写入错误！key：{}，values：{}，错误信息：{}", key, values, e.getMessage());
            return null;
        }
    }

    /**
     * 将set数据写入缓存并设置过期时间
     * @param key
     * @param time  小于等于0则不设置过期
     * @param values
     * @return 成功个数
     */
    public Long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) expire(key, time);
            return count;
        } catch (Exception e) {
            log.error("sSet写入错误！key：{}，time：{}，values：{}，错误信息：{}", key, time, values, e.getMessage());
            return null;
        }
    }

    /**
     * 获取set缓存的长度
     * @param key
     * @return
     */
    public Long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            log.error("sGetSetSize获取错误！key：{}，错误信息：{}", key, e.getMessage());
            return null;
        }
    }

    /**
     * 批量移除指定键值对
     * @param key
     * @param values
     * @return 移除成功个数
     */
    public Long setRemove(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            log.error("setRemove移除错误！key：{}，values：{}，错误信息：{}", key, values, e.getMessage());
            return null;
        }
    }

    // ===============================list=================================

    /**
     * 获取list缓存的内容
     * @param key
     * @param start
     * @param end  0到-1代表所有值
     * @return
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error("lGet获取错误！key：{}，start：{}，end：{}，错误信息：{}", key, start, end, e.getMessage());
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     * @param key 键
     * @return
     */
    public Long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            log.error("lGetListSize获取错误！key：{}，错误信息：{}", key, e.getMessage());
            return null;
        }
    }

    /**
     * 通过索引 获取list中的值
     * @param key
     * @param index index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            log.error("lGetIndex获取错误！key：{}，index：{}，错误信息：{}", key, index, e.getMessage());
            return null;
        }
    }

    /**
     * 将value写入list缓存
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            log.error("lSet写入错误！key：{}，value：{}，错误信息：{}", key, value, e.getMessage());
            return false;
        }
    }

    /**
     * 将value写入list缓存并设置过期时间
     * @param key
     * @param value
     * @param time  小于等于0则不设置过期
     * @return
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) expire(key, time);
            return true;
        } catch (Exception e) {
            log.error("lSet写入错误！key：{}，value：{}，time：{}，错误信息：{}", key, value, time, e.getMessage());
            return false;
        }
    }

    /**
     * 将list写入缓存
     * @param key
     * @param value
     * @return
     */
    public boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            log.error("lSet写入错误！key：{}，value：{}，错误信息：{}", key, value, e.getMessage());
            return false;
        }
    }

    /**
     * 将list写入缓存并设置过期时间
     * @param key
     * @param value
     * @param time  小于等于0则不设置过期
     * @return
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) expire(key, time);
            return true;
        } catch (Exception e) {
            log.error("lSet写入错误！key：{}，value：{}，time：{}，错误信息：{}", key, value, time, e.getMessage());
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     * @param key
     * @param index
     * @param value
     * @return
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            log.error("lUpdateIndex更新错误！key：{}，index：{}，value：{}，错误信息：{}", key, index, value, e.getMessage());
            return false;
        }
    }

    /**
     * 移除list中N个值为value的元素
     * @param key
     * @param count 移除数量
     * @param value
     * @return 移除成功的个数
     */
    public Long lRemove(String key, long count, Object value) {
        try {
            return redisTemplate.opsForList().remove(key, count, value);
        } catch (Exception e) {
            log.error("lRemove移除错误！key：{}，count：{}，value：{}，错误信息：{}", key, count, value, e.getMessage());
            return null;
        }
    }

    public void setString(String key, Object value, Long time, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), time, unit);
    }

    /**
     * 逻辑过期
     * @param key
     * @param value
     * @param time  过期时间
     * @param unit  过期时间单位
     */
    public void setWithLogicalExpire(String key, Object value, Long time, TimeUnit unit) {
        RedisBean redisData = new RedisBean();
        redisData.setData(value);
        redisData.setExpireTime(LocalDateTime.now().plusSeconds(unit.toSeconds(time)));
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(redisData));
    }

    /**
     * 取数据（缓存空值防止缓存穿透）
     * @param keyPrefix key前缀
     * @param id  id
     * @param type  id类型
     * @param dbFallback  缓存不存在，则通过该函数新建缓存
     * @param time  写入缓存的过期时间
     * @param unit  写入缓存的过期时间单位
     * @param <R>
     * @param <ID>
     * @return
     */
    public <R,ID> R getWithPassThrough(
            String keyPrefix,
            ID id,
            Class<R> type,
            Function<ID, R> dbFallback,
            Long time,
            TimeUnit unit
    ){
        String key = keyPrefix + id;
        String json = stringRedisTemplate.opsForValue().get(key);
        if (StrUtil.isNotBlank(json)) {
            return JSONUtil.toBean(json, type);
        }
        if (json != null) {
            return null;
        }
        // id不存在，去数据库查询
        R r = dbFallback.apply(id);
        if (r == null) {
            // 数据不存在，则存空值，防止缓存穿透
            stringRedisTemplate.opsForValue().set(key, "", CACHE_NULL_TTL, CACHE_UNIT);
            return null;
        }
        // 数据存在，则写入缓存
        this.setString(key, r, time, unit);
        return r;
    }

    /**
     * 取数据（逻辑过期防止缓存击穿）
     * @param keyPrefix
     * @param id
     * @param type
     * @param dbFallback
     * @param time
     * @param unit
     * @param <R>
     * @param <ID>
     * @return
     */
    public <R, ID> R getWithLogicalExpire(
            String keyPrefix,
            ID id,
            Class<R> type,
            Function<ID, R> dbFallback,
            Long time,
            TimeUnit unit
    ) {
        String key = keyPrefix + id;
        String json = stringRedisTemplate.opsForValue().get(key);
        if (StrUtil.isBlank(json)) {
            return null;
        }
        // 命中，需要先把json反序列化为对象
        RedisBean redisData = JSONUtil.toBean(json, RedisBean.class);
        R r = JSONUtil.toBean((JSONObject) redisData.getData(), type);
        LocalDateTime expireTime = redisData.getExpireTime();
        if(expireTime.isAfter(LocalDateTime.now())) {
            // 未过期，直接返回信息
            return r;
        }
        // 缓存重建,获取互斥锁
        String lockKey = LOCK_APP_KEY + id;
        boolean isLock = tryLock(lockKey);
        if (isLock){
            // 成功获取锁，开启独立线程，实现缓存重建
            CACHE_REBUILD_EXECUTOR.submit(() -> {
                try {
                    R newR = dbFallback.apply(id);
                    this.setWithLogicalExpire(key, newR, time, unit);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }finally {
                    unlock(lockKey);
                }
            });
        }
        return r;
    }

    /**
     * 取数据（互斥锁防止缓存击穿）
     * @param keyPrefix
     * @param id
     * @param type
     * @param dbFallback
     * @param time
     * @param unit
     * @param <R>
     * @param <ID>
     * @return
     */
    public <R, ID> R getWithMutex(
            String keyPrefix,
            ID id,
            Class<R> type,
            Function<ID, R> dbFallback,
            Long time,
            TimeUnit unit
    ) {
        String key = keyPrefix + id;
        String shopJson = stringRedisTemplate.opsForValue().get(key);
        if (StrUtil.isNotBlank(shopJson)) {
            return JSONUtil.toBean(shopJson, type);
        }
        if (shopJson != null) {
            return null;
        }

        // 实现缓存重建，获取互斥锁
        String lockKey = LOCK_APP_KEY + id;
        R r = null;
        try {
            boolean isLock = tryLock(lockKey);
            if (!isLock) {
                // 获取锁失败，休眠并重试
                Thread.sleep(50);
                return getWithMutex(keyPrefix, id, type, dbFallback, time, unit);
            }
            // 获取锁成功，根据id查询数据库
            r = dbFallback.apply(id);
            if (r == null) {
                // 将空值写入redis
                stringRedisTemplate.opsForValue().set(key, "", CACHE_NULL_TTL, TimeUnit.MINUTES);
                return null;
            }
            // 存在则写入redis
            this.setString(key, r, time, unit);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            unlock(lockKey);
        }
        return r;
    }

    private boolean tryLock(String key) {
        Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(key, "1", 10, TimeUnit.SECONDS);
        return BooleanUtil.isTrue(flag);
    }

    private void unlock(String key) {
        stringRedisTemplate.delete(key);
    }
}
