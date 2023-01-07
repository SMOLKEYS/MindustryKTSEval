import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins{
    java
    kotlin("jvm") version "1.7.21"
}

val mindustryVersion = "v140.4"
/** The output jar files will contain this string in their names. */
val jarName = "mindktseval"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("com.github.Anuken.Arc:arc-core:$mindustryVersion")
    compileOnly("com.github.Anuken.Mindustry:core:$mindustryVersion")
    
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    
    
    implementation("org.jetbrains.kotlin:kotlin-script-runtime:1.7.21")
    //implementation("org.jetbrains.kotlin:kotlin-script-util:1.7.21")
    implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.7.21")
    implementation("org.jetbrains.kotlin:kotlin-scripting-compiler-embeddable:1.7.21")
    //implementation("org.jetbrains.kotlin:kotlin-scripting-jsr223:1.7.21")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        // use the experimental kotlin compiler, which is better than the old one.
        freeCompilerArgs += "-Xuse-k2"
    }
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    archiveFileName.set("${jarName}.jar")

    from(*configurations.runtimeClasspath.files.map { if (it.isDirectory()) it else zipTree(it) }.toTypedArray())

    from(rootDir) {
        include("plugin.json")
    }
}
