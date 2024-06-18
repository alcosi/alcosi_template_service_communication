import com.alcosi.gradle.dependency.group.JsonGroupedGenerator
import com.alcosi.gradle.dependency.group.MDGroupedGenerator
import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage
import com.bmuschko.gradle.docker.tasks.image.DockerPushImage
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
    id("com.bmuschko.docker-remote-api") version "9.4.0"
    id("org.springframework.boot") version "3.3.0"
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
version = "1.0-$env"
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
The application is a ready API Gateway with implemented features, including:
- Logging
- Authorization
- OpenAPI and Swagger distribution
- Authentication and authorization with Ethereum wallets, including refresh tokens.           
        """)
        val repository = "https://$repo"
        url.set(repository)
        licenses {
            license {
                name.set("Apache 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0")
            }
        }
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
    compileOnly("org.springframework.boot:spring-boot-starter:3.0.0")
    api("io.github.breninsul:okhttp-logging-interceptor:1.0.0")
    api("com.squareup.okhttp3:okhttp:4.12.0")
    api("com.squareup.wire:wire-grpc-client:4.9.9")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
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

val dockerUsername = System.getenv()["DOCKER_XRT_USERNAME"] ?: System.getenv()["CI_REGISTRY_USER"]
val dockerPass = System.getenv()["DOCKER_XRT_PASSWORD"] ?: System.getenv()["CI_JOB_TOKEN"]
val dockerRegistry = (System.getenv()["DOCKER_XRT_REGISTRY"] ?: System.getenv()["CI_REGISTRY"]) ?: "harbor.alcosi.com"
val dockerProjectNamespace = (System.getenv()["DOCKER_XRT_PROJECT_NAMESPACE"] ?: System.getenv()["CI_PROJECT_NAMESPACE"]) ?: "nft"
val dockerProjectName = (System.getenv()["DOCKER_XRT_PROJECT_NAME"] ?: System.getenv()["CI_PROJECT_NAME"]) ?: "nft-subscription"
val dockerHubProject = System.getenv()["DOCKER_XRT_PROJECT"] ?: "$dockerProjectNamespace/$dockerProjectName/"


val appName = project.name
val imageVersion = project.version

val dockerBuildDir = "build/docker/"
val uniqueContainerName = "$dockerRegistry/$dockerHubProject$appName:$imageVersion"
val uniqueContainerNameArm = "${uniqueContainerName}_arm"
val uniqueContainerNameX86 = "${uniqueContainerName}_x86"




docker {
    registryCredentials {
        url.set("https://$dockerRegistry/")
        username.set(dockerUsername)
        password.set(dockerPass)
    }
}

tasks.create("createDockerfile", com.bmuschko.gradle.docker.tasks.image.Dockerfile::class) {
    dependsOn("bootJar")
    destFile.set(project.file("$dockerBuildDir/Dockerfile"))
    from("amazoncorretto:21.0.3-alpine3.19")
    runCommand("mkdir /opt/app && mkdir /opt/app/logs")
    addFile("${project.name}-$version.jar", "/opt/app/app.jar")
    entryPoint("java")
    defaultCommand(
        "-jar",
        "--add-opens=java.base/java.lang.reflect=ALL-UNNAMED",
        "-Dapp.home=/opt/app/",
        "/opt/app/app.jar",
    )
}

tasks.create<Copy>("copyJarToDockerBuildDir") {
    dependsOn("createDockerfile")
    from("build/libs")
    into(dockerBuildDir)
    include("*.jar")
    doLast {
        println("copy File end")
    }
}
tasks.create<DockerBuildImage>("buildDockerImageX86") {
    val buildPlatform = "linux/amd64"
    val imageName = uniqueContainerNameX86
    configBuildTask(this, buildPlatform, imageName)
}

tasks.create<DockerBuildImage>("buildDockerImageArm") {
    val buildPlatform = "linux/arm64/v8"
    val imageName = uniqueContainerNameArm
    configBuildTask(this, buildPlatform, imageName)
}
tasks.create<Task>("buildDockerImages") {
    dependsOn("buildDockerImageArm", "buildDockerImageX86")
}

tasks.create<DockerPushImage>("pushDockerImageArm") {
    dependsOn("buildDockerImageArm")
    images.addAll(listOf(uniqueContainerNameArm))
    doLast {
        println("Image pushed: $uniqueContainerNameArm")
    }
}

tasks.create<DockerPushImage>("pushDockerImageX86") {
    dependsOn("buildDockerImageX86")
    images.addAll(listOf(uniqueContainerNameX86))
    doLast {
        println("Image pushed: $uniqueContainerNameX86")
    }
}

tasks.create<Task>("pushDockerImage") {
    dependsOn("pushDockerImageArm", "pushDockerImageX86")
}

fun configBuildTask(
    dockerBuildImage: DockerBuildImage,
    buildPlatform: String,
    imageName: String,
) {
    dockerBuildImage.dependsOn("copyJarToDockerBuildDir")
    dockerBuildImage.platform.set(buildPlatform)
    dockerBuildImage.inputDir.set(project.file(dockerBuildDir))
    dockerBuildImage.images.add(imageName)
}


wire {
    kotlin {}
}
