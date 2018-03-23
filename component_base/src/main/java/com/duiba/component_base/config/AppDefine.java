package com.duiba.component_base.config;

import android.os.Environment;


import com.duiba.library_common.util.FileDownloadUtils;

import java.io.File;

/**
 * @author tangkun
 * @date 2017/10/25
 */
public class AppDefine {

    public static final int REQUEST_PHONE_PERMISSIONS = 0;
    public static final String WECHAT_APPID = "wx5afb8bbbdcf6f868";
    public static final String QQ_APPID = "1106477805";
    public static final int LIVESDK_APPID = 1400045640;
    public static final int LIVESDK_ACCOUNT_TYPE = 18353;
    public static final int LIVESDK_APPID_TEST = 1400045640;//test 1400050371
    public static final int LIVESDK_ACCOUNT_TYPE_TEST = 18353;// test 19242
    public static final String BUGLY_APPID_TEST = "1400050371";//
    public static final String BUGLY_APPID = "d4cb4f13b2";// buglytest

    public static final String AGREEMENT_URL = "file:///android_asset/agreement.html";
    public static boolean isAccountEmploy = true;
    public static boolean isAccountNotLogin = true;
    public static boolean isAccountBlack = true;
    public static boolean isLoginTencentSuccess = false;

    //wawaId
    public static final String KEY_WAWAID = "homeId";

    //wawaJIId
    public static final String KEY_WAWAROBOTID = "wawaRobotId";
    public static final String KEY_MATCHINES = "key_matchines";
    public static final String KEY_REFER = "refer";


    //客服
    public static final String KEY_MYCUSTOMER_SERVICES = "https://kefu.easemob.com/webim/im.html?configId=281b3685-9a81-405c-aa54-b7df0f3bc030";
    public static final String CHAT_CLIENT_APPKEY = "1470171214068841#kefuchannelapp50935";// 环信appkey
    public static final String CHAT_CLIENT_IM = "kefuchannelimid_579369";// 环信appkey
    public static final String CHAT_CLIENT_TENANTID = "50935";// 环信TenantId
    public static final String CHAT_YUNDUN_ID = "f297dba173894fcaa0c2a8ef6b0348df";// 环信TenantId
    public static final String CHAT_URL = "http://kefu.easemob.com/v1/Tenants/50935/robots/visitor/greetings/app";

    //配置音乐缓存地址
    public static final String KEY_MUSIC_CACHE = FileDownloadUtils.getDefaultSaveRootPath() + File.separator;

    //分享图片缓存目录
    public static final String KEY_SHARE_IMG_CACHE = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "share/img/";

    //crash log缓存目录
    public static final String KEY_CRASH_LOG_PATH = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "crash/log/";

    public static String APP_FILE_PATH = Environment.getExternalStorageDirectory().toString() + "/DCIM/prizeclaw/";

    public static class Role {
        public static final String VIDEO_ROLE_UP_VIDEO = "LiveGuest";//上麦角色
        public static final String VIDEO_ROLE_GUEST = "Guest";//访客角色
    }

    public static class GameSoundType {
        public static final String SOUND_ROOM_CLICK = "control"; // 方向控制
        public static final String SOUND_ROOM_PRIZE_CLAW = "prize_down"; // 下爪
        public static final String SOUND_RECHARGE_SUCCESS = "recharge_successed"; // 充值成功
        public static final String SOUND_ROOM_PRIZE_DOWN = "prize_btn_down"; //按下抓按钮
        public static final String SOUND_ROOM_PRIZE_UP = "prize_btn_up"; // 放开抓按钮
        public static final String SOUND_READY_GO = "ready_go"; // readygo
        public static final String SOUND_CLAW_SUCCESS = "prize_success"; // 抓成功
        public static final String SOUND_CLAW_FAIL = "prize_fail"; // 抓失败
        public static final String SOUND_NULL = "prize_null"; // 空音效
        public static final String SOUND_BG_MUSIC = "sound_bg_music"; // 背景音效
        public static final String SOUND_GAME_MUSIC = "sound_game_music"; // 游戏音效
        public static final String SOUND_BACK_MUSIC = "back_music.mp3"; //游戏音效  assets里的配置 不要修改
        public static final String SOUND_BACK_GAME_MUSIC = "game_success.mp3"; // 游戏背景音效   assets里的配置 不要修改
    }

