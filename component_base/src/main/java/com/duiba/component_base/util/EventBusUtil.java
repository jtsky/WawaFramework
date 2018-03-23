package com.duiba.component_base.util;

import com.duiba.library_common.bean.Event;
import org.greenrobot.eventbus.EventBus;

/**
 * @author: jintai
 * @time: 2017/6/19-13:56
 * @Email: jintai@duiba.com.cn
 * @desc:
 */
public class EventBusUtil {

  public static void register(Object subscriber) {
    EventBus.getDefault().register(subscriber);
  }

  public static void unRegister(Object subscriber) {
    EventBus.getDefault().unregister(subscriber);
  }

  public static void sendEvent(Event event) {
    EventBus.getDefault().post(event);
  }

  public static void sendStickyEvent(Event event) {
    EventBus.getDefault().postSticky(event);
  }
}
