package app.morphe.patches.twitch.shared.compat

import app.morphe.patcher.patch.Compatibility

internal object AppCompatibilities {
    val TWITCH = Compatibility(
        name = "Twitch",
        packageName = "tv.twitch.android.app",
        appIconColor = 0x9146FF,
    )
}