    /**
     * 分享活动类型
     */
    public static class ShareActivityType {
        public static final int SHARE_INVITE = 10000001; // 拉新活动分享
    }

    /**
     * 弹窗类型 统一加上800
     * 新人  0.1.2  老用户0.1.2.3.4
     */
    public static class DialogType {
        public static final int TYPE_NEW_VERSION = 8000; // 版本更新弹窗
        public static final int TYPE_NEW_POPUP = 8001; // 新人弹窗
        public static final int TYPE_INVITE = 8002; // 邀请弹窗
        public static final int TYPE_OPERATE_1 = 8003; // 运营弹窗1
        public static final int TYPE_OPERATE_2 = 8004; // 运营弹窗2
        public static final int TYPE_CHARGE_OK_POPUP = 8005; // 充值成功弹窗
        public static final int TYPE_HOME_FLOAT = 8006; // 首页浮标
        public static final int TYPE_HOME_BUBBLE = 8007; // 首页气泡
        public static final int TYPE_NEVER_PLAY_POPUP = 8008; // 从未游戏用户弹窗
        public static final int TYPE_START_AD_POPUP = 8009; // 开屏广告
        public static final int TYPE_NEW_PEOPLE_INVITE_POPUP = 80010; // 邀请用户
    }

    /**
     * H5点击跳转
     */
    public static class H5Type {
        public static final int TYPE_H5_GO = 5000; //关闭当前H5
        public static final int TYPE_H5_REFRESH = 5001; //刷新当前H5
    }

    /**
     * 支付类型
     */
    public static class AppPayType {
        public static final int MY_PAY = 99991;//默认
        public static final int WX_PAY = 99991;//微信支付
        public static final int ALIPAY_PAY = 99992;//支付宝支付
        public static final int QQ_PAY = 99993;//qq支付
    }

    /**
     * 订单类型
     */
    public static class WinOrderType {
        public static final int TYPE_COMMON = 0; //普通
        public static final int TYPE_GIFT = 1; //包赔红包
    }


    /**
     * 用户类型
     */
    public static class UserType {
        public static final String TEST_USER = "TEST_USER"; //测试用户
        public static final String BLACK_USER = "BLACK_USER"; //黑名单用户
        public static final String ORDINARY_USER = "ORDINARY_USER"; //普通用户
    }

    /**
     * 登录类型（1.QQ 2.微博 20.微信）
     */
    public static class LoginType {
        public static final int QQ = 1;
        public static final int WEIBO = 2;
        public static final int WECHAT = 20;
    }

    /**
     * 分享渠道
     */
    public static class ShareChannel {
        public static final int WEIXIN = 1;
        public static final int QQ = 2;
        public static final int SINAWEIBO = 3;
        public static final int COPY = 4;
        public static final int WEIXINMOMENTS = 5;
        public static final int QQZONE = 6;
        public static final int SAVE = 7;
    }

    /**
     * 分享类型
     */
    public static class ShareType {
        public static final int SHARE_TYPE_ROOM = 1;
        public static final int SHARE_TYPE_IMG = 2;
        public static final int SHARE_TYPE_BANNER = 3;
        public static final int SHARE_TYPE_REPLAY = 4;
        public static final int SHARE_TYPE_AUDIO = 5;
        public static final int SHARE_TYPE_IMG_FILE = 6;
    }

    /**
     * 充值页面类型
     */
    public static class RechargeType {
        public static final int TYPE_A = 0;
        public static final int TYPE_B = 1;
        public static final int TYPE_C = 2;
        public static final int TYPE_A_1 = 3;
    }

    /**
     * 快捷充值来源
     */
    public static class RechargeFrom {
        public static final int TYPE_ROOM = 0;  //房间币不足充值
        public static final int TYPE_ACTIVITY = 1;  //H5活动
        public static final int TYPE_ROOM_FAILD_POP = 2;  //
    }

