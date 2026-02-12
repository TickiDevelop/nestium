import org.gradle.api.publish.maven.MavenPublication
import org.gradle.jvm.tasks.Jar
import org.gradle.language.jvm.tasks.ProcessResources

plugins {
    id("java-library")
    id("multiloader-base")
    id("maven-publish")
}

val configurationDesktopIntegrationJava: Configuration = configurations.create("commonDesktopIntegration") {
    isCanBeResolved = true
}

dependencies {
    configurationDesktopIntegrationJava(project(path = ":common", configuration = "commonDesktopJava"))
}

tasks {
    named<ProcessResources>("processResources") {
        inputs.property("version", version)

        filesMatching(listOf("fabric.mod.json", "META-INF/neoforge.mods.toml")) {
            expand(mapOf("version" to version))
        }
    }

    named<Jar>("jar") {
        duplicatesStrategy = DuplicatesStrategy.FAIL
        from(rootDir.resolve("LICENSE.md"))

        // Entry-point for desktop integration when the file is executed directly
        from(configurationDesktopIntegrationJava)
        manifest.attributes["Main-Class"] = "net.caffeinemc.mods.nestium.desktop.LaunchWarn"
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()

            from(components["java"])
        }
    }
}
