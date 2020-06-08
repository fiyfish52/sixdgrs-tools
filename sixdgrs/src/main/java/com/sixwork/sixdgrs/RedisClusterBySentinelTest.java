package com.sixwork.sixdgrs;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;


public class RedisClusterBySentinelTest {

	public static void main(String[] zhangko) {
		//conectSentinel();
		conectCluster();
	}
	
	public static void conectSentinel() {
		 Set<String> sentinels = new HashSet<String>();  
	        
	        sentinels.add("192.168.51.81:26379");
	        sentinels.add("192.168.51.81:26380");
	        sentinels.add("192.168.51.81:26381");
//	        sentinels.add("192.168.51.82:26379");
//	        sentinels.add("192.168.51.82:26380");
//	        sentinels.add("192.168.51.82:26381");
//	        sentinels.add("192.168.51.83:26379");
//	        sentinels.add("192.168.51.83:26380");
//	        sentinels.add("192.168.51.83:26381");
	        String clusterName = "master" ;   
	        JedisSentinelPool redisSentinelJedisPool = new JedisSentinelPool(clusterName,sentinels);  
	        Jedis jedis = null;  
	        try {  
	            jedis = redisSentinelJedisPool.getResource();  
	            //jedis.set("key", "aaa");  
	            //System.out.println(jedis.set("name","zlp"));  
	            System.out.println(jedis.get("name"));  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } finally {  
	            jedis.close();  
	        }  
	        redisSentinelJedisPool.close();  
	}
	
	public static void conectCluster() {
		Set<String> sentinels = new HashSet<String>();
		String hostAndPort1 = "192.168.51.81:26379";
		// 创建集群的节点集合
		Set<HostAndPort> nodes = new HashSet<HostAndPort>();
		
		// 添加节点到集群中
		nodes.add(new HostAndPort("192.168.51.81", 6379));
		nodes.add(new HostAndPort("192.168.51.81", 6380));
		nodes.add(new HostAndPort("192.168.51.81", 6381));
		nodes.add(new HostAndPort("192.168.51.82", 6379));
		nodes.add(new HostAndPort("192.168.51.82", 6380));
		nodes.add(new HostAndPort("192.168.51.82", 6381));
		nodes.add(new HostAndPort("192.168.51.83", 6379));
		nodes.add(new HostAndPort("192.168.51.83", 6380));
		nodes.add(new HostAndPort("192.168.51.83", 6381));
		// 读取Redis Pool的配置文件（位于classpath目录下）
//		InputStream _is = TestJedisCluster.class.getClassLoader().getResourceAsStream("redis_pool.properties");
//		Properties props = new Properties();
//		props.load(_is);
		
		// 读取配置项
		int maxTotal = 30;
		int maxIdle = 15;
		int MinIdle = 5;
		
		// 设置Redis Pool相关参数
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(maxTotal);
		poolConfig.setMaxIdle(maxIdle);
		poolConfig.setMinIdle(MinIdle);		
		
		// 利用上面的集群节点nodes和poolConfig，创建redis集群连接池，并获取一个redis连接
		JedisCluster jedisCluster = new JedisCluster(nodes, poolConfig);
		
		// 利用获取的jedisCluster可以进行jedis的所有操作
		//System.out.println(jedisCluster.set("name1", "beyond"));
		System.out.println(jedisCluster.get("name1"));
		System.out.println(jedisCluster.get("name"));
		System.out.println(jedisCluster.get("age"));
		// 归还连接
		jedisCluster.close();
		
	}
}
