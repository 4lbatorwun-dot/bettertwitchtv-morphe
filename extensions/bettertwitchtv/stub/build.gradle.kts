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