    /**
     * 房间快捷充值来源
     */
    public static class RoomRechargeFrom {
        public static final int TYPE_ROOM_TOP_RIGHT = 0;  //房间右上角充值通道充值弹窗 活动图片曝光
        public static final int TYPE_ROOM_NO_COIN = 1; // 抓抓币不足点击开始抓充值弹窗 活动图片曝光
    }

    /**
     * 我的值页面类型
     */
    public static class MineType {
        public static final int TYPE_A = 0;
        public static final int TYPE_B = 1;
        public static final int TYPE_C = 2;
    }

    /**
     * 信令类型
     */
    public static class CMDType {
        public static final int CMD_TYPE_CONTROL_TOP = 10101;            //上
        public static final int CMD_TYPE_CONTROL_LEFT = 10104;           //左
        public static final int CMD_TYPE_CONTROL_RIGHT = 10108;          //右
        public static final int CMD_TYPE_CONTROL_BOTTOM = 10102;         //下
        public static final int CMD_TYPE_CONTROL_GRAB = 10112;           //下爪
        public static final int CMD_TYPE_CONTROL_STOP = 10116;           //停止
        public static final int CMD_TYPE_CONTROL_START_WEAK = 10009;          //开始弱抓力
        public static final int CMD_TYPE_CONTROL_START_STRONG = 10100;        //开始抢抓力
        public static final int CMD_TYPE_UP_TO_VIDEO_SUCCESS = 20200;    //连麦成功
        public static final int CMD_TYPE_UP_TO_VIDEO_FAIL = 20201;       //连麦失败
        public static final int CMD_TYPE_GAME_RESULT_SUCCESS = 20300;    //抓娃娃成功
        public static final int CMD_TYPE_GAME_RESULT_FAIL = 20301;       //抓娃娃失败
    }


    /**
     * 充值来源
     */
    public static class RECHARGERefer {
        public static final int REFER_ROOM_START = 1;            //开始游戏充值弹层
        public static final int REFER_ROOM_TOP_RECHARGE = 2;            //娃娃机页面点击充值
        public static final int REFER_ROOM_RESULT_RECHARGE = 3;       //结果反馈弹窗引导
        public static final int REFER_ROOM_HOME_RECHARGE = 4;         //首页首冲
        public static final int REFER_ROOM_MINE_RECHARGE = 5;                  //我的页面充值入口
        public static final int REFER_HOME_TOP_RIGHT = 6;            //首页右上角充值入口
        public static final int REFER_HOME_FIRST_RECHARGE = 7;            //首页首充icon来源
        public static final int REFER_H5_PAY_RECHARGE = 8;                     //8、点击红包进入，
        public static final int REFER_H5_ACTIVITY_RECHARGE = 9;            // 9、活动H5页面进入
        public static final int REFER_RECHARGE_QUICK_BANNER = 10;            // 10 快捷充值弹窗banner
        public static final int REFER_RECHARGE_HOME_BANNER = 11;            //11 充值主页banner

    }

    /**
     * 充值页面状态
     */
    public static class RechargeStatus {
        public static final int A = 1;            //开始游戏充值弹层
        public static final int B = 2;            //娃娃机页面点击充值
        public static final int C = 3;       //结果反馈弹窗引导
    }


    /**
     * 娃娃状态
     */
    public static class DollStatus {
        public static final int DOLL_STATUS_PLAYING = 1;     //游戏中
        public static final int CMD_TYPE_CONTROL_FREE = 2;   //空闲
    }

    public static class DollOrderStauts {
        public static final int STATUS_JICUN = 0;            //寄存中
        public static final int STATUS_WAIT_DELIVER = 1;     //待发货
        public static final int STATUS_DELIVERED = 2;        //已发货
        public static final int STATUS_RETURN_BACK = 3;      //娃娃被收回
        public static final int STATUS_GET = 4;      //已领取
        public static final int STATUS_IN_TRANSIT = 5;      //在途中
        public static final int STATUS_COLLECTED = 6;      //已揽收
        public static final int STATUS_DIFFICULT = 7;      //疑难
        public static final int STATUS_SIGN_IN = 8;      //已签收
        public static final int STATUS_SIGN_OUT = 9;      //退签
        public static final int STATUS_SENDING = 10;      //同城派送中
        public static final int STATUS_RETURN = 11;      //退回
        public static final int STATUS_TURN = 12;      //转单
    }

