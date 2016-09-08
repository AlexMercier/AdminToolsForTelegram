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

        protected abstract void toStringBuilder(int shift, StringBuilder s);
    }

    public abstract static class TLFunction extends TLObject {
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("Animation").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("width").append(" = ").append(width).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("height").append(" = ").append(height).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("fileName").append(" = ").append(fileName).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("mimeType").append(" = ").append(mimeType).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("thumb").append(" = "); thumb.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("animation").append(" = "); animation.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("Animations").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("animations").append(" = ").append("Animation[]").append(" {").append(Arrays.toString(animations)).append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("Audio").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("duration").append(" = ").append(duration).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("title").append(" = ").append(title).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("performer").append(" = ").append(performer).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("fileName").append(" = ").append(fileName).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("mimeType").append(" = ").append(mimeType).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("albumCoverThumb").append(" = "); albumCoverThumb.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("audio").append(" = "); audio.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("AuthStateWaitPhoneNumber").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class AuthStateWaitCode extends AuthState {

        public AuthStateWaitCode() {
        }

        public static final int CONSTRUCTOR = -1154394952;

        @Override
        public int getConstructor() {
            return -1154394952;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("AuthStateWaitCode").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class AuthStateWaitName extends AuthState {

        public AuthStateWaitName() {
        }

        public static final int CONSTRUCTOR = -245435948;

        @Override
        public int getConstructor() {
            return -245435948;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("AuthStateWaitName").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("AuthStateWaitPassword").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("hint").append(" = ").append(hint).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("hasRecovery").append(" = ").append(hasRecovery).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("emailUnconfirmedPattern").append(" = ").append(emailUnconfirmedPattern).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("AuthStateOk").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("AuthStateLoggingOut").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("BotCommand").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("command").append(" = ").append(command).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("description").append(" = ").append(description).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public abstract static class BotInfo extends TLObject {
    }

    public static class BotInfoEmpty extends BotInfo {

        public BotInfoEmpty() {
        }

        public static final int CONSTRUCTOR = -1154598962;

        @Override
        public int getConstructor() {
            return -1154598962;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("BotInfoEmpty").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class BotInfoGeneral extends BotInfo {
        public String shareText;
        public String description;
        public BotCommand[] commands;

        public BotInfoGeneral() {
        }

        public BotInfoGeneral(String shareText, String description, BotCommand[] commands) {
            this.shareText = shareText;
            this.description = description;
            this.commands = commands;
        }

        public static final int CONSTRUCTOR = 1269773691;

        @Override
        public int getConstructor() {
            return 1269773691;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("BotInfoGeneral").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("shareText").append(" = ").append(shareText).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("description").append(" = ").append(description).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("commands").append(" = ").append("BotCommand[]").append(" {").append(Arrays.toString(commands)).append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class Channel extends TLObject {
        public int id;
        public String username;
        public int date;
        public ChatParticipantRole role;
        public boolean anyoneCanInvite;
        public boolean signMessages;
        public boolean isBroadcast;
        public boolean isSupergroup;
        public boolean isVerified;
        public String restrictionReason;

        public Channel() {
        }

        public Channel(int id, String username, int date, ChatParticipantRole role, boolean anyoneCanInvite, boolean signMessages, boolean isBroadcast, boolean isSupergroup, boolean isVerified, String restrictionReason) {
            this.id = id;
            this.username = username;
            this.date = date;
            this.role = role;
            this.anyoneCanInvite = anyoneCanInvite;
            this.signMessages = signMessages;
            this.isBroadcast = isBroadcast;
            this.isSupergroup = isSupergroup;
            this.isVerified = isVerified;
            this.restrictionReason = restrictionReason;
        }

        public static final int CONSTRUCTOR = -167731781;

        @Override
        public int getConstructor() {
            return -167731781;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("Channel").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("id").append(" = ").append(id).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("username").append(" = ").append(username).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("date").append(" = ").append(date).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("role").append(" = "); role.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("anyoneCanInvite").append(" = ").append(anyoneCanInvite).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("signMessages").append(" = ").append(signMessages).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("isBroadcast").append(" = ").append(isBroadcast).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("isSupergroup").append(" = ").append(isSupergroup).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("isVerified").append(" = ").append(isVerified).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("restrictionReason").append(" = ").append(restrictionReason).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class ChannelFull extends TLObject {
        public Channel channel;
        public String about;
        public int participantsCount;
        public int adminsCount;
        public int kickedCount;
        public boolean canGetParticipants;
        public String inviteLink;
        public int migratedFromGroupId;
        public int migratedFromMaxMessageId;

        public ChannelFull() {
        }

        public ChannelFull(Channel channel, String about, int participantsCount, int adminsCount, int kickedCount, boolean canGetParticipants, String inviteLink, int migratedFromGroupId, int migratedFromMaxMessageId) {
            this.channel = channel;
            this.about = about;
            this.participantsCount = participantsCount;
            this.adminsCount = adminsCount;
            this.kickedCount = kickedCount;
            this.canGetParticipants = canGetParticipants;
            this.inviteLink = inviteLink;
            this.migratedFromGroupId = migratedFromGroupId;
            this.migratedFromMaxMessageId = migratedFromMaxMessageId;
        }

        public static final int CONSTRUCTOR = -236597239;

        @Override
        public int getConstructor() {
            return -236597239;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChannelFull").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("channel").append(" = "); channel.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("about").append(" = ").append(about).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("participantsCount").append(" = ").append(participantsCount).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("adminsCount").append(" = ").append(adminsCount).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("kickedCount").append(" = ").append(kickedCount).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("canGetParticipants").append(" = ").append(canGetParticipants).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("inviteLink").append(" = ").append(inviteLink).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("migratedFromGroupId").append(" = ").append(migratedFromGroupId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("migratedFromMaxMessageId").append(" = ").append(migratedFromMaxMessageId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public abstract static class ChannelParticipantsFilter extends TLObject {
    }

    public static class ChannelParticipantsRecent extends ChannelParticipantsFilter {

        public ChannelParticipantsRecent() {
        }

        public static final int CONSTRUCTOR = -566281095;

        @Override
        public int getConstructor() {
            return -566281095;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChannelParticipantsRecent").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class ChannelParticipantsAdmins extends ChannelParticipantsFilter {

        public ChannelParticipantsAdmins() {
        }

        public static final int CONSTRUCTOR = -1268741783;

        @Override
        public int getConstructor() {
            return -1268741783;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChannelParticipantsAdmins").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class ChannelParticipantsKicked extends ChannelParticipantsFilter {

        public ChannelParticipantsKicked() {
        }

        public static final int CONSTRUCTOR = 1010285434;

        @Override
        public int getConstructor() {
            return 1010285434;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChannelParticipantsKicked").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class ChannelParticipantsBots extends ChannelParticipantsFilter {

        public ChannelParticipantsBots() {
        }

        public static final int CONSTRUCTOR = -1328445861;

        @Override
        public int getConstructor() {
            return -1328445861;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChannelParticipantsBots").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        public ChatInfo type;

        public Chat() {
        }

        public Chat(long id, String title, ChatPhoto photo, Message topMessage, long order, int unreadCount, int lastReadInboxMessageId, int lastReadOutboxMessageId, NotificationSettings notificationSettings, int replyMarkupMessageId, ChatInfo type) {
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
            this.type = type;
        }

        public static final int CONSTRUCTOR = 639725222;

        @Override
        public int getConstructor() {
            return 639725222;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("Chat").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("id").append(" = ").append(id).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("title").append(" = ").append(title).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("photo").append(" = "); photo.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("topMessage").append(" = "); topMessage.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("order").append(" = ").append(order).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("unreadCount").append(" = ").append(unreadCount).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("lastReadInboxMessageId").append(" = ").append(lastReadInboxMessageId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("lastReadOutboxMessageId").append(" = ").append(lastReadOutboxMessageId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("notificationSettings").append(" = "); notificationSettings.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("replyMarkupMessageId").append(" = ").append(replyMarkupMessageId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("type").append(" = "); type.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("PrivateChatInfo").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("user").append(" = "); user.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("GroupChatInfo").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("group").append(" = "); group.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChannelChatInfo").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("channel").append(" = "); channel.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class SecretChatInfo extends ChatInfo {
        public SecretChat secretChat;

        public SecretChatInfo() {
        }

        public SecretChatInfo(SecretChat secretChat) {
            this.secretChat = secretChat;
        }

        public static final int CONSTRUCTOR = 1178675350;

        @Override
        public int getConstructor() {
            return 1178675350;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SecretChatInfo").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("secretChat").append(" = "); secretChat.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChatInviteLink").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("inviteLink").append(" = ").append(inviteLink).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class ChatInviteLinkInfo extends TLObject {
        public long chatId;
        public String title;
        public boolean isGroup;
        public boolean isChannel;
        public boolean isBroadcastChannel;
        public boolean isPublicChannel;
        public boolean isSupergroupChannel;

        public ChatInviteLinkInfo() {
        }

        public ChatInviteLinkInfo(long chatId, String title, boolean isGroup, boolean isChannel, boolean isBroadcastChannel, boolean isPublicChannel, boolean isSupergroupChannel) {
            this.chatId = chatId;
            this.title = title;
            this.isGroup = isGroup;
            this.isChannel = isChannel;
            this.isBroadcastChannel = isBroadcastChannel;
            this.isPublicChannel = isPublicChannel;
            this.isSupergroupChannel = isSupergroupChannel;
        }

        public static final int CONSTRUCTOR = 948466112;

        @Override
        public int getConstructor() {
            return 948466112;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChatInviteLinkInfo").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("title").append(" = ").append(title).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("isGroup").append(" = ").append(isGroup).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("isChannel").append(" = ").append(isChannel).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("isBroadcastChannel").append(" = ").append(isBroadcastChannel).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("isPublicChannel").append(" = ").append(isPublicChannel).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("isSupergroupChannel").append(" = ").append(isSupergroupChannel).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class ChatParticipant extends TLObject {
        public User user;
        public int inviterId;
        public int joinDate;
        public ChatParticipantRole role;
        public BotInfo botInfo;

        public ChatParticipant() {
        }

        public ChatParticipant(User user, int inviterId, int joinDate, ChatParticipantRole role, BotInfo botInfo) {
            this.user = user;
            this.inviterId = inviterId;
            this.joinDate = joinDate;
            this.role = role;
            this.botInfo = botInfo;
        }

        public static final int CONSTRUCTOR = 4451682;

        @Override
        public int getConstructor() {
            return 4451682;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChatParticipant").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("user").append(" = "); user.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("inviterId").append(" = ").append(inviterId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("joinDate").append(" = ").append(joinDate).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("role").append(" = "); role.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("botInfo").append(" = "); botInfo.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public abstract static class ChatParticipantRole extends TLObject {
    }

    public static class ChatParticipantRoleAdmin extends ChatParticipantRole {

        public ChatParticipantRoleAdmin() {
        }

        public static final int CONSTRUCTOR = -1668488066;

        @Override
        public int getConstructor() {
            return -1668488066;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChatParticipantRoleAdmin").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class ChatParticipantRoleEditor extends ChatParticipantRole {

        public ChatParticipantRoleEditor() {
        }

        public static final int CONSTRUCTOR = -1981910583;

        @Override
        public int getConstructor() {
            return -1981910583;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChatParticipantRoleEditor").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class ChatParticipantRoleModerator extends ChatParticipantRole {

        public ChatParticipantRoleModerator() {
        }

        public static final int CONSTRUCTOR = 1964166193;

        @Override
        public int getConstructor() {
            return 1964166193;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChatParticipantRoleModerator").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class ChatParticipantRoleGeneral extends ChatParticipantRole {

        public ChatParticipantRoleGeneral() {
        }

        public static final int CONSTRUCTOR = -486762553;

        @Override
        public int getConstructor() {
            return -486762553;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChatParticipantRoleGeneral").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class ChatParticipantRoleLeft extends ChatParticipantRole {

        public ChatParticipantRoleLeft() {
        }

        public static final int CONSTRUCTOR = 825688243;

        @Override
        public int getConstructor() {
            return 825688243;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChatParticipantRoleLeft").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class ChatParticipantRoleKicked extends ChatParticipantRole {

        public ChatParticipantRoleKicked() {
        }

        public static final int CONSTRUCTOR = 1361255364;

        @Override
        public int getConstructor() {
            return 1361255364;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChatParticipantRoleKicked").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class ChatParticipants extends TLObject {
        public int totalCount;
        public ChatParticipant[] participants;

        public ChatParticipants() {
        }

        public ChatParticipants(int totalCount, ChatParticipant[] participants) {
            this.totalCount = totalCount;
            this.participants = participants;
        }

        public static final int CONSTRUCTOR = 985093454;

        @Override
        public int getConstructor() {
            return 985093454;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChatParticipants").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("totalCount").append(" = ").append(totalCount).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("participants").append(" = ").append("ChatParticipant[]").append(" {").append(Arrays.toString(participants)).append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChatPhoto").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("small").append(" = "); small.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("big").append(" = "); big.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("Chats").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chats").append(" = ").append("Chat[]").append(" {").append(Arrays.toString(chats)).append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class Contacts extends TLObject {
        public User[] users;

        public Contacts() {
        }

        public Contacts(User[] users) {
            this.users = users;
        }

        public static final int CONSTRUCTOR = 1238821485;

        @Override
        public int getConstructor() {
            return 1238821485;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("Contacts").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("users").append(" = ").append("User[]").append(" {").append(Arrays.toString(users)).append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("Document").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("fileName").append(" = ").append(fileName).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("mimeType").append(" = ").append(mimeType).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("thumb").append(" = "); thumb.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("document").append(" = "); document.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class Error extends TLObject {
        public int code;
        public String text;

        public Error() {
        }

        public Error(int code, String text) {
            this.code = code;
            this.text = text;
        }

        public static final int CONSTRUCTOR = -994444869;

        @Override
        public int getConstructor() {
            return -994444869;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("Error").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("code").append(" = ").append(code).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("text").append(" = ").append(text).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("File").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("id").append(" = ").append(id).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("persistentId").append(" = ").append(persistentId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("size").append(" = ").append(size).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("path").append(" = ").append(path).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class Group extends TLObject {
        public int id;
        public int participantsCount;
        public ChatParticipantRole role;
        public boolean anyoneCanEdit;
        public boolean isActive;
        public int migratedToChannelId;

        public Group() {
        }

        public Group(int id, int participantsCount, ChatParticipantRole role, boolean anyoneCanEdit, boolean isActive, int migratedToChannelId) {
            this.id = id;
            this.participantsCount = participantsCount;
            this.role = role;
            this.anyoneCanEdit = anyoneCanEdit;
            this.isActive = isActive;
            this.migratedToChannelId = migratedToChannelId;
        }

        public static final int CONSTRUCTOR = -2105342645;

        @Override
        public int getConstructor() {
            return -2105342645;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("Group").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("id").append(" = ").append(id).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("participantsCount").append(" = ").append(participantsCount).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("role").append(" = "); role.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("anyoneCanEdit").append(" = ").append(anyoneCanEdit).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("isActive").append(" = ").append(isActive).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("migratedToChannelId").append(" = ").append(migratedToChannelId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class GroupFull extends TLObject {
        public Group group;
        public int adminId;
        public ChatParticipant[] participants;
        public String inviteLink;

        public GroupFull() {
        }

        public GroupFull(Group group, int adminId, ChatParticipant[] participants, String inviteLink) {
            this.group = group;
            this.adminId = adminId;
            this.participants = participants;
            this.inviteLink = inviteLink;
        }

        public static final int CONSTRUCTOR = 987785959;

        @Override
        public int getConstructor() {
            return 987785959;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("GroupFull").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("group").append(" = "); group.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("adminId").append(" = ").append(adminId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("participants").append(" = ").append("ChatParticipant[]").append(" {").append(Arrays.toString(participants)).append("}\n");
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("inviteLink").append(" = ").append(inviteLink).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        public InputMessageContent message;

        public InlineQueryResultArticle() {
        }

        public InlineQueryResultArticle(String id, String url, boolean hideUrl, String title, String description, String thumbUrl, int thumbWidth, int thumbHeight, InputMessageContent message) {
            this.id = id;
            this.url = url;
            this.hideUrl = hideUrl;
            this.title = title;
            this.description = description;
            this.thumbUrl = thumbUrl;
            this.thumbWidth = thumbWidth;
            this.thumbHeight = thumbHeight;
            this.message = message;
        }

        public static final int CONSTRUCTOR = 111003847;

        @Override
        public int getConstructor() {
            return 111003847;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineQueryResultArticle").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("id").append(" = ").append(id).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("url").append(" = ").append(url).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("hideUrl").append(" = ").append(hideUrl).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("title").append(" = ").append(title).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("description").append(" = ").append(description).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("thumbUrl").append(" = ").append(thumbUrl).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("thumbWidth").append(" = ").append(thumbWidth).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("thumbHeight").append(" = ").append(thumbHeight).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("message").append(" = "); message.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class InlineQueryResultPhoto extends InlineQueryResult {
        public String id;
        public String title;
        public String description;
        public String thumbUrl;
        public String photoUrl;
        public int photoWidth;
        public int photoHeight;
        public InputMessageContent message;

        public InlineQueryResultPhoto() {
        }

        public InlineQueryResultPhoto(String id, String title, String description, String thumbUrl, String photoUrl, int photoWidth, int photoHeight, InputMessageContent message) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.thumbUrl = thumbUrl;
            this.photoUrl = photoUrl;
            this.photoWidth = photoWidth;
            this.photoHeight = photoHeight;
            this.message = message;
        }

        public static final int CONSTRUCTOR = 2146421621;

        @Override
        public int getConstructor() {
            return 2146421621;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineQueryResultPhoto").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("id").append(" = ").append(id).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("title").append(" = ").append(title).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("description").append(" = ").append(description).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("thumbUrl").append(" = ").append(thumbUrl).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("photoUrl").append(" = ").append(photoUrl).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("photoWidth").append(" = ").append(photoWidth).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("photoHeight").append(" = ").append(photoHeight).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("message").append(" = "); message.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class InlineQueryResultAnimatedGif extends InlineQueryResult {
        public String id;
        public String title;
        public String thumbUrl;
        public String gifUrl;
        public int gifWidth;
        public int gifHeight;
        public InputMessageContent message;

        public InlineQueryResultAnimatedGif() {
        }

        public InlineQueryResultAnimatedGif(String id, String title, String thumbUrl, String gifUrl, int gifWidth, int gifHeight, InputMessageContent message) {
            this.id = id;
            this.title = title;
            this.thumbUrl = thumbUrl;
            this.gifUrl = gifUrl;
            this.gifWidth = gifWidth;
            this.gifHeight = gifHeight;
            this.message = message;
        }

        public static final int CONSTRUCTOR = -1944033660;

        @Override
        public int getConstructor() {
            return -1944033660;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineQueryResultAnimatedGif").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("id").append(" = ").append(id).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("title").append(" = ").append(title).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("thumbUrl").append(" = ").append(thumbUrl).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("gifUrl").append(" = ").append(gifUrl).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("gifWidth").append(" = ").append(gifWidth).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("gifHeight").append(" = ").append(gifHeight).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("message").append(" = "); message.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class InlineQueryResultAnimatedMpeg4 extends InlineQueryResult {
        public String id;
        public String title;
        public String thumbUrl;
        public String mpeg4Url;
        public int mpeg4Width;
        public int mpeg4Height;
        public InputMessageContent message;

        public InlineQueryResultAnimatedMpeg4() {
        }

        public InlineQueryResultAnimatedMpeg4(String id, String title, String thumbUrl, String mpeg4Url, int mpeg4Width, int mpeg4Height, InputMessageContent message) {
            this.id = id;
            this.title = title;
            this.thumbUrl = thumbUrl;
            this.mpeg4Url = mpeg4Url;
            this.mpeg4Width = mpeg4Width;
            this.mpeg4Height = mpeg4Height;
            this.message = message;
        }

        public static final int CONSTRUCTOR = -76471915;

        @Override
        public int getConstructor() {
            return -76471915;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineQueryResultAnimatedMpeg4").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("id").append(" = ").append(id).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("title").append(" = ").append(title).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("thumbUrl").append(" = ").append(thumbUrl).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("mpeg4Url").append(" = ").append(mpeg4Url).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("mpeg4Width").append(" = ").append(mpeg4Width).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("mpeg4Height").append(" = ").append(mpeg4Height).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("message").append(" = "); message.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class InlineQueryResultVideo extends InlineQueryResult {
        public String id;
        public String title;
        public String description;
        public String thumbUrl;
        public String videoUrl;
        public String mimeType;
        public int videoWidth;
        public int videoHeight;
        public int videoDuration;
        public InputMessageContent message;

        public InlineQueryResultVideo() {
        }

        public InlineQueryResultVideo(String id, String title, String description, String thumbUrl, String videoUrl, String mimeType, int videoWidth, int videoHeight, int videoDuration, InputMessageContent message) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.thumbUrl = thumbUrl;
            this.videoUrl = videoUrl;
            this.mimeType = mimeType;
            this.videoWidth = videoWidth;
            this.videoHeight = videoHeight;
            this.videoDuration = videoDuration;
            this.message = message;
        }

        public static final int CONSTRUCTOR = -741636295;

        @Override
        public int getConstructor() {
            return -741636295;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineQueryResultVideo").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("id").append(" = ").append(id).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("title").append(" = ").append(title).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("description").append(" = ").append(description).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("thumbUrl").append(" = ").append(thumbUrl).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("videoUrl").append(" = ").append(videoUrl).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("mimeType").append(" = ").append(mimeType).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("videoWidth").append(" = ").append(videoWidth).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("videoHeight").append(" = ").append(videoHeight).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("videoDuration").append(" = ").append(videoDuration).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("message").append(" = "); message.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class InlineQueryResultCachedPhoto extends InlineQueryResult {
        public String id;
        public Photo photo;
        public InputMessageContent message;

        public InlineQueryResultCachedPhoto() {
        }

        public InlineQueryResultCachedPhoto(String id, Photo photo, InputMessageContent message) {
            this.id = id;
            this.photo = photo;
            this.message = message;
        }

        public static final int CONSTRUCTOR = -617732293;

        @Override
        public int getConstructor() {
            return -617732293;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineQueryResultCachedPhoto").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("id").append(" = ").append(id).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("photo").append(" = "); photo.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("message").append(" = "); message.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class InlineQueryResultCachedAnimation extends InlineQueryResult {
        public String id;
        public Animation animation;
        public InputMessageContent message;

        public InlineQueryResultCachedAnimation() {
        }

        public InlineQueryResultCachedAnimation(String id, Animation animation, InputMessageContent message) {
            this.id = id;
            this.animation = animation;
            this.message = message;
        }

        public static final int CONSTRUCTOR = 742840235;

        @Override
        public int getConstructor() {
            return 742840235;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineQueryResultCachedAnimation").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("id").append(" = ").append(id).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("animation").append(" = "); animation.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("message").append(" = "); message.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class InlineQueryResults extends TLObject {
        public long inlineQueryId;
        public String nextOffset;
        public InlineQueryResult[] results;

        public InlineQueryResults() {
        }

        public InlineQueryResults(long inlineQueryId, String nextOffset, InlineQueryResult[] results) {
            this.inlineQueryId = inlineQueryId;
            this.nextOffset = nextOffset;
            this.results = results;
        }

        public static final int CONSTRUCTOR = 1601490522;

        @Override
        public int getConstructor() {
            return 1601490522;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineQueryResults").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("inlineQueryId").append(" = ").append(inlineQueryId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("nextOffset").append(" = ").append(nextOffset).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("results").append(" = ").append("InlineQueryResult[]").append(" {").append(Arrays.toString(results)).append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class InputContact extends TLObject {
        public String phoneNumber;
        public String firstName;
        public String lastName;

        public InputContact() {
        }

        public InputContact(String phoneNumber, String firstName, String lastName) {
            this.phoneNumber = phoneNumber;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public static final int CONSTRUCTOR = 1707235253;

        @Override
        public int getConstructor() {
            return 1707235253;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputContact").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("phoneNumber").append(" = ").append(phoneNumber).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("firstName").append(" = ").append(firstName).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("lastName").append(" = ").append(lastName).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputFileId").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("id").append(" = ").append(id).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputFilePersistentId").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("persistentId").append(" = ").append(persistentId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputFileLocal").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("path").append(" = ").append(path).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public abstract static class InputMessageContent extends TLObject {
    }

    public static class InputMessageText extends InputMessageContent {
        public String text;
        public boolean disableWebPagePreview;
        public MessageEntity[] entities;

        public InputMessageText() {
        }

        public InputMessageText(String text, boolean disableWebPagePreview, MessageEntity[] entities) {
            this.text = text;
            this.disableWebPagePreview = disableWebPagePreview;
            this.entities = entities;
        }

        public static final int CONSTRUCTOR = -764893502;

        @Override
        public int getConstructor() {
            return -764893502;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessageText").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("text").append(" = ").append(text).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("disableWebPagePreview").append(" = ").append(disableWebPagePreview).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("entities").append(" = ").append("MessageEntity[]").append(" {").append(Arrays.toString(entities)).append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class InputMessageAnimation extends InputMessageContent {
        public InputFile animation;
        public int width;
        public int height;
        public String caption;

        public InputMessageAnimation() {
        }

        public InputMessageAnimation(InputFile animation, int width, int height, String caption) {
            this.animation = animation;
            this.width = width;
            this.height = height;
            this.caption = caption;
        }

        public static final int CONSTRUCTOR = 629438140;

        @Override
        public int getConstructor() {
            return 629438140;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessageAnimation").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("animation").append(" = "); animation.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("width").append(" = ").append(width).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("height").append(" = ").append(height).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("caption").append(" = ").append(caption).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class InputMessageAudio extends InputMessageContent {
        public InputFile audio;
        public int duration;
        public String title;
        public String performer;

        public InputMessageAudio() {
        }

        public InputMessageAudio(InputFile audio, int duration, String title, String performer) {
            this.audio = audio;
            this.duration = duration;
            this.title = title;
            this.performer = performer;
        }

        public static final int CONSTRUCTOR = -674962739;

        @Override
        public int getConstructor() {
            return -674962739;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessageAudio").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("audio").append(" = "); audio.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("duration").append(" = ").append(duration).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("title").append(" = ").append(title).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("performer").append(" = ").append(performer).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class InputMessageDocument extends InputMessageContent {
        public InputFile document;
        public String caption;

        public InputMessageDocument() {
        }

        public InputMessageDocument(InputFile document, String caption) {
            this.document = document;
            this.caption = caption;
        }

        public static final int CONSTRUCTOR = 1289989663;

        @Override
        public int getConstructor() {
            return 1289989663;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessageDocument").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("document").append(" = "); document.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("caption").append(" = ").append(caption).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessagePhoto").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("photo").append(" = "); photo.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("caption").append(" = ").append(caption).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class InputMessageSticker extends InputMessageContent {
        public InputFile sticker;

        public InputMessageSticker() {
        }

        public InputMessageSticker(InputFile sticker) {
            this.sticker = sticker;
        }

        public static final int CONSTRUCTOR = 1579676898;

        @Override
        public int getConstructor() {
            return 1579676898;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessageSticker").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("sticker").append(" = "); sticker.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class InputMessageVideo extends InputMessageContent {
        public InputFile video;
        public String caption;
        public int duration;
        public int width;
        public int height;

        public InputMessageVideo() {
        }

        public InputMessageVideo(InputFile video, String caption, int duration, int width, int height) {
            this.video = video;
            this.caption = caption;
            this.duration = duration;
            this.width = width;
            this.height = height;
        }

        public static final int CONSTRUCTOR = -1404882448;

        @Override
        public int getConstructor() {
            return -1404882448;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessageVideo").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("video").append(" = "); video.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("caption").append(" = ").append(caption).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("duration").append(" = ").append(duration).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("width").append(" = ").append(width).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("height").append(" = ").append(height).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class InputMessageVoice extends InputMessageContent {
        public InputFile voice;
        public int duration;
        public byte[] waveform;

        public InputMessageVoice() {
        }

        public InputMessageVoice(InputFile voice, int duration, byte[] waveform) {
            this.voice = voice;
            this.duration = duration;
            this.waveform = waveform;
        }

        public static final int CONSTRUCTOR = -942554872;

        @Override
        public int getConstructor() {
            return -942554872;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessageVoice").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("voice").append(" = "); voice.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("duration").append(" = ").append(duration).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("waveform").append(" = ").append("bytes { "); { for (byte k : waveform) { int b = (int)k & 255; s.append(HEX_CHARACTERS[b >> 4]).append(HEX_CHARACTERS[b & 15]).append(' '); } } s.append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class InputMessageLocation extends InputMessageContent {
        public double longitude;
        public double latitude;

        public InputMessageLocation() {
        }

        public InputMessageLocation(double longitude, double latitude) {
            this.longitude = longitude;
            this.latitude = latitude;
        }

        public static final int CONSTRUCTOR = 1494132433;

        @Override
        public int getConstructor() {
            return 1494132433;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessageLocation").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("longitude").append(" = ").append(longitude).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("latitude").append(" = ").append(latitude).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class InputMessageVenue extends InputMessageContent {
        public double longitude;
        public double latitude;
        public String title;
        public String address;
        public String provider;
        public String venueId;

        public InputMessageVenue() {
        }

        public InputMessageVenue(double longitude, double latitude, String title, String address, String provider, String venueId) {
            this.longitude = longitude;
            this.latitude = latitude;
            this.title = title;
            this.address = address;
            this.provider = provider;
            this.venueId = venueId;
        }

        public static final int CONSTRUCTOR = -971070542;

        @Override
        public int getConstructor() {
            return -971070542;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessageVenue").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("longitude").append(" = ").append(longitude).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("latitude").append(" = ").append(latitude).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("title").append(" = ").append(title).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("address").append(" = ").append(address).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("provider").append(" = ").append(provider).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("venueId").append(" = ").append(venueId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class InputMessageContact extends InputMessageContent {
        public String phoneNumber;
        public String firstName;
        public String lastName;
        public int userId;

        public InputMessageContact() {
        }

        public InputMessageContact(String phoneNumber, String firstName, String lastName, int userId) {
            this.phoneNumber = phoneNumber;
            this.firstName = firstName;
            this.lastName = lastName;
            this.userId = userId;
        }

        public static final int CONSTRUCTOR = -1261805057;

        @Override
        public int getConstructor() {
            return -1261805057;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessageContact").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("phoneNumber").append(" = ").append(phoneNumber).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("firstName").append(" = ").append(firstName).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("lastName").append(" = ").append(lastName).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("userId").append(" = ").append(userId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessageForwarded").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("fromChatId").append(" = ").append(fromChatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("messageId").append(" = ").append(messageId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("LinkStateNone").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("LinkStateKnowsPhoneNumber").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("LinkStateContact").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class Location extends TLObject {
        public double longitude;
        public double latitude;

        public Location() {
        }

        public Location(double longitude, double latitude) {
            this.longitude = longitude;
            this.latitude = latitude;
        }

        public static final int CONSTRUCTOR = -1691941094;

        @Override
        public int getConstructor() {
            return -1691941094;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("Location").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("longitude").append(" = ").append(longitude).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("latitude").append(" = ").append(latitude).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class Message extends TLObject {
        public int id;
        public int fromId;
        public long chatId;
        public MessageSendState sendState;
        public boolean canBeDeleted;
        public boolean isPost;
        public int date;
        public int editDate;
        public MessageForwardInfo forwardInfo;
        public int replyToMessageId;
        public int viaBotId;
        public int views;
        public MessageContent content;
        public ReplyMarkup replyMarkup;

        public Message() {
        }

        public Message(int id, int fromId, long chatId, MessageSendState sendState, boolean canBeDeleted, boolean isPost, int date, int editDate, MessageForwardInfo forwardInfo, int replyToMessageId, int viaBotId, int views, MessageContent content, ReplyMarkup replyMarkup) {
            this.id = id;
            this.fromId = fromId;
            this.chatId = chatId;
            this.sendState = sendState;
            this.canBeDeleted = canBeDeleted;
            this.isPost = isPost;
            this.date = date;
            this.editDate = editDate;
            this.forwardInfo = forwardInfo;
            this.replyToMessageId = replyToMessageId;
            this.viaBotId = viaBotId;
            this.views = views;
            this.content = content;
            this.replyMarkup = replyMarkup;
        }

        public static final int CONSTRUCTOR = -1203578833;

        @Override
        public int getConstructor() {
            return -1203578833;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("Message").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("id").append(" = ").append(id).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("fromId").append(" = ").append(fromId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("sendState").append(" = "); sendState.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("canBeDeleted").append(" = ").append(canBeDeleted).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("isPost").append(" = ").append(isPost).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("date").append(" = ").append(date).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("editDate").append(" = ").append(editDate).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("forwardInfo").append(" = "); forwardInfo.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("replyToMessageId").append(" = ").append(replyToMessageId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("viaBotId").append(" = ").append(viaBotId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("views").append(" = ").append(views).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("content").append(" = "); content.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("replyMarkup").append(" = "); replyMarkup.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public abstract static class MessageContent extends TLObject {
    }

    public static class MessageText extends MessageContent {
        public String text;
        public MessageEntity[] entities;

        public MessageText() {
        }

        public MessageText(String text, MessageEntity[] entities) {
            this.text = text;
            this.entities = entities;
        }

        public static final int CONSTRUCTOR = -1525886228;

        @Override
        public int getConstructor() {
            return -1525886228;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageText").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("text").append(" = ").append(text).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("entities").append(" = ").append("MessageEntity[]").append(" {").append(Arrays.toString(entities)).append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageAnimation").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("animation").append(" = "); animation.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("caption").append(" = ").append(caption).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class MessageAudio extends MessageContent {
        public Audio audio;
        public boolean isListened;

        public MessageAudio() {
        }

        public MessageAudio(Audio audio, boolean isListened) {
            this.audio = audio;
            this.isListened = isListened;
        }

        public static final int CONSTRUCTOR = 1604677406;

        @Override
        public int getConstructor() {
            return 1604677406;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageAudio").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("audio").append(" = "); audio.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("isListened").append(" = ").append(isListened).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageDocument").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("document").append(" = "); document.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("caption").append(" = ").append(caption).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessagePhoto").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("photo").append(" = "); photo.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("caption").append(" = ").append(caption).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageSticker").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("sticker").append(" = "); sticker.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageVideo").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("video").append(" = "); video.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("caption").append(" = ").append(caption).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class MessageVoice extends MessageContent {
        public Voice voice;
        public boolean isListened;

        public MessageVoice() {
        }

        public MessageVoice(Voice voice, boolean isListened) {
            this.voice = voice;
            this.isListened = isListened;
        }

        public static final int CONSTRUCTOR = 590783451;

        @Override
        public int getConstructor() {
            return 590783451;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageVoice").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("voice").append(" = "); voice.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("isListened").append(" = ").append(isListened).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class MessageWebPage extends MessageContent {
        public String text;
        public MessageEntity[] entities;
        public WebPage webPage;

        public MessageWebPage() {
        }

        public MessageWebPage(String text, MessageEntity[] entities, WebPage webPage) {
            this.text = text;
            this.entities = entities;
            this.webPage = webPage;
        }

        public static final int CONSTRUCTOR = -1978747464;

        @Override
        public int getConstructor() {
            return -1978747464;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageWebPage").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("text").append(" = ").append(text).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("entities").append(" = ").append("MessageEntity[]").append(" {").append(Arrays.toString(entities)).append("}\n");
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("webPage").append(" = "); webPage.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageLocation").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("location").append(" = "); location.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class MessageVenue extends MessageContent {
        public Location location;
        public String title;
        public String address;
        public String provider;
        public String venueId;

        public MessageVenue() {
        }

        public MessageVenue(Location location, String title, String address, String provider, String venueId) {
            this.location = location;
            this.title = title;
            this.address = address;
            this.provider = provider;
            this.venueId = venueId;
        }

        public static final int CONSTRUCTOR = 586749589;

        @Override
        public int getConstructor() {
            return 586749589;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageVenue").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("location").append(" = "); location.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("title").append(" = ").append(title).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("address").append(" = ").append(address).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("provider").append(" = ").append(provider).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("venueId").append(" = ").append(venueId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class MessageContact extends MessageContent {
        public String phoneNumber;
        public String firstName;
        public String lastName;
        public int userId;

        public MessageContact() {
        }

        public MessageContact(String phoneNumber, String firstName, String lastName, int userId) {
            this.phoneNumber = phoneNumber;
            this.firstName = firstName;
            this.lastName = lastName;
            this.userId = userId;
        }

        public static final int CONSTRUCTOR = 216059403;

        @Override
        public int getConstructor() {
            return 216059403;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageContact").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("phoneNumber").append(" = ").append(phoneNumber).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("firstName").append(" = ").append(firstName).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("lastName").append(" = ").append(lastName).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("userId").append(" = ").append(userId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class MessageGroupChatCreate extends MessageContent {
        public String title;
        public User[] participants;

        public MessageGroupChatCreate() {
        }

        public MessageGroupChatCreate(String title, User[] participants) {
            this.title = title;
            this.participants = participants;
        }

        public static final int CONSTRUCTOR = -1856328772;

        @Override
        public int getConstructor() {
            return -1856328772;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageGroupChatCreate").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("title").append(" = ").append(title).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("participants").append(" = ").append("User[]").append(" {").append(Arrays.toString(participants)).append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageChannelChatCreate").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("title").append(" = ").append(title).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageChatChangeTitle").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("title").append(" = ").append(title).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageChatChangePhoto").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("photo").append(" = "); photo.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageChatDeletePhoto").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class MessageChatAddParticipants extends MessageContent {
        public User[] participants;

        public MessageChatAddParticipants() {
        }

        public MessageChatAddParticipants(User[] participants) {
            this.participants = participants;
        }

        public static final int CONSTRUCTOR = 1759366874;

        @Override
        public int getConstructor() {
            return 1759366874;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageChatAddParticipants").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("participants").append(" = ").append("User[]").append(" {").append(Arrays.toString(participants)).append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class MessageChatJoinByLink extends MessageContent {
        public int inviterId;

        public MessageChatJoinByLink() {
        }

        public MessageChatJoinByLink(int inviterId) {
            this.inviterId = inviterId;
        }

        public static final int CONSTRUCTOR = 1832922905;

        @Override
        public int getConstructor() {
            return 1832922905;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageChatJoinByLink").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("inviterId").append(" = ").append(inviterId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class MessageChatDeleteParticipant extends MessageContent {
        public User user;

        public MessageChatDeleteParticipant() {
        }

        public MessageChatDeleteParticipant(User user) {
            this.user = user;
        }

        public static final int CONSTRUCTOR = 2091163657;

        @Override
        public int getConstructor() {
            return 2091163657;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageChatDeleteParticipant").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("user").append(" = "); user.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageChatMigrateTo").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("channelId").append(" = ").append(channelId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageChatMigrateFrom").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("title").append(" = ").append(title).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("groupId").append(" = ").append(groupId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class MessageDeleted extends MessageContent {

        public MessageDeleted() {
        }

        public static final int CONSTRUCTOR = 2145503191;

        @Override
        public int getConstructor() {
            return 2145503191;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageDeleted").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageUnsupported").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageEntityMention").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("offset").append(" = ").append(offset).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("length").append(" = ").append(length).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageEntityHashtag").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("offset").append(" = ").append(offset).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("length").append(" = ").append(length).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageEntityBotCommand").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("offset").append(" = ").append(offset).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("length").append(" = ").append(length).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageEntityUrl").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("offset").append(" = ").append(offset).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("length").append(" = ").append(length).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageEntityEmail").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("offset").append(" = ").append(offset).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("length").append(" = ").append(length).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageEntityBold").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("offset").append(" = ").append(offset).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("length").append(" = ").append(length).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageEntityItalic").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("offset").append(" = ").append(offset).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("length").append(" = ").append(length).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageEntityCode").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("offset").append(" = ").append(offset).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("length").append(" = ").append(length).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageEntityPre").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("offset").append(" = ").append(offset).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("length").append(" = ").append(length).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageEntityPreCode").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("offset").append(" = ").append(offset).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("length").append(" = ").append(length).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("language").append(" = ").append(language).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageEntityTextUrl").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("offset").append(" = ").append(offset).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("length").append(" = ").append(length).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("url").append(" = ").append(url).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public abstract static class MessageForwardInfo extends TLObject {
    }

    public static class MessageNotForwarded extends MessageForwardInfo {

        public MessageNotForwarded() {
        }

        public static final int CONSTRUCTOR = 1151377659;

        @Override
        public int getConstructor() {
            return 1151377659;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageNotForwarded").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class MessageForwardedFromUser extends MessageForwardInfo {
        public int userId;
        public int date;

        public MessageForwardedFromUser() {
        }

        public MessageForwardedFromUser(int userId, int date) {
            this.userId = userId;
            this.date = date;
        }

        public static final int CONSTRUCTOR = 449683284;

        @Override
        public int getConstructor() {
            return 449683284;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageForwardedFromUser").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("userId").append(" = ").append(userId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("date").append(" = ").append(date).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class MessageForwardedFromChannel extends MessageForwardInfo {
        public int channelId;
        public int date;
        public int userId;
        public int messageId;

        public MessageForwardedFromChannel() {
        }

        public MessageForwardedFromChannel(int channelId, int date, int userId, int messageId) {
            this.channelId = channelId;
            this.date = date;
            this.userId = userId;
            this.messageId = messageId;
        }

        public static final int CONSTRUCTOR = 572357536;

        @Override
        public int getConstructor() {
            return 572357536;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageForwardedFromChannel").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("channelId").append(" = ").append(channelId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("date").append(" = ").append(date).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("userId").append(" = ").append(userId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("messageId").append(" = ").append(messageId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageIsIncoming").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageIsBeingSent").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageIsSuccessfullySent").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageIsFailedToSend").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("Messages").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("totalCount").append(" = ").append(totalCount).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("messages").append(" = ").append("Message[]").append(" {").append(Arrays.toString(messages)).append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class NotificationSettings extends TLObject {
        public int muteFor;
        public String sound;
        public boolean showPreviews;

        public NotificationSettings() {
        }

        public NotificationSettings(int muteFor, String sound, boolean showPreviews) {
            this.muteFor = muteFor;
            this.sound = sound;
            this.showPreviews = showPreviews;
        }

        public static final int CONSTRUCTOR = 824630963;

        @Override
        public int getConstructor() {
            return 824630963;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("NotificationSettings").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("muteFor").append(" = ").append(muteFor).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("sound").append(" = ").append(sound).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("showPreviews").append(" = ").append(showPreviews).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("NotificationSettingsForChat").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("NotificationSettingsForPrivateChats").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("NotificationSettingsForGroupChats").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("NotificationSettingsForAllChats").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("Ok").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("OptionBoolean").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("value").append(" = ").append(value).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("OptionEmpty").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("OptionInteger").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("value").append(" = ").append(value).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("OptionString").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("value").append(" = ").append(value).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class Photo extends TLObject {
        public long id;
        public PhotoSize[] photos;

        public Photo() {
        }

        public Photo(long id, PhotoSize[] photos) {
            this.id = id;
            this.photos = photos;
        }

        public static final int CONSTRUCTOR = 700401344;

        @Override
        public int getConstructor() {
            return 700401344;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("Photo").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("id").append(" = ").append(id).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("photos").append(" = ").append("PhotoSize[]").append(" {").append(Arrays.toString(photos)).append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class PhotoCrop extends TLObject {
        public double left;
        public double top;
        public double width;

        public PhotoCrop() {
        }

        public PhotoCrop(double left, double top, double width) {
            this.left = left;
            this.top = top;
            this.width = width;
        }

        public static final int CONSTRUCTOR = -1565529698;

        @Override
        public int getConstructor() {
            return -1565529698;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("PhotoCrop").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("left").append(" = ").append(left).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("top").append(" = ").append(top).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("width").append(" = ").append(width).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("PhotoSize").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("type").append(" = ").append(type).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("photo").append(" = "); photo.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("width").append(" = ").append(width).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("height").append(" = ").append(height).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ProfilePhoto").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("id").append(" = ").append(id).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("small").append(" = "); small.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("big").append(" = "); big.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public abstract static class ReplyMarkup extends TLObject {
    }

    public static class ReplyMarkupNone extends ReplyMarkup {

        public ReplyMarkupNone() {
        }

        public static final int CONSTRUCTOR = -1623666456;

        @Override
        public int getConstructor() {
            return -1623666456;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ReplyMarkupNone").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ReplyMarkupHideKeyboard").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("personal").append(" = ").append(personal).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ReplyMarkupForceReply").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("personal").append(" = ").append(personal).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class ReplyMarkupShowKeyboard extends ReplyMarkup {
        public String[][] rows;
        public boolean resizeKeyboard;
        public boolean oneTime;
        public boolean personal;

        public ReplyMarkupShowKeyboard() {
        }

        public ReplyMarkupShowKeyboard(String[][] rows, boolean resizeKeyboard, boolean oneTime, boolean personal) {
            this.rows = rows;
            this.resizeKeyboard = resizeKeyboard;
            this.oneTime = oneTime;
            this.personal = personal;
        }

        public static final int CONSTRUCTOR = 22090330;

        @Override
        public int getConstructor() {
            return 22090330;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ReplyMarkupShowKeyboard").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("rows").append(" = ").append("String[][]").append(" {").append(Arrays.deepToString(rows)).append("}\n");
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("resizeKeyboard").append(" = ").append(resizeKeyboard).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("oneTime").append(" = ").append(oneTime).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("personal").append(" = ").append(personal).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchMessagesFilterEmpty").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchMessagesFilterAnimation").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchMessagesFilterAudio").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchMessagesFilterDocument").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchMessagesFilterPhoto").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchMessagesFilterVideo").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchMessagesFilterVoice").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchMessagesFilterPhotoAndVideo").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchMessagesFilterUrl").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class SecretChat extends TLObject {
        public int userId;
        public int state;
        public int ttl;

        public SecretChat() {
        }

        public SecretChat(int userId, int state, int ttl) {
            this.userId = userId;
            this.state = state;
            this.ttl = ttl;
        }

        public static final int CONSTRUCTOR = -1398715296;

        @Override
        public int getConstructor() {
            return -1398715296;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SecretChat").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("userId").append(" = ").append(userId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("state").append(" = ").append(state).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("ttl").append(" = ").append(ttl).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SecretChatFull").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("secretChat").append(" = "); secretChat.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("keyHash").append(" = ").append(keyHash).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SendMessageTypingAction").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SendMessageCancelAction").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SendMessageRecordVideoAction").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SendMessageUploadVideoAction").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("progress").append(" = ").append(progress).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SendMessageRecordVoiceAction").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SendMessageUploadVoiceAction").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("progress").append(" = ").append(progress).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SendMessageUploadPhotoAction").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("progress").append(" = ").append(progress).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SendMessageUploadDocumentAction").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("progress").append(" = ").append(progress).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SendMessageGeoLocationAction").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SendMessageChooseContactAction").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("Sticker").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("setId").append(" = ").append(setId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("width").append(" = ").append(width).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("height").append(" = ").append(height).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("emoji").append(" = ").append(emoji).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("rating").append(" = ").append(rating).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("thumb").append(" = "); thumb.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("sticker").append(" = "); sticker.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("StickerSet").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("id").append(" = ").append(id).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("title").append(" = ").append(title).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("name").append(" = ").append(name).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("rating").append(" = ").append(rating).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("isInstalled").append(" = ").append(isInstalled).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("isEnabled").append(" = ").append(isEnabled).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("isOfficial").append(" = ").append(isOfficial).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("stickers").append(" = ").append("Sticker[]").append(" {").append(Arrays.toString(stickers)).append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("StickerSetInfo").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("id").append(" = ").append(id).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("title").append(" = ").append(title).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("name").append(" = ").append(name).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("rating").append(" = ").append(rating).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("isInstalled").append(" = ").append(isInstalled).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("isEnabled").append(" = ").append(isEnabled).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("isOfficial").append(" = ").append(isOfficial).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("size").append(" = ").append(size).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("StickerSets").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("sets").append(" = ").append("StickerSetInfo[]").append(" {").append(Arrays.toString(sets)).append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("Stickers").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("stickers").append(" = ").append("Sticker[]").append(" {").append(Arrays.toString(stickers)).append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public abstract static class Update extends TLObject {
    }

    public static class UpdateNewMessage extends Update {
        public Message message;

        public UpdateNewMessage() {
        }

        public UpdateNewMessage(Message message) {
            this.message = message;
        }

        public static final int CONSTRUCTOR = -563105266;

        @Override
        public int getConstructor() {
            return -563105266;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateNewMessage").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("message").append(" = "); message.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class UpdateMessageId extends Update {
        public long chatId;
        public int oldId;
        public int newId;

        public UpdateMessageId() {
        }

        public UpdateMessageId(long chatId, int oldId, int newId) {
            this.chatId = chatId;
            this.oldId = oldId;
            this.newId = newId;
        }

        public static final int CONSTRUCTOR = 1840811241;

        @Override
        public int getConstructor() {
            return 1840811241;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateMessageId").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("oldId").append(" = ").append(oldId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("newId").append(" = ").append(newId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class UpdateMessageDate extends Update {
        public long chatId;
        public int messageId;
        public int newDate;

        public UpdateMessageDate() {
        }

        public UpdateMessageDate(long chatId, int messageId, int newDate) {
            this.chatId = chatId;
            this.messageId = messageId;
            this.newDate = newDate;
        }

        public static final int CONSTRUCTOR = 211076103;

        @Override
        public int getConstructor() {
            return 211076103;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateMessageDate").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("messageId").append(" = ").append(messageId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("newDate").append(" = ").append(newDate).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateMessageContent").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("messageId").append(" = ").append(messageId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("newContent").append(" = "); newContent.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class UpdateMessageSendFailed extends Update {
        public long chatId;
        public int messageId;
        public int errorCode;
        public String errorDescription;

        public UpdateMessageSendFailed() {
        }

        public UpdateMessageSendFailed(long chatId, int messageId, int errorCode, String errorDescription) {
            this.chatId = chatId;
            this.messageId = messageId;
            this.errorCode = errorCode;
            this.errorDescription = errorDescription;
        }

        public static final int CONSTRUCTOR = 2098937137;

        @Override
        public int getConstructor() {
            return 2098937137;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateMessageSendFailed").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("messageId").append(" = ").append(messageId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("errorCode").append(" = ").append(errorCode).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("errorDescription").append(" = ").append(errorDescription).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateMessageViews").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("messageId").append(" = ").append(messageId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("views").append(" = ").append(views).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateChat").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chat").append(" = "); chat.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateChatOrder").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("order").append(" = ").append(order).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateChatTitle").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("title").append(" = ").append(title).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateChatPhoto").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("photo").append(" = "); photo.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateChatReadInbox").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("lastReadInboxMessageId").append(" = ").append(lastReadInboxMessageId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("unreadCount").append(" = ").append(unreadCount).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateChatReadOutbox").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("lastReadOutboxMessageId").append(" = ").append(lastReadOutboxMessageId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateChatReplyMarkup").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("replyMarkupMessageId").append(" = ").append(replyMarkupMessageId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateNotificationSettings").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("scope").append(" = "); scope.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("notificationSettings").append(" = "); notificationSettings.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateDeleteMessages").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("messageIds").append(" = ").append("int[]").append(" {").append(Arrays.toString(messageIds)).append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateUserAction").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("userId").append(" = ").append(userId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("action").append(" = "); action.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateUserStatus").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("userId").append(" = ").append(userId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("status").append(" = "); status.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateUser").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("user").append(" = "); user.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateGroup").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("group").append(" = "); group.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateChannel").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("channel").append(" = "); channel.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class UpdateSecretChat extends Update {
        public SecretChat secretChat;

        public UpdateSecretChat() {
        }

        public UpdateSecretChat(SecretChat secretChat) {
            this.secretChat = secretChat;
        }

        public static final int CONSTRUCTOR = -1172216513;

        @Override
        public int getConstructor() {
            return -1172216513;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateSecretChat").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("secretChat").append(" = "); secretChat.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateUserBlocked").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("userId").append(" = ").append(userId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("isBlocked").append(" = ").append(isBlocked).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateNewAuthorization").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("date").append(" = ").append(date).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("device").append(" = ").append(device).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("location").append(" = ").append(location).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateFileProgress").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("fileId").append(" = ").append(fileId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("size").append(" = ").append(size).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("ready").append(" = ").append(ready).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateFile").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("file").append(" = "); file.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateOption").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("name").append(" = ").append(name).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("value").append(" = "); value.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateStickers").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateSavedAnimations").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class UpdateNewInlineQuery extends Update {
        public long id;
        public int fromId;
        public String query;
        public String offset;

        public UpdateNewInlineQuery() {
        }

        public UpdateNewInlineQuery(long id, int fromId, String query, String offset) {
            this.id = id;
            this.fromId = fromId;
            this.query = query;
            this.offset = offset;
        }

        public static final int CONSTRUCTOR = -286842233;

        @Override
        public int getConstructor() {
            return -286842233;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateNewInlineQuery").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("id").append(" = ").append(id).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("fromId").append(" = ").append(fromId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("query").append(" = ").append(query).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("offset").append(" = ").append(offset).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class UpdateNewChosenInlineResult extends Update {
        public int fromId;
        public String query;
        public String resultId;

        public UpdateNewChosenInlineResult() {
        }

        public UpdateNewChosenInlineResult(int fromId, String query, String resultId) {
            this.fromId = fromId;
            this.query = query;
            this.resultId = resultId;
        }

        public static final int CONSTRUCTOR = -1602350604;

        @Override
        public int getConstructor() {
            return -1602350604;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateNewChosenInlineResult").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("fromId").append(" = ").append(fromId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("query").append(" = ").append(query).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("resultId").append(" = ").append(resultId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("User").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("id").append(" = ").append(id).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("firstName").append(" = ").append(firstName).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("lastName").append(" = ").append(lastName).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("username").append(" = ").append(username).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("phoneNumber").append(" = ").append(phoneNumber).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("status").append(" = "); status.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("profilePhoto").append(" = "); profilePhoto.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("myLink").append(" = "); myLink.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("foreignLink").append(" = "); foreignLink.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("isVerified").append(" = ").append(isVerified).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("restrictionReason").append(" = ").append(restrictionReason).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("haveAccess").append(" = ").append(haveAccess).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("type").append(" = "); type.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class UserFull extends TLObject {
        public User user;
        public boolean isBlocked;
        public BotInfo botInfo;

        public UserFull() {
        }

        public UserFull(User user, boolean isBlocked, BotInfo botInfo) {
            this.user = user;
            this.isBlocked = isBlocked;
            this.botInfo = botInfo;
        }

        public static final int CONSTRUCTOR = -1158674134;

        @Override
        public int getConstructor() {
            return -1158674134;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UserFull").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("user").append(" = "); user.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("isBlocked").append(" = ").append(isBlocked).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("botInfo").append(" = "); botInfo.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UserProfilePhotos").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("totalCount").append(" = ").append(totalCount).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("photos").append(" = ").append("Photo[]").append(" {").append(Arrays.toString(photos)).append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UserStatusEmpty").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UserStatusOnline").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("expires").append(" = ").append(expires).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UserStatusOffline").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("wasOnline").append(" = ").append(wasOnline).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UserStatusRecently").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UserStatusLastWeek").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UserStatusLastMonth").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UserTypeGeneral").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UserTypeDeleted").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class UserTypeBot extends UserType {
        public boolean canJoinGroupChats;
        public boolean canReadAllGroupChatMessages;
        public boolean isInline;
        public String inlineQueryPlaceholder;

        public UserTypeBot() {
        }

        public UserTypeBot(boolean canJoinGroupChats, boolean canReadAllGroupChatMessages, boolean isInline, String inlineQueryPlaceholder) {
            this.canJoinGroupChats = canJoinGroupChats;
            this.canReadAllGroupChatMessages = canReadAllGroupChatMessages;
            this.isInline = isInline;
            this.inlineQueryPlaceholder = inlineQueryPlaceholder;
        }

        public static final int CONSTRUCTOR = 1075507465;

        @Override
        public int getConstructor() {
            return 1075507465;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UserTypeBot").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("canJoinGroupChats").append(" = ").append(canJoinGroupChats).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("canReadAllGroupChatMessages").append(" = ").append(canReadAllGroupChatMessages).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("isInline").append(" = ").append(isInline).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("inlineQueryPlaceholder").append(" = ").append(inlineQueryPlaceholder).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UserTypeUnknown").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class Users extends TLObject {
        public User[] users;

        public Users() {
        }

        public Users(User[] users) {
            this.users = users;
        }

        public static final int CONSTRUCTOR = -53148716;

        @Override
        public int getConstructor() {
            return -53148716;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("Users").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("users").append(" = ").append("User[]").append(" {").append(Arrays.toString(users)).append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("Video").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("duration").append(" = ").append(duration).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("width").append(" = ").append(width).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("height").append(" = ").append(height).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("fileName").append(" = ").append(fileName).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("mimeType").append(" = ").append(mimeType).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("thumb").append(" = "); thumb.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("video").append(" = "); video.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("Voice").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("duration").append(" = ").append(duration).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("waveform").append(" = ").append("bytes { "); { for (byte k : waveform) { int b = (int)k & 255; s.append(HEX_CHARACTERS[b >> 4]).append(HEX_CHARACTERS[b & 15]).append(' '); } } s.append("}\n");
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("mimeType").append(" = ").append(mimeType).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("voice").append(" = "); voice.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        public Document document;
        public Sticker sticker;

        public WebPage() {
        }

        public WebPage(String url, String displayUrl, String type, String siteName, String title, String description, Photo photo, String embedUrl, String embedType, int embedWidth, int embedHeight, int duration, String author, Animation animation, Document document, Sticker sticker) {
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
            this.document = document;
            this.sticker = sticker;
        }

        public static final int CONSTRUCTOR = -839747118;

        @Override
        public int getConstructor() {
            return -839747118;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("WebPage").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("url").append(" = ").append(url).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("displayUrl").append(" = ").append(displayUrl).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("type").append(" = ").append(type).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("siteName").append(" = ").append(siteName).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("title").append(" = ").append(title).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("description").append(" = ").append(description).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("photo").append(" = "); photo.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("embedUrl").append(" = ").append(embedUrl).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("embedType").append(" = ").append(embedType).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("embedWidth").append(" = ").append(embedWidth).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("embedHeight").append(" = ").append(embedHeight).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("duration").append(" = ").append(duration).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("author").append(" = ").append(author).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("animation").append(" = "); animation.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("document").append(" = "); document.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("sticker").append(" = "); sticker.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestBytes").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("value").append(" = ").append("bytes { "); { for (byte k : value) { int b = (int)k & 255; s.append(HEX_CHARACTERS[b >> 4]).append(HEX_CHARACTERS[b & 15]).append(' '); } } s.append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestEmpty").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestInt").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("value").append(" = ").append(value).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestString").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("value").append(" = ").append(value).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestVectorInt").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("value").append(" = ").append("int[]").append(" {").append(Arrays.toString(value)).append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestVectorIntObject").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("value").append(" = ").append("TestInt[]").append(" {").append(Arrays.toString(value)).append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestVectorString").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("value").append(" = ").append("String[]").append(" {").append(Arrays.toString(value)).append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestVectorStringObject").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("value").append(" = ").append("TestString[]").append(" {").append(Arrays.toString(value)).append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class AddChatParticipant extends TLFunction {
        public long chatId;
        public int userId;
        public int forwardLimit;

        public AddChatParticipant() {
        }

        public AddChatParticipant(long chatId, int userId, int forwardLimit) {
            this.chatId = chatId;
            this.userId = userId;
            this.forwardLimit = forwardLimit;
        }

        public static final int CONSTRUCTOR = 572126454;

        @Override
        public int getConstructor() {
            return 572126454;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("AddChatParticipant").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("userId").append(" = ").append(userId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("forwardLimit").append(" = ").append(forwardLimit).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class AddChatParticipants extends TLFunction {
        public long chatId;
        public int[] userIds;

        public AddChatParticipants() {
        }

        public AddChatParticipants(long chatId, int[] userIds) {
            this.chatId = chatId;
            this.userIds = userIds;
        }

        public static final int CONSTRUCTOR = 1218907097;

        @Override
        public int getConstructor() {
            return 1218907097;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("AddChatParticipants").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("userIds").append(" = ").append("int[]").append(" {").append(Arrays.toString(userIds)).append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("AddRecentlyFoundChat").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("AddSavedAnimation").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("animation").append(" = "); animation.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class AnswerInlineQuery extends TLFunction {
        public long inlineQueryId;
        public boolean isPersonal;
        public InlineQueryResult[] results;
        public int cacheTime;
        public String nextOffset;

        public AnswerInlineQuery() {
        }

        public AnswerInlineQuery(long inlineQueryId, boolean isPersonal, InlineQueryResult[] results, int cacheTime, String nextOffset) {
            this.inlineQueryId = inlineQueryId;
            this.isPersonal = isPersonal;
            this.results = results;
            this.cacheTime = cacheTime;
            this.nextOffset = nextOffset;
        }

        public static final int CONSTRUCTOR = -545035034;

        @Override
        public int getConstructor() {
            return -545035034;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("AnswerInlineQuery").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("inlineQueryId").append(" = ").append(inlineQueryId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("isPersonal").append(" = ").append(isPersonal).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("results").append(" = ").append("InlineQueryResult[]").append(" {").append(Arrays.toString(results)).append("}\n");
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("cacheTime").append(" = ").append(cacheTime).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("nextOffset").append(" = ").append(nextOffset).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("BlockUser").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("userId").append(" = ").append(userId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("CancelDownloadFile").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("fileId").append(" = ").append(fileId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChangeChannelAbout").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("channelId").append(" = ").append(channelId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("about").append(" = ").append(about).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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

        public static final int CONSTRUCTOR = -393592521;

        @Override
        public int getConstructor() {
            return -393592521;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChangeChannelUsername").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("channelId").append(" = ").append(channelId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("username").append(" = ").append(username).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class ChangeChatParticipantRole extends TLFunction {
        public long chatId;
        public int userId;
        public ChatParticipantRole role;

        public ChangeChatParticipantRole() {
        }

        public ChangeChatParticipantRole(long chatId, int userId, ChatParticipantRole role) {
            this.chatId = chatId;
            this.userId = userId;
            this.role = role;
        }

        public static final int CONSTRUCTOR = -1967351300;

        @Override
        public int getConstructor() {
            return -1967351300;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChangeChatParticipantRole").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("userId").append(" = ").append(userId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("role").append(" = "); role.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class ChangeChatPhoto extends TLFunction {
        public long chatId;
        public InputFile photo;
        public PhotoCrop crop;

        public ChangeChatPhoto() {
        }

        public ChangeChatPhoto(long chatId, InputFile photo, PhotoCrop crop) {
            this.chatId = chatId;
            this.photo = photo;
            this.crop = crop;
        }

        public static final int CONSTRUCTOR = 1565887063;

        @Override
        public int getConstructor() {
            return 1565887063;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChangeChatPhoto").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("photo").append(" = "); photo.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("crop").append(" = "); crop.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChangeChatTitle").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("title").append(" = ").append(title).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChangeName").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("firstName").append(" = ").append(firstName).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("lastName").append(" = ").append(lastName).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChangeUsername").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("username").append(" = ").append(username).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("CheckAuthBotToken").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("token").append(" = ").append(token).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class CheckAuthCode extends TLFunction {
        public String code;

        public CheckAuthCode() {
        }

        public CheckAuthCode(String code) {
            this.code = code;
        }

        public static final int CONSTRUCTOR = 1145501069;

        @Override
        public int getConstructor() {
            return 1145501069;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("CheckAuthCode").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("code").append(" = ").append(code).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("CheckAuthPassword").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("password").append(" = ").append(password).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("CheckChatInviteLink").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("inviteLink").append(" = ").append(inviteLink).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("CloseChat").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("CreateChannelChat").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("channelId").append(" = ").append(channelId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("CreateGroupChat").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("groupId").append(" = ").append(groupId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class CreateNewChannelChat extends TLFunction {
        public String title;
        public boolean isBroadcast;
        public boolean isSupergroup;
        public String about;

        public CreateNewChannelChat() {
        }

        public CreateNewChannelChat(String title, boolean isBroadcast, boolean isSupergroup, String about) {
            this.title = title;
            this.isBroadcast = isBroadcast;
            this.isSupergroup = isSupergroup;
            this.about = about;
        }

        public static final int CONSTRUCTOR = 1818605150;

        @Override
        public int getConstructor() {
            return 1818605150;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("CreateNewChannelChat").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("title").append(" = ").append(title).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("isBroadcast").append(" = ").append(isBroadcast).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("isSupergroup").append(" = ").append(isSupergroup).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("about").append(" = ").append(about).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class CreateNewGroupChat extends TLFunction {
        public int[] participantIds;
        public String title;

        public CreateNewGroupChat() {
        }

        public CreateNewGroupChat(int[] participantIds, String title) {
            this.participantIds = participantIds;
            this.title = title;
        }

        public static final int CONSTRUCTOR = 253168424;

        @Override
        public int getConstructor() {
            return 253168424;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("CreateNewGroupChat").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("participantIds").append(" = ").append("int[]").append(" {").append(Arrays.toString(participantIds)).append("}\n");
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("title").append(" = ").append(title).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("CreateNewSecretChat").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("userId").append(" = ").append(userId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("CreatePrivateChat").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("userId").append(" = ").append(userId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("DeleteChannel").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("channelId").append(" = ").append(channelId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class DeleteChatHistory extends TLFunction {
        public long chatId;

        public DeleteChatHistory() {
        }

        public DeleteChatHistory(long chatId) {
            this.chatId = chatId;
        }

        public static final int CONSTRUCTOR = -1065852609;

        @Override
        public int getConstructor() {
            return -1065852609;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("DeleteChatHistory").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("DeleteChatReplyMarkup").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("messageId").append(" = ").append(messageId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("DeleteContacts").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("userIds").append(" = ").append("int[]").append(" {").append(Arrays.toString(userIds)).append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("DeleteMessages").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("messageIds").append(" = ").append("int[]").append(" {").append(Arrays.toString(messageIds)).append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("DeleteProfilePhoto").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("profilePhotoId").append(" = ").append(profilePhotoId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("DeleteRecentlyFoundChats").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("DeleteSavedAnimation").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("animation").append(" = "); animation.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("DownloadFile").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("fileId").append(" = ").append(fileId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ExportChatInviteLink").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class ForwardMessages extends TLFunction {
        public long chatId;
        public long fromChatId;
        public int[] messageIds;
        public boolean isPost;
        public boolean disableNotification;
        public boolean fromBackground;

        public ForwardMessages() {
        }

        public ForwardMessages(long chatId, long fromChatId, int[] messageIds, boolean isPost, boolean disableNotification, boolean fromBackground) {
            this.chatId = chatId;
            this.fromChatId = fromChatId;
            this.messageIds = messageIds;
            this.isPost = isPost;
            this.disableNotification = disableNotification;
            this.fromBackground = fromBackground;
        }

        public static final int CONSTRUCTOR = -1620591163;

        @Override
        public int getConstructor() {
            return -1620591163;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ForwardMessages").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("fromChatId").append(" = ").append(fromChatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("messageIds").append(" = ").append("int[]").append(" {").append(Arrays.toString(messageIds)).append("}\n");
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("isPost").append(" = ").append(isPost).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("disableNotification").append(" = ").append(disableNotification).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("fromBackground").append(" = ").append(fromBackground).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetAuthState").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetChannel").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("channelId").append(" = ").append(channelId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetChannelFull").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("channelId").append(" = ").append(channelId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class GetChannelParticipants extends TLFunction {
        public int channelId;
        public ChannelParticipantsFilter filter;
        public int offset;
        public int limit;

        public GetChannelParticipants() {
        }

        public GetChannelParticipants(int channelId, ChannelParticipantsFilter filter, int offset, int limit) {
            this.channelId = channelId;
            this.filter = filter;
            this.offset = offset;
            this.limit = limit;
        }

        public static final int CONSTRUCTOR = -1920911169;

        @Override
        public int getConstructor() {
            return -1920911169;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetChannelParticipants").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("channelId").append(" = ").append(channelId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("filter").append(" = "); filter.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("offset").append(" = ").append(offset).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("limit").append(" = ").append(limit).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetChat").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class GetChatHistory extends TLFunction {
        public long chatId;
        public int fromId;
        public int offset;
        public int limit;

        public GetChatHistory() {
        }

        public GetChatHistory(long chatId, int fromId, int offset, int limit) {
            this.chatId = chatId;
            this.fromId = fromId;
            this.offset = offset;
            this.limit = limit;
        }

        public static final int CONSTRUCTOR = 1089149649;

        @Override
        public int getConstructor() {
            return 1089149649;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetChatHistory").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("fromId").append(" = ").append(fromId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("offset").append(" = ").append(offset).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("limit").append(" = ").append(limit).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetChats").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("offsetOrder").append(" = ").append(offsetOrder).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("offsetChatId").append(" = ").append(offsetChatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("limit").append(" = ").append(limit).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class GetContacts extends TLFunction {

        public GetContacts() {
        }

        public static final int CONSTRUCTOR = 854387241;

        @Override
        public int getConstructor() {
            return 854387241;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetContacts").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetFile").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("fileId").append(" = ").append(fileId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetFilePersistent").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("persistentFileId").append(" = ").append(persistentFileId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetGroup").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("groupId").append(" = ").append(groupId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetGroupFull").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("groupId").append(" = ").append(groupId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class GetInlineQueryResults extends TLFunction {
        public int botUserId;
        public String query;
        public String offset;

        public GetInlineQueryResults() {
        }

        public GetInlineQueryResults(int botUserId, String query, String offset) {
            this.botUserId = botUserId;
            this.query = query;
            this.offset = offset;
        }

        public static final int CONSTRUCTOR = -1653782635;

        @Override
        public int getConstructor() {
            return -1653782635;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetInlineQueryResults").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("botUserId").append(" = ").append(botUserId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("query").append(" = ").append(query).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("offset").append(" = ").append(offset).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetMe").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetMessage").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("messageId").append(" = ").append(messageId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetNotificationSettings").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("scope").append(" = "); scope.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetOption").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("name").append(" = ").append(name).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetRecentInlineBots").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetSavedAnimations").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetStickerSet").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("setId").append(" = ").append(setId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetStickerSets").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("onlyEnabled").append(" = ").append(onlyEnabled).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetStickers").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("emoji").append(" = ").append(emoji).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetUser").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("userId").append(" = ").append(userId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetUserFull").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("userId").append(" = ").append(userId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetUserProfilePhotos").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("userId").append(" = ").append(userId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("offset").append(" = ").append(offset).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("limit").append(" = ").append(limit).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetWebPagePreview").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("messageText").append(" = ").append(messageText).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ImportChatInviteLink").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("inviteLink").append(" = ").append(inviteLink).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class ImportContacts extends TLFunction {
        public InputContact[] inputContacts;

        public ImportContacts() {
        }

        public ImportContacts(InputContact[] inputContacts) {
            this.inputContacts = inputContacts;
        }

        public static final int CONSTRUCTOR = 577501086;

        @Override
        public int getConstructor() {
            return 577501086;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ImportContacts").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("inputContacts").append(" = ").append("InputContact[]").append(" {").append(Arrays.toString(inputContacts)).append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("MigrateGroupChatToChannelChat").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("OpenChat").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("OpenMessageContent").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("messageId").append(" = ").append(messageId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("RecoverAuthPassword").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("recoverCode").append(" = ").append(recoverCode).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("RequestAuthPasswordRecovery").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ResetAuth").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("force").append(" = ").append(force).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class SearchChannel extends TLFunction {
        public String username;

        public SearchChannel() {
        }

        public SearchChannel(String username) {
            this.username = username;
        }

        public static final int CONSTRUCTOR = -1374578750;

        @Override
        public int getConstructor() {
            return -1374578750;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchChannel").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("username").append(" = ").append(username).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class SearchChatMessages extends TLFunction {
        public long chatId;
        public String query;
        public int fromMessageId;
        public int limit;
        public boolean importantOnly;
        public SearchMessagesFilter filter;

        public SearchChatMessages() {
        }

        public SearchChatMessages(long chatId, String query, int fromMessageId, int limit, boolean importantOnly, SearchMessagesFilter filter) {
            this.chatId = chatId;
            this.query = query;
            this.fromMessageId = fromMessageId;
            this.limit = limit;
            this.importantOnly = importantOnly;
            this.filter = filter;
        }

        public static final int CONSTRUCTOR = 174458122;

        @Override
        public int getConstructor() {
            return 174458122;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchChatMessages").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("query").append(" = ").append(query).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("fromMessageId").append(" = ").append(fromMessageId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("limit").append(" = ").append(limit).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("importantOnly").append(" = ").append(importantOnly).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("filter").append(" = "); filter.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchChats").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("query").append(" = ").append(query).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("limit").append(" = ").append(limit).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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

        public static final int CONSTRUCTOR = -358629071;

        @Override
        public int getConstructor() {
            return -358629071;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchContacts").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("query").append(" = ").append(query).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("limit").append(" = ").append(limit).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchMessages").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("query").append(" = ").append(query).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("offsetDate").append(" = ").append(offsetDate).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("offsetChatId").append(" = ").append(offsetChatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("offsetMessageId").append(" = ").append(offsetMessageId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("limit").append(" = ").append(limit).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchPublicChats").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("usernamePrefix").append(" = ").append(usernamePrefix).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchStickerSet").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("name").append(" = ").append(name).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class SearchUser extends TLFunction {
        public String username;

        public SearchUser() {
        }

        public SearchUser(String username) {
            this.username = username;
        }

        public static final int CONSTRUCTOR = 1973882035;

        @Override
        public int getConstructor() {
            return 1973882035;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SearchUser").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("username").append(" = ").append(username).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SendBotStartMessage").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("botUserId").append(" = ").append(botUserId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("parameter").append(" = ").append(parameter).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SendChatAction").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("action").append(" = "); action.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class SendInlineQueryResultMessage extends TLFunction {
        public long chatId;
        public int replyToMessageId;
        public boolean isPost;
        public boolean disableNotification;
        public boolean fromBackground;
        public long queryId;
        public String resultId;

        public SendInlineQueryResultMessage() {
        }

        public SendInlineQueryResultMessage(long chatId, int replyToMessageId, boolean isPost, boolean disableNotification, boolean fromBackground, long queryId, String resultId) {
            this.chatId = chatId;
            this.replyToMessageId = replyToMessageId;
            this.isPost = isPost;
            this.disableNotification = disableNotification;
            this.fromBackground = fromBackground;
            this.queryId = queryId;
            this.resultId = resultId;
        }

        public static final int CONSTRUCTOR = -351697766;

        @Override
        public int getConstructor() {
            return -351697766;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SendInlineQueryResultMessage").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("replyToMessageId").append(" = ").append(replyToMessageId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("isPost").append(" = ").append(isPost).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("disableNotification").append(" = ").append(disableNotification).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("fromBackground").append(" = ").append(fromBackground).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("queryId").append(" = ").append(queryId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("resultId").append(" = ").append(resultId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class SendMessage extends TLFunction {
        public long chatId;
        public int replyToMessageId;
        public boolean isPost;
        public boolean disableNotification;
        public boolean fromBackground;
        public ReplyMarkup replyMarkup;
        public InputMessageContent message;

        public SendMessage() {
        }

        public SendMessage(long chatId, int replyToMessageId, boolean isPost, boolean disableNotification, boolean fromBackground, ReplyMarkup replyMarkup, InputMessageContent message) {
            this.chatId = chatId;
            this.replyToMessageId = replyToMessageId;
            this.isPost = isPost;
            this.disableNotification = disableNotification;
            this.fromBackground = fromBackground;
            this.replyMarkup = replyMarkup;
            this.message = message;
        }

        public static final int CONSTRUCTOR = 1813785991;

        @Override
        public int getConstructor() {
            return 1813785991;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SendMessage").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("replyToMessageId").append(" = ").append(replyToMessageId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("isPost").append(" = ").append(isPost).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("disableNotification").append(" = ").append(disableNotification).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("fromBackground").append(" = ").append(fromBackground).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("replyMarkup").append(" = "); replyMarkup.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("message").append(" = "); message.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class SetAuthName extends TLFunction {
        public String firstName;
        public String lastName;

        public SetAuthName() {
        }

        public SetAuthName(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public static final int CONSTRUCTOR = 2074658130;

        @Override
        public int getConstructor() {
            return 2074658130;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SetAuthName").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("firstName").append(" = ").append(firstName).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("lastName").append(" = ").append(lastName).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class SetAuthPhoneNumber extends TLFunction {
        public String phoneNumber;

        public SetAuthPhoneNumber() {
        }

        public SetAuthPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public static final int CONSTRUCTOR = 1502564090;

        @Override
        public int getConstructor() {
            return 1502564090;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SetAuthPhoneNumber").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("phoneNumber").append(" = ").append(phoneNumber).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SetNotificationSettings").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("scope").append(" = "); scope.toStringBuilder(shift, s);
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("notificationSettings").append(" = "); notificationSettings.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SetOption").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("name").append(" = ").append(name).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("value").append(" = "); value.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class SetProfilePhoto extends TLFunction {
        public String photoPath;
        public PhotoCrop crop;

        public SetProfilePhoto() {
        }

        public SetProfilePhoto(String photoPath, PhotoCrop crop) {
            this.photoPath = photoPath;
            this.crop = crop;
        }

        public static final int CONSTRUCTOR = -652884715;

        @Override
        public int getConstructor() {
            return -652884715;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("SetProfilePhoto").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("photoPath").append(" = ").append(photoPath).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("crop").append(" = "); crop.toStringBuilder(shift, s);
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestCallBytes").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("x").append(" = ").append("bytes { "); { for (byte k : x) { int b = (int)k & 255; s.append(HEX_CHARACTERS[b >> 4]).append(HEX_CHARACTERS[b & 15]).append(' '); } } s.append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestCallEmpty").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestCallString").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("x").append(" = ").append(x).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestCallVectorInt").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("x").append(" = ").append("int[]").append(" {").append(Arrays.toString(x)).append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestCallVectorIntObject").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("x").append(" = ").append("TestInt[]").append(" {").append(Arrays.toString(x)).append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestCallVectorString").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("x").append(" = ").append("String[]").append(" {").append(Arrays.toString(x)).append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestCallVectorStringObject").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("x").append(" = ").append("TestString[]").append(" {").append(Arrays.toString(x)).append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestSquareInt").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("x").append(" = ").append(x).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestTestNet").append(" {\n");
            shift += 2;
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

    public static class ToggleChannelComments extends TLFunction {
        public int channelId;
        public boolean isBroadcast;

        public ToggleChannelComments() {
        }

        public ToggleChannelComments(int channelId, boolean isBroadcast) {
            this.channelId = channelId;
            this.isBroadcast = isBroadcast;
        }

        public static final int CONSTRUCTOR = 1326940102;

        @Override
        public int getConstructor() {
            return 1326940102;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ToggleChannelComments").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("channelId").append(" = ").append(channelId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("isBroadcast").append(" = ").append(isBroadcast).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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

        public static final int CONSTRUCTOR = 1029576155;

        @Override
        public int getConstructor() {
            return 1029576155;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ToggleChannelInvites").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("channelId").append(" = ").append(channelId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("anyoneCanInvite").append(" = ").append(anyoneCanInvite).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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

        public static final int CONSTRUCTOR = 775614344;

        @Override
        public int getConstructor() {
            return 775614344;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ToggleChannelSignMessages").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("channelId").append(" = ").append(channelId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("signMessages").append(" = ").append(signMessages).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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

        public static final int CONSTRUCTOR = 1816585875;

        @Override
        public int getConstructor() {
            return 1816585875;
        }

        @Override
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ToggleGroupEditors").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("groupId").append(" = ").append(groupId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("anyoneCanEdit").append(" = ").append(anyoneCanEdit).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UnblockUser").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("userId").append(" = ").append(userId).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateStickerSet").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("setId").append(" = ").append(setId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("isInstalled").append(" = ").append(isInstalled).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("isEnabled").append(" = ").append(isEnabled).append('\n');
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
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
        protected void toStringBuilder(int shift, StringBuilder s) {
            s.append("ViewMessages").append(" {\n");
            shift += 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("chatId").append(" = ").append(chatId).append('\n');
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("messageIds").append(" = ").append("int[]").append(" {").append(Arrays.toString(messageIds)).append("}\n");
            shift -= 2;
            for (int i = 0; i < shift; i++) { s.append(' '); } s.append("}\n");
        }
    }

}
