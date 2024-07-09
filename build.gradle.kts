import com.alcosi.gradle.dependency.group.JsonGroupedGenerator
import com.alcosi.gradle.dependency.group.MDGroupedGenerator
import com.github.jk1.license.LicenseReportExtension

/** This plugin is only used to generate DEPENDENCIES.md file */
buildscript {
    dependencies {
        classpath("com.alcosi:dependency-license-page-generator:1.0.0")
    }
}
plugins {
    val kotlinVersion = "2.0.0"
    id("idea")
    id("org.jetbrains.kotlin.plugin.allopen") version "2.0.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
    id("org.springframework.boot") version "3.3.1"
    id("io.spring.dependency-management") version "1.1.5"
    id("java-library")
    id("maven-publish")
    id("net.thebugmc.gradle.sonatype-central-portal-publisher") version "1.2.3"
    id("com.github.jk1.dependency-license-report") version "2.8"
    id("org.jetbrains.dokka") version "1.9.20"
    id("org.jetbrains.kotlin.kapt") version kotlinVersion
    id("org.jetbrains.kotlin.jvm") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
    id("com.squareup.wire") version "4.9.9"

}


val gitUsername = "${System.getenv()["GIHUB_PACKAGE_USERNAME"] ?: System.getenv()["GITHUB_PACKAGE_USERNAME"]}"
val gitToken = "${System.getenv()["GIHUB_PACKAGE_TOKEN"] ?: System.getenv()["GITHUB_PACKAGE_TOKEN"]}"



val javaVersion = JavaVersion.VERSION_21
val env = "RELEASE"

group = "com.alcosi"
version = "1.14-$env"
java.sourceCompatibility = javaVersion

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}
java {
    withSourcesJar()
    withJavadocJar()
}





val repo = "github.com/alcosi/alcosi_template_service_communication"

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven {
        url = uri("https://repo1.maven.org/maven2")
    }
    maven { url = uri("https://jitpack.io") }
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.$repo")
            credentials {
                username = gitUsername
                password = gitToken
            }
        }
    }
}
signing {
    useGpgCmd()
}

centralPortal {
    pom {
        packaging = "jar"
        name.set(project.name)
        description.set("""
Library for low-level communication with Alcosi (C#) template service with spring boot initialization.        
        """)
        val repository = "https://$repo"
        url.set(repository)
//        licenses {
//            license {
//                name.set("Apache 2.0")
//                url.set("http://www.apache.org/licenses/LICENSE-2.0")
//            }
//        }
        scm {
            connection.set("scm:$repository.git")
            developerConnection.set("scm:git@$repo.git")
            url.set(repository)
        }
        developers {
            developer {
                id.set("Alcosi")
                name.set("Alcosi Group")
                email.set("info@alcosi.com")
                url.set("alcosi.com")
            }
        }
    }
}


configurations {
    configureEach {
        exclude(module = "flyway-core")
        exclude(module = "logback-classic")
        exclude(module = "log4j-to-slf4j")
    }
}



dependencies {
    compileOnly("org.springframework.boot:spring-boot-starter:3.3.1")
    api("io.github.breninsul:okhttp-logging-interceptor:1.1.3")
    api("com.squareup.okhttp3:okhttp:4.12.0")
    api("com.squareup.wire:wire-grpc-client:4.9.9")
    api("io.github.breninsul:named-limited-virtual-thread-executor:1.0.0")
    api("com.fasterxml.jackson.core:jackson-databind:2.17.1")
    api("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.1")
    api("io.github.breninsul:java-timer-scheduler-starter:1.0.3")
    kapt("org.apache.logging.log4j:log4j-core")
    kapt("org.springframework.boot:spring-boot-autoconfigure-processor")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
configure<SourceSetContainer> {
    named("main") {
        java.srcDir("src/main/kotlin")
    }
}

configurations {
    configureEach {
        exclude(module = "logback-classic")
        exclude(module = "log4j-to-slf4j")
    }
}


tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
tasks.getByName<Jar>("jar") {
    enabled = true
    archiveClassifier = ""
}
tasks.named("generateLicenseReport") {
    outputs.upToDateWhen { false }
}
/*
 * This plugin is only used to generate DEPENDENCIES.md file
 */
 licenseReport {
    unionParentPomLicenses = false
    outputDir = "$projectDir/reports/license"
    configurations = LicenseReportExtension.ALL
    excludeOwnGroup = false
    excludeBoms = false
    renderers =
        arrayOf(
            JsonGroupedGenerator("group-report.json", onlyOneLicensePerModule = false),
            MDGroupedGenerator(
                "../../DEPENDENCIES.md",
                onlyOneLicensePerModule = false,
            ),
        )
 }


wire {
    kotlin {}
}
