import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // kotlin
    kotlin("jvm") version "1.6.21"

    // spring
    id("io.spring.dependency-management") version "1.0.15.RELEASE" apply false
    kotlin("plugin.spring") version "1.6.21" apply false
    id("org.springframework.boot") version "2.7.7" apply false
}

java.sourceCompatibility = JavaVersion.VERSION_11

allprojects{
    group = "io.kopring.multimodule"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects{
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    dependencies {
//        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    tasks.getByName("bootJar"){
        enabled = false
    }

    tasks.getByName("jar"){
        enabled = true
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

