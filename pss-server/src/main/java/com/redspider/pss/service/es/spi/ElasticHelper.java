package com.redspider.pss.service.es.spi;

import com.alibaba.fastjson.JSONObject;
import com.redspider.pss.dto.es.SearchSourceDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author QingChen
 * @date 2021/8/3
 * @description es相关操作
 * @since
 */

@Service
public class ElasticHelper {

    private Logger logger = LoggerFactory.getLogger(ElasticHelper.class);

    private RestClient client;
    private RestHighLevelClient highLevelClient;

    private static int socketTimeOut = 180;

    public ElasticHelper() {
        HttpHost[] hosts = new HttpHost[]{new HttpHost("8.136.193.14", 10004, "http")};
        RestClientBuilder clientBuilder = RestClient.builder(hosts).setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(5000);
            requestConfigBuilder.setSocketTimeout(socketTimeOut * 1000);
            requestConfigBuilder.setConnectionRequestTimeout(1000);
            return requestConfigBuilder;
        });
        highLevelClient = new RestHighLevelClient(clientBuilder);
        client = highLevelClient.getLowLevelClient();
    }

    /**
     * 新增或更新
     * @param indices
     * @param obj
     * @param id
     * @return
     */
    public boolean addAndUpdate(String indices, JSONObject obj, String id) {
        try {
            IndexRequest request = new IndexRequest(indices).source(obj);
            if(StringUtils.isNotBlank(id)) {
                request.id(id);
            }
            IndexResponse response = highLevelClient.index(request, RequestOptions.DEFAULT);
            DocWriteResponse.Result result = response.getResult();
            if(result == DocWriteResponse.Result.CREATED) {
                logger.info("文档新增成功，ID：{}", response.getId());
            }
            if(result == DocWriteResponse.Result.UPDATED) {
                logger.info("文档修改成功，ID：{}", id);
            }
            return true;
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据id获取文档
     * @param indices
     * @param id
     * @return
     */
    public JSONObject searchById(String indices, String id) {
        try {
            IdsQueryBuilder queryBuilder = QueryBuilders.idsQuery().addIds(id);
            List<JSONObject> results = search(queryBuilder, indices, new SearchSourceDTO());
            if(results.size() == 0) {
                throw new Exception("查询失败，id：" + id + "不存在");
            }
            return results.get(0);
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 检索
     * @param queryBuilder
     * @param indices
     * @return
     * @throws Exception
     */
    public List<JSONObject> search(QueryBuilder queryBuilder, String indices, SearchSourceDTO sourceDTO) throws Exception {
        return doSearch(queryBuilder, indices, sourceDTO);
    }

    private List<JSONObject> doSearch(QueryBuilder queryBuilder, String indices, SearchSourceDTO sourceDTO) throws Exception {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().query(queryBuilder);
        fillBuilder(sourceBuilder, sourceDTO);
        SearchRequest request = new SearchRequest(indices).source(sourceBuilder);
        SearchResponse response = highLevelClient.search(request, RequestOptions.DEFAULT);
        return packageDoc(response);
    }

    /**
     * 根据提供的条件对query条件进行完善
     * @param builder
     * @param sourceDTO
     */
    private void fillBuilder(SearchSourceBuilder builder, SearchSourceDTO sourceDTO) {
        if(sourceDTO == null) {
            return ;
        }
        if(sourceDTO.getFrom() != null) {
            builder.from(sourceDTO.getFrom());
        }
        if(sourceDTO.getSize() != null) {
            builder.size(sourceDTO.getFrom());
        }
        String[] includes = sourceDTO.getIncludes();
        String[] excludes = sourceDTO.getExcludes();
        if(includes != null) {
            builder.fetchSource(includes, excludes == null ? new String[]{} : excludes);
        }
        if(StringUtils.isNotBlank(sourceDTO.getSortKey())) {
            builder.sort(sourceDTO.getSortKey(), sourceDTO.isAsc() ? SortOrder.ASC : SortOrder.DESC);
        }
    }

    /**
     * 打包文档查询
     * @param response
     * @return
     */
    private List<JSONObject> packageDoc(SearchResponse response) {
        SearchHit[] hits = response.getHits().getHits();
        if(hits.length == 0) {
            return new ArrayList<>();
        }
        return Arrays.stream(hits)
                .map(hit -> {
                    JSONObject object = new JSONObject(hit.getSourceAsMap());
                    object.put("_id", hit.getId());
                    return object;
                }).collect(Collectors.toList());
    }

    public RestHighLevelClient getHighLevelClient() {
        return highLevelClient;
    }
}
