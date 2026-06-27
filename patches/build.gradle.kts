group = "app.morphe.patches.twitch"

patches {
    about {
        name = "BetterTTV Twitch Patches"
        description = "Integrate BetterTTV (BTTV), FrankerFaceZ (FFZ), and 7TV emotes into the official Twitch Android app."
        source = "https://github.com/4lbatorwun-dot/bettertwitchtv-morphe"
        author = "4lbatorwun-dot"
        contact = "na"
        website = "na"
        license = "GPLv3"
    }
}


kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }
}

// Separate configuration so gson is available at runtime for the
// generatePatchesList task but never bundled into the APK.
val patchListGeneratorClasspath: Configuration by configurations.creating

dependencies {
    compileOnly(libs.gson)
    patchListGeneratorClasspath(libs.gson)
}

tasks {
    register<JavaExec>("generatePatchesList") {
        description = "Build patch with patch list"

        dependsOn(build)

        classpath = sourceSets["main"].runtimeClasspath + patchListGeneratorClasspath
        mainClass.set("util.PatchListGeneratorKt")
    }

    // Used by gradle-semantic-release-plugin.
    publish {
        dependsOn("generatePatchesList")
    }
}