package com.abee.ad.client;

import com.abee.ad.client.vo.Plan;
import com.abee.ad.client.vo.PlanGetRequest;
import com.abee.ad.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author xincong yao
 */
@FeignClient(value = "ad-sponsor-service", fallback = SponsorClientHystrix.class)
public interface SponsorClient {

    @PostMapping("/ad-sponsor/plans")
    CommonResponse<List<Plan>> getPlans(@RequestBody PlanGetRequest request);
}
