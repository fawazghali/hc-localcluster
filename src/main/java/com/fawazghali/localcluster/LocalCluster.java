/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.fawazghali.localcluster;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import java.util.Map;

/**
 *
 * @author cmpfghal
 */
public class LocalCluster {

    public static void main(String[] args) {
        Config localClusterConfig = new Config();
        localClusterConfig.setClusterName("LocalCluster");

        HazelcastInstance hz = Hazelcast.newHazelcastInstance(localClusterConfig);
        HazelcastInstance hz2 = Hazelcast.newHazelcastInstance(localClusterConfig);
        HazelcastInstance hz3 = Hazelcast.newHazelcastInstance(localClusterConfig);

        Map<String, String> map = hz.getMap("my-distributed-map");
        map.put("1", "John");
        map.put("2", "Mary");
        map.put("3", "Jane");

        System.out.println(map.get("1"));
        System.out.println(map.get("2"));
        System.out.println(map.get("3"));
    }
}
