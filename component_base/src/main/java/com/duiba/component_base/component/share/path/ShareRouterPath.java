package com.duiba.component_base.component.share.path;

/**
 * @author: jintai
 * @time: 2018/3/20-18:54
 * @Email: jintai@qccr.com
 * @desc:
 */
public class ShareRouterPath {
    private static final String SHARE_ROOT = "/share/";


    /**
     * =============================================分享组件服务router============================================
     */
    /**
     * 用户组件page
     */
    public static final String SHARE_ACTIVITY_SHARE = SHARE_ROOT + "share_activity";

    public static final String SHARE_ACTIVITY_OTHER = SHARE_ROOT + "other_activity";

    /**
     * 用户组件资源服务
     */
    public static final String SHARE_SERVER_RES = SHARE_ROOT + "res_service";
    /**
     * 用户组件功能服务
     */
    public static final String SHARE_SERVER_FUN = SHARE_ROOT + "fun_service";
    /**
     * 用户组件控件服务
     */
    public static final String SHARE_SERVER_VIEW = SHARE_ROOT + "view_service";
    /**
     * 用户组件其他服务
     */
    public static final String SHARE_SERVER_OTHER = SHARE_ROOT + "other_service";
}
