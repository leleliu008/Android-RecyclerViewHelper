import java.util.Properties

group = "com.fpliu"
version = "1.0.0"

plugins {
    id("com.android.library")
    id("com.github.dcendents.android-maven")
    id("com.jfrog.bintray")
}

android {
    compileSdkVersion(26)
    buildToolsVersion("26.0.2")

    defaultConfig {
        minSdkVersion(14)
        targetSdkVersion(25)
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
    api(fileTree(mapOf(Pair("dir", "src/main/libs"), Pair("include", "*.jar"))))

    api("com.android.support:recyclerview-v7:26.1.0")

    //http://kotlinlang.org/docs/reference/using-gradle.html#configuring-dependencies
//    api("org.jetbrains.kotlin:kotlin-stdlib:1.2.21")
}

// 项目的主页,这个是说明，可随便填
val siteUrl = "https://github.com/leleliu008/Android-RecyclerViewHelper"

// GitHub仓库的URL,这个是说明，可随便填
val gitUrl = "https://github.com/leleliu008/Android-RecyclerViewHelper"

tasks {
    "install"(Upload::class) {
        repositories {
            withConvention(MavenRepositoryHandlerConvention::class) {
                mavenInstaller {
                    pom.project {
                        withGroovyBuilder {
                            "packaging"("jar")
                            "name"("Android-RecyclerViewHelper")
                            "url"(siteUrl)
                            "licenses" {
                                "license" {
                                    "name"("The Apache Software License, Version 2.0")
                                    "url"("http://www.apache.org/licenses/LICENSE-2.0.txt")
                                }
                            }
                            "developers" {
                                "developer" {
                                    "id"("fpliu")
                                    "name"("fpliu")
                                    "email"("leleliu008@gmail.com")
                                }
                            }
                            "scm" {
                                "connection"(gitUrl)
                                "developerConnection"(gitUrl)
                                "url"(siteUrl)
                            }
                        }
                    }
                }
            }
        }
    }
}


// 生成jar包的task
val sourcesJarTask = task("sourcesJar", Jar::class) {
    from(android.sourceSets["main"].java.srcDirs)
    classifier = "sources"
}

// 生成jarDoc的task
val javadocTask = task("javadoc", Javadoc::class) {
    source(android.sourceSets["main"].java.srcDirs)
    classpath += project.files(android.bootClasspath)
}

// 生成javaDoc的jar
val javadocJarTask = task("javadocJar", Jar::class) {
    dependsOn(javadocTask)
    classifier = "javadoc"
    from(javadocTask.destinationDir)
}

artifacts {
    add("archives", javadocJarTask)
    add("archives", sourcesJarTask)
}

val properties = Properties().apply { load(project.rootProject.file("local.properties").inputStream()) }
bintray {
    user = properties.getProperty("bintray.user")
    key = properties.getProperty("bintray.apikey")
    setConfigurations("archives")
    pkg = PackageConfig().apply {
        userOrg = "fpliu"
        repo = "newton"
        name = "Android-RecyclerViewHelper"
        websiteUrl = siteUrl
        vcsUrl = gitUrl
        setLicenses("Apache-2.0")
        publish = true
    }
}
