import net.fabricmc.loom.task.RemapJarTask
import net.fabricmc.loom.task.RemapSourcesJarTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.text.MessageFormat.format as messageFormat

plugins {
    id("fabric-loom")
    id("maven-publish")
    id("org.jetbrains.kotlin.jvm")
}
repositories {
    jcenter()
    maven { url = uri("https://maven.fabricmc.net/") }
    maven { url = uri("https://maven.terraformersmc.com/releases/") }
    maven { url = uri("https://maven.shedaniel.me") }
}

dependencies {
    minecraft("com.mojang:minecraft:${project.extra["minecraft_version"]}")
    mappings("net.fabricmc:yarn:${project.extra["yarn_mappings"]}:v2")
    modImplementation("net.fabricmc:fabric-loader:${project.extra["loader_version"]}")

    // DEPENDENCY: Fabric API
    modImplementation("net.fabricmc.fabric-api:fabric-api:${project.extra["fabric_version"]}")

    // DEPENDENCY: Fabric Language Kotlin
    modImplementation("net.fabricmc:fabric-language-kotlin:${project.extra["fabric_kotlin_version"]}")

    // DEPENDENCY: Cloth Config 2
    modApi("me.shedaniel.cloth:cloth-config-fabric:${project.extra["cloth_config_version"]}") {
        exclude("net.fabricmc.fabric-api", "fabric-api")
    }
    include("me.shedaniel.cloth:cloth-config-fabric:${project.extra["cloth_config_version"]}") {
        exclude("net.fabricmc.fabric-api", "fabric-api")
    }

    //COMPATIBILITY: ModMenu
    modCompileOnly("com.terraformersmc:modmenu:${project.extra["modmenu_version"]}")

    // Test environiment with some mods
    modRuntimeOnly("me.shedaniel:RoughlyEnoughItems-fabric:${project.extra["rei_version"]}") {
        exclude("me.shedaniel.cloth")
    }

}

base.archivesBaseName = "${project.extra["archives_base_name"]}"
group = "${project.extra["maven_group"]}"
version = "${project.extra["mod_version"]}"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile> { kotlinOptions.jvmTarget = "17" }
tasks.withType<JavaCompile> { options.encoding = "UTF-8" }

tasks.processResources {
    inputs.property("version", project.version)

    filesMatching("fabric.mod.json") {
        expand("version" to project.version)
    }
}

val remapJar = tasks.getByName<RemapJarTask>("remapJar")
val remapSourcesJar = tasks.getByName<RemapSourcesJarTask>("remapSourcesJar")

// Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
// if it is present.
// If you remove this task, sources will not be generated.
val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

tasks.jar { from("LICENSE") }

// configure the maven publication
publishing {
    publications {
        create("mavenJava", MavenPublication::class.java) {
            groupId = project.group.toString()
            artifactId = project.name.toLowerCase()
            version = project.version.toString()

            // add all the jars that should be included when publishing to maven
            artifact(remapJar) { builtBy(remapJar) }
            artifact(sourcesJar) { builtBy(remapSourcesJar) }
        }
    }
    // select the repositories you want to publish to
    repositories { mavenLocal() }
}

tasks.create("printInfo") {
    doLast {
        println(
            messageFormat(
                """
            ----- BEGIN -----
            ### Release Info
            This release was built for Minecraft **{0}**, with Fabric Loader **{1}**, Fabric API **{2}** and Fabric Language Kotlin **{3}**.
    
            Mod Menu Integration was tested using version **{4}**.
            ------ END ------""".trimIndent(),
                project.extra["minecraft_version"],
                project.extra["loader_version"],
                project.extra["fabric_version"],
                project.extra["fabric_kotlin_version"],
                project.extra["modmenu_version"]
            )
        )
    }
}