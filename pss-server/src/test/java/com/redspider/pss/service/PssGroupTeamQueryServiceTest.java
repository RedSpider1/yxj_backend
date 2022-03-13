package com.redspider.pss.service;

import cn.binarywang.wx.miniapp.util.crypt.WxMaCryptUtils;
import com.redspider.pss.dto.user.AttendUserDTO;
import com.redspider.pss.mapper.expand.PssGroupTeamV1Mapper;
import com.redspider.pss.base.PageResult;
import com.redspider.pss.service.db.impl.PssGroupTeamQueryServiceImpl;
import com.redspider.pss.service.db.spi.PssGroupTeamQueryService;
import com.redspider.pss.vo.team.PssGroupTeamDetailsVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PssGroupTeamQueryServiceImpl.class)
public class PssGroupTeamQueryServiceTest {

    //指明可能发生mock代理的类，按理来讲应该可以使用接口，但是这边测试无法启动test，改为使用实现类
    @Autowired
    private PssGroupTeamQueryService queryService;

    //指明被代理的实例，会覆盖@InjectMocks注解的示例中的同名bean
    @MockBean
    protected PssGroupTeamV1Mapper pssGroupTeamMapper;

    @Test
    void queryListByUser() {
//        PssGroupTeamReq queryPageRequest = new PssGroupTeamReq();
//        queryPageRequest.setPageNum(1);
//        queryPageRequest.setPageSize(5);
//        PageResult<PssGroupTeamQueryVo> res = queryService.queryListByUser(queryPageRequest);
//        log.info("result:{}", res);
//        String sessionKey = "rEE/4dpW+Ma7n6Ch2nv6GQ==";
//        String encryptedData = "jq+UqwPbgwK15ZPmHsKoiizols/pUmR+H1qwuZSPrUdEm/rzJW/i4JqpYoTK4nfqD9ddwMHmuVp/vDWyt+5W2JolwZGVXMRB7y71U42V7WZvEGZgreO5Pk1N7vdnRxM4BpOfxtqHzwIafcx3ujWBqJVvW5aJjwBgYjdWJbLDP4Vte3jeEbzAPBEeuv1t7gaaNDqVPqnqcwfh4ECuKcpoBA==";
//        String ivStr = "hXfFX1enE6E70w/2yLXXgw==";
        String sessionKey = "gsxepcmFLAmL+Lrsnj/rsw==";
        String encryptedData = "lSAuODTrRqgh3nfpTQJHgH3Xeicuo8ZqJ+LT2WbWsyMlUV3WW0j3M0i+XWMJHKbs7/YP2roq3lFA0Y9GBNpckgc5b0Gvf619noIzdnGDyRxGd7/VQKcrqBOebd+IKHSC20FbJzVWajSeolhN1GhA5GNKbRmNnfcoBff1NtR/acCHFh5M+EQr9DjufQiD5h6ukEu71s2+zwhFuwTBL2mx3w==";
        String ivStr = "VleW0ysm/NWKfZAJKCwgPA==";
        String decrypt = WxMaCryptUtils.decrypt(sessionKey, encryptedData, ivStr);
        System.out.println(decrypt);
    }


    /**
     * mock demo
     * @author 千般婉转皆戏言丶
     * @date 2021/9/2 1:11
     */
    @Test
    void queryUsersByGroupIdTest(){
        //自定义一个返回值
        ArrayList<AttendUserDTO> queryUsersByGroupIdRes = new ArrayList<>();
        queryUsersByGroupIdRes.add(new AttendUserDTO());
        PssGroupTeamDetailsVO groupTeamDetailsRes = new PssGroupTeamDetailsVO();
        /*
        when(method) -> 被代理的实例执行某个方法时
        any() -> 适配器。这里也可以写明具体参数，如1L
        .thenReturn(Object) -> 上方指明的方法执行时返回某个结果，也可以使用.thenThrow(Throwable)来模拟调用时产生异常
        */
        when(pssGroupTeamMapper.queryUsersByGroupId(anyLong())).thenReturn(queryUsersByGroupIdRes);
        when(pssGroupTeamMapper.groupTeamDetails(anyLong())).thenReturn(groupTeamDetailsRes);
        PageResult res = queryService.queryUsersByGroupId(1L);
        Assertions.assertEquals(res.getCode(), 200);
        Assertions.assertEquals(res.getTotal(),1);
    }

}