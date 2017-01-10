package com.madpixels.tgadmintools.helper;

import org.drinkless.td.libcore.telegram.TdApi;

/**
 * Created by Snake on 05.01.2017.
 */

public class SendMessageHelper {

    public static void sendMessageItalic(long chatId, String text){
        TdApi.InputMessageText msg = new TdApi.InputMessageText();

        msg.text = text;

        msg.entities = new TdApi.MessageEntity[1];
        msg.entities[0] = new TdApi.MessageEntityItalic(0, text.length());

        TdApi.TLFunction f = new TdApi.SendMessage(chatId, 0, false, true, null, msg);
        TgH.send(f);
    }
}
