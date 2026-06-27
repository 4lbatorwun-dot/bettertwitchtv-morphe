extension {
    name = "extensions/bettertwitchtv.mpe"
}

android {
    namespace = "bttv"
}

dependencies {
    compileOnly(project(":extensions:bettertwitchtv:stub"))
    compileOnly("androidx.appcompat:appcompat:1.2.0")
    compileOnly("com.google.android.material:material:1.3.0")
    implementation("com.github.zjupure:webpdecoder:2.0.4.12.0") {
        exclude(group = "com.github.bumptech.glide", module = "glide")
    }
    implementation("com.caverock:androidsvg-aar:1.4")
}



