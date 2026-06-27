plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "bttv.stub"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
    }
}

dependencies {
    compileOnly("io.reactivex.rxjava2:rxjava:2.2.21")
    compileOnly("javax.inject:javax.inject:1")
    compileOnly("androidx.recyclerview:recyclerview:1.2.1")
    compileOnly("androidx.constraintlayout:constraintlayout:2.0.4")
    compileOnly("androidx.fragment:fragment:1.3.6")
    compileOnly("androidx.appcompat:appcompat:1.2.0")
}

