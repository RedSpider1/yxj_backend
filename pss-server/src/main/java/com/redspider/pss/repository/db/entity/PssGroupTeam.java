package com.redspider.pss.repository.db.entity;

import java.util.Date;

public class PssGroupTeam {
    private Long id;

    private String title;

    private String pictureUrl;

    private String introduce;

    private Date examineTime;

    private Date expireTime;

    private Integer minTeamSize;

    private Integer maxTeamSize;

    private String province;

    private String city;

    private String area;

    private String place;

    private String lon;

    private String lat;

    private Integer containMe;

    private Integer releaseStatus;

    private Integer teamStatus;

    private Integer confirmStatus;

    private Integer examineType;

    private String examineUser;

    private Integer examineStatus;

    private Date startTime;

    private String examineMsg;

    private Long creatorId;

    private Long updaterId;

    private Date createTime;

    private Date updateTime;

    private Integer deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getExamineTime() {
        return examineTime;
    }

    public void setExamineTime(Date examineTime) {
        this.examineTime = examineTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getMinTeamSize() {
        return minTeamSize;
    }

    public void setMinTeamSize(Integer minTeamSize) {
        this.minTeamSize = minTeamSize;
    }

    public Integer getMaxTeamSize() {
        return maxTeamSize;
    }

    public void setMaxTeamSize(Integer maxTeamSize) {
        this.maxTeamSize = maxTeamSize;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Integer getContainMe() {
        return containMe;
    }

    public void setContainMe(Integer containMe) {
        this.containMe = containMe;
    }

    public Integer getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(Integer releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public Integer getTeamStatus() {
        return teamStatus;
    }

    public void setTeamStatus(Integer teamStatus) {
        this.teamStatus = teamStatus;
    }

    public Integer getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(Integer confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public Integer getExamineType() {
        return examineType;
    }

    public void setExamineType(Integer examineType) {
        this.examineType = examineType;
    }

    public String getExamineUser() {
        return examineUser;
    }

    public void setExamineUser(String examineUser) {
        this.examineUser = examineUser;
    }

    public Integer getExamineStatus() {
        return examineStatus;
    }

    public void setExamineStatus(Integer examineStatus) {
        this.examineStatus = examineStatus;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getExamineMsg() {
        return examineMsg;
    }

    public void setExamineMsg(String examineMsg) {
        this.examineMsg = examineMsg;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(Long updaterId) {
        this.updaterId = updaterId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", pictureUrl=").append(pictureUrl);
        sb.append(", introduce=").append(introduce);
        sb.append(", examineTime=").append(examineTime);
        sb.append(", expireTime=").append(expireTime);
        sb.append(", minTeamSize=").append(minTeamSize);
        sb.append(", maxTeamSize=").append(maxTeamSize);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", area=").append(area);
        sb.append(", place=").append(place);
        sb.append(", lon=").append(lon);
        sb.append(", lat=").append(lat);
        sb.append(", containMe=").append(containMe);
        sb.append(", releaseStatus=").append(releaseStatus);
        sb.append(", teamStatus=").append(teamStatus);
        sb.append(", confirmStatus=").append(confirmStatus);
        sb.append(", examineType=").append(examineType);
        sb.append(", examineUser=").append(examineUser);
        sb.append(", examineStatus=").append(examineStatus);
        sb.append(", startTime=").append(startTime);
        sb.append(", examineMsg=").append(examineMsg);
        sb.append(", creatorId=").append(creatorId);
        sb.append(", updaterId=").append(updaterId);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", deleted=").append(deleted);
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
        PssGroupTeam other = (PssGroupTeam) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getPictureUrl() == null ? other.getPictureUrl() == null : this.getPictureUrl().equals(other.getPictureUrl()))
            && (this.getIntroduce() == null ? other.getIntroduce() == null : this.getIntroduce().equals(other.getIntroduce()))
            && (this.getExamineTime() == null ? other.getExamineTime() == null : this.getExamineTime().equals(other.getExamineTime()))
            && (this.getExpireTime() == null ? other.getExpireTime() == null : this.getExpireTime().equals(other.getExpireTime()))
            && (this.getMinTeamSize() == null ? other.getMinTeamSize() == null : this.getMinTeamSize().equals(other.getMinTeamSize()))
            && (this.getMaxTeamSize() == null ? other.getMaxTeamSize() == null : this.getMaxTeamSize().equals(other.getMaxTeamSize()))
            && (this.getProvince() == null ? other.getProvince() == null : this.getProvince().equals(other.getProvince()))
            && (this.getCity() == null ? other.getCity() == null : this.getCity().equals(other.getCity()))
            && (this.getArea() == null ? other.getArea() == null : this.getArea().equals(other.getArea()))
            && (this.getPlace() == null ? other.getPlace() == null : this.getPlace().equals(other.getPlace()))
            && (this.getLon() == null ? other.getLon() == null : this.getLon().equals(other.getLon()))
            && (this.getLat() == null ? other.getLat() == null : this.getLat().equals(other.getLat()))
            && (this.getContainMe() == null ? other.getContainMe() == null : this.getContainMe().equals(other.getContainMe()))
            && (this.getReleaseStatus() == null ? other.getReleaseStatus() == null : this.getReleaseStatus().equals(other.getReleaseStatus()))
            && (this.getTeamStatus() == null ? other.getTeamStatus() == null : this.getTeamStatus().equals(other.getTeamStatus()))
            && (this.getConfirmStatus() == null ? other.getConfirmStatus() == null : this.getConfirmStatus().equals(other.getConfirmStatus()))
            && (this.getExamineType() == null ? other.getExamineType() == null : this.getExamineType().equals(other.getExamineType()))
            && (this.getExamineUser() == null ? other.getExamineUser() == null : this.getExamineUser().equals(other.getExamineUser()))
            && (this.getExamineStatus() == null ? other.getExamineStatus() == null : this.getExamineStatus().equals(other.getExamineStatus()))
            && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
            && (this.getExamineMsg() == null ? other.getExamineMsg() == null : this.getExamineMsg().equals(other.getExamineMsg()))
            && (this.getCreatorId() == null ? other.getCreatorId() == null : this.getCreatorId().equals(other.getCreatorId()))
            && (this.getUpdaterId() == null ? other.getUpdaterId() == null : this.getUpdaterId().equals(other.getUpdaterId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getPictureUrl() == null) ? 0 : getPictureUrl().hashCode());
        result = prime * result + ((getIntroduce() == null) ? 0 : getIntroduce().hashCode());
        result = prime * result + ((getExamineTime() == null) ? 0 : getExamineTime().hashCode());
        result = prime * result + ((getExpireTime() == null) ? 0 : getExpireTime().hashCode());
        result = prime * result + ((getMinTeamSize() == null) ? 0 : getMinTeamSize().hashCode());
        result = prime * result + ((getMaxTeamSize() == null) ? 0 : getMaxTeamSize().hashCode());
        result = prime * result + ((getProvince() == null) ? 0 : getProvince().hashCode());
        result = prime * result + ((getCity() == null) ? 0 : getCity().hashCode());
        result = prime * result + ((getArea() == null) ? 0 : getArea().hashCode());
        result = prime * result + ((getPlace() == null) ? 0 : getPlace().hashCode());
        result = prime * result + ((getLon() == null) ? 0 : getLon().hashCode());
        result = prime * result + ((getLat() == null) ? 0 : getLat().hashCode());
        result = prime * result + ((getContainMe() == null) ? 0 : getContainMe().hashCode());
        result = prime * result + ((getReleaseStatus() == null) ? 0 : getReleaseStatus().hashCode());
        result = prime * result + ((getTeamStatus() == null) ? 0 : getTeamStatus().hashCode());
        result = prime * result + ((getConfirmStatus() == null) ? 0 : getConfirmStatus().hashCode());
        result = prime * result + ((getExamineType() == null) ? 0 : getExamineType().hashCode());
        result = prime * result + ((getExamineUser() == null) ? 0 : getExamineUser().hashCode());
        result = prime * result + ((getExamineStatus() == null) ? 0 : getExamineStatus().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
        result = prime * result + ((getExamineMsg() == null) ? 0 : getExamineMsg().hashCode());
        result = prime * result + ((getCreatorId() == null) ? 0 : getCreatorId().hashCode());
        result = prime * result + ((getUpdaterId() == null) ? 0 : getUpdaterId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        return result;
    }
}