package com.abee.ad.serivce;

import com.abee.ad.SponsorApplicationTest;
import com.abee.ad.exception.AdException;
import com.abee.ad.service.IPlanService;
import com.abee.ad.vo.PlanGetRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SponsorApplicationTest.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AdPlanServiceTest {

    @Autowired
    private IPlanService planService;

    @Test
    public void testGetPlanByIds() throws AdException {
        System.out.println(planService.getPlanByIds(
                new PlanGetRequest(15L, Collections.singletonList(10L))
                )
        );
    }
}
