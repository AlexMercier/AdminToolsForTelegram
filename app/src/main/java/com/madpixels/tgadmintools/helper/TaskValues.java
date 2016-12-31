package com.madpixels.tgadmintools.helper;

import android.support.annotation.StringRes;

import com.madpixels.tgadmintools.R;
import com.madpixels.tgadmintools.entities.ChatTask;
import com.madpixels.tgadmintools.utils.LogUtil;

/**
 * Created by Snake on 27.12.2016.
 */

public class TaskValues {

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
        }

        throw new RuntimeException("String not found " + action);
    }

    @StringRes
    public static Integer getTitleForAddTask(ChatTask.TYPE pType) {
        switch (pType) {
            case STICKERS:
                return R.string.title_task_add_stickers;
            case VOICE:
                return R.string.title_task_add_voices;
            case LINKS:
                return R.string.title_task_add_link;
            case GAME:
                return R.string.title_task_add_games;
            case IMAGES:
                return R.string.title_task_add_photos;
            case DOCS:
                return R.string.title_task_add_documents;
            case GIF:
                return R.string.title_task_add_gif;
            case AUDIO:
                return R.string.title_task_audio;
            case VIDEO:
                return R.string.title_task_video;
        }

        throw new RuntimeException("String not found " + pType);
    }

    @StringRes
    public static int getBanReason(ChatTask.TYPE mType) {
        switch (mType) {
            case BANWORDS:
                return (R.string.logAction_banForBlackWord);
            case STICKERS:
                return (R.string.logAction_banForSticker);
            case VOICE:
                return (R.string.logAction_banForVoice);
            case LINKS:
                return (R.string.logAction_banForLink);
            case GAME:
                return (R.string.logAction_banForGame);
            case IMAGES:
                return R.string.logAction_banForPhotos;
            case DOCS:
                return R.string.logAction_banForDocs;
            case GIF:
                return R.string.logAction_banForGifs;
            case AUDIO:
                return R.string.logAction_banForAudio;
            case VIDEO:
                return R.string.logAction_banForVideo;
        }
        throw new RuntimeException("String not found " + mType);
    }

    @StringRes
    public static int getWarnText(ChatTask.TYPE mType, int textType) {
        switch (mType) {
            case FLOOD:
                return R.string.warntext_flood_default_last;
            case STICKERS:
                return textType == 1 ? R.string.warntext_stickers_default_first : R.string.warntext_stickers_default_last;

            case LINKS:
                return textType == 1 ? R.string.warntext_links_default_firts : R.string.warntext_links_default_last;

            case VOICE:
                return textType == 1 ? R.string.warntext_voice_default_first : R.string.warntext_voice_default_last;

            case BANWORDS:
                return textType == 1 ? R.string.warntext_banword_first : R.string.warntext_banword_last;

            case GAME:
                return textType == 1 ? R.string.warntext_bangame_first : R.string.warntext_bangame_last;
            case IMAGES:
                return textType == 1 ? R.string.warntext_banphoto_first : R.string.warntext_banphoto_last;
            case DOCS:
                return textType == 1 ? R.string.warntext_bandocs_first : R.string.warntext_bandocs_last;
            case GIF:
                return textType == 1 ? R.string.warntext_bangifs_first : R.string.warntext_bangifs_last;
            case AUDIO:
                return textType == 1 ? R.string.warntext_banAudio_first : R.string.warntext_banAudio_last;
            case VIDEO:
                return textType == 1 ? R.string.warntext_banVideo_first : R.string.warntext_banVideo_last;

        }
        throw new RuntimeException("String not found " + mType + " " + textType);
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
