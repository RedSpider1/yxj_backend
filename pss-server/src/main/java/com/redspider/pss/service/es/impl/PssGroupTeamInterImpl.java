package com.redspider.pss.service.es.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.redspider.pss.dto.team.PssGroupTeamDTO;
import com.redspider.pss.service.es.IndicesConst;
import com.redspider.pss.dto.es.SearchSourceDTO;

import com.redspider.pss.service.es.spi.ElasticHelper;
import com.redspider.pss.service.es.spi.PssGroupTeamSearchInter;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author QingChen
 * @date 2021/8/3
 * @description
 * @since
 */

@Service
public class PssGroupTeamInterImpl implements PssGroupTeamSearchInter {

    Logger logger = LoggerFactory.getLogger(PssGroupTeamInterImpl.class);

    @Autowired
    private ElasticHelper elasticHelper;

    @Override
    public boolean add(PssGroupTeamDTO pssGroupTeam) {
        JSONObject convert = convert(pssGroupTeam);
        boolean addAndUpdate = elasticHelper.addAndUpdate(IndicesConst.PSS_GROUP_TEAM_TEST, convert, null);
        if(!addAndUpdate) {
            logger.error("文档添加失败");
        }
        return addAndUpdate;
    }

    @Override
    public boolean update(PssGroupTeamDTO pssGroupTeam) {
        try {
            // 数据库id
            Long id = pssGroupTeam.getId();
            BoolQueryBuilder bool = QueryBuilders.boolQuery();
            bool.filter(QueryBuilders.termQuery("id", id));
            // 获取文档id
            List<JSONObject> list = elasticHelper.search(bool, IndicesConst.PSS_GROUP_TEAM_TEST, new SearchSourceDTO());
            // 更新
            if(list.size() != 0) {
                for (JSONObject object : list) {
                    String _id = (String)object.remove("_id");
                    // 更新文档
                    elasticHelper.addAndUpdate(IndicesConst.PSS_GROUP_TEAM_TEST, object, _id);
                }
            }else {
                // 直接新增
            }
            return true;
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                elasticHelper.getHighLevelClient().close();
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<PssGroupTeamDTO> search(String keyword) {
        return null;
    }

    /**
     * 将lon、lat转换成location
     * @param pssGroupTeam
     * @return
     */
    private static JSONObject convert(PssGroupTeamDTO pssGroupTeam) {
        String lon = pssGroupTeam.getLon();
        double doubleLon = StringUtils.isBlank(lon) ? 0.0 : Double.parseDouble(lon);
        String lat = pssGroupTeam.getLat();
        double doubleLat = StringUtils.isBlank(lat) ? 0.0 : Double.parseDouble(lat);
        JSONObject object = JSON.parseObject(JSON.toJSONString(pssGroupTeam));
        object.remove("lon");
        object.remove("lat");
        JSONObject location = new JSONObject();
        location.put("lon", doubleLon);
        location.put("lat", doubleLat);
        object.put("location", location);
        return object;
    }

    public static void main(String[] args) {
        String objStr = "{\n" +
                "    \"id\":46,\n" +
                "    \"title\":\"飒飒\",\n" +
                "    \"pictureUrlArray\":null,\n" +
                "    \"labelIdArray\":null,\n" +
                "    \"introduce\":\"发发发\",\n" +
                "    \"expireTime\":\"2021-07-14 21:38:00\",\n" +
                "    \"examineTime\":\"2021-07-29 21:38:42\",\n" +
                "    \"minTeamSize\":1,\n" +
                "    \"maxTeamSize\":3,\n" +
                "    \"province\":\"\",\n" +
                "    \"city\":\"\",\n" +
                "    \"area\":\"\",\n" +
                "    \"place\":\"\",\n" +
                "    \"lon\":\"\",\n" +
                "    \"lat\":\"\",\n" +
                "    \"releaseStatus\":1,\n" +
                "    \"confirmStatus\":0,\n" +
                "    \"createTime\":\"2021-07-29 21:38:42\",\n" +
                "    \"creatorId\":16,\n" +
                "    \"createName\":\"15129652009\"\n" +
                "  }";
        PssGroupTeamDTO obj = JSON.parseObject(objStr, PssGroupTeamDTO.class);
        JSONObject convert = convert(obj);
        ElasticHelper es = new ElasticHelper();
        boolean b = es.addAndUpdate(IndicesConst.PSS_GROUP_TEAM_TEST, convert, null);
    }
}
