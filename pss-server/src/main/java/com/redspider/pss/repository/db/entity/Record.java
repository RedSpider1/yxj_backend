package com.redspider.pss.repository.db.entity;

import java.util.Date;

public class Record {
    private Long id;

    private String bizType;

    private String title;

    private String pictureUrl;

    private String introduce;

    private Date startTime;

    private Date endTime;

    private String status;

    private Integer recordCondition;

    private Integer anonymous;

    private String contractType;

    private String contactInfo;

    private Long addressId;

    private Long auditId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRecordCondition() {
        return recordCondition;
    }

    public void setRecordCondition(Integer recordCondition) {
        this.recordCondition = recordCondition;
    }

    public Integer getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Integer anonymous) {
        this.anonymous = anonymous;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getAuditId() {
        return auditId;
    }

    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", bizType=").append(bizType);
        sb.append(", title=").append(title);
        sb.append(", pictureUrl=").append(pictureUrl);
        sb.append(", introduce=").append(introduce);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", status=").append(status);
        sb.append(", recordCondition=").append(recordCondition);
        sb.append(", anonymous=").append(anonymous);
        sb.append(", contractType=").append(contractType);
        sb.append(", contactInfo=").append(contactInfo);
        sb.append(", addressId=").append(addressId);
        sb.append(", auditId=").append(auditId);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Record other = (Record) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getBizType() == null ? other.getBizType() == null : this.getBizType().equals(other.getBizType()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getPictureUrl() == null ? other.getPictureUrl() == null : this.getPictureUrl().equals(other.getPictureUrl()))
            && (this.getIntroduce() == null ? other.getIntroduce() == null : this.getIntroduce().equals(other.getIntroduce()))
            && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getRecordCondition() == null ? other.getRecordCondition() == null : this.getRecordCondition().equals(other.getRecordCondition()))
            && (this.getAnonymous() == null ? other.getAnonymous() == null : this.getAnonymous().equals(other.getAnonymous()))
            && (this.getContractType() == null ? other.getContractType() == null : this.getContractType().equals(other.getContractType()))
            && (this.getContactInfo() == null ? other.getContactInfo() == null : this.getContactInfo().equals(other.getContactInfo()))
            && (this.getAddressId() == null ? other.getAddressId() == null : this.getAddressId().equals(other.getAddressId()))
            && (this.getAuditId() == null ? other.getAuditId() == null : this.getAuditId().equals(other.getAuditId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getBizType() == null) ? 0 : getBizType().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getPictureUrl() == null) ? 0 : getPictureUrl().hashCode());
        result = prime * result + ((getIntroduce() == null) ? 0 : getIntroduce().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getRecordCondition() == null) ? 0 : getRecordCondition().hashCode());
        result = prime * result + ((getAnonymous() == null) ? 0 : getAnonymous().hashCode());
        result = prime * result + ((getContractType() == null) ? 0 : getContractType().hashCode());
        result = prime * result + ((getContactInfo() == null) ? 0 : getContactInfo().hashCode());
        result = prime * result + ((getAddressId() == null) ? 0 : getAddressId().hashCode());
        result = prime * result + ((getAuditId() == null) ? 0 : getAuditId().hashCode());
        return result;
    }
}