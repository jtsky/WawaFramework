package com.duiba.plugin_component

import com.duiba.plugin_component.extend.ComExtension
import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

/**
 * A Plugin represents an extension to Gradle. A plugin applies some configuration to a target object.
 * Usually, this target object is a Project, but plugins can be applied to any type of objects.
 * 不插入相关代码 只配置gradle
 * */
class ComBuildSingle implements Plugin<Project> {
    //需要单独运行的组件 默认是app，直接运行assembleRelease的时候，等同于运行app:assembleRelease
    String mCompileModuleName = "app"
    /*There is a one-to-one relationship between a Project and a "build.gradle" file*/
    /**
     * mProject 对应于每个module下面的build.gradle文件
     *
     * */
    @Override
    void apply(Project project) {
        //创建当前module扩展对象 便于配置 属性名为ComExtension的属性名
        project.extensions.create('comBuild', ComExtension)
        String mainModuleName = project.rootProject.properties.get("mainModuleName")
        println("\n")
        println("\n")
        println("====mainModuleName====>${mainModuleName}")
        //得到当前项目的gradle配置
        List<String> taskNames = project.gradle.startParameter.taskNames
        String str_taskNames = taskNames.toString()
        println("===taskNames====>${str_taskNames}")
        //当前模块名
        String moduleName = project.path.replace(":", "")
        println("===current module is====>${moduleName}")
        //得到当前项目编译task详情
        AssembleTask assembleTask = getAssembleTask(taskNames)
        println("====assembleTask======>${assembleTask.toString()}")

        if (assembleTask.isAssemble) {
            fetchMainModuleName(project, assembleTask)
            println("====mCompileModuleName====>${mCompileModuleName}")
        }

        //判断当前模块是都配置了单独运行
        if (!project.hasProperty('isRunAlone')) {
            throw new RuntimeException("请在 ${moduleName} 的gradle.properties下设置isRunAlone 字段")
        }
        //对于isRunAlone==true的情况需要根据实际情况修改其值，
        //但如果是false，则不用修改，该module作为一个lib，运行module:assembleRelease则发布aar到中央仓库
        boolean moduleIsRunAlone = Boolean.parseBoolean(project.properties.get("isRunAlone"))

        if (moduleIsRunAlone && assembleTask.isAssemble) {
            //对于要编译的组件,isRunAlone修改为true,其他组件修改为false
            //这意味着library不能引用application
            if (moduleName == mCompileModuleName || moduleName == mainModuleName) {
                moduleIsRunAlone = true
            } else {
                moduleIsRunAlone = false
            }
        }
        //设置当前运行组件的isRunAlone 为true 其他的为false
        project.setProperty("isRunAlone", moduleIsRunAlone)
        //根据配置添加各种组件依赖，并且自动化生成组件加载代码
        if (moduleIsRunAlone) {
            project.apply plugin: 'com.android.application'
            if (moduleName != mainModuleName) {
                project.android.sourceSets {
                    main {
                        manifest.srcFile 'src/runalone/AndroidManifest.xml'
                        java.srcDirs = ['src/main/java', 'src/runalone/java']
                        res.srcDirs = ['src/main/res', 'src/runalone/res']
                    }
                }
            }
            println("${moduleName} apply plugin application")
            if (assembleTask.isAssemble && moduleName == mCompileModuleName) {
                //自动关联第三方模块
                compileComponents(assembleTask, project)
                //注册Transform 进行动态代码注入
                //project.android.registerTransform(new ComCodeTransform(project))
                //清理依赖
//                try {
//                    cancelDependent(project)
//                } catch (Exception e) {
//                    println("e==>${e.getMessage()}")
//                }
            }
        } else {
            project.apply plugin: 'com.android.library'
            println("${moduleName} apply plugin library")
            project.afterEvaluate {
                Task assembleReleaseTask = project.tasks.findByPath("assembleRelease")
                if (assembleReleaseTask != null) {
                    assembleReleaseTask.doLast {
                        File inFile = project.file("build/outputs/aar/$moduleName-release.aar")
                        File outFile = project.file("../componentrelease")
                        File desFile = project.file("$moduleName-release.aar")
                        project.copy {
                            from inFile
                            into outFile
                            //重命名文件名
                            rename { String fileName -> desFile.name }
                        }
                        println("$moduleName-release.aar 拷贝成功")
                    }
                }
            }
        }
    }

