package com.abee.ad.search;

import com.abee.ad.SearchApplicationTest;
import com.abee.ad.search.vo.SearchRequest;
import com.abee.ad.search.vo.feature.DistrictFeature;
import com.abee.ad.search.vo.feature.FeatureRelationship;
import com.abee.ad.search.vo.feature.ItFeature;
import com.abee.ad.search.vo.feature.KeywordFeature;
import com.abee.ad.search.vo.media.AdSlot;
import com.abee.ad.search.vo.media.App;
import com.abee.ad.search.vo.media.Device;
import com.abee.ad.search.vo.media.Geo;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchApplicationTest.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SearchTest {

    @Autowired
    private ISearch search;

    @Test
    public void testFetchAds1() {
        System.out.println("hello  ");
        SearchRequest searchRequest = new SearchRequest();

        searchRequest.setMediaId("abee-1");

        searchRequest.setRequestInfo(new SearchRequest.RequestInfo(
                "test-1",
                Collections.singletonList(
                        new AdSlot(
                                "ad-x", 1,
                                1080, 720,
                                Arrays.asList(1, 2), 1000)
                ),
                buildAppExample(),
                buildGeoExample(),
                buildDeviceExample()
        ));

        searchRequest.setFeatureInfo(
                buildFeatureExample(
                        Arrays.asList("宝马", "大众"),
                        Collections.singletonList(new DistrictFeature.Location("安徽省", "合肥市")),
                        Arrays.asList("排球", "游泳"),
                        FeatureRelationship.OR
                )
        );

        System.out.println(JSON.toJSONString(searchRequest));
        System.out.println(JSON.toJSONString(search.fetchAds(searchRequest)));
    }

    @Test
    public void testFetchAds2() {
        SearchRequest searchRequest = new SearchRequest();

        searchRequest.setMediaId("abee-1");

        searchRequest.setRequestInfo(new SearchRequest.RequestInfo(
                "test-2",
                Collections.singletonList(
                        new AdSlot(
                                "ad-y", 1,
                                1080, 720,
                                Arrays.asList(1, 2), 1000)
                ),
                buildAppExample(),
                buildGeoExample(),
                buildDeviceExample()
        ));

        searchRequest.setFeatureInfo(
                buildFeatureExample(
                        Arrays.asList("宝马", "大众", "无"),
                        Collections.singletonList(new DistrictFeature.Location("安徽省", "合肥市")),
                        Arrays.asList("排球", "游泳"),
                        FeatureRelationship.AND
                )
        );

        System.out.println(JSON.toJSONString(searchRequest));
        System.out.println(JSON.toJSONString(search.fetchAds(searchRequest)));
    }

    private App buildAppExample() {
        return new App("abee-app", "abee", "com.abee", "abee");
    }

    private Geo buildGeoExample() {
        return new Geo(100.28f, 88.61f, "北京市", "北京市");
    }

    private Device buildDeviceExample() {
        return new Device("iphone", "00-16-EA-AE-3C-40",
                "127.0.0.1", "X", "1080*720",
                "5.5", "1234567890");
    }

    private SearchRequest.FeatureInfo buildFeatureExample(
            List<String> keywords,
            List<DistrictFeature.Location> locations,
            List<String> its,
            FeatureRelationship relationship
    ) {
        return new SearchRequest.FeatureInfo(
                new KeywordFeature(keywords),
                new DistrictFeature(locations),
                new ItFeature(its),
                relationship
        );
    }

}
