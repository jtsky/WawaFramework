package com.duiba.plugin_component.extend;

/**
 * @author: jintai
 * @time: 2017/11/7-14:19
 * @Email: jintai@qccr.com
 * @desc:
 */
class ComExtension {

  /**
   * 是否自动注册组件，true则会使用字节码插入的方式自动注册代码
   * false的话，需要手动使用反射的方式来注册*/
  boolean isRegisterCompoAuto = false
  /**
   * 当前组件的applicationName，用于字节码插入。
   * 当isRegisterCompoAuto==true的时候是必须的*/
  String applicationName
  /**
   * 所有ApplicationLike接口完整包名*/
  String iApplicationLikeName
  /**
   * componentManager的完整包名
   * componentManager必须是单例模式的模板代码
   * 参考component_base里的ComponentManager*/
  String componentManagerName
}