    /**
     * 为什么要进行依赖清理 因为当我们完整打包并安装以后 组件的代码合资源已经进行了关联
     * 如果不手动进行清理的话 直接进行编码 我们能够在主模块直接引入其他组件的资源和代码
     *
     * @param project
     */
    private void cancelDependent(Project project) throws Exception {
        project.afterEvaluate {
            Task assembleDebugTask = project.tasks.findByPath("assembleDebug")
            if (assembleDebugTask != null) {
                assembleDebugTask.doLast {
                    //判断操作系统类型并执行指定的命令
                    if (Os.isFamily(Os.FAMILY_WINDOWS)) {
                        project.rootProject.exec {
                            commandLine "gradlew.bat",
                                    ":app:generateDebugSources",
                                    ":app:generateDebugAndroidTestSources",
                                    ":app:mockableAndroidJar"
                        }
                    } else {
                        project.rootProject.exec {
                            commandLine "gradlew",
                                    ":app:generateDebugSources",
                                    ":app:generateDebugAndroidTestSources",
                                    ":app:mockableAndroidJar"
                        }
                    }

                    println("===清理依赖成功===")
                }
            }
        }
    }

    /**
     * @sesc 进行代码隔离 自动添加依赖，只在运行assemble任务的才会添加依赖，因此在开发期间组件之间是完全感知不到的，这是做到完全隔离的关键
     * 支持两种语法：module或者modulePackage:module,前者之间引用module工程，后者使用componentrelease中已经发布的aar
     * @param assembleTask
     * @param project
     */
    private void compileComponents(AssembleTask assembleTask, Project project) {
        String components
        if (assembleTask.isDebug) {
            components = project.properties.get("debugComponent")
        } else {
            components = project.properties.get("compileComponent")
        }

        if (!components) {
            println("${mCompileModuleName} 没有第三方依赖")
            return
        }

        String[] compileComponents = components.split(",")
        if (compileComponents == null || compileComponents.length == 0) {
            println("${mCompileModuleName} 没有第三方依赖")
            return
        }
        for (String component : compileComponents) {
            println("${mCompileModuleName} 依赖 ${component}")
            if (component.contains(":")) {
                File file = project.file("../componentrelease/${component.split(":")[1]}-release.aar")
                if (file.exists()) {
                    project.dependencies.add("api", "$component-release@aar")
                    println("$mCompileModuleName 添加 $component--release@aar 成功")
                } else {
                    throw new RuntimeException(" $component--release@aar not found")
                }
            } else {

                project.dependencies.add("api", project.project(":$component"))
                println("$mCompileModuleName 添加 :$component 成功")
            }
        }
    }

    /**
     * @desc 根据当前的task，获取当前要单独运行的组件，规则如下：
     * assembleRelease ---app
     * app:assembleRelease :app:assembleRelease ---app
     * component_share:assembleRelease :component_share:assembleRelease ---component_share
     * @param assembleTask
     */
    private void fetchMainModuleName(Project project, AssembleTask assembleTask) {
        if (!project.rootProject.hasProperty('mainModuleName')) {
            throw new RuntimeException('请在rootproject\'s gradle.properties中设置主Module的名称')
        }
        if (assembleTask.assembleNames.size() > 0 &&
                assembleTask.assembleNames.get(0) !=
                null &&
                assembleTask.assembleNames.get(0).trim().length() >
                0 &&
                !assembleTask.assembleNames.get(0).equals("all")) {
            mCompileModuleName = assembleTask.assembleNames.get(0)
        } else {
            mCompileModuleName = project.rootProject.properties.get("mainModuleName")
        }

        if (!mCompileModuleName) {
            mCompileModuleName = 'app'
        }
    }

    //从所有的task中得到属于编译的task
    private AssembleTask getAssembleTask(List<String> taskNames) {
        AssembleTask assembleTask = new AssembleTask()
        for (String task : taskNames) {
            if (task.toUpperCase().contains("ASSEMBLE") || task.contains("aR") ||
                    task.toUpperCase().contains("RESGUARD")) {
                if (task.toUpperCase().contains("DEBUG")) {
                    assembleTask.isDebug = true
                }
                assembleTask.isAssemble = true
                String[] strs = task.split(":")
                assembleTask.assembleNames.add(strs.length > 1 ? strs[strs.length - 2] : "all")
                break
            }
        }
        return assembleTask
    }
}