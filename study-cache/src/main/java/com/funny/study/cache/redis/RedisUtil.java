package com.funny.study.cache.redis;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 */
@Component
public class RedisUtil {
    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    @Resource
    private StringRedisTemplate redisTemplate;


    /** -------------------key相关操作--------------------- */

    /**
     * 删除key
     */
    public boolean delete(String key) {
        logger.debug("删除key:{}", key);
        boolean result = true;
        try {
            redisTemplate.delete(key);
        } catch (Throwable e) {
            logger.error("异常，删除Key:{}", key, e);
            result = false;
        }
        return result;
    }

    /**
     * 批量删除key
     */
    public void delete(Collection<String> keys) {
        logger.debug("批量删除keys:{}", keys);
        try {
            redisTemplate.delete(keys);
        } catch (Throwable e) {
            logger.error("异常，批量删除keys:{}", keys, e);
        }
    }

    /**
     * 序列化key
     */
    public byte[] dump(String key) {
        logger.debug("序列化key:{}", key);
        try {
            return redisTemplate.dump(key);
        } catch (Throwable e) {
            logger.error("异常，序列化key:{}", key, e);
        }
        return null;
    }

    /**
     * 是否存在key
     */
    public Boolean hasKey(String key) {
        logger.debug("是否存在key:{}", key);
        try {
            return redisTemplate.hasKey(key);
        } catch (Throwable e) {
            logger.error("异常，是否存在key:{}", key, e);
        }
        return false;
    }

