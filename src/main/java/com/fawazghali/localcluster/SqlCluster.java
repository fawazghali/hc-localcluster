/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fawazghali.localcluster;

/**
 *
 * @author cmpfghal
 */
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.jet.config.JetConfig;
import com.hazelcast.sql.SqlResult;
import com.hazelcast.sql.SqlRow;
import com.hazelcast.sql.SqlService;
import java.util.Arrays;
import java.util.List;

public class SqlCluster {

    public static void main(String[] args) {
        Config sqlClusterConfig = new Config();
        JetConfig jetConfig = sqlClusterConfig.getJetConfig();
        jetConfig.setEnabled(true);
        HazelcastInstance hz = Hazelcast.newHazelcastInstance(sqlClusterConfig);

        SqlService sql = hz.getSql();
        String createMappingQuery = "CREATE MAPPING myDistributedMap\n"
                + "TYPE IMap\n"
                + "OPTIONS ('keyFormat'='varchar','valueFormat'='varchar')";
// execute mapping query
        sql.execute(createMappingQuery);
        List<String> insertionQueries = Arrays.asList(
                "SINK INTO myDistributedMap VALUES('1', 'John')",
                "SINK INTO myDistributedMap VALUES('2', 'Mary')",
                "SINK INTO myDistributedMap VALUES('3', 'Jane')"
        );
// execute insertion queries
        for (String insertionQuery : insertionQueries) {
            sql.execute(insertionQuery);
        }
        String scanQuery = "SELECT * FROM myDistributedMap";
// execute the select/scan query and print the resulting rows
        try ( SqlResult result = sql.execute(scanQuery)) {
            int columnCount = result.getRowMetadata().getColumnCount();
            for (SqlRow row : result) {
                for (int colIdx = 0; colIdx < columnCount; colIdx++) {
                    System.out.print(row.getObject(colIdx) + " ");
                }
                System.out.println();
            }
        }
    }
}
