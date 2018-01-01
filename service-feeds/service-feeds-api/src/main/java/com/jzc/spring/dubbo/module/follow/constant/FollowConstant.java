package com.jzc.spring.dubbo.module.follow.constant;

import com.jzc.spring.dubbo.module.follow.dto.FollowDto;
import org.apache.commons.lang.StringUtils;

public class FollowConstant {

    /** 关注 */
    public static Integer FOLLOW_FLAG = 1;
    /** 取消关注 */
    public static Integer UNFOLLOW_FLAG = -1;
    /** 全部关注用户列表 */
    public static String FOLLOW_ALL = "_follow_all";
    /** 全部粉丝用户列表 */
    public static String FANS_ALL = "_fans_all";

    public static String FOLLOW_STATUS = "follow_status_";
    /** 关注前缀 */
    public static String FOLLOW_PREFIX = "_follow_";

    public static String getHashKey(FollowDto followDto) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("zgzyq");
        stringBuffer.append("_");
        stringBuffer.append("20030");
        stringBuffer.append("_");
        stringBuffer.append(StringUtils.isNotBlank(followDto.getCreateUserId()) ? followDto.getCreateUserId() : 0L);

        return stringBuffer.toString();
    }

}