    /**
     * 设置过期时间
     */
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        logger.debug("设置过期时间key:{}", key);
        try {
            return redisTemplate.expire(key, timeout, unit);
        } catch (Throwable e) {
            logger.error("异常，设置过期时间key:{}", key, e);
        }
        return false;
    }

    /**
     * 设置过期时间
     */
    public Boolean expireAt(String key, Date date) {
        logger.debug("设置过期时间key:{}", key);
        try {
            return redisTemplate.expireAt(key, date);
        } catch (Throwable e) {
            logger.error("异常，设置过期时间key:{}", key, e);
        }
        return false;
    }

    /**
     * 查找匹配的key
     */
    public Set<String> keys(String pattern) {
        logger.debug("查找匹配的key, pattern:{}", pattern);
        try {
            return redisTemplate.keys(pattern);
        } catch (Throwable e) {
            logger.error("异常，查找匹配的key, pattern:{}", pattern, e);
        }
        return null;
    }

    /**
     * 将当前数据库的key移动到给定的数据库 db 当中
     */
    public Boolean move(String key, int dbIndex) {
        logger.debug("将当前数据库的 key:{}移动到给定的数据库 dbIndex :{}当中", key, dbIndex);
        try {
            return redisTemplate.move(key, dbIndex);
        } catch (Throwable e) {
            logger.error("异常，将当前数据库的 key:{}移动到给定的数据库 dbIndex :{} 当中", key, dbIndex, e);
        }
        return false;
    }

    /**
     * 移除key的过期时间，key 将持久保持
     */
    public Boolean persist(String key) {
        logger.debug("移除 key:{} 的过期时间,key 将持久保持", key);
        try {
            return redisTemplate.persist(key);
        } catch (Throwable e) {
            logger.error("异常，移除 key:{} 的过期时间,key 将持久保持", key, e);
        }
        return false;
    }

    /**
     * 返回key的剩余的过期时间
     */
    public Long getExpire(String key, TimeUnit unit) {
        logger.debug("返回key:{}的剩余的过期时间, TimeUnit:{}", key, unit);
        try {
            return redisTemplate.getExpire(key, unit);
        } catch (Throwable e) {
            logger.error("异常，返回key:{}的剩余的过期时间, TimeUnit:{}", key, unit, e);
        }
        return null;
    }

    /**
     * 返回key的剩余的过期时间
     */
    public Long getExpire(String key) {
        logger.debug("返回key:{}的剩余的过期时间", key);
        try {
            return redisTemplate.getExpire(key);
        } catch (Throwable e) {
            logger.error("异常，返回key:{}的剩余的过期时间", key, e);
        }
        return null;
    }

    /**
     * 从当前数据库中随机返回一个 key
     */
    public String randomKey() {
        logger.debug("从当前数据库中随机返回一个 key");
        try {
            return redisTemplate.randomKey();
        } catch (Throwable e) {
            logger.error("异常，从当前数据库中随机返回一个 key", e);
        }
        return null;
    }

    /**
     * 修改key的名称
     */
    public void rename(String oldKey, String newKey) {
        logger.debug("修改key:{}的名称为:{}", oldKey, newKey);
        try {
            redisTemplate.rename(oldKey, newKey);
        } catch (Throwable e) {
            logger.error("异常，修改key:{}的名称为:{}", oldKey, newKey, e);
        }
    }

    /**
     * 仅当 newkey 不存在时，将 oldKey 改名为 newkey
     */
    public Boolean renameIfAbsent(String oldKey, String newKey) {
        logger.debug("仅当 newkey 不存在时，修改key:{}的名称为:{}", oldKey, newKey);
        try {
            return redisTemplate.renameIfAbsent(oldKey, newKey);
        } catch (Throwable e) {
            logger.error("异常，仅当 newkey 不存在时，修改key:{}的名称为:{}}", oldKey, newKey, e);
        }
        return false;
    }

    /**
     * 返回key所储存的值的类型
     */
    public DataType type(String key) {
        logger.debug("返回key:{}所储存的值的类型", key);
        try {
            return redisTemplate.type(key);
        } catch (Throwable e) {
            logger.error("异常，返回key:{}所储存的值的类型", key, e);
        }
        return null;
    }

    /** -------------------string相关操作--------------------- */

    /**
     * 设置指定key的值
     */
    public boolean set(String key, String value) {
        logger.debug("设置指定key:{}的值:{}", key, value);
        boolean result = true;
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Throwable e) {
            logger.error("异常，设置指定key:{}的值:{}", key, value, e);
            result = false;
        }
        return result;
    }

    /**
     * setNx设置指定key的值
     */
    public boolean setNx(String key, Object value) {
        logger.debug("setNx指定key:{}的值:{}", key, value);
        boolean result = false;
        try {
            result = redisTemplate.opsForValue().setIfAbsent(key, value.toString());
        } catch (Throwable e) {
            logger.error("异常，setNx指定key:{}的值:{}", key, value, e);
        }
        return result;
    }

    /**
     * 获取指定key的值
     */
    public String get(String key) {
        logger.debug("获取指定 key:{} 的值", key);
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Throwable e) {
            logger.error("异常，获取指定 key:{} 的值", key, e);
        }
        return null;
    }

    /**
     * 获取指定key的值, 指定tClass
     */
    public <T> T get(String key, Class<T> tClass) {
        logger.debug("获取数据信息,Key:{}, tClass:{}", key, tClass);
        String object;
        try {
            object = redisTemplate.opsForValue().get(key);
            return JSON.parseObject(object, tClass);
        } catch (Throwable e) {
            logger.error("异常,获取数据信息,Key:{}, tClass:{}", key, tClass, e);
        }
        return null;
    }

    /**
     * 返回key中字符串值的子字符
     */
    public String getRange(String key, long start, long end) {
        logger.debug("返回key:{}中字符串值的子字符, start:{}, end:{}", key, start, end);
        try {
            return redisTemplate.opsForValue().get(key, start, end);
        } catch (Throwable e) {
            logger.error("异常，返回key:{}中字符串值的子字符, start:{}, end:{}", key, start, end, e);
        }
        return null;
    }

    /**
     * 将给定key的值设为value，并返回key的旧值(old value)
     */
    public String getAndSet(String key, String value) {
        logger.debug("将给定key:{}的值设为 value:{} ，并返回key的旧值(old value)", key, value);
        try {
            return redisTemplate.opsForValue().getAndSet(key, value);
        } catch (Throwable e) {
            logger.error("异常，将给定key:{}的值设为 value:{} ，并返回key的旧值(old value)", key, value, e);
        }
        return null;
    }

    /**
     * 对key所储存的字符串值，获取指定偏移量上的位(bit)
     */
    public Boolean getBit(String key, long offset) {
        logger.debug("对key:{}所储存的字符串值，获取指定偏移量上的位(bit), offset:{}", key, offset);
        try {
            return redisTemplate.opsForValue().getBit(key, offset);
        } catch (Throwable e) {
            logger.error("异常，对key:{}所储存的字符串值，获取指定偏移量上的位(bit), offset:{}", key, offset, e);
        }
        return false;
    }

    /**
     * 批量获取
     */
    public List<String> multiGet(Collection<String> keys) {
        logger.debug("批量获取keys:{}", keys);
        try {
            return redisTemplate.opsForValue().multiGet(keys);
        } catch (Throwable e) {
            logger.error("异常，批量获取keys:{}", keys, e);
        }
        return null;
    }

    /**
     * 设置ASCII码, 字符串'a'的ASCII码是97, 转为二进制是'01100001', 此方法是将二进制第offset位值变为value
     */
    public boolean setBit(String key, long offset, boolean value) {
        logger.debug("设置ASCII码, key:{}, offset:{}, value:{}", key, offset, value);
        try {
            return redisTemplate.opsForValue().setBit(key, offset, value);
        } catch (Throwable e) {
            logger.error("异常，设置ASCII码, key:{}, offset:{}, value:{}", key, offset, value, e);
        }
        return false;
    }

    /**
     * 将值value关联到key，并将key的过期时间设为 timeout
     */
    public boolean setEx(String key, String value, long timeout, TimeUnit unit) {
        logger.debug("将值value:{}关联到key:{}，并将key的过期时间设为 timeout:{}, unit:{}", value, key, timeout, unit);
        boolean result = true;
        try {
            redisTemplate.opsForValue().set(key, value, timeout, unit);
        } catch (Throwable e) {
            logger.error("异常，将值value:{}关联到key:{}，并将key的过期时间设为 timeout:{}, unit:{}", value, key, timeout, unit, e);
            result = false;
        }
        return result;
    }

    /**
     * 将值value关联到key，并将key的过期时间设为 date
     */
    public boolean setExpireAt(String key, String value, Date date) {
        logger.debug("将值value:{}关联到key:{}，并将key的过期时间设为 date:{}", value, key, date);
        boolean result = true;
        try {
            redisTemplate.opsForValue().set(key, value);
            redisTemplate.expireAt(key, date);
        } catch (Throwable e) {
            logger.error("异常，将值value:{}关联到key:{}，并将key的过期时间设为 date:{}", value, key, date, e);
            result = false;
        }
        return result;
    }

    /**
     * 将值value关联到key，并设置key的过期时间（单位：秒）
     */
    public boolean setExpire(String key, String value, long seconds) {
        logger.debug("将值value:{}关联到key:{}，key的过期时间为 seconds:{}", value, key, seconds);
        boolean result = true;
        try {
            redisTemplate.opsForValue().set(key, value);
            redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
        } catch (Throwable e) {
            logger.error("异常，将值value:{}关联到key:{}，key的过期时间为 seconds:{}", value, key, seconds, e);
            result = false;
        }
        return result;
    }

    /**
     * 只有在key不存在时设置key的值
     *
     * @return 之前已经存在返回false, 不存在返回true
     */
    public boolean setIfAbsent(String key, String value) {
        logger.debug("只有在key:{}不存在时设置key的值", key);
        try {
            return redisTemplate.opsForValue().setIfAbsent(key, value);
        } catch (Throwable e) {
            logger.error("异常，只有在key:{}不存在时设置key的值", key, e);
        }
        return false;
    }

    /**
     * 用value参数覆写给定key所储存的字符串值，从偏移量offset开始
     */
    public void setRange(String key, String value, long offset) {
        logger.debug("用value:{}参数覆写给定key:{}所储存的字符串值，从偏移量offset:{}开始", value, key, offset);
        try {
            redisTemplate.opsForValue().set(key, value, offset);
        } catch (Throwable e) {
            logger.error("异常，用value:{}参数覆写给定key:{}所储存的字符串值，从偏移量offset:{}开始", value, key, offset, e);
        }
    }

    /**
     * 获取字符串的长度
     */
    public Long size(String key) {
        logger.debug("获取字符串的长度,key:{}", key);
        try {
            return redisTemplate.opsForValue().size(key);
        } catch (Throwable e) {
            logger.error("异常，获取字符串的长度,key:{}", key, e);
        }
        return null;
    }

    /**
     * 批量添加
     */
    public void multiSet(Map<String, String> maps) {
        logger.debug("批量添加, maps:{}", maps);
        try {
            redisTemplate.opsForValue().multiSet(maps);
        } catch (Throwable e) {
            logger.error("异常，批量添加, maps:{}", maps, e);
        }
    }

    /**
     * 同时设置一个或多个 key-value 对，当且仅当所有给定key都不存在
     *
     * @return 之前已经存在返回false, 不存在返回true
     */
    public boolean multiSetIfAbsent(Map<String, String> maps) {
        logger.debug("同时设置一个或多个 key-value 对，当且仅当所有给定key都不存在, maps:{}", maps);
        try {
            return redisTemplate.opsForValue().multiSetIfAbsent(maps);
        } catch (Throwable e) {
            logger.error("异常，同时设置一个或多个 key-value 对，当且仅当所有给定key都不存在, maps:{}", maps, e);
        }
        return false;
    }

    /**
     * 增加(自增长), 负数则为自减
     */
    public Long incrBy(String key, long increment) {
        logger.debug("增加(自增长), 负数则为自减, key:{}, increment:{}", key, increment);
        try {
            return redisTemplate.opsForValue().increment(key, increment);
        } catch (Throwable e) {
            logger.error("异常，增加(自增长), 负数则为自减, key:{}, increment:{}", key, increment, e);
        }
        return null;
    }

    /**
     * 增加(自增长), 负数则为自减
     */
    public Double incrByFloat(String key, double increment) {
        logger.debug("增加(自增长), 负数则为自减, key:{}, increment:{}", key, increment);
        try {
            return redisTemplate.opsForValue().increment(key, increment);
        } catch (Throwable e) {
            logger.error("异常，增加(自增长), 负数则为自减, key:{}, increment:{}", key, increment, e);
        }
        return null;
    }

    /**
     * 追加到末尾
     */
    public Integer append(String key, String value) {
        logger.debug("追加到末尾, key:{}, value:{}", key, value);
        try {
            return redisTemplate.opsForValue().append(key, value);
        } catch (Throwable e) {
            logger.error("追加到末尾, key:{}, value:{}", key, value, e);
        }
        return null;
    }

    /** -------------------hash相关操作------------------------- */

    /**
     * 获取存储在哈希表中指定字段的值
     */
    public Object hGet(String key, String field) {
        logger.debug("获取存储在哈希表中指定字段的值, key:{}, field:{}", key, field);
        try {
            return redisTemplate.opsForHash().get(key, field);
        } catch (Throwable e) {
            logger.error("异常，获取存储在哈希表中指定字段的值, key:{}, field:{}", key, field, e);
        }
        return null;
    }

    /**
     * 获取所有给定字段的值
     */
    public Map<Object, Object> hGetAll(String key) {
        logger.debug("获取所有给定字段的值, key:{}", key);
        try {
            return redisTemplate.opsForHash().entries(key);
        } catch (Throwable e) {
            logger.error("异常，获取所有给定字段的值, key:{}", key, e);
        }
        return null;
    }

    /**
     * 获取所有给定字段的值
     */
    public List<Object> hMultiGet(String key, Collection<Object> fields) {
        logger.debug("获取所有给定字段的值, key:{}, fields:{}", key, fields);
        try {
            return redisTemplate.opsForHash().multiGet(key, fields);
        } catch (Throwable e) {
            logger.error("异常，获取所有给定字段的值, key:{}, fields:{}", key, fields, e);
        }
        return null;
    }

    /**
     * 对hash赋值
     */
    public void hPut(String key, String hashKey, String value) {
        logger.debug("对hash赋值, key:{}, hashKey:{}, value:{}", key, hashKey, value);
        try {
            redisTemplate.opsForHash().put(key, hashKey, value);
        } catch (Throwable e) {
            logger.error("异常，对hash赋值, key:{}, hashKey:{}, value:{}", key, hashKey, value, e);
        }
    }

    /**
     * 对hash赋值
     */
    public void hPutAll(String key, Map<String, String> maps) {
        logger.debug("对hash赋值, key:{}, maps:{}", key, maps);
        try {
            redisTemplate.opsForHash().putAll(key, maps);
        } catch (Throwable e) {
            logger.error("异常，对hash赋值, key:{}, maps:{}", key, maps, e);
        }
    }

    /**
     * 仅当hashKey不存在时才设置
     */
    public Boolean hPutIfAbsent(String key, String hashKey, String value) {
        logger.debug("仅当hashKey不存在时才设置, key:{}, hashKey:{}, value:{}", key, hashKey, value);
        try {
            return redisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
        } catch (Throwable e) {
            logger.error("异常，仅当hashKey不存在时才设置, key:{}, hashKey:{}, value:{}", key, hashKey, value, e);
        }
        return null;
    }

    /**
     * 删除一个或多个哈希表字段
     */
    public Long hDelete(String key, Object... fields) {
        logger.debug("删除一个或多个哈希表字段, key:{}, fields:{}", key, fields);
        try {
            return redisTemplate.opsForHash().delete(key, fields);
        } catch (Throwable e) {
            logger.error("异常，删除一个或多个哈希表字段, key:{}, fields:{}", key, fields, e);
        }
        return null;
    }

    /**
     * 查看哈希表key中，指定的字段是否存在
     */
    public boolean hExists(String key, String field) {
        logger.debug("查看哈希表key:{}中，指定的字段field:{}是否存在", key, field);
        try {
            return redisTemplate.opsForHash().hasKey(key, field);
        } catch (Throwable e) {
            logger.error("异常，查看哈希表key:{}中，指定的字段field:{}是否存在", key, field, e);
        }
        return false;
    }

    /**
     * 为哈希表key中的指定字段的整数值加上增量 increment
     */
    public Long hIncrBy(String key, Object field, long increment) {
        logger.debug("为哈希表key:{}中的指定字段field:{}的整数值加上增量 increment:{}", key, field, increment);
        try {
            return redisTemplate.opsForHash().increment(key, field, increment);
        } catch (Throwable e) {
            logger.error("异常，为哈希表key:{}中的指定字段field:{}的整数值加上增量 increment:{}", key, field, increment, e);
        }
        return null;
    }

    /**
     * 为哈希表key中的指定字段的整数值加上增量 increment
     */
    public Double hIncrByFloat(String key, Object field, double delta) {
        logger.debug("为哈希表key:{}中的指定字段field:{}的整数值加上增量 delta:{}", key, field, delta);
        try {
            return redisTemplate.opsForHash().increment(key, field, delta);
        } catch (Throwable e) {
            logger.error("异常，为哈希表key:{}中的指定字段field:{}的整数值加上增量 delta:{}", key, field, delta, e);
        }
        return null;
    }

    /**
     * 获取所有哈希表中的字段
     */
    public Set<Object> hKeys(String key) {
        logger.debug("获取所有哈希表中的字段key:{}", key);
        try {
            return redisTemplate.opsForHash().keys(key);
        } catch (Throwable e) {
            logger.error("异常，获取所有哈希表中的字段key:{}", key, e);
        }
        return null;
    }

    /**
     * 获取哈希表中字段的数量
     */
    public Long hSize(String key) {
        logger.debug("获取哈希表中字段的数量key:{}", key);
        try {
            return redisTemplate.opsForHash().size(key);
        } catch (Throwable e) {
            logger.error("异常，获取哈希表中字段的数量key:{}", key, e);
        }
        return null;
    }

    /**
     * 获取哈希表中所有值
     */
    public List<Object> hValues(String key) {
        logger.debug("获取哈希表中所有值key:{}", key);
        try {
            return redisTemplate.opsForHash().values(key);
        } catch (Throwable e) {
            logger.error("异常，获取哈希表中所有值key:{}", key, e);
        }
        return null;
    }

    /**
     * 迭代哈希表中的键值对
     */
    public Cursor<Entry<Object, Object>> hScan(String key, ScanOptions options) {
        logger.debug("迭代哈希表中的键值对key:{}, ScanOptions:{}", key, options);
        try {
            return redisTemplate.opsForHash().scan(key, options);
        } catch (Throwable e) {
            logger.error("异常，迭代哈希表中的键值对key:{}, ScanOptions:{}", key, options, e);
        }
        return null;
    }

    /** ------------------------list相关操作---------------------------- */

    /**
     * 通过索引获取列表中的元素
     */
    public String lIndex(String key, long index) {
        logger.debug("通过索引获取列表中的元素, key:{}, index:{}", key, index);
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Throwable e) {
            logger.error("异常，通过索引获取列表中的元素, key:{}, index:{}", key, index, e);
        }
        return null;
    }

    /**
     * 获取列表指定范围内的元素
     */
    public List<String> lRange(String key, long start, long end) {
        logger.debug("获取列表指定范围内的元素, key:{}, start:{}, end:{}", key, start, end);
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Throwable e) {
            logger.error("异常，获取列表指定范围内的元素, key:{}, start:{}, end:{}", key, start, end, e);
        }
        return null;
    }

    /**
     * 存储在list头部
     */
    public Long lLeftPush(String key, String value) {
        logger.debug("存储在list头部, key:{}, value:{}", key, value);
        try {
            return redisTemplate.opsForList().leftPush(key, value);
        } catch (Throwable e) {
            logger.error("异常，存储在list头部, key:{}, value:{}", key, value, e);
        }
        return null;
    }

    /**
     * 从list左边push
     */
    public Long lLeftPushAll(String key, String... value) {
        logger.debug("从list左边push, key:{}, values:{}", key, value);
        try {
            return redisTemplate.opsForList().leftPushAll(key, value);
        } catch (Throwable e) {
            logger.error("异常，从list左边push, key:{}, values:{}", key, value, e);
        }
        return null;
    }

    /**
     * 从list右边push
     */
    public Long lLeftPushAll(String key, Collection<String> value) {
        logger.debug("从list右边push, key:{}, values:{}", key, value);
        try {
            return redisTemplate.opsForList().leftPushAll(key, value);
        } catch (Throwable e) {
            logger.error("异常，从list右边push, key:{}, values:{}", key, value, e);
        }
        return null;
    }

    /**
     * 当list存在的时候才加入
     */
    public Long lLeftPushIfPresent(String key, String value) {
        logger.debug("当list存在的时候才加入, key:{}, value:{}", key, value);
        try {
            return redisTemplate.opsForList().leftPushIfPresent(key, value);
        } catch (Throwable e) {
            logger.error("异常，当list存在的时候才加入, key:{}, value:{}", key, value, e);
        }
        return null;
    }

    /**
     * 如果pivot存在,再pivot前面添加
     */
    public Long lLeftPush(String key, String pivot, String value) {
        logger.debug("如果pivot:{}存在,再pivot前面添加, key:{}, value:{}", pivot, key, value);
        try {
            return redisTemplate.opsForList().leftPush(key, pivot, value);
        } catch (Throwable e) {
            logger.error("异常，如果pivot:{}存在,再pivot前面添加, key:{}, value:{}", pivot, key, value, e);
        }
        return null;
    }

    /**
     * 从list右边push
     */
    public Long lRightPush(String key, String value) {
        logger.debug("从list右边push, key:{}, value:{}", key, value);
        try {
            return redisTemplate.opsForList().rightPush(key, value);
        } catch (Throwable e) {
            logger.error("异常，从list右边push, key:{}, value:{}", key, value, e);
        }
        return null;
    }

    /**
     * 从list右边push
     */
    public Long lRightPushAll(String key, String... value) {
        logger.debug("从list右边push, key:{}, value:{}", key, value);
        try {
            return redisTemplate.opsForList().rightPushAll(key, value);
        } catch (Throwable e) {
            logger.error("异常，从list右边push, key:{}, value:{}", key, value, e);
        }
        return null;
    }

    /**
     * 从list右边push
     */
    public Long lRightPushAll(String key, Collection<String> value) {
        logger.debug("从list右边push, key:{}, value:{}", key, value);
        try {
            return redisTemplate.opsForList().rightPushAll(key, value);
        } catch (Throwable e) {
            logger.error("异常，从list右边push, key:{}, value:{}", key, value, e);
        }
        return null;
    }

    /**
     * 为已存在的列表添加值
     */
    public Long lRightPushIfPresent(String key, String value) {
        logger.debug("为已存在的列表添加值, key:{}, value:{}", key, value);
        try {
            return redisTemplate.opsForList().rightPushIfPresent(key, value);
        } catch (Throwable e) {
            logger.error("异常，为已存在的列表添加值, key:{}, value:{}", key, value, e);
        }
        return null;
    }

    /**
     * 在pivot元素的右边添加值
     */
    public Long lRightPush(String key, String pivot, String value) {
        logger.debug("在pivot:{}元素的右边添加值, key:{}, value:{}", pivot, key, value);
        try {
            return redisTemplate.opsForList().rightPush(key, pivot, value);
        } catch (Throwable e) {
            logger.error("异常，在pivot:{}元素的右边添加值, key:{}, value:{}", pivot, key, value, e);
        }
        return null;
    }

    /**
     * 通过索引设置列表元素的值
     */
    public void lSet(String key, long index, String value) {
        logger.debug("通过索引设置列表元素的值, key:{}, index:{}, value:{}", key, index, value);
        try {
            redisTemplate.opsForList().set(key, index, value);
        } catch (Throwable e) {
            logger.error("异常，通过索引设置列表元素的值, key:{}, index:{}, value:{}", key, index, value, e);
        }
    }

    /**
     * 移出并获取列表的第一个元素
     *
     * @return 删除的元素
     */
    public String lLeftPop(String key) {
        logger.debug("移出并获取列表的第一个元素, key:{}", key);
        try {
            return redisTemplate.opsForList().leftPop(key);
        } catch (Throwable e) {
            logger.error("异常，移出并获取列表的第一个元素, key:{}", key, e);
        }
        return null;
    }

    /**
     * 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     */
    public String lBLeftPop(String key, long timeout, TimeUnit unit) {
        logger.debug("移出并获取列表的第一个元素, key:{}, timeout:{}, unit:{}", key, timeout, unit);
        try {
            return redisTemplate.opsForList().leftPop(key, timeout, unit);
        } catch (Throwable e) {
            logger.error("异常，移出并获取列表的第一个元素, key:{}, timeout:{}, unit:{}", key, timeout, unit, e);
        }
        return null;
    }

    /**
     * 移除并获取列表最后一个元素
     *
     * @return 删除的元素
     */
    public String lRightPop(String key) {
        logger.debug("移除并获取列表最后一个元素, key:{}", key);
        try {
            return redisTemplate.opsForList().rightPop(key);
        } catch (Throwable e) {
            logger.error("异常，移除并获取列表最后一个元素, key:{}", key, e);
        }
        return null;
    }

    /**
     * 移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     */
    public String lBRightPop(String key, long timeout, TimeUnit unit) {
        logger.debug("移出并获取列表的最后一个元素, key:{}, timeout:{}, unit:{}", key, timeout, unit);
        try {
            return redisTemplate.opsForList().rightPop(key, timeout, unit);
        } catch (Throwable e) {
            logger.error("异常，移出并获取列表的最后一个元素, key:{}, timeout:{}, unit:{}", key, timeout, unit, e);
        }
        return null;
    }

    /**
     * 移除列表的最后一个元素，并将该元素添加到另一个列表并返回
     */
    public String lRightPopAndLeftPush(String sourceKey, String destinationKey) {
        logger.debug("移除列表的最后一个元素，并将该元素添加到另一个列表并返回, sourceKey:{}, destinationKey:{}", sourceKey, destinationKey);
        try {
            return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey);
        } catch (Throwable e) {
            logger.error("异常，移除列表的最后一个元素，并将该元素添加到另一个列表并返回, sourceKey:{}, destinationKey:{}", sourceKey, destinationKey, e);
        }
        return null;
    }

    /**
     * 从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它； 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     */
    public String lBRightPopAndLeftPush(String sourceKey, String destinationKey,
                                        long timeout, TimeUnit unit) {
        logger.debug("从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它, sourceKey:{}, destinationKey:{}, timeout:{}, unit:{}", sourceKey, destinationKey, timeout, unit);
        try {
            return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout, unit);
        } catch (Throwable e) {
            logger.error("异常，从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它, sourceKey:{}, destinationKey:{}, timeout:{}, unit:{}", sourceKey, destinationKey, timeout, unit, e);
        }
        return null;
    }

    /**
     * 删除集合中值等于value得元素
     * index=0, 删除所有值等于value的元素; index>0, 从头部开始删除第一个值等于value的元素;
     * index<0, 从尾部开始删除第一个值等于value的元素;
     */
    public Long lRemove(String key, long index, String value) {
        logger.debug("删除集合中值等于value得元素, key:{}, index:{}, value:{}", key, index, value);
        try {
            return redisTemplate.opsForList().remove(key, index, value);
        } catch (Throwable e) {
            logger.error("异常，删除集合中值等于value得元素, key:{}, index:{}, value:{}", key, index, value, e);
        }
        return null;
    }

    /**
     * 裁剪list
     */
    public void lTrim(String key, long start, long end) {
        logger.debug("裁剪list, key:{}, start:{}, end:{}", key, start, end);
        try {
            redisTemplate.opsForList().trim(key, start, end);
        } catch (Throwable e) {
            logger.error("异常，裁剪list, key:{}, start:{}, end:{}", key, start, end, e);
        }
    }

    /**
     * 获取列表长度
     */
    public Long lLen(String key) {
        logger.debug("获取列表长度, key:{}", key);
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Throwable e) {
            logger.error("异常，获取列表长度, key:{}", key, e);
        }
        return null;
    }

    /** --------------------set相关操作-------------------------- */

    /**
     * set添加元素
     */
    public Long sAdd(String key, String... values) {
        logger.debug("set添加元素, key:{}, values:{}", key, values);
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Throwable e) {
            logger.error("异常，set添加元素, key:{}, values:{}", key, values, e);
        }
        return null;
    }

    /**
     * set移除元素
     */
    public Long sRemove(String key, Object... values) {
        logger.debug("set移除元素, key:{}, values:{}", key, values);
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Throwable e) {
            logger.error("异常，set移除元素, key:{}, values:{}", key, values, e);
        }
        return null;
    }

    /**
     * 移除并返回集合的一个随机元素
     */
    public String sPop(String key) {
        logger.debug("移除并返回集合的一个随机元素, key:{}", key);
        try {
            return redisTemplate.opsForSet().pop(key);
        } catch (Throwable e) {
            logger.error("异常，移除并返回集合的一个随机元素, key:{}", key, e);
        }
        return null;
    }

    /**
     * 将元素value从一个集合移到另一个集合
     */
    public Boolean sMove(String key, String value, String destKey) {
        logger.debug("将元素value从一个集合移到另一个集合, key:{}, value:{}, destKey:{}", key, value, destKey);
        try {
            return redisTemplate.opsForSet().move(key, value, destKey);
        } catch (Throwable e) {
            logger.error("异常，将元素value从一个集合移到另一个集合, key:{}, value:{}, destKey:{}", key, value, destKey, e);
        }
        return false;
    }

    /**
     * 获取集合的大小
     */
    public Long sSize(String key) {
        logger.debug("获取集合的大小, key:{}", key);
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Throwable e) {
            logger.error("异常，获取集合的大小, key:{}", key, e);
        }
        return null;
    }

    /**
     * 判断集合是否包含value
     */
    public Boolean sIsMember(String key, Object value) {
        logger.debug("判断集合是否包含value, key:{}, value:{}", key, value);
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Throwable e) {
            logger.error("异常，判断集合是否包含value, key:{}, value:{}", key, value, e);
        }
        return false;
    }

    /**
     * 获取两个集合的交集
     */
    public Set<String> sIntersect(String key, String otherKey) {
        logger.debug("获取两个集合的交集, key:{}, otherKey:{}", key, otherKey);
        try {
            return redisTemplate.opsForSet().intersect(key, otherKey);
        } catch (Throwable e) {
            logger.error("异常，获取两个集合的交集, key:{}, otherKey:{}", key, otherKey, e);
        }
        return null;
    }

    /**
     * 获取key集合与多个集合的交集
     */
    public Set<String> sIntersect(String key, Collection<String> otherKeys) {
        logger.debug("获取key集合与多个集合的交集, key:{}, otherKeys:{}", key, otherKeys);
        try {
            return redisTemplate.opsForSet().intersect(key, otherKeys);
        } catch (Throwable e) {
            logger.error("异常，获取key集合与多个集合的交集, key:{}, otherKeys:{}", key, otherKeys, e);
        }
        return null;
    }

    /**
     * key集合与otherKey集合的交集存储到destKey集合中
     */
    public Long sIntersectAndStore(String key, String otherKey, String destKey) {
        logger.debug("key:{}集合与otherKey:{}集合的交集存储到destKey:{}集合中:{}", key, otherKey, destKey);
        try {
            return redisTemplate.opsForSet().intersectAndStore(key, otherKey, destKey);
        } catch (Throwable e) {
            logger.error("异常，key:{}集合与otherKey:{}集合的交集存储到destKey:{}集合中:{}", key, otherKey, destKey, e);
        }
        return null;
    }

    /**
     * key集合与多个集合的交集存储到destKey集合中
     */
    public Long sIntersectAndStore(String key, Collection<String> otherKeys,
                                   String destKey) {
        logger.debug("key:{}集合与多个集合otherKeys:{}的交集存储到destKey:{}集合中:{}", key, otherKeys, destKey);
        try {
            return redisTemplate.opsForSet().intersectAndStore(key, otherKeys, destKey);
        } catch (Throwable e) {
            logger.error("异常，key:{}集合与多个集合otherKeys:{}的交集存储到destKey:{}集合中:{}", key, otherKeys, destKey, e);
        }
        return null;
    }

    /**
     * 获取两个集合的并集
     */
    public Set<String> sUnion(String key, String otherKeys) {
        logger.debug("获取两个集合的并集, key:{}, otherKeys:{}", key, otherKeys);
        try {
            return redisTemplate.opsForSet().union(key, otherKeys);
        } catch (Throwable e) {
            logger.error("异常，获取两个集合的并集, key:{}, otherKeys:{}", key, otherKeys, e);
        }
        return null;
    }

    /**
     * 获取key集合与多个集合的并集
     */
    public Set<String> sUnion(String key, Collection<String> otherKeys) {
        logger.debug("获取key集合与多个集合的并集, key:{}, otherKeys:{}", key, otherKeys);
        try {
            return redisTemplate.opsForSet().union(key, otherKeys);
        } catch (Throwable e) {
            logger.error("异常，获取key集合与多个集合的并集, key:{}, otherKeys:{}", key, otherKeys, e);
        }
        return null;
    }

    /**
     * key集合与otherKey集合的并集存储到destKey中
     */
    public Long sUnionAndStore(String key, String otherKey, String destKey) {
        logger.debug("key:{}集合与otherKey:{}集合的并集存储到destKey:{}中", key, otherKey, destKey);
        try {
            return redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
        } catch (Throwable e) {
            logger.error("异常，key:{}集合与otherKey:{}集合的并集存储到destKey:{}中", key, otherKey, destKey, e);
        }
        return null;
    }

    /**
     * key集合与多个集合的并集存储到destKey中
     */
    public Long sUnionAndStore(String key, Collection<String> otherKeys,
                               String destKey) {
        logger.debug("key:{}集合与多个集合otherKeys:{}的并集存储到destKey:{}中", key, otherKeys, destKey);
        try {
            return redisTemplate.opsForSet().unionAndStore(key, otherKeys, destKey);
        } catch (Throwable e) {
            logger.error("异常，key:{}集合与多个集合otherKeys:{}的并集存储到destKey:{}中", key, otherKeys, destKey, e);
        }
        return null;
    }

    /**
     * 获取两个集合的差集
     */
    public Set<String> sDifference(String key, String otherKey) {
        logger.debug("获取两个集合的差集, key:{}, otherKey:{}", key, otherKey);
        try {
            return redisTemplate.opsForSet().difference(key, otherKey);
        } catch (Throwable e) {
            logger.error("异常，获取两个集合的差集, key:{}, otherKey:{}", key, otherKey, e);
        }
        return null;
    }

    /**
     * 获取key集合与多个集合的差集
     */
    public Set<String> sDifference(String key, Collection<String> otherKeys) {
        logger.debug("获取key集合与多个集合的差集, key:{}, otherKeys:{}", key, otherKeys);
        try {
            return redisTemplate.opsForSet().difference(key, otherKeys);
        } catch (Throwable e) {
            logger.error("异常，获取key集合与多个集合的差集, key:{}, otherKeys:{}", key, otherKeys, e);
        }
        return null;
    }

    /**
     * key集合与otherKey集合的差集存储到destKey中
     */
    public Long sDifference(String key, String otherKey, String destKey) {
        logger.debug("key:{}集合与otherKey:{}集合的差集存储到destKey:{}中", key, otherKey, destKey);
        try {
            return redisTemplate.opsForSet().differenceAndStore(key, otherKey, destKey);
        } catch (Throwable e) {
            logger.error("异常，key:{}集合与otherKey:{}集合的差集存储到destKey:{}中", key, otherKey, destKey, e);
        }
        return null;
    }

    /**
     * key集合与多个集合的差集存储到destKey中
     */
    public Long sDifference(String key, Collection<String> otherKeys,
                            String destKey) {
        logger.debug("key:{}集合与多个集合otherKeys:{}的差集存储到destKey:{}中", key, otherKeys, destKey);
        try {
            return redisTemplate.opsForSet().differenceAndStore(key, otherKeys, destKey);
        } catch (Throwable e) {
            logger.error("异常，key:{}集合与多个集合otherKeys:{}的差集存储到destKey:{}中", key, otherKeys, destKey, e);
        }
        return null;
    }

    /**
     * 获取集合所有元素
     */
    public Set<String> setMembers(String key) {
        logger.debug("获取集合所有元素, key:{}", key);
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Throwable e) {
            logger.error("异常，获取集合所有元素, key:{}", key, e);
        }
        return null;
    }

    /**
     * 随机获取集合中的一个元素
     */
    public String sRandomMember(String key) {
        logger.debug("随机获取集合中的一个元素, key:{}", key);
        try {
            return redisTemplate.opsForSet().randomMember(key);
        } catch (Throwable e) {
            logger.error("异常，随机获取集合中的一个元素, key:{}", key, e);
        }
        return null;
    }

    /**
     * 随机获取集合中count个元素
     */
    public List<String> sRandomMembers(String key, long count) {
        logger.debug("随机获取集合中count个元素, key:{}, count:{}", key, count);
        try {
            return redisTemplate.opsForSet().randomMembers(key, count);
        } catch (Throwable e) {
            logger.error("异常，随机获取集合中count个元素, key:{}, count:{}", key, count, e);
        }
        return null;
    }

    /**
     * 随机获取集合中count个元素并且去除重复的
     */
    public Set<String> sDistinctRandomMembers(String key, long count) {
        logger.debug("随机获取集合中count个元素并且去除重复的, key:{}, count:{}", key, count);
        try {
            return redisTemplate.opsForSet().distinctRandomMembers(key, count);
        } catch (Throwable e) {
            logger.error("异常，随机获取集合中count个元素并且去除重复的, key:{}, count:{}", key, count, e);
        }
        return null;
    }

    /**
     * 遍历集合
     */
    public Cursor<String> sScan(String key, ScanOptions options) {
        logger.debug("遍历集合, key:{}, ScanOptions:{}", key, options);
        try {
            return redisTemplate.opsForSet().scan(key, options);
        } catch (Throwable e) {
            logger.error("异常，遍历集合, key:{}, ScanOptions:{}", key, options, e);
        }
        return null;
    }

    /**------------------zSet相关操作--------------------------------*/

    /**
     * zSet添加元素,有序集合是按照元素的score值由小到大排列
     */
    public Boolean zAdd(String key, String value, double score) {
        logger.debug("zSet添加元素, key:{}, value:{}, score:{}", key, value, score);
        try {
            return redisTemplate.opsForZSet().add(key, value, score);
        } catch (Throwable e) {
            logger.error("异常，zSet添加元素, key:{}, value:{}, score:{}", key, value, score, e);
        }
        return false;
    }

    /**
     * zSet添加元素
     */
    public Long zAdd(String key, Set<TypedTuple<String>> values) {
        logger.debug("zSet添加元素, key:{}, Set<TypedTuple<String>>:{}", key, values);
        try {
            return redisTemplate.opsForZSet().add(key, values);
        } catch (Throwable e) {
            logger.error("异常，zSet添加元素, key:{}, Set<TypedTuple<String>>:{}", key, values, e);
        }
        return null;
    }

    /**
     * zSet移除元素
     */
    public Long zRemove(String key, Object... values) {
        logger.debug("zSet移除元素, key:{}, values:{}", key, values);
        try {
            return redisTemplate.opsForZSet().remove(key, values);
        } catch (Throwable e) {
            logger.error("异常，zSet移除元素, key:{}, values:{}", key, values, e);
        }
        return null;
    }

    /**
     * 增加元素的score值，并返回增加后的值
     */
    public Double zIncrementScore(String key, String value, double delta) {
        logger.debug("增加元素的score值，并返回增加后的值, key:{}, value:{}, delta:{}", key, value, delta);
        try {
            return redisTemplate.opsForZSet().incrementScore(key, value, delta);
        } catch (Throwable e) {
            logger.error("异常，增加元素的score值，并返回增加后的值, key:{}, value:{}, delta:{}", key, value, delta, e);
        }
        return null;
    }

    /**
     * 返回元素在集合的排名,有序集合是按照元素的score值由小到大排列
     *
     * @return 0表示第一位
     */
    public Long zRank(String key, Object value) {
        logger.debug("返回元素在集合的排名,score值由小到大排列, key:{}, value:{}", key, value);
        try {
            return redisTemplate.opsForZSet().rank(key, value);
        } catch (Throwable e) {
            logger.error("异常，返回元素在集合的排名,score值由小到大排列, key:{}, value:{}", key, value, e);
        }
        return null;
    }

    /**
     * 返回元素在集合的排名,按元素的score值由大到小排列
     */
    public Long zReverseRank(String key, Object value) {
        logger.debug("返回元素在集合的排名,score值由大到小排列, key:{}, value:{}", key, value);
        try {
            return redisTemplate.opsForZSet().reverseRank(key, value);
        } catch (Throwable e) {
            logger.error("异常，返回元素在集合的排名,score值由大到小排列, key:{}, value:{}", key, value, e);
        }
        return null;
    }

    /**
     * 获取集合的元素, 从小到大排序
     */
    public Set<String> zRange(String key, long start, long end) {
        logger.debug("获取集合的元素, 从小到大排序, key:{}, start:{}, end:{}", key, start, end);
        try {
            return redisTemplate.opsForZSet().range(key, start, end);
        } catch (Throwable e) {
            logger.error("异常，获取集合的元素, 从小到大排序, key:{}, start:{}, end:{}", key, start, end, e);
        }
        return null;
    }

    /**
     * 获取集合元素, 并且把score值也获取
     */
    public Set<TypedTuple<String>> zRangeWithScores(String key, long start,
                                                    long end) {
        logger.debug("获取集合元素, 并且把score值也获取, key:{}, start:{}, end:{}", key, start, end);
        try {
            return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
        } catch (Throwable e) {
            logger.error("异常，获取集合元素, 并且把score值也获取, key:{}, start:{}, end:{}", key, start, end, e);
        }
        return null;
    }

    /**
     * 根据Score值查询集合元素
     */
    public Set<String> zRangeByScore(String key, double min, double max) {
        logger.debug("根据Score值查询集合元素, key:{}, min:{}, max:{}", key, min, max);
        try {
            return redisTemplate.opsForZSet().rangeByScore(key, min, max);
        } catch (Throwable e) {
            logger.error("异常，根据Score值查询集合元素, key:{}, min:{}, max:{}", key, min, max, e);
        }
        return null;
    }

    /**
     * 根据Score值查询集合元素, 从小到大排序
     */
    public Set<TypedTuple<String>> zRangeByScoreWithScores(String key,
                                                           double min, double max) {
        logger.debug("根据Score值查询集合元素, 从小到大排序, key:{}, min:{}, max:{}", key, min, max);
        try {
            return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
        } catch (Throwable e) {
            logger.error("异常，根据Score值查询集合元素, 从小到大排序, key:{}, min:{}, max:{}", key, min, max, e);
        }
        return null;
    }

    /**
     * 获取集合的元素, 从大到小排序
     */
    public Set<String> zReverseRange(String key, long start, long end) {
        logger.debug("获取集合的元素, 从大到小排序, key:{}, start:{}, end:{}", key, start, end);
        try {
            return redisTemplate.opsForZSet().reverseRange(key, start, end);
        } catch (Throwable e) {
            logger.error("异常，获取集合的元素, 从大到小排序, key:{}, start:{}, end:{}", key, start, end, e);
        }
        return null;
    }

    /**
     * 获取集合的元素, 从大到小排序, 并返回score值
     */
    public Set<TypedTuple<String>> zReverseRangeWithScores(String key,
                                                           long start, long end) {
        logger.debug("获取集合的元素, 从大到小排序, 并返回score值, key:{}, start:{}, end:{}", key, start, end);
        try {
            return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
        } catch (Throwable e) {
            logger.error("异常，获取集合的元素, 从大到小排序, 并返回score值, key:{}, start:{}, end:{}", key, start, end, e);
        }
        return null;
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     */
    public Set<String> zReverseRangeByScore(String key, double min,
                                            double max) {
        logger.debug("根据Score值查询集合元素, 从大到小排序, key:{}, min:{}, max:{}", key, min, max);
        try {
            return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
        } catch (Throwable e) {
            logger.error("异常，根据Score值查询集合元素, 从大到小排序, key:{}, min:{}, max:{}", key, min, max, e);
        }
        return null;
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     */
    public Set<TypedTuple<String>> zReverseRangeByScoreWithScores(
            String key, double min, double max) {
        logger.debug("根据Score值查询集合元素, 从大到小排序, key:{}, min:{}, max:{}", key, min, max);
        try {
            return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max);
        } catch (Throwable e) {
            logger.error("异常，根据Score值查询集合元素, 从大到小排序, key:{}, min:{}, max:{}", key, min, max, e);
        }
        return null;
    }

    /**
     * 根据score值获取集合元素数量
     */
    public Long zCount(String key, double min, double max) {
        logger.debug("根据score值获取集合元素数量, key:{}, min:{}, max:{}", key, min, max);
        try {
            return redisTemplate.opsForZSet().count(key, min, max);
        } catch (Throwable e) {
            logger.error("异常，根据score值获取集合元素数量, key:{}, min:{}, max:{}", key, min, max, e);
        }
        return null;
    }

    /**
     * 获取集合大小
     */
    public Long zSize(String key) {
        logger.debug("获取集合大小, key:{}", key);
        try {
            return redisTemplate.opsForZSet().size(key);
        } catch (Throwable e) {
            logger.error("异常，获取集合大小, key:{}", key, e);
        }
        return null;
    }

    /**
     * 获取集合大小
     */
    public Long zZCard(String key) {
        logger.debug("获取集合大小, key:{}", key);
        try {
            return redisTemplate.opsForZSet().zCard(key);
        } catch (Throwable e) {
            logger.error("异常，获取集合大小, key:{}", key, e);
        }
        return null;
    }

    /**
     * 获取集合中value元素的score值
     */
    public Double zScore(String key, Object value) {
        logger.debug("获取集合中value元素的score值, key:{}, value:{}", key, value);
        try {
            return redisTemplate.opsForZSet().score(key, value);
        } catch (Throwable e) {
            logger.error("异常，获取集合中value元素的score值, key:{}, value:{}", key, value, e);
        }
        return null;
    }

    /**
     * 移除指定索引位置的成员
     */
    public Long zRemoveRange(String key, long start, long end) {
        logger.debug("移除指定索引位置的成员, key:{}, start:{}, end:{}", key, start, end);
        try {
            return redisTemplate.opsForZSet().removeRange(key, start, end);
        } catch (Throwable e) {
            logger.error("异常，移除指定索引位置的成员, key:{}, start:{}, end:{}", key, start, end, e);
        }
        return null;
    }

    /**
     * 根据指定的score值的范围来移除成员
     */
    public Long zRemoveRangeByScore(String key, double min, double max) {
        logger.debug("根据指定的score值的范围来移除成员, key:{}, min:{}, max:{}", key, min, max);
        try {
            return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
        } catch (Throwable e) {
            logger.error("异常，根据指定的score值的范围来移除成员, key:{}, min:{}, max:{}", key, min, max, e);
        }
        return null;
    }

    /**
     * 获取key和otherKey的并集并存储在destKey中
     */
    public Long zUnionAndStore(String key, String otherKey, String destKey) {
        logger.debug("获取key和otherKey的并集并存储在destKey中, key:{}, otherKey:{}, destKey:{}", key, otherKey, destKey);
        try {
            return redisTemplate.opsForZSet().unionAndStore(key, otherKey, destKey);
        } catch (Throwable e) {
            logger.error("异常，获取key和otherKey的并集并存储在destKey中, key:{}, otherKey:{}, destKey:{}", key, otherKey, destKey, e);
        }
        return null;
    }

    /**
     * 交集
     */
    public Long zIntersectAndStore(String key, String otherKey,
                                   String destKey) {
        logger.debug("交集, key:{}, otherKey:{}, destKey:{}", key, otherKey, destKey);
        try {
            return redisTemplate.opsForZSet().intersectAndStore(key, otherKey, destKey);
        } catch (Throwable e) {
            logger.error("异常，交集, key:{}, otherKey:{}, destKey:{}", key, otherKey, destKey, e);
        }
        return null;
    }

    /**
     * 交集
     */
    public Long zIntersectAndStore(String key, Collection<String> otherKeys,
                                   String destKey) {
        logger.debug("交集, key:{}, otherKeys:{}, destKey:{}", key, otherKeys, destKey);
        try {
            return redisTemplate.opsForZSet().intersectAndStore(key, otherKeys, destKey);
        } catch (Throwable e) {
            logger.error("异常，交集, key:{}, otherKeys:{}, destKey:{}", key, otherKeys, destKey, e);
        }
        return null;
    }

    /**
     * zSet遍历
     */
    public Cursor<TypedTuple<String>> zScan(String key, ScanOptions options) {
        logger.debug("zSet遍历, key:{}, ScanOptions:{}", key, options);
        try {
            return redisTemplate.opsForZSet().scan(key, options);
        } catch (Throwable e) {
            logger.error("异常，zSet遍历, key:{}, ScanOptions:{}", key, options, e);
        }
        return null;
    }

    /**
     * 功能：访问速度
     * 描述：单位时间内的范围限制
     *
     * @param lockKey   key
     * @param lockCount 访问次数
     * @param seconds   单位时间内
     * @return
     */
    public boolean accessRateLimit(String lockKey, Integer lockCount, int seconds) {
        logger.info("accessRateLimit init, key:{}, ScanOptions:{}", lockKey, seconds);
        try {
            String result = redisTemplate.opsForValue().get(lockKey);
            Integer lock = StringUtils.isEmpty(result) ? 0 : Integer.parseInt(result);
            logger.info("accessRateLimit  lock, key:{}, result:{}", lockKey, result);
            if (lock >= lockCount) {
                logger.info("accessRateLimit 1, key:{}, result:{}", lockKey, result);
                return false;
            }
            redisTemplate.opsForValue().increment(lockKey);
            logger.info("accessRateLimit  fac , key:{}, result:{}", lockKey, result);
            String savedValue = redisTemplate.opsForValue().get(lockKey);
            Integer savedLock = StringUtils.isEmpty(savedValue) ? 0 : Integer.parseInt(savedValue);
            if (savedLock > lockCount) {
                logger.info("accessRateLimit 2, key:{}, result:{}", lockKey, result);
                return false;
            }
            if (lock == 0) {
                redisTemplate.expire(lockKey, lockCount, TimeUnit.SECONDS);
            }
            logger.info("accessRateLimit 3, key:{}, result:{}", lockKey, result);
            return true;
        } catch (Throwable e) {
            logger.error("accessRateLimit异常, key:{}, ScanOptions:{}", lockKey, lockCount, e);
        }
        return false;
    }
}