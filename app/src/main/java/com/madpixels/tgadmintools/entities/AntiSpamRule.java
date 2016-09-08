package com.madpixels.tgadmintools.entities;

/**
 * Created by Snake on 09.03.2016.
 */
public class AntiSpamRule {
    public int id;
    public long chat_id;

    public boolean isRemoveLinks = false;
    public boolean isRemoveStickers = false;

    public boolean isBanForLinks = false;
    public int mAllowLinksCount = 1;
    /** текст предупреждения перед попыткой бана если mAllowLinksCount>0  */
    public String mWarnTextLink = "", mWarnTextBlackWords="";
    public long mLinkBanAgeMsec = 0;
    public boolean isLinkReturnOnUnban = false;

    public boolean isBanForStickers = false;
    public int mAllowStickersCount = 1;
    /** текст предупреждения перед попыткой если mAllowStickersCount>0  */
    public String mWarnTextStickers = "";
    public long mStickerBanAgeMsec = 0;
    public boolean isStickerReturnOnUnban = false;
    public boolean isWarnBeforeStickersBan=true, isWarnBeforeLinksBan=true;

    // public boolean isImagesFloodEnabled = false;
    public int mImagesFloodLimit = 10;
    public long imagesBanAge = 0;
    public boolean isImagesReturnOnUnban = false;
    public boolean isBanForBlackWords = false, isBlackWordReturnOnBanExp=false, isDelMsgBlackWords=false;
    public long mBlackWordBanAgeMsec = 0;
    public boolean isRemoveLeaveMessage=false, isRemoveJoinMessage=false;

    public int option_link_banage_val=0, option_links_banage_mp=0,
            option_sticks_banage_val=0, option_sticks_banage_mp=0,
            option_blackword_banage_val=0, option_blackword_banage_mp=0;
    public boolean isAlertAboutWordBan=true;
    public int banWordsFloodLimit = 1;
    public boolean isWarnBeforeBanForWord=true;


    public String getLinksWarnText() {
        if(!mWarnTextLink.isEmpty())
            return mWarnTextLink;
        return null;
    }

    public String getStickersWarnText() {
        if(!mWarnTextStickers.isEmpty())
            return mWarnTextStickers;
        return null;
    }


    public String getBanWordsWarnText(){
        if(!mWarnTextBlackWords.isEmpty())
            return mWarnTextBlackWords;
        return null;
    }
}
