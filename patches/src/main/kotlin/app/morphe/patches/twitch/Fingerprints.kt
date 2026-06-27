package app.morphe.patches.twitch

import app.morphe.patcher.Fingerprint
import com.android.tools.smali.dexlib2.AccessFlags

object TwitchApplicationOnCreateFingerprint : Fingerprint(
    definingClass = "Ltv/twitch/android/app/consumer/TwitchApplication;",
    name = "onCreate",
    returnType = "V",
    parameters = emptyList()
)

object StreamMetadataModelProviderFingerprint : Fingerprint(
    name = "invoke",
    returnType = "Ltv/twitch/android/feature/theatre/metadata/VideoMetadataModel;",
    parameters = listOf("Ltv/twitch/android/models/streams/StreamModel;"),
    custom = { _, classDef ->
        classDef.type.contains("StreamMetadataModelProvider") && classDef.type.contains("dataObserver")
    }
)


object ChatMessageFactoryFingerprint : Fingerprint(
    custom = { method, classDef ->
        classDef.type.endsWith("/ChatMessageFactory;") &&
        method.parameters.any { it.type == "Ltv/twitch/android/shared/chat/pub/messages/ui/ChatMessageInterface;" }
    }
)

object SubNoticeChatMessageFactoryFingerprint : Fingerprint(
    custom = { method, classDef ->
        classDef.type.endsWith("/SubNoticeChatMessageFactory;") &&
        method.parameters.any { it.type == "Ltv/twitch/chat/library/model/ChatMessageInfo;" }
    }
)

object EmoteUrlUtilFingerprint : Fingerprint(
    returnType = "Ljava/lang/String;",
    custom = { method, classDef ->
        classDef.type.endsWith("/EmoteUrlUtil;") &&
        method.name.startsWith("generateEmoteUrl")
    }
)

object EmoteApiImplFingerprint : Fingerprint(
    returnType = "Lio/reactivex/Single;",
    parameters = listOf("Ljava/lang/String;"),
    custom = { method, classDef ->
        classDef.type.endsWith("/EmoteApiImpl;")
    }
)
