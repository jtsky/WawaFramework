package com.duiba.plugin_component

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import javassist.*
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

/**
 * @auth Jin
 */
class ComCodeTransform extends Transform {

  Project mProject
  ClassPool mClassPool
  String mApplicationName
  String mIApplicationLikeName
  String mComponentManagerName

  ComCodeTransform(Project mProject) {
    this.mProject = mProject
  }

  @Override
  void transform(TransformInvocation transformInvocation)
      throws TransformException, InterruptedException, IOException {
    Collection<TransformInput> inputs = transformInvocation.getInputs()

    setRealApplicationInfo()
    mClassPool = ClassPool.getDefault()

    mProject.android.bootClasspath.each {
      //添加android sdk 根路径到mClassPool中 ===bootClasspath===D:\Android\SDK\platforms\android-26\android.jar
      mClassPool.appendClassPath((String) it.absolutePath)
    }
    println("===inputs===$inputs")
    //将.class文件转化为CtClass集合 方便操作
    List<CtClass> box = ConvertUtils.toCtClasses(inputs, mClassPool)
    //要收集的application，一般情况下只有一个
    List<CtClass> applications = new ArrayList<>()
    //要收集的applicationlikes，一般情况下有几个组件就有几个applicationlike
    List<CtClass> applicationLikes = new ArrayList<>()

    for (CtClass ctClass : box) {
      if (checkIsApplication(ctClass)) {
        applications.add(ctClass)
        println("===Application===${ctClass.getName()}")
        //假如已经是application 肯定不是applicationlike
        continue
      }

      if (checkIsApplicationLike(ctClass)) {
        applicationLikes.add(ctClass)
        println("===Applicationlike===${ctClass.getName()}")
      }
    }

    inputs.each { TransformInput input ->
      //对类型为jar文件的input进行遍历
      input.jarInputs.each { JarInput jarInput ->
        //jar文件一般是第三方依赖库jar文件
        // 重命名输出文件（同目录copyFile会冲突）
        def jarName = jarInput.name
        def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
        if (jarName.endsWith(".jar")) {
          jarName = jarName.substring(0, jarName.length() - 4)
        }
        //生成输出路径
        def dest = transformInvocation.outputProvider.getContentLocation(jarName + md5Name,
            jarInput.contentTypes, jarInput.scopes, Format.JAR)
        //将输入内容复制到输出
        FileUtils.copyFile(jarInput.file, dest)
      }
      //对类型为“文件夹”的input进行遍历
      input.directoryInputs.each { DirectoryInput directoryInput ->
        boolean isRegisterCompoAuto = mProject.extensions.comBuild.isRegisterCompoAuto
        //是否需要自动注入代码
        if (isRegisterCompoAuto) {
          String fileName = directoryInput.file.absolutePath
          File dir = new File(fileName)
          //对文件下的文件进行遍历
          dir.eachFileRecurse { File file ->
            String filePath = file.absolutePath
            String classNameTemp = filePath.replace(fileName, "").
                replace("\\", ".").
                replace("/", ".")
            if (classNameTemp.endsWith(".class")) {
              String className = classNameTemp.substring(1, classNameTemp.length() - 6)
              if (className == (mApplicationName)) {
                injectApplicationCode(applications.get(0), applicationLikes, fileName)
              }
            }
          }
        }
        def dest = transformInvocation.outputProvider.getContentLocation(directoryInput.name,
            directoryInput.contentTypes,
            directoryInput.scopes, Format.DIRECTORY)
        // 将input的目录复制到output指定目录
        FileUtils.copyDirectory(directoryInput.file, dest)
      }
    }
  }

