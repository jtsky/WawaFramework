package com.duiba.library_common.bean;

/**
 * @author: jintai
 * @time: 2017/6/19-13:58
 * @Email: jintai@qccr.com
 * @desc:eventBus bean
 */
public class Event<T extends Object> {
  private EventCode EventCode;
  private T data;

  public Event(EventCode eventCode, T data) {
    EventCode = eventCode;
    this.data = data;
  }

  public EventCode getEventCode() {
    return EventCode;
  }

  public void setEventCode(EventCode eventCode) {
    EventCode = eventCode;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
