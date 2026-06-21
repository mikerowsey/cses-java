plugins {
    application
}

import org.gradle.api.tasks.JavaExec
import java.io.File

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
    mainClass.set("cses.CsesHelp")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<JavaExec>("runProblem") {
    group = "application"
    description = "Runs a chosen CSES class. Example: ./gradlew runProblem -Pprob=MissingNumber"

    val targetNameProvider = providers.gradleProperty("prob")

    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set(targetNameProvider.map { "cses.$it" })
    standardInput = System.`in`

    doFirst {
        if (!targetNameProvider.isPresent) {
            throw GradleException("Error: pass a problem name via -Pprob, for example -Pprob=WeirdAlgorithm")
        }
    }
}

tasks.register("bundleMain") {
    group = "verification"
    description = "Builds build/distributions/Main.java for CSES submission from -Pprob=<ProblemClass>"
    notCompatibleWithConfigurationCache("Uses ad-hoc file system traversal for source bundling")

    val targetNameProvider = providers.gradleProperty("prob")
    val srcDir = layout.projectDirectory.dir("src/main/java/cses").asFile
    val outputFileProvider = layout.buildDirectory.file("distributions/Main.java")

    doLast {
        val targetName = targetNameProvider.orNull
            ?: throw GradleException("Error: pass a problem name via -Pprob, for example -Pprob=WeirdAlgorithm")

        val mainFile = File(srcDir, "$targetName.java")
        val outputFile = outputFileProvider.get().asFile

        if (!mainFile.exists()) {
            throw GradleException("Error: missing file ${mainFile.absolutePath}")
        }

        outputFile.parentFile.mkdirs()
        outputFile.writeText("")

        val allImports = mutableSetOf<String>()
        srcDir.listFiles { file -> file.isFile && file.extension == "java" }?.forEach { sourceFile ->
            sourceFile.useLines { lines ->
                lines.forEach { line ->
                    if (line.trim().startsWith("import ")) {
                        allImports.add(line.trim())
                    }
                }
            }
        }
        allImports.sorted().forEach { outputFile.appendText(it + "\n") }
        outputFile.appendText("\n")

        mainFile.useLines { lines ->
            lines.forEach { line ->
                val trimmed = line.trim()
                if (!trimmed.startsWith("package ") && !trimmed.startsWith("import ")) {
                    val modifiedLine = line
                        .replace("public class $targetName", "public class Main")
                        .replace("class $targetName", "class Main")
                    outputFile.appendText(modifiedLine + "\n")
                }
            }
        }
        outputFile.appendText("\n")

        val utilities = listOf("FastScanner.java", "FastWriter.java")
        srcDir.listFiles { file -> file.isFile && file.name in utilities }?.forEach { utilityFile ->
            if (utilityFile.name == "$targetName.java") {
                return@forEach
            }
            utilityFile.useLines { lines ->
                lines.forEach { line ->
                    val trimmed = line.trim()
                    if (!trimmed.startsWith("package ") && !trimmed.startsWith("import ")) {
                        val modifiedLine = line.replace("public class", "class")
                        outputFile.appendText(modifiedLine + "\n")
                    }
                }
            }
            outputFile.appendText("\n")
        }

        println("Success: created ${outputFile.absolutePath}")
    }
}