  //设置applicationName
  private void setRealApplicationInfo() {
    this.mApplicationName = mProject.extensions.comBuild.applicationName
    this.mIApplicationLikeName = mProject.extensions.comBuild.iApplicationLikeName
    this.mComponentManagerName = mProject.extensions.comBuild.componentManagerName
    if (mApplicationName == null || mApplicationName.isEmpty()) {
      throw new NullPointerException(
          "请在${mProject.path.replace(":", "")}的combuild中配置applicationName")
    }

    if (mIApplicationLikeName == null || mIApplicationLikeName.isEmpty()) {
      throw new NullPointerException(
          "请在${mProject.path.replace(":", "")}的combuild中配置iApplicationLikeName")
    }
    if (mComponentManagerName == null || mComponentManagerName.isEmpty()) {
      throw new NullPointerException(
          "请在${mProject.path.replace(":", "")}的combuild中配置componentManagerName")
    }

    println("===mApplicationName===$mApplicationName")
    println("===mIApplicationLikeName===$mIApplicationLikeName")
    println("===mIApplicationLikeName===$mComponentManagerName")
  }

  /**
   * 注入代码
   * @param ctClassApplication
   * @param applicationLikes
   * @param patch
   */
  private void injectApplicationCode(CtClass ctClassApplication, List<CtClass> applicationLikes,
      String patch) {
    println("===injectApplicationCode begin===")
    //相对文件进行解除绑定 否则会报错
    ctClassApplication.defrost()
    try {
      CtMethod attachBaseContextMethod = ctClassApplication.getDeclaredMethod("onCreate", null)
      //获得要在application中需要注入的组件代码
      String insertCode = getAutoLoadComCode(applicationLikes)
      println("===insertCode===$insertCode")
      attachBaseContextMethod.insertAfter(insertCode)
    } catch (CannotCompileException | NotFoundException e) {
      StringBuilder methodBody = new StringBuilder()
      methodBody.append("protected void onCreate() {")
      methodBody.append("super.onCreate();")
      methodBody.
          append(getAutoLoadComCode(applicationLikes))
      methodBody.append("}")
      ctClassApplication.addMethod(CtMethod.make(methodBody.toString(), ctClassApplication))
    } catch (Exception e) {
    }
    //将新的application写入到指定的路径下
    ctClassApplication.writeFile(patch)
    //从ClassPool中溢出当前文件
    ctClassApplication.detach()

    println("===injectApplicationCode success===")
  }

  private String getAutoLoadComCode(List<CtClass> applicationLikes) {
    StringBuilder autoLoadComCode = new StringBuilder()
    for (CtClass ctClass : applicationLikes) {
      //autoLoadComCode.append("new " + ctClass.getName() + "()" + ".onCreate();")
      //ComponentManager.getInstance().registerComponent("com.jin.component_user.applike.UserAppLike")
      autoLoadComCode.append(
          "${mComponentManagerName}.getInstance().registerComponent(\"${ctClass.getName()}\");")
    }

    return autoLoadComCode.toString()
  }

  /**
   * @desc 检查是否是application类型的类
   * @param ctClass
   * @return
   */
  private boolean checkIsApplication(CtClass ctClass) {
    try {
      if (mApplicationName != null && mApplicationName == ctClass.getName()) {
        return true
      }
    } catch (Exception e) {
      println "class not found exception class name:  " + ctClass.getName()
    }
    return false
  }

  /**
   * @desc 检查是否是applicationLike类型的类
   * @param ctClass
   * @return
   */
  private boolean checkIsApplicationLike(CtClass ctClass) {
    try {
      for (CtClass ctClassInter : ctClass.getInterfaces()) {
        if (mIApplicationLikeName == (ctClassInter.name)) {
          return true
        }
      }
    } catch (Exception e) {
      println "class not found exception class name:  " + ctClass.getName()
    }

    return false
  }

  // 设置我们自定义的Transform对应的Task名称
  // 类似：TransformClassesWithPreDexForXXX
  @Override
  String getName() {
    return "ComCodeTransform"
  }

  // 指定输入的类型，通过这里的设定，可以指定我们要处理的文件类型
  //这样确保其他类型的文件不会传入
  @Override
  Set<QualifiedContent.ContentType> getInputTypes() {
    //只转换.class文件
    return TransformManager.CONTENT_CLASS
  }

  // 指定Transform的作用范围
  @Override
  Set<? super QualifiedContent.Scope> getScopes() {
    return TransformManager.SCOPE_FULL_PROJECT
  }

  @Override
  boolean isIncremental() {
    return false
  }
}