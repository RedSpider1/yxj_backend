package com.redspider.pss.constant.enums;

import com.redspider.pss.utils.annotation.EnumDescription;
import lombok.Getter;

public enum Group {

    ;

    @Getter
    @EnumDescription("组队单状态")
    public enum GroupStatus {
    
        @EnumDescription("草稿")
        DRAFT(0, "draft"),
        @EnumDescription("审核中")
        AUDITING(10, "auditing"),
        @EnumDescription("组队中")
        FINDING(20, "finding"),
        @EnumDescription("组队成功")
        SUCCESS(30, "success"),
        @EnumDescription("组队失败")
        FAIL(40, "fail"),
        @EnumDescription("重新提交")
        REVERT(50, "revert"),
        @EnumDescription("组队单废弃")
        CANCEL(60, "cancel");

        private Integer code;
        private String description;

        GroupStatus(int code, String description) {
            this.code = code;
            this.description = description;
        }
    }

    @Getter
    @EnumDescription("组队单是否需要确认")
    public enum GroupConfirm {

        NO(0, "no"),
        YES(1, "yes");

        private int code;

        private String description;

        GroupConfirm(int code, String description) {
            this.code = code;
            this.description = description;
        }
    }

    @Getter
    @EnumDescription("组队单包含状态")
    public enum GroupContainType {
    
        UN_CONTAIN((byte) 0,  "un_contain"),
        CONTAIN((byte) 1, "contain");

        private byte code;
        private String description;

        GroupContainType(byte code, String description) {
            this.code = code;
            this.description = description;
        }
    }

    @Getter
    @EnumDescription("用户组队状态")
    public enum GroupUserStatus {

        APPLY(0, "apply"),
        SUCCESS(1, "success"),
        FAIL(2, "fail");

        private int code;
        private String description;

        GroupUserStatus(int code, String description) {
            this.code = code;
            this.description = description;
        }
    }
    @Getter
    @EnumDescription("组队单参与/退出")
    public enum GroupInvolveFlag {
        
        INVOLVE(0, "involve"),
        QUIT(1, "quit");
        
        private int code;
        private String description;
    
        GroupInvolveFlag(int code, String description) {
            this.code = code;
            this.description = description;
        }
    }
    
    @Getter
    @EnumDescription("组队单列表类型")
    public enum GroupListType {
        /**
         * 首页
         */
        @EnumDescription("首页")
        HOME(0, "home"),
        /**
         * 我参与的
         */
        @EnumDescription("我参与的")
        INVOLVE(1, "involve"),
        /**
         * 我浏览过
         */
        @EnumDescription("我浏览过")
        VIEWED(2, "viewed"),
        /**
         * 收藏列表
         */
        @EnumDescription("收藏列表")
        COLLECT(3, "collect"),
        /**
         * 我创建的
         */
        @EnumDescription("我创建的")
        CREATE(4, "create"),
        /**
         * 搜索
         */
        @EnumDescription("搜索")
        SEARCH(5, "search");
        
        private int code;
        private String description;
    
        GroupListType(int code, String description) {
            this.code = code;
            this.description = description;
        }
    }
}
