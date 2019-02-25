package com.demo.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author WangJunJun
 */
@Repository
public interface ApplyRepository extends MongoRepository<ApplyPO, String> {

}
