package com.bjpowernode.crm.base.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.*;

public class RedisUtil {

    //Jedis:Connection
    public static Jedis getJedis(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(500);
        config.setMaxIdle(5);
        config.setMaxWaitMillis(100);
        JedisPool jedisPool =
                new JedisPool(config,"192.168.31.128",6379,1000,"123456");
        Jedis jedis = jedisPool.getResource();
        return jedis;
    }

    //keyPattern:person:
    //向Redis中写入对象
    public static <T> void dbToRedis(Jedis jedis,int index,String keyPattern,List<T> data){
        //选库
        jedis.select(index);

        for (T t : data) {
            //目标Map
            Map<String,Object> map = new HashMap<String, Object>();

            //存储到Redis的临时map
            Map<String,String> temp = new HashMap<String, String>();
            BeanUtil.beanToMap(t, map, false, true);

            for(Map.Entry<String,Object> entry : map.entrySet()){
                temp.put(entry.getKey(), entry.getValue() + "");
            }
            //person:1 person:2 person:3 peronIndex
            String key = keyPattern + jedis.incr("Index" + keyPattern  );
            jedis.hmset(key,temp);
        }
    }

    //把Redis取出来放入到集合中 keyPattern:person:*
    public static <T> List<T> redisToBean(Jedis jedis,int index,String keyPattern,Class<T> tClass){
        //选库
        jedis.select(index);

        //匹配满足条件的所有key
        Set<String> keys = jedis.keys(keyPattern);

        List<T> data = new ArrayList<T>();
        for (String key : keys) {
            Map<String, String> map = jedis.hgetAll(key);

            T t = BeanUtil.mapToBean(map, tClass, false, new CopyOptions().ignoreNullValue());
            data.add(t);
        }
        return data;
    }
}
