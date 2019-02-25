package com.demo.service;

import com.demo.domain.ApplyBasicInfoPO;
import com.demo.domain.ApplyBasicInfoRepository;
import com.demo.domain.ApplyPO;
import com.demo.domain.ApplyRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author tangxu
 * @date 2019/1/1714:06
 */
@Slf4j
@Service
public class MongoDemoService {

    private MongoTemplate mongoTemplate;
    private ApplyRepository applyRepository;
    private ApplyBasicInfoRepository applyBasicInfoRepository;

    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String COMMA = "\t,";

    public MongoDemoService(MongoTemplate mongoTemplate, ApplyRepository applyRepository, ApplyBasicInfoRepository applyBasicInfoRepository) {
        this.mongoTemplate = mongoTemplate;
        this.applyRepository = applyRepository;
        this.applyBasicInfoRepository = applyBasicInfoRepository;
    }

    public void readerApplyPO() throws ParseException, IOException {
        List<Criteria> criterias = new ArrayList<>();
        Date startDate = DateUtils.parseDate("2018-12-24 00:00:00", DEFAULT_PATTERN);
        criterias.add(Criteria.where("created_time").gte(startDate));
        Date endDate = DateUtils.parseDate("2019-01-17 00:00:00", DEFAULT_PATTERN);
        criterias.add(Criteria.where("created_time").lt(endDate));
        criterias.add(Criteria.where("contract_subject").is("VCREDIT"));
        criterias.add(Criteria.where("channel_code").is("YEEPAY"));
        criterias.add(Criteria.where("apply_result").is("SIGNED"));
        String regex = "^.{25,33}$";
        criterias.add(Criteria.where("protocol_number").regex(regex));

        Criteria criteria = new Criteria();
        if (criterias.size() > 0) {
            criteria = criteria.andOperator(criterias.toArray(new Criteria[0]));
        }
        Query query = new Query(criteria);
        List<ApplyPO> list = mongoTemplate.find(query, ApplyPO.class);
        log.info("总共查询到符合条件的记录数：{}", list.size());
        FileOutputStream fos2 = null;
        try {
            fos2 = new FileOutputStream("D:\\data\\yeepay_info.txt", true);
            FileOutputStream finalFos = fos2;
            AtomicInteger count = new AtomicInteger();
            list.forEach(po -> {
                ApplyBasicInfoPO basic = applyBasicInfoRepository.findFirstByApplyId(po.getApplyId()).orElse(null);
                if (null != basic) {
                    String bankCode = StringUtils.isBlank(basic.getBankCode()) ? "empty" : basic.getBankCode();
                    try {
                        finalFos.write(basic.getAccountName()
                                .concat(COMMA)
                                .concat(basic.getDocumentNumber())
                                .concat(COMMA)
                                .concat(basic.getMobilePhone())
                                .concat(COMMA)
                                .concat(basic.getAccountNo())
                                .concat(COMMA)
                                .concat(bankCode)
                                .concat("\r\n").getBytes(UTF_8));
                        count.getAndIncrement();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            assert fos2 != null;
            fos2.close();
        }
    }


    public void outputCSV() throws Exception {
        List<Criteria> criterias = new ArrayList<>();
        String regex = "^.*stock_.*$";
        criterias.add(Criteria.where("apply_id").regex(regex));
        Date startDate = DateUtils.parseDate("2019-01-19 00:00:00", DEFAULT_PATTERN);
        criterias.add(Criteria.where("created_time").gte(startDate));
        Date endDate = DateUtils.parseDate("2019-01-30 00:00:00", DEFAULT_PATTERN);
        criterias.add(Criteria.where("created_time").lt(endDate));
        criterias.add(Criteria.where("contract_subject").is("VCREDIT"));
        criterias.add(Criteria.where("channel_code").is("YEEPAY"));
        criterias.add(Criteria.where("apply_result").is("SIGNED"));
        criterias.add(Criteria.where("merchant_id").is("10025556954"));

        Criteria criteria = new Criteria();
        if (criterias.size() > 0) {
            criteria = criteria.andOperator(criterias.toArray(new Criteria[0]));
        }
        Query query = new Query(criteria);
        List<ApplyPO> list = mongoTemplate.find(query, ApplyPO.class);
        log.info("总共查询到符合条件的记录数：{}", list.size());
        FileOutputStream fos2;
        BufferedWriter csvWtriter = null;
        try {
            fos2 = new FileOutputStream("D:\\data\\yeepay_info.csv", true);
            OutputStreamWriter osw = new OutputStreamWriter(fos2, UTF_8);
            csvWtriter = new BufferedWriter(new OutputStreamWriter(fos2, UTF_8), 10240);
            AtomicInteger count = new AtomicInteger();
            BufferedWriter finalCsvWtriter = csvWtriter;
            list.forEach(po -> {
                ApplyBasicInfoPO basic = applyBasicInfoRepository.findFirstByApplyId(po.getApplyId()).orElse(null);
                if (null != basic) {
                    try {
                        String rowStr = basic.getAccountName()
                                .concat(COMMA)
                                .concat(basic.getDocumentNumber())
                                .concat(COMMA)
                                .concat(basic.getMobilePhone())
                                .concat(COMMA)
                                .concat(basic.getAccountNo())
                                .concat("\r\n");
                        finalCsvWtriter.write(rowStr);
                        finalCsvWtriter.flush();
                        if (count.getAndIncrement() % 2000 == 0) {
                            log.info("已处理2000条记录");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            assert csvWtriter != null;
            csvWtriter.close();
        }
    }


}