    public static class EventbusType {
        public static final int MESSAGE_DELIVER_SUCCESS = 200001;     //发货成功
        public static final int MESSAGE_UPDATE_USERINFO = 200002;     //刷新用户消息
        public static final int MESSAGE_LOGIN_OUT = 200003;           //退出登录
        public static final int MESSAGE_UPDATE_BALANCE = 200005;           //更新余额
        public static final int MESSAGE_UPDATE_ROOM_LIST = 200006;           //刷新首页
        public static final int MESSAGE_HIDE_RECHARGE_POP = 200007;           //隐藏首冲气泡
        public static final int MESSAGE_UPDATE = 200008;           //隐藏首冲气泡
        public static final int MESSAGE_UPDATE_MAIN_RED_DOT = 200009;           //刷新首页未读消息提示
        public static final int MESSAGE_CHECK_VERSION = 200010;           //检查版本更新
        public static final int MESSAGE_SOCKET = 200011;           //socket消息
        public static final int MESSAGE_STOP_HEART_BEAT = 200012;           //socket消息
        public static final int MESSAGE_SEND_HEART_BEAT = 200013;           //socket消息
        public static final int MESSAGE_FIRST_DELIVER_SUCCESS = 200014;     //首次发货成功
        public static final int MESSAGE_FIRST_RECHARGE_GIVE_UP = 300001;//首冲引导弹窗放弃
        public static final int MESSAGE_FIRST_RECHARGE_RECHARGE = 300002;//首冲引导弹窗点击充值
        public static final int MESSAGE_RECHARGE_GIVE_UP = 300003;//充值放弃
        public static final int MESSAGE_RECHARGE_RECHARGE = 300004;//充值弹窗点击充值
        public static final int MESSAGE_RED_RECHARGE_GIVE_UP = 300005;//红包未打开引导充值弹窗放弃
        public static final int MESSAGE_RED_RECHARGE_RECHARGE = 300006;//红包未打开引导充值弹窗点击
        public static final int MESSAGE_PHONE_LOGINSUCCESS = 300007;//手机登录成功
        public static final int MESSAGE_REFRESH_MSG_CENTER_NUM = 300008;
        public static final int MESSAGE_SELECT_HOME = 300009; //选择首页
        public static final int MESSAGE_SELECT_POINTSMALL = 3000010;
        public static final int MESSAGE_SELECT_MINE = 3000011;
        public static final int MESSAGE_UPDATE_NEW_USER_SIGN_RED = 3000012;//新人签到成功
        public static final int MESSAGE_STOP_SIGN_ANIM = 3000013;
        public static final int MESSAGE_CHAT_MESSAGE = 3000014;  //点击客服
    }

    /**
     * 接受的socket消息类型
     */
    public static class SocketMsgType {
        public static final int TYPE_GAME_RESULT = 1;     //游戏结果
        public static final int TYPE_WAWA_STATU = 4;     //游戏结果
        public static final int TYPE_GAME_NOTIFY = 5;     //抓中全局播报
        public static final int TYPE_GAME_RECORD = 3;     //抓中记录播报
        public static final int TYPE_GET_MEMBER_LIST = 6;   //获取房间成员列表
        public static final int TYPE_UPDATE_MEMBER = 7;   //更新房间成员列表
        public static final int TYPE_UPDATE_MEMBER_NEW = 8;//更新房间成员列表
        public static final int TYPE_UPDATE_MSG_CENTET_NUM = 9;//未读消息数量
        public static final int TYPE_ROOM_CHAT_RECEIVE = 10;//聊天消息


        public static final int TYPE_ROOM_HEARY_BEAT = 10001;     //房间心跳
        public static final int TYPE_ROOM_CHAT = 10002;
    }

