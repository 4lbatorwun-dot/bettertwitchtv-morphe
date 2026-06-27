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
    compileOnly("io.reactivex.rxjava2:rxjava:2.2.21")
    compileOnly("javax.inject:javax.inject:1")
    compileOnly("androidx.recyclerview:recyclerview:1.2.1")
    compileOnly("androidx.constraintlayout:constraintlayout:2.0.4")
    compileOnly("androidx.fragment:fragment:1.3.6")
    compileOnly("com.github.bumptech.glide:glide:4.12.0")
    compileOnly("com.squareup.okhttp3:okhttp:4.9.1")
    compileOnly("com.squareup.okio:okio:2.10.0")
    compileOnly("ru.noties.markwon:markwon:3.0.1")
    implementation("com.github.zjupure:webpdecoder:2.0.4.12.0") {
        exclude(group = "com.github.bumptech.glide", module = "glide")
    }
    implementation("com.caverock:androidsvg-aar:1.4")
}
