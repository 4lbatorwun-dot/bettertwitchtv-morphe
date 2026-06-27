package app.morphe.patches.twitch.shared.compat

import app.morphe.patcher.patch.ApkFileType
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility

internal object AppCompatibilities {
    val TWITCH = Compatibility(
        name = "Twitch",
        packageName = "tv.twitch.android.app",
        apkFileType = ApkFileType.APK,
        appIconColor = 0x9146FF,
        targets = listOf(
            AppTarget(version = null)
        )
    )
}
