package org.drinkless.td.libcore.telegram;

import java.util.Arrays;

public class TdApi {
    private static final char[] HEX_CHARACTERS = "0123456789ABCDEF".toCharArray();

    public abstract static class TLObject {

        public abstract int getConstructor();

        public String toString() {
            StringBuilder s = new StringBuilder();
            toStringBuilder(0, s);
            return s.toString();
        }

        StringBuilder appendLine(StringBuilder s, int shift) {
            s.append('\n');
            for (int i = 0; i < shift; i++) {
                s.append(' ');
            }
            return s;
        }

        abstract void toStringBuilder(int shift, StringBuilder s);
    }

    public abstract static class TLFunction extends TLObject {
    }

    public static class AccountTtl extends TLObject {
        public int days;

        public AccountTtl() {
        }

        public AccountTtl(int days) {
            this.days = days;
        }

        public static final int CONSTRUCTOR = 813220011;

        @Override
        public int getConstructor() {
            return 813220011;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("AccountTtl").append(" {");
            shift += 2;
            appendLine(s, shift).append("days = ").append(days);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class Animation extends TLObject {
        public int width;
        public int height;
        public String fileName;
        public String mimeType;
        public PhotoSize thumb;
        public File animation;

        public Animation() {
        }

        public Animation(int width, int height, String fileName, String mimeType, PhotoSize thumb, File animation) {
            this.width = width;
            this.height = height;
            this.fileName = fileName;
            this.mimeType = mimeType;
            this.thumb = thumb;
            this.animation = animation;
        }

        public static final int CONSTRUCTOR = -278960527;

        @Override
        public int getConstructor() {
            return -278960527;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("Animation").append(" {");
            shift += 2;
            appendLine(s, shift).append("width = ").append(width);
            appendLine(s, shift).append("height = ").append(height);
            appendLine(s, shift).append("fileName = ").append(fileName);
            appendLine(s, shift).append("mimeType = ").append(mimeType);
            appendLine(s, shift).append("thumb = "); thumb.toStringBuilder(shift, s);
            appendLine(s, shift).append("animation = "); animation.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class Animations extends TLObject {
        public Animation[] animations;

        public Animations() {
        }

        public Animations(Animation[] animations) {
            this.animations = animations;
        }

        public static final int CONSTRUCTOR = 344216945;

        @Override
        public int getConstructor() {
            return 344216945;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("Animations").append(" {");
            shift += 2;
            appendLine(s, shift).append("animations = ").append(Arrays.toString(animations));
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class Audio extends TLObject {
        public int duration;
        public String title;
        public String performer;
        public String fileName;
        public String mimeType;
        public PhotoSize albumCoverThumb;
        public File audio;

        public Audio() {
        }

        public Audio(int duration, String title, String performer, String fileName, String mimeType, PhotoSize albumCoverThumb, File audio) {
            this.duration = duration;
            this.title = title;
            this.performer = performer;
            this.fileName = fileName;
            this.mimeType = mimeType;
            this.albumCoverThumb = albumCoverThumb;
            this.audio = audio;
        }

        public static final int CONSTRUCTOR = -794337070;

        @Override
        public int getConstructor() {
            return -794337070;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("Audio").append(" {");
            shift += 2;
            appendLine(s, shift).append("duration = ").append(duration);
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("performer = ").append(performer);
            appendLine(s, shift).append("fileName = ").append(fileName);
            appendLine(s, shift).append("mimeType = ").append(mimeType);
            appendLine(s, shift).append("albumCoverThumb = "); albumCoverThumb.toStringBuilder(shift, s);
            appendLine(s, shift).append("audio = "); audio.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public abstract static class AuthCodeType extends TLObject {
    }

    public static class AuthCodeTypeMessage extends AuthCodeType {
        public int length;

        public AuthCodeTypeMessage() {
        }

        public AuthCodeTypeMessage(int length) {
            this.length = length;
        }

        public static final int CONSTRUCTOR = 1692441707;

        @Override
        public int getConstructor() {
            return 1692441707;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("AuthCodeTypeMessage").append(" {");
            shift += 2;
            appendLine(s, shift).append("length = ").append(length);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class AuthCodeTypeSms extends AuthCodeType {
        public int length;

        public AuthCodeTypeSms() {
        }

        public AuthCodeTypeSms(int length) {
            this.length = length;
        }

        public static final int CONSTRUCTOR = -7919996;

        @Override
        public int getConstructor() {
            return -7919996;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("AuthCodeTypeSms").append(" {");
            shift += 2;
            appendLine(s, shift).append("length = ").append(length);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class AuthCodeTypeCall extends AuthCodeType {
        public int length;

        public AuthCodeTypeCall() {
        }

        public AuthCodeTypeCall(int length) {
            this.length = length;
        }

        public static final int CONSTRUCTOR = -1432894294;

        @Override
        public int getConstructor() {
            return -1432894294;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("AuthCodeTypeCall").append(" {");
            shift += 2;
            appendLine(s, shift).append("length = ").append(length);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class AuthCodeTypeFlashCall extends AuthCodeType {
        public String pattern;

        public AuthCodeTypeFlashCall() {
        }

        public AuthCodeTypeFlashCall(String pattern) {
            this.pattern = pattern;
        }

        public static final int CONSTRUCTOR = -1080364751;

        @Override
        public int getConstructor() {
            return -1080364751;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("AuthCodeTypeFlashCall").append(" {");
            shift += 2;
            appendLine(s, shift).append("pattern = ").append(pattern);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public abstract static class AuthState extends TLObject {
    }

    public static class AuthStateWaitPhoneNumber extends AuthState {

        public AuthStateWaitPhoneNumber() {
        }

        public static final int CONSTRUCTOR = 167878457;

        @Override
        public int getConstructor() {
            return 167878457;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("AuthStateWaitPhoneNumber").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class AuthStateWaitCode extends AuthState {
        public boolean isRegistered;
        public AuthCodeType codeType;
        public AuthCodeType nextCodeType;
        public int timeout;

        public AuthStateWaitCode() {
        }

        public AuthStateWaitCode(boolean isRegistered, AuthCodeType codeType, AuthCodeType nextCodeType, int timeout) {
            this.isRegistered = isRegistered;
            this.codeType = codeType;
            this.nextCodeType = nextCodeType;
            this.timeout = timeout;
        }

        public static final int CONSTRUCTOR = 2103299766;

        @Override
        public int getConstructor() {
            return 2103299766;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("AuthStateWaitCode").append(" {");
            shift += 2;
            appendLine(s, shift).append("isRegistered = ").append(isRegistered);
            appendLine(s, shift).append("codeType = "); codeType.toStringBuilder(shift, s);
            appendLine(s, shift).append("nextCodeType = "); nextCodeType.toStringBuilder(shift, s);
            appendLine(s, shift).append("timeout = ").append(timeout);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class AuthStateWaitPassword extends AuthState {
        public String hint;
        public boolean hasRecovery;
        public String emailUnconfirmedPattern;

        public AuthStateWaitPassword() {
        }

        public AuthStateWaitPassword(String hint, boolean hasRecovery, String emailUnconfirmedPattern) {
            this.hint = hint;
            this.hasRecovery = hasRecovery;
            this.emailUnconfirmedPattern = emailUnconfirmedPattern;
        }

        public static final int CONSTRUCTOR = -338450931;

        @Override
        public int getConstructor() {
            return -338450931;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("AuthStateWaitPassword").append(" {");
            shift += 2;
            appendLine(s, shift).append("hint = ").append(hint);
            appendLine(s, shift).append("hasRecovery = ").append(hasRecovery);
            appendLine(s, shift).append("emailUnconfirmedPattern = ").append(emailUnconfirmedPattern);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class AuthStateOk extends AuthState {

        public AuthStateOk() {
        }

        public static final int CONSTRUCTOR = 1222968966;

        @Override
        public int getConstructor() {
            return 1222968966;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("AuthStateOk").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class AuthStateLoggingOut extends AuthState {

        public AuthStateLoggingOut() {
        }

        public static final int CONSTRUCTOR = 965468001;

        @Override
        public int getConstructor() {
            return 965468001;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("AuthStateLoggingOut").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class Authorization extends TLObject {
        public long id;
        public boolean isCurrent;
        public int appId;
        public String appName;
        public String appVersion;
        public boolean isOfficialApp;
        public String deviceModel;
        public String platform;
        public String systemVersion;
        public int dateCreated;
        public int dateActive;
        public String ip;
        public String country;
        public String region;

        public Authorization() {
        }

        public Authorization(long id, boolean isCurrent, int appId, String appName, String appVersion, boolean isOfficialApp, String deviceModel, String platform, String systemVersion, int dateCreated, int dateActive, String ip, String country, String region) {
            this.id = id;
            this.isCurrent = isCurrent;
            this.appId = appId;
            this.appName = appName;
            this.appVersion = appVersion;
            this.isOfficialApp = isOfficialApp;
            this.deviceModel = deviceModel;
            this.platform = platform;
            this.systemVersion = systemVersion;
            this.dateCreated = dateCreated;
            this.dateActive = dateActive;
            this.ip = ip;
            this.country = country;
            this.region = region;
        }

        public static final int CONSTRUCTOR = -844705341;

        @Override
        public int getConstructor() {
            return -844705341;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("Authorization").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("isCurrent = ").append(isCurrent);
            appendLine(s, shift).append("appId = ").append(appId);
            appendLine(s, shift).append("appName = ").append(appName);
            appendLine(s, shift).append("appVersion = ").append(appVersion);
            appendLine(s, shift).append("isOfficialApp = ").append(isOfficialApp);
            appendLine(s, shift).append("deviceModel = ").append(deviceModel);
            appendLine(s, shift).append("platform = ").append(platform);
            appendLine(s, shift).append("systemVersion = ").append(systemVersion);
            appendLine(s, shift).append("dateCreated = ").append(dateCreated);
            appendLine(s, shift).append("dateActive = ").append(dateActive);
            appendLine(s, shift).append("ip = ").append(ip);
            appendLine(s, shift).append("country = ").append(country);
            appendLine(s, shift).append("region = ").append(region);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class Authorizations extends TLObject {
        public Authorization[] authorizations;

        public Authorizations() {
        }

        public Authorizations(Authorization[] authorizations) {
            this.authorizations = authorizations;
        }

        public static final int CONSTRUCTOR = -1499890846;

        @Override
        public int getConstructor() {
            return -1499890846;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("Authorizations").append(" {");
            shift += 2;
            appendLine(s, shift).append("authorizations = ").append(Arrays.toString(authorizations));
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class BotCommand extends TLObject {
        public String command;
        public String description;

        public BotCommand() {
        }

        public BotCommand(String command, String description) {
            this.command = command;
            this.description = description;
        }

        public static final int CONSTRUCTOR = -1032140601;

        @Override
        public int getConstructor() {
            return -1032140601;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("BotCommand").append(" {");
            shift += 2;
            appendLine(s, shift).append("command = ").append(command);
            appendLine(s, shift).append("description = ").append(description);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class BotInfo extends TLObject {
        public String description;
        public BotCommand[] commands;

        public BotInfo() {
        }

        public BotInfo(String description, BotCommand[] commands) {
            this.description = description;
            this.commands = commands;
        }

        public static final int CONSTRUCTOR = 1296528907;

        @Override
        public int getConstructor() {
            return 1296528907;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("BotInfo").append(" {");
            shift += 2;
            appendLine(s, shift).append("description = ").append(description);
            appendLine(s, shift).append("commands = ").append(Arrays.toString(commands));
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class CallbackQueryAnswer extends TLObject {
        public String text;
        public boolean showAlert;
        public String url;

        public CallbackQueryAnswer() {
        }

        public CallbackQueryAnswer(String text, boolean showAlert, String url) {
            this.text = text;
            this.showAlert = showAlert;
            this.url = url;
        }

        public static final int CONSTRUCTOR = 360867933;

        @Override
        public int getConstructor() {
            return 360867933;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("CallbackQueryAnswer").append(" {");
            shift += 2;
            appendLine(s, shift).append("text = ").append(text);
            appendLine(s, shift).append("showAlert = ").append(showAlert);
            appendLine(s, shift).append("url = ").append(url);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public abstract static class CallbackQueryPayload extends TLObject {
    }

    public static class CallbackQueryData extends CallbackQueryPayload {
        public byte[] data;

        public CallbackQueryData() {
        }

        public CallbackQueryData(byte[] data) {
            this.data = data;
        }

        public static final int CONSTRUCTOR = 1624256266;

        @Override
        public int getConstructor() {
            return 1624256266;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("CallbackQueryData").append(" {");
            shift += 2;
            appendLine(s, shift).append("data = ").append("bytes { "); { for (byte k : data) { int b = (int)k & 255; s.append(HEX_CHARACTERS[b >> 4]).append(HEX_CHARACTERS[b & 15]).append(' '); } } s.append('}');
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class CallbackQueryGame extends CallbackQueryPayload {
        public String gameShortName;

        public CallbackQueryGame() {
        }

        public CallbackQueryGame(String gameShortName) {
            this.gameShortName = gameShortName;
        }

        public static final int CONSTRUCTOR = -1634706916;

        @Override
        public int getConstructor() {
            return -1634706916;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("CallbackQueryGame").append(" {");
            shift += 2;
            appendLine(s, shift).append("gameShortName = ").append(gameShortName);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class Channel extends TLObject {
        public int id;
        public String username;
        public int date;
        public ChatMemberStatus status;
        public boolean anyoneCanInvite;
        public boolean signMessages;
        public boolean isSupergroup;
        public boolean isVerified;
        public String restrictionReason;

        public Channel() {
        }

        public Channel(int id, String username, int date, ChatMemberStatus status, boolean anyoneCanInvite, boolean signMessages, boolean isSupergroup, boolean isVerified, String restrictionReason) {
            this.id = id;
            this.username = username;
            this.date = date;
            this.status = status;
            this.anyoneCanInvite = anyoneCanInvite;
            this.signMessages = signMessages;
            this.isSupergroup = isSupergroup;
            this.isVerified = isVerified;
            this.restrictionReason = restrictionReason;
        }

        public static final int CONSTRUCTOR = -1532245212;

        @Override
        public int getConstructor() {
            return -1532245212;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("Channel").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("username = ").append(username);
            appendLine(s, shift).append("date = ").append(date);
            appendLine(s, shift).append("status = "); status.toStringBuilder(shift, s);
            appendLine(s, shift).append("anyoneCanInvite = ").append(anyoneCanInvite);
            appendLine(s, shift).append("signMessages = ").append(signMessages);
            appendLine(s, shift).append("isSupergroup = ").append(isSupergroup);
            appendLine(s, shift).append("isVerified = ").append(isVerified);
            appendLine(s, shift).append("restrictionReason = ").append(restrictionReason);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ChannelFull extends TLObject {
        public Channel channel;
        public String about;
        public int memberCount;
        public int administratorCount;
        public int kickedCount;
        public boolean canGetMembers;
        public boolean canSetUsername;
        public String inviteLink;
        public int pinnedMessageId;
        public int migratedFromGroupId;
        public int migratedFromMaxMessageId;

        public ChannelFull() {
        }

        public ChannelFull(Channel channel, String about, int memberCount, int administratorCount, int kickedCount, boolean canGetMembers, boolean canSetUsername, String inviteLink, int pinnedMessageId, int migratedFromGroupId, int migratedFromMaxMessageId) {
            this.channel = channel;
            this.about = about;
            this.memberCount = memberCount;
            this.administratorCount = administratorCount;
            this.kickedCount = kickedCount;
            this.canGetMembers = canGetMembers;
            this.canSetUsername = canSetUsername;
            this.inviteLink = inviteLink;
            this.pinnedMessageId = pinnedMessageId;
            this.migratedFromGroupId = migratedFromGroupId;
            this.migratedFromMaxMessageId = migratedFromMaxMessageId;
        }

        public static final int CONSTRUCTOR = -1962673954;

        @Override
        public int getConstructor() {
            return -1962673954;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChannelFull").append(" {");
            shift += 2;
            appendLine(s, shift).append("channel = "); channel.toStringBuilder(shift, s);
            appendLine(s, shift).append("about = ").append(about);
            appendLine(s, shift).append("memberCount = ").append(memberCount);
            appendLine(s, shift).append("administratorCount = ").append(administratorCount);
            appendLine(s, shift).append("kickedCount = ").append(kickedCount);
            appendLine(s, shift).append("canGetMembers = ").append(canGetMembers);
            appendLine(s, shift).append("canSetUsername = ").append(canSetUsername);
            appendLine(s, shift).append("inviteLink = ").append(inviteLink);
            appendLine(s, shift).append("pinnedMessageId = ").append(pinnedMessageId);
            appendLine(s, shift).append("migratedFromGroupId = ").append(migratedFromGroupId);
            appendLine(s, shift).append("migratedFromMaxMessageId = ").append(migratedFromMaxMessageId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public abstract static class ChannelMembersFilter extends TLObject {
    }

    public static class ChannelMembersRecent extends ChannelMembersFilter {

        public ChannelMembersRecent() {
        }

        public static final int CONSTRUCTOR = -1275194555;

        @Override
        public int getConstructor() {
            return -1275194555;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChannelMembersRecent").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ChannelMembersAdministrators extends ChannelMembersFilter {

        public ChannelMembersAdministrators() {
        }

        public static final int CONSTRUCTOR = 791495882;

        @Override
        public int getConstructor() {
            return 791495882;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChannelMembersAdministrators").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ChannelMembersKicked extends ChannelMembersFilter {

        public ChannelMembersKicked() {
        }

        public static final int CONSTRUCTOR = -1100658;

        @Override
        public int getConstructor() {
            return -1100658;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChannelMembersKicked").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ChannelMembersBots extends ChannelMembersFilter {

        public ChannelMembersBots() {
        }

        public static final int CONSTRUCTOR = 1290076627;

        @Override
        public int getConstructor() {
            return 1290076627;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChannelMembersBots").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class Chat extends TLObject {
        public long id;
        public String title;
        public ChatPhoto photo;
        public Message topMessage;
        public long order;
        public int unreadCount;
        public int lastReadInboxMessageId;
        public int lastReadOutboxMessageId;
        public NotificationSettings notificationSettings;
        public int replyMarkupMessageId;
        public DraftMessage draftMessage;
        public ChatInfo type;

        public Chat() {
        }

        public Chat(long id, String title, ChatPhoto photo, Message topMessage, long order, int unreadCount, int lastReadInboxMessageId, int lastReadOutboxMessageId, NotificationSettings notificationSettings, int replyMarkupMessageId, DraftMessage draftMessage, ChatInfo type) {
            this.id = id;
            this.title = title;
            this.photo = photo;
            this.topMessage = topMessage;
            this.order = order;
            this.unreadCount = unreadCount;
            this.lastReadInboxMessageId = lastReadInboxMessageId;
            this.lastReadOutboxMessageId = lastReadOutboxMessageId;
            this.notificationSettings = notificationSettings;
            this.replyMarkupMessageId = replyMarkupMessageId;
            this.draftMessage = draftMessage;
            this.type = type;
        }

        public static final int CONSTRUCTOR = -712119535;

        @Override
        public int getConstructor() {
            return -712119535;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("Chat").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("photo = "); photo.toStringBuilder(shift, s);
            appendLine(s, shift).append("topMessage = "); topMessage.toStringBuilder(shift, s);
            appendLine(s, shift).append("order = ").append(order);
            appendLine(s, shift).append("unreadCount = ").append(unreadCount);
            appendLine(s, shift).append("lastReadInboxMessageId = ").append(lastReadInboxMessageId);
            appendLine(s, shift).append("lastReadOutboxMessageId = ").append(lastReadOutboxMessageId);
            appendLine(s, shift).append("notificationSettings = "); notificationSettings.toStringBuilder(shift, s);
            appendLine(s, shift).append("replyMarkupMessageId = ").append(replyMarkupMessageId);
            appendLine(s, shift).append("draftMessage = "); draftMessage.toStringBuilder(shift, s);
            appendLine(s, shift).append("type = "); type.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public abstract static class ChatInfo extends TLObject {
    }

    public static class PrivateChatInfo extends ChatInfo {
        public User user;

        public PrivateChatInfo() {
        }

        public PrivateChatInfo(User user) {
            this.user = user;
        }

        public static final int CONSTRUCTOR = -241270753;

        @Override
        public int getConstructor() {
            return -241270753;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("PrivateChatInfo").append(" {");
            shift += 2;
            appendLine(s, shift).append("user = "); user.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GroupChatInfo extends ChatInfo {
        public Group group;

        public GroupChatInfo() {
        }

        public GroupChatInfo(Group group) {
            this.group = group;
        }

        public static final int CONSTRUCTOR = 1276053779;

        @Override
        public int getConstructor() {
            return 1276053779;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GroupChatInfo").append(" {");
            shift += 2;
            appendLine(s, shift).append("group = "); group.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ChannelChatInfo extends ChatInfo {
        public Channel channel;

        public ChannelChatInfo() {
        }

        public ChannelChatInfo(Channel channel) {
            this.channel = channel;
        }

        public static final int CONSTRUCTOR = -1297606545;

        @Override
        public int getConstructor() {
            return -1297606545;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChannelChatInfo").append(" {");
            shift += 2;
            appendLine(s, shift).append("channel = "); channel.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SecretChatInfo extends ChatInfo {
        public SecretChat secretChat;

        public SecretChatInfo() {
        }

        public SecretChatInfo(SecretChat secretChat) {
            this.secretChat = secretChat;
        }

        public static final int CONSTRUCTOR = 361623800;

        @Override
        public int getConstructor() {
            return 361623800;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SecretChatInfo").append(" {");
            shift += 2;
            appendLine(s, shift).append("secretChat = "); secretChat.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ChatInviteLink extends TLObject {
        public String inviteLink;

        public ChatInviteLink() {
        }

        public ChatInviteLink(String inviteLink) {
            this.inviteLink = inviteLink;
        }

        public static final int CONSTRUCTOR = -882072492;

        @Override
        public int getConstructor() {
            return -882072492;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChatInviteLink").append(" {");
            shift += 2;
            appendLine(s, shift).append("inviteLink = ").append(inviteLink);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ChatInviteLinkInfo extends TLObject {
        public long chatId;
        public String title;
        public ChatPhoto photo;
        public int memberCount;
        public User[] members;
        public boolean isGroup;
        public boolean isChannel;
        public boolean isPublicChannel;
        public boolean isSupergroupChannel;

        public ChatInviteLinkInfo() {
        }

        public ChatInviteLinkInfo(long chatId, String title, ChatPhoto photo, int memberCount, User[] members, boolean isGroup, boolean isChannel, boolean isPublicChannel, boolean isSupergroupChannel) {
            this.chatId = chatId;
            this.title = title;
            this.photo = photo;
            this.memberCount = memberCount;
            this.members = members;
            this.isGroup = isGroup;
            this.isChannel = isChannel;
            this.isPublicChannel = isPublicChannel;
            this.isSupergroupChannel = isSupergroupChannel;
        }

        public static final int CONSTRUCTOR = 1161132260;

        @Override
        public int getConstructor() {
            return 1161132260;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChatInviteLinkInfo").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("photo = "); photo.toStringBuilder(shift, s);
            appendLine(s, shift).append("memberCount = ").append(memberCount);
            appendLine(s, shift).append("members = ").append(Arrays.toString(members));
            appendLine(s, shift).append("isGroup = ").append(isGroup);
            appendLine(s, shift).append("isChannel = ").append(isChannel);
            appendLine(s, shift).append("isPublicChannel = ").append(isPublicChannel);
            appendLine(s, shift).append("isSupergroupChannel = ").append(isSupergroupChannel);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ChatMember extends TLObject {
        public int userId;
        public int inviterUserId;
        public int joinDate;
        public ChatMemberStatus status;
        public BotInfo botInfo;

        public ChatMember() {
        }

        public ChatMember(int userId, int inviterUserId, int joinDate, ChatMemberStatus status, BotInfo botInfo) {
            this.userId = userId;
            this.inviterUserId = inviterUserId;
            this.joinDate = joinDate;
            this.status = status;
            this.botInfo = botInfo;
        }

        public static final int CONSTRUCTOR = -1759820220;

        @Override
        public int getConstructor() {
            return -1759820220;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChatMember").append(" {");
            shift += 2;
            appendLine(s, shift).append("userId = ").append(userId);
            appendLine(s, shift).append("inviterUserId = ").append(inviterUserId);
            appendLine(s, shift).append("joinDate = ").append(joinDate);
            appendLine(s, shift).append("status = "); status.toStringBuilder(shift, s);
            appendLine(s, shift).append("botInfo = "); botInfo.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public abstract static class ChatMemberStatus extends TLObject {
    }

    public static class ChatMemberStatusCreator extends ChatMemberStatus {

        public ChatMemberStatusCreator() {
        }

        public static final int CONSTRUCTOR = 920396693;

        @Override
        public int getConstructor() {
            return 920396693;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChatMemberStatusCreator").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ChatMemberStatusEditor extends ChatMemberStatus {

        public ChatMemberStatusEditor() {
        }

        public static final int CONSTRUCTOR = -2061812836;

        @Override
        public int getConstructor() {
            return -2061812836;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChatMemberStatusEditor").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ChatMemberStatusModerator extends ChatMemberStatus {

        public ChatMemberStatusModerator() {
        }

        public static final int CONSTRUCTOR = 1687825199;

        @Override
        public int getConstructor() {
            return 1687825199;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChatMemberStatusModerator").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ChatMemberStatusMember extends ChatMemberStatus {

        public ChatMemberStatusMember() {
        }

        public static final int CONSTRUCTOR = 844723285;

        @Override
        public int getConstructor() {
            return 844723285;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChatMemberStatusMember").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ChatMemberStatusLeft extends ChatMemberStatus {

        public ChatMemberStatusLeft() {
        }

        public static final int CONSTRUCTOR = -5815259;

        @Override
        public int getConstructor() {
            return -5815259;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChatMemberStatusLeft").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ChatMemberStatusKicked extends ChatMemberStatus {

        public ChatMemberStatusKicked() {
        }

        public static final int CONSTRUCTOR = -982188162;

        @Override
        public int getConstructor() {
            return -982188162;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChatMemberStatusKicked").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ChatMembers extends TLObject {
        public int totalCount;
        public ChatMember[] members;

        public ChatMembers() {
        }

        public ChatMembers(int totalCount, ChatMember[] members) {
            this.totalCount = totalCount;
            this.members = members;
        }

        public static final int CONSTRUCTOR = -1191923299;

        @Override
        public int getConstructor() {
            return -1191923299;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChatMembers").append(" {");
            shift += 2;
            appendLine(s, shift).append("totalCount = ").append(totalCount);
            appendLine(s, shift).append("members = ").append(Arrays.toString(members));
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ChatPhoto extends TLObject {
        public File small;
        public File big;

        public ChatPhoto() {
        }

        public ChatPhoto(File small, File big) {
            this.small = small;
            this.big = big;
        }

        public static final int CONSTRUCTOR = -217062456;

        @Override
        public int getConstructor() {
            return -217062456;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChatPhoto").append(" {");
            shift += 2;
            appendLine(s, shift).append("small = "); small.toStringBuilder(shift, s);
            appendLine(s, shift).append("big = "); big.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class Chats extends TLObject {
        public Chat[] chats;

        public Chats() {
        }

        public Chats(Chat[] chats) {
            this.chats = chats;
        }

        public static final int CONSTRUCTOR = -203185581;

        @Override
        public int getConstructor() {
            return -203185581;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("Chats").append(" {");
            shift += 2;
            appendLine(s, shift).append("chats = ").append(Arrays.toString(chats));
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class Contact extends TLObject {
        public String phoneNumber;
        public String firstName;
        public String lastName;
        public int userId;

        public Contact() {
        }

        public Contact(String phoneNumber, String firstName, String lastName, int userId) {
            this.phoneNumber = phoneNumber;
            this.firstName = firstName;
            this.lastName = lastName;
            this.userId = userId;
        }

        public static final int CONSTRUCTOR = -2089833262;

        @Override
        public int getConstructor() {
            return -2089833262;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("Contact").append(" {");
            shift += 2;
            appendLine(s, shift).append("phoneNumber = ").append(phoneNumber);
            appendLine(s, shift).append("firstName = ").append(firstName);
            appendLine(s, shift).append("lastName = ").append(lastName);
            appendLine(s, shift).append("userId = ").append(userId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class Document extends TLObject {
        public String fileName;
        public String mimeType;
        public PhotoSize thumb;
        public File document;

        public Document() {
        }

        public Document(String fileName, String mimeType, PhotoSize thumb, File document) {
            this.fileName = fileName;
            this.mimeType = mimeType;
            this.thumb = thumb;
            this.document = document;
        }

        public static final int CONSTRUCTOR = 742605884;

        @Override
        public int getConstructor() {
            return 742605884;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("Document").append(" {");
            shift += 2;
            appendLine(s, shift).append("fileName = ").append(fileName);
            appendLine(s, shift).append("mimeType = ").append(mimeType);
            appendLine(s, shift).append("thumb = "); thumb.toStringBuilder(shift, s);
            appendLine(s, shift).append("document = "); document.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class DraftMessage extends TLObject {
        public int replyToMessageId;
        public InputMessageContent inputMessageText;

        public DraftMessage() {
        }

        public DraftMessage(int replyToMessageId, InputMessageContent inputMessageText) {
            this.replyToMessageId = replyToMessageId;
            this.inputMessageText = inputMessageText;
        }

        public static final int CONSTRUCTOR = 1818402019;

        @Override
        public int getConstructor() {
            return 1818402019;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("DraftMessage").append(" {");
            shift += 2;
            appendLine(s, shift).append("replyToMessageId = ").append(replyToMessageId);
            appendLine(s, shift).append("inputMessageText = "); inputMessageText.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class Error extends TLObject {
        public int code;
        public String message;

        public Error() {
        }

        public Error(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public static final int CONSTRUCTOR = 2115198528;

        @Override
        public int getConstructor() {
            return 2115198528;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("Error").append(" {");
            shift += 2;
            appendLine(s, shift).append("code = ").append(code);
            appendLine(s, shift).append("message = ").append(message);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class File extends TLObject {
        public int id;
        public String persistentId;
        public int size;
        public String path;

        public File() {
        }

        public File(int id, String persistentId, int size, String path) {
            this.id = id;
            this.persistentId = persistentId;
            this.size = size;
            this.path = path;
        }

        public static final int CONSTRUCTOR = -1956331593;

        @Override
        public int getConstructor() {
            return -1956331593;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("File").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("persistentId = ").append(persistentId);
            appendLine(s, shift).append("size = ").append(size);
            appendLine(s, shift).append("path = ").append(path);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class Game extends TLObject {
        public long id;
        public String shortName;
        public String title;
        public String text;
        public MessageEntity[] textEntities;
        public String description;
        public Photo photo;
        public Animation animation;

        public Game() {
        }

        public Game(long id, String shortName, String title, String text, MessageEntity[] textEntities, String description, Photo photo, Animation animation) {
            this.id = id;
            this.shortName = shortName;
            this.title = title;
            this.text = text;
            this.textEntities = textEntities;
            this.description = description;
            this.photo = photo;
            this.animation = animation;
        }

        public static final int CONSTRUCTOR = -1777252919;

        @Override
        public int getConstructor() {
            return -1777252919;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("Game").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("shortName = ").append(shortName);
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("text = ").append(text);
            appendLine(s, shift).append("textEntities = ").append(Arrays.toString(textEntities));
            appendLine(s, shift).append("description = ").append(description);
            appendLine(s, shift).append("photo = "); photo.toStringBuilder(shift, s);
            appendLine(s, shift).append("animation = "); animation.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GameHighScore extends TLObject {
        public int position;
        public int userId;
        public int score;

        public GameHighScore() {
        }

        public GameHighScore(int position, int userId, int score) {
            this.position = position;
            this.userId = userId;
            this.score = score;
        }

        public static final int CONSTRUCTOR = 518716217;

        @Override
        public int getConstructor() {
            return 518716217;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GameHighScore").append(" {");
            shift += 2;
            appendLine(s, shift).append("position = ").append(position);
            appendLine(s, shift).append("userId = ").append(userId);
            appendLine(s, shift).append("score = ").append(score);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GameHighScores extends TLObject {
        public GameHighScore[] scores;

        public GameHighScores() {
        }

        public GameHighScores(GameHighScore[] scores) {
            this.scores = scores;
        }

        public static final int CONSTRUCTOR = -725770727;

        @Override
        public int getConstructor() {
            return -725770727;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GameHighScores").append(" {");
            shift += 2;
            appendLine(s, shift).append("scores = ").append(Arrays.toString(scores));
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class Group extends TLObject {
        public int id;
        public int memberCount;
        public ChatMemberStatus status;
        public boolean anyoneCanEdit;
        public boolean isActive;
        public int migratedToChannelId;

        public Group() {
        }

        public Group(int id, int memberCount, ChatMemberStatus status, boolean anyoneCanEdit, boolean isActive, int migratedToChannelId) {
            this.id = id;
            this.memberCount = memberCount;
            this.status = status;
            this.anyoneCanEdit = anyoneCanEdit;
            this.isActive = isActive;
            this.migratedToChannelId = migratedToChannelId;
        }

        public static final int CONSTRUCTOR = 1988317413;

        @Override
        public int getConstructor() {
            return 1988317413;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("Group").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("memberCount = ").append(memberCount);
            appendLine(s, shift).append("status = "); status.toStringBuilder(shift, s);
            appendLine(s, shift).append("anyoneCanEdit = ").append(anyoneCanEdit);
            appendLine(s, shift).append("isActive = ").append(isActive);
            appendLine(s, shift).append("migratedToChannelId = ").append(migratedToChannelId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GroupFull extends TLObject {
        public Group group;
        public int creatorUserId;
        public ChatMember[] members;
        public String inviteLink;

        public GroupFull() {
        }

        public GroupFull(Group group, int creatorUserId, ChatMember[] members, String inviteLink) {
            this.group = group;
            this.creatorUserId = creatorUserId;
            this.members = members;
            this.inviteLink = inviteLink;
        }

        public static final int CONSTRUCTOR = -1934840272;

        @Override
        public int getConstructor() {
            return -1934840272;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GroupFull").append(" {");
            shift += 2;
            appendLine(s, shift).append("group = "); group.toStringBuilder(shift, s);
            appendLine(s, shift).append("creatorUserId = ").append(creatorUserId);
            appendLine(s, shift).append("members = ").append(Arrays.toString(members));
            appendLine(s, shift).append("inviteLink = ").append(inviteLink);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InlineKeyboardButton extends TLObject {
        public String text;
        public InlineKeyboardButtonType type;

        public InlineKeyboardButton() {
        }

        public InlineKeyboardButton(String text, InlineKeyboardButtonType type) {
            this.text = text;
            this.type = type;
        }

        public static final int CONSTRUCTOR = -372105704;

        @Override
        public int getConstructor() {
            return -372105704;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineKeyboardButton").append(" {");
            shift += 2;
            appendLine(s, shift).append("text = ").append(text);
            appendLine(s, shift).append("type = "); type.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public abstract static class InlineKeyboardButtonType extends TLObject {
    }

    public static class InlineKeyboardButtonTypeUrl extends InlineKeyboardButtonType {
        public String url;

        public InlineKeyboardButtonTypeUrl() {
        }

        public InlineKeyboardButtonTypeUrl(String url) {
            this.url = url;
        }

        public static final int CONSTRUCTOR = 1130741420;

        @Override
        public int getConstructor() {
            return 1130741420;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineKeyboardButtonTypeUrl").append(" {");
            shift += 2;
            appendLine(s, shift).append("url = ").append(url);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InlineKeyboardButtonTypeCallback extends InlineKeyboardButtonType {
        public byte[] data;

        public InlineKeyboardButtonTypeCallback() {
        }

        public InlineKeyboardButtonTypeCallback(byte[] data) {
            this.data = data;
        }

        public static final int CONSTRUCTOR = -1127515139;

        @Override
        public int getConstructor() {
            return -1127515139;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineKeyboardButtonTypeCallback").append(" {");
            shift += 2;
            appendLine(s, shift).append("data = ").append("bytes { "); { for (byte k : data) { int b = (int)k & 255; s.append(HEX_CHARACTERS[b >> 4]).append(HEX_CHARACTERS[b & 15]).append(' '); } } s.append('}');
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InlineKeyboardButtonTypeCallbackGame extends InlineKeyboardButtonType {

        public InlineKeyboardButtonTypeCallbackGame() {
        }

        public static final int CONSTRUCTOR = -383429528;

        @Override
        public int getConstructor() {
            return -383429528;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineKeyboardButtonTypeCallbackGame").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InlineKeyboardButtonTypeSwitchInline extends InlineKeyboardButtonType {
        public String query;
        public boolean inCurrentChat;

        public InlineKeyboardButtonTypeSwitchInline() {
        }

        public InlineKeyboardButtonTypeSwitchInline(String query, boolean inCurrentChat) {
            this.query = query;
            this.inCurrentChat = inCurrentChat;
        }

        public static final int CONSTRUCTOR = -2035563307;

        @Override
        public int getConstructor() {
            return -2035563307;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineKeyboardButtonTypeSwitchInline").append(" {");
            shift += 2;
            appendLine(s, shift).append("query = ").append(query);
            appendLine(s, shift).append("inCurrentChat = ").append(inCurrentChat);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public abstract static class InlineQueryResult extends TLObject {
    }

    public static class InlineQueryResultArticle extends InlineQueryResult {
        public String id;
        public String url;
        public boolean hideUrl;
        public String title;
        public String description;
        public String thumbUrl;
        public int thumbWidth;
        public int thumbHeight;

        public InlineQueryResultArticle() {
        }

        public InlineQueryResultArticle(String id, String url, boolean hideUrl, String title, String description, String thumbUrl, int thumbWidth, int thumbHeight) {
            this.id = id;
            this.url = url;
            this.hideUrl = hideUrl;
            this.title = title;
            this.description = description;
            this.thumbUrl = thumbUrl;
            this.thumbWidth = thumbWidth;
            this.thumbHeight = thumbHeight;
        }

        public static final int CONSTRUCTOR = 571709788;

        @Override
        public int getConstructor() {
            return 571709788;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineQueryResultArticle").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("url = ").append(url);
            appendLine(s, shift).append("hideUrl = ").append(hideUrl);
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("description = ").append(description);
            appendLine(s, shift).append("thumbUrl = ").append(thumbUrl);
            appendLine(s, shift).append("thumbWidth = ").append(thumbWidth);
            appendLine(s, shift).append("thumbHeight = ").append(thumbHeight);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InlineQueryResultContact extends InlineQueryResult {
        public String id;
        public Contact contact;
        public String thumbUrl;
        public int thumbWidth;
        public int thumbHeight;

        public InlineQueryResultContact() {
        }

        public InlineQueryResultContact(String id, Contact contact, String thumbUrl, int thumbWidth, int thumbHeight) {
            this.id = id;
            this.contact = contact;
            this.thumbUrl = thumbUrl;
            this.thumbWidth = thumbWidth;
            this.thumbHeight = thumbHeight;
        }

        public static final int CONSTRUCTOR = 1362196731;

        @Override
        public int getConstructor() {
            return 1362196731;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineQueryResultContact").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("contact = "); contact.toStringBuilder(shift, s);
            appendLine(s, shift).append("thumbUrl = ").append(thumbUrl);
            appendLine(s, shift).append("thumbWidth = ").append(thumbWidth);
            appendLine(s, shift).append("thumbHeight = ").append(thumbHeight);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InlineQueryResultLocation extends InlineQueryResult {
        public String id;
        public Location location;
        public String title;
        public String thumbUrl;
        public int thumbWidth;
        public int thumbHeight;

        public InlineQueryResultLocation() {
        }

        public InlineQueryResultLocation(String id, Location location, String title, String thumbUrl, int thumbWidth, int thumbHeight) {
            this.id = id;
            this.location = location;
            this.title = title;
            this.thumbUrl = thumbUrl;
            this.thumbWidth = thumbWidth;
            this.thumbHeight = thumbHeight;
        }

        public static final int CONSTRUCTOR = 1290211266;

        @Override
        public int getConstructor() {
            return 1290211266;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineQueryResultLocation").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("location = "); location.toStringBuilder(shift, s);
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("thumbUrl = ").append(thumbUrl);
            appendLine(s, shift).append("thumbWidth = ").append(thumbWidth);
            appendLine(s, shift).append("thumbHeight = ").append(thumbHeight);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InlineQueryResultVenue extends InlineQueryResult {
        public String id;
        public Venue venue;
        public String thumbUrl;
        public int thumbWidth;
        public int thumbHeight;

        public InlineQueryResultVenue() {
        }

        public InlineQueryResultVenue(String id, Venue venue, String thumbUrl, int thumbWidth, int thumbHeight) {
            this.id = id;
            this.venue = venue;
            this.thumbUrl = thumbUrl;
            this.thumbWidth = thumbWidth;
            this.thumbHeight = thumbHeight;
        }

        public static final int CONSTRUCTOR = -1226696829;

        @Override
        public int getConstructor() {
            return -1226696829;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineQueryResultVenue").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("venue = "); venue.toStringBuilder(shift, s);
            appendLine(s, shift).append("thumbUrl = ").append(thumbUrl);
            appendLine(s, shift).append("thumbWidth = ").append(thumbWidth);
            appendLine(s, shift).append("thumbHeight = ").append(thumbHeight);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InlineQueryResultGame extends InlineQueryResult {
        public String id;
        public Game game;

        public InlineQueryResultGame() {
        }

        public InlineQueryResultGame(String id, Game game) {
            this.id = id;
            this.game = game;
        }

        public static final int CONSTRUCTOR = 1706916987;

        @Override
        public int getConstructor() {
            return 1706916987;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineQueryResultGame").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("game = "); game.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InlineQueryResultAnimation extends InlineQueryResult {
        public String id;
        public Animation animation;
        public String title;

        public InlineQueryResultAnimation() {
        }

        public InlineQueryResultAnimation(String id, Animation animation, String title) {
            this.id = id;
            this.animation = animation;
            this.title = title;
        }

        public static final int CONSTRUCTOR = 2009984267;

        @Override
        public int getConstructor() {
            return 2009984267;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineQueryResultAnimation").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("animation = "); animation.toStringBuilder(shift, s);
            appendLine(s, shift).append("title = ").append(title);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InlineQueryResultAudio extends InlineQueryResult {
        public String id;
        public Audio audio;

        public InlineQueryResultAudio() {
        }

        public InlineQueryResultAudio(String id, Audio audio) {
            this.id = id;
            this.audio = audio;
        }

        public static final int CONSTRUCTOR = 842650360;

        @Override
        public int getConstructor() {
            return 842650360;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineQueryResultAudio").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("audio = "); audio.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InlineQueryResultDocument extends InlineQueryResult {
        public String id;
        public Document document;
        public String title;
        public String description;

        public InlineQueryResultDocument() {
        }

        public InlineQueryResultDocument(String id, Document document, String title, String description) {
            this.id = id;
            this.document = document;
            this.title = title;
            this.description = description;
        }

        public static final int CONSTRUCTOR = -1491268539;

        @Override
        public int getConstructor() {
            return -1491268539;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineQueryResultDocument").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("document = "); document.toStringBuilder(shift, s);
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("description = ").append(description);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InlineQueryResultPhoto extends InlineQueryResult {
        public String id;
        public Photo photo;
        public String title;
        public String description;

        public InlineQueryResultPhoto() {
        }

        public InlineQueryResultPhoto(String id, Photo photo, String title, String description) {
            this.id = id;
            this.photo = photo;
            this.title = title;
            this.description = description;
        }

        public static final int CONSTRUCTOR = 1848319440;

        @Override
        public int getConstructor() {
            return 1848319440;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineQueryResultPhoto").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("photo = "); photo.toStringBuilder(shift, s);
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("description = ").append(description);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InlineQueryResultSticker extends InlineQueryResult {
        public String id;
        public Sticker sticker;

        public InlineQueryResultSticker() {
        }

        public InlineQueryResultSticker(String id, Sticker sticker) {
            this.id = id;
            this.sticker = sticker;
        }

        public static final int CONSTRUCTOR = -1848224245;

        @Override
        public int getConstructor() {
            return -1848224245;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineQueryResultSticker").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("sticker = "); sticker.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InlineQueryResultVideo extends InlineQueryResult {
        public String id;
        public Video video;
        public String title;
        public String description;

        public InlineQueryResultVideo() {
        }

        public InlineQueryResultVideo(String id, Video video, String title, String description) {
            this.id = id;
            this.video = video;
            this.title = title;
            this.description = description;
        }

        public static final int CONSTRUCTOR = -1373158683;

        @Override
        public int getConstructor() {
            return -1373158683;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineQueryResultVideo").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("video = "); video.toStringBuilder(shift, s);
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("description = ").append(description);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InlineQueryResultVoice extends InlineQueryResult {
        public String id;
        public Voice voice;
        public String title;

        public InlineQueryResultVoice() {
        }

        public InlineQueryResultVoice(String id, Voice voice, String title) {
            this.id = id;
            this.voice = voice;
            this.title = title;
        }

        public static final int CONSTRUCTOR = -1799819518;

        @Override
        public int getConstructor() {
            return -1799819518;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineQueryResultVoice").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("voice = "); voice.toStringBuilder(shift, s);
            appendLine(s, shift).append("title = ").append(title);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InlineQueryResults extends TLObject {
        public long inlineQueryId;
        public String nextOffset;
        public InlineQueryResult[] results;
        public String switchPmText;
        public String switchPmParameter;

        public InlineQueryResults() {
        }

        public InlineQueryResults(long inlineQueryId, String nextOffset, InlineQueryResult[] results, String switchPmText, String switchPmParameter) {
            this.inlineQueryId = inlineQueryId;
            this.nextOffset = nextOffset;
            this.results = results;
            this.switchPmText = switchPmText;
            this.switchPmParameter = switchPmParameter;
        }

        public static final int CONSTRUCTOR = -399798881;

        @Override
        public int getConstructor() {
            return -399798881;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineQueryResults").append(" {");
            shift += 2;
            appendLine(s, shift).append("inlineQueryId = ").append(inlineQueryId);
            appendLine(s, shift).append("nextOffset = ").append(nextOffset);
            appendLine(s, shift).append("results = ").append(Arrays.toString(results));
            appendLine(s, shift).append("switchPmText = ").append(switchPmText);
            appendLine(s, shift).append("switchPmParameter = ").append(switchPmParameter);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public abstract static class InputFile extends TLObject {
    }

    public static class InputFileId extends InputFile {
        public int id;

        public InputFileId() {
        }

        public InputFileId(int id) {
            this.id = id;
        }

        public static final int CONSTRUCTOR = 1553438243;

        @Override
        public int getConstructor() {
            return 1553438243;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputFileId").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InputFilePersistentId extends InputFile {
        public String persistentId;

        public InputFilePersistentId() {
        }

        public InputFilePersistentId(String persistentId) {
            this.persistentId = persistentId;
        }

        public static final int CONSTRUCTOR = 1856539551;

        @Override
        public int getConstructor() {
            return 1856539551;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputFilePersistentId").append(" {");
            shift += 2;
            appendLine(s, shift).append("persistentId = ").append(persistentId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InputFileLocal extends InputFile {
        public String path;

        public InputFileLocal() {
        }

        public InputFileLocal(String path) {
            this.path = path;
        }

        public static final int CONSTRUCTOR = 2056030919;

        @Override
        public int getConstructor() {
            return 2056030919;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputFileLocal").append(" {");
            shift += 2;
            appendLine(s, shift).append("path = ").append(path);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public abstract static class InputInlineQueryResult extends TLObject {
    }

    public static class InputInlineQueryResultAnimatedGif extends InputInlineQueryResult {
        public String id;
        public String title;
        public String thumbUrl;
        public String gifUrl;
        public int gifWidth;
        public int gifHeight;
        public ReplyMarkup replyMarkup;
        public InputMessageContent inputMessageContent;

        public InputInlineQueryResultAnimatedGif() {
        }

        public InputInlineQueryResultAnimatedGif(String id, String title, String thumbUrl, String gifUrl, int gifWidth, int gifHeight, ReplyMarkup replyMarkup, InputMessageContent inputMessageContent) {
            this.id = id;
            this.title = title;
            this.thumbUrl = thumbUrl;
            this.gifUrl = gifUrl;
            this.gifWidth = gifWidth;
            this.gifHeight = gifHeight;
            this.replyMarkup = replyMarkup;
            this.inputMessageContent = inputMessageContent;
        }

        public static final int CONSTRUCTOR = 1525729396;

        @Override
        public int getConstructor() {
            return 1525729396;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputInlineQueryResultAnimatedGif").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("thumbUrl = ").append(thumbUrl);
            appendLine(s, shift).append("gifUrl = ").append(gifUrl);
            appendLine(s, shift).append("gifWidth = ").append(gifWidth);
            appendLine(s, shift).append("gifHeight = ").append(gifHeight);
            appendLine(s, shift).append("replyMarkup = "); replyMarkup.toStringBuilder(shift, s);
            appendLine(s, shift).append("inputMessageContent = "); inputMessageContent.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InputInlineQueryResultAnimatedMpeg4 extends InputInlineQueryResult {
        public String id;
        public String title;
        public String thumbUrl;
        public String mpeg4Url;
        public int mpeg4Width;
        public int mpeg4Height;
        public ReplyMarkup replyMarkup;
        public InputMessageContent inputMessageContent;

        public InputInlineQueryResultAnimatedMpeg4() {
        }

        public InputInlineQueryResultAnimatedMpeg4(String id, String title, String thumbUrl, String mpeg4Url, int mpeg4Width, int mpeg4Height, ReplyMarkup replyMarkup, InputMessageContent inputMessageContent) {
            this.id = id;
            this.title = title;
            this.thumbUrl = thumbUrl;
            this.mpeg4Url = mpeg4Url;
            this.mpeg4Width = mpeg4Width;
            this.mpeg4Height = mpeg4Height;
            this.replyMarkup = replyMarkup;
            this.inputMessageContent = inputMessageContent;
        }

        public static final int CONSTRUCTOR = 1614693872;

        @Override
        public int getConstructor() {
            return 1614693872;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputInlineQueryResultAnimatedMpeg4").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("thumbUrl = ").append(thumbUrl);
            appendLine(s, shift).append("mpeg4Url = ").append(mpeg4Url);
            appendLine(s, shift).append("mpeg4Width = ").append(mpeg4Width);
            appendLine(s, shift).append("mpeg4Height = ").append(mpeg4Height);
            appendLine(s, shift).append("replyMarkup = "); replyMarkup.toStringBuilder(shift, s);
            appendLine(s, shift).append("inputMessageContent = "); inputMessageContent.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InputInlineQueryResultArticle extends InputInlineQueryResult {
        public String id;
        public String url;
        public boolean hideUrl;
        public String title;
        public String description;
        public String thumbUrl;
        public int thumbWidth;
        public int thumbHeight;
        public ReplyMarkup replyMarkup;
        public InputMessageContent inputMessageContent;

        public InputInlineQueryResultArticle() {
        }

        public InputInlineQueryResultArticle(String id, String url, boolean hideUrl, String title, String description, String thumbUrl, int thumbWidth, int thumbHeight, ReplyMarkup replyMarkup, InputMessageContent inputMessageContent) {
            this.id = id;
            this.url = url;
            this.hideUrl = hideUrl;
            this.title = title;
            this.description = description;
            this.thumbUrl = thumbUrl;
            this.thumbWidth = thumbWidth;
            this.thumbHeight = thumbHeight;
            this.replyMarkup = replyMarkup;
            this.inputMessageContent = inputMessageContent;
        }

        public static final int CONSTRUCTOR = 1146479239;

        @Override
        public int getConstructor() {
            return 1146479239;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputInlineQueryResultArticle").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("url = ").append(url);
            appendLine(s, shift).append("hideUrl = ").append(hideUrl);
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("description = ").append(description);
            appendLine(s, shift).append("thumbUrl = ").append(thumbUrl);
            appendLine(s, shift).append("thumbWidth = ").append(thumbWidth);
            appendLine(s, shift).append("thumbHeight = ").append(thumbHeight);
            appendLine(s, shift).append("replyMarkup = "); replyMarkup.toStringBuilder(shift, s);
            appendLine(s, shift).append("inputMessageContent = "); inputMessageContent.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InputInlineQueryResultAudio extends InputInlineQueryResult {
        public String id;
        public String title;
        public String performer;
        public String audioUrl;
        public int audioDuration;
        public ReplyMarkup replyMarkup;
        public InputMessageContent inputMessageContent;

        public InputInlineQueryResultAudio() {
        }

        public InputInlineQueryResultAudio(String id, String title, String performer, String audioUrl, int audioDuration, ReplyMarkup replyMarkup, InputMessageContent inputMessageContent) {
            this.id = id;
            this.title = title;
            this.performer = performer;
            this.audioUrl = audioUrl;
            this.audioDuration = audioDuration;
            this.replyMarkup = replyMarkup;
            this.inputMessageContent = inputMessageContent;
        }

        public static final int CONSTRUCTOR = 1891166308;

        @Override
        public int getConstructor() {
            return 1891166308;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputInlineQueryResultAudio").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("performer = ").append(performer);
            appendLine(s, shift).append("audioUrl = ").append(audioUrl);
            appendLine(s, shift).append("audioDuration = ").append(audioDuration);
            appendLine(s, shift).append("replyMarkup = "); replyMarkup.toStringBuilder(shift, s);
            appendLine(s, shift).append("inputMessageContent = "); inputMessageContent.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InputInlineQueryResultContact extends InputInlineQueryResult {
        public String id;
        public Contact contact;
        public String thumbUrl;
        public int thumbWidth;
        public int thumbHeight;
        public ReplyMarkup replyMarkup;
        public InputMessageContent inputMessageContent;

        public InputInlineQueryResultContact() {
        }

        public InputInlineQueryResultContact(String id, Contact contact, String thumbUrl, int thumbWidth, int thumbHeight, ReplyMarkup replyMarkup, InputMessageContent inputMessageContent) {
            this.id = id;
            this.contact = contact;
            this.thumbUrl = thumbUrl;
            this.thumbWidth = thumbWidth;
            this.thumbHeight = thumbHeight;
            this.replyMarkup = replyMarkup;
            this.inputMessageContent = inputMessageContent;
        }

        public static final int CONSTRUCTOR = -1322216516;

        @Override
        public int getConstructor() {
            return -1322216516;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputInlineQueryResultContact").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("contact = "); contact.toStringBuilder(shift, s);
            appendLine(s, shift).append("thumbUrl = ").append(thumbUrl);
            appendLine(s, shift).append("thumbWidth = ").append(thumbWidth);
            appendLine(s, shift).append("thumbHeight = ").append(thumbHeight);
            appendLine(s, shift).append("replyMarkup = "); replyMarkup.toStringBuilder(shift, s);
            appendLine(s, shift).append("inputMessageContent = "); inputMessageContent.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InputInlineQueryResultDocument extends InputInlineQueryResult {
        public String id;
        public String title;
        public String description;
        public String documentUrl;
        public String mimeType;
        public String thumbUrl;
        public int thumbWidth;
        public int thumbHeight;
        public ReplyMarkup replyMarkup;
        public InputMessageContent inputMessageContent;

        public InputInlineQueryResultDocument() {
        }

        public InputInlineQueryResultDocument(String id, String title, String description, String documentUrl, String mimeType, String thumbUrl, int thumbWidth, int thumbHeight, ReplyMarkup replyMarkup, InputMessageContent inputMessageContent) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.documentUrl = documentUrl;
            this.mimeType = mimeType;
            this.thumbUrl = thumbUrl;
            this.thumbWidth = thumbWidth;
            this.thumbHeight = thumbHeight;
            this.replyMarkup = replyMarkup;
            this.inputMessageContent = inputMessageContent;
        }

        public static final int CONSTRUCTOR = 310750688;

        @Override
        public int getConstructor() {
            return 310750688;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputInlineQueryResultDocument").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("description = ").append(description);
            appendLine(s, shift).append("documentUrl = ").append(documentUrl);
            appendLine(s, shift).append("mimeType = ").append(mimeType);
            appendLine(s, shift).append("thumbUrl = ").append(thumbUrl);
            appendLine(s, shift).append("thumbWidth = ").append(thumbWidth);
            appendLine(s, shift).append("thumbHeight = ").append(thumbHeight);
            appendLine(s, shift).append("replyMarkup = "); replyMarkup.toStringBuilder(shift, s);
            appendLine(s, shift).append("inputMessageContent = "); inputMessageContent.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InputInlineQueryResultGame extends InputInlineQueryResult {
        public String id;
        public String gameShortName;
        public ReplyMarkup replyMarkup;

        public InputInlineQueryResultGame() {
        }

        public InputInlineQueryResultGame(String id, String gameShortName, ReplyMarkup replyMarkup) {
            this.id = id;
            this.gameShortName = gameShortName;
            this.replyMarkup = replyMarkup;
        }

        public static final int CONSTRUCTOR = 966074327;

        @Override
        public int getConstructor() {
            return 966074327;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputInlineQueryResultGame").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("gameShortName = ").append(gameShortName);
            appendLine(s, shift).append("replyMarkup = "); replyMarkup.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InputInlineQueryResultLocation extends InputInlineQueryResult {
        public String id;
        public Location location;
        public String title;
        public String thumbUrl;
        public int thumbWidth;
        public int thumbHeight;
        public ReplyMarkup replyMarkup;
        public InputMessageContent inputMessageContent;

        public InputInlineQueryResultLocation() {
        }

        public InputInlineQueryResultLocation(String id, Location location, String title, String thumbUrl, int thumbWidth, int thumbHeight, ReplyMarkup replyMarkup, InputMessageContent inputMessageContent) {
            this.id = id;
            this.location = location;
            this.title = title;
            this.thumbUrl = thumbUrl;
            this.thumbWidth = thumbWidth;
            this.thumbHeight = thumbHeight;
            this.replyMarkup = replyMarkup;
            this.inputMessageContent = inputMessageContent;
        }

        public static final int CONSTRUCTOR = 1842110793;

        @Override
        public int getConstructor() {
            return 1842110793;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputInlineQueryResultLocation").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("location = "); location.toStringBuilder(shift, s);
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("thumbUrl = ").append(thumbUrl);
            appendLine(s, shift).append("thumbWidth = ").append(thumbWidth);
            appendLine(s, shift).append("thumbHeight = ").append(thumbHeight);
            appendLine(s, shift).append("replyMarkup = "); replyMarkup.toStringBuilder(shift, s);
            appendLine(s, shift).append("inputMessageContent = "); inputMessageContent.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InputInlineQueryResultPhoto extends InputInlineQueryResult {
        public String id;
        public String title;
        public String description;
        public String thumbUrl;
        public String photoUrl;
        public int photoWidth;
        public int photoHeight;
        public ReplyMarkup replyMarkup;
        public InputMessageContent inputMessageContent;

        public InputInlineQueryResultPhoto() {
        }

        public InputInlineQueryResultPhoto(String id, String title, String description, String thumbUrl, String photoUrl, int photoWidth, int photoHeight, ReplyMarkup replyMarkup, InputMessageContent inputMessageContent) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.thumbUrl = thumbUrl;
            this.photoUrl = photoUrl;
            this.photoWidth = photoWidth;
            this.photoHeight = photoHeight;
            this.replyMarkup = replyMarkup;
            this.inputMessageContent = inputMessageContent;
        }

        public static final int CONSTRUCTOR = 737237435;

        @Override
        public int getConstructor() {
            return 737237435;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputInlineQueryResultPhoto").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("description = ").append(description);
            appendLine(s, shift).append("thumbUrl = ").append(thumbUrl);
            appendLine(s, shift).append("photoUrl = ").append(photoUrl);
            appendLine(s, shift).append("photoWidth = ").append(photoWidth);
            appendLine(s, shift).append("photoHeight = ").append(photoHeight);
            appendLine(s, shift).append("replyMarkup = "); replyMarkup.toStringBuilder(shift, s);
            appendLine(s, shift).append("inputMessageContent = "); inputMessageContent.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InputInlineQueryResultSticker extends InputInlineQueryResult {
        public String id;
        public String thumbUrl;
        public String stickerUrl;
        public int stickerWidth;
        public int stickerHeight;
        public ReplyMarkup replyMarkup;
        public InputMessageContent inputMessageContent;

        public InputInlineQueryResultSticker() {
        }

        public InputInlineQueryResultSticker(String id, String thumbUrl, String stickerUrl, int stickerWidth, int stickerHeight, ReplyMarkup replyMarkup, InputMessageContent inputMessageContent) {
            this.id = id;
            this.thumbUrl = thumbUrl;
            this.stickerUrl = stickerUrl;
            this.stickerWidth = stickerWidth;
            this.stickerHeight = stickerHeight;
            this.replyMarkup = replyMarkup;
            this.inputMessageContent = inputMessageContent;
        }

        public static final int CONSTRUCTOR = 850856384;

        @Override
        public int getConstructor() {
            return 850856384;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputInlineQueryResultSticker").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("thumbUrl = ").append(thumbUrl);
            appendLine(s, shift).append("stickerUrl = ").append(stickerUrl);
            appendLine(s, shift).append("stickerWidth = ").append(stickerWidth);
            appendLine(s, shift).append("stickerHeight = ").append(stickerHeight);
            appendLine(s, shift).append("replyMarkup = "); replyMarkup.toStringBuilder(shift, s);
            appendLine(s, shift).append("inputMessageContent = "); inputMessageContent.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InputInlineQueryResultVenue extends InputInlineQueryResult {
        public String id;
        public Venue venue;
        public String thumbUrl;
        public int thumbWidth;
        public int thumbHeight;
        public ReplyMarkup replyMarkup;
        public InputMessageContent inputMessageContent;

        public InputInlineQueryResultVenue() {
        }

        public InputInlineQueryResultVenue(String id, Venue venue, String thumbUrl, int thumbWidth, int thumbHeight, ReplyMarkup replyMarkup, InputMessageContent inputMessageContent) {
            this.id = id;
            this.venue = venue;
            this.thumbUrl = thumbUrl;
            this.thumbWidth = thumbWidth;
            this.thumbHeight = thumbHeight;
            this.replyMarkup = replyMarkup;
            this.inputMessageContent = inputMessageContent;
        }

        public static final int CONSTRUCTOR = -567519575;

        @Override
        public int getConstructor() {
            return -567519575;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputInlineQueryResultVenue").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("venue = "); venue.toStringBuilder(shift, s);
            appendLine(s, shift).append("thumbUrl = ").append(thumbUrl);
            appendLine(s, shift).append("thumbWidth = ").append(thumbWidth);
            appendLine(s, shift).append("thumbHeight = ").append(thumbHeight);
            appendLine(s, shift).append("replyMarkup = "); replyMarkup.toStringBuilder(shift, s);
            appendLine(s, shift).append("inputMessageContent = "); inputMessageContent.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InputInlineQueryResultVideo extends InputInlineQueryResult {
        public String id;
        public String title;
        public String description;
        public String thumbUrl;
        public String videoUrl;
        public String mimeType;
        public int videoWidth;
        public int videoHeight;
        public int videoDuration;
        public ReplyMarkup replyMarkup;
        public InputMessageContent inputMessageContent;

        public InputInlineQueryResultVideo() {
        }

        public InputInlineQueryResultVideo(String id, String title, String description, String thumbUrl, String videoUrl, String mimeType, int videoWidth, int videoHeight, int videoDuration, ReplyMarkup replyMarkup, InputMessageContent inputMessageContent) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.thumbUrl = thumbUrl;
            this.videoUrl = videoUrl;
            this.mimeType = mimeType;
            this.videoWidth = videoWidth;
            this.videoHeight = videoHeight;
            this.videoDuration = videoDuration;
            this.replyMarkup = replyMarkup;
            this.inputMessageContent = inputMessageContent;
        }

        public static final int CONSTRUCTOR = -2120242070;

        @Override
        public int getConstructor() {
            return -2120242070;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputInlineQueryResultVideo").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("description = ").append(description);
            appendLine(s, shift).append("thumbUrl = ").append(thumbUrl);
            appendLine(s, shift).append("videoUrl = ").append(videoUrl);
            appendLine(s, shift).append("mimeType = ").append(mimeType);
            appendLine(s, shift).append("videoWidth = ").append(videoWidth);
            appendLine(s, shift).append("videoHeight = ").append(videoHeight);
            appendLine(s, shift).append("videoDuration = ").append(videoDuration);
            appendLine(s, shift).append("replyMarkup = "); replyMarkup.toStringBuilder(shift, s);
            appendLine(s, shift).append("inputMessageContent = "); inputMessageContent.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InputInlineQueryResultVoice extends InputInlineQueryResult {
        public String id;
        public String title;
        public String voiceUrl;
        public int voiceDuration;
        public ReplyMarkup replyMarkup;
        public InputMessageContent inputMessageContent;

        public InputInlineQueryResultVoice() {
        }

        public InputInlineQueryResultVoice(String id, String title, String voiceUrl, int voiceDuration, ReplyMarkup replyMarkup, InputMessageContent inputMessageContent) {
            this.id = id;
            this.title = title;
            this.voiceUrl = voiceUrl;
            this.voiceDuration = voiceDuration;
            this.replyMarkup = replyMarkup;
            this.inputMessageContent = inputMessageContent;
        }

        public static final int CONSTRUCTOR = 950126512;

        @Override
        public int getConstructor() {
            return 950126512;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputInlineQueryResultVoice").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("voiceUrl = ").append(voiceUrl);
            appendLine(s, shift).append("voiceDuration = ").append(voiceDuration);
            appendLine(s, shift).append("replyMarkup = "); replyMarkup.toStringBuilder(shift, s);
            appendLine(s, shift).append("inputMessageContent = "); inputMessageContent.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public abstract static class InputMessageContent extends TLObject {
    }

    public static class InputMessageText extends InputMessageContent {
        public String text;
        public boolean disableWebPagePreview;
        public boolean clearDraft;
        public MessageEntity[] entities;
        public TextParseMode parseMode;

        public InputMessageText() {
        }

        public InputMessageText(String text, boolean disableWebPagePreview, boolean clearDraft, MessageEntity[] entities, TextParseMode parseMode) {
            this.text = text;
            this.disableWebPagePreview = disableWebPagePreview;
            this.clearDraft = clearDraft;
            this.entities = entities;
            this.parseMode = parseMode;
        }

        public static final int CONSTRUCTOR = 1095854080;

        @Override
        public int getConstructor() {
            return 1095854080;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessageText").append(" {");
            shift += 2;
            appendLine(s, shift).append("text = ").append(text);
            appendLine(s, shift).append("disableWebPagePreview = ").append(disableWebPagePreview);
            appendLine(s, shift).append("clearDraft = ").append(clearDraft);
            appendLine(s, shift).append("entities = ").append(Arrays.toString(entities));
            appendLine(s, shift).append("parseMode = "); parseMode.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InputMessageAnimation extends InputMessageContent {
        public InputFile animation;
        public InputThumb thumb;
        public int width;
        public int height;
        public String caption;

        public InputMessageAnimation() {
        }

        public InputMessageAnimation(InputFile animation, InputThumb thumb, int width, int height, String caption) {
            this.animation = animation;
            this.thumb = thumb;
            this.width = width;
            this.height = height;
            this.caption = caption;
        }

        public static final int CONSTRUCTOR = -238183558;

        @Override
        public int getConstructor() {
            return -238183558;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessageAnimation").append(" {");
            shift += 2;
            appendLine(s, shift).append("animation = "); animation.toStringBuilder(shift, s);
            appendLine(s, shift).append("thumb = "); thumb.toStringBuilder(shift, s);
            appendLine(s, shift).append("width = ").append(width);
            appendLine(s, shift).append("height = ").append(height);
            appendLine(s, shift).append("caption = ").append(caption);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InputMessageAudio extends InputMessageContent {
        public InputFile audio;
        public InputThumb albumCoverThumb;
        public int duration;
        public String title;
        public String performer;
        public String caption;

        public InputMessageAudio() {
        }

        public InputMessageAudio(InputFile audio, InputThumb albumCoverThumb, int duration, String title, String performer, String caption) {
            this.audio = audio;
            this.albumCoverThumb = albumCoverThumb;
            this.duration = duration;
            this.title = title;
            this.performer = performer;
            this.caption = caption;
        }

        public static final int CONSTRUCTOR = -1122193064;

        @Override
        public int getConstructor() {
            return -1122193064;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessageAudio").append(" {");
            shift += 2;
            appendLine(s, shift).append("audio = "); audio.toStringBuilder(shift, s);
            appendLine(s, shift).append("albumCoverThumb = "); albumCoverThumb.toStringBuilder(shift, s);
            appendLine(s, shift).append("duration = ").append(duration);
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("performer = ").append(performer);
            appendLine(s, shift).append("caption = ").append(caption);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InputMessageDocument extends InputMessageContent {
        public InputFile document;
        public InputThumb thumb;
        public String caption;

        public InputMessageDocument() {
        }

        public InputMessageDocument(InputFile document, InputThumb thumb, String caption) {
            this.document = document;
            this.thumb = thumb;
            this.caption = caption;
        }

        public static final int CONSTRUCTOR = 395779985;

        @Override
        public int getConstructor() {
            return 395779985;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessageDocument").append(" {");
            shift += 2;
            appendLine(s, shift).append("document = "); document.toStringBuilder(shift, s);
            appendLine(s, shift).append("thumb = "); thumb.toStringBuilder(shift, s);
            appendLine(s, shift).append("caption = ").append(caption);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InputMessagePhoto extends InputMessageContent {
        public InputFile photo;
        public String caption;

        public InputMessagePhoto() {
        }

        public InputMessagePhoto(InputFile photo, String caption) {
            this.photo = photo;
            this.caption = caption;
        }

        public static final int CONSTRUCTOR = 762116923;

        @Override
        public int getConstructor() {
            return 762116923;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessagePhoto").append(" {");
            shift += 2;
            appendLine(s, shift).append("photo = "); photo.toStringBuilder(shift, s);
            appendLine(s, shift).append("caption = ").append(caption);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InputMessageSticker extends InputMessageContent {
        public InputFile sticker;
        public InputThumb thumb;

        public InputMessageSticker() {
        }

        public InputMessageSticker(InputFile sticker, InputThumb thumb) {
            this.sticker = sticker;
            this.thumb = thumb;
        }

        public static final int CONSTRUCTOR = -892964456;

        @Override
        public int getConstructor() {
            return -892964456;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessageSticker").append(" {");
            shift += 2;
            appendLine(s, shift).append("sticker = "); sticker.toStringBuilder(shift, s);
            appendLine(s, shift).append("thumb = "); thumb.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InputMessageVideo extends InputMessageContent {
        public InputFile video;
        public InputThumb thumb;
        public int duration;
        public int width;
        public int height;
        public String caption;

        public InputMessageVideo() {
        }

        public InputMessageVideo(InputFile video, InputThumb thumb, int duration, int width, int height, String caption) {
            this.video = video;
            this.thumb = thumb;
            this.duration = duration;
            this.width = width;
            this.height = height;
            this.caption = caption;
        }

        public static final int CONSTRUCTOR = -1954994738;

        @Override
        public int getConstructor() {
            return -1954994738;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessageVideo").append(" {");
            shift += 2;
            appendLine(s, shift).append("video = "); video.toStringBuilder(shift, s);
            appendLine(s, shift).append("thumb = "); thumb.toStringBuilder(shift, s);
            appendLine(s, shift).append("duration = ").append(duration);
            appendLine(s, shift).append("width = ").append(width);
            appendLine(s, shift).append("height = ").append(height);
            appendLine(s, shift).append("caption = ").append(caption);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InputMessageVoice extends InputMessageContent {
        public InputFile voice;
        public int duration;
        public byte[] waveform;
        public String caption;

        public InputMessageVoice() {
        }

        public InputMessageVoice(InputFile voice, int duration, byte[] waveform, String caption) {
            this.voice = voice;
            this.duration = duration;
            this.waveform = waveform;
            this.caption = caption;
        }

        public static final int CONSTRUCTOR = -2011553452;

        @Override
        public int getConstructor() {
            return -2011553452;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessageVoice").append(" {");
            shift += 2;
            appendLine(s, shift).append("voice = "); voice.toStringBuilder(shift, s);
            appendLine(s, shift).append("duration = ").append(duration);
            appendLine(s, shift).append("waveform = ").append("bytes { "); { for (byte k : waveform) { int b = (int)k & 255; s.append(HEX_CHARACTERS[b >> 4]).append(HEX_CHARACTERS[b & 15]).append(' '); } } s.append('}');
            appendLine(s, shift).append("caption = ").append(caption);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InputMessageLocation extends InputMessageContent {
        public Location location;

        public InputMessageLocation() {
        }

        public InputMessageLocation(Location location) {
            this.location = location;
        }

        public static final int CONSTRUCTOR = 2121763042;

        @Override
        public int getConstructor() {
            return 2121763042;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessageLocation").append(" {");
            shift += 2;
            appendLine(s, shift).append("location = "); location.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InputMessageVenue extends InputMessageContent {
        public Venue venue;

        public InputMessageVenue() {
        }

        public InputMessageVenue(Venue venue) {
            this.venue = venue;
        }

        public static final int CONSTRUCTOR = 1447926269;

        @Override
        public int getConstructor() {
            return 1447926269;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessageVenue").append(" {");
            shift += 2;
            appendLine(s, shift).append("venue = "); venue.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InputMessageContact extends InputMessageContent {
        public Contact contact;

        public InputMessageContact() {
        }

        public InputMessageContact(Contact contact) {
            this.contact = contact;
        }

        public static final int CONSTRUCTOR = -982446849;

        @Override
        public int getConstructor() {
            return -982446849;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessageContact").append(" {");
            shift += 2;
            appendLine(s, shift).append("contact = "); contact.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InputMessageGame extends InputMessageContent {
        public int botUserId;
        public String gameShortName;

        public InputMessageGame() {
        }

        public InputMessageGame(int botUserId, String gameShortName) {
            this.botUserId = botUserId;
            this.gameShortName = gameShortName;
        }

        public static final int CONSTRUCTOR = 1989596156;

        @Override
        public int getConstructor() {
            return 1989596156;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessageGame").append(" {");
            shift += 2;
            appendLine(s, shift).append("botUserId = ").append(botUserId);
            appendLine(s, shift).append("gameShortName = ").append(gameShortName);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InputMessageForwarded extends InputMessageContent {
        public long fromChatId;
        public int messageId;

        public InputMessageForwarded() {
        }

        public InputMessageForwarded(long fromChatId, int messageId) {
            this.fromChatId = fromChatId;
            this.messageId = messageId;
        }

        public static final int CONSTRUCTOR = 863879612;

        @Override
        public int getConstructor() {
            return 863879612;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessageForwarded").append(" {");
            shift += 2;
            appendLine(s, shift).append("fromChatId = ").append(fromChatId);
            appendLine(s, shift).append("messageId = ").append(messageId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class InputThumb extends TLObject {
        public String path;
        public int width;
        public int height;

        public InputThumb() {
        }

        public InputThumb(String path, int width, int height) {
            this.path = path;
            this.width = width;
            this.height = height;
        }

        public static final int CONSTRUCTOR = -227565803;

        @Override
        public int getConstructor() {
            return -227565803;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputThumb").append(" {");
            shift += 2;
            appendLine(s, shift).append("path = ").append(path);
            appendLine(s, shift).append("width = ").append(width);
            appendLine(s, shift).append("height = ").append(height);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class KeyboardButton extends TLObject {
        public String text;
        public KeyboardButtonType type;

        public KeyboardButton() {
        }

        public KeyboardButton(String text, KeyboardButtonType type) {
            this.text = text;
            this.type = type;
        }

        public static final int CONSTRUCTOR = -2069836172;

        @Override
        public int getConstructor() {
            return -2069836172;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("KeyboardButton").append(" {");
            shift += 2;
            appendLine(s, shift).append("text = ").append(text);
            appendLine(s, shift).append("type = "); type.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public abstract static class KeyboardButtonType extends TLObject {
    }

    public static class KeyboardButtonTypeText extends KeyboardButtonType {

        public KeyboardButtonTypeText() {
        }

        public static final int CONSTRUCTOR = -1773037256;

        @Override
        public int getConstructor() {
            return -1773037256;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("KeyboardButtonTypeText").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class KeyboardButtonTypeRequestPhoneNumber extends KeyboardButtonType {

        public KeyboardButtonTypeRequestPhoneNumber() {
        }

        public static final int CONSTRUCTOR = -1529235527;

        @Override
        public int getConstructor() {
            return -1529235527;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("KeyboardButtonTypeRequestPhoneNumber").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class KeyboardButtonTypeRequestLocation extends KeyboardButtonType {

        public KeyboardButtonTypeRequestLocation() {
        }

        public static final int CONSTRUCTOR = -125661955;

        @Override
        public int getConstructor() {
            return -125661955;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("KeyboardButtonTypeRequestLocation").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public abstract static class LinkState extends TLObject {
    }

    public static class LinkStateNone extends LinkState {

        public LinkStateNone() {
        }

        public static final int CONSTRUCTOR = 951430287;

        @Override
        public int getConstructor() {
            return 951430287;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("LinkStateNone").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class LinkStateKnowsPhoneNumber extends LinkState {

        public LinkStateKnowsPhoneNumber() {
        }

        public static final int CONSTRUCTOR = 380898199;

        @Override
        public int getConstructor() {
            return 380898199;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("LinkStateKnowsPhoneNumber").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class LinkStateContact extends LinkState {

        public LinkStateContact() {
        }

        public static final int CONSTRUCTOR = -731324681;

        @Override
        public int getConstructor() {
            return -731324681;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("LinkStateContact").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class Location extends TLObject {
        public double latitude;
        public double longitude;

        public Location() {
        }

        public Location(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public static final int CONSTRUCTOR = 749028016;

        @Override
        public int getConstructor() {
            return 749028016;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("Location").append(" {");
            shift += 2;
            appendLine(s, shift).append("latitude = ").append(latitude);
            appendLine(s, shift).append("longitude = ").append(longitude);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class Message extends TLObject {
        public int id;
        public int senderUserId;
        public long chatId;
        public MessageSendState sendState;
        public boolean canBeEdited;
        public boolean canBeDeleted;
        public boolean isPost;
        public int date;
        public int editDate;
        public MessageForwardInfo forwardInfo;
        public int replyToMessageId;
        public int viaBotUserId;
        public int views;
        public MessageContent content;
        public ReplyMarkup replyMarkup;

        public Message() {
        }

        public Message(int id, int senderUserId, long chatId, MessageSendState sendState, boolean canBeEdited, boolean canBeDeleted, boolean isPost, int date, int editDate, MessageForwardInfo forwardInfo, int replyToMessageId, int viaBotUserId, int views, MessageContent content, ReplyMarkup replyMarkup) {
            this.id = id;
            this.senderUserId = senderUserId;
            this.chatId = chatId;
            this.sendState = sendState;
            this.canBeEdited = canBeEdited;
            this.canBeDeleted = canBeDeleted;
            this.isPost = isPost;
            this.date = date;
            this.editDate = editDate;
            this.forwardInfo = forwardInfo;
            this.replyToMessageId = replyToMessageId;
            this.viaBotUserId = viaBotUserId;
            this.views = views;
            this.content = content;
            this.replyMarkup = replyMarkup;
        }

        public static final int CONSTRUCTOR = 1284920704;

        @Override
        public int getConstructor() {
            return 1284920704;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("Message").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("senderUserId = ").append(senderUserId);
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("sendState = "); sendState.toStringBuilder(shift, s);
            appendLine(s, shift).append("canBeEdited = ").append(canBeEdited);
            appendLine(s, shift).append("canBeDeleted = ").append(canBeDeleted);
            appendLine(s, shift).append("isPost = ").append(isPost);
            appendLine(s, shift).append("date = ").append(date);
            appendLine(s, shift).append("editDate = ").append(editDate);
            appendLine(s, shift).append("forwardInfo = "); forwardInfo.toStringBuilder(shift, s);
            appendLine(s, shift).append("replyToMessageId = ").append(replyToMessageId);
            appendLine(s, shift).append("viaBotUserId = ").append(viaBotUserId);
            appendLine(s, shift).append("views = ").append(views);
            appendLine(s, shift).append("content = "); content.toStringBuilder(shift, s);
            appendLine(s, shift).append("replyMarkup = "); replyMarkup.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public abstract static class MessageContent extends TLObject {
    }

    public static class MessageText extends MessageContent {
        public String text;
        public MessageEntity[] entities;
        public WebPage webPage;

        public MessageText() {
        }

        public MessageText(String text, MessageEntity[] entities, WebPage webPage) {
            this.text = text;
            this.entities = entities;
            this.webPage = webPage;
        }

        public static final int CONSTRUCTOR = 964064453;

        @Override
        public int getConstructor() {
            return 964064453;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageText").append(" {");
            shift += 2;
            appendLine(s, shift).append("text = ").append(text);
            appendLine(s, shift).append("entities = ").append(Arrays.toString(entities));
            appendLine(s, shift).append("webPage = "); webPage.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageAnimation extends MessageContent {
        public Animation animation;
        public String caption;

        public MessageAnimation() {
        }

        public MessageAnimation(Animation animation, String caption) {
            this.animation = animation;
            this.caption = caption;
        }

        public static final int CONSTRUCTOR = -49928664;

        @Override
        public int getConstructor() {
            return -49928664;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageAnimation").append(" {");
            shift += 2;
            appendLine(s, shift).append("animation = "); animation.toStringBuilder(shift, s);
            appendLine(s, shift).append("caption = ").append(caption);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageAudio extends MessageContent {
        public Audio audio;
        public String caption;
        public boolean isListened;

        public MessageAudio() {
        }

        public MessageAudio(Audio audio, String caption, boolean isListened) {
            this.audio = audio;
            this.caption = caption;
            this.isListened = isListened;
        }

        public static final int CONSTRUCTOR = 226441982;

        @Override
        public int getConstructor() {
            return 226441982;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageAudio").append(" {");
            shift += 2;
            appendLine(s, shift).append("audio = "); audio.toStringBuilder(shift, s);
            appendLine(s, shift).append("caption = ").append(caption);
            appendLine(s, shift).append("isListened = ").append(isListened);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageDocument extends MessageContent {
        public Document document;
        public String caption;

        public MessageDocument() {
        }

        public MessageDocument(Document document, String caption) {
            this.document = document;
            this.caption = caption;
        }

        public static final int CONSTRUCTOR = 1630748077;

        @Override
        public int getConstructor() {
            return 1630748077;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageDocument").append(" {");
            shift += 2;
            appendLine(s, shift).append("document = "); document.toStringBuilder(shift, s);
            appendLine(s, shift).append("caption = ").append(caption);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessagePhoto extends MessageContent {
        public Photo photo;
        public String caption;

        public MessagePhoto() {
        }

        public MessagePhoto(Photo photo, String caption) {
            this.photo = photo;
            this.caption = caption;
        }

        public static final int CONSTRUCTOR = 1469704153;

        @Override
        public int getConstructor() {
            return 1469704153;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessagePhoto").append(" {");
            shift += 2;
            appendLine(s, shift).append("photo = "); photo.toStringBuilder(shift, s);
            appendLine(s, shift).append("caption = ").append(caption);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageSticker extends MessageContent {
        public Sticker sticker;

        public MessageSticker() {
        }

        public MessageSticker(Sticker sticker) {
            this.sticker = sticker;
        }

        public static final int CONSTRUCTOR = 1779022878;

        @Override
        public int getConstructor() {
            return 1779022878;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageSticker").append(" {");
            shift += 2;
            appendLine(s, shift).append("sticker = "); sticker.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageVideo extends MessageContent {
        public Video video;
        public String caption;

        public MessageVideo() {
        }

        public MessageVideo(Video video, String caption) {
            this.video = video;
            this.caption = caption;
        }

        public static final int CONSTRUCTOR = 1267590961;

        @Override
        public int getConstructor() {
            return 1267590961;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageVideo").append(" {");
            shift += 2;
            appendLine(s, shift).append("video = "); video.toStringBuilder(shift, s);
            appendLine(s, shift).append("caption = ").append(caption);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageVoice extends MessageContent {
        public Voice voice;
        public String caption;
        public boolean isListened;

        public MessageVoice() {
        }

        public MessageVoice(Voice voice, String caption, boolean isListened) {
            this.voice = voice;
            this.caption = caption;
            this.isListened = isListened;
        }

        public static final int CONSTRUCTOR = -631462405;

        @Override
        public int getConstructor() {
            return -631462405;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageVoice").append(" {");
            shift += 2;
            appendLine(s, shift).append("voice = "); voice.toStringBuilder(shift, s);
            appendLine(s, shift).append("caption = ").append(caption);
            appendLine(s, shift).append("isListened = ").append(isListened);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageLocation extends MessageContent {
        public Location location;

        public MessageLocation() {
        }

        public MessageLocation(Location location) {
            this.location = location;
        }

        public static final int CONSTRUCTOR = 161545583;

        @Override
        public int getConstructor() {
            return 161545583;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageLocation").append(" {");
            shift += 2;
            appendLine(s, shift).append("location = "); location.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageVenue extends MessageContent {
        public Venue venue;

        public MessageVenue() {
        }

        public MessageVenue(Venue venue) {
            this.venue = venue;
        }

        public static final int CONSTRUCTOR = -2146492043;

        @Override
        public int getConstructor() {
            return -2146492043;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageVenue").append(" {");
            shift += 2;
            appendLine(s, shift).append("venue = "); venue.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageContact extends MessageContent {
        public Contact contact;

        public MessageContact() {
        }

        public MessageContact(Contact contact) {
            this.contact = contact;
        }

        public static final int CONSTRUCTOR = -512684966;

        @Override
        public int getConstructor() {
            return -512684966;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageContact").append(" {");
            shift += 2;
            appendLine(s, shift).append("contact = "); contact.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageGame extends MessageContent {
        public Game game;

        public MessageGame() {
        }

        public MessageGame(Game game) {
            this.game = game;
        }

        public static final int CONSTRUCTOR = -69441162;

        @Override
        public int getConstructor() {
            return -69441162;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageGame").append(" {");
            shift += 2;
            appendLine(s, shift).append("game = "); game.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageGroupChatCreate extends MessageContent {
        public String title;
        public User[] members;

        public MessageGroupChatCreate() {
        }

        public MessageGroupChatCreate(String title, User[] members) {
            this.title = title;
            this.members = members;
        }

        public static final int CONSTRUCTOR = 201696375;

        @Override
        public int getConstructor() {
            return 201696375;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageGroupChatCreate").append(" {");
            shift += 2;
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("members = ").append(Arrays.toString(members));
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageChannelChatCreate extends MessageContent {
        public String title;

        public MessageChannelChatCreate() {
        }

        public MessageChannelChatCreate(String title) {
            this.title = title;
        }

        public static final int CONSTRUCTOR = 554984181;

        @Override
        public int getConstructor() {
            return 554984181;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageChannelChatCreate").append(" {");
            shift += 2;
            appendLine(s, shift).append("title = ").append(title);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageChatChangeTitle extends MessageContent {
        public String title;

        public MessageChatChangeTitle() {
        }

        public MessageChatChangeTitle(String title) {
            this.title = title;
        }

        public static final int CONSTRUCTOR = 748272449;

        @Override
        public int getConstructor() {
            return 748272449;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageChatChangeTitle").append(" {");
            shift += 2;
            appendLine(s, shift).append("title = ").append(title);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageChatChangePhoto extends MessageContent {
        public Photo photo;

        public MessageChatChangePhoto() {
        }

        public MessageChatChangePhoto(Photo photo) {
            this.photo = photo;
        }

        public static final int CONSTRUCTOR = 319630249;

        @Override
        public int getConstructor() {
            return 319630249;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageChatChangePhoto").append(" {");
            shift += 2;
            appendLine(s, shift).append("photo = "); photo.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageChatDeletePhoto extends MessageContent {

        public MessageChatDeletePhoto() {
        }

        public static final int CONSTRUCTOR = -184374809;

        @Override
        public int getConstructor() {
            return -184374809;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageChatDeletePhoto").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageChatAddMembers extends MessageContent {
        public User[] members;

        public MessageChatAddMembers() {
        }

        public MessageChatAddMembers(User[] members) {
            this.members = members;
        }

        public static final int CONSTRUCTOR = 423474768;

        @Override
        public int getConstructor() {
            return 423474768;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageChatAddMembers").append(" {");
            shift += 2;
            appendLine(s, shift).append("members = ").append(Arrays.toString(members));
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageChatJoinByLink extends MessageContent {
        public int inviterUserId;

        public MessageChatJoinByLink() {
        }

        public MessageChatJoinByLink(int inviterUserId) {
            this.inviterUserId = inviterUserId;
        }

        public static final int CONSTRUCTOR = 1774397587;

        @Override
        public int getConstructor() {
            return 1774397587;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageChatJoinByLink").append(" {");
            shift += 2;
            appendLine(s, shift).append("inviterUserId = ").append(inviterUserId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageChatDeleteMember extends MessageContent {
        public User user;

        public MessageChatDeleteMember() {
        }

        public MessageChatDeleteMember(User user) {
            this.user = user;
        }

        public static final int CONSTRUCTOR = 640851473;

        @Override
        public int getConstructor() {
            return 640851473;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageChatDeleteMember").append(" {");
            shift += 2;
            appendLine(s, shift).append("user = "); user.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageChatMigrateTo extends MessageContent {
        public int channelId;

        public MessageChatMigrateTo() {
        }

        public MessageChatMigrateTo(int channelId) {
            this.channelId = channelId;
        }

        public static final int CONSTRUCTOR = -850335744;

        @Override
        public int getConstructor() {
            return -850335744;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageChatMigrateTo").append(" {");
            shift += 2;
            appendLine(s, shift).append("channelId = ").append(channelId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageChatMigrateFrom extends MessageContent {
        public String title;
        public int groupId;

        public MessageChatMigrateFrom() {
        }

        public MessageChatMigrateFrom(String title, int groupId) {
            this.title = title;
            this.groupId = groupId;
        }

        public static final int CONSTRUCTOR = -2130688522;

        @Override
        public int getConstructor() {
            return -2130688522;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageChatMigrateFrom").append(" {");
            shift += 2;
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("groupId = ").append(groupId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessagePinMessage extends MessageContent {
        public int messageId;

        public MessagePinMessage() {
        }

        public MessagePinMessage(int messageId) {
            this.messageId = messageId;
        }

        public static final int CONSTRUCTOR = 206180326;

        @Override
        public int getConstructor() {
            return 206180326;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessagePinMessage").append(" {");
            shift += 2;
            appendLine(s, shift).append("messageId = ").append(messageId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageGameScore extends MessageContent {
        public int gameMessageId;
        public long gameId;
        public int score;

        public MessageGameScore() {
        }

        public MessageGameScore(int gameMessageId, long gameId, int score) {
            this.gameMessageId = gameMessageId;
            this.gameId = gameId;
            this.score = score;
        }

        public static final int CONSTRUCTOR = 535384543;

        @Override
        public int getConstructor() {
            return 535384543;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageGameScore").append(" {");
            shift += 2;
            appendLine(s, shift).append("gameMessageId = ").append(gameMessageId);
            appendLine(s, shift).append("gameId = ").append(gameId);
            appendLine(s, shift).append("score = ").append(score);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageUnsupported extends MessageContent {

        public MessageUnsupported() {
        }

        public static final int CONSTRUCTOR = -1816726139;

        @Override
        public int getConstructor() {
            return -1816726139;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageUnsupported").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public abstract static class MessageEntity extends TLObject {
    }

    public static class MessageEntityMention extends MessageEntity {
        public int offset;
        public int length;

        public MessageEntityMention() {
        }

        public MessageEntityMention(int offset, int length) {
            this.offset = offset;
            this.length = length;
        }

        public static final int CONSTRUCTOR = -100378723;

        @Override
        public int getConstructor() {
            return -100378723;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageEntityMention").append(" {");
            shift += 2;
            appendLine(s, shift).append("offset = ").append(offset);
            appendLine(s, shift).append("length = ").append(length);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageEntityHashtag extends MessageEntity {
        public int offset;
        public int length;

        public MessageEntityHashtag() {
        }

        public MessageEntityHashtag(int offset, int length) {
            this.offset = offset;
            this.length = length;
        }

        public static final int CONSTRUCTOR = 1868782349;

        @Override
        public int getConstructor() {
            return 1868782349;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageEntityHashtag").append(" {");
            shift += 2;
            appendLine(s, shift).append("offset = ").append(offset);
            appendLine(s, shift).append("length = ").append(length);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageEntityBotCommand extends MessageEntity {
        public int offset;
        public int length;

        public MessageEntityBotCommand() {
        }

        public MessageEntityBotCommand(int offset, int length) {
            this.offset = offset;
            this.length = length;
        }

        public static final int CONSTRUCTOR = 1827637959;

        @Override
        public int getConstructor() {
            return 1827637959;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageEntityBotCommand").append(" {");
            shift += 2;
            appendLine(s, shift).append("offset = ").append(offset);
            appendLine(s, shift).append("length = ").append(length);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageEntityUrl extends MessageEntity {
        public int offset;
        public int length;

        public MessageEntityUrl() {
        }

        public MessageEntityUrl(int offset, int length) {
            this.offset = offset;
            this.length = length;
        }

        public static final int CONSTRUCTOR = 1859134776;

        @Override
        public int getConstructor() {
            return 1859134776;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageEntityUrl").append(" {");
            shift += 2;
            appendLine(s, shift).append("offset = ").append(offset);
            appendLine(s, shift).append("length = ").append(length);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageEntityEmail extends MessageEntity {
        public int offset;
        public int length;

        public MessageEntityEmail() {
        }

        public MessageEntityEmail(int offset, int length) {
            this.offset = offset;
            this.length = length;
        }

        public static final int CONSTRUCTOR = 1692693954;

        @Override
        public int getConstructor() {
            return 1692693954;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageEntityEmail").append(" {");
            shift += 2;
            appendLine(s, shift).append("offset = ").append(offset);
            appendLine(s, shift).append("length = ").append(length);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageEntityBold extends MessageEntity {
        public int offset;
        public int length;

        public MessageEntityBold() {
        }

        public MessageEntityBold(int offset, int length) {
            this.offset = offset;
            this.length = length;
        }

        public static final int CONSTRUCTOR = -1117713463;

        @Override
        public int getConstructor() {
            return -1117713463;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageEntityBold").append(" {");
            shift += 2;
            appendLine(s, shift).append("offset = ").append(offset);
            appendLine(s, shift).append("length = ").append(length);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageEntityItalic extends MessageEntity {
        public int offset;
        public int length;

        public MessageEntityItalic() {
        }

        public MessageEntityItalic(int offset, int length) {
            this.offset = offset;
            this.length = length;
        }

        public static final int CONSTRUCTOR = -2106619040;

        @Override
        public int getConstructor() {
            return -2106619040;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageEntityItalic").append(" {");
            shift += 2;
            appendLine(s, shift).append("offset = ").append(offset);
            appendLine(s, shift).append("length = ").append(length);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageEntityCode extends MessageEntity {
        public int offset;
        public int length;

        public MessageEntityCode() {
        }

        public MessageEntityCode(int offset, int length) {
            this.offset = offset;
            this.length = length;
        }

        public static final int CONSTRUCTOR = 681706865;

        @Override
        public int getConstructor() {
            return 681706865;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageEntityCode").append(" {");
            shift += 2;
            appendLine(s, shift).append("offset = ").append(offset);
            appendLine(s, shift).append("length = ").append(length);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageEntityPre extends MessageEntity {
        public int offset;
        public int length;

        public MessageEntityPre() {
        }

        public MessageEntityPre(int offset, int length) {
            this.offset = offset;
            this.length = length;
        }

        public static final int CONSTRUCTOR = -953571395;

        @Override
        public int getConstructor() {
            return -953571395;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageEntityPre").append(" {");
            shift += 2;
            appendLine(s, shift).append("offset = ").append(offset);
            appendLine(s, shift).append("length = ").append(length);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageEntityPreCode extends MessageEntity {
        public int offset;
        public int length;
        public String language;

        public MessageEntityPreCode() {
        }

        public MessageEntityPreCode(int offset, int length, String language) {
            this.offset = offset;
            this.length = length;
            this.language = language;
        }

        public static final int CONSTRUCTOR = 1764622354;

        @Override
        public int getConstructor() {
            return 1764622354;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageEntityPreCode").append(" {");
            shift += 2;
            appendLine(s, shift).append("offset = ").append(offset);
            appendLine(s, shift).append("length = ").append(length);
            appendLine(s, shift).append("language = ").append(language);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageEntityTextUrl extends MessageEntity {
        public int offset;
        public int length;
        public String url;

        public MessageEntityTextUrl() {
        }

        public MessageEntityTextUrl(int offset, int length, String url) {
            this.offset = offset;
            this.length = length;
            this.url = url;
        }

        public static final int CONSTRUCTOR = 1990644519;

        @Override
        public int getConstructor() {
            return 1990644519;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageEntityTextUrl").append(" {");
            shift += 2;
            appendLine(s, shift).append("offset = ").append(offset);
            appendLine(s, shift).append("length = ").append(length);
            appendLine(s, shift).append("url = ").append(url);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageEntityMentionName extends MessageEntity {
        public int offset;
        public int length;
        public int userId;

        public MessageEntityMentionName() {
        }

        public MessageEntityMentionName(int offset, int length, int userId) {
            this.offset = offset;
            this.length = length;
            this.userId = userId;
        }

        public static final int CONSTRUCTOR = 892193368;

        @Override
        public int getConstructor() {
            return 892193368;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageEntityMentionName").append(" {");
            shift += 2;
            appendLine(s, shift).append("offset = ").append(offset);
            appendLine(s, shift).append("length = ").append(length);
            appendLine(s, shift).append("userId = ").append(userId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public abstract static class MessageForwardInfo extends TLObject {
    }

    public static class MessageForwardedFromUser extends MessageForwardInfo {
        public int senderUserId;
        public int date;

        public MessageForwardedFromUser() {
        }

        public MessageForwardedFromUser(int senderUserId, int date) {
            this.senderUserId = senderUserId;
            this.date = date;
        }

        public static final int CONSTRUCTOR = -721966663;

        @Override
        public int getConstructor() {
            return -721966663;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageForwardedFromUser").append(" {");
            shift += 2;
            appendLine(s, shift).append("senderUserId = ").append(senderUserId);
            appendLine(s, shift).append("date = ").append(date);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageForwardedPost extends MessageForwardInfo {
        public long chatId;
        public int senderUserId;
        public int date;
        public int messageId;

        public MessageForwardedPost() {
        }

        public MessageForwardedPost(long chatId, int senderUserId, int date, int messageId) {
            this.chatId = chatId;
            this.senderUserId = senderUserId;
            this.date = date;
            this.messageId = messageId;
        }

        public static final int CONSTRUCTOR = -163738338;

        @Override
        public int getConstructor() {
            return -163738338;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageForwardedPost").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("senderUserId = ").append(senderUserId);
            appendLine(s, shift).append("date = ").append(date);
            appendLine(s, shift).append("messageId = ").append(messageId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public abstract static class MessageSendState extends TLObject {
    }

    public static class MessageIsIncoming extends MessageSendState {

        public MessageIsIncoming() {
        }

        public static final int CONSTRUCTOR = -175134344;

        @Override
        public int getConstructor() {
            return -175134344;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageIsIncoming").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageIsBeingSent extends MessageSendState {

        public MessageIsBeingSent() {
        }

        public static final int CONSTRUCTOR = 302358579;

        @Override
        public int getConstructor() {
            return 302358579;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageIsBeingSent").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageIsSuccessfullySent extends MessageSendState {

        public MessageIsSuccessfullySent() {
        }

        public static final int CONSTRUCTOR = -555213890;

        @Override
        public int getConstructor() {
            return -555213890;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageIsSuccessfullySent").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MessageIsFailedToSend extends MessageSendState {

        public MessageIsFailedToSend() {
        }

        public static final int CONSTRUCTOR = -122502139;

        @Override
        public int getConstructor() {
            return -122502139;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageIsFailedToSend").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class Messages extends TLObject {
        public int totalCount;
        public Message[] messages;

        public Messages() {
        }

        public Messages(int totalCount, Message[] messages) {
            this.totalCount = totalCount;
            this.messages = messages;
        }

        public static final int CONSTRUCTOR = 1550441659;

        @Override
        public int getConstructor() {
            return 1550441659;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("Messages").append(" {");
            shift += 2;
            appendLine(s, shift).append("totalCount = ").append(totalCount);
            appendLine(s, shift).append("messages = ").append(Arrays.toString(messages));
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class NotificationSettings extends TLObject {
        public int muteFor;
        public String sound;
        public boolean showPreview;

        public NotificationSettings() {
        }

        public NotificationSettings(int muteFor, String sound, boolean showPreview) {
            this.muteFor = muteFor;
            this.sound = sound;
            this.showPreview = showPreview;
        }

        public static final int CONSTRUCTOR = -1244785780;

        @Override
        public int getConstructor() {
            return -1244785780;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("NotificationSettings").append(" {");
            shift += 2;
            appendLine(s, shift).append("muteFor = ").append(muteFor);
            appendLine(s, shift).append("sound = ").append(sound);
            appendLine(s, shift).append("showPreview = ").append(showPreview);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public abstract static class NotificationSettingsScope extends TLObject {
    }

    public static class NotificationSettingsForChat extends NotificationSettingsScope {
        public long chatId;

        public NotificationSettingsForChat() {
        }

        public NotificationSettingsForChat(long chatId) {
            this.chatId = chatId;
        }

        public static final int CONSTRUCTOR = 1920084409;

        @Override
        public int getConstructor() {
            return 1920084409;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("NotificationSettingsForChat").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class NotificationSettingsForPrivateChats extends NotificationSettingsScope {

        public NotificationSettingsForPrivateChats() {
        }

        public static final int CONSTRUCTOR = 792026226;

        @Override
        public int getConstructor() {
            return 792026226;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("NotificationSettingsForPrivateChats").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class NotificationSettingsForGroupChats extends NotificationSettingsScope {

        public NotificationSettingsForGroupChats() {
        }

        public static final int CONSTRUCTOR = -1019160145;

        @Override
        public int getConstructor() {
            return -1019160145;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("NotificationSettingsForGroupChats").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class NotificationSettingsForAllChats extends NotificationSettingsScope {

        public NotificationSettingsForAllChats() {
        }

        public static final int CONSTRUCTOR = 2121050176;

        @Override
        public int getConstructor() {
            return 2121050176;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("NotificationSettingsForAllChats").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class Ok extends TLObject {

        public Ok() {
        }

        public static final int CONSTRUCTOR = -722616727;

        @Override
        public int getConstructor() {
            return -722616727;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("Ok").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public abstract static class OptionValue extends TLObject {
    }

    public static class OptionBoolean extends OptionValue {
        public boolean value;

        public OptionBoolean() {
        }

        public OptionBoolean(boolean value) {
            this.value = value;
        }

        public static final int CONSTRUCTOR = 280624660;

        @Override
        public int getConstructor() {
            return 280624660;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("OptionBoolean").append(" {");
            shift += 2;
            appendLine(s, shift).append("value = ").append(value);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class OptionEmpty extends OptionValue {

        public OptionEmpty() {
        }

        public static final int CONSTRUCTOR = 1025799436;

        @Override
        public int getConstructor() {
            return 1025799436;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("OptionEmpty").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class OptionInteger extends OptionValue {
        public int value;

        public OptionInteger() {
        }

        public OptionInteger(int value) {
            this.value = value;
        }

        public static final int CONSTRUCTOR = 1383938450;

        @Override
        public int getConstructor() {
            return 1383938450;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("OptionInteger").append(" {");
            shift += 2;
            appendLine(s, shift).append("value = ").append(value);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class OptionString extends OptionValue {
        public String value;

        public OptionString() {
        }

        public OptionString(String value) {
            this.value = value;
        }

        public static final int CONSTRUCTOR = -841614037;

        @Override
        public int getConstructor() {
            return -841614037;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("OptionString").append(" {");
            shift += 2;
            appendLine(s, shift).append("value = ").append(value);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class Photo extends TLObject {
        public long id;
        public PhotoSize[] sizes;

        public Photo() {
        }

        public Photo(long id, PhotoSize[] sizes) {
            this.id = id;
            this.sizes = sizes;
        }

        public static final int CONSTRUCTOR = 811378213;

        @Override
        public int getConstructor() {
            return 811378213;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("Photo").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("sizes = ").append(Arrays.toString(sizes));
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class PhotoSize extends TLObject {
        public String type;
        public File photo;
        public int width;
        public int height;

        public PhotoSize() {
        }

        public PhotoSize(String type, File photo, int width, int height) {
            this.type = type;
            this.photo = photo;
            this.width = width;
            this.height = height;
        }

        public static final int CONSTRUCTOR = -796190918;

        @Override
        public int getConstructor() {
            return -796190918;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("PhotoSize").append(" {");
            shift += 2;
            appendLine(s, shift).append("type = ").append(type);
            appendLine(s, shift).append("photo = "); photo.toStringBuilder(shift, s);
            appendLine(s, shift).append("width = ").append(width);
            appendLine(s, shift).append("height = ").append(height);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ProfilePhoto extends TLObject {
        public long id;
        public File small;
        public File big;

        public ProfilePhoto() {
        }

        public ProfilePhoto(long id, File small, File big) {
            this.id = id;
            this.small = small;
            this.big = big;
        }

        public static final int CONSTRUCTOR = -1954106867;

        @Override
        public int getConstructor() {
            return -1954106867;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ProfilePhoto").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("small = "); small.toStringBuilder(shift, s);
            appendLine(s, shift).append("big = "); big.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public abstract static class ReplyMarkup extends TLObject {
    }

    public static class ReplyMarkupHideKeyboard extends ReplyMarkup {
        public boolean personal;

        public ReplyMarkupHideKeyboard() {
        }

        public ReplyMarkupHideKeyboard(boolean personal) {
            this.personal = personal;
        }

        public static final int CONSTRUCTOR = 1614435429;

        @Override
        public int getConstructor() {
            return 1614435429;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ReplyMarkupHideKeyboard").append(" {");
            shift += 2;
            appendLine(s, shift).append("personal = ").append(personal);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ReplyMarkupForceReply extends ReplyMarkup {
        public boolean personal;

        public ReplyMarkupForceReply() {
        }

        public ReplyMarkupForceReply(boolean personal) {
            this.personal = personal;
        }

        public static final int CONSTRUCTOR = -1880611604;

        @Override
        public int getConstructor() {
            return -1880611604;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ReplyMarkupForceReply").append(" {");
            shift += 2;
            appendLine(s, shift).append("personal = ").append(personal);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ReplyMarkupShowKeyboard extends ReplyMarkup {
        public KeyboardButton[][] rows;
        public boolean resizeKeyboard;
        public boolean oneTime;
        public boolean personal;

        public ReplyMarkupShowKeyboard() {
        }

        public ReplyMarkupShowKeyboard(KeyboardButton[][] rows, boolean resizeKeyboard, boolean oneTime, boolean personal) {
            this.rows = rows;
            this.resizeKeyboard = resizeKeyboard;
            this.oneTime = oneTime;
            this.personal = personal;
        }

        public static final int CONSTRUCTOR = -840982893;

        @Override
        public int getConstructor() {
            return -840982893;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ReplyMarkupShowKeyboard").append(" {");
            shift += 2;
            appendLine(s, shift).append("rows = ").append(Arrays.deepToString(rows));
            appendLine(s, shift).append("resizeKeyboard = ").append(resizeKeyboard);
            appendLine(s, shift).append("oneTime = ").append(oneTime);
            appendLine(s, shift).append("personal = ").append(personal);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ReplyMarkupInlineKeyboard extends ReplyMarkup {
        public InlineKeyboardButton[][] rows;

        public ReplyMarkupInlineKeyboard() {
        }

        public ReplyMarkupInlineKeyboard(InlineKeyboardButton[][] rows) {
            this.rows = rows;
        }

        public static final int CONSTRUCTOR = -619317658;

        @Override
        public int getConstructor() {
            return -619317658;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ReplyMarkupInlineKeyboard").append(" {");
            shift += 2;
            appendLine(s, shift).append("rows = ").append(Arrays.deepToString(rows));
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public abstract static class SearchMessagesFilter extends TLObject {
    }

    public static class SearchMessagesFilterEmpty extends SearchMessagesFilter {

        public SearchMessagesFilterEmpty() {
        }

        public static final int CONSTRUCTOR = -869395657;

        @Override
        public int getConstructor() {
            return -869395657;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchMessagesFilterEmpty").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SearchMessagesFilterAnimation extends SearchMessagesFilter {

        public SearchMessagesFilterAnimation() {
        }

        public static final int CONSTRUCTOR = -155713339;

        @Override
        public int getConstructor() {
            return -155713339;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchMessagesFilterAnimation").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SearchMessagesFilterAudio extends SearchMessagesFilter {

        public SearchMessagesFilterAudio() {
        }

        public static final int CONSTRUCTOR = 867505275;

        @Override
        public int getConstructor() {
            return 867505275;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchMessagesFilterAudio").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SearchMessagesFilterDocument extends SearchMessagesFilter {

        public SearchMessagesFilterDocument() {
        }

        public static final int CONSTRUCTOR = 1526331215;

        @Override
        public int getConstructor() {
            return 1526331215;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchMessagesFilterDocument").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SearchMessagesFilterPhoto extends SearchMessagesFilter {

        public SearchMessagesFilterPhoto() {
        }

        public static final int CONSTRUCTOR = 925932293;

        @Override
        public int getConstructor() {
            return 925932293;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchMessagesFilterPhoto").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SearchMessagesFilterVideo extends SearchMessagesFilter {

        public SearchMessagesFilterVideo() {
        }

        public static final int CONSTRUCTOR = 115538222;

        @Override
        public int getConstructor() {
            return 115538222;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchMessagesFilterVideo").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SearchMessagesFilterVoice extends SearchMessagesFilter {

        public SearchMessagesFilterVoice() {
        }

        public static final int CONSTRUCTOR = 1123427595;

        @Override
        public int getConstructor() {
            return 1123427595;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchMessagesFilterVoice").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SearchMessagesFilterPhotoAndVideo extends SearchMessagesFilter {

        public SearchMessagesFilterPhotoAndVideo() {
        }

        public static final int CONSTRUCTOR = 1352130963;

        @Override
        public int getConstructor() {
            return 1352130963;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchMessagesFilterPhotoAndVideo").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SearchMessagesFilterUrl extends SearchMessagesFilter {

        public SearchMessagesFilterUrl() {
        }

        public static final int CONSTRUCTOR = -1828724341;

        @Override
        public int getConstructor() {
            return -1828724341;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchMessagesFilterUrl").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SearchMessagesFilterChatPhoto extends SearchMessagesFilter {

        public SearchMessagesFilterChatPhoto() {
        }

        public static final int CONSTRUCTOR = -1247751329;

        @Override
        public int getConstructor() {
            return -1247751329;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchMessagesFilterChatPhoto").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SecretChat extends TLObject {
        public int id;
        public int userId;
        public int state;
        public int ttl;

        public SecretChat() {
        }

        public SecretChat(int id, int userId, int state, int ttl) {
            this.id = id;
            this.userId = userId;
            this.state = state;
            this.ttl = ttl;
        }

        public static final int CONSTRUCTOR = 1575069994;

        @Override
        public int getConstructor() {
            return 1575069994;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SecretChat").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("userId = ").append(userId);
            appendLine(s, shift).append("state = ").append(state);
            appendLine(s, shift).append("ttl = ").append(ttl);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SecretChatFull extends TLObject {
        public SecretChat secretChat;
        public String keyHash;

        public SecretChatFull() {
        }

        public SecretChatFull(SecretChat secretChat, String keyHash) {
            this.secretChat = secretChat;
            this.keyHash = keyHash;
        }

        public static final int CONSTRUCTOR = 230450236;

        @Override
        public int getConstructor() {
            return 230450236;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SecretChatFull").append(" {");
            shift += 2;
            appendLine(s, shift).append("secretChat = "); secretChat.toStringBuilder(shift, s);
            appendLine(s, shift).append("keyHash = ").append(keyHash);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public abstract static class SendMessageAction extends TLObject {
    }

    public static class SendMessageTypingAction extends SendMessageAction {

        public SendMessageTypingAction() {
        }

        public static final int CONSTRUCTOR = 381645902;

        @Override
        public int getConstructor() {
            return 381645902;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SendMessageTypingAction").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SendMessageCancelAction extends SendMessageAction {

        public SendMessageCancelAction() {
        }

        public static final int CONSTRUCTOR = -44119819;

        @Override
        public int getConstructor() {
            return -44119819;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SendMessageCancelAction").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SendMessageRecordVideoAction extends SendMessageAction {

        public SendMessageRecordVideoAction() {
        }

        public static final int CONSTRUCTOR = -1584933265;

        @Override
        public int getConstructor() {
            return -1584933265;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SendMessageRecordVideoAction").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SendMessageUploadVideoAction extends SendMessageAction {
        public int progress;

        public SendMessageUploadVideoAction() {
        }

        public SendMessageUploadVideoAction(int progress) {
            this.progress = progress;
        }

        public static final int CONSTRUCTOR = -378127636;

        @Override
        public int getConstructor() {
            return -378127636;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SendMessageUploadVideoAction").append(" {");
            shift += 2;
            appendLine(s, shift).append("progress = ").append(progress);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SendMessageRecordVoiceAction extends SendMessageAction {

        public SendMessageRecordVoiceAction() {
        }

        public static final int CONSTRUCTOR = -1470755762;

        @Override
        public int getConstructor() {
            return -1470755762;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SendMessageRecordVoiceAction").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SendMessageUploadVoiceAction extends SendMessageAction {
        public int progress;

        public SendMessageUploadVoiceAction() {
        }

        public SendMessageUploadVoiceAction(int progress) {
            this.progress = progress;
        }

        public static final int CONSTRUCTOR = 64055712;

        @Override
        public int getConstructor() {
            return 64055712;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SendMessageUploadVoiceAction").append(" {");
            shift += 2;
            appendLine(s, shift).append("progress = ").append(progress);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SendMessageUploadPhotoAction extends SendMessageAction {
        public int progress;

        public SendMessageUploadPhotoAction() {
        }

        public SendMessageUploadPhotoAction(int progress) {
            this.progress = progress;
        }

        public static final int CONSTRUCTOR = -774682074;

        @Override
        public int getConstructor() {
            return -774682074;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SendMessageUploadPhotoAction").append(" {");
            shift += 2;
            appendLine(s, shift).append("progress = ").append(progress);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SendMessageUploadDocumentAction extends SendMessageAction {
        public int progress;

        public SendMessageUploadDocumentAction() {
        }

        public SendMessageUploadDocumentAction(int progress) {
            this.progress = progress;
        }

        public static final int CONSTRUCTOR = -1441998364;

        @Override
        public int getConstructor() {
            return -1441998364;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SendMessageUploadDocumentAction").append(" {");
            shift += 2;
            appendLine(s, shift).append("progress = ").append(progress);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SendMessageGeoLocationAction extends SendMessageAction {

        public SendMessageGeoLocationAction() {
        }

        public static final int CONSTRUCTOR = 393186209;

        @Override
        public int getConstructor() {
            return 393186209;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SendMessageGeoLocationAction").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SendMessageChooseContactAction extends SendMessageAction {

        public SendMessageChooseContactAction() {
        }

        public static final int CONSTRUCTOR = 1653390447;

        @Override
        public int getConstructor() {
            return 1653390447;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SendMessageChooseContactAction").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SendMessageStartPlayGameAction extends SendMessageAction {

        public SendMessageStartPlayGameAction() {
        }

        public static final int CONSTRUCTOR = -2099820430;

        @Override
        public int getConstructor() {
            return -2099820430;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SendMessageStartPlayGameAction").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SendMessageStopPlayGameAction extends SendMessageAction {

        public SendMessageStopPlayGameAction() {
        }

        public static final int CONSTRUCTOR = 911329680;

        @Override
        public int getConstructor() {
            return 911329680;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SendMessageStopPlayGameAction").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class Sticker extends TLObject {
        public long setId;
        public int width;
        public int height;
        public String emoji;
        public double rating;
        public PhotoSize thumb;
        public File sticker;

        public Sticker() {
        }

        public Sticker(long setId, int width, int height, String emoji, double rating, PhotoSize thumb, File sticker) {
            this.setId = setId;
            this.width = width;
            this.height = height;
            this.emoji = emoji;
            this.rating = rating;
            this.thumb = thumb;
            this.sticker = sticker;
        }

        public static final int CONSTRUCTOR = -319806232;

        @Override
        public int getConstructor() {
            return -319806232;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("Sticker").append(" {");
            shift += 2;
            appendLine(s, shift).append("setId = ").append(setId);
            appendLine(s, shift).append("width = ").append(width);
            appendLine(s, shift).append("height = ").append(height);
            appendLine(s, shift).append("emoji = ").append(emoji);
            appendLine(s, shift).append("rating = ").append(rating);
            appendLine(s, shift).append("thumb = "); thumb.toStringBuilder(shift, s);
            appendLine(s, shift).append("sticker = "); sticker.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class StickerSet extends TLObject {
        public long id;
        public String title;
        public String name;
        public double rating;
        public boolean isInstalled;
        public boolean isEnabled;
        public boolean isOfficial;
        public Sticker[] stickers;

        public StickerSet() {
        }

        public StickerSet(long id, String title, String name, double rating, boolean isInstalled, boolean isEnabled, boolean isOfficial, Sticker[] stickers) {
            this.id = id;
            this.title = title;
            this.name = name;
            this.rating = rating;
            this.isInstalled = isInstalled;
            this.isEnabled = isEnabled;
            this.isOfficial = isOfficial;
            this.stickers = stickers;
        }

        public static final int CONSTRUCTOR = 1942998252;

        @Override
        public int getConstructor() {
            return 1942998252;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("StickerSet").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("name = ").append(name);
            appendLine(s, shift).append("rating = ").append(rating);
            appendLine(s, shift).append("isInstalled = ").append(isInstalled);
            appendLine(s, shift).append("isEnabled = ").append(isEnabled);
            appendLine(s, shift).append("isOfficial = ").append(isOfficial);
            appendLine(s, shift).append("stickers = ").append(Arrays.toString(stickers));
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class StickerSetInfo extends TLObject {
        public long id;
        public String title;
        public String name;
        public double rating;
        public boolean isInstalled;
        public boolean isEnabled;
        public boolean isOfficial;
        public int size;

        public StickerSetInfo() {
        }

        public StickerSetInfo(long id, String title, String name, double rating, boolean isInstalled, boolean isEnabled, boolean isOfficial, int size) {
            this.id = id;
            this.title = title;
            this.name = name;
            this.rating = rating;
            this.isInstalled = isInstalled;
            this.isEnabled = isEnabled;
            this.isOfficial = isOfficial;
            this.size = size;
        }

        public static final int CONSTRUCTOR = -1268445223;

        @Override
        public int getConstructor() {
            return -1268445223;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("StickerSetInfo").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("name = ").append(name);
            appendLine(s, shift).append("rating = ").append(rating);
            appendLine(s, shift).append("isInstalled = ").append(isInstalled);
            appendLine(s, shift).append("isEnabled = ").append(isEnabled);
            appendLine(s, shift).append("isOfficial = ").append(isOfficial);
            appendLine(s, shift).append("size = ").append(size);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class StickerSets extends TLObject {
        public StickerSetInfo[] sets;

        public StickerSets() {
        }

        public StickerSets(StickerSetInfo[] sets) {
            this.sets = sets;
        }

        public static final int CONSTRUCTOR = -1141691090;

        @Override
        public int getConstructor() {
            return -1141691090;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("StickerSets").append(" {");
            shift += 2;
            appendLine(s, shift).append("sets = ").append(Arrays.toString(sets));
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class Stickers extends TLObject {
        public Sticker[] stickers;

        public Stickers() {
        }

        public Stickers(Sticker[] stickers) {
            this.stickers = stickers;
        }

        public static final int CONSTRUCTOR = 1974859260;

        @Override
        public int getConstructor() {
            return 1974859260;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("Stickers").append(" {");
            shift += 2;
            appendLine(s, shift).append("stickers = ").append(Arrays.toString(stickers));
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public abstract static class TextParseMode extends TLObject {
    }

    public static class TextParseModeMarkdown extends TextParseMode {

        public TextParseModeMarkdown() {
        }

        public static final int CONSTRUCTOR = 969225580;

        @Override
        public int getConstructor() {
            return 969225580;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("TextParseModeMarkdown").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class TextParseModeHTML extends TextParseMode {

        public TextParseModeHTML() {
        }

        public static final int CONSTRUCTOR = 1660208627;

        @Override
        public int getConstructor() {
            return 1660208627;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("TextParseModeHTML").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public abstract static class Update extends TLObject {
    }

    public static class UpdateNewMessage extends Update {
        public Message message;
        public boolean disableNotification;

        public UpdateNewMessage() {
        }

        public UpdateNewMessage(Message message, boolean disableNotification) {
            this.message = message;
            this.disableNotification = disableNotification;
        }

        public static final int CONSTRUCTOR = 1098732806;

        @Override
        public int getConstructor() {
            return 1098732806;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateNewMessage").append(" {");
            shift += 2;
            appendLine(s, shift).append("message = "); message.toStringBuilder(shift, s);
            appendLine(s, shift).append("disableNotification = ").append(disableNotification);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateMessageSendSucceeded extends Update {
        public Message message;
        public int oldMessageId;

        public UpdateMessageSendSucceeded() {
        }

        public UpdateMessageSendSucceeded(Message message, int oldMessageId) {
            this.message = message;
            this.oldMessageId = oldMessageId;
        }

        public static final int CONSTRUCTOR = -272606842;

        @Override
        public int getConstructor() {
            return -272606842;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateMessageSendSucceeded").append(" {");
            shift += 2;
            appendLine(s, shift).append("message = "); message.toStringBuilder(shift, s);
            appendLine(s, shift).append("oldMessageId = ").append(oldMessageId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateMessageSendFailed extends Update {
        public long chatId;
        public int messageId;
        public int errorCode;
        public String errorMessage;

        public UpdateMessageSendFailed() {
        }

        public UpdateMessageSendFailed(long chatId, int messageId, int errorCode, String errorMessage) {
            this.chatId = chatId;
            this.messageId = messageId;
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
        }

        public static final int CONSTRUCTOR = -1711539093;

        @Override
        public int getConstructor() {
            return -1711539093;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateMessageSendFailed").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("messageId = ").append(messageId);
            appendLine(s, shift).append("errorCode = ").append(errorCode);
            appendLine(s, shift).append("errorMessage = ").append(errorMessage);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateMessageContent extends Update {
        public long chatId;
        public int messageId;
        public MessageContent newContent;

        public UpdateMessageContent() {
        }

        public UpdateMessageContent(long chatId, int messageId, MessageContent newContent) {
            this.chatId = chatId;
            this.messageId = messageId;
            this.newContent = newContent;
        }

        public static final int CONSTRUCTOR = 561472729;

        @Override
        public int getConstructor() {
            return 561472729;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateMessageContent").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("messageId = ").append(messageId);
            appendLine(s, shift).append("newContent = "); newContent.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateMessageEdited extends Update {
        public long chatId;
        public int messageId;
        public int editDate;
        public ReplyMarkup replyMarkup;

        public UpdateMessageEdited() {
        }

        public UpdateMessageEdited(long chatId, int messageId, int editDate, ReplyMarkup replyMarkup) {
            this.chatId = chatId;
            this.messageId = messageId;
            this.editDate = editDate;
            this.replyMarkup = replyMarkup;
        }

        public static final int CONSTRUCTOR = -1312681878;

        @Override
        public int getConstructor() {
            return -1312681878;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateMessageEdited").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("messageId = ").append(messageId);
            appendLine(s, shift).append("editDate = ").append(editDate);
            appendLine(s, shift).append("replyMarkup = "); replyMarkup.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateMessageViews extends Update {
        public long chatId;
        public int messageId;
        public int views;

        public UpdateMessageViews() {
        }

        public UpdateMessageViews(long chatId, int messageId, int views) {
            this.chatId = chatId;
            this.messageId = messageId;
            this.views = views;
        }

        public static final int CONSTRUCTOR = -1812594738;

        @Override
        public int getConstructor() {
            return -1812594738;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateMessageViews").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("messageId = ").append(messageId);
            appendLine(s, shift).append("views = ").append(views);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateChat extends Update {
        public Chat chat;

        public UpdateChat() {
        }

        public UpdateChat(Chat chat) {
            this.chat = chat;
        }

        public static final int CONSTRUCTOR = -1253621217;

        @Override
        public int getConstructor() {
            return -1253621217;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateChat").append(" {");
            shift += 2;
            appendLine(s, shift).append("chat = "); chat.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateChatTopMessage extends Update {
        public long chatId;
        public Message topMessage;

        public UpdateChatTopMessage() {
        }

        public UpdateChatTopMessage(long chatId, Message topMessage) {
            this.chatId = chatId;
            this.topMessage = topMessage;
        }

        public static final int CONSTRUCTOR = 281918326;

        @Override
        public int getConstructor() {
            return 281918326;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateChatTopMessage").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("topMessage = "); topMessage.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateChatOrder extends Update {
        public long chatId;
        public long order;

        public UpdateChatOrder() {
        }

        public UpdateChatOrder(long chatId, long order) {
            this.chatId = chatId;
            this.order = order;
        }

        public static final int CONSTRUCTOR = 1977527814;

        @Override
        public int getConstructor() {
            return 1977527814;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateChatOrder").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("order = ").append(order);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateChatTitle extends Update {
        public long chatId;
        public String title;

        public UpdateChatTitle() {
        }

        public UpdateChatTitle(long chatId, String title) {
            this.chatId = chatId;
            this.title = title;
        }

        public static final int CONSTRUCTOR = 1931125386;

        @Override
        public int getConstructor() {
            return 1931125386;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateChatTitle").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("title = ").append(title);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateChatPhoto extends Update {
        public long chatId;
        public ChatPhoto photo;

        public UpdateChatPhoto() {
        }

        public UpdateChatPhoto(long chatId, ChatPhoto photo) {
            this.chatId = chatId;
            this.photo = photo;
        }

        public static final int CONSTRUCTOR = 556185369;

        @Override
        public int getConstructor() {
            return 556185369;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateChatPhoto").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("photo = "); photo.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateChatReadInbox extends Update {
        public long chatId;
        public int lastReadInboxMessageId;
        public int unreadCount;

        public UpdateChatReadInbox() {
        }

        public UpdateChatReadInbox(long chatId, int lastReadInboxMessageId, int unreadCount) {
            this.chatId = chatId;
            this.lastReadInboxMessageId = lastReadInboxMessageId;
            this.unreadCount = unreadCount;
        }

        public static final int CONSTRUCTOR = -58810942;

        @Override
        public int getConstructor() {
            return -58810942;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateChatReadInbox").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("lastReadInboxMessageId = ").append(lastReadInboxMessageId);
            appendLine(s, shift).append("unreadCount = ").append(unreadCount);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateChatReadOutbox extends Update {
        public long chatId;
        public int lastReadOutboxMessageId;

        public UpdateChatReadOutbox() {
        }

        public UpdateChatReadOutbox(long chatId, int lastReadOutboxMessageId) {
            this.chatId = chatId;
            this.lastReadOutboxMessageId = lastReadOutboxMessageId;
        }

        public static final int CONSTRUCTOR = 877103058;

        @Override
        public int getConstructor() {
            return 877103058;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateChatReadOutbox").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("lastReadOutboxMessageId = ").append(lastReadOutboxMessageId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateChatReplyMarkup extends Update {
        public long chatId;
        public int replyMarkupMessageId;

        public UpdateChatReplyMarkup() {
        }

        public UpdateChatReplyMarkup(long chatId, int replyMarkupMessageId) {
            this.chatId = chatId;
            this.replyMarkupMessageId = replyMarkupMessageId;
        }

        public static final int CONSTRUCTOR = 301018472;

        @Override
        public int getConstructor() {
            return 301018472;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateChatReplyMarkup").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("replyMarkupMessageId = ").append(replyMarkupMessageId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateChatDraftMessage extends Update {
        public long chatId;
        public DraftMessage draftMessage;

        public UpdateChatDraftMessage() {
        }

        public UpdateChatDraftMessage(long chatId, DraftMessage draftMessage) {
            this.chatId = chatId;
            this.draftMessage = draftMessage;
        }

        public static final int CONSTRUCTOR = 602781138;

        @Override
        public int getConstructor() {
            return 602781138;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateChatDraftMessage").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("draftMessage = "); draftMessage.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateNotificationSettings extends Update {
        public NotificationSettingsScope scope;
        public NotificationSettings notificationSettings;

        public UpdateNotificationSettings() {
        }

        public UpdateNotificationSettings(NotificationSettingsScope scope, NotificationSettings notificationSettings) {
            this.scope = scope;
            this.notificationSettings = notificationSettings;
        }

        public static final int CONSTRUCTOR = -1767306883;

        @Override
        public int getConstructor() {
            return -1767306883;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateNotificationSettings").append(" {");
            shift += 2;
            appendLine(s, shift).append("scope = "); scope.toStringBuilder(shift, s);
            appendLine(s, shift).append("notificationSettings = "); notificationSettings.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateDeleteMessages extends Update {
        public long chatId;
        public int[] messageIds;

        public UpdateDeleteMessages() {
        }

        public UpdateDeleteMessages(long chatId, int[] messageIds) {
            this.chatId = chatId;
            this.messageIds = messageIds;
        }

        public static final int CONSTRUCTOR = 129908480;

        @Override
        public int getConstructor() {
            return 129908480;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateDeleteMessages").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("messageIds = ").append(Arrays.toString(messageIds));
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateUserAction extends Update {
        public long chatId;
        public int userId;
        public SendMessageAction action;

        public UpdateUserAction() {
        }

        public UpdateUserAction(long chatId, int userId, SendMessageAction action) {
            this.chatId = chatId;
            this.userId = userId;
            this.action = action;
        }

        public static final int CONSTRUCTOR = 223420164;

        @Override
        public int getConstructor() {
            return 223420164;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateUserAction").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("userId = ").append(userId);
            appendLine(s, shift).append("action = "); action.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateUserStatus extends Update {
        public int userId;
        public UserStatus status;

        public UpdateUserStatus() {
        }

        public UpdateUserStatus(int userId, UserStatus status) {
            this.userId = userId;
            this.status = status;
        }

        public static final int CONSTRUCTOR = 469489699;

        @Override
        public int getConstructor() {
            return 469489699;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateUserStatus").append(" {");
            shift += 2;
            appendLine(s, shift).append("userId = ").append(userId);
            appendLine(s, shift).append("status = "); status.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateUser extends Update {
        public User user;

        public UpdateUser() {
        }

        public UpdateUser(User user) {
            this.user = user;
        }

        public static final int CONSTRUCTOR = 1183394041;

        @Override
        public int getConstructor() {
            return 1183394041;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateUser").append(" {");
            shift += 2;
            appendLine(s, shift).append("user = "); user.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateGroup extends Update {
        public Group group;

        public UpdateGroup() {
        }

        public UpdateGroup(Group group) {
            this.group = group;
        }

        public static final int CONSTRUCTOR = -301840552;

        @Override
        public int getConstructor() {
            return -301840552;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateGroup").append(" {");
            shift += 2;
            appendLine(s, shift).append("group = "); group.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateChannel extends Update {
        public Channel channel;

        public UpdateChannel() {
        }

        public UpdateChannel(Channel channel) {
            this.channel = channel;
        }

        public static final int CONSTRUCTOR = 492671396;

        @Override
        public int getConstructor() {
            return 492671396;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateChannel").append(" {");
            shift += 2;
            appendLine(s, shift).append("channel = "); channel.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateSecretChat extends Update {
        public SecretChat secretChat;

        public UpdateSecretChat() {
        }

        public UpdateSecretChat(SecretChat secretChat) {
            this.secretChat = secretChat;
        }

        public static final int CONSTRUCTOR = -1666903253;

        @Override
        public int getConstructor() {
            return -1666903253;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateSecretChat").append(" {");
            shift += 2;
            appendLine(s, shift).append("secretChat = "); secretChat.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateChannelFull extends Update {
        public ChannelFull channelFull;

        public UpdateChannelFull() {
        }

        public UpdateChannelFull(ChannelFull channelFull) {
            this.channelFull = channelFull;
        }

        public static final int CONSTRUCTOR = 1140192938;

        @Override
        public int getConstructor() {
            return 1140192938;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateChannelFull").append(" {");
            shift += 2;
            appendLine(s, shift).append("channelFull = "); channelFull.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateUserBlocked extends Update {
        public int userId;
        public boolean isBlocked;

        public UpdateUserBlocked() {
        }

        public UpdateUserBlocked(int userId, boolean isBlocked) {
            this.userId = userId;
            this.isBlocked = isBlocked;
        }

        public static final int CONSTRUCTOR = 1341545325;

        @Override
        public int getConstructor() {
            return 1341545325;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateUserBlocked").append(" {");
            shift += 2;
            appendLine(s, shift).append("userId = ").append(userId);
            appendLine(s, shift).append("isBlocked = ").append(isBlocked);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateNewAuthorization extends Update {
        public int date;
        public String device;
        public String location;

        public UpdateNewAuthorization() {
        }

        public UpdateNewAuthorization(int date, String device, String location) {
            this.date = date;
            this.device = device;
            this.location = location;
        }

        public static final int CONSTRUCTOR = -176559980;

        @Override
        public int getConstructor() {
            return -176559980;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateNewAuthorization").append(" {");
            shift += 2;
            appendLine(s, shift).append("date = ").append(date);
            appendLine(s, shift).append("device = ").append(device);
            appendLine(s, shift).append("location = ").append(location);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateFileProgress extends Update {
        public int fileId;
        public int size;
        public int ready;

        public UpdateFileProgress() {
        }

        public UpdateFileProgress(int fileId, int size, int ready) {
            this.fileId = fileId;
            this.size = size;
            this.ready = ready;
        }

        public static final int CONSTRUCTOR = 1340921194;

        @Override
        public int getConstructor() {
            return 1340921194;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateFileProgress").append(" {");
            shift += 2;
            appendLine(s, shift).append("fileId = ").append(fileId);
            appendLine(s, shift).append("size = ").append(size);
            appendLine(s, shift).append("ready = ").append(ready);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateFile extends Update {
        public File file;

        public UpdateFile() {
        }

        public UpdateFile(File file) {
            this.file = file;
        }

        public static final int CONSTRUCTOR = 114132831;

        @Override
        public int getConstructor() {
            return 114132831;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateFile").append(" {");
            shift += 2;
            appendLine(s, shift).append("file = "); file.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateOption extends Update {
        public String name;
        public OptionValue value;

        public UpdateOption() {
        }

        public UpdateOption(String name, OptionValue value) {
            this.name = name;
            this.value = value;
        }

        public static final int CONSTRUCTOR = 900822020;

        @Override
        public int getConstructor() {
            return 900822020;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateOption").append(" {");
            shift += 2;
            appendLine(s, shift).append("name = ").append(name);
            appendLine(s, shift).append("value = "); value.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateStickers extends Update {

        public UpdateStickers() {
        }

        public static final int CONSTRUCTOR = -456211753;

        @Override
        public int getConstructor() {
            return -456211753;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateStickers").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateSavedAnimations extends Update {

        public UpdateSavedAnimations() {
        }

        public static final int CONSTRUCTOR = -1517884047;

        @Override
        public int getConstructor() {
            return -1517884047;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateSavedAnimations").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateNewInlineQuery extends Update {
        public long id;
        public int senderUserId;
        public Location userLocation;
        public String query;
        public String offset;

        public UpdateNewInlineQuery() {
        }

        public UpdateNewInlineQuery(long id, int senderUserId, Location userLocation, String query, String offset) {
            this.id = id;
            this.senderUserId = senderUserId;
            this.userLocation = userLocation;
            this.query = query;
            this.offset = offset;
        }

        public static final int CONSTRUCTOR = -820108313;

        @Override
        public int getConstructor() {
            return -820108313;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateNewInlineQuery").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("senderUserId = ").append(senderUserId);
            appendLine(s, shift).append("userLocation = "); userLocation.toStringBuilder(shift, s);
            appendLine(s, shift).append("query = ").append(query);
            appendLine(s, shift).append("offset = ").append(offset);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateNewChosenInlineResult extends Update {
        public int senderUserId;
        public Location userLocation;
        public String query;
        public String resultId;
        public String inlineMessageId;

        public UpdateNewChosenInlineResult() {
        }

        public UpdateNewChosenInlineResult(int senderUserId, Location userLocation, String query, String resultId, String inlineMessageId) {
            this.senderUserId = senderUserId;
            this.userLocation = userLocation;
            this.query = query;
            this.resultId = resultId;
            this.inlineMessageId = inlineMessageId;
        }

        public static final int CONSTRUCTOR = -867559680;

        @Override
        public int getConstructor() {
            return -867559680;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateNewChosenInlineResult").append(" {");
            shift += 2;
            appendLine(s, shift).append("senderUserId = ").append(senderUserId);
            appendLine(s, shift).append("userLocation = "); userLocation.toStringBuilder(shift, s);
            appendLine(s, shift).append("query = ").append(query);
            appendLine(s, shift).append("resultId = ").append(resultId);
            appendLine(s, shift).append("inlineMessageId = ").append(inlineMessageId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateNewCallbackQuery extends Update {
        public long id;
        public int senderUserId;
        public long chatId;
        public int messageId;
        public long chatInstance;
        public CallbackQueryPayload payload;

        public UpdateNewCallbackQuery() {
        }

        public UpdateNewCallbackQuery(long id, int senderUserId, long chatId, int messageId, long chatInstance, CallbackQueryPayload payload) {
            this.id = id;
            this.senderUserId = senderUserId;
            this.chatId = chatId;
            this.messageId = messageId;
            this.chatInstance = chatInstance;
            this.payload = payload;
        }

        public static final int CONSTRUCTOR = -469662806;

        @Override
        public int getConstructor() {
            return -469662806;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateNewCallbackQuery").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("senderUserId = ").append(senderUserId);
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("messageId = ").append(messageId);
            appendLine(s, shift).append("chatInstance = ").append(chatInstance);
            appendLine(s, shift).append("payload = "); payload.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateNewInlineCallbackQuery extends Update {
        public long id;
        public int senderUserId;
        public String inlineMessageId;
        public long chatInstance;
        public CallbackQueryPayload payload;

        public UpdateNewInlineCallbackQuery() {
        }

        public UpdateNewInlineCallbackQuery(long id, int senderUserId, String inlineMessageId, long chatInstance, CallbackQueryPayload payload) {
            this.id = id;
            this.senderUserId = senderUserId;
            this.inlineMessageId = inlineMessageId;
            this.chatInstance = chatInstance;
            this.payload = payload;
        }

        public static final int CONSTRUCTOR = -1564879277;

        @Override
        public int getConstructor() {
            return -1564879277;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateNewInlineCallbackQuery").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("senderUserId = ").append(senderUserId);
            appendLine(s, shift).append("inlineMessageId = ").append(inlineMessageId);
            appendLine(s, shift).append("chatInstance = ").append(chatInstance);
            appendLine(s, shift).append("payload = "); payload.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class User extends TLObject {
        public int id;
        public String firstName;
        public String lastName;
        public String username;
        public String phoneNumber;
        public UserStatus status;
        public ProfilePhoto profilePhoto;
        public LinkState myLink;
        public LinkState foreignLink;
        public boolean isVerified;
        public String restrictionReason;
        public boolean haveAccess;
        public UserType type;

        public User() {
        }

        public User(int id, String firstName, String lastName, String username, String phoneNumber, UserStatus status, ProfilePhoto profilePhoto, LinkState myLink, LinkState foreignLink, boolean isVerified, String restrictionReason, boolean haveAccess, UserType type) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.username = username;
            this.phoneNumber = phoneNumber;
            this.status = status;
            this.profilePhoto = profilePhoto;
            this.myLink = myLink;
            this.foreignLink = foreignLink;
            this.isVerified = isVerified;
            this.restrictionReason = restrictionReason;
            this.haveAccess = haveAccess;
            this.type = type;
        }

        public static final int CONSTRUCTOR = -551088334;

        @Override
        public int getConstructor() {
            return -551088334;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("User").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("firstName = ").append(firstName);
            appendLine(s, shift).append("lastName = ").append(lastName);
            appendLine(s, shift).append("username = ").append(username);
            appendLine(s, shift).append("phoneNumber = ").append(phoneNumber);
            appendLine(s, shift).append("status = "); status.toStringBuilder(shift, s);
            appendLine(s, shift).append("profilePhoto = "); profilePhoto.toStringBuilder(shift, s);
            appendLine(s, shift).append("myLink = "); myLink.toStringBuilder(shift, s);
            appendLine(s, shift).append("foreignLink = "); foreignLink.toStringBuilder(shift, s);
            appendLine(s, shift).append("isVerified = ").append(isVerified);
            appendLine(s, shift).append("restrictionReason = ").append(restrictionReason);
            appendLine(s, shift).append("haveAccess = ").append(haveAccess);
            appendLine(s, shift).append("type = "); type.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UserFull extends TLObject {
        public User user;
        public boolean isBlocked;
        public String about;
        public BotInfo botInfo;

        public UserFull() {
        }

        public UserFull(User user, boolean isBlocked, String about, BotInfo botInfo) {
            this.user = user;
            this.isBlocked = isBlocked;
            this.about = about;
            this.botInfo = botInfo;
        }

        public static final int CONSTRUCTOR = -539848475;

        @Override
        public int getConstructor() {
            return -539848475;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UserFull").append(" {");
            shift += 2;
            appendLine(s, shift).append("user = "); user.toStringBuilder(shift, s);
            appendLine(s, shift).append("isBlocked = ").append(isBlocked);
            appendLine(s, shift).append("about = ").append(about);
            appendLine(s, shift).append("botInfo = "); botInfo.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UserProfilePhotos extends TLObject {
        public int totalCount;
        public Photo[] photos;

        public UserProfilePhotos() {
        }

        public UserProfilePhotos(int totalCount, Photo[] photos) {
            this.totalCount = totalCount;
            this.photos = photos;
        }

        public static final int CONSTRUCTOR = -1425984405;

        @Override
        public int getConstructor() {
            return -1425984405;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UserProfilePhotos").append(" {");
            shift += 2;
            appendLine(s, shift).append("totalCount = ").append(totalCount);
            appendLine(s, shift).append("photos = ").append(Arrays.toString(photos));
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public abstract static class UserStatus extends TLObject {
    }

    public static class UserStatusEmpty extends UserStatus {

        public UserStatusEmpty() {
        }

        public static final int CONSTRUCTOR = 164646985;

        @Override
        public int getConstructor() {
            return 164646985;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UserStatusEmpty").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UserStatusOnline extends UserStatus {
        public int expires;

        public UserStatusOnline() {
        }

        public UserStatusOnline(int expires) {
            this.expires = expires;
        }

        public static final int CONSTRUCTOR = -306628279;

        @Override
        public int getConstructor() {
            return -306628279;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UserStatusOnline").append(" {");
            shift += 2;
            appendLine(s, shift).append("expires = ").append(expires);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UserStatusOffline extends UserStatus {
        public int wasOnline;

        public UserStatusOffline() {
        }

        public UserStatusOffline(int wasOnline) {
            this.wasOnline = wasOnline;
        }

        public static final int CONSTRUCTOR = 9203775;

        @Override
        public int getConstructor() {
            return 9203775;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UserStatusOffline").append(" {");
            shift += 2;
            appendLine(s, shift).append("wasOnline = ").append(wasOnline);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UserStatusRecently extends UserStatus {

        public UserStatusRecently() {
        }

        public static final int CONSTRUCTOR = -496024847;

        @Override
        public int getConstructor() {
            return -496024847;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UserStatusRecently").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UserStatusLastWeek extends UserStatus {

        public UserStatusLastWeek() {
        }

        public static final int CONSTRUCTOR = 129960444;

        @Override
        public int getConstructor() {
            return 129960444;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UserStatusLastWeek").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UserStatusLastMonth extends UserStatus {

        public UserStatusLastMonth() {
        }

        public static final int CONSTRUCTOR = 2011940674;

        @Override
        public int getConstructor() {
            return 2011940674;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UserStatusLastMonth").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public abstract static class UserType extends TLObject {
    }

    public static class UserTypeGeneral extends UserType {

        public UserTypeGeneral() {
        }

        public static final int CONSTRUCTOR = -955149573;

        @Override
        public int getConstructor() {
            return -955149573;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UserTypeGeneral").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UserTypeDeleted extends UserType {

        public UserTypeDeleted() {
        }

        public static final int CONSTRUCTOR = -1807729372;

        @Override
        public int getConstructor() {
            return -1807729372;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UserTypeDeleted").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UserTypeBot extends UserType {
        public boolean canJoinGroupChats;
        public boolean canReadAllGroupChatMessages;
        public boolean isInline;
        public String inlineQueryPlaceholder;
        public boolean needLocation;

        public UserTypeBot() {
        }

        public UserTypeBot(boolean canJoinGroupChats, boolean canReadAllGroupChatMessages, boolean isInline, String inlineQueryPlaceholder, boolean needLocation) {
            this.canJoinGroupChats = canJoinGroupChats;
            this.canReadAllGroupChatMessages = canReadAllGroupChatMessages;
            this.isInline = isInline;
            this.inlineQueryPlaceholder = inlineQueryPlaceholder;
            this.needLocation = needLocation;
        }

        public static final int CONSTRUCTOR = -610455780;

        @Override
        public int getConstructor() {
            return -610455780;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UserTypeBot").append(" {");
            shift += 2;
            appendLine(s, shift).append("canJoinGroupChats = ").append(canJoinGroupChats);
            appendLine(s, shift).append("canReadAllGroupChatMessages = ").append(canReadAllGroupChatMessages);
            appendLine(s, shift).append("isInline = ").append(isInline);
            appendLine(s, shift).append("inlineQueryPlaceholder = ").append(inlineQueryPlaceholder);
            appendLine(s, shift).append("needLocation = ").append(needLocation);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UserTypeUnknown extends UserType {

        public UserTypeUnknown() {
        }

        public static final int CONSTRUCTOR = -724541123;

        @Override
        public int getConstructor() {
            return -724541123;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UserTypeUnknown").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class Users extends TLObject {
        public int totalCount;
        public User[] users;

        public Users() {
        }

        public Users(int totalCount, User[] users) {
            this.totalCount = totalCount;
            this.users = users;
        }

        public static final int CONSTRUCTOR = 469005719;

        @Override
        public int getConstructor() {
            return 469005719;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("Users").append(" {");
            shift += 2;
            appendLine(s, shift).append("totalCount = ").append(totalCount);
            appendLine(s, shift).append("users = ").append(Arrays.toString(users));
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class Venue extends TLObject {
        public Location location;
        public String title;
        public String address;
        public String provider;
        public String id;

        public Venue() {
        }

        public Venue(Location location, String title, String address, String provider, String id) {
            this.location = location;
            this.title = title;
            this.address = address;
            this.provider = provider;
            this.id = id;
        }

        public static final int CONSTRUCTOR = -674687867;

        @Override
        public int getConstructor() {
            return -674687867;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("Venue").append(" {");
            shift += 2;
            appendLine(s, shift).append("location = "); location.toStringBuilder(shift, s);
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("address = ").append(address);
            appendLine(s, shift).append("provider = ").append(provider);
            appendLine(s, shift).append("id = ").append(id);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class Video extends TLObject {
        public int duration;
        public int width;
        public int height;
        public String fileName;
        public String mimeType;
        public PhotoSize thumb;
        public File video;

        public Video() {
        }

        public Video(int duration, int width, int height, String fileName, String mimeType, PhotoSize thumb, File video) {
            this.duration = duration;
            this.width = width;
            this.height = height;
            this.fileName = fileName;
            this.mimeType = mimeType;
            this.thumb = thumb;
            this.video = video;
        }

        public static final int CONSTRUCTOR = 839000879;

        @Override
        public int getConstructor() {
            return 839000879;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("Video").append(" {");
            shift += 2;
            appendLine(s, shift).append("duration = ").append(duration);
            appendLine(s, shift).append("width = ").append(width);
            appendLine(s, shift).append("height = ").append(height);
            appendLine(s, shift).append("fileName = ").append(fileName);
            appendLine(s, shift).append("mimeType = ").append(mimeType);
            appendLine(s, shift).append("thumb = "); thumb.toStringBuilder(shift, s);
            appendLine(s, shift).append("video = "); video.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class Voice extends TLObject {
        public int duration;
        public byte[] waveform;
        public String mimeType;
        public File voice;

        public Voice() {
        }

        public Voice(int duration, byte[] waveform, String mimeType, File voice) {
            this.duration = duration;
            this.waveform = waveform;
            this.mimeType = mimeType;
            this.voice = voice;
        }

        public static final int CONSTRUCTOR = -348096919;

        @Override
        public int getConstructor() {
            return -348096919;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("Voice").append(" {");
            shift += 2;
            appendLine(s, shift).append("duration = ").append(duration);
            appendLine(s, shift).append("waveform = ").append("bytes { "); { for (byte k : waveform) { int b = (int)k & 255; s.append(HEX_CHARACTERS[b >> 4]).append(HEX_CHARACTERS[b & 15]).append(' '); } } s.append('}');
            appendLine(s, shift).append("mimeType = ").append(mimeType);
            appendLine(s, shift).append("voice = "); voice.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class Wallpaper extends TLObject {
        public PhotoSize[] sizes;
        public int color;

        public Wallpaper() {
        }

        public Wallpaper(PhotoSize[] sizes, int color) {
            this.sizes = sizes;
            this.color = color;
        }

        public static final int CONSTRUCTOR = -803346388;

        @Override
        public int getConstructor() {
            return -803346388;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("Wallpaper").append(" {");
            shift += 2;
            appendLine(s, shift).append("sizes = ").append(Arrays.toString(sizes));
            appendLine(s, shift).append("color = ").append(color);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class Wallpapers extends TLObject {
        public Wallpaper[] wallpapers;

        public Wallpapers() {
        }

        public Wallpapers(Wallpaper[] wallpapers) {
            this.wallpapers = wallpapers;
        }

        public static final int CONSTRUCTOR = 877926640;

        @Override
        public int getConstructor() {
            return 877926640;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("Wallpapers").append(" {");
            shift += 2;
            appendLine(s, shift).append("wallpapers = ").append(Arrays.toString(wallpapers));
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class WebPage extends TLObject {
        public String url;
        public String displayUrl;
        public String type;
        public String siteName;
        public String title;
        public String description;
        public Photo photo;
        public String embedUrl;
        public String embedType;
        public int embedWidth;
        public int embedHeight;
        public int duration;
        public String author;
        public Animation animation;
        public Audio audio;
        public Document document;
        public Sticker sticker;
        public Video video;
        public Voice voice;

        public WebPage() {
        }

        public WebPage(String url, String displayUrl, String type, String siteName, String title, String description, Photo photo, String embedUrl, String embedType, int embedWidth, int embedHeight, int duration, String author, Animation animation, Audio audio, Document document, Sticker sticker, Video video, Voice voice) {
            this.url = url;
            this.displayUrl = displayUrl;
            this.type = type;
            this.siteName = siteName;
            this.title = title;
            this.description = description;
            this.photo = photo;
            this.embedUrl = embedUrl;
            this.embedType = embedType;
            this.embedWidth = embedWidth;
            this.embedHeight = embedHeight;
            this.duration = duration;
            this.author = author;
            this.animation = animation;
            this.audio = audio;
            this.document = document;
            this.sticker = sticker;
            this.video = video;
            this.voice = voice;
        }

        public static final int CONSTRUCTOR = -268383594;

        @Override
        public int getConstructor() {
            return -268383594;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("WebPage").append(" {");
            shift += 2;
            appendLine(s, shift).append("url = ").append(url);
            appendLine(s, shift).append("displayUrl = ").append(displayUrl);
            appendLine(s, shift).append("type = ").append(type);
            appendLine(s, shift).append("siteName = ").append(siteName);
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("description = ").append(description);
            appendLine(s, shift).append("photo = "); photo.toStringBuilder(shift, s);
            appendLine(s, shift).append("embedUrl = ").append(embedUrl);
            appendLine(s, shift).append("embedType = ").append(embedType);
            appendLine(s, shift).append("embedWidth = ").append(embedWidth);
            appendLine(s, shift).append("embedHeight = ").append(embedHeight);
            appendLine(s, shift).append("duration = ").append(duration);
            appendLine(s, shift).append("author = ").append(author);
            appendLine(s, shift).append("animation = "); animation.toStringBuilder(shift, s);
            appendLine(s, shift).append("audio = "); audio.toStringBuilder(shift, s);
            appendLine(s, shift).append("document = "); document.toStringBuilder(shift, s);
            appendLine(s, shift).append("sticker = "); sticker.toStringBuilder(shift, s);
            appendLine(s, shift).append("video = "); video.toStringBuilder(shift, s);
            appendLine(s, shift).append("voice = "); voice.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class TestBytes extends TLObject {
        public byte[] value;

        public TestBytes() {
        }

        public TestBytes(byte[] value) {
            this.value = value;
        }

        public static final int CONSTRUCTOR = 667099484;

        @Override
        public int getConstructor() {
            return 667099484;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestBytes").append(" {");
            shift += 2;
            appendLine(s, shift).append("value = ").append("bytes { "); { for (byte k : value) { int b = (int)k & 255; s.append(HEX_CHARACTERS[b >> 4]).append(HEX_CHARACTERS[b & 15]).append(' '); } } s.append('}');
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class TestEmpty extends TLObject {

        public TestEmpty() {
        }

        public static final int CONSTRUCTOR = 1453429851;

        @Override
        public int getConstructor() {
            return 1453429851;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestEmpty").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class TestInt extends TLObject {
        public int value;

        public TestInt() {
        }

        public TestInt(int value) {
            this.value = value;
        }

        public static final int CONSTRUCTOR = 1472758404;

        @Override
        public int getConstructor() {
            return 1472758404;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestInt").append(" {");
            shift += 2;
            appendLine(s, shift).append("value = ").append(value);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class TestString extends TLObject {
        public String value;

        public TestString() {
        }

        public TestString(String value) {
            this.value = value;
        }

        public static final int CONSTRUCTOR = -705221530;

        @Override
        public int getConstructor() {
            return -705221530;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestString").append(" {");
            shift += 2;
            appendLine(s, shift).append("value = ").append(value);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class TestVectorInt extends TLObject {
        public int[] value;

        public TestVectorInt() {
        }

        public TestVectorInt(int[] value) {
            this.value = value;
        }

        public static final int CONSTRUCTOR = -278984267;

        @Override
        public int getConstructor() {
            return -278984267;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestVectorInt").append(" {");
            shift += 2;
            appendLine(s, shift).append("value = ").append(Arrays.toString(value));
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class TestVectorIntObject extends TLObject {
        public TestInt[] value;

        public TestVectorIntObject() {
        }

        public TestVectorIntObject(TestInt[] value) {
            this.value = value;
        }

        public static final int CONSTRUCTOR = 1915770327;

        @Override
        public int getConstructor() {
            return 1915770327;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestVectorIntObject").append(" {");
            shift += 2;
            appendLine(s, shift).append("value = ").append(Arrays.toString(value));
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class TestVectorString extends TLObject {
        public String[] value;

        public TestVectorString() {
        }

        public TestVectorString(String[] value) {
            this.value = value;
        }

        public static final int CONSTRUCTOR = 1800468445;

        @Override
        public int getConstructor() {
            return 1800468445;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestVectorString").append(" {");
            shift += 2;
            appendLine(s, shift).append("value = ").append(Arrays.toString(value));
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class TestVectorStringObject extends TLObject {
        public TestString[] value;

        public TestVectorStringObject() {
        }

        public TestVectorStringObject(TestString[] value) {
            this.value = value;
        }

        public static final int CONSTRUCTOR = -1261798902;

        @Override
        public int getConstructor() {
            return -1261798902;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestVectorStringObject").append(" {");
            shift += 2;
            appendLine(s, shift).append("value = ").append(Arrays.toString(value));
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class AddChatMember extends TLFunction {
        public long chatId;
        public int userId;
        public int forwardLimit;

        public AddChatMember() {
        }

        public AddChatMember(long chatId, int userId, int forwardLimit) {
            this.chatId = chatId;
            this.userId = userId;
            this.forwardLimit = forwardLimit;
        }

        public static final int CONSTRUCTOR = 1252828345;

        @Override
        public int getConstructor() {
            return 1252828345;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("AddChatMember").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("userId = ").append(userId);
            appendLine(s, shift).append("forwardLimit = ").append(forwardLimit);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class AddChatMembers extends TLFunction {
        public long chatId;
        public int[] userIds;

        public AddChatMembers() {
        }

        public AddChatMembers(long chatId, int[] userIds) {
            this.chatId = chatId;
            this.userIds = userIds;
        }

        public static final int CONSTRUCTOR = 693690362;

        @Override
        public int getConstructor() {
            return 693690362;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("AddChatMembers").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("userIds = ").append(Arrays.toString(userIds));
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class AddRecentlyFoundChat extends TLFunction {
        public long chatId;

        public AddRecentlyFoundChat() {
        }

        public AddRecentlyFoundChat(long chatId) {
            this.chatId = chatId;
        }

        public static final int CONSTRUCTOR = 2127862625;

        @Override
        public int getConstructor() {
            return 2127862625;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("AddRecentlyFoundChat").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class AddSavedAnimation extends TLFunction {
        public InputFile animation;

        public AddSavedAnimation() {
        }

        public AddSavedAnimation(InputFile animation) {
            this.animation = animation;
        }

        public static final int CONSTRUCTOR = -1538525088;

        @Override
        public int getConstructor() {
            return -1538525088;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("AddSavedAnimation").append(" {");
            shift += 2;
            appendLine(s, shift).append("animation = "); animation.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class AnswerCallbackQuery extends TLFunction {
        public long callbackQueryId;
        public String text;
        public boolean showAlert;
        public String url;

        public AnswerCallbackQuery() {
        }

        public AnswerCallbackQuery(long callbackQueryId, String text, boolean showAlert, String url) {
            this.callbackQueryId = callbackQueryId;
            this.text = text;
            this.showAlert = showAlert;
            this.url = url;
        }

        public static final int CONSTRUCTOR = 373886252;

        @Override
        public int getConstructor() {
            return 373886252;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("AnswerCallbackQuery").append(" {");
            shift += 2;
            appendLine(s, shift).append("callbackQueryId = ").append(callbackQueryId);
            appendLine(s, shift).append("text = ").append(text);
            appendLine(s, shift).append("showAlert = ").append(showAlert);
            appendLine(s, shift).append("url = ").append(url);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class AnswerInlineQuery extends TLFunction {
        public long inlineQueryId;
        public boolean isPersonal;
        public InputInlineQueryResult[] results;
        public int cacheTime;
        public String nextOffset;
        public String switchPmText;
        public String switchPmParameter;

        public AnswerInlineQuery() {
        }

        public AnswerInlineQuery(long inlineQueryId, boolean isPersonal, InputInlineQueryResult[] results, int cacheTime, String nextOffset, String switchPmText, String switchPmParameter) {
            this.inlineQueryId = inlineQueryId;
            this.isPersonal = isPersonal;
            this.results = results;
            this.cacheTime = cacheTime;
            this.nextOffset = nextOffset;
            this.switchPmText = switchPmText;
            this.switchPmParameter = switchPmParameter;
        }

        public static final int CONSTRUCTOR = 1529641829;

        @Override
        public int getConstructor() {
            return 1529641829;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("AnswerInlineQuery").append(" {");
            shift += 2;
            appendLine(s, shift).append("inlineQueryId = ").append(inlineQueryId);
            appendLine(s, shift).append("isPersonal = ").append(isPersonal);
            appendLine(s, shift).append("results = ").append(Arrays.toString(results));
            appendLine(s, shift).append("cacheTime = ").append(cacheTime);
            appendLine(s, shift).append("nextOffset = ").append(nextOffset);
            appendLine(s, shift).append("switchPmText = ").append(switchPmText);
            appendLine(s, shift).append("switchPmParameter = ").append(switchPmParameter);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class BlockUser extends TLFunction {
        public int userId;

        public BlockUser() {
        }

        public BlockUser(int userId) {
            this.userId = userId;
        }

        public static final int CONSTRUCTOR = -200788058;

        @Override
        public int getConstructor() {
            return -200788058;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("BlockUser").append(" {");
            shift += 2;
            appendLine(s, shift).append("userId = ").append(userId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class CancelDownloadFile extends TLFunction {
        public int fileId;

        public CancelDownloadFile() {
        }

        public CancelDownloadFile(int fileId) {
            this.fileId = fileId;
        }

        public static final int CONSTRUCTOR = 18489866;

        @Override
        public int getConstructor() {
            return 18489866;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("CancelDownloadFile").append(" {");
            shift += 2;
            appendLine(s, shift).append("fileId = ").append(fileId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ChangeAbout extends TLFunction {
        public String about;

        public ChangeAbout() {
        }

        public ChangeAbout(String about) {
            this.about = about;
        }

        public static final int CONSTRUCTOR = -1364842334;

        @Override
        public int getConstructor() {
            return -1364842334;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChangeAbout").append(" {");
            shift += 2;
            appendLine(s, shift).append("about = ").append(about);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ChangeAccountTtl extends TLFunction {
        public AccountTtl ttl;

        public ChangeAccountTtl() {
        }

        public ChangeAccountTtl(AccountTtl ttl) {
            this.ttl = ttl;
        }

        public static final int CONSTRUCTOR = -1079644217;

        @Override
        public int getConstructor() {
            return -1079644217;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChangeAccountTtl").append(" {");
            shift += 2;
            appendLine(s, shift).append("ttl = "); ttl.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ChangeChannelAbout extends TLFunction {
        public int channelId;
        public String about;

        public ChangeChannelAbout() {
        }

        public ChangeChannelAbout(int channelId, String about) {
            this.channelId = channelId;
            this.about = about;
        }

        public static final int CONSTRUCTOR = -1985280026;

        @Override
        public int getConstructor() {
            return -1985280026;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChangeChannelAbout").append(" {");
            shift += 2;
            appendLine(s, shift).append("channelId = ").append(channelId);
            appendLine(s, shift).append("about = ").append(about);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ChangeChannelUsername extends TLFunction {
        public int channelId;
        public String username;

        public ChangeChannelUsername() {
        }

        public ChangeChannelUsername(int channelId, String username) {
            this.channelId = channelId;
            this.username = username;
        }

        public static final int CONSTRUCTOR = -1681482857;

        @Override
        public int getConstructor() {
            return -1681482857;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChangeChannelUsername").append(" {");
            shift += 2;
            appendLine(s, shift).append("channelId = ").append(channelId);
            appendLine(s, shift).append("username = ").append(username);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ChangeChatDraftMessage extends TLFunction {
        public long chatId;
        public DraftMessage draftMessage;

        public ChangeChatDraftMessage() {
        }

        public ChangeChatDraftMessage(long chatId, DraftMessage draftMessage) {
            this.chatId = chatId;
            this.draftMessage = draftMessage;
        }

        public static final int CONSTRUCTOR = 1572219043;

        @Override
        public int getConstructor() {
            return 1572219043;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChangeChatDraftMessage").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("draftMessage = "); draftMessage.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ChangeChatMemberStatus extends TLFunction {
        public long chatId;
        public int userId;
        public ChatMemberStatus status;

        public ChangeChatMemberStatus() {
        }

        public ChangeChatMemberStatus(long chatId, int userId, ChatMemberStatus status) {
            this.chatId = chatId;
            this.userId = userId;
            this.status = status;
        }

        public static final int CONSTRUCTOR = -1725424647;

        @Override
        public int getConstructor() {
            return -1725424647;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChangeChatMemberStatus").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("userId = ").append(userId);
            appendLine(s, shift).append("status = "); status.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ChangeChatPhoto extends TLFunction {
        public long chatId;
        public InputFile photo;

        public ChangeChatPhoto() {
        }

        public ChangeChatPhoto(long chatId, InputFile photo) {
            this.chatId = chatId;
            this.photo = photo;
        }

        public static final int CONSTRUCTOR = -452848448;

        @Override
        public int getConstructor() {
            return -452848448;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChangeChatPhoto").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("photo = "); photo.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ChangeChatTitle extends TLFunction {
        public long chatId;
        public String title;

        public ChangeChatTitle() {
        }

        public ChangeChatTitle(long chatId, String title) {
            this.chatId = chatId;
            this.title = title;
        }

        public static final int CONSTRUCTOR = -503002783;

        @Override
        public int getConstructor() {
            return -503002783;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChangeChatTitle").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("title = ").append(title);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ChangeName extends TLFunction {
        public String firstName;
        public String lastName;

        public ChangeName() {
        }

        public ChangeName(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public static final int CONSTRUCTOR = 1341435471;

        @Override
        public int getConstructor() {
            return 1341435471;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChangeName").append(" {");
            shift += 2;
            appendLine(s, shift).append("firstName = ").append(firstName);
            appendLine(s, shift).append("lastName = ").append(lastName);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ChangePhoneNumber extends TLFunction {
        public String phoneNumber;
        public boolean allowFlashCall;
        public boolean isCurrentPhoneNumber;

        public ChangePhoneNumber() {
        }

        public ChangePhoneNumber(String phoneNumber, boolean allowFlashCall, boolean isCurrentPhoneNumber) {
            this.phoneNumber = phoneNumber;
            this.allowFlashCall = allowFlashCall;
            this.isCurrentPhoneNumber = isCurrentPhoneNumber;
        }

        public static final int CONSTRUCTOR = 1329283881;

        @Override
        public int getConstructor() {
            return 1329283881;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChangePhoneNumber").append(" {");
            shift += 2;
            appendLine(s, shift).append("phoneNumber = ").append(phoneNumber);
            appendLine(s, shift).append("allowFlashCall = ").append(allowFlashCall);
            appendLine(s, shift).append("isCurrentPhoneNumber = ").append(isCurrentPhoneNumber);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ChangeUsername extends TLFunction {
        public String username;

        public ChangeUsername() {
        }

        public ChangeUsername(String username) {
            this.username = username;
        }

        public static final int CONSTRUCTOR = 2015886676;

        @Override
        public int getConstructor() {
            return 2015886676;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChangeUsername").append(" {");
            shift += 2;
            appendLine(s, shift).append("username = ").append(username);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class CheckAuthBotToken extends TLFunction {
        public String token;

        public CheckAuthBotToken() {
        }

        public CheckAuthBotToken(String token) {
            this.token = token;
        }

        public static final int CONSTRUCTOR = -1829581747;

        @Override
        public int getConstructor() {
            return -1829581747;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("CheckAuthBotToken").append(" {");
            shift += 2;
            appendLine(s, shift).append("token = ").append(token);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class CheckAuthCode extends TLFunction {
        public String code;
        public String firstName;
        public String lastName;

        public CheckAuthCode() {
        }

        public CheckAuthCode(String code, String firstName, String lastName) {
            this.code = code;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public static final int CONSTRUCTOR = -1636693521;

        @Override
        public int getConstructor() {
            return -1636693521;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("CheckAuthCode").append(" {");
            shift += 2;
            appendLine(s, shift).append("code = ").append(code);
            appendLine(s, shift).append("firstName = ").append(firstName);
            appendLine(s, shift).append("lastName = ").append(lastName);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class CheckAuthPassword extends TLFunction {
        public String password;

        public CheckAuthPassword() {
        }

        public CheckAuthPassword(String password) {
            this.password = password;
        }

        public static final int CONSTRUCTOR = -1138590405;

        @Override
        public int getConstructor() {
            return -1138590405;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("CheckAuthPassword").append(" {");
            shift += 2;
            appendLine(s, shift).append("password = ").append(password);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class CheckChangePhoneNumberCode extends TLFunction {
        public String code;

        public CheckChangePhoneNumberCode() {
        }

        public CheckChangePhoneNumberCode(String code) {
            this.code = code;
        }

        public static final int CONSTRUCTOR = 1010764228;

        @Override
        public int getConstructor() {
            return 1010764228;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("CheckChangePhoneNumberCode").append(" {");
            shift += 2;
            appendLine(s, shift).append("code = ").append(code);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class CheckChatInviteLink extends TLFunction {
        public String inviteLink;

        public CheckChatInviteLink() {
        }

        public CheckChatInviteLink(String inviteLink) {
            this.inviteLink = inviteLink;
        }

        public static final int CONSTRUCTOR = -496940997;

        @Override
        public int getConstructor() {
            return -496940997;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("CheckChatInviteLink").append(" {");
            shift += 2;
            appendLine(s, shift).append("inviteLink = ").append(inviteLink);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class CloseChat extends TLFunction {
        public long chatId;

        public CloseChat() {
        }

        public CloseChat(long chatId) {
            this.chatId = chatId;
        }

        public static final int CONSTRUCTOR = 1996586409;

        @Override
        public int getConstructor() {
            return 1996586409;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("CloseChat").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class CreateChannelChat extends TLFunction {
        public int channelId;

        public CreateChannelChat() {
        }

        public CreateChannelChat(int channelId) {
            this.channelId = channelId;
        }

        public static final int CONSTRUCTOR = 31132213;

        @Override
        public int getConstructor() {
            return 31132213;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("CreateChannelChat").append(" {");
            shift += 2;
            appendLine(s, shift).append("channelId = ").append(channelId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class CreateGroupChat extends TLFunction {
        public int groupId;

        public CreateGroupChat() {
        }

        public CreateGroupChat(int groupId) {
            this.groupId = groupId;
        }

        public static final int CONSTRUCTOR = -804136412;

        @Override
        public int getConstructor() {
            return -804136412;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("CreateGroupChat").append(" {");
            shift += 2;
            appendLine(s, shift).append("groupId = ").append(groupId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class CreateNewChannelChat extends TLFunction {
        public String title;
        public boolean isSupergroup;
        public String about;

        public CreateNewChannelChat() {
        }

        public CreateNewChannelChat(String title, boolean isSupergroup, String about) {
            this.title = title;
            this.isSupergroup = isSupergroup;
            this.about = about;
        }

        public static final int CONSTRUCTOR = -1547755747;

        @Override
        public int getConstructor() {
            return -1547755747;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("CreateNewChannelChat").append(" {");
            shift += 2;
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("isSupergroup = ").append(isSupergroup);
            appendLine(s, shift).append("about = ").append(about);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class CreateNewGroupChat extends TLFunction {
        public int[] userIds;
        public String title;

        public CreateNewGroupChat() {
        }

        public CreateNewGroupChat(int[] userIds, String title) {
            this.userIds = userIds;
            this.title = title;
        }

        public static final int CONSTRUCTOR = -1513123868;

        @Override
        public int getConstructor() {
            return -1513123868;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("CreateNewGroupChat").append(" {");
            shift += 2;
            appendLine(s, shift).append("userIds = ").append(Arrays.toString(userIds));
            appendLine(s, shift).append("title = ").append(title);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class CreateNewSecretChat extends TLFunction {
        public int userId;

        public CreateNewSecretChat() {
        }

        public CreateNewSecretChat(int userId) {
            this.userId = userId;
        }

        public static final int CONSTRUCTOR = 391182939;

        @Override
        public int getConstructor() {
            return 391182939;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("CreateNewSecretChat").append(" {");
            shift += 2;
            appendLine(s, shift).append("userId = ").append(userId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class CreatePrivateChat extends TLFunction {
        public int userId;

        public CreatePrivateChat() {
        }

        public CreatePrivateChat(int userId) {
            this.userId = userId;
        }

        public static final int CONSTRUCTOR = 1204324690;

        @Override
        public int getConstructor() {
            return 1204324690;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("CreatePrivateChat").append(" {");
            shift += 2;
            appendLine(s, shift).append("userId = ").append(userId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class CreateSecretChat extends TLFunction {
        public int secretChatId;

        public CreateSecretChat() {
        }

        public CreateSecretChat(int secretChatId) {
            this.secretChatId = secretChatId;
        }

        public static final int CONSTRUCTOR = -74300012;

        @Override
        public int getConstructor() {
            return -74300012;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("CreateSecretChat").append(" {");
            shift += 2;
            appendLine(s, shift).append("secretChatId = ").append(secretChatId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class DeleteAccount extends TLFunction {
        public String reason;

        public DeleteAccount() {
        }

        public DeleteAccount(String reason) {
            this.reason = reason;
        }

        public static final int CONSTRUCTOR = -1203056508;

        @Override
        public int getConstructor() {
            return -1203056508;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("DeleteAccount").append(" {");
            shift += 2;
            appendLine(s, shift).append("reason = ").append(reason);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class DeleteAuthorization extends TLFunction {
        public long authorizationId;

        public DeleteAuthorization() {
        }

        public DeleteAuthorization(long authorizationId) {
            this.authorizationId = authorizationId;
        }

        public static final int CONSTRUCTOR = -834637761;

        @Override
        public int getConstructor() {
            return -834637761;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("DeleteAuthorization").append(" {");
            shift += 2;
            appendLine(s, shift).append("authorizationId = ").append(authorizationId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class DeleteChannel extends TLFunction {
        public int channelId;

        public DeleteChannel() {
        }

        public DeleteChannel(int channelId) {
            this.channelId = channelId;
        }

        public static final int CONSTRUCTOR = 251111194;

        @Override
        public int getConstructor() {
            return 251111194;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("DeleteChannel").append(" {");
            shift += 2;
            appendLine(s, shift).append("channelId = ").append(channelId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class DeleteChatHistory extends TLFunction {
        public long chatId;
        public boolean removeFromChatList;

        public DeleteChatHistory() {
        }

        public DeleteChatHistory(long chatId, boolean removeFromChatList) {
            this.chatId = chatId;
            this.removeFromChatList = removeFromChatList;
        }

        public static final int CONSTRUCTOR = 987906679;

        @Override
        public int getConstructor() {
            return 987906679;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("DeleteChatHistory").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("removeFromChatList = ").append(removeFromChatList);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class DeleteChatReplyMarkup extends TLFunction {
        public long chatId;
        public int messageId;

        public DeleteChatReplyMarkup() {
        }

        public DeleteChatReplyMarkup(long chatId, int messageId) {
            this.chatId = chatId;
            this.messageId = messageId;
        }

        public static final int CONSTRUCTOR = 959624272;

        @Override
        public int getConstructor() {
            return 959624272;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("DeleteChatReplyMarkup").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("messageId = ").append(messageId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class DeleteContacts extends TLFunction {
        public int[] userIds;

        public DeleteContacts() {
        }

        public DeleteContacts(int[] userIds) {
            this.userIds = userIds;
        }

        public static final int CONSTRUCTOR = 641913511;

        @Override
        public int getConstructor() {
            return 641913511;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("DeleteContacts").append(" {");
            shift += 2;
            appendLine(s, shift).append("userIds = ").append(Arrays.toString(userIds));
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class DeleteMessages extends TLFunction {
        public long chatId;
        public int[] messageIds;

        public DeleteMessages() {
        }

        public DeleteMessages(long chatId, int[] messageIds) {
            this.chatId = chatId;
            this.messageIds = messageIds;
        }

        public static final int CONSTRUCTOR = 1789583863;

        @Override
        public int getConstructor() {
            return 1789583863;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("DeleteMessages").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("messageIds = ").append(Arrays.toString(messageIds));
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class DeleteProfilePhoto extends TLFunction {
        public long profilePhotoId;

        public DeleteProfilePhoto() {
        }

        public DeleteProfilePhoto(long profilePhotoId) {
            this.profilePhotoId = profilePhotoId;
        }

        public static final int CONSTRUCTOR = -564878026;

        @Override
        public int getConstructor() {
            return -564878026;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("DeleteProfilePhoto").append(" {");
            shift += 2;
            appendLine(s, shift).append("profilePhotoId = ").append(profilePhotoId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class DeleteRecentlyFoundChat extends TLFunction {
        public long chatId;

        public DeleteRecentlyFoundChat() {
        }

        public DeleteRecentlyFoundChat(long chatId) {
            this.chatId = chatId;
        }

        public static final int CONSTRUCTOR = 1317692233;

        @Override
        public int getConstructor() {
            return 1317692233;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("DeleteRecentlyFoundChat").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class DeleteRecentlyFoundChats extends TLFunction {

        public DeleteRecentlyFoundChats() {
        }

        public static final int CONSTRUCTOR = -552660433;

        @Override
        public int getConstructor() {
            return -552660433;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("DeleteRecentlyFoundChats").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class DeleteSavedAnimation extends TLFunction {
        public InputFile animation;

        public DeleteSavedAnimation() {
        }

        public DeleteSavedAnimation(InputFile animation) {
            this.animation = animation;
        }

        public static final int CONSTRUCTOR = 2029723055;

        @Override
        public int getConstructor() {
            return 2029723055;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("DeleteSavedAnimation").append(" {");
            shift += 2;
            appendLine(s, shift).append("animation = "); animation.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class DownloadFile extends TLFunction {
        public int fileId;

        public DownloadFile() {
        }

        public DownloadFile(int fileId) {
            this.fileId = fileId;
        }

        public static final int CONSTRUCTOR = 888468545;

        @Override
        public int getConstructor() {
            return 888468545;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("DownloadFile").append(" {");
            shift += 2;
            appendLine(s, shift).append("fileId = ").append(fileId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class EditInlineMessageCaption extends TLFunction {
        public String inlineMessageId;
        public ReplyMarkup replyMarkup;
        public String caption;

        public EditInlineMessageCaption() {
        }

        public EditInlineMessageCaption(String inlineMessageId, ReplyMarkup replyMarkup, String caption) {
            this.inlineMessageId = inlineMessageId;
            this.replyMarkup = replyMarkup;
            this.caption = caption;
        }

        public static final int CONSTRUCTOR = 676019578;

        @Override
        public int getConstructor() {
            return 676019578;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("EditInlineMessageCaption").append(" {");
            shift += 2;
            appendLine(s, shift).append("inlineMessageId = ").append(inlineMessageId);
            appendLine(s, shift).append("replyMarkup = "); replyMarkup.toStringBuilder(shift, s);
            appendLine(s, shift).append("caption = ").append(caption);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class EditInlineMessageReplyMarkup extends TLFunction {
        public String inlineMessageId;
        public ReplyMarkup replyMarkup;

        public EditInlineMessageReplyMarkup() {
        }

        public EditInlineMessageReplyMarkup(String inlineMessageId, ReplyMarkup replyMarkup) {
            this.inlineMessageId = inlineMessageId;
            this.replyMarkup = replyMarkup;
        }

        public static final int CONSTRUCTOR = -67565858;

        @Override
        public int getConstructor() {
            return -67565858;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("EditInlineMessageReplyMarkup").append(" {");
            shift += 2;
            appendLine(s, shift).append("inlineMessageId = ").append(inlineMessageId);
            appendLine(s, shift).append("replyMarkup = "); replyMarkup.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class EditInlineMessageText extends TLFunction {
        public String inlineMessageId;
        public ReplyMarkup replyMarkup;
        public InputMessageContent inputMessageContent;

        public EditInlineMessageText() {
        }

        public EditInlineMessageText(String inlineMessageId, ReplyMarkup replyMarkup, InputMessageContent inputMessageContent) {
            this.inlineMessageId = inlineMessageId;
            this.replyMarkup = replyMarkup;
            this.inputMessageContent = inputMessageContent;
        }

        public static final int CONSTRUCTOR = -855457307;

        @Override
        public int getConstructor() {
            return -855457307;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("EditInlineMessageText").append(" {");
            shift += 2;
            appendLine(s, shift).append("inlineMessageId = ").append(inlineMessageId);
            appendLine(s, shift).append("replyMarkup = "); replyMarkup.toStringBuilder(shift, s);
            appendLine(s, shift).append("inputMessageContent = "); inputMessageContent.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class EditMessageCaption extends TLFunction {
        public long chatId;
        public int messageId;
        public ReplyMarkup replyMarkup;
        public String caption;

        public EditMessageCaption() {
        }

        public EditMessageCaption(long chatId, int messageId, ReplyMarkup replyMarkup, String caption) {
            this.chatId = chatId;
            this.messageId = messageId;
            this.replyMarkup = replyMarkup;
            this.caption = caption;
        }

        public static final int CONSTRUCTOR = 702735393;

        @Override
        public int getConstructor() {
            return 702735393;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("EditMessageCaption").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("messageId = ").append(messageId);
            appendLine(s, shift).append("replyMarkup = "); replyMarkup.toStringBuilder(shift, s);
            appendLine(s, shift).append("caption = ").append(caption);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class EditMessageReplyMarkup extends TLFunction {
        public long chatId;
        public int messageId;
        public ReplyMarkup replyMarkup;

        public EditMessageReplyMarkup() {
        }

        public EditMessageReplyMarkup(long chatId, int messageId, ReplyMarkup replyMarkup) {
            this.chatId = chatId;
            this.messageId = messageId;
            this.replyMarkup = replyMarkup;
        }

        public static final int CONSTRUCTOR = -1344470531;

        @Override
        public int getConstructor() {
            return -1344470531;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("EditMessageReplyMarkup").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("messageId = ").append(messageId);
            appendLine(s, shift).append("replyMarkup = "); replyMarkup.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class EditMessageText extends TLFunction {
        public long chatId;
        public int messageId;
        public ReplyMarkup replyMarkup;
        public InputMessageContent inputMessageContent;

        public EditMessageText() {
        }

        public EditMessageText(long chatId, int messageId, ReplyMarkup replyMarkup, InputMessageContent inputMessageContent) {
            this.chatId = chatId;
            this.messageId = messageId;
            this.replyMarkup = replyMarkup;
            this.inputMessageContent = inputMessageContent;
        }

        public static final int CONSTRUCTOR = 1316368529;

        @Override
        public int getConstructor() {
            return 1316368529;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("EditMessageText").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("messageId = ").append(messageId);
            appendLine(s, shift).append("replyMarkup = "); replyMarkup.toStringBuilder(shift, s);
            appendLine(s, shift).append("inputMessageContent = "); inputMessageContent.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ExportChatInviteLink extends TLFunction {
        public long chatId;

        public ExportChatInviteLink() {
        }

        public ExportChatInviteLink(long chatId) {
            this.chatId = chatId;
        }

        public static final int CONSTRUCTOR = 1549493828;

        @Override
        public int getConstructor() {
            return 1549493828;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ExportChatInviteLink").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ForwardMessages extends TLFunction {
        public long chatId;
        public long fromChatId;
        public int[] messageIds;
        public boolean disableNotification;
        public boolean fromBackground;

        public ForwardMessages() {
        }

        public ForwardMessages(long chatId, long fromChatId, int[] messageIds, boolean disableNotification, boolean fromBackground) {
            this.chatId = chatId;
            this.fromChatId = fromChatId;
            this.messageIds = messageIds;
            this.disableNotification = disableNotification;
            this.fromBackground = fromBackground;
        }

        public static final int CONSTRUCTOR = 716825012;

        @Override
        public int getConstructor() {
            return 716825012;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ForwardMessages").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("fromChatId = ").append(fromChatId);
            appendLine(s, shift).append("messageIds = ").append(Arrays.toString(messageIds));
            appendLine(s, shift).append("disableNotification = ").append(disableNotification);
            appendLine(s, shift).append("fromBackground = ").append(fromBackground);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetAccountTtl extends TLFunction {

        public GetAccountTtl() {
        }

        public static final int CONSTRUCTOR = -443905161;

        @Override
        public int getConstructor() {
            return -443905161;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetAccountTtl").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetAuthState extends TLFunction {

        public GetAuthState() {
        }

        public static final int CONSTRUCTOR = 1193342487;

        @Override
        public int getConstructor() {
            return 1193342487;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetAuthState").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetAuthorizations extends TLFunction {

        public GetAuthorizations() {
        }

        public static final int CONSTRUCTOR = -1232858954;

        @Override
        public int getConstructor() {
            return -1232858954;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetAuthorizations").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetBlockedUsers extends TLFunction {
        public int offset;
        public int limit;

        public GetBlockedUsers() {
        }

        public GetBlockedUsers(int offset, int limit) {
            this.offset = offset;
            this.limit = limit;
        }

        public static final int CONSTRUCTOR = 9255073;

        @Override
        public int getConstructor() {
            return 9255073;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetBlockedUsers").append(" {");
            shift += 2;
            appendLine(s, shift).append("offset = ").append(offset);
            appendLine(s, shift).append("limit = ").append(limit);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetCallbackQueryAnswer extends TLFunction {
        public long chatId;
        public int messageId;
        public CallbackQueryPayload payload;

        public GetCallbackQueryAnswer() {
        }

        public GetCallbackQueryAnswer(long chatId, int messageId, CallbackQueryPayload payload) {
            this.chatId = chatId;
            this.messageId = messageId;
            this.payload = payload;
        }

        public static final int CONSTRUCTOR = 693485097;

        @Override
        public int getConstructor() {
            return 693485097;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetCallbackQueryAnswer").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("messageId = ").append(messageId);
            appendLine(s, shift).append("payload = "); payload.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetChannel extends TLFunction {
        public int channelId;

        public GetChannel() {
        }

        public GetChannel(int channelId) {
            this.channelId = channelId;
        }

        public static final int CONSTRUCTOR = 1117537550;

        @Override
        public int getConstructor() {
            return 1117537550;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetChannel").append(" {");
            shift += 2;
            appendLine(s, shift).append("channelId = ").append(channelId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetChannelFull extends TLFunction {
        public int channelId;

        public GetChannelFull() {
        }

        public GetChannelFull(int channelId) {
            this.channelId = channelId;
        }

        public static final int CONSTRUCTOR = -704893497;

        @Override
        public int getConstructor() {
            return -704893497;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetChannelFull").append(" {");
            shift += 2;
            appendLine(s, shift).append("channelId = ").append(channelId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetChannelMembers extends TLFunction {
        public int channelId;
        public ChannelMembersFilter filter;
        public int offset;
        public int limit;

        public GetChannelMembers() {
        }

        public GetChannelMembers(int channelId, ChannelMembersFilter filter, int offset, int limit) {
            this.channelId = channelId;
            this.filter = filter;
            this.offset = offset;
            this.limit = limit;
        }

        public static final int CONSTRUCTOR = 2116707652;

        @Override
        public int getConstructor() {
            return 2116707652;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetChannelMembers").append(" {");
            shift += 2;
            appendLine(s, shift).append("channelId = ").append(channelId);
            appendLine(s, shift).append("filter = "); filter.toStringBuilder(shift, s);
            appendLine(s, shift).append("offset = ").append(offset);
            appendLine(s, shift).append("limit = ").append(limit);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetChat extends TLFunction {
        public long chatId;

        public GetChat() {
        }

        public GetChat(long chatId) {
            this.chatId = chatId;
        }

        public static final int CONSTRUCTOR = -1645526841;

        @Override
        public int getConstructor() {
            return -1645526841;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetChat").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetChatHistory extends TLFunction {
        public long chatId;
        public int fromMessageId;
        public int offset;
        public int limit;

        public GetChatHistory() {
        }

        public GetChatHistory(long chatId, int fromMessageId, int offset, int limit) {
            this.chatId = chatId;
            this.fromMessageId = fromMessageId;
            this.offset = offset;
            this.limit = limit;
        }

        public static final int CONSTRUCTOR = 851842897;

        @Override
        public int getConstructor() {
            return 851842897;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetChatHistory").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("fromMessageId = ").append(fromMessageId);
            appendLine(s, shift).append("offset = ").append(offset);
            appendLine(s, shift).append("limit = ").append(limit);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetChatMember extends TLFunction {
        public long chatId;
        public int userId;

        public GetChatMember() {
        }

        public GetChatMember(long chatId, int userId) {
            this.chatId = chatId;
            this.userId = userId;
        }

        public static final int CONSTRUCTOR = 1885026956;

        @Override
        public int getConstructor() {
            return 1885026956;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetChatMember").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("userId = ").append(userId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetChats extends TLFunction {
        public long offsetOrder;
        public long offsetChatId;
        public int limit;

        public GetChats() {
        }

        public GetChats(long offsetOrder, long offsetChatId, int limit) {
            this.offsetOrder = offsetOrder;
            this.offsetChatId = offsetChatId;
            this.limit = limit;
        }

        public static final int CONSTRUCTOR = 1867515173;

        @Override
        public int getConstructor() {
            return 1867515173;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetChats").append(" {");
            shift += 2;
            appendLine(s, shift).append("offsetOrder = ").append(offsetOrder);
            appendLine(s, shift).append("offsetChatId = ").append(offsetChatId);
            appendLine(s, shift).append("limit = ").append(limit);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetFile extends TLFunction {
        public int fileId;

        public GetFile() {
        }

        public GetFile(int fileId) {
            this.fileId = fileId;
        }

        public static final int CONSTRUCTOR = -225569621;

        @Override
        public int getConstructor() {
            return -225569621;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetFile").append(" {");
            shift += 2;
            appendLine(s, shift).append("fileId = ").append(fileId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetFilePersistent extends TLFunction {
        public String persistentFileId;

        public GetFilePersistent() {
        }

        public GetFilePersistent(String persistentFileId) {
            this.persistentFileId = persistentFileId;
        }

        public static final int CONSTRUCTOR = -1202286332;

        @Override
        public int getConstructor() {
            return -1202286332;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetFilePersistent").append(" {");
            shift += 2;
            appendLine(s, shift).append("persistentFileId = ").append(persistentFileId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetGameHighScores extends TLFunction {
        public long chatId;
        public int messageId;
        public int userId;

        public GetGameHighScores() {
        }

        public GetGameHighScores(long chatId, int messageId, int userId) {
            this.chatId = chatId;
            this.messageId = messageId;
            this.userId = userId;
        }

        public static final int CONSTRUCTOR = 1287568611;

        @Override
        public int getConstructor() {
            return 1287568611;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetGameHighScores").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("messageId = ").append(messageId);
            appendLine(s, shift).append("userId = ").append(userId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetGroup extends TLFunction {
        public int groupId;

        public GetGroup() {
        }

        public GetGroup(int groupId) {
            this.groupId = groupId;
        }

        public static final int CONSTRUCTOR = 1642068863;

        @Override
        public int getConstructor() {
            return 1642068863;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetGroup").append(" {");
            shift += 2;
            appendLine(s, shift).append("groupId = ").append(groupId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetGroupFull extends TLFunction {
        public int groupId;

        public GetGroupFull() {
        }

        public GetGroupFull(int groupId) {
            this.groupId = groupId;
        }

        public static final int CONSTRUCTOR = -1459161427;

        @Override
        public int getConstructor() {
            return -1459161427;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetGroupFull").append(" {");
            shift += 2;
            appendLine(s, shift).append("groupId = ").append(groupId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetInlineGameHighScores extends TLFunction {
        public String inlineMessageId;
        public int userId;

        public GetInlineGameHighScores() {
        }

        public GetInlineGameHighScores(String inlineMessageId, int userId) {
            this.inlineMessageId = inlineMessageId;
            this.userId = userId;
        }

        public static final int CONSTRUCTOR = 1458552156;

        @Override
        public int getConstructor() {
            return 1458552156;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetInlineGameHighScores").append(" {");
            shift += 2;
            appendLine(s, shift).append("inlineMessageId = ").append(inlineMessageId);
            appendLine(s, shift).append("userId = ").append(userId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetInlineQueryResults extends TLFunction {
        public int botUserId;
        public long chatId;
        public Location userLocation;
        public String query;
        public String offset;

        public GetInlineQueryResults() {
        }

        public GetInlineQueryResults(int botUserId, long chatId, Location userLocation, String query, String offset) {
            this.botUserId = botUserId;
            this.chatId = chatId;
            this.userLocation = userLocation;
            this.query = query;
            this.offset = offset;
        }

        public static final int CONSTRUCTOR = 992897566;

        @Override
        public int getConstructor() {
            return 992897566;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetInlineQueryResults").append(" {");
            shift += 2;
            appendLine(s, shift).append("botUserId = ").append(botUserId);
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("userLocation = "); userLocation.toStringBuilder(shift, s);
            appendLine(s, shift).append("query = ").append(query);
            appendLine(s, shift).append("offset = ").append(offset);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetMe extends TLFunction {

        public GetMe() {
        }

        public static final int CONSTRUCTOR = -191516033;

        @Override
        public int getConstructor() {
            return -191516033;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetMe").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetMessage extends TLFunction {
        public long chatId;
        public int messageId;

        public GetMessage() {
        }

        public GetMessage(long chatId, int messageId) {
            this.chatId = chatId;
            this.messageId = messageId;
        }

        public static final int CONSTRUCTOR = -1209218520;

        @Override
        public int getConstructor() {
            return -1209218520;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetMessage").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("messageId = ").append(messageId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetMessages extends TLFunction {
        public long chatId;
        public int[] messageIds;

        public GetMessages() {
        }

        public GetMessages(long chatId, int[] messageIds) {
            this.chatId = chatId;
            this.messageIds = messageIds;
        }

        public static final int CONSTRUCTOR = 1391199071;

        @Override
        public int getConstructor() {
            return 1391199071;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetMessages").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("messageIds = ").append(Arrays.toString(messageIds));
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetNotificationSettings extends TLFunction {
        public NotificationSettingsScope scope;

        public GetNotificationSettings() {
        }

        public GetNotificationSettings(NotificationSettingsScope scope) {
            this.scope = scope;
        }

        public static final int CONSTRUCTOR = 907144391;

        @Override
        public int getConstructor() {
            return 907144391;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetNotificationSettings").append(" {");
            shift += 2;
            appendLine(s, shift).append("scope = "); scope.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetOption extends TLFunction {
        public String name;

        public GetOption() {
        }

        public GetOption(String name) {
            this.name = name;
        }

        public static final int CONSTRUCTOR = -1572495746;

        @Override
        public int getConstructor() {
            return -1572495746;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetOption").append(" {");
            shift += 2;
            appendLine(s, shift).append("name = ").append(name);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetRecentInlineBots extends TLFunction {

        public GetRecentInlineBots() {
        }

        public static final int CONSTRUCTOR = 1437823548;

        @Override
        public int getConstructor() {
            return 1437823548;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetRecentInlineBots").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetSavedAnimations extends TLFunction {

        public GetSavedAnimations() {
        }

        public static final int CONSTRUCTOR = 7051032;

        @Override
        public int getConstructor() {
            return 7051032;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetSavedAnimations").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetStickerSet extends TLFunction {
        public long setId;

        public GetStickerSet() {
        }

        public GetStickerSet(long setId) {
            this.setId = setId;
        }

        public static final int CONSTRUCTOR = 1684803767;

        @Override
        public int getConstructor() {
            return 1684803767;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetStickerSet").append(" {");
            shift += 2;
            appendLine(s, shift).append("setId = ").append(setId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetStickerSets extends TLFunction {
        public boolean onlyEnabled;

        public GetStickerSets() {
        }

        public GetStickerSets(boolean onlyEnabled) {
            this.onlyEnabled = onlyEnabled;
        }

        public static final int CONSTRUCTOR = -700104885;

        @Override
        public int getConstructor() {
            return -700104885;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetStickerSets").append(" {");
            shift += 2;
            appendLine(s, shift).append("onlyEnabled = ").append(onlyEnabled);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetStickers extends TLFunction {
        public String emoji;

        public GetStickers() {
        }

        public GetStickers(String emoji) {
            this.emoji = emoji;
        }

        public static final int CONSTRUCTOR = 1712531528;

        @Override
        public int getConstructor() {
            return 1712531528;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetStickers").append(" {");
            shift += 2;
            appendLine(s, shift).append("emoji = ").append(emoji);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetSupportUser extends TLFunction {

        public GetSupportUser() {
        }

        public static final int CONSTRUCTOR = -1733497700;

        @Override
        public int getConstructor() {
            return -1733497700;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetSupportUser").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetUser extends TLFunction {
        public int userId;

        public GetUser() {
        }

        public GetUser(int userId) {
            this.userId = userId;
        }

        public static final int CONSTRUCTOR = -501534519;

        @Override
        public int getConstructor() {
            return -501534519;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetUser").append(" {");
            shift += 2;
            appendLine(s, shift).append("userId = ").append(userId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetUserFull extends TLFunction {
        public int userId;

        public GetUserFull() {
        }

        public GetUserFull(int userId) {
            this.userId = userId;
        }

        public static final int CONSTRUCTOR = -1977480168;

        @Override
        public int getConstructor() {
            return -1977480168;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetUserFull").append(" {");
            shift += 2;
            appendLine(s, shift).append("userId = ").append(userId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetUserProfilePhotos extends TLFunction {
        public int userId;
        public int offset;
        public int limit;

        public GetUserProfilePhotos() {
        }

        public GetUserProfilePhotos(int userId, int offset, int limit) {
            this.userId = userId;
            this.offset = offset;
            this.limit = limit;
        }

        public static final int CONSTRUCTOR = 1810450184;

        @Override
        public int getConstructor() {
            return 1810450184;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetUserProfilePhotos").append(" {");
            shift += 2;
            appendLine(s, shift).append("userId = ").append(userId);
            appendLine(s, shift).append("offset = ").append(offset);
            appendLine(s, shift).append("limit = ").append(limit);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetWallpapers extends TLFunction {

        public GetWallpapers() {
        }

        public static final int CONSTRUCTOR = 2097518555;

        @Override
        public int getConstructor() {
            return 2097518555;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetWallpapers").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class GetWebPagePreview extends TLFunction {
        public String messageText;

        public GetWebPagePreview() {
        }

        public GetWebPagePreview(String messageText) {
            this.messageText = messageText;
        }

        public static final int CONSTRUCTOR = 1322216444;

        @Override
        public int getConstructor() {
            return 1322216444;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetWebPagePreview").append(" {");
            shift += 2;
            appendLine(s, shift).append("messageText = ").append(messageText);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ImportChatInviteLink extends TLFunction {
        public String inviteLink;

        public ImportChatInviteLink() {
        }

        public ImportChatInviteLink(String inviteLink) {
            this.inviteLink = inviteLink;
        }

        public static final int CONSTRUCTOR = 1824153031;

        @Override
        public int getConstructor() {
            return 1824153031;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ImportChatInviteLink").append(" {");
            shift += 2;
            appendLine(s, shift).append("inviteLink = ").append(inviteLink);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ImportContacts extends TLFunction {
        public Contact[] contacts;

        public ImportContacts() {
        }

        public ImportContacts(Contact[] contacts) {
            this.contacts = contacts;
        }

        public static final int CONSTRUCTOR = -1365609265;

        @Override
        public int getConstructor() {
            return -1365609265;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ImportContacts").append(" {");
            shift += 2;
            appendLine(s, shift).append("contacts = ").append(Arrays.toString(contacts));
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class MigrateGroupChatToChannelChat extends TLFunction {
        public long chatId;

        public MigrateGroupChatToChannelChat() {
        }

        public MigrateGroupChatToChannelChat(long chatId) {
            this.chatId = chatId;
        }

        public static final int CONSTRUCTOR = 1374124771;

        @Override
        public int getConstructor() {
            return 1374124771;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MigrateGroupChatToChannelChat").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class OpenChat extends TLFunction {
        public long chatId;

        public OpenChat() {
        }

        public OpenChat(long chatId) {
            this.chatId = chatId;
        }

        public static final int CONSTRUCTOR = -1638354005;

        @Override
        public int getConstructor() {
            return -1638354005;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("OpenChat").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class OpenMessageContent extends TLFunction {
        public long chatId;
        public int messageId;

        public OpenMessageContent() {
        }

        public OpenMessageContent(long chatId, int messageId) {
            this.chatId = chatId;
            this.messageId = messageId;
        }

        public static final int CONSTRUCTOR = -318527532;

        @Override
        public int getConstructor() {
            return -318527532;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("OpenMessageContent").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("messageId = ").append(messageId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class PinChannelMessage extends TLFunction {
        public int channelId;
        public int messageId;
        public boolean disableNotification;

        public PinChannelMessage() {
        }

        public PinChannelMessage(int channelId, int messageId, boolean disableNotification) {
            this.channelId = channelId;
            this.messageId = messageId;
            this.disableNotification = disableNotification;
        }

        public static final int CONSTRUCTOR = -2024026179;

        @Override
        public int getConstructor() {
            return -2024026179;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("PinChannelMessage").append(" {");
            shift += 2;
            appendLine(s, shift).append("channelId = ").append(channelId);
            appendLine(s, shift).append("messageId = ").append(messageId);
            appendLine(s, shift).append("disableNotification = ").append(disableNotification);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class RecoverAuthPassword extends TLFunction {
        public String recoverCode;

        public RecoverAuthPassword() {
        }

        public RecoverAuthPassword(String recoverCode) {
            this.recoverCode = recoverCode;
        }

        public static final int CONSTRUCTOR = 130965839;

        @Override
        public int getConstructor() {
            return 130965839;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("RecoverAuthPassword").append(" {");
            shift += 2;
            appendLine(s, shift).append("recoverCode = ").append(recoverCode);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class RequestAuthPasswordRecovery extends TLFunction {

        public RequestAuthPasswordRecovery() {
        }

        public static final int CONSTRUCTOR = -1561685090;

        @Override
        public int getConstructor() {
            return -1561685090;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("RequestAuthPasswordRecovery").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ResendAuthCode extends TLFunction {

        public ResendAuthCode() {
        }

        public static final int CONSTRUCTOR = -2105917663;

        @Override
        public int getConstructor() {
            return -2105917663;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ResendAuthCode").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ResendChangePhoneNumberCode extends TLFunction {

        public ResendChangePhoneNumberCode() {
        }

        public static final int CONSTRUCTOR = -866825426;

        @Override
        public int getConstructor() {
            return -866825426;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ResendChangePhoneNumberCode").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ResetAuth extends TLFunction {
        public boolean force;

        public ResetAuth() {
        }

        public ResetAuth(boolean force) {
            this.force = force;
        }

        public static final int CONSTRUCTOR = -78661379;

        @Override
        public int getConstructor() {
            return -78661379;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ResetAuth").append(" {");
            shift += 2;
            appendLine(s, shift).append("force = ").append(force);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SearchChatMessages extends TLFunction {
        public long chatId;
        public String query;
        public int fromMessageId;
        public int limit;
        public SearchMessagesFilter filter;

        public SearchChatMessages() {
        }

        public SearchChatMessages(long chatId, String query, int fromMessageId, int limit, SearchMessagesFilter filter) {
            this.chatId = chatId;
            this.query = query;
            this.fromMessageId = fromMessageId;
            this.limit = limit;
            this.filter = filter;
        }

        public static final int CONSTRUCTOR = -12337489;

        @Override
        public int getConstructor() {
            return -12337489;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchChatMessages").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("query = ").append(query);
            appendLine(s, shift).append("fromMessageId = ").append(fromMessageId);
            appendLine(s, shift).append("limit = ").append(limit);
            appendLine(s, shift).append("filter = "); filter.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SearchChats extends TLFunction {
        public String query;
        public int limit;

        public SearchChats() {
        }

        public SearchChats(String query, int limit) {
            this.query = query;
            this.limit = limit;
        }

        public static final int CONSTRUCTOR = 344296595;

        @Override
        public int getConstructor() {
            return 344296595;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchChats").append(" {");
            shift += 2;
            appendLine(s, shift).append("query = ").append(query);
            appendLine(s, shift).append("limit = ").append(limit);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SearchContacts extends TLFunction {
        public String query;
        public int limit;

        public SearchContacts() {
        }

        public SearchContacts(String query, int limit) {
            this.query = query;
            this.limit = limit;
        }

        public static final int CONSTRUCTOR = 511929675;

        @Override
        public int getConstructor() {
            return 511929675;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchContacts").append(" {");
            shift += 2;
            appendLine(s, shift).append("query = ").append(query);
            appendLine(s, shift).append("limit = ").append(limit);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SearchMessages extends TLFunction {
        public String query;
        public int offsetDate;
        public long offsetChatId;
        public int offsetMessageId;
        public int limit;

        public SearchMessages() {
        }

        public SearchMessages(String query, int offsetDate, long offsetChatId, int offsetMessageId, int limit) {
            this.query = query;
            this.offsetDate = offsetDate;
            this.offsetChatId = offsetChatId;
            this.offsetMessageId = offsetMessageId;
            this.limit = limit;
        }

        public static final int CONSTRUCTOR = -65689397;

        @Override
        public int getConstructor() {
            return -65689397;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchMessages").append(" {");
            shift += 2;
            appendLine(s, shift).append("query = ").append(query);
            appendLine(s, shift).append("offsetDate = ").append(offsetDate);
            appendLine(s, shift).append("offsetChatId = ").append(offsetChatId);
            appendLine(s, shift).append("offsetMessageId = ").append(offsetMessageId);
            appendLine(s, shift).append("limit = ").append(limit);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SearchPublicChat extends TLFunction {
        public String username;

        public SearchPublicChat() {
        }

        public SearchPublicChat(String username) {
            this.username = username;
        }

        public static final int CONSTRUCTOR = 857135533;

        @Override
        public int getConstructor() {
            return 857135533;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchPublicChat").append(" {");
            shift += 2;
            appendLine(s, shift).append("username = ").append(username);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SearchPublicChats extends TLFunction {
        public String usernamePrefix;

        public SearchPublicChats() {
        }

        public SearchPublicChats(String usernamePrefix) {
            this.usernamePrefix = usernamePrefix;
        }

        public static final int CONSTRUCTOR = 505050556;

        @Override
        public int getConstructor() {
            return 505050556;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchPublicChats").append(" {");
            shift += 2;
            appendLine(s, shift).append("usernamePrefix = ").append(usernamePrefix);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SearchStickerSet extends TLFunction {
        public String name;

        public SearchStickerSet() {
        }

        public SearchStickerSet(String name) {
            this.name = name;
        }

        public static final int CONSTRUCTOR = 1157930222;

        @Override
        public int getConstructor() {
            return 1157930222;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchStickerSet").append(" {");
            shift += 2;
            appendLine(s, shift).append("name = ").append(name);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SendBotStartMessage extends TLFunction {
        public int botUserId;
        public long chatId;
        public String parameter;

        public SendBotStartMessage() {
        }

        public SendBotStartMessage(int botUserId, long chatId, String parameter) {
            this.botUserId = botUserId;
            this.chatId = chatId;
            this.parameter = parameter;
        }

        public static final int CONSTRUCTOR = -952608730;

        @Override
        public int getConstructor() {
            return -952608730;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SendBotStartMessage").append(" {");
            shift += 2;
            appendLine(s, shift).append("botUserId = ").append(botUserId);
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("parameter = ").append(parameter);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SendChatAction extends TLFunction {
        public long chatId;
        public SendMessageAction action;

        public SendChatAction() {
        }

        public SendChatAction(long chatId, SendMessageAction action) {
            this.chatId = chatId;
            this.action = action;
        }

        public static final int CONSTRUCTOR = 346586363;

        @Override
        public int getConstructor() {
            return 346586363;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SendChatAction").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("action = "); action.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SendInlineQueryResultMessage extends TLFunction {
        public long chatId;
        public int replyToMessageId;
        public boolean disableNotification;
        public boolean fromBackground;
        public long queryId;
        public String resultId;

        public SendInlineQueryResultMessage() {
        }

        public SendInlineQueryResultMessage(long chatId, int replyToMessageId, boolean disableNotification, boolean fromBackground, long queryId, String resultId) {
            this.chatId = chatId;
            this.replyToMessageId = replyToMessageId;
            this.disableNotification = disableNotification;
            this.fromBackground = fromBackground;
            this.queryId = queryId;
            this.resultId = resultId;
        }

        public static final int CONSTRUCTOR = -1342090907;

        @Override
        public int getConstructor() {
            return -1342090907;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SendInlineQueryResultMessage").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("replyToMessageId = ").append(replyToMessageId);
            appendLine(s, shift).append("disableNotification = ").append(disableNotification);
            appendLine(s, shift).append("fromBackground = ").append(fromBackground);
            appendLine(s, shift).append("queryId = ").append(queryId);
            appendLine(s, shift).append("resultId = ").append(resultId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SendMessage extends TLFunction {
        public long chatId;
        public int replyToMessageId;
        public boolean disableNotification;
        public boolean fromBackground;
        public ReplyMarkup replyMarkup;
        public InputMessageContent inputMessageContent;

        public SendMessage() {
        }

        public SendMessage(long chatId, int replyToMessageId, boolean disableNotification, boolean fromBackground, ReplyMarkup replyMarkup, InputMessageContent inputMessageContent) {
            this.chatId = chatId;
            this.replyToMessageId = replyToMessageId;
            this.disableNotification = disableNotification;
            this.fromBackground = fromBackground;
            this.replyMarkup = replyMarkup;
            this.inputMessageContent = inputMessageContent;
        }

        public static final int CONSTRUCTOR = 777848191;

        @Override
        public int getConstructor() {
            return 777848191;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SendMessage").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("replyToMessageId = ").append(replyToMessageId);
            appendLine(s, shift).append("disableNotification = ").append(disableNotification);
            appendLine(s, shift).append("fromBackground = ").append(fromBackground);
            appendLine(s, shift).append("replyMarkup = "); replyMarkup.toStringBuilder(shift, s);
            appendLine(s, shift).append("inputMessageContent = "); inputMessageContent.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SetAlarm extends TLFunction {
        public double seconds;

        public SetAlarm() {
        }

        public SetAlarm(double seconds) {
            this.seconds = seconds;
        }

        public static final int CONSTRUCTOR = -873497067;

        @Override
        public int getConstructor() {
            return -873497067;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SetAlarm").append(" {");
            shift += 2;
            appendLine(s, shift).append("seconds = ").append(seconds);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SetAuthPhoneNumber extends TLFunction {
        public String phoneNumber;
        public boolean allowFlashCall;
        public boolean isCurrentPhoneNumber;

        public SetAuthPhoneNumber() {
        }

        public SetAuthPhoneNumber(String phoneNumber, boolean allowFlashCall, boolean isCurrentPhoneNumber) {
            this.phoneNumber = phoneNumber;
            this.allowFlashCall = allowFlashCall;
            this.isCurrentPhoneNumber = isCurrentPhoneNumber;
        }

        public static final int CONSTRUCTOR = 270883354;

        @Override
        public int getConstructor() {
            return 270883354;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SetAuthPhoneNumber").append(" {");
            shift += 2;
            appendLine(s, shift).append("phoneNumber = ").append(phoneNumber);
            appendLine(s, shift).append("allowFlashCall = ").append(allowFlashCall);
            appendLine(s, shift).append("isCurrentPhoneNumber = ").append(isCurrentPhoneNumber);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SetGameScore extends TLFunction {
        public long chatId;
        public int messageId;
        public boolean editMessage;
        public int userId;
        public int score;

        public SetGameScore() {
        }

        public SetGameScore(long chatId, int messageId, boolean editMessage, int userId, int score) {
            this.chatId = chatId;
            this.messageId = messageId;
            this.editMessage = editMessage;
            this.userId = userId;
            this.score = score;
        }

        public static final int CONSTRUCTOR = 395584402;

        @Override
        public int getConstructor() {
            return 395584402;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SetGameScore").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("messageId = ").append(messageId);
            appendLine(s, shift).append("editMessage = ").append(editMessage);
            appendLine(s, shift).append("userId = ").append(userId);
            appendLine(s, shift).append("score = ").append(score);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SetInlineGameScore extends TLFunction {
        public String inlineMessageId;
        public boolean editMessage;
        public int userId;
        public int score;

        public SetInlineGameScore() {
        }

        public SetInlineGameScore(String inlineMessageId, boolean editMessage, int userId, int score) {
            this.inlineMessageId = inlineMessageId;
            this.editMessage = editMessage;
            this.userId = userId;
            this.score = score;
        }

        public static final int CONSTRUCTOR = 1403858858;

        @Override
        public int getConstructor() {
            return 1403858858;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SetInlineGameScore").append(" {");
            shift += 2;
            appendLine(s, shift).append("inlineMessageId = ").append(inlineMessageId);
            appendLine(s, shift).append("editMessage = ").append(editMessage);
            appendLine(s, shift).append("userId = ").append(userId);
            appendLine(s, shift).append("score = ").append(score);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SetNotificationSettings extends TLFunction {
        public NotificationSettingsScope scope;
        public NotificationSettings notificationSettings;

        public SetNotificationSettings() {
        }

        public SetNotificationSettings(NotificationSettingsScope scope, NotificationSettings notificationSettings) {
            this.scope = scope;
            this.notificationSettings = notificationSettings;
        }

        public static final int CONSTRUCTOR = -134430483;

        @Override
        public int getConstructor() {
            return -134430483;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SetNotificationSettings").append(" {");
            shift += 2;
            appendLine(s, shift).append("scope = "); scope.toStringBuilder(shift, s);
            appendLine(s, shift).append("notificationSettings = "); notificationSettings.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SetOption extends TLFunction {
        public String name;
        public OptionValue value;

        public SetOption() {
        }

        public SetOption(String name, OptionValue value) {
            this.name = name;
            this.value = value;
        }

        public static final int CONSTRUCTOR = 2114670322;

        @Override
        public int getConstructor() {
            return 2114670322;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SetOption").append(" {");
            shift += 2;
            appendLine(s, shift).append("name = ").append(name);
            appendLine(s, shift).append("value = "); value.toStringBuilder(shift, s);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class SetProfilePhoto extends TLFunction {
        public String photoPath;

        public SetProfilePhoto() {
        }

        public SetProfilePhoto(String photoPath) {
            this.photoPath = photoPath;
        }

        public static final int CONSTRUCTOR = 508736004;

        @Override
        public int getConstructor() {
            return 508736004;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SetProfilePhoto").append(" {");
            shift += 2;
            appendLine(s, shift).append("photoPath = ").append(photoPath);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class TestCallBytes extends TLFunction {
        public byte[] x;

        public TestCallBytes() {
        }

        public TestCallBytes(byte[] x) {
            this.x = x;
        }

        public static final int CONSTRUCTOR = -372062627;

        @Override
        public int getConstructor() {
            return -372062627;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestCallBytes").append(" {");
            shift += 2;
            appendLine(s, shift).append("x = ").append("bytes { "); { for (byte k : x) { int b = (int)k & 255; s.append(HEX_CHARACTERS[b >> 4]).append(HEX_CHARACTERS[b & 15]).append(' '); } } s.append('}');
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class TestCallEmpty extends TLFunction {

        public TestCallEmpty() {
        }

        public static final int CONSTRUCTOR = 482209969;

        @Override
        public int getConstructor() {
            return 482209969;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestCallEmpty").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class TestCallString extends TLFunction {
        public String x;

        public TestCallString() {
        }

        public TestCallString(String x) {
            this.x = x;
        }

        public static final int CONSTRUCTOR = -1706148074;

        @Override
        public int getConstructor() {
            return -1706148074;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestCallString").append(" {");
            shift += 2;
            appendLine(s, shift).append("x = ").append(x);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class TestCallVectorInt extends TLFunction {
        public int[] x;

        public TestCallVectorInt() {
        }

        public TestCallVectorInt(int[] x) {
            this.x = x;
        }

        public static final int CONSTRUCTOR = 1339160226;

        @Override
        public int getConstructor() {
            return 1339160226;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestCallVectorInt").append(" {");
            shift += 2;
            appendLine(s, shift).append("x = ").append(Arrays.toString(x));
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class TestCallVectorIntObject extends TLFunction {
        public TestInt[] x;

        public TestCallVectorIntObject() {
        }

        public TestCallVectorIntObject(TestInt[] x) {
            this.x = x;
        }

        public static final int CONSTRUCTOR = -918272776;

        @Override
        public int getConstructor() {
            return -918272776;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestCallVectorIntObject").append(" {");
            shift += 2;
            appendLine(s, shift).append("x = ").append(Arrays.toString(x));
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class TestCallVectorString extends TLFunction {
        public String[] x;

        public TestCallVectorString() {
        }

        public TestCallVectorString(String[] x) {
            this.x = x;
        }

        public static final int CONSTRUCTOR = 1331730807;

        @Override
        public int getConstructor() {
            return 1331730807;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestCallVectorString").append(" {");
            shift += 2;
            appendLine(s, shift).append("x = ").append(Arrays.toString(x));
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class TestCallVectorStringObject extends TLFunction {
        public TestString[] x;

        public TestCallVectorStringObject() {
        }

        public TestCallVectorStringObject(TestString[] x) {
            this.x = x;
        }

        public static final int CONSTRUCTOR = 178414472;

        @Override
        public int getConstructor() {
            return 178414472;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestCallVectorStringObject").append(" {");
            shift += 2;
            appendLine(s, shift).append("x = ").append(Arrays.toString(x));
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class TestSquareInt extends TLFunction {
        public int x;

        public TestSquareInt() {
        }

        public TestSquareInt(int x) {
            this.x = x;
        }

        public static final int CONSTRUCTOR = -924631417;

        @Override
        public int getConstructor() {
            return -924631417;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestSquareInt").append(" {");
            shift += 2;
            appendLine(s, shift).append("x = ").append(x);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class TestTestNet extends TLFunction {

        public TestTestNet() {
        }

        public static final int CONSTRUCTOR = -1597860997;

        @Override
        public int getConstructor() {
            return -1597860997;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestTestNet").append(" {");
            shift += 2;
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ToggleChannelInvites extends TLFunction {
        public int channelId;
        public boolean anyoneCanInvite;

        public ToggleChannelInvites() {
        }

        public ToggleChannelInvites(int channelId, boolean anyoneCanInvite) {
            this.channelId = channelId;
            this.anyoneCanInvite = anyoneCanInvite;
        }

        public static final int CONSTRUCTOR = 1454440041;

        @Override
        public int getConstructor() {
            return 1454440041;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ToggleChannelInvites").append(" {");
            shift += 2;
            appendLine(s, shift).append("channelId = ").append(channelId);
            appendLine(s, shift).append("anyoneCanInvite = ").append(anyoneCanInvite);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ToggleChannelSignMessages extends TLFunction {
        public int channelId;
        public boolean signMessages;

        public ToggleChannelSignMessages() {
        }

        public ToggleChannelSignMessages(int channelId, boolean signMessages) {
            this.channelId = channelId;
            this.signMessages = signMessages;
        }

        public static final int CONSTRUCTOR = -329182347;

        @Override
        public int getConstructor() {
            return -329182347;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ToggleChannelSignMessages").append(" {");
            shift += 2;
            appendLine(s, shift).append("channelId = ").append(channelId);
            appendLine(s, shift).append("signMessages = ").append(signMessages);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ToggleGroupEditors extends TLFunction {
        public int groupId;
        public boolean anyoneCanEdit;

        public ToggleGroupEditors() {
        }

        public ToggleGroupEditors(int groupId, boolean anyoneCanEdit) {
            this.groupId = groupId;
            this.anyoneCanEdit = anyoneCanEdit;
        }

        public static final int CONSTRUCTOR = 970466546;

        @Override
        public int getConstructor() {
            return 970466546;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ToggleGroupEditors").append(" {");
            shift += 2;
            appendLine(s, shift).append("groupId = ").append(groupId);
            appendLine(s, shift).append("anyoneCanEdit = ").append(anyoneCanEdit);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UnblockUser extends TLFunction {
        public int userId;

        public UnblockUser() {
        }

        public UnblockUser(int userId) {
            this.userId = userId;
        }

        public static final int CONSTRUCTOR = -50809642;

        @Override
        public int getConstructor() {
            return -50809642;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UnblockUser").append(" {");
            shift += 2;
            appendLine(s, shift).append("userId = ").append(userId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UnpinChannelMessage extends TLFunction {
        public int channelId;

        public UnpinChannelMessage() {
        }

        public UnpinChannelMessage(int channelId) {
            this.channelId = channelId;
        }

        public static final int CONSTRUCTOR = -1650033548;

        @Override
        public int getConstructor() {
            return -1650033548;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UnpinChannelMessage").append(" {");
            shift += 2;
            appendLine(s, shift).append("channelId = ").append(channelId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class UpdateStickerSet extends TLFunction {
        public long setId;
        public boolean isInstalled;
        public boolean isEnabled;

        public UpdateStickerSet() {
        }

        public UpdateStickerSet(long setId, boolean isInstalled, boolean isEnabled) {
            this.setId = setId;
            this.isInstalled = isInstalled;
            this.isEnabled = isEnabled;
        }

        public static final int CONSTRUCTOR = 64609373;

        @Override
        public int getConstructor() {
            return 64609373;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateStickerSet").append(" {");
            shift += 2;
            appendLine(s, shift).append("setId = ").append(setId);
            appendLine(s, shift).append("isInstalled = ").append(isInstalled);
            appendLine(s, shift).append("isEnabled = ").append(isEnabled);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    public static class ViewMessages extends TLFunction {
        public long chatId;
        public int[] messageIds;

        public ViewMessages() {
        }

        public ViewMessages(long chatId, int[] messageIds) {
            this.chatId = chatId;
            this.messageIds = messageIds;
        }

        public static final int CONSTRUCTOR = 1572261384;

        @Override
        public int getConstructor() {
            return 1572261384;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ViewMessages").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("messageIds = ").append(Arrays.toString(messageIds));
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

}
