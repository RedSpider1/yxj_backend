package com.redspider.pss.repository.db.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 资源表
 * @TableName resource
 */
public class Resource implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件上传时的osskey
     */
    private String ossKey;

    /**
     * 资源路径
     */
    private String path;

    /**
     * 添加人
     */
    private Long creatorId;

    /**
     * 修改人
     */
    private Long updaterId;

    /**
     * 添加时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除状态 0正常
     */
    private Byte deleted;

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 文件名称
     */
    public String getName() {
        return name;
    }

    /**
     * 文件名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 文件上传时的osskey
     */
    public String getOssKey() {
        return ossKey;
    }

    /**
     * 文件上传时的osskey
     */
    public void setOssKey(String ossKey) {
        this.ossKey = ossKey;
    }

    /**
     * 资源路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 资源路径
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 添加人
     */
    public Long getCreatorId() {
        return creatorId;
    }

    /**
     * 添加人
     */
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * 修改人
     */
    public Long getUpdaterId() {
        return updaterId;
    }

    /**
     * 修改人
     */
    public void setUpdaterId(Long updaterId) {
        this.updaterId = updaterId;
    }

    /**
     * 添加时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 添加时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 删除状态 0正常
     */
    public Byte getDeleted() {
        return deleted;
    }

    /**
     * 删除状态 0正常
     */
    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
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
        Resource other = (Resource) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getOssKey() == null ? other.getOssKey() == null : this.getOssKey().equals(other.getOssKey()))
            && (this.getPath() == null ? other.getPath() == null : this.getPath().equals(other.getPath()))
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
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getOssKey() == null) ? 0 : getOssKey().hashCode());
        result = prime * result + ((getPath() == null) ? 0 : getPath().hashCode());
        result = prime * result + ((getCreatorId() == null) ? 0 : getCreatorId().hashCode());
        result = prime * result + ((getUpdaterId() == null) ? 0 : getUpdaterId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", ossKey=").append(ossKey);
        sb.append(", path=").append(path);
        sb.append(", creatorId=").append(creatorId);
        sb.append(", updaterId=").append(updaterId);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", deleted=").append(deleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}