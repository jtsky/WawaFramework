package com.duiba.component_base.component.user.path;

/**
 * @author: jintai
 * @time: 2018/3/20-18:54
 * @Email: jintai@qccr.com
 * @desc:
 */
public class UserRouterPath {

    private static final String USER_ROOT = "/user/";



    /**
     * =============================================用户组件服务router============================================
     */
    /**
     * 用户组件page
     */
    public static final String USER_ACTIVITY_SHARE = USER_ROOT + "share_activity";

    public static final String USER_ACTIVITY_OTHER = USER_ROOT + "other_activity";

    /**
     * 用户组件资源服务
     */
    public static final String USER_SERVER_RES = USER_ROOT + "res_service";
    /**
     * 用户组件功能服务
     */
    public static final String USER_SERVER_FUN = USER_ROOT + "fun_service";
    /**
     * 用户组件控件服务
     */
    public static final String USER_SERVER_VIEW = USER_ROOT + "view_service";
    /**
     * 用户组件其他服务
     */
    public static final String USER_SERVER_OTHER = USER_ROOT + "other_service";








}
