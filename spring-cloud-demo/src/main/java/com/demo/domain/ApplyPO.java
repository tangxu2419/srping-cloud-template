package com.demo.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.*;
import java.util.Date;

/**
 * @author Dong Zhuming
 */
@Data
@Document(collection = "apply")
public class ApplyPO implements Serializable {

    public static final String CLIENT_ID = "X-CLIENT-ID";

    @Id
    private String id;
    @Indexed(unique = true)
    private String applyId;
    private String transactionId;
    private String merchantId;
    private String channelCode;
    private String contractSubject;

    /**
     * 基本信息摘要。包括银行卡，证件号，证件类型，手机号，商户号
     */
    @Indexed
    private String basicInfoSummary;

    /**
     * 签约号。签约成功后，从通道返回该字段
     */
    private String protocolNumber;

    @CreatedDate
    @Indexed(direction = IndexDirection.DESCENDING)
    private Date createdTime;
    @LastModifiedDate
    private Date lastModifiedTime;

    /**
     * 上一次发送短信时间。申请和重发时会更新此字段
     */
    private Date lastVerificationTime;

    /**
     * 申请状态
     */
    private ApplyStatus applyStatus;

    /**
     * 申请结果
     */
    private ApplyResult applyResult;

    private String reasonCode;

    private String message;

    /**
     * 记录已失效标志位
     */
    private boolean voided;

    /**
     * 签约时间
     */
    private Date contractDate;

    /**
     * 解约时间
     */
    private Date dismissalDate;

    private String clientId;

    private String extension;

    /**
     * 申请签约流程状态
     */
    public enum ApplyStatus {
        /**
         * 初始化
         */
        NEW,
        /**
         * 申请完成
         */
        SUBMITTED,
        /**
         * 申请失败
         */
        FAILED,
        /**
         * 确认完成
         */
        VERIFIED,
        /**
         * 签约完成
         */
        COMPLETED
    }

    /**
     * 签约结果
     */
    public enum ApplyResult {
        /**
         *
         */
        @Deprecated
        APPLYING,
        /**
         *
         */
        @Deprecated
        TRACKING,
        /**
         * 已签约
         */
        SIGNED,
        /**
         * 未签约
         */
        UNSIGNED,
        /**
         * 解约
         */
        DISMISSED
    }

    public ApplyPO myClone() throws Exception {
        ByteArrayOutputStream baas = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baas);
        oos.writeObject(this);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baas.toByteArray()));
        return (ApplyPO) ois.readObject();
    }
}
