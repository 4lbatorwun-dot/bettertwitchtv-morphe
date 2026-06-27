package app.morphe.patches.twitch

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.twitch.shared.compat.AppCompatibilities
import com.android.tools.smali.dexlib2.AccessFlags

@Suppress("unused")
val twitchBTTVPatch = bytecodePatch(
    name = "BetterTTV Integration",
    description = "Integrate BetterTTV (BTTV), FrankerFaceZ (FFZ), and 7TV emotes into Twitch chat.",
    default = true
) {
    compatibleWith(AppCompatibilities.TWITCH)
    extendWith("extensions/bettertwitchtv.mpe")

    execute {
        // 1. Hook TwitchApplication.onCreate to set context
        TwitchApplicationOnCreateFingerprint.method.apply {
            addInstructions(
                0,
                """
                    invoke-static {p0}, Lbttv/Data;->setContext(Landroid/content/Context;)V
                """.trimIndent()
            )
        }

        // 2. Hook StreamMetadataModelProvider$dataObserver$1 to track channel
        StreamMetadataModelProviderFingerprint.method.apply {
            addInstructions(
                0,
                """
                    invoke-static {p1}, Lbttv/Data;->setCurrentBroadcasterId(Ltv/twitch/android/models/streams/StreamModel;)V
                """.trimIndent()
            )
        }

        // 3. Make ChatMessageInfo fields public and mutable
        val chatMessageInfoClass = mutableClassDefBy("Ltv/twitch/chat/library/model/ChatMessageInfo;")
        chatMessageInfoClass.fields.first { it.name == "messageType" }.apply {
            accessFlags = (accessFlags or AccessFlags.PUBLIC.value) and AccessFlags.FINAL.value.inv() and AccessFlags.PRIVATE.value.inv() and AccessFlags.PROTECTED.value.inv()
        }
        chatMessageInfoClass.fields.first { it.name == "tokens" }.apply {
            accessFlags = (accessFlags or AccessFlags.PUBLIC.value) and AccessFlags.FINAL.value.inv() and AccessFlags.PRIVATE.value.inv() and AccessFlags.PROTECTED.value.inv()
        }

        // 4. Make ChatMessageDelegate chatMessage field public and mutable
        val chatMessageDelegateClass = mutableClassDefBy("Ltv/twitch/android/shared/chat/ChatMessageDelegate;")
        chatMessageDelegateClass.fields.first { it.name == "chatMessage" }.apply {
            accessFlags = (accessFlags or AccessFlags.PUBLIC.value) and AccessFlags.FINAL.value.inv() and AccessFlags.PRIVATE.value.inv() and AccessFlags.PROTECTED.value.inv()
        }

        // 5. Hook ChatMessageFactory to tokenize emotes and block blacklisted messages
        ChatMessageFactoryFingerprint.method.apply {
            val parameterRegister = if (AccessFlags.STATIC.isSet(accessFlags)) {
                val index = parameters.map { it.type }.indexOf("Ltv/twitch/android/shared/chat/pub/messages/ui/ChatMessageInterface;")
                "p$index"
            } else {
                val index = parameters.map { it.type }.indexOf("Ltv/twitch/android/shared/chat/pub/messages/ui/ChatMessageInterface;")
                "p${index + 1}"
            }

            addInstructions(
                0,
                """
                    invoke-static { $parameterRegister }, Lbttv/Tokenizer;->retokenizeLiveChatMessage(Ltv/twitch/android/shared/chat/pub/messages/ui/ChatMessageInterface;)V
                    invoke-static { $parameterRegister }, Lbttv/api/Blacklist;->isBlocked(Ltv/twitch/android/shared/chat/pub/messages/ui/ChatMessageInterface;)Z
                    move-result v0
                    if-eqz v0, :skip_message
                    const/4 v0, 0x0
                    return-object v0
                    :skip_message
                """.trimIndent()
            )
        }

        // 6. Hook SubNoticeChatMessageFactory to tokenize emotes
        SubNoticeChatMessageFactoryFingerprint.method.apply {
            val parameterRegister = if (AccessFlags.STATIC.isSet(accessFlags)) {
                val index = parameters.map { it.type }.indexOf("Ltv/twitch/chat/library/model/ChatMessageInfo;")
                "p$index"
            } else {
                val index = parameters.map { it.type }.indexOf("Ltv/twitch/chat/library/model/ChatMessageInfo;")
                "p${index + 1}"
            }

            addInstructions(
                0,
                """
                    invoke-static { $parameterRegister }, Lbttv/Tokenizer;->retokenizeLiveChatMessage(Ltv/twitch/chat/library/model/ChatMessageInfo;)V
                """.trimIndent()
            )
        }

        // 7. Hook EmoteUrlUtil.generateEmoteUrl to intercept custom emote CDN URLs
        EmoteUrlUtilFingerprint.method.apply {
            val idRegister = if (AccessFlags.STATIC.isSet(accessFlags)) "p0" else "p1"
            addInstructions(
                0,
                """
                    invoke-static { $idRegister }, Lbttv/emote/EmoteUrlUtil;->getEmoteUrl(Ljava/lang/String;)Ljava/lang/String;
                    move-result-object v0
                    if-eqz v0, :bttv_url
                    return-object v0
                    :bttv_url
                """.trimIndent()
            )
        }

        // 8. Hook EmoteApiImpl to intercept clicks on custom emotes and show card details
        EmoteApiImplFingerprint.method.apply {
            val idRegister = if (AccessFlags.STATIC.isSet(accessFlags)) "p0" else "p1"
            addInstructions(
                0,
                """
                    invoke-static { $idRegister }, Lbttv/emote/EmoteCardUtil;->getEmoteResponseOrNull(Ljava/lang/String;)Lio/reactivex/Single;
                    move-result-object v0
                    if-eqz v0, :bttv_response
                    return-object v0
                    :bttv_response
                """.trimIndent()
            )
        }
    }
}
