package com.jzc.spring.dubbo.constant;

import org.apache.commons.lang3.StringUtils;

public class EventReportConstant {

    /** 点赞数 */
    public static String LIKE_COUNT = "like_count";

    /** 评论数 */
    public static String COMMENT_COUNT = "comment_count";

    /** 收藏数 */
    public static String FAVORITE_COUNT = "favorite_count";

    /** 分享数 */
    public static String SHARE_COUNT = "share_count";

    /** 浏览数 */
    public static String VIEW_COUNT = "view_count";

    /** 点赞状态前缀 */
    public static String LIKE_STATUS = "like_status_";

    /** 收藏状态前缀 */
    public static String FAVORITE_STATUS = "favorite_status_";

    /**
     * 默认的tenantId
     * */
    public static String DEFAULT_TENANTID = "zgzyq";


    /**
     * 获取hash中的key值
     * @param   tenantId
     * @param   moduleEnum
     * @param   infoId
     * @return
     * */
    public static String getHashKey(String tenantId, String moduleEnum, Long infoId) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(StringUtils.isNotBlank(tenantId) ? tenantId : "");
        stringBuffer.append("_");
        stringBuffer.append(StringUtils.isNotBlank(moduleEnum) ? moduleEnum : "");
        stringBuffer.append("_");
        stringBuffer.append(infoId != null ? infoId : 0L);

        return stringBuffer.toString();
    }

    /**
     * 判断是否奇数
     * @param   count
     * @return
     * */
    public static boolean whetherOdd(Long count) {
        return (count & 1) == 1 ? true : false;
    }


    /**
     * 默认的tenantId取值
     * @param tenantId
     * @return
     * */
    public static String defaultTenantId(String tenantId) {
        if(StringUtils.isBlank(tenantId)){
            return EventReportConstant.DEFAULT_TENANTID;
        }

        return tenantId;
    }

}
