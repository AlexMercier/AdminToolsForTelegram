package com.madpixels.tgadmintools.helper;

import android.support.annotation.StringRes;

import com.madpixels.tgadmintools.App;
import com.madpixels.tgadmintools.BuildConfig;
import com.madpixels.tgadmintools.R;
import com.madpixels.tgadmintools.entities.ChatTask;
import com.madpixels.tgadmintools.utils.LogUtil;

/**
 * Created by Snake on 27.12.2016.
 */

public class TaskValues {

    //static SparseArray<String> strings = new SparseArray<>();

    public static String getString(@StringRes int resID) {
        return getString(resID, "");
    }

    public static String getString(@StringRes int resID, String defaultVal) {
        if (resID == 0)
            return defaultVal;
        try {
            return App.getContext().getString(resID);
        } catch (Exception e) {
            return defaultVal;
        }

    }

    @StringRes
    public static Integer getTitleLogAction(LogUtil.Action action) {
        switch (action) {
            /* Ban for: */
            case BanForVoice:
                return R.string.logAction_banForVoice;
            case BanForGame:
                return R.string.logAction_banForGame;
            case BanManually:
                return R.string.logAction_banUser;
            case BanForBlackWord:
                return R.string.logAction_banForBlackWord;
            case BanForFlood:
                return R.string.logAction_banForFlood;
            case BanForSticker:
                return R.string.logAction_banForSticker;
            case BanForLink:
                return R.string.logAction_banForLink;
            case BanForImages:
                return R.string.logAction_banForPhotos;
            case BanForDocs:
                return R.string.logAction_banForDocs;
            case BanForGif:
                return R.string.logAction_banForGifs;
            case BanForAudio:
                return R.string.logAction_banForAudio;
            case BanForVideo:
                return R.string.logAction_banForVideo;

            /* Remove message: */
            case RemoveVoice:
                return R.string.logAction_removedVoice;
            case RemoveFlood:
                return R.string.logAction_removedFloodMessage;
            case DeleteMsgBlackWord:
                return R.string.logAction_removedBlackWordMessage;
            case RemoveSticker:
                return R.string.logAction_removedSticker;
            case RemoveGame:
                return R.string.logAction_removedGameMessage;
            case RemoveDocs:
                return R.string.logAction_removedDocs;
            case RemoveImage:
                return R.string.logAction_removedPhoto;
            case RemoveLink:
                return R.string.logAction_removedLink;
            case RemoveGif:
                return R.string.logAction_removedGif;
            case RemoveAudio:
                return R.string.logAction_removedAudio;
            case RemoveVideo:
                return R.string.logAction_removedVideo;
            case RemoveMuted:
                return R.string.logAction_removedMutedMessage;
            case RemoveJoinMessage:
                return R.string.logAction_removedJoinMessage;
            case RemoveLeaveMessage:
                return R.string.logAction_removeLeaveMessage;

            /* Flood warning: */
            case DocsFloodWarn:
                return R.string.logAction_DocsFloodWarn;
            case ImagesFloodWarn:
                return R.string.logAction_PhotosFloodWarn;
            case LinksFloodAttempt:
                return R.string.logAction_linksFloodWarn;
            case BanWordsFloodWarn:
                return R.string.logAction_banwordsFloodWarn;
            case StickersFloodWarn:
                return R.string.logAction_stickersFloodWarn;
            case GifsFloodWarn:
                return R.string.logAction_gifsFloodWarn;
            case AudioFloodWarn:
                return R.string.logAction_audioFloodWarn;
            case VideoFloodWarn:
                return R.string.logAction_videoFloodWarn;
            case GameFloodWarn:
                return R.string.logAction_gameFloodWarn;
            case VoiceFloodWarn:
                return R.string.logAction_voiceFloodWarn;

            /*Other */
            case AutoUnbanByAdminInvite:
                return R.string.logAction_unbanByAdminInvite;
            case AutoUnban:
                return R.string.logAction_autoUnban;
            case AutoReturnToChat:
                return R.string.logAction_return_to_chat;
            case AutoKickFromGroup:
                return R.string.logAction_autokick;
            case CommandExecError:
                return R.string.logAction_execCommandError;
            case BOT_ERROR:
                return R.string.logAction_sendViaBotError;
            case CMDTitleChanged:
                return R.string.logAction_userTitleChanged;
            case USER_MUTED:
                return R.string.logAction_userWasMuted;
            case USER_UNMUTED:
                return R.string.logAction_userWasUnMuted;
        }

        if (BuildConfig.DEBUG && false)
            throw new RuntimeException("String not found " + action);
        else
            return 0;
    }