    public static class ErrorCode {
        public static final int ERROR_UNDEFINE = 100000;     //未定义错误
        public static final int ERROR_NOT_LOGIN = 100001;     //未登录
        public static final int ERROR_LOGIN_EMPLOY = 100003;     //账号被抢登
        public static final int ERROR_LOGIN_BLACK = 100501;     //黑名单强制退出
        public static final int ERROR_SYSTEM_ERROR = 999999;     //系统错误
        public static final int ERROR_ROOM_MAINTAIN = 100100;        //娃娃机维护
        public static final int ERROR_ROOM_IN_USEING = 100101;       //娃娃机占用
        public static final int ERROR_ROOM_GAMING = 100102;          //娃娃机占用
        public static final int ERROR_BALANCE_NOT_ENOUGH = 100103;   //抓币不足
        public static final int MESSAGE_NETWORK_ERROR = 110000;           //无网络
        public static final int MESSAGE_NETWORK_OK = 120000;           //无网络
        public static final int MESSAGE_LOGIN = 130000;           //手机登陆
    }

    /**
     * 数据埋点类型
     */
    public static class EventSubType {
        public static final int ACTION_DOWNLOAD = 1;                 //点击下载
        public static final int ACTION_WECHAT_LOGIN_CLICK = 2;       //点击微信登录
        public static final int ACTION_WECHAT_LOGIN_SUCCESS = 3;     //登录成功
        public static final int ACTION_SHOW_NEW_USER_GIFT = 4;       //新人大礼弹窗曝光
        public static final int ACTION_NEW_USER_GIFT_CLICK = 5;      //新人大礼点击立即开始
        public static final int ACTION_ENTER_HOME = 6;               //到达首页
        public static final int ACTION_PULL_ROOM_LIST = 7;           //下拉刷新
        public static final int ACTION_SHOW_BANNER = 8;              //banner曝光
        public static final int ACTION_BANNER_CLICK = 127;             //点击banner
        public static final int ACTION_ENTER_ROOM_CLICK = 10;              //点击进入房间
        public static final int ACTION_START_GAME_CLICK = 11;        //点击开始抓娃娃
        public static final int ACTION_CHANGE_CAMERA_CLICK = 12;     //点击切换视角
        public static final int ACTION_CONTROL_TOP_CLICK = 13;       //点击上键
        public static final int ACTION_CONTROL_BOTTOM_CLICK = 14;    //点击下键
        public static final int ACTION_CONTROL_LEFT_CLICK = 15;      //点击左键
        public static final int ACTION_CONTROL_RIGHT_CLICK = 16;     //点击右键
        public static final int ACTION_CONTROL_GRAB_CLICK = 17;      //点击下抓
        public static final int ACTION_ROOM_RETURN_CLICK = 18;       //点击返回
        public static final int ACTION_ROOM_EXIT_GAME_SHOW = 19;     //退出提示弹窗
        public static final int ACTION_ROOM_CONTINUE_GAME_CLICK = 20;//点击继续游戏
        public static final int ACTION_ROOM_EXIT_GAME_CLICK = 21;    //点击中途放弃
        public static final int ACTION_ROOM_GAME_FAIL_DIALOG_SHOW = 22;//失败弹窗
        public static final int ACTION_ROOM_GAME_FAIL_COME_AGAIN = 23;//失败弹窗_点击再来一次
        public static final int ACTION_ROOM_GAME_FAIL_GIVE_UP = 24;  //失败弹窗_点击放弃
        public static final int ACTION_ROOM_GAME_SUCCESS_COME_AGAIN = 25;//成功弹窗_点击再来一次
        public static final int ACTION_ROOM_GAME_SUCCESS_CHECK_DOLL = 129;//成功弹窗_点击查看娃娃
        public static final int ACTION_HOME_FIRST_RECHARGE_SHOW = 27;//首充弹窗曝光
        public static final int ACTION_HOME_FIRST_RECHARGE_CLICK = 28;//首充弹窗点击
        public static final int ACTION_HOME_FIRST_RECHARGE_GIFT_SHOW = 29;//首充礼包icon浮标曝光
        public static final int ACTION_HOME_FIRST_RECHARGE_GIFT_CLICK = 30;//击首充礼包
        public static final int ACTION_HOME_RECHARGE_CLICK = 31;//点击抓抓币
        public static final int ACTION_MINE_RECHARGE_CLICK = 32;//点击我的游戏币
        public static final int ACTION_ENTER_RECHARGE = 33;//进入充值页
        public static final int ACTION_RECHARGE_ITEM_CLICK = 34;//点击选择充值
        public static final int ACTION_RECHARGE_PAY_CLICK = 35;//点击确认支付
        public static final int ACTION_ROOM_FIRST_RECHARGE_SHOW = 36;//首次失败弹窗
        public static final int ACTION_ROOM_FIRST_RECHARGE_CLICK = 37;//首次失败弹窗_点击首充
        public static final int ACTION_ROOM_FIRST_RECHARGE_GIVE_UP = 38;//首次失败弹窗_点击放弃
        public static final int ACTION_ROOM_RECHARGE_CLICK = 39;//点击充值
        public static final int ACTION_ROOM_START_GAME_RECHARGE_SHOW = 40;//开始游戏_余额不足弹窗
        public static final int ACTION_ROOM_START_GAME_RECHARGE_PAY = 41;//开始游戏_余额不足弹窗_点击充值
        public static final int ACTION_RECHARGE_DIALOG_SHOW = 42;//快捷充值页曝光
        public static final int ACTION_RECHARGE_DIALOG_ITEM_CLICK = 43;//点击选择充值
        public static final int ACTION_RECHARGE_DIALOG_PAY_CLICK = 44;//点击选择充值
        public static final int ACTION_MINE_DOLL_CLICK = 45;//点击我的娃娃
        public static final int ACTION_MINE_SERVICE_CLICK = 46;//联系客服入口 点击
        public static final int ACTION_MINE_LOGIN_OUT_CLICK = 47;//点击退出账号
        public static final int ACTION_MINE_LOGIN_OUT_CONFIRM = 48;//退出账号_点击确认退出
        public static final int ACTION_MINE_SERVICE_QQ_CLICK = 49;//点击联系
        public static final int ACTION_MINE_REQUEST_DELIVER_CLICK = 50;//点击申请发货
        public static final int ACTION_MINE_INPUT_ADDRESS_SUBMIT = 51;//点击地址确认
        public static final int ACTION_MINE_DELIVER_CONFIRM = 52;//点击确认发货
        public static final int ACTION_GAME_TIME = 53;//从开始游戏到游戏结束的时长
        public static final int ACTION_LOGIN_SHOW = 55;//登录页曝光
        public static final int ACTION_GET_AUDIO_PERMISSION = 56;//用户授予音频权限
        public static final int ACTION_GAME_SHOW_SUCCESS_DIALOG = 128;//成功弹窗
        public static final int ACTION_UPDATE_DIALOG_SHOW = 65;//版本更新弹窗曝光
        public static final int ACTION_UPDATE_DIALOG_CLICK = 66;//版本更新弹窗点击
        public static final int ACTION_RECHARGE_INVITE_USER_DIALOG_SHOW = 67;//邀请好友-快捷充值入口曝光
        public static final int ACTION_RECHARGE_INVITE_USER_DIALOG_CLICK = 68;//邀请好友-快捷充值入口点击
        public static final int ACTION_GAME_FAIL_INVITE_USER_DIALOG_SHOW = 69;//邀请好友-引导首充弹窗曝光
        public static final int ACTION_GAME_FAIL_INVITE_USER_DIALOG_CLICK = 70;//邀请好友-引导首充弹窗点击
        public static final int ACTION_ENTER_MINE = 71;//进入我的页面
        public static final int ACTION_MINE_INVITE_USER_CLICK = 72;//点击我的页面_邀请好友
        public static final int ACTION_INVITE_GIFT_DIALOG_SHOW = 80;//好友分享大礼包弹窗
        public static final int ACTION_SHARE_CHANNEL_CLICK = 75;//分享渠道点击
        public static final int ACTION_PUSH_MESSAGE_SHOW = 78; //推送消息曝光
        public static final int ACTION_PUSH_MESSAGE_CLICK = 79;//推送消息点击
        public static final int ACTION_WAWA_SINGLE_DIALOG = 88;//一只娃娃弹窗
        public static final int ACTION_RECHARGE_BANNER_EXPOSURE = 89;//充值  充值主页banner曝光
        //v1.2
        public static final int ACTION_ROOM_SCROLL = 90;//进房间滑动
        public static final int ACTION_ROOM_REPORT_CLICK = 91;//故障入口点击
        public static final int ACTION_ROOM_DOLL_DETAIL_CLICK = 92;//娃娃详情点击
        public static final int ACTION_ROOM_DOLL_RECORD_CLICK = 93;//娃娃抓取记录点击
        public static final int ACTION_ROOM_DOLL_RECORD_POP_CLICK = 94;//抓取记录气泡点击

