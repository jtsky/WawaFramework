package com.duiba.plugin_component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: jintai
 * @time: 2017/11/7-14:38
 * @Email: jintai@duiba.com.cn
 * @desc:
 */
class AssembleTask {
  /**
   * 是否属于编译task
   */
  boolean isAssemble = false;
  /**
   * 是否debug
   */
  boolean isDebug = false;
  /**
   * 当前模块执行打包时所执行的相关task
   */
  List<String> assembleNames = new ArrayList<>();

  @Override public String toString() {
    return "AssembleTask{"
        + "isAssemble="
        + isAssemble
        + ", isDebug="
        + isDebug
        + ", assembleNames="
        + assembleNames
        + '}';
  }
}
