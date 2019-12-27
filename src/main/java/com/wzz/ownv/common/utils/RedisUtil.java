package com.wzz.ownv.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @program: eat
 * @description: 缓存连接
 * @author: wzz
 * @create: 2019-12-26 13:59
 */
@Component
public class RedisUtil {
    //redis连接的地址
    private static String IP_CONFIG="10.1.240.172";
    //redis的端口
    private static int PORT =6379;
    //redis的密码
    private static String PASSWORD="";
    //redis实例最大连接数
    private static int MAX_ACTIVE=8;
    //jedis最大空闲实例
    private static int MAX_IDLE=8;
    //等待可用实例的最大时间
    private static int MAX_WAIT=10000;
    //连接超时的时间　　
    private static int TIMEOUT = 10000;
    // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;

    private static JedisPool jedisPool = null;
    //数据库模式是16个数据库 0~15
    public static final int DEFAULT_DATABASE = 0;


    private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);


    /**
     * 初始化Redis连接池
     */

    static {

        try {

            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, IP_CONFIG, PORT, TIMEOUT,PASSWORD,DEFAULT_DATABASE);

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    /**
     * 获取Jedis实例
     */

    public synchronized static Jedis getJedis() {

        try {

            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                System.out.println("redis--服务正在运行: "+resource.ping());
                return resource;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /***
     *
     * 释放资源
     */

    public static void returnResources(final Jedis jedis) {
        if(jedis != null) {
            jedis.close();
        }

    }





}