        public static final int ACTION_HOME_OPERATE_EXPOSURE = 101;//大弹窗运营位	大弹窗运营位 曝光	101
        public static final int ACTION_HOME_OPERATE_CLICK = 102;//大弹窗运营位	大弹窗运营位 点击	102
        public static final int ACTION_HOME_RECHARGE_OK_EXPOSURE = 103;//充值成功再弹弹窗 曝光	103
        public static final int ACTION_RECHARGE_OK_CLICK = 104;//充值成功再弹弹窗 点击	104
        public static final int ACTION_SPLASH_CLICK = 105;//开屏广告广告点击	105
        public static final int ACTION_SPLASH_SKIP_CLICK = 106;//开屏广告跳过按钮 点击	106
        public static final int ACTION_HOME_ACTIVITY_FLOAT_CENTER_EXPOSURE = 107;//	活动中心首页浮标 曝光	107
        public static final int ACTION_HOME_ACTIVITY_FLOAT_CENTER_CLICK = 108;//	活动中心首页浮标 点击	108
        public static final int ACTION_HOME_ACTIVITY_POP_CENTER_EXPOSURE = 109;//	活动中心首页气泡 曝光	107
        public static final int ACTION_HOME_ACTIVITY_POP_CENTER_CLICK = 110;//	活动中心首页气泡 点击	108
        public static final int ACTION_LOGIN_BLACK_LIST = 111;//黑名单阻挡弹窗 曝光
        public static final int ACTION_LOGIN_BLACK_LIST_SERVICE = 112;//黑名单联系客服点击

