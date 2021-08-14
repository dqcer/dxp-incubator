package com.dqcer.dxpprovider.open;

import com.sun.istack.internal.NotNull;

import java.io.Serializable;

public class SyncDto implements Serializable {

    private static final long serialVersionUID = 6730859864196506502L;

    @NotNull
    private Long cid;

    @NotNull
    private Long sysId;

    private String tableName;

    private String startTime;

    private String endTime;

    private Integer pageNum;

    private Integer limit;


    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getSysId() {
        return sysId;
    }

    public void setSysId(Long sysId) {
        this.sysId = sysId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    public static final class SyncDtoBuilder {
        private Long cid;
        private Long sysId;
        private String startTime;
        private String endTime;

        private SyncDtoBuilder() {
        }

        public static SyncDtoBuilder aSyncDto() {
            return new SyncDtoBuilder();
        }

        public SyncDtoBuilder withCid(Long cid) {
            this.cid = cid;
            return this;
        }

        public SyncDtoBuilder withSysId(Long sysId) {
            this.sysId = sysId;
            return this;
        }

        public SyncDtoBuilder withStartTime(String startTime) {
            this.startTime = startTime;
            return this;
        }

        public SyncDtoBuilder withEndTime(String endTime) {
            this.endTime = endTime;
            return this;
        }

        public SyncDto build() {
            SyncDto syncDto = new SyncDto();
            syncDto.setCid(cid);
            syncDto.setSysId(sysId);
            syncDto.setStartTime(startTime);
            syncDto.setEndTime(endTime);
            return syncDto;
        }
    }
}
