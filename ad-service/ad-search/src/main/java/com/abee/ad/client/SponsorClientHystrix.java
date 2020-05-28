package com.abee.ad.client;

import com.abee.ad.client.vo.Plan;
import com.abee.ad.client.vo.PlanGetRequest;
import com.abee.ad.vo.CommonResponse;

import java.util.List;

/**
 * @author xincong yao
 */
public class SponsorClientHystrix implements SponsorClient {
    @Override
    public CommonResponse<List<Plan>> getPlans(PlanGetRequest request) {
        return new CommonResponse<>(-1, "ad-sponsor-service error");
    }
}