        //v1.3.0
        public static final int ACTION_HOME_BANNER_SHOW = 115;// 首页banner曝光
        public static final int ACTION_ROOM_NO_COIN_EXPOSURE = 116;// 抓抓币不足点击开始抓充值弹窗 活动图片曝光
        public static final int ACTION_ROOM_TOP_RIGHT_RECHARGE_EXPOSURE = 117;// 房间右上角充值通道充值弹窗 活动图片曝光
        public static final int ACTION_AGAIN_NO_COIN_EXPOSURE = 118;// 用户抓完一句娃娃后点击再抓一次抓抓币不足 活动弹窗曝光
        public static final int ACTION_ROOM_PLAYER_VIEW_SHOW = 123;// 房间左上角头像曝光
        public static final int ACTION_ROOM_PLAYER_VIEW_CLICK = 124;// 房间左上角头像点击
        public static final int ACTION_ROOM_MEMBERLIST_SHOW = 125;// 房间左上角头像曝光
        public static final int ACTION_ROOM_MEMBERLIST_CLICK = 126;// 房间右上角头像点击

        //v1.4.0
        public static final int ACTION_SPLASH_EXPOSURE = 138;// 开屏广告广告曝光
        public static final int ACTION_DOLL_DETAIL_POP_EXPOSURE = 139;// 填写地址前两只发货规则提醒弹窗  曝光
        public static final int ACTION_GAME_SUCCESS_SHARE_CLICK = 130;//抓中娃娃弹窗 炫耀一下点击
        public static final int ACTION_ORDER_SHARE_SHOW = 131;//炫耀一下入口曝光
        public static final int ACTION_ORDER_SHARE_CLICK = 132;//炫耀一下入口点击
        public static final int ACTION_SHARE_DOLL_SHOW = 133;//分享页曝光
        public static final int ACTION_SHARE_DOLL_SHARE_CLICK = 134;//分享页分享渠道点击
        public static final int ACTION_STRONG_NOT_CATCH_DIALOG_SHOW = 135;//没抓中福利弹窗 曝光
        public static final int ACTION_STRONG_NOT_CATCH_DIALOG_CONTINUE = 136;//没抓中福利弹窗 继续抓 点击

