package com.demo.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author Dong Zhuming
 */
@Data
@Document(collection="apply_basic_info")
public class ApplyBasicInfoPO {
    @Id
    private String id;

    @Indexed(unique = true)
    private String applyId;
    private String bankCode;
    @Indexed
    private String accountNo;
    private String accountName;
    private String accountType;
    private DocumentType documentType;
    private String documentNumber;
    private String mobilePhone;
    private String creditCardValidateTo;
    private String creditCardCVN2;
    private String expireDate;
    private String limitPerTrans;
    private String limitPerDay;
    private String limitPerMonth;
    @CreatedDate
    private Date createdTime;

    public enum DocumentType{
        /**
         * 身份证
         */
        D001,

        /**
         * 户口簿
         */
        D002,

        /**
         * 护照
         */
        D003,

        /**
         * 军官证
         */
        D004,

        /**
         * 士兵证
         */
        D005,

        /**
         * 港澳居民来往内地通行证
         */
        D006,

        /**
         * 台湾同胞来往内地通行证
         */
        D007,

        /**
         * 临时身份证
         */
        D008,

        /**
         * 外国人居留证
         */
        D009,

        /**
         * 警官证
         */
        D010,

        /**
         * 其他证件
         */
        D011
    }

}
