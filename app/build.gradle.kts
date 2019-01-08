plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(28)

    defaultConfig {
        minSdkVersion(18)
        targetSdkVersion(28)
        applicationId = "com.fpliu.newton.ui.recyclerview.sample"
        versionCode = 1
        versionName = "1.0.0"
    }

    sourceSets {
        getByName("main") {
            jniLibs.srcDir("src/main/libs")
            aidl.srcDirs("src/main/java")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    lintOptions {
        isAbortOnError = false
    }

    compileOptions {
        //使用JAVA8语法解析
        setSourceCompatibility(JavaVersion.VERSION_1_8)
        setTargetCompatibility(JavaVersion.VERSION_1_8)
    }
}

dependencies {
    api(project(":library"))
//    api("com.fpliu:Android-RecyclerViewHelper:1.0.0")

    //http://kotlinlang.org/docs/reference/using-gradle.html#configuring-dependencies
    api(kotlin("stdlib", rootProject.extra["kotlinVersion"] as String))

    api("com.fpliu:Android-BaseUI:2.0.0")
    api("com.fpliu:Android-CustomDimen:1.0.0")
    api("com.fpliu:Android-CustomDrawable:1.0.0")

//    api("com.fpliu:Android-List:1.0.0")
}