        //v1.5.0
        public static final int ACTION_DOLL_DETAIL_SHOW = 95;//娃娃详情页曝光
        public static final int ACTION_DOLL_RECORD_SHOW = 96;//抓取记录页曝光

        public static final int ACTION_HOME_ACTIVITY_EXPOSURE = 140;//	首页胶囊位 banner 曝光
        public static final int ACTION_HOME_ACTIVITY_CLICK = 141;//	首页胶囊位 banner 点击
        public static final int ACTION_ROOM_GUIDE_RECHARGE_SHOW = 148;//	房间 引导充值活动弹窗 曝光
        public static final int ACTION_ROOM_GUIDE_RECHARGE_CLICK = 149;//	房间 引导充值活动弹窗 点击

        //v2.0
        public static final int ACTION_HOME_FLOOR_EXPOSURE = 152;//	楼层资源位 曝光
        public static final int ACTION_HOME_FLOOR_CLICK = 153;//	楼层资源位 曝光
        public static final int ACTION_HOME_MESSAGE_CLICK = 156;//		站内信 入口点击
        public static final int ACTION_LEDOU_MALL_CLICK = 157;//	乐豆商城 点击
        public static final int ACTION_MINE_SIGN_CLICK = 162;//	签到入口点击
        public static final int ACTION_ROOM_SHOW = 154;//	房间曝光
        public static final int ACTION_ROOM_SWITCH_CLICK = 161;//	房间切一切按钮点击
        public static final int ACTION_HOME_SIGN_CLICK = 155;//	首页签到领币点击

        //v2.0.1
        public static final int ACTION_ROOM_SWITCH_CHAT = 158;//展开收起聊天按钮点击
        public static final int ACTION_ROOM_CHAT_CLICK = 159;//展开收起聊天按钮点击
        public static final int ACTION_ROOM_CHAT_SEND = 160;//展开收起聊天按钮点击

    }

    public static class RECHAEGE_REFER_TYPE {
        public static final int from_start_game = 1;
        public static final int from_room_recharge = 2;
        public static final int from_game_result = 3;
        public static final int from_home_first_recharge = 4;
        public static final int from_mine = 5;
        public static final int from_home_recharge = 6;
    }

    public static final class Game_Fail_Dialog_Config {
        public static final int TYPE_COMMON = 1;//普通抓取失败
        public static final int TYPE_FIRST_RECHARGE = 2;//首充弹窗
        public static final int TYPE_STRONG_GRAB_FAIL = 3;//天降福利弹窗
        public static final int TYPE_RECHARGE = 4;//充值弹窗
        public static final int TYPE_RED_PACKET_RECHARGE = 5;//红包未打开引导充值弹窗

    }

    /**
     * 发送的socket消息类型
     */
    public static class ACY_TYPE {
        public static final int TYPE_REGISTER = 1; //创建用户端连接
        public static final int TYPE_MACHINE = 5; //发送给娃娃机
        public static final int TYPE_HEARTBEAT = 0; //心跳包
        public static final int TYPE_ROOM_HEARTBEAT = 6; //房间心跳包
    }


    /**
     * 娃娃页面类型
     */
    public static class WawaListType {
        public static final int TYPE_A = 0;
    }

    /**
     * 娃娃页面类型
     */
    public static class WawaDetailListType {
        public static final int TYPE_A = 0;
        public static final int TYPE_B = 1;
        public static final int TYPE_C = 2;
        public static final int TYPE_ADDRESS = 3;
    }
}
