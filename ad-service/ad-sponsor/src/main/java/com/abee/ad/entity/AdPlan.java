package com.abee.ad.entity;

import com.abee.ad.constant.CommonStatus;
import com.abee.ad.exception.AdException;
import com.abee.ad.utils.CommonUtils;
import com.abee.ad.vo.PlanRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author xincong yao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_plan")
public class AdPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Basic
    @Column(name = "plan_name", nullable = false)
    private String planName;

    @Basic
    @Column(name = "plan_status", nullable = false)
    private Integer planStatus;

    @Basic
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Basic
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Basic
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Basic
    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    public AdPlan(Long userId, String planName,
                  Date startDate, Date endDate) {
        this.userId = userId;
        this.planName = planName;
        this.planStatus = CommonStatus.VALID.getStatus();
        this.startDate = startDate;
        this.endDate = endDate;
        this.createTime = new Date();
        this.updateTime = this.createTime;
    }

    public AdPlan(PlanRequest request) throws AdException {
        this(request.getUserId(), request.getPlanName(),
                CommonUtils.parseDate(request.getStartTime()),
                CommonUtils.parseDate(request.getEndTime()));
    }

    public void update(AdPlan plan) {
        if (plan.planName != null) {
            setPlanName(plan.planName);
        }
        if (plan.startDate != null) {
            setStartDate(plan.startDate);
        }
        if (plan.endDate != null) {
            setEndDate(plan.endDate);
        }

        setUpdateTime(new Date());
    }

    public void delete() {
        setPlanStatus(CommonStatus.INVALID.getStatus());
        setUpdateTime(new Date());
    }
}