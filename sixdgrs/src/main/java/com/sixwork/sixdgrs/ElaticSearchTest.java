package com.sixwork.sixdgrs;
import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;


public class ElaticSearchTest {
	
	//http://192.168.51.81:9200/
//	{
//		  "name" : "node-1",
//		  "cluster_name" : "BSS-ES-Clusters",
//		  "cluster_uuid" : "zWH6pwX-S6qPoGaWws3VOw",
//		  "version" : {
//		    "number" : "7.7.0",
//		    "build_flavor" : "default",
//		    "build_type" : "rpm",
//		    "build_hash" : "81a1e9eda8e6183f5237786246f6dced26a10eaf",
//		    "build_date" : "2020-05-12T02:01:37.602180Z",
//		    "build_snapshot" : false,
//		    "lucene_version" : "8.5.1",
//		    "minimum_wire_compatibility_version" : "6.8.0",
//		    "minimum_index_compatibility_version" : "6.0.0-beta1"
//		  },
//		  "tagline" : "You Know, for Search"
//		}
	 /*本机IP地址*/
    //public final static String HOST="172.28.28.146";
    public final static String HOST="192.168.51.81";
    /*端扣号*/
    public final static int PORT=9300;
    /*节点名，安装好后默认的节点名*/
    public final static String CLUSTERNAME="elastic";

    public static void main(String[] argaz) throws IOException {
    	RestHighLevelClient client;
    	 
    	client = new RestHighLevelClient(RestClient.builder(new HttpHost("192.168.51.81", 9200, "http")));
    	 
    	SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
    	 
    	QueryBuilder query = QueryBuilders.termQuery("type", "user");
    	 
    	QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("content", "地方");
    	 
    	sourceBuilder.query(query).query(matchQueryBuilder);
    	 
    	sourceBuilder.from(0);
    	 
    	sourceBuilder.size(5);
    	 
    	 
    	 
    	SearchRequest searchRequest = new SearchRequest();
    	 
    	searchRequest.source(sourceBuilder);
    	 
    	SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
    	 
    	RestStatus status = searchResponse.status();
    	 
    	System.out.println(status);
    	 
    	SearchHits hits = searchResponse.getHits();
    	 
    	// System.out.println(hits);
    	 
    	TotalHits totalHits = hits.getTotalHits();
    	 
    	System.out.println(totalHits);
    	 
    	long numHits = totalHits.value;
    	 
    	System.out.println(numHits);
    	 
    	SearchHit[] searchHits = hits.getHits();
    	 
    	for (SearchHit hit : searchHits) {
    	// String index = hit.getIndex();
    	 
    	String id = hit.getId();
    	 
    	System.out.println(id);
    	 
    	float score = hit.getScore();
    	 
    	System.out.println(score);
    	 
    	Map<String, Object> sourceAsMap = hit.getSourceAsMap();
    	 
    	System.out.println(sourceAsMap);
    	 
    	}
    	 
    	client.close();
    }
    
public void getConnection() {
	
	 
   
}









}