package com.demo.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author WangJunJun
 */
@Repository
public interface ApplyBasicInfoRepository extends MongoRepository<ApplyBasicInfoPO, String> {

    /**
     * 查询签约申请客户基本信息
     * @param applyId 前端签约申请流水号
     * @return ApplyBasicInfoPO
     */
    Optional<ApplyBasicInfoPO> findFirstByApplyId(String applyId);
}