    public static String getTitleForAddTask(ChatTask.TYPE pType) {
        int strRes = 0;
        switch (pType) {
            case FLOOD:
                strRes = R.string.title_flood_control;
                break;
            case STICKERS:
                strRes = R.string.title_task_add_stickers;
                break;
            case VOICE:
                strRes = R.string.title_task_add_voices;
                break;
            case LINKS:
                strRes = R.string.title_task_add_link;
                break;
            case GAME:
                strRes = R.string.title_task_add_games;
                break;
            case IMAGES:
                strRes = R.string.title_task_add_photos;
                break;
            case DOCS:
                strRes = R.string.title_task_add_documents;
                break;
            case GIF:
                strRes = R.string.title_task_add_gif;
                break;
            case AUDIO:
                strRes = R.string.title_task_audio;
                break;
            case VIDEO:
                strRes = R.string.title_task_video;
                break;
        }

        if (strRes == 0) {
            if (BuildConfig.DEBUG)
                throw new RuntimeException("String not found " + pType);
        }
        return getString(strRes, pType.name());

    }

    public static String getBanReason(ChatTask.TYPE pType) {
        int strRes = 0;
        switch (pType) {
            case BANWORDS:
                strRes = (R.string.logAction_banForBlackWord);
                break;
            case STICKERS:
                strRes = (R.string.logAction_banForSticker);
                break;
            case VOICE:
                strRes = (R.string.logAction_banForVoice);
                break;
            case LINKS:
                strRes = (R.string.logAction_banForLink);
                break;
            case GAME:
                strRes = (R.string.logAction_banForGame);
                break;
            case IMAGES:
                strRes = R.string.logAction_banForPhotos;
                break;
            case DOCS:
                strRes = R.string.logAction_banForDocs;
                break;
            case GIF:
                strRes = R.string.logAction_banForGifs;
                break;
            case AUDIO:
                strRes = R.string.logAction_banForAudio;
                break;
            case VIDEO:
                strRes = R.string.logAction_banForVideo;
                break;
            case FLOOD:
                strRes = R.string.logAction_banForFlood;
                break;
        }

        if (strRes == 0) {
            if (BuildConfig.DEBUG)
                throw new RuntimeException("String not found " + pType);
        }
        return getString(strRes, pType.name());
    }


    public static String getWarnText(ChatTask.TYPE pType, int textType) {
        int strRes = 0;

        switch (pType) {
            case FLOOD:
                strRes = R.string.warntext_flood_default_last;
                break;
            case STICKERS:
                strRes = textType == 1 ? R.string.warntext_stickers_default_first : R.string.warntext_stickers_default_last;
                break;

            case LINKS:
                strRes = textType == 1 ? R.string.warntext_links_default_firts : R.string.warntext_links_default_last;
                break;

            case VOICE:
                strRes = textType == 1 ? R.string.warntext_voice_default_first : R.string.warntext_voice_default_last;
                break;

            case BANWORDS:
                strRes = textType == 1 ? R.string.warntext_banword_first : R.string.warntext_banword_last;
                break;

            case GAME:
                strRes = textType == 1 ? R.string.warntext_bangame_first : R.string.warntext_bangame_last;
                break;
            case IMAGES:
                strRes = textType == 1 ? R.string.warntext_banphoto_first : R.string.warntext_banphoto_last;
                break;
            case DOCS:
                strRes = textType == 1 ? R.string.warntext_bandocs_first : R.string.warntext_bandocs_last;
                break;
            case GIF:
                strRes = textType == 1 ? R.string.warntext_bangifs_first : R.string.warntext_bangifs_last;
                break;
            case AUDIO:
                strRes = textType == 1 ? R.string.warntext_banAudio_first : R.string.warntext_banAudio_last;
                break;
            case VIDEO:
                strRes = textType == 1 ? R.string.warntext_banVideo_first : R.string.warntext_banVideo_last;
                break;

        }

        if (strRes == 0) {
            if (BuildConfig.DEBUG)
                throw new RuntimeException("String not found " + pType + " " + textType);
        }
        return getString(strRes, pType.name());


    }

    public static LogUtil.Action getWarningAction(ChatTask.TYPE type) {
        switch (type) {
            //NOTE add new type here when new type implemented
            case STICKERS:
                return LogUtil.Action.StickersFloodWarn;

            case VOICE:
                return LogUtil.Action.VoiceFloodWarn;

            case GAME:
                return LogUtil.Action.GameFloodWarn;

            case IMAGES:
                return LogUtil.Action.ImagesFloodWarn;

            case BANWORDS:
                return LogUtil.Action.BanWordsFloodWarn;

            case DOCS:
                return LogUtil.Action.DocsFloodWarn;
            case GIF:
                return LogUtil.Action.GifsFloodWarn;
            case AUDIO:
                return LogUtil.Action.AudioFloodWarn;
            case VIDEO:
                return LogUtil.Action.VideoFloodWarn;

        }

        throw new RuntimeException("String not found " + type);
    }
}
