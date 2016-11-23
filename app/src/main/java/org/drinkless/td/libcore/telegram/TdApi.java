package org.drinkless.td.libcore.telegram;

import android.support.annotation.Nullable;
import java.util.Arrays;

/**
 * This class contains as static nested classes all other TDLib interface 
 * type-classes and function-classes.
 * <p>
 * It has no inner classes, functions or public members.
 */
public class TdApi {
    private static final char[] HEX_CHARACTERS = "0123456789ABCDEF".toCharArray();

    /**
     * This class is a base class for all TDLib interface classes.
     */
    public abstract static class TLObject {

        /**
         * @return identifier uniquely determining TL-type of the object.
         */
        public abstract int getConstructor();

        /**
         * @return string representation of the object.
         */
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

    /**
     * This class is a base class for all TDLib interface function-classes.
     */
    public abstract static class TLFunction extends TLObject {
    }

    /**
     * Contains infotmation about period of inactivity, after which the account of currently logged in user will be automatically deleted.
     */
    public static class AccountTtl extends TLObject {
        /**
         * Number of days of inactivity before account deletion, should be from 30 up to 366.
         */
        public int days;

        /**
         * Default constructor.
         */
        public AccountTtl() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param days Number of days of inactivity before account deletion, should be from 30 up to 366.
         */
        public AccountTtl(int days) {
            this.days = days;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 813220011;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Describes animation file. Animation should be encoded in gif or mp4 format.
     */
    public static class Animation extends TLObject {
        /**
         * Width of the animation.
         */
        public int width;
        /**
         * Height of the animation.
         */
        public int height;
        /**
         * Original name of a file as defined by sender.
         */
        public String fileName;
        /**
         * MIME type of a file, usually &quot;image/gif&quot; or &quot;video/mp4&quot;.
         */
        public String mimeType;
        /**
         * Animation thumb, nullable.
         */
        public @Nullable PhotoSize thumb;
        /**
         * File with the animation.
         */
        public File animation;

        /**
         * Default constructor.
         */
        public Animation() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param width Width of the animation.
         * @param height Height of the animation.
         * @param fileName Original name of a file as defined by sender.
         * @param mimeType MIME type of a file, usually &quot;image/gif&quot; or &quot;video/mp4&quot;.
         * @param thumb Animation thumb, nullable.
         * @param animation File with the animation.
         */
        public Animation(int width, int height, String fileName, String mimeType, PhotoSize thumb, File animation) {
            this.width = width;
            this.height = height;
            this.fileName = fileName;
            this.mimeType = mimeType;
            this.thumb = thumb;
            this.animation = animation;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -278960527;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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
            appendLine(s, shift).append("thumb = "); if (thumb != null) { thumb.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("animation = "); if (animation != null) { animation.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Represents list of animations.
     */
    public static class Animations extends TLObject {
        /**
         * Animations.
         */
        public Animation[] animations;

        /**
         * Default constructor.
         */
        public Animations() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param animations Animations.
         */
        public Animations(Animation[] animations) {
            this.animations = animations;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 344216945;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Describes audio file. Audio is usually in mp3 format.
     */
    public static class Audio extends TLObject {
        /**
         * Duration of the audio in seconds as defined by sender.
         */
        public int duration;
        /**
         * Title of the audio as defined by sender.
         */
        public String title;
        /**
         * Performer of the audio as defined by sender.
         */
        public String performer;
        /**
         * Original name of a file as defined by sender.
         */
        public String fileName;
        /**
         * MIME type of a file as defined by sender.
         */
        public String mimeType;
        /**
         * Thumb of the album's cover as defined by sender. Full size thumb should be extracted from the downloaded file, nullable.
         */
        public @Nullable PhotoSize albumCoverThumb;
        /**
         * File with the audio.
         */
        public File audio;

        /**
         * Default constructor.
         */
        public Audio() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param duration Duration of the audio in seconds as defined by sender.
         * @param title Title of the audio as defined by sender.
         * @param performer Performer of the audio as defined by sender.
         * @param fileName Original name of a file as defined by sender.
         * @param mimeType MIME type of a file as defined by sender.
         * @param albumCoverThumb Thumb of the album's cover as defined by sender. Full size thumb should be extracted from the downloaded file, nullable.
         * @param audio File with the audio.
         */
        public Audio(int duration, String title, String performer, String fileName, String mimeType, PhotoSize albumCoverThumb, File audio) {
            this.duration = duration;
            this.title = title;
            this.performer = performer;
            this.fileName = fileName;
            this.mimeType = mimeType;
            this.albumCoverThumb = albumCoverThumb;
            this.audio = audio;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -794337070;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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
            appendLine(s, shift).append("albumCoverThumb = "); if (albumCoverThumb != null) { albumCoverThumb.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("audio = "); if (audio != null) { audio.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * This class is an abstract base class.
     * Provides information about the way an authentication code is delivered to the user.
     */
    public abstract static class AuthCodeType extends TLObject {
    }

    /**
     * Code is delivered through private Telegram message, which can be viewed in the other client.
     */
    public static class AuthCodeTypeMessage extends AuthCodeType {
        /**
         * Length of the code.
         */
        public int length;

        /**
         * Default constructor.
         */
        public AuthCodeTypeMessage() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param length Length of the code.
         */
        public AuthCodeTypeMessage(int length) {
            this.length = length;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1692441707;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Code is delivered by SMS to the specified phone number.
     */
    public static class AuthCodeTypeSms extends AuthCodeType {
        /**
         * Length of the code.
         */
        public int length;

        /**
         * Default constructor.
         */
        public AuthCodeTypeSms() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param length Length of the code.
         */
        public AuthCodeTypeSms(int length) {
            this.length = length;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -7919996;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Code is delievered by voice call to the specified phone number.
     */
    public static class AuthCodeTypeCall extends AuthCodeType {
        /**
         * Length of the code.
         */
        public int length;

        /**
         * Default constructor.
         */
        public AuthCodeTypeCall() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param length Length of the code.
         */
        public AuthCodeTypeCall(int length) {
            this.length = length;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1432894294;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Code is delivered by the immediately cancelled call to the specified phone number. Number from which the call was done is the code.
     */
    public static class AuthCodeTypeFlashCall extends AuthCodeType {
        /**
         * Pattern of the phone number from which the call will be done.
         */
        public String pattern;

        /**
         * Default constructor.
         */
        public AuthCodeTypeFlashCall() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param pattern Pattern of the phone number from which the call will be done.
         */
        public AuthCodeTypeFlashCall(String pattern) {
            this.pattern = pattern;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1080364751;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * This class is an abstract base class.
     * Represents current authorization state of the Client.
     */
    public abstract static class AuthState extends TLObject {
    }

    /**
     * TDLib needs user's phone number to authorize.
     */
    public static class AuthStateWaitPhoneNumber extends AuthState {

        /**
         * Default constructor.
         */
        public AuthStateWaitPhoneNumber() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 167878457;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * TDLib needs user authentication code to finish authorization.
     */
    public static class AuthStateWaitCode extends AuthState {
        /**
         * True, if user is already registered.
         */
        public boolean isRegistered;
        /**
         * Describes the way, code was sent to the user.
         */
        public AuthCodeType codeType;
        /**
         * Describes the way, next code will be sent to the user, nullable.
         */
        public @Nullable AuthCodeType nextCodeType;
        /**
         * Timeout in seconds before code should be resent by calling resendAuthCode.
         */
        public int timeout;

        /**
         * Default constructor.
         */
        public AuthStateWaitCode() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param isRegistered True, if user is already registered.
         * @param codeType Describes the way, code was sent to the user.
         * @param nextCodeType Describes the way, next code will be sent to the user, nullable.
         * @param timeout Timeout in seconds before code should be resent by calling resendAuthCode.
         */
        public AuthStateWaitCode(boolean isRegistered, AuthCodeType codeType, AuthCodeType nextCodeType, int timeout) {
            this.isRegistered = isRegistered;
            this.codeType = codeType;
            this.nextCodeType = nextCodeType;
            this.timeout = timeout;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 2103299766;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 2103299766;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("AuthStateWaitCode").append(" {");
            shift += 2;
            appendLine(s, shift).append("isRegistered = ").append(isRegistered);
            appendLine(s, shift).append("codeType = "); if (codeType != null) { codeType.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("nextCodeType = "); if (nextCodeType != null) { nextCodeType.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("timeout = ").append(timeout);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * User is authorized but he needs to enter its password to begin to use application.
     */
    public static class AuthStateWaitPassword extends AuthState {
        /**
         * Hint on password, can be empty.
         */
        public String hint;
        /**
         * Is recovery email set up.
         */
        public boolean hasRecovery;
        /**
         * Pattern of email to which recovery mail was sent, empty before recovery email was sent.
         */
        public String emailUnconfirmedPattern;

        /**
         * Default constructor.
         */
        public AuthStateWaitPassword() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param hint Hint on password, can be empty.
         * @param hasRecovery Is recovery email set up.
         * @param emailUnconfirmedPattern Pattern of email to which recovery mail was sent, empty before recovery email was sent.
         */
        public AuthStateWaitPassword(String hint, boolean hasRecovery, String emailUnconfirmedPattern) {
            this.hint = hint;
            this.hasRecovery = hasRecovery;
            this.emailUnconfirmedPattern = emailUnconfirmedPattern;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -338450931;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * User is successfully authorized. TDLib can answer queries.
     */
    public static class AuthStateOk extends AuthState {

        /**
         * Default constructor.
         */
        public AuthStateOk() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1222968966;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * User is currently logging out.
     */
    public static class AuthStateLoggingOut extends AuthState {

        /**
         * Default constructor.
         */
        public AuthStateLoggingOut() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 965468001;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Contains information about one authorization in some application used by the user.
     */
    public static class Authorization extends TLObject {
        /**
         * Authorization identifier.
         */
        public long id;
        /**
         * True, if it is current authorization.
         */
        public boolean isCurrent;
        /**
         * Application identifier, provided by the application.
         */
        public int appId;
        /**
         * Name of the application, provided by the application.
         */
        public String appName;
        /**
         * Version of the application, provided by the application.
         */
        public String appVersion;
        /**
         * True, if the application is an official application or uses the appId of some official application.
         */
        public boolean isOfficialApp;
        /**
         * Model of a device application is runned on, provided by the application.
         */
        public String deviceModel;
        /**
         * Operating system application is runned on, provided by the application.
         */
        public String platform;
        /**
         * Version of operating system application is runned on, provided by the application.
         */
        public String systemVersion;
        /**
         * Date the user has logged in, unix time.
         */
        public int dateCreated;
        /**
         * Date the authorization was used last time, unix time.
         */
        public int dateActive;
        /**
         * An ip address from which authorization was created in a human-readable format.
         */
        public String ip;
        /**
         * Two-letter country code from which authorization was created based on the ip.
         */
        public String country;
        /**
         * Region code from which authorization was created based on the ip.
         */
        public String region;

        /**
         * Default constructor.
         */
        public Authorization() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Authorization identifier.
         * @param isCurrent True, if it is current authorization.
         * @param appId Application identifier, provided by the application.
         * @param appName Name of the application, provided by the application.
         * @param appVersion Version of the application, provided by the application.
         * @param isOfficialApp True, if the application is an official application or uses the appId of some official application.
         * @param deviceModel Model of a device application is runned on, provided by the application.
         * @param platform Operating system application is runned on, provided by the application.
         * @param systemVersion Version of operating system application is runned on, provided by the application.
         * @param dateCreated Date the user has logged in, unix time.
         * @param dateActive Date the authorization was used last time, unix time.
         * @param ip An ip address from which authorization was created in a human-readable format.
         * @param country Two-letter country code from which authorization was created based on the ip.
         * @param region Region code from which authorization was created based on the ip.
         */
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

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -844705341;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Contains list of authorizations.
     */
    public static class Authorizations extends TLObject {
        /**
         * List of authorizations.
         */
        public Authorization[] authorizations;

        /**
         * Default constructor.
         */
        public Authorizations() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param authorizations List of authorizations.
         */
        public Authorizations(Authorization[] authorizations) {
            this.authorizations = authorizations;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1499890846;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Represents command supported by bot.
     */
    public static class BotCommand extends TLObject {
        /**
         * Text of the bot command.
         */
        public String command;
        /**
         * Description of the bot command.
         */
        public String description;

        /**
         * Default constructor.
         */
        public BotCommand() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param command Text of the bot command.
         * @param description Description of the bot command.
         */
        public BotCommand(String command, String description) {
            this.command = command;
            this.description = description;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1032140601;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Provides information about bot and command supported by him.
     */
    public static class BotInfo extends TLObject {
        /**
         * Big description shown in user info page.
         */
        public String description;
        /**
         * List of commands cupported by bot.
         */
        public BotCommand[] commands;

        /**
         * Default constructor.
         */
        public BotInfo() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param description Big description shown in user info page.
         * @param commands List of commands cupported by bot.
         */
        public BotInfo(String description, BotCommand[] commands) {
            this.description = description;
            this.commands = commands;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1296528907;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Contains answer of the bot to the callback query.
     */
    public static class CallbackQueryAnswer extends TLObject {
        /**
         * Text of the answer.
         */
        public String text;
        /**
         * If true, an alert should be shown to the user instead of a toast.
         */
        public boolean showAlert;
        /**
         * URL to be open.
         */
        public String url;

        /**
         * Default constructor.
         */
        public CallbackQueryAnswer() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param text Text of the answer.
         * @param showAlert If true, an alert should be shown to the user instead of a toast.
         * @param url URL to be open.
         */
        public CallbackQueryAnswer(String text, boolean showAlert, String url) {
            this.text = text;
            this.showAlert = showAlert;
            this.url = url;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 360867933;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * This class is an abstract base class.
     * Represents payload of a callback query.
     */
    public abstract static class CallbackQueryPayload extends TLObject {
    }

    /**
     * Payload from a general callback button.
     */
    public static class CallbackQueryData extends CallbackQueryPayload {
        /**
         * Data that was attached to the callback button as specified by the users client.
         */
        public byte[] data;

        /**
         * Default constructor.
         */
        public CallbackQueryData() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param data Data that was attached to the callback button as specified by the users client.
         */
        public CallbackQueryData(byte[] data) {
            this.data = data;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1624256266;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Payload from a game callback button.
     */
    public static class CallbackQueryGame extends CallbackQueryPayload {
        /**
         * Short name of the game that was attached to the callback button.
         */
        public String gameShortName;

        /**
         * Default constructor.
         */
        public CallbackQueryGame() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param gameShortName Short name of the game that was attached to the callback button.
         */
        public CallbackQueryGame(String gameShortName) {
            this.gameShortName = gameShortName;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1634706916;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Represents a channel with zero or more subscribers. There two different kinds of channels: supergroups and broadcast channels.
     */
    public static class Channel extends TLObject {
        /**
         * Channel identifier.
         */
        public int id;
        /**
         * Channel username, empty for private channels.
         */
        public String username;
        /**
         * Date when current user has joined the channel or date when channel was created, if user is not a member. Unix time.
         */
        public int date;
        /**
         * Status of the current user in the channel.
         */
        public ChatMemberStatus status;
        /**
         * True, if any member of the supergroup can invite other members. If the channel is not a supergroup, the field is meaningless.
         */
        public boolean anyoneCanInvite;
        /**
         * True, if messages sent to the channel should content information about the sender. If the channel is a supergroup, the field is meaningless.
         */
        public boolean signMessages;
        /**
         * True, if channel is a supergroup and is not a broadcast.
         */
        public boolean isSupergroup;
        /**
         * True, if the channel is verified.
         */
        public boolean isVerified;
        /**
         * If non-empty, contains the reason, why access to this channel must be restricted. Format of the string is &quot;{type}: {description}&quot;. {type} contains type of the restriction and at least one of the suffixes &quot;-all&quot;, &quot;-ios&quot;, &quot;-android&quot;, &quot;-wp&quot;, which describes platforms on which access should be restricted. For example, &quot;terms-ios-android&quot;. {description} contains human-readable description of the restriction, which can be showed to the user.
         */
        public String restrictionReason;

        /**
         * Default constructor.
         */
        public Channel() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Channel identifier.
         * @param username Channel username, empty for private channels.
         * @param date Date when current user has joined the channel or date when channel was created, if user is not a member. Unix time.
         * @param status Status of the current user in the channel.
         * @param anyoneCanInvite True, if any member of the supergroup can invite other members. If the channel is not a supergroup, the field is meaningless.
         * @param signMessages True, if messages sent to the channel should content information about the sender. If the channel is a supergroup, the field is meaningless.
         * @param isSupergroup True, if channel is a supergroup and is not a broadcast.
         * @param isVerified True, if the channel is verified.
         * @param restrictionReason If non-empty, contains the reason, why access to this channel must be restricted. Format of the string is &quot;{type}: {description}&quot;. {type} contains type of the restriction and at least one of the suffixes &quot;-all&quot;, &quot;-ios&quot;, &quot;-android&quot;, &quot;-wp&quot;, which describes platforms on which access should be restricted. For example, &quot;terms-ios-android&quot;. {description} contains human-readable description of the restriction, which can be showed to the user.
         */
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

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1532245212;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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
            appendLine(s, shift).append("status = "); if (status != null) { status.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("anyoneCanInvite = ").append(anyoneCanInvite);
            appendLine(s, shift).append("signMessages = ").append(signMessages);
            appendLine(s, shift).append("isSupergroup = ").append(isSupergroup);
            appendLine(s, shift).append("isVerified = ").append(isVerified);
            appendLine(s, shift).append("restrictionReason = ").append(restrictionReason);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Gives full information about a channel.
     */
    public static class ChannelFull extends TLObject {
        /**
         * General info about the channel.
         */
        public Channel channel;
        /**
         * Information about the channel.
         */
        public String about;
        /**
         * Channel member count, 0 if unknown.
         */
        public int memberCount;
        /**
         * Number of privileged users in the channel, 0 if unknown.
         */
        public int administratorCount;
        /**
         * Number of users kicked from the channel, 0 if unknown.
         */
        public int kickedCount;
        /**
         * True, if members of the channel can be retrieved.
         */
        public boolean canGetMembers;
        /**
         * True, if channel can be made public.
         */
        public boolean canSetUsername;
        /**
         * Invite link for this channel.
         */
        public String inviteLink;
        /**
         * Identifier of the pinned message in the channel chat, or 0 if none.
         */
        public int pinnedMessageId;
        /**
         * Identifier of the group, this supergroup migrated from, or 0 if none.
         */
        public int migratedFromGroupId;
        /**
         * Identifier of last message in the group chat migrated from, or 0 if none.
         */
        public int migratedFromMaxMessageId;

        /**
         * Default constructor.
         */
        public ChannelFull() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param channel General info about the channel.
         * @param about Information about the channel.
         * @param memberCount Channel member count, 0 if unknown.
         * @param administratorCount Number of privileged users in the channel, 0 if unknown.
         * @param kickedCount Number of users kicked from the channel, 0 if unknown.
         * @param canGetMembers True, if members of the channel can be retrieved.
         * @param canSetUsername True, if channel can be made public.
         * @param inviteLink Invite link for this channel.
         * @param pinnedMessageId Identifier of the pinned message in the channel chat, or 0 if none.
         * @param migratedFromGroupId Identifier of the group, this supergroup migrated from, or 0 if none.
         * @param migratedFromMaxMessageId Identifier of last message in the group chat migrated from, or 0 if none.
         */
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

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1962673954;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -1962673954;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChannelFull").append(" {");
            shift += 2;
            appendLine(s, shift).append("channel = "); if (channel != null) { channel.toStringBuilder(shift, s); } else { s.append("null"); }
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

    /**
     * This class is an abstract base class.
     * Specifies kind of chat users to return in getChannelMembers.
     */
    public abstract static class ChannelMembersFilter extends TLObject {
    }

    /**
     * Return recently active users in reverse chronological order.
     */
    public static class ChannelMembersRecent extends ChannelMembersFilter {

        /**
         * Default constructor.
         */
        public ChannelMembersRecent() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1275194555;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Return privileged members, i.e. creator, editors and moderators are returned.
     */
    public static class ChannelMembersAdministrators extends ChannelMembersFilter {

        /**
         * Default constructor.
         */
        public ChannelMembersAdministrators() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 791495882;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Return kicked from the channel.
     */
    public static class ChannelMembersKicked extends ChannelMembersFilter {

        /**
         * Default constructor.
         */
        public ChannelMembersKicked() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1100658;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Return bots in the channel.
     */
    public static class ChannelMembersBots extends ChannelMembersFilter {

        /**
         * Default constructor.
         */
        public ChannelMembersBots() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1290076627;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Chat (private chat or group chat or channel chat).
     */
    public static class Chat extends TLObject {
        /**
         * Chat unique identifier.
         */
        public long id;
        /**
         * Chat title.
         */
        public String title;
        /**
         * Chat photo, nullable.
         */
        public @Nullable ChatPhoto photo;
        /**
         * Last message in the chat, nullable.
         */
        public @Nullable Message topMessage;
        /**
         * Parameter by descending of which chats are sorted in the chat list. If order of two chats is equal, then they need to be sorted by id also in descending order. If order == 0, position of the chat in the list is undetermined.
         */
        public long order;
        /**
         * Count of unread messages in the chat.
         */
        public int unreadCount;
        /**
         * Identifier of last read incoming message.
         */
        public int lastReadInboxMessageId;
        /**
         * Identifier of last read outgoing message.
         */
        public int lastReadOutboxMessageId;
        /**
         * Notification settings for this chat.
         */
        public NotificationSettings notificationSettings;
        /**
         * Identifier of the message from which reply markup need to be used or 0 if there is no default custom reply markup in the chat.
         */
        public int replyMarkupMessageId;
        /**
         * Draft of a message in the chat, nullable. parseMode in inputMessageText always will be null.
         */
        public @Nullable DraftMessage draftMessage;
        /**
         * Information about type of the chat.
         */
        public ChatInfo type;

        /**
         * Default constructor.
         */
        public Chat() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Chat unique identifier.
         * @param title Chat title.
         * @param photo Chat photo, nullable.
         * @param topMessage Last message in the chat, nullable.
         * @param order Parameter by descending of which chats are sorted in the chat list. If order of two chats is equal, then they need to be sorted by id also in descending order. If order == 0, position of the chat in the list is undetermined.
         * @param unreadCount Count of unread messages in the chat.
         * @param lastReadInboxMessageId Identifier of last read incoming message.
         * @param lastReadOutboxMessageId Identifier of last read outgoing message.
         * @param notificationSettings Notification settings for this chat.
         * @param replyMarkupMessageId Identifier of the message from which reply markup need to be used or 0 if there is no default custom reply markup in the chat.
         * @param draftMessage Draft of a message in the chat, nullable. parseMode in inputMessageText always will be null.
         * @param type Information about type of the chat.
         */
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

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -712119535;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -712119535;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("Chat").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("photo = "); if (photo != null) { photo.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("topMessage = "); if (topMessage != null) { topMessage.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("order = ").append(order);
            appendLine(s, shift).append("unreadCount = ").append(unreadCount);
            appendLine(s, shift).append("lastReadInboxMessageId = ").append(lastReadInboxMessageId);
            appendLine(s, shift).append("lastReadOutboxMessageId = ").append(lastReadOutboxMessageId);
            appendLine(s, shift).append("notificationSettings = "); if (notificationSettings != null) { notificationSettings.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("replyMarkupMessageId = ").append(replyMarkupMessageId);
            appendLine(s, shift).append("draftMessage = "); if (draftMessage != null) { draftMessage.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("type = "); if (type != null) { type.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * This class is an abstract base class.
     * Describes type of a chat.
     */
    public abstract static class ChatInfo extends TLObject {
    }

    /**
     * Ordinary chat with one user.
     */
    public static class PrivateChatInfo extends ChatInfo {
        /**
         * Information about interlocutor.
         */
        public User user;

        /**
         * Default constructor.
         */
        public PrivateChatInfo() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param user Information about interlocutor.
         */
        public PrivateChatInfo(User user) {
            this.user = user;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -241270753;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -241270753;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("PrivateChatInfo").append(" {");
            shift += 2;
            appendLine(s, shift).append("user = "); if (user != null) { user.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Chat with zero or more other users.
     */
    public static class GroupChatInfo extends ChatInfo {
        /**
         * Information about the chat.
         */
        public Group group;

        /**
         * Default constructor.
         */
        public GroupChatInfo() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param group Information about the chat.
         */
        public GroupChatInfo(Group group) {
            this.group = group;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1276053779;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 1276053779;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GroupChatInfo").append(" {");
            shift += 2;
            appendLine(s, shift).append("group = "); if (group != null) { group.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Chat with unlimited number of members.
     */
    public static class ChannelChatInfo extends ChatInfo {
        /**
         * Information about the chat.
         */
        public Channel channel;

        /**
         * Default constructor.
         */
        public ChannelChatInfo() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param channel Information about the chat.
         */
        public ChannelChatInfo(Channel channel) {
            this.channel = channel;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1297606545;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -1297606545;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChannelChatInfo").append(" {");
            shift += 2;
            appendLine(s, shift).append("channel = "); if (channel != null) { channel.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Secret chat with one user.
     */
    public static class SecretChatInfo extends ChatInfo {
        /**
         * Information about the chat.
         */
        public SecretChat secretChat;

        /**
         * Default constructor.
         */
        public SecretChatInfo() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param secretChat Information about the chat.
         */
        public SecretChatInfo(SecretChat secretChat) {
            this.secretChat = secretChat;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 361623800;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 361623800;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SecretChatInfo").append(" {");
            shift += 2;
            appendLine(s, shift).append("secretChat = "); if (secretChat != null) { secretChat.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Contains chat invite link.
     */
    public static class ChatInviteLink extends TLObject {
        /**
         * Chat invite link.
         */
        public String inviteLink;

        /**
         * Default constructor.
         */
        public ChatInviteLink() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param inviteLink Chat invite link.
         */
        public ChatInviteLink(String inviteLink) {
            this.inviteLink = inviteLink;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -882072492;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Contains information about chat invite link.
     */
    public static class ChatInviteLinkInfo extends TLObject {
        /**
         * Chat identifier of the invite link or 0 if user is not a member of this chat.
         */
        public long chatId;
        /**
         * Title of the chat.
         */
        public String title;
        /**
         * Chat photo, nullable.
         */
        public @Nullable ChatPhoto photo;
        /**
         * Total member count.
         */
        public int memberCount;
        /**
         * Some chat members that may be known to the current user.
         */
        public User[] members;
        /**
         * True, if the chat is a group chat.
         */
        public boolean isGroup;
        /**
         * True, if the chat is a channel chat.
         */
        public boolean isChannel;
        /**
         * True, if the chat is a channel chat with set up username.
         */
        public boolean isPublicChannel;
        /**
         * True, if the chat is a supergroup channel chat.
         */
        public boolean isSupergroupChannel;

        /**
         * Default constructor.
         */
        public ChatInviteLinkInfo() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier of the invite link or 0 if user is not a member of this chat.
         * @param title Title of the chat.
         * @param photo Chat photo, nullable.
         * @param memberCount Total member count.
         * @param members Some chat members that may be known to the current user.
         * @param isGroup True, if the chat is a group chat.
         * @param isChannel True, if the chat is a channel chat.
         * @param isPublicChannel True, if the chat is a channel chat with set up username.
         * @param isSupergroupChannel True, if the chat is a supergroup channel chat.
         */
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

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1161132260;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 1161132260;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChatInviteLinkInfo").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("photo = "); if (photo != null) { photo.toStringBuilder(shift, s); } else { s.append("null"); }
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

    /**
     * User with information about its chat joining/kicking.
     */
    public static class ChatMember extends TLObject {
        /**
         * User identifier of the chat member.
         */
        public int userId;
        /**
         * Identifier of a user invited this member to/kicked this member from the chat, 0 if unknown.
         */
        public int inviterUserId;
        /**
         * Date the user has joined a chat, unix time.
         */
        public int joinDate;
        /**
         * Status of the member in the chat.
         */
        public ChatMemberStatus status;
        /**
         * Information about bot if user is a bot, nullable. Can be null even for bot if bot is not a chat member.
         */
        public @Nullable BotInfo botInfo;

        /**
         * Default constructor.
         */
        public ChatMember() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param userId User identifier of the chat member.
         * @param inviterUserId Identifier of a user invited this member to/kicked this member from the chat, 0 if unknown.
         * @param joinDate Date the user has joined a chat, unix time.
         * @param status Status of the member in the chat.
         * @param botInfo Information about bot if user is a bot, nullable. Can be null even for bot if bot is not a chat member.
         */
        public ChatMember(int userId, int inviterUserId, int joinDate, ChatMemberStatus status, BotInfo botInfo) {
            this.userId = userId;
            this.inviterUserId = inviterUserId;
            this.joinDate = joinDate;
            this.status = status;
            this.botInfo = botInfo;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1759820220;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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
            appendLine(s, shift).append("status = "); if (status != null) { status.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("botInfo = "); if (botInfo != null) { botInfo.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * This class is an abstract base class.
     * Provides information about status of a member in the chat.
     */
    public abstract static class ChatMemberStatus extends TLObject {
    }

    /**
     * Creator of the chat, can delete any message, kick any user and add editors and moderators in channels.
     */
    public static class ChatMemberStatusCreator extends ChatMemberStatus {

        /**
         * Default constructor.
         */
        public ChatMemberStatusCreator() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 920396693;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * In broadcast channels, member that can post messages to the broadcast channel and have moderator rights. In groups and supergroups, member that can add new members to the chat/kick unpriviledged members.
     */
    public static class ChatMemberStatusEditor extends ChatMemberStatus {

        /**
         * Default constructor.
         */
        public ChatMemberStatusEditor() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -2061812836;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Only for channels, member that can delete messages of unprivileged members and kick them.
     */
    public static class ChatMemberStatusModerator extends ChatMemberStatus {

        /**
         * Default constructor.
         */
        public ChatMemberStatusModerator() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1687825199;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * User is a member of the chat, but have no any additional privileges.
     */
    public static class ChatMemberStatusMember extends ChatMemberStatus {

        /**
         * Default constructor.
         */
        public ChatMemberStatusMember() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 844723285;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * User is not a chat member.
     */
    public static class ChatMemberStatusLeft extends ChatMemberStatus {

        /**
         * Default constructor.
         */
        public ChatMemberStatusLeft() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -5815259;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * User was kicked from the chat (and obviously is not a chat member).
     */
    public static class ChatMemberStatusKicked extends ChatMemberStatus {

        /**
         * Default constructor.
         */
        public ChatMemberStatusKicked() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -982188162;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Contains list of chat members.
     */
    public static class ChatMembers extends TLObject {
        /**
         * Approximate total count of found chat members.
         */
        public int totalCount;
        /**
         * List of members.
         */
        public ChatMember[] members;

        /**
         * Default constructor.
         */
        public ChatMembers() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param totalCount Approximate total count of found chat members.
         * @param members List of members.
         */
        public ChatMembers(int totalCount, ChatMember[] members) {
            this.totalCount = totalCount;
            this.members = members;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1191923299;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Describes chat photo.
     */
    public static class ChatPhoto extends TLObject {
        /**
         * Small (160x160) chat photo.
         */
        public File small;
        /**
         * Big (640x640) chat photo.
         */
        public File big;

        /**
         * Default constructor.
         */
        public ChatPhoto() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param small Small (160x160) chat photo.
         * @param big Big (640x640) chat photo.
         */
        public ChatPhoto(File small, File big) {
            this.small = small;
            this.big = big;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -217062456;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -217062456;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChatPhoto").append(" {");
            shift += 2;
            appendLine(s, shift).append("small = "); if (small != null) { small.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("big = "); if (big != null) { big.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Contains list of chats.
     */
    public static class Chats extends TLObject {
        /**
         * List of chats.
         */
        public Chat[] chats;

        /**
         * Default constructor.
         */
        public Chats() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chats List of chats.
         */
        public Chats(Chat[] chats) {
            this.chats = chats;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -203185581;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Describes user contact.
     */
    public static class Contact extends TLObject {
        /**
         * User's phone number.
         */
        public String phoneNumber;
        /**
         * User first name, 1-255 characters.
         */
        public String firstName;
        /**
         * User last name.
         */
        public String lastName;
        /**
         * User identifier if known, 0 otherwise.
         */
        public int userId;

        /**
         * Default constructor.
         */
        public Contact() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param phoneNumber User's phone number.
         * @param firstName User first name, 1-255 characters.
         * @param lastName User last name.
         * @param userId User identifier if known, 0 otherwise.
         */
        public Contact(String phoneNumber, String firstName, String lastName, int userId) {
            this.phoneNumber = phoneNumber;
            this.firstName = firstName;
            this.lastName = lastName;
            this.userId = userId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -2089833262;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Describes document of any type.
     */
    public static class Document extends TLObject {
        /**
         * Original name of a file as defined by sender.
         */
        public String fileName;
        /**
         * MIME type of file as defined by sender.
         */
        public String mimeType;
        /**
         * Document thumb as defined by sender, nullable.
         */
        public @Nullable PhotoSize thumb;
        /**
         * File with document.
         */
        public File document;

        /**
         * Default constructor.
         */
        public Document() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param fileName Original name of a file as defined by sender.
         * @param mimeType MIME type of file as defined by sender.
         * @param thumb Document thumb as defined by sender, nullable.
         * @param document File with document.
         */
        public Document(String fileName, String mimeType, PhotoSize thumb, File document) {
            this.fileName = fileName;
            this.mimeType = mimeType;
            this.thumb = thumb;
            this.document = document;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 742605884;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 742605884;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("Document").append(" {");
            shift += 2;
            appendLine(s, shift).append("fileName = ").append(fileName);
            appendLine(s, shift).append("mimeType = ").append(mimeType);
            appendLine(s, shift).append("thumb = "); if (thumb != null) { thumb.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("document = "); if (document != null) { document.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Contains information about draft of a message.
     */
    public static class DraftMessage extends TLObject {
        /**
         * Identifier of a message to reply to or 0.
         */
        public int replyToMessageId;
        /**
         * Content of a draft message, always should be of a type inputMessageText.
         */
        public InputMessageContent inputMessageText;

        /**
         * Default constructor.
         */
        public DraftMessage() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param replyToMessageId Identifier of a message to reply to or 0.
         * @param inputMessageText Content of a draft message, always should be of a type inputMessageText.
         */
        public DraftMessage(int replyToMessageId, InputMessageContent inputMessageText) {
            this.replyToMessageId = replyToMessageId;
            this.inputMessageText = inputMessageText;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1818402019;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 1818402019;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("DraftMessage").append(" {");
            shift += 2;
            appendLine(s, shift).append("replyToMessageId = ").append(replyToMessageId);
            appendLine(s, shift).append("inputMessageText = "); if (inputMessageText != null) { inputMessageText.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Object of this type may be returned on every function call in case of the error.
     */
    public static class Error extends TLObject {
        /**
         * Error code, maybe changed in the future.
         */
        public int code;
        /**
         * Error message, may be changed in the future.
         */
        public String message;

        /**
         * Default constructor.
         */
        public Error() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param code Error code, maybe changed in the future.
         * @param message Error message, may be changed in the future.
         */
        public Error(int code, String message) {
            this.code = code;
            this.message = message;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 2115198528;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Represents a file.
     */
    public static class File extends TLObject {
        /**
         * Unique file identifier, 0 for empty file.
         */
        public int id;
        /**
         * Persistent file identifier, if exists. Can be used across application restarts or even other devices for current logged user. If begins with &quot;http://&quot; or &quot;https://&quot;, it is HTTP URL of the file. Currently, TDLib is unable to download files if only they URL is known.
         */
        public String persistentId;
        /**
         * File size, 0 if unknown.
         */
        public int size;
        /**
         * Local path to the file, if available.
         */
        public String path;

        /**
         * Default constructor.
         */
        public File() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Unique file identifier, 0 for empty file.
         * @param persistentId Persistent file identifier, if exists. Can be used across application restarts or even other devices for current logged user. If begins with &quot;http://&quot; or &quot;https://&quot;, it is HTTP URL of the file. Currently, TDLib is unable to download files if only they URL is known.
         * @param size File size, 0 if unknown.
         * @param path Local path to the file, if available.
         */
        public File(int id, String persistentId, int size, String path) {
            this.id = id;
            this.persistentId = persistentId;
            this.size = size;
            this.path = path;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1956331593;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Describes a game.
     */
    public static class Game extends TLObject {
        /**
         * Game id.
         */
        public long id;
        /**
         * Game short name, to share a game use a URL https://telegram.me/{botUsername}?game={gameShortName}.
         */
        public String shortName;
        /**
         * Game title.
         */
        public String title;
        /**
         * Game text, usually containing game scoreboards.
         */
        public String text;
        /**
         * Entities contained in the text.
         */
        public MessageEntity[] textEntities;
        /**
         * Game description.
         */
        public String description;
        /**
         * Game photo.
         */
        public Photo photo;
        /**
         * Game animation, nullable.
         */
        public @Nullable Animation animation;

        /**
         * Default constructor.
         */
        public Game() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Game id.
         * @param shortName Game short name, to share a game use a URL https://telegram.me/{botUsername}?game={gameShortName}.
         * @param title Game title.
         * @param text Game text, usually containing game scoreboards.
         * @param textEntities Entities contained in the text.
         * @param description Game description.
         * @param photo Game photo.
         * @param animation Game animation, nullable.
         */
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

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1777252919;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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
            appendLine(s, shift).append("photo = "); if (photo != null) { photo.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("animation = "); if (animation != null) { animation.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Contains one row of the game high scores table.
     */
    public static class GameHighScore extends TLObject {
        /**
         * Position in the high score table.
         */
        public int position;
        /**
         * User identifier.
         */
        public int userId;
        /**
         * User score.
         */
        public int score;

        /**
         * Default constructor.
         */
        public GameHighScore() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param position Position in the high score table.
         * @param userId User identifier.
         * @param score User score.
         */
        public GameHighScore(int position, int userId, int score) {
            this.position = position;
            this.userId = userId;
            this.score = score;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 518716217;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Contains list of game high scores.
     */
    public static class GameHighScores extends TLObject {
        /**
         * List of game high scores.
         */
        public GameHighScore[] scores;

        /**
         * Default constructor.
         */
        public GameHighScores() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param scores List of game high scores.
         */
        public GameHighScores(GameHighScore[] scores) {
            this.scores = scores;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -725770727;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Represents a group of zero or more other users.
     */
    public static class Group extends TLObject {
        /**
         * Group identifier.
         */
        public int id;
        /**
         * Group member count.
         */
        public int memberCount;
        /**
         * Status of the current user in the group.
         */
        public ChatMemberStatus status;
        /**
         * True, if all members granted editor rights in the group.
         */
        public boolean anyoneCanEdit;
        /**
         * True, if group is active.
         */
        public boolean isActive;
        /**
         * Identifier of channel (supergroup) to which this group was migrated or 0 if none.
         */
        public int migratedToChannelId;

        /**
         * Default constructor.
         */
        public Group() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Group identifier.
         * @param memberCount Group member count.
         * @param status Status of the current user in the group.
         * @param anyoneCanEdit True, if all members granted editor rights in the group.
         * @param isActive True, if group is active.
         * @param migratedToChannelId Identifier of channel (supergroup) to which this group was migrated or 0 if none.
         */
        public Group(int id, int memberCount, ChatMemberStatus status, boolean anyoneCanEdit, boolean isActive, int migratedToChannelId) {
            this.id = id;
            this.memberCount = memberCount;
            this.status = status;
            this.anyoneCanEdit = anyoneCanEdit;
            this.isActive = isActive;
            this.migratedToChannelId = migratedToChannelId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1988317413;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 1988317413;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("Group").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("memberCount = ").append(memberCount);
            appendLine(s, shift).append("status = "); if (status != null) { status.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("anyoneCanEdit = ").append(anyoneCanEdit);
            appendLine(s, shift).append("isActive = ").append(isActive);
            appendLine(s, shift).append("migratedToChannelId = ").append(migratedToChannelId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Gives full information about a group.
     */
    public static class GroupFull extends TLObject {
        /**
         * General info about the group.
         */
        public Group group;
        /**
         * User identifier of the group creator, 0 if unknown.
         */
        public int creatorUserId;
        /**
         * Group members.
         */
        public ChatMember[] members;
        /**
         * Invite link for this group, available only for group creator and only after it is generated at least once.
         */
        public String inviteLink;

        /**
         * Default constructor.
         */
        public GroupFull() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param group General info about the group.
         * @param creatorUserId User identifier of the group creator, 0 if unknown.
         * @param members Group members.
         * @param inviteLink Invite link for this group, available only for group creator and only after it is generated at least once.
         */
        public GroupFull(Group group, int creatorUserId, ChatMember[] members, String inviteLink) {
            this.group = group;
            this.creatorUserId = creatorUserId;
            this.members = members;
            this.inviteLink = inviteLink;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1934840272;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -1934840272;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GroupFull").append(" {");
            shift += 2;
            appendLine(s, shift).append("group = "); if (group != null) { group.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("creatorUserId = ").append(creatorUserId);
            appendLine(s, shift).append("members = ").append(Arrays.toString(members));
            appendLine(s, shift).append("inviteLink = ").append(inviteLink);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Represents one button of the inline keyboard.
     */
    public static class InlineKeyboardButton extends TLObject {
        /**
         * Text of the button.
         */
        public String text;
        /**
         * Type of the button.
         */
        public InlineKeyboardButtonType type;

        /**
         * Default constructor.
         */
        public InlineKeyboardButton() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param text Text of the button.
         * @param type Type of the button.
         */
        public InlineKeyboardButton(String text, InlineKeyboardButtonType type) {
            this.text = text;
            this.type = type;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -372105704;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -372105704;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineKeyboardButton").append(" {");
            shift += 2;
            appendLine(s, shift).append("text = ").append(text);
            appendLine(s, shift).append("type = "); if (type != null) { type.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * This class is an abstract base class.
     * Describes type of the inline keyboard button.
     */
    public abstract static class InlineKeyboardButtonType extends TLObject {
    }

    /**
     * A button which opens the specified URL.
     */
    public static class InlineKeyboardButtonTypeUrl extends InlineKeyboardButtonType {
        /**
         * URL to open.
         */
        public String url;

        /**
         * Default constructor.
         */
        public InlineKeyboardButtonTypeUrl() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param url URL to open.
         */
        public InlineKeyboardButtonTypeUrl(String url) {
            this.url = url;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1130741420;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * A button which sends to the bot special callback query.
     */
    public static class InlineKeyboardButtonTypeCallback extends InlineKeyboardButtonType {
        /**
         * Data to be sent to the bot through a callack query.
         */
        public byte[] data;

        /**
         * Default constructor.
         */
        public InlineKeyboardButtonTypeCallback() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param data Data to be sent to the bot through a callack query.
         */
        public InlineKeyboardButtonTypeCallback(byte[] data) {
            this.data = data;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1127515139;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * A button with a game which sends to the bot special callback query, must be in the first column and row of the keyboard, can be attached only to a message with content of the type messageGame.
     */
    public static class InlineKeyboardButtonTypeCallbackGame extends InlineKeyboardButtonType {

        /**
         * Default constructor.
         */
        public InlineKeyboardButtonTypeCallbackGame() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -383429528;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * A button which forces inline query to the bot to be substitued in the input field.
     */
    public static class InlineKeyboardButtonTypeSwitchInline extends InlineKeyboardButtonType {
        /**
         * Inline query to be sent to the bot.
         */
        public String query;
        /**
         * True, if the inline query should be sent from the current chat.
         */
        public boolean inCurrentChat;

        /**
         * Default constructor.
         */
        public InlineKeyboardButtonTypeSwitchInline() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param query Inline query to be sent to the bot.
         * @param inCurrentChat True, if the inline query should be sent from the current chat.
         */
        public InlineKeyboardButtonTypeSwitchInline(String query, boolean inCurrentChat) {
            this.query = query;
            this.inCurrentChat = inCurrentChat;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -2035563307;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * This class is an abstract base class.
     * Represents one result of the inline query.
     */
    public abstract static class InlineQueryResult extends TLObject {
    }

    /**
     * Represents link to an article or web page.
     */
    public static class InlineQueryResultArticle extends InlineQueryResult {
        /**
         * Unique identifier of this result.
         */
        public String id;
        /**
         * Url of the result, if exists.
         */
        public String url;
        /**
         * True, if url must be not shown.
         */
        public boolean hideUrl;
        /**
         * Title of the result.
         */
        public String title;
        /**
         * Short description of the result.
         */
        public String description;
        /**
         * Url of the result thumb, if exists.
         */
        public String thumbUrl;
        /**
         * Thumb width, if known.
         */
        public int thumbWidth;
        /**
         * Thumb height, if known.
         */
        public int thumbHeight;

        /**
         * Default constructor.
         */
        public InlineQueryResultArticle() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Unique identifier of this result.
         * @param url Url of the result, if exists.
         * @param hideUrl True, if url must be not shown.
         * @param title Title of the result.
         * @param description Short description of the result.
         * @param thumbUrl Url of the result thumb, if exists.
         * @param thumbWidth Thumb width, if known.
         * @param thumbHeight Thumb height, if known.
         */
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

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 571709788;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Represents user contact.
     */
    public static class InlineQueryResultContact extends InlineQueryResult {
        /**
         * Unique identifier of this result.
         */
        public String id;
        /**
         * User contact.
         */
        public Contact contact;
        /**
         * Url of the result thumb, if exists.
         */
        public String thumbUrl;
        /**
         * Thumb width, if known.
         */
        public int thumbWidth;
        /**
         * Thumb height, if known.
         */
        public int thumbHeight;

        /**
         * Default constructor.
         */
        public InlineQueryResultContact() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Unique identifier of this result.
         * @param contact User contact.
         * @param thumbUrl Url of the result thumb, if exists.
         * @param thumbWidth Thumb width, if known.
         * @param thumbHeight Thumb height, if known.
         */
        public InlineQueryResultContact(String id, Contact contact, String thumbUrl, int thumbWidth, int thumbHeight) {
            this.id = id;
            this.contact = contact;
            this.thumbUrl = thumbUrl;
            this.thumbWidth = thumbWidth;
            this.thumbHeight = thumbHeight;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1362196731;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 1362196731;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineQueryResultContact").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("contact = "); if (contact != null) { contact.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("thumbUrl = ").append(thumbUrl);
            appendLine(s, shift).append("thumbWidth = ").append(thumbWidth);
            appendLine(s, shift).append("thumbHeight = ").append(thumbHeight);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Represents a point on the map.
     */
    public static class InlineQueryResultLocation extends InlineQueryResult {
        /**
         * Unique identifier of this result.
         */
        public String id;
        /**
         * The result.
         */
        public Location location;
        /**
         * Title of the result.
         */
        public String title;
        /**
         * Url of the result thumb, if exists.
         */
        public String thumbUrl;
        /**
         * Thumb width, if known.
         */
        public int thumbWidth;
        /**
         * Thumb height, if known.
         */
        public int thumbHeight;

        /**
         * Default constructor.
         */
        public InlineQueryResultLocation() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Unique identifier of this result.
         * @param location The result.
         * @param title Title of the result.
         * @param thumbUrl Url of the result thumb, if exists.
         * @param thumbWidth Thumb width, if known.
         * @param thumbHeight Thumb height, if known.
         */
        public InlineQueryResultLocation(String id, Location location, String title, String thumbUrl, int thumbWidth, int thumbHeight) {
            this.id = id;
            this.location = location;
            this.title = title;
            this.thumbUrl = thumbUrl;
            this.thumbWidth = thumbWidth;
            this.thumbHeight = thumbHeight;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1290211266;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 1290211266;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineQueryResultLocation").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("location = "); if (location != null) { location.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("thumbUrl = ").append(thumbUrl);
            appendLine(s, shift).append("thumbWidth = ").append(thumbWidth);
            appendLine(s, shift).append("thumbHeight = ").append(thumbHeight);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Represents information about a venue.
     */
    public static class InlineQueryResultVenue extends InlineQueryResult {
        /**
         * Unique identifier of this result.
         */
        public String id;
        /**
         * The result.
         */
        public Venue venue;
        /**
         * Url of the result thumb, if exists.
         */
        public String thumbUrl;
        /**
         * Thumb width, if known.
         */
        public int thumbWidth;
        /**
         * Thumb height, if known.
         */
        public int thumbHeight;

        /**
         * Default constructor.
         */
        public InlineQueryResultVenue() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Unique identifier of this result.
         * @param venue The result.
         * @param thumbUrl Url of the result thumb, if exists.
         * @param thumbWidth Thumb width, if known.
         * @param thumbHeight Thumb height, if known.
         */
        public InlineQueryResultVenue(String id, Venue venue, String thumbUrl, int thumbWidth, int thumbHeight) {
            this.id = id;
            this.venue = venue;
            this.thumbUrl = thumbUrl;
            this.thumbWidth = thumbWidth;
            this.thumbHeight = thumbHeight;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1226696829;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -1226696829;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineQueryResultVenue").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("venue = "); if (venue != null) { venue.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("thumbUrl = ").append(thumbUrl);
            appendLine(s, shift).append("thumbWidth = ").append(thumbWidth);
            appendLine(s, shift).append("thumbHeight = ").append(thumbHeight);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Represents information about a game.
     */
    public static class InlineQueryResultGame extends InlineQueryResult {
        /**
         * Unique identifier of this result.
         */
        public String id;
        /**
         * The result.
         */
        public Game game;

        /**
         * Default constructor.
         */
        public InlineQueryResultGame() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Unique identifier of this result.
         * @param game The result.
         */
        public InlineQueryResultGame(String id, Game game) {
            this.id = id;
            this.game = game;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1706916987;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 1706916987;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineQueryResultGame").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("game = "); if (game != null) { game.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Represents an animation cached on the telegram server.
     */
    public static class InlineQueryResultAnimation extends InlineQueryResult {
        /**
         * Unique identifier of this result.
         */
        public String id;
        /**
         * The animation.
         */
        public Animation animation;
        /**
         * Animation title.
         */
        public String title;

        /**
         * Default constructor.
         */
        public InlineQueryResultAnimation() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Unique identifier of this result.
         * @param animation The animation.
         * @param title Animation title.
         */
        public InlineQueryResultAnimation(String id, Animation animation, String title) {
            this.id = id;
            this.animation = animation;
            this.title = title;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 2009984267;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 2009984267;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineQueryResultAnimation").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("animation = "); if (animation != null) { animation.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("title = ").append(title);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Represents an audio cached on the telegram server.
     */
    public static class InlineQueryResultAudio extends InlineQueryResult {
        /**
         * Unique identifier of this result.
         */
        public String id;
        /**
         * The audio.
         */
        public Audio audio;

        /**
         * Default constructor.
         */
        public InlineQueryResultAudio() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Unique identifier of this result.
         * @param audio The audio.
         */
        public InlineQueryResultAudio(String id, Audio audio) {
            this.id = id;
            this.audio = audio;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 842650360;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 842650360;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineQueryResultAudio").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("audio = "); if (audio != null) { audio.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Represents a document cached on the telegram server.
     */
    public static class InlineQueryResultDocument extends InlineQueryResult {
        /**
         * Unique identifier of this result.
         */
        public String id;
        /**
         * The document.
         */
        public Document document;
        /**
         * Document title.
         */
        public String title;
        /**
         * Document description.
         */
        public String description;

        /**
         * Default constructor.
         */
        public InlineQueryResultDocument() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Unique identifier of this result.
         * @param document The document.
         * @param title Document title.
         * @param description Document description.
         */
        public InlineQueryResultDocument(String id, Document document, String title, String description) {
            this.id = id;
            this.document = document;
            this.title = title;
            this.description = description;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1491268539;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -1491268539;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineQueryResultDocument").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("document = "); if (document != null) { document.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("description = ").append(description);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Represents a photo cached on the telegram server.
     */
    public static class InlineQueryResultPhoto extends InlineQueryResult {
        /**
         * Unique identifier of this result.
         */
        public String id;
        /**
         * The photo.
         */
        public Photo photo;
        /**
         * Title of the result, if known.
         */
        public String title;
        /**
         * Short description of the result, if known.
         */
        public String description;

        /**
         * Default constructor.
         */
        public InlineQueryResultPhoto() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Unique identifier of this result.
         * @param photo The photo.
         * @param title Title of the result, if known.
         * @param description Short description of the result, if known.
         */
        public InlineQueryResultPhoto(String id, Photo photo, String title, String description) {
            this.id = id;
            this.photo = photo;
            this.title = title;
            this.description = description;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1848319440;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 1848319440;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineQueryResultPhoto").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("photo = "); if (photo != null) { photo.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("description = ").append(description);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Represents a sticker cached on the telegram server.
     */
    public static class InlineQueryResultSticker extends InlineQueryResult {
        /**
         * Unique identifier of this result.
         */
        public String id;
        /**
         * The sticker.
         */
        public Sticker sticker;

        /**
         * Default constructor.
         */
        public InlineQueryResultSticker() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Unique identifier of this result.
         * @param sticker The sticker.
         */
        public InlineQueryResultSticker(String id, Sticker sticker) {
            this.id = id;
            this.sticker = sticker;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1848224245;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -1848224245;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineQueryResultSticker").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("sticker = "); if (sticker != null) { sticker.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Represents a video cached on the telegram server.
     */
    public static class InlineQueryResultVideo extends InlineQueryResult {
        /**
         * Unique identifier of this result.
         */
        public String id;
        /**
         * The video.
         */
        public Video video;
        /**
         * Title of the video.
         */
        public String title;
        /**
         * Description of the video.
         */
        public String description;

        /**
         * Default constructor.
         */
        public InlineQueryResultVideo() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Unique identifier of this result.
         * @param video The video.
         * @param title Title of the video.
         * @param description Description of the video.
         */
        public InlineQueryResultVideo(String id, Video video, String title, String description) {
            this.id = id;
            this.video = video;
            this.title = title;
            this.description = description;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1373158683;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -1373158683;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineQueryResultVideo").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("video = "); if (video != null) { video.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("description = ").append(description);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Represents a voice cached on the telegram server.
     */
    public static class InlineQueryResultVoice extends InlineQueryResult {
        /**
         * Unique identifier of this result.
         */
        public String id;
        /**
         * The voice.
         */
        public Voice voice;
        /**
         * Title of the voice file.
         */
        public String title;

        /**
         * Default constructor.
         */
        public InlineQueryResultVoice() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Unique identifier of this result.
         * @param voice The voice.
         * @param title Title of the voice file.
         */
        public InlineQueryResultVoice(String id, Voice voice, String title) {
            this.id = id;
            this.voice = voice;
            this.title = title;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1799819518;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -1799819518;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InlineQueryResultVoice").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("voice = "); if (voice != null) { voice.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("title = ").append(title);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Represents results of the inline query. Use sendInlineQueryResultMessage to send the result of the query.
     */
    public static class InlineQueryResults extends TLObject {
        /**
         * Unique identifier of the inline query.
         */
        public long inlineQueryId;
        /**
         * Offset for the next request. If it is empty, there is no more results.
         */
        public String nextOffset;
        /**
         * Results of the query.
         */
        public InlineQueryResult[] results;
        /**
         * If non-empty, this text should be shown on the button, which opens private chat with the bot and sends bot start message with parameter switchPmParameter.
         */
        public String switchPmText;
        /**
         * Parameter for the bot start message.
         */
        public String switchPmParameter;

        /**
         * Default constructor.
         */
        public InlineQueryResults() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param inlineQueryId Unique identifier of the inline query.
         * @param nextOffset Offset for the next request. If it is empty, there is no more results.
         * @param results Results of the query.
         * @param switchPmText If non-empty, this text should be shown on the button, which opens private chat with the bot and sends bot start message with parameter switchPmParameter.
         * @param switchPmParameter Parameter for the bot start message.
         */
        public InlineQueryResults(long inlineQueryId, String nextOffset, InlineQueryResult[] results, String switchPmText, String switchPmParameter) {
            this.inlineQueryId = inlineQueryId;
            this.nextOffset = nextOffset;
            this.results = results;
            this.switchPmText = switchPmText;
            this.switchPmParameter = switchPmParameter;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -399798881;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * This class is an abstract base class.
     * Points to some file.
     */
    public abstract static class InputFile extends TLObject {
    }

    /**
     * File defined by its id.
     */
    public static class InputFileId extends InputFile {
        /**
         * Unique file identifier.
         */
        public int id;

        /**
         * Default constructor.
         */
        public InputFileId() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Unique file identifier.
         */
        public InputFileId(int id) {
            this.id = id;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1553438243;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * File defined by its persistent id.
     */
    public static class InputFilePersistentId extends InputFile {
        /**
         * Persistent file identifier.
         */
        public String persistentId;

        /**
         * Default constructor.
         */
        public InputFilePersistentId() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param persistentId Persistent file identifier.
         */
        public InputFilePersistentId(String persistentId) {
            this.persistentId = persistentId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1856539551;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * File deifned by local path.
     */
    public static class InputFileLocal extends InputFile {
        /**
         * Local path to the file.
         */
        public String path;

        /**
         * Default constructor.
         */
        public InputFileLocal() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param path Local path to the file.
         */
        public InputFileLocal(String path) {
            this.path = path;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 2056030919;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * This class is an abstract base class.
     * Represents one result of the inline query received from the bot.
     */
    public abstract static class InputInlineQueryResult extends TLObject {
    }

    /**
     * Represents link to an animated gif.
     */
    public static class InputInlineQueryResultAnimatedGif extends InputInlineQueryResult {
        /**
         * Unique identifier of this result.
         */
        public String id;
        /**
         * Title of the result.
         */
        public String title;
        /**
         * Url of the static result thumb (jpeg or gif), if exists.
         */
        public String thumbUrl;
        /**
         * Url of the gif-file (file size must not exceed 1MB).
         */
        public String gifUrl;
        /**
         * Width of the gif.
         */
        public int gifWidth;
        /**
         * Height of the gif.
         */
        public int gifHeight;
        /**
         * Message reply markup, should be of type replyMarkupInlineKeyboard or null.
         */
        public ReplyMarkup replyMarkup;
        /**
         * Content of the message to be sent, should be of type inputMessageText or inputMessageAnimation or InputMessageLocation or InputMessageVenue or InputMessageContact.
         */
        public InputMessageContent inputMessageContent;

        /**
         * Default constructor.
         */
        public InputInlineQueryResultAnimatedGif() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Unique identifier of this result.
         * @param title Title of the result.
         * @param thumbUrl Url of the static result thumb (jpeg or gif), if exists.
         * @param gifUrl Url of the gif-file (file size must not exceed 1MB).
         * @param gifWidth Width of the gif.
         * @param gifHeight Height of the gif.
         * @param replyMarkup Message reply markup, should be of type replyMarkupInlineKeyboard or null.
         * @param inputMessageContent Content of the message to be sent, should be of type inputMessageText or inputMessageAnimation or InputMessageLocation or InputMessageVenue or InputMessageContact.
         */
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

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1525729396;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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
            appendLine(s, shift).append("replyMarkup = "); if (replyMarkup != null) { replyMarkup.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("inputMessageContent = "); if (inputMessageContent != null) { inputMessageContent.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Represents link to an animated (i.e. without sound) H.264/MPEG-4 AVC video.
     */
    public static class InputInlineQueryResultAnimatedMpeg4 extends InputInlineQueryResult {
        /**
         * Unique identifier of this result.
         */
        public String id;
        /**
         * Title of the result.
         */
        public String title;
        /**
         * Url of the static result thumb (jpeg or gif), if exists.
         */
        public String thumbUrl;
        /**
         * Url of the mp4-file (file size must not exceed 1MB).
         */
        public String mpeg4Url;
        /**
         * Width of the video.
         */
        public int mpeg4Width;
        /**
         * Height of the video.
         */
        public int mpeg4Height;
        /**
         * Message reply markup, should be of type replyMarkupInlineKeyboard or null.
         */
        public ReplyMarkup replyMarkup;
        /**
         * Content of the message to be sent, should be of type inputMessageText or inputMessageAnimation or InputMessageLocation or InputMessageVenue or InputMessageContact.
         */
        public InputMessageContent inputMessageContent;

        /**
         * Default constructor.
         */
        public InputInlineQueryResultAnimatedMpeg4() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Unique identifier of this result.
         * @param title Title of the result.
         * @param thumbUrl Url of the static result thumb (jpeg or gif), if exists.
         * @param mpeg4Url Url of the mp4-file (file size must not exceed 1MB).
         * @param mpeg4Width Width of the video.
         * @param mpeg4Height Height of the video.
         * @param replyMarkup Message reply markup, should be of type replyMarkupInlineKeyboard or null.
         * @param inputMessageContent Content of the message to be sent, should be of type inputMessageText or inputMessageAnimation or InputMessageLocation or InputMessageVenue or InputMessageContact.
         */
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

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1614693872;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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
            appendLine(s, shift).append("replyMarkup = "); if (replyMarkup != null) { replyMarkup.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("inputMessageContent = "); if (inputMessageContent != null) { inputMessageContent.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Represents link to an article or web page.
     */
    public static class InputInlineQueryResultArticle extends InputInlineQueryResult {
        /**
         * Unique identifier of this result.
         */
        public String id;
        /**
         * Url of the result, if exists.
         */
        public String url;
        /**
         * True, if url must be not shown.
         */
        public boolean hideUrl;
        /**
         * Title of the result.
         */
        public String title;
        /**
         * Short description of the result.
         */
        public String description;
        /**
         * Url of the result thumb, if exists.
         */
        public String thumbUrl;
        /**
         * Thumb width, if known.
         */
        public int thumbWidth;
        /**
         * Thumb height, if known.
         */
        public int thumbHeight;
        /**
         * Message reply markup, should be of type replyMarkupInlineKeyboard or null.
         */
        public ReplyMarkup replyMarkup;
        /**
         * Content of the message to be sent, should be of type inputMessageText or InputMessageLocation or InputMessageVenue or InputMessageContact.
         */
        public InputMessageContent inputMessageContent;

        /**
         * Default constructor.
         */
        public InputInlineQueryResultArticle() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Unique identifier of this result.
         * @param url Url of the result, if exists.
         * @param hideUrl True, if url must be not shown.
         * @param title Title of the result.
         * @param description Short description of the result.
         * @param thumbUrl Url of the result thumb, if exists.
         * @param thumbWidth Thumb width, if known.
         * @param thumbHeight Thumb height, if known.
         * @param replyMarkup Message reply markup, should be of type replyMarkupInlineKeyboard or null.
         * @param inputMessageContent Content of the message to be sent, should be of type inputMessageText or InputMessageLocation or InputMessageVenue or InputMessageContact.
         */
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

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1146479239;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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
            appendLine(s, shift).append("replyMarkup = "); if (replyMarkup != null) { replyMarkup.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("inputMessageContent = "); if (inputMessageContent != null) { inputMessageContent.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Represents link to a mp3 audio file.
     */
    public static class InputInlineQueryResultAudio extends InputInlineQueryResult {
        /**
         * Unique identifier of this result.
         */
        public String id;
        /**
         * Title of the audio.
         */
        public String title;
        /**
         * Performer of the audio.
         */
        public String performer;
        /**
         * Url of the audio file.
         */
        public String audioUrl;
        /**
         * Audio duration in seconds.
         */
        public int audioDuration;
        /**
         * Message reply markup, should be of type replyMarkupInlineKeyboard or null.
         */
        public ReplyMarkup replyMarkup;
        /**
         * Content of the message to be sent, should be of type inputMessageText or inputMessageAudio or InputMessageLocation or InputMessageVenue or InputMessageContact.
         */
        public InputMessageContent inputMessageContent;

        /**
         * Default constructor.
         */
        public InputInlineQueryResultAudio() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Unique identifier of this result.
         * @param title Title of the audio.
         * @param performer Performer of the audio.
         * @param audioUrl Url of the audio file.
         * @param audioDuration Audio duration in seconds.
         * @param replyMarkup Message reply markup, should be of type replyMarkupInlineKeyboard or null.
         * @param inputMessageContent Content of the message to be sent, should be of type inputMessageText or inputMessageAudio or InputMessageLocation or InputMessageVenue or InputMessageContact.
         */
        public InputInlineQueryResultAudio(String id, String title, String performer, String audioUrl, int audioDuration, ReplyMarkup replyMarkup, InputMessageContent inputMessageContent) {
            this.id = id;
            this.title = title;
            this.performer = performer;
            this.audioUrl = audioUrl;
            this.audioDuration = audioDuration;
            this.replyMarkup = replyMarkup;
            this.inputMessageContent = inputMessageContent;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1891166308;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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
            appendLine(s, shift).append("replyMarkup = "); if (replyMarkup != null) { replyMarkup.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("inputMessageContent = "); if (inputMessageContent != null) { inputMessageContent.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Represents user contact.
     */
    public static class InputInlineQueryResultContact extends InputInlineQueryResult {
        /**
         * Unique identifier of this result.
         */
        public String id;
        /**
         * User contact.
         */
        public Contact contact;
        /**
         * Url of the result thumb, if exists.
         */
        public String thumbUrl;
        /**
         * Thumb width, if known.
         */
        public int thumbWidth;
        /**
         * Thumb height, if known.
         */
        public int thumbHeight;
        /**
         * Message reply markup, should be of type replyMarkupInlineKeyboard or null.
         */
        public ReplyMarkup replyMarkup;
        /**
         * Content of the message to be sent, should be of type inputMessageText or InputMessageLocation or InputMessageVenue or InputMessageContact.
         */
        public InputMessageContent inputMessageContent;

        /**
         * Default constructor.
         */
        public InputInlineQueryResultContact() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Unique identifier of this result.
         * @param contact User contact.
         * @param thumbUrl Url of the result thumb, if exists.
         * @param thumbWidth Thumb width, if known.
         * @param thumbHeight Thumb height, if known.
         * @param replyMarkup Message reply markup, should be of type replyMarkupInlineKeyboard or null.
         * @param inputMessageContent Content of the message to be sent, should be of type inputMessageText or InputMessageLocation or InputMessageVenue or InputMessageContact.
         */
        public InputInlineQueryResultContact(String id, Contact contact, String thumbUrl, int thumbWidth, int thumbHeight, ReplyMarkup replyMarkup, InputMessageContent inputMessageContent) {
            this.id = id;
            this.contact = contact;
            this.thumbUrl = thumbUrl;
            this.thumbWidth = thumbWidth;
            this.thumbHeight = thumbHeight;
            this.replyMarkup = replyMarkup;
            this.inputMessageContent = inputMessageContent;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1322216516;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -1322216516;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputInlineQueryResultContact").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("contact = "); if (contact != null) { contact.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("thumbUrl = ").append(thumbUrl);
            appendLine(s, shift).append("thumbWidth = ").append(thumbWidth);
            appendLine(s, shift).append("thumbHeight = ").append(thumbHeight);
            appendLine(s, shift).append("replyMarkup = "); if (replyMarkup != null) { replyMarkup.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("inputMessageContent = "); if (inputMessageContent != null) { inputMessageContent.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Represents link to a file.
     */
    public static class InputInlineQueryResultDocument extends InputInlineQueryResult {
        /**
         * Unique identifier of this result.
         */
        public String id;
        /**
         * Title of the result.
         */
        public String title;
        /**
         * Short description of the result, if known.
         */
        public String description;
        /**
         * Url of the file.
         */
        public String documentUrl;
        /**
         * MIME type of the file content, only “application/pdf” and “application/zip” are allowed now.
         */
        public String mimeType;
        /**
         * Url of the file thumb, if exists.
         */
        public String thumbUrl;
        /**
         * Width of the thumb.
         */
        public int thumbWidth;
        /**
         * Height of the thumb.
         */
        public int thumbHeight;
        /**
         * Message reply markup, should be of type replyMarkupInlineKeyboard or null.
         */
        public ReplyMarkup replyMarkup;
        /**
         * Content of the message to be sent, should be of type inputMessageText or inputMessageDocument or InputMessageLocation or InputMessageVenue or InputMessageContact.
         */
        public InputMessageContent inputMessageContent;

        /**
         * Default constructor.
         */
        public InputInlineQueryResultDocument() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Unique identifier of this result.
         * @param title Title of the result.
         * @param description Short description of the result, if known.
         * @param documentUrl Url of the file.
         * @param mimeType MIME type of the file content, only “application/pdf” and “application/zip” are allowed now.
         * @param thumbUrl Url of the file thumb, if exists.
         * @param thumbWidth Width of the thumb.
         * @param thumbHeight Height of the thumb.
         * @param replyMarkup Message reply markup, should be of type replyMarkupInlineKeyboard or null.
         * @param inputMessageContent Content of the message to be sent, should be of type inputMessageText or inputMessageDocument or InputMessageLocation or InputMessageVenue or InputMessageContact.
         */
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

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 310750688;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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
            appendLine(s, shift).append("replyMarkup = "); if (replyMarkup != null) { replyMarkup.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("inputMessageContent = "); if (inputMessageContent != null) { inputMessageContent.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Represents a game.
     */
    public static class InputInlineQueryResultGame extends InputInlineQueryResult {
        /**
         * Unique identifier of this result.
         */
        public String id;
        /**
         * Game short name.
         */
        public String gameShortName;
        /**
         * Message reply markup, should be of type replyMarkupInlineKeyboard or null.
         */
        public ReplyMarkup replyMarkup;

        /**
         * Default constructor.
         */
        public InputInlineQueryResultGame() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Unique identifier of this result.
         * @param gameShortName Game short name.
         * @param replyMarkup Message reply markup, should be of type replyMarkupInlineKeyboard or null.
         */
        public InputInlineQueryResultGame(String id, String gameShortName, ReplyMarkup replyMarkup) {
            this.id = id;
            this.gameShortName = gameShortName;
            this.replyMarkup = replyMarkup;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 966074327;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 966074327;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputInlineQueryResultGame").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("gameShortName = ").append(gameShortName);
            appendLine(s, shift).append("replyMarkup = "); if (replyMarkup != null) { replyMarkup.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Represents a point on the map.
     */
    public static class InputInlineQueryResultLocation extends InputInlineQueryResult {
        /**
         * Unique identifier of this result.
         */
        public String id;
        /**
         * Result.
         */
        public Location location;
        /**
         * Title of the result.
         */
        public String title;
        /**
         * Url of the result thumb, if exists.
         */
        public String thumbUrl;
        /**
         * Thumb width, if known.
         */
        public int thumbWidth;
        /**
         * Thumb height, if known.
         */
        public int thumbHeight;
        /**
         * Message reply markup, should be of type replyMarkupInlineKeyboard or null.
         */
        public ReplyMarkup replyMarkup;
        /**
         * Content of the message to be sent, should be of type inputMessageText or InputMessageLocation or InputMessageVenue or InputMessageContact.
         */
        public InputMessageContent inputMessageContent;

        /**
         * Default constructor.
         */
        public InputInlineQueryResultLocation() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Unique identifier of this result.
         * @param location Result.
         * @param title Title of the result.
         * @param thumbUrl Url of the result thumb, if exists.
         * @param thumbWidth Thumb width, if known.
         * @param thumbHeight Thumb height, if known.
         * @param replyMarkup Message reply markup, should be of type replyMarkupInlineKeyboard or null.
         * @param inputMessageContent Content of the message to be sent, should be of type inputMessageText or InputMessageLocation or InputMessageVenue or InputMessageContact.
         */
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

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1842110793;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 1842110793;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputInlineQueryResultLocation").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("location = "); if (location != null) { location.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("thumbUrl = ").append(thumbUrl);
            appendLine(s, shift).append("thumbWidth = ").append(thumbWidth);
            appendLine(s, shift).append("thumbHeight = ").append(thumbHeight);
            appendLine(s, shift).append("replyMarkup = "); if (replyMarkup != null) { replyMarkup.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("inputMessageContent = "); if (inputMessageContent != null) { inputMessageContent.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Represents link to a jpeg photo.
     */
    public static class InputInlineQueryResultPhoto extends InputInlineQueryResult {
        /**
         * Unique identifier of this result.
         */
        public String id;
        /**
         * Title of the result, if known.
         */
        public String title;
        /**
         * Short description of the result, if known.
         */
        public String description;
        /**
         * Url of the photo thumb, if exists.
         */
        public String thumbUrl;
        /**
         * Url of the jpeg photo (photo must not exceed 5MB).
         */
        public String photoUrl;
        /**
         * Width of the photo.
         */
        public int photoWidth;
        /**
         * Height of the photo.
         */
        public int photoHeight;
        /**
         * Message reply markup, should be of type replyMarkupInlineKeyboard or null.
         */
        public ReplyMarkup replyMarkup;
        /**
         * Content of the message to be sent, should be of type inputMessageText or inputMessagePhoto or InputMessageLocation or InputMessageVenue or InputMessageContact.
         */
        public InputMessageContent inputMessageContent;

        /**
         * Default constructor.
         */
        public InputInlineQueryResultPhoto() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Unique identifier of this result.
         * @param title Title of the result, if known.
         * @param description Short description of the result, if known.
         * @param thumbUrl Url of the photo thumb, if exists.
         * @param photoUrl Url of the jpeg photo (photo must not exceed 5MB).
         * @param photoWidth Width of the photo.
         * @param photoHeight Height of the photo.
         * @param replyMarkup Message reply markup, should be of type replyMarkupInlineKeyboard or null.
         * @param inputMessageContent Content of the message to be sent, should be of type inputMessageText or inputMessagePhoto or InputMessageLocation or InputMessageVenue or InputMessageContact.
         */
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

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 737237435;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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
            appendLine(s, shift).append("replyMarkup = "); if (replyMarkup != null) { replyMarkup.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("inputMessageContent = "); if (inputMessageContent != null) { inputMessageContent.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Represents link to a webp sticker.
     */
    public static class InputInlineQueryResultSticker extends InputInlineQueryResult {
        /**
         * Unique identifier of this result.
         */
        public String id;
        /**
         * Url of the sticker thumb, if exists.
         */
        public String thumbUrl;
        /**
         * Url of the webp sticker (file with a sticker must not exceed 5MB).
         */
        public String stickerUrl;
        /**
         * Width of the sticker.
         */
        public int stickerWidth;
        /**
         * Height of the sticker.
         */
        public int stickerHeight;
        /**
         * Message reply markup, should be of type replyMarkupInlineKeyboard or null.
         */
        public ReplyMarkup replyMarkup;
        /**
         * Content of the message to be sent, should be of type inputMessageText or inputMessageSticker or InputMessageLocation or InputMessageVenue or InputMessageContact.
         */
        public InputMessageContent inputMessageContent;

        /**
         * Default constructor.
         */
        public InputInlineQueryResultSticker() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Unique identifier of this result.
         * @param thumbUrl Url of the sticker thumb, if exists.
         * @param stickerUrl Url of the webp sticker (file with a sticker must not exceed 5MB).
         * @param stickerWidth Width of the sticker.
         * @param stickerHeight Height of the sticker.
         * @param replyMarkup Message reply markup, should be of type replyMarkupInlineKeyboard or null.
         * @param inputMessageContent Content of the message to be sent, should be of type inputMessageText or inputMessageSticker or InputMessageLocation or InputMessageVenue or InputMessageContact.
         */
        public InputInlineQueryResultSticker(String id, String thumbUrl, String stickerUrl, int stickerWidth, int stickerHeight, ReplyMarkup replyMarkup, InputMessageContent inputMessageContent) {
            this.id = id;
            this.thumbUrl = thumbUrl;
            this.stickerUrl = stickerUrl;
            this.stickerWidth = stickerWidth;
            this.stickerHeight = stickerHeight;
            this.replyMarkup = replyMarkup;
            this.inputMessageContent = inputMessageContent;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 850856384;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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
            appendLine(s, shift).append("replyMarkup = "); if (replyMarkup != null) { replyMarkup.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("inputMessageContent = "); if (inputMessageContent != null) { inputMessageContent.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Represents information about a venue.
     */
    public static class InputInlineQueryResultVenue extends InputInlineQueryResult {
        /**
         * Unique identifier of this result.
         */
        public String id;
        /**
         * Result.
         */
        public Venue venue;
        /**
         * Url of the result thumb, if exists.
         */
        public String thumbUrl;
        /**
         * Thumb width, if known.
         */
        public int thumbWidth;
        /**
         * Thumb height, if known.
         */
        public int thumbHeight;
        /**
         * Message reply markup, should be of type replyMarkupInlineKeyboard or null.
         */
        public ReplyMarkup replyMarkup;
        /**
         * Content of the message to be sent, should be of type inputMessageText or InputMessageLocation or InputMessageVenue or InputMessageContact.
         */
        public InputMessageContent inputMessageContent;

        /**
         * Default constructor.
         */
        public InputInlineQueryResultVenue() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Unique identifier of this result.
         * @param venue Result.
         * @param thumbUrl Url of the result thumb, if exists.
         * @param thumbWidth Thumb width, if known.
         * @param thumbHeight Thumb height, if known.
         * @param replyMarkup Message reply markup, should be of type replyMarkupInlineKeyboard or null.
         * @param inputMessageContent Content of the message to be sent, should be of type inputMessageText or InputMessageLocation or InputMessageVenue or InputMessageContact.
         */
        public InputInlineQueryResultVenue(String id, Venue venue, String thumbUrl, int thumbWidth, int thumbHeight, ReplyMarkup replyMarkup, InputMessageContent inputMessageContent) {
            this.id = id;
            this.venue = venue;
            this.thumbUrl = thumbUrl;
            this.thumbWidth = thumbWidth;
            this.thumbHeight = thumbHeight;
            this.replyMarkup = replyMarkup;
            this.inputMessageContent = inputMessageContent;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -567519575;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -567519575;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputInlineQueryResultVenue").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("venue = "); if (venue != null) { venue.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("thumbUrl = ").append(thumbUrl);
            appendLine(s, shift).append("thumbWidth = ").append(thumbWidth);
            appendLine(s, shift).append("thumbHeight = ").append(thumbHeight);
            appendLine(s, shift).append("replyMarkup = "); if (replyMarkup != null) { replyMarkup.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("inputMessageContent = "); if (inputMessageContent != null) { inputMessageContent.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Represents link to a page containing an embedded video player or a video file.
     */
    public static class InputInlineQueryResultVideo extends InputInlineQueryResult {
        /**
         * Unique identifier of this result.
         */
        public String id;
        /**
         * Title of the result.
         */
        public String title;
        /**
         * Short description of the result, if known.
         */
        public String description;
        /**
         * Url of the video thumb (jpeg), if exists.
         */
        public String thumbUrl;
        /**
         * Url of the embedded video player or video file.
         */
        public String videoUrl;
        /**
         * MIME type of the content of video url, only &quot;text/html&quot; or &quot;video/mp4&quot; are allowed now.
         */
        public String mimeType;
        /**
         * Video width.
         */
        public int videoWidth;
        /**
         * Video height.
         */
        public int videoHeight;
        /**
         * Video duration in seconds.
         */
        public int videoDuration;
        /**
         * Message reply markup, should be of type replyMarkupInlineKeyboard or null.
         */
        public ReplyMarkup replyMarkup;
        /**
         * Content of the message to be sent, should be of type inputMessageText or inputMessageVideo or InputMessageLocation or InputMessageVenue or InputMessageContact.
         */
        public InputMessageContent inputMessageContent;

        /**
         * Default constructor.
         */
        public InputInlineQueryResultVideo() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Unique identifier of this result.
         * @param title Title of the result.
         * @param description Short description of the result, if known.
         * @param thumbUrl Url of the video thumb (jpeg), if exists.
         * @param videoUrl Url of the embedded video player or video file.
         * @param mimeType MIME type of the content of video url, only &quot;text/html&quot; or &quot;video/mp4&quot; are allowed now.
         * @param videoWidth Video width.
         * @param videoHeight Video height.
         * @param videoDuration Video duration in seconds.
         * @param replyMarkup Message reply markup, should be of type replyMarkupInlineKeyboard or null.
         * @param inputMessageContent Content of the message to be sent, should be of type inputMessageText or inputMessageVideo or InputMessageLocation or InputMessageVenue or InputMessageContact.
         */
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

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -2120242070;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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
            appendLine(s, shift).append("replyMarkup = "); if (replyMarkup != null) { replyMarkup.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("inputMessageContent = "); if (inputMessageContent != null) { inputMessageContent.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Represents link to a opus encoded audio file in ogg contatiner.
     */
    public static class InputInlineQueryResultVoice extends InputInlineQueryResult {
        /**
         * Unique identifier of this result.
         */
        public String id;
        /**
         * Title of the voice file.
         */
        public String title;
        /**
         * Url of the voice file.
         */
        public String voiceUrl;
        /**
         * Voice duration in seconds.
         */
        public int voiceDuration;
        /**
         * Message reply markup, should be of type replyMarkupInlineKeyboard or null.
         */
        public ReplyMarkup replyMarkup;
        /**
         * Content of the message to be sent, should be of type inputMessageText or inputMessageVoice or InputMessageLocation or InputMessageVenue or InputMessageContact.
         */
        public InputMessageContent inputMessageContent;

        /**
         * Default constructor.
         */
        public InputInlineQueryResultVoice() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Unique identifier of this result.
         * @param title Title of the voice file.
         * @param voiceUrl Url of the voice file.
         * @param voiceDuration Voice duration in seconds.
         * @param replyMarkup Message reply markup, should be of type replyMarkupInlineKeyboard or null.
         * @param inputMessageContent Content of the message to be sent, should be of type inputMessageText or inputMessageVoice or InputMessageLocation or InputMessageVenue or InputMessageContact.
         */
        public InputInlineQueryResultVoice(String id, String title, String voiceUrl, int voiceDuration, ReplyMarkup replyMarkup, InputMessageContent inputMessageContent) {
            this.id = id;
            this.title = title;
            this.voiceUrl = voiceUrl;
            this.voiceDuration = voiceDuration;
            this.replyMarkup = replyMarkup;
            this.inputMessageContent = inputMessageContent;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 950126512;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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
            appendLine(s, shift).append("replyMarkup = "); if (replyMarkup != null) { replyMarkup.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("inputMessageContent = "); if (inputMessageContent != null) { inputMessageContent.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * This class is an abstract base class.
     * Content of a message to send.
     */
    public abstract static class InputMessageContent extends TLObject {
    }

    /**
     * Text message.
     */
    public static class InputMessageText extends InputMessageContent {
        /**
         * Text to send.
         */
        public String text;
        /**
         * Pass true to disable rich preview for link in the message text.
         */
        public boolean disableWebPagePreview;
        /**
         * Pass true if chat draft message should be deleted.
         */
        public boolean clearDraft;
        /**
         * Bold, Italic, Code, Pre, PreCode and TextUrl entities contained in the text. Non-bot users can't use TextUrl entities. Can't be used with non-null parseMode.
         */
        public MessageEntity[] entities;
        /**
         * Text parse mode, nullable. Can't be used along with enitities.
         */
        public @Nullable TextParseMode parseMode;

        /**
         * Default constructor.
         */
        public InputMessageText() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param text Text to send.
         * @param disableWebPagePreview Pass true to disable rich preview for link in the message text.
         * @param clearDraft Pass true if chat draft message should be deleted.
         * @param entities Bold, Italic, Code, Pre, PreCode and TextUrl entities contained in the text. Non-bot users can't use TextUrl entities. Can't be used with non-null parseMode.
         * @param parseMode Text parse mode, nullable. Can't be used along with enitities.
         */
        public InputMessageText(String text, boolean disableWebPagePreview, boolean clearDraft, MessageEntity[] entities, TextParseMode parseMode) {
            this.text = text;
            this.disableWebPagePreview = disableWebPagePreview;
            this.clearDraft = clearDraft;
            this.entities = entities;
            this.parseMode = parseMode;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1095854080;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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
            appendLine(s, shift).append("parseMode = "); if (parseMode != null) { parseMode.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Animation message.
     */
    public static class InputMessageAnimation extends InputMessageContent {
        /**
         * Animation file to send.
         */
        public InputFile animation;
        /**
         * Animation thumb, if available.
         */
        public InputThumb thumb;
        /**
         * Width of the animation, may be replaced by the server.
         */
        public int width;
        /**
         * Height of the animation, may be replaced by the server.
         */
        public int height;
        /**
         * Animation caption, 0-200 characters.
         */
        public String caption;

        /**
         * Default constructor.
         */
        public InputMessageAnimation() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param animation Animation file to send.
         * @param thumb Animation thumb, if available.
         * @param width Width of the animation, may be replaced by the server.
         * @param height Height of the animation, may be replaced by the server.
         * @param caption Animation caption, 0-200 characters.
         */
        public InputMessageAnimation(InputFile animation, InputThumb thumb, int width, int height, String caption) {
            this.animation = animation;
            this.thumb = thumb;
            this.width = width;
            this.height = height;
            this.caption = caption;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -238183558;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -238183558;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessageAnimation").append(" {");
            shift += 2;
            appendLine(s, shift).append("animation = "); if (animation != null) { animation.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("thumb = "); if (thumb != null) { thumb.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("width = ").append(width);
            appendLine(s, shift).append("height = ").append(height);
            appendLine(s, shift).append("caption = ").append(caption);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Audio message.
     */
    public static class InputMessageAudio extends InputMessageContent {
        /**
         * Audio file to send.
         */
        public InputFile audio;
        /**
         * Thumb of the album's cover, if available.
         */
        public InputThumb albumCoverThumb;
        /**
         * Duration of audio in seconds, may be replaced by the server.
         */
        public int duration;
        /**
         * Title of the audio, 0-64 characters, may be replaced by the server.
         */
        public String title;
        /**
         * Performer of the audio, 0-64 characters, may be replaced by the server.
         */
        public String performer;
        /**
         * Audio caption, 0-200 characters.
         */
        public String caption;

        /**
         * Default constructor.
         */
        public InputMessageAudio() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param audio Audio file to send.
         * @param albumCoverThumb Thumb of the album's cover, if available.
         * @param duration Duration of audio in seconds, may be replaced by the server.
         * @param title Title of the audio, 0-64 characters, may be replaced by the server.
         * @param performer Performer of the audio, 0-64 characters, may be replaced by the server.
         * @param caption Audio caption, 0-200 characters.
         */
        public InputMessageAudio(InputFile audio, InputThumb albumCoverThumb, int duration, String title, String performer, String caption) {
            this.audio = audio;
            this.albumCoverThumb = albumCoverThumb;
            this.duration = duration;
            this.title = title;
            this.performer = performer;
            this.caption = caption;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1122193064;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -1122193064;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessageAudio").append(" {");
            shift += 2;
            appendLine(s, shift).append("audio = "); if (audio != null) { audio.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("albumCoverThumb = "); if (albumCoverThumb != null) { albumCoverThumb.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("duration = ").append(duration);
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("performer = ").append(performer);
            appendLine(s, shift).append("caption = ").append(caption);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Document message.
     */
    public static class InputMessageDocument extends InputMessageContent {
        /**
         * Document to send.
         */
        public InputFile document;
        /**
         * Document thumb, if available.
         */
        public InputThumb thumb;
        /**
         * Document caption, 0-200 characters.
         */
        public String caption;

        /**
         * Default constructor.
         */
        public InputMessageDocument() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param document Document to send.
         * @param thumb Document thumb, if available.
         * @param caption Document caption, 0-200 characters.
         */
        public InputMessageDocument(InputFile document, InputThumb thumb, String caption) {
            this.document = document;
            this.thumb = thumb;
            this.caption = caption;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 395779985;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 395779985;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessageDocument").append(" {");
            shift += 2;
            appendLine(s, shift).append("document = "); if (document != null) { document.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("thumb = "); if (thumb != null) { thumb.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("caption = ").append(caption);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Photo message.
     */
    public static class InputMessagePhoto extends InputMessageContent {
        /**
         * Photo to send.
         */
        public InputFile photo;
        /**
         * Photo caption, 0-200 characters.
         */
        public String caption;

        /**
         * Default constructor.
         */
        public InputMessagePhoto() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param photo Photo to send.
         * @param caption Photo caption, 0-200 characters.
         */
        public InputMessagePhoto(InputFile photo, String caption) {
            this.photo = photo;
            this.caption = caption;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 762116923;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 762116923;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessagePhoto").append(" {");
            shift += 2;
            appendLine(s, shift).append("photo = "); if (photo != null) { photo.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("caption = ").append(caption);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Sticker message.
     */
    public static class InputMessageSticker extends InputMessageContent {
        /**
         * Sticker to send.
         */
        public InputFile sticker;
        /**
         * Sticker thumb, if available.
         */
        public InputThumb thumb;

        /**
         * Default constructor.
         */
        public InputMessageSticker() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param sticker Sticker to send.
         * @param thumb Sticker thumb, if available.
         */
        public InputMessageSticker(InputFile sticker, InputThumb thumb) {
            this.sticker = sticker;
            this.thumb = thumb;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -892964456;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -892964456;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessageSticker").append(" {");
            shift += 2;
            appendLine(s, shift).append("sticker = "); if (sticker != null) { sticker.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("thumb = "); if (thumb != null) { thumb.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Video message.
     */
    public static class InputMessageVideo extends InputMessageContent {
        /**
         * Video to send.
         */
        public InputFile video;
        /**
         * Video thumb, if available.
         */
        public InputThumb thumb;
        /**
         * Duration of video in seconds.
         */
        public int duration;
        /**
         * Video width.
         */
        public int width;
        /**
         * Video height.
         */
        public int height;
        /**
         * Video caption, 0-200 characters.
         */
        public String caption;

        /**
         * Default constructor.
         */
        public InputMessageVideo() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param video Video to send.
         * @param thumb Video thumb, if available.
         * @param duration Duration of video in seconds.
         * @param width Video width.
         * @param height Video height.
         * @param caption Video caption, 0-200 characters.
         */
        public InputMessageVideo(InputFile video, InputThumb thumb, int duration, int width, int height, String caption) {
            this.video = video;
            this.thumb = thumb;
            this.duration = duration;
            this.width = width;
            this.height = height;
            this.caption = caption;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1954994738;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -1954994738;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessageVideo").append(" {");
            shift += 2;
            appendLine(s, shift).append("video = "); if (video != null) { video.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("thumb = "); if (thumb != null) { thumb.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("duration = ").append(duration);
            appendLine(s, shift).append("width = ").append(width);
            appendLine(s, shift).append("height = ").append(height);
            appendLine(s, shift).append("caption = ").append(caption);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Voice message.
     */
    public static class InputMessageVoice extends InputMessageContent {
        /**
         * Voice file to send.
         */
        public InputFile voice;
        /**
         * Duration of voice in seconds.
         */
        public int duration;
        /**
         * Waveform representation of the voice in 5-bit format.
         */
        public byte[] waveform;
        /**
         * Voice caption, 0-200 characters.
         */
        public String caption;

        /**
         * Default constructor.
         */
        public InputMessageVoice() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param voice Voice file to send.
         * @param duration Duration of voice in seconds.
         * @param waveform Waveform representation of the voice in 5-bit format.
         * @param caption Voice caption, 0-200 characters.
         */
        public InputMessageVoice(InputFile voice, int duration, byte[] waveform, String caption) {
            this.voice = voice;
            this.duration = duration;
            this.waveform = waveform;
            this.caption = caption;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -2011553452;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -2011553452;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessageVoice").append(" {");
            shift += 2;
            appendLine(s, shift).append("voice = "); if (voice != null) { voice.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("duration = ").append(duration);
            appendLine(s, shift).append("waveform = ").append("bytes { "); { for (byte k : waveform) { int b = (int)k & 255; s.append(HEX_CHARACTERS[b >> 4]).append(HEX_CHARACTERS[b & 15]).append(' '); } } s.append('}');
            appendLine(s, shift).append("caption = ").append(caption);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Message with location.
     */
    public static class InputMessageLocation extends InputMessageContent {
        /**
         * Location to send.
         */
        public Location location;

        /**
         * Default constructor.
         */
        public InputMessageLocation() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param location Location to send.
         */
        public InputMessageLocation(Location location) {
            this.location = location;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 2121763042;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 2121763042;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessageLocation").append(" {");
            shift += 2;
            appendLine(s, shift).append("location = "); if (location != null) { location.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Message with information about venue.
     */
    public static class InputMessageVenue extends InputMessageContent {
        /**
         * Venue to send.
         */
        public Venue venue;

        /**
         * Default constructor.
         */
        public InputMessageVenue() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param venue Venue to send.
         */
        public InputMessageVenue(Venue venue) {
            this.venue = venue;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1447926269;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 1447926269;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessageVenue").append(" {");
            shift += 2;
            appendLine(s, shift).append("venue = "); if (venue != null) { venue.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * User contact message.
     */
    public static class InputMessageContact extends InputMessageContent {
        /**
         * Contact to send.
         */
        public Contact contact;

        /**
         * Default constructor.
         */
        public InputMessageContact() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param contact Contact to send.
         */
        public InputMessageContact(Contact contact) {
            this.contact = contact;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -982446849;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -982446849;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("InputMessageContact").append(" {");
            shift += 2;
            appendLine(s, shift).append("contact = "); if (contact != null) { contact.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Message with a game.
     */
    public static class InputMessageGame extends InputMessageContent {
        /**
         * User identifier of a bot owned the game.
         */
        public int botUserId;
        /**
         * Game short name.
         */
        public String gameShortName;

        /**
         * Default constructor.
         */
        public InputMessageGame() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param botUserId User identifier of a bot owned the game.
         * @param gameShortName Game short name.
         */
        public InputMessageGame(int botUserId, String gameShortName) {
            this.botUserId = botUserId;
            this.gameShortName = gameShortName;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1989596156;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Forwarded message.
     */
    public static class InputMessageForwarded extends InputMessageContent {
        /**
         * Chat identifier of the message to forward.
         */
        public long fromChatId;
        /**
         * Identifier of the message to forward.
         */
        public int messageId;

        /**
         * Default constructor.
         */
        public InputMessageForwarded() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param fromChatId Chat identifier of the message to forward.
         * @param messageId Identifier of the message to forward.
         */
        public InputMessageForwarded(long fromChatId, int messageId) {
            this.fromChatId = fromChatId;
            this.messageId = messageId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 863879612;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Thumb to send along with a file, should be in jpeg format or webp format for stickers.
     */
    public static class InputThumb extends TLObject {
        /**
         * Path to the file with the thumb.
         */
        public String path;
        /**
         * Thumb width, use 0 if unknown.
         */
        public int width;
        /**
         * Thumb height, use 0 if unknown.
         */
        public int height;

        /**
         * Default constructor.
         */
        public InputThumb() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param path Path to the file with the thumb.
         * @param width Thumb width, use 0 if unknown.
         * @param height Thumb height, use 0 if unknown.
         */
        public InputThumb(String path, int width, int height) {
            this.path = path;
            this.width = width;
            this.height = height;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -227565803;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Represents one button of the bot keyboard.
     */
    public static class KeyboardButton extends TLObject {
        /**
         * Text of the button.
         */
        public String text;
        /**
         * Type of the button.
         */
        public KeyboardButtonType type;

        /**
         * Default constructor.
         */
        public KeyboardButton() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param text Text of the button.
         * @param type Type of the button.
         */
        public KeyboardButton(String text, KeyboardButtonType type) {
            this.text = text;
            this.type = type;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -2069836172;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -2069836172;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("KeyboardButton").append(" {");
            shift += 2;
            appendLine(s, shift).append("text = ").append(text);
            appendLine(s, shift).append("type = "); if (type != null) { type.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * This class is an abstract base class.
     * Describes type of the keyboard button.
     */
    public abstract static class KeyboardButtonType extends TLObject {
    }

    /**
     * Simple button with a text, which should be sent when the button is pressed.
     */
    public static class KeyboardButtonTypeText extends KeyboardButtonType {

        /**
         * Default constructor.
         */
        public KeyboardButtonTypeText() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1773037256;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * A button which sends user's phone number when pressed, available only in private chats.
     */
    public static class KeyboardButtonTypeRequestPhoneNumber extends KeyboardButtonType {

        /**
         * Default constructor.
         */
        public KeyboardButtonTypeRequestPhoneNumber() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1529235527;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * A button which sends user location when pressed, available only in private chats.
     */
    public static class KeyboardButtonTypeRequestLocation extends KeyboardButtonType {

        /**
         * Default constructor.
         */
        public KeyboardButtonTypeRequestLocation() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -125661955;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * This class is an abstract base class.
     * Represents ordered relationship between two users.
     */
    public abstract static class LinkState extends TLObject {
    }

    /**
     * Other user's phone number doesn't known.
     */
    public static class LinkStateNone extends LinkState {

        /**
         * Default constructor.
         */
        public LinkStateNone() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 951430287;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Other user's phone number is known but user not in contacts list.
     */
    public static class LinkStateKnowsPhoneNumber extends LinkState {

        /**
         * Default constructor.
         */
        public LinkStateKnowsPhoneNumber() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 380898199;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Other user is in contacts list, particularly its phone number is known.
     */
    public static class LinkStateContact extends LinkState {

        /**
         * Default constructor.
         */
        public LinkStateContact() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -731324681;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Describes location on Earth.
     */
    public static class Location extends TLObject {
        /**
         * Latitude of location in degrees as defined by sender.
         */
        public double latitude;
        /**
         * Longitude of location in degrees as defined by sender.
         */
        public double longitude;

        /**
         * Default constructor.
         */
        public Location() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param latitude Latitude of location in degrees as defined by sender.
         * @param longitude Longitude of location in degrees as defined by sender.
         */
        public Location(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 749028016;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Describes message.
     */
    public static class Message extends TLObject {
        /**
         * Unique message identifier.
         */
        public int id;
        /**
         * Identifier of the user who sent the message, 0 if unknown. It can be unknown for channel posts which are not signed by the author.
         */
        public int senderUserId;
        /**
         * Chat identifier.
         */
        public long chatId;
        /**
         * Information about sending state of the message.
         */
        public MessageSendState sendState;
        /**
         * True, if message can be edited.
         */
        public boolean canBeEdited;
        /**
         * True, if message can be deleted.
         */
        public boolean canBeDeleted;
        /**
         * True, if message is channel post. All messages to broadcast channels are posts, all other messages are not posts.
         */
        public boolean isPost;
        /**
         * Date when message was sent, unix time.
         */
        public int date;
        /**
         * Date when message was edited last time, unix time.
         */
        public int editDate;
        /**
         * Information about initial message sender, nullable.
         */
        public @Nullable MessageForwardInfo forwardInfo;
        /**
         * If non-zero, identifier of the message this message replies to, can be identifier of deleted message.
         */
        public int replyToMessageId;
        /**
         * If non-zero, user identifier of the bot this message is sent via.
         */
        public int viaBotUserId;
        /**
         * Number of times this message was viewed.
         */
        public int views;
        /**
         * Content of the message.
         */
        public MessageContent content;
        /**
         * Reply markup for the message, nullable.
         */
        public @Nullable ReplyMarkup replyMarkup;

        /**
         * Default constructor.
         */
        public Message() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Unique message identifier.
         * @param senderUserId Identifier of the user who sent the message, 0 if unknown. It can be unknown for channel posts which are not signed by the author.
         * @param chatId Chat identifier.
         * @param sendState Information about sending state of the message.
         * @param canBeEdited True, if message can be edited.
         * @param canBeDeleted True, if message can be deleted.
         * @param isPost True, if message is channel post. All messages to broadcast channels are posts, all other messages are not posts.
         * @param date Date when message was sent, unix time.
         * @param editDate Date when message was edited last time, unix time.
         * @param forwardInfo Information about initial message sender, nullable.
         * @param replyToMessageId If non-zero, identifier of the message this message replies to, can be identifier of deleted message.
         * @param viaBotUserId If non-zero, user identifier of the bot this message is sent via.
         * @param views Number of times this message was viewed.
         * @param content Content of the message.
         * @param replyMarkup Reply markup for the message, nullable.
         */
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

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1284920704;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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
            appendLine(s, shift).append("sendState = "); if (sendState != null) { sendState.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("canBeEdited = ").append(canBeEdited);
            appendLine(s, shift).append("canBeDeleted = ").append(canBeDeleted);
            appendLine(s, shift).append("isPost = ").append(isPost);
            appendLine(s, shift).append("date = ").append(date);
            appendLine(s, shift).append("editDate = ").append(editDate);
            appendLine(s, shift).append("forwardInfo = "); if (forwardInfo != null) { forwardInfo.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("replyToMessageId = ").append(replyToMessageId);
            appendLine(s, shift).append("viaBotUserId = ").append(viaBotUserId);
            appendLine(s, shift).append("views = ").append(views);
            appendLine(s, shift).append("content = "); if (content != null) { content.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("replyMarkup = "); if (replyMarkup != null) { replyMarkup.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * This class is an abstract base class.
     * Content of a message.
     */
    public abstract static class MessageContent extends TLObject {
    }

    /**
     * Text message.
     */
    public static class MessageText extends MessageContent {
        /**
         * Text of the message.
         */
        public String text;
        /**
         * Entities contained in the text.
         */
        public MessageEntity[] entities;
        /**
         * Preview of a web page mentioned in the text, nullable.
         */
        public @Nullable WebPage webPage;

        /**
         * Default constructor.
         */
        public MessageText() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param text Text of the message.
         * @param entities Entities contained in the text.
         * @param webPage Preview of a web page mentioned in the text, nullable.
         */
        public MessageText(String text, MessageEntity[] entities, WebPage webPage) {
            this.text = text;
            this.entities = entities;
            this.webPage = webPage;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 964064453;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 964064453;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageText").append(" {");
            shift += 2;
            appendLine(s, shift).append("text = ").append(text);
            appendLine(s, shift).append("entities = ").append(Arrays.toString(entities));
            appendLine(s, shift).append("webPage = "); if (webPage != null) { webPage.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Animation message.
     */
    public static class MessageAnimation extends MessageContent {
        /**
         * Message content.
         */
        public Animation animation;
        /**
         * Animation caption.
         */
        public String caption;

        /**
         * Default constructor.
         */
        public MessageAnimation() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param animation Message content.
         * @param caption Animation caption.
         */
        public MessageAnimation(Animation animation, String caption) {
            this.animation = animation;
            this.caption = caption;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -49928664;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -49928664;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageAnimation").append(" {");
            shift += 2;
            appendLine(s, shift).append("animation = "); if (animation != null) { animation.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("caption = ").append(caption);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Audio message.
     */
    public static class MessageAudio extends MessageContent {
        /**
         * Message content.
         */
        public Audio audio;
        /**
         * Audio caption.
         */
        public String caption;
        /**
         * True, if the audio message was listened to.
         */
        public boolean isListened;

        /**
         * Default constructor.
         */
        public MessageAudio() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param audio Message content.
         * @param caption Audio caption.
         * @param isListened True, if the audio message was listened to.
         */
        public MessageAudio(Audio audio, String caption, boolean isListened) {
            this.audio = audio;
            this.caption = caption;
            this.isListened = isListened;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 226441982;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 226441982;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageAudio").append(" {");
            shift += 2;
            appendLine(s, shift).append("audio = "); if (audio != null) { audio.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("caption = ").append(caption);
            appendLine(s, shift).append("isListened = ").append(isListened);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Document message.
     */
    public static class MessageDocument extends MessageContent {
        /**
         * Message content.
         */
        public Document document;
        /**
         * Document caption.
         */
        public String caption;

        /**
         * Default constructor.
         */
        public MessageDocument() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param document Message content.
         * @param caption Document caption.
         */
        public MessageDocument(Document document, String caption) {
            this.document = document;
            this.caption = caption;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1630748077;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 1630748077;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageDocument").append(" {");
            shift += 2;
            appendLine(s, shift).append("document = "); if (document != null) { document.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("caption = ").append(caption);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Photo message.
     */
    public static class MessagePhoto extends MessageContent {
        /**
         * Message content.
         */
        public Photo photo;
        /**
         * Photo caption.
         */
        public String caption;

        /**
         * Default constructor.
         */
        public MessagePhoto() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param photo Message content.
         * @param caption Photo caption.
         */
        public MessagePhoto(Photo photo, String caption) {
            this.photo = photo;
            this.caption = caption;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1469704153;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 1469704153;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessagePhoto").append(" {");
            shift += 2;
            appendLine(s, shift).append("photo = "); if (photo != null) { photo.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("caption = ").append(caption);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Sticker message.
     */
    public static class MessageSticker extends MessageContent {
        /**
         * Message content.
         */
        public Sticker sticker;

        /**
         * Default constructor.
         */
        public MessageSticker() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param sticker Message content.
         */
        public MessageSticker(Sticker sticker) {
            this.sticker = sticker;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1779022878;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 1779022878;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageSticker").append(" {");
            shift += 2;
            appendLine(s, shift).append("sticker = "); if (sticker != null) { sticker.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Video message.
     */
    public static class MessageVideo extends MessageContent {
        /**
         * Message content.
         */
        public Video video;
        /**
         * Video caption.
         */
        public String caption;

        /**
         * Default constructor.
         */
        public MessageVideo() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param video Message content.
         * @param caption Video caption.
         */
        public MessageVideo(Video video, String caption) {
            this.video = video;
            this.caption = caption;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1267590961;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 1267590961;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageVideo").append(" {");
            shift += 2;
            appendLine(s, shift).append("video = "); if (video != null) { video.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("caption = ").append(caption);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Voice message.
     */
    public static class MessageVoice extends MessageContent {
        /**
         * Message content.
         */
        public Voice voice;
        /**
         * Voice caption.
         */
        public String caption;
        /**
         * True, if the voice message was listened to.
         */
        public boolean isListened;

        /**
         * Default constructor.
         */
        public MessageVoice() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param voice Message content.
         * @param caption Voice caption.
         * @param isListened True, if the voice message was listened to.
         */
        public MessageVoice(Voice voice, String caption, boolean isListened) {
            this.voice = voice;
            this.caption = caption;
            this.isListened = isListened;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -631462405;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -631462405;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageVoice").append(" {");
            shift += 2;
            appendLine(s, shift).append("voice = "); if (voice != null) { voice.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("caption = ").append(caption);
            appendLine(s, shift).append("isListened = ").append(isListened);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Message with location.
     */
    public static class MessageLocation extends MessageContent {
        /**
         * Message content.
         */
        public Location location;

        /**
         * Default constructor.
         */
        public MessageLocation() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param location Message content.
         */
        public MessageLocation(Location location) {
            this.location = location;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 161545583;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 161545583;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageLocation").append(" {");
            shift += 2;
            appendLine(s, shift).append("location = "); if (location != null) { location.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Message with information about venue.
     */
    public static class MessageVenue extends MessageContent {
        /**
         * Message content.
         */
        public Venue venue;

        /**
         * Default constructor.
         */
        public MessageVenue() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param venue Message content.
         */
        public MessageVenue(Venue venue) {
            this.venue = venue;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -2146492043;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -2146492043;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageVenue").append(" {");
            shift += 2;
            appendLine(s, shift).append("venue = "); if (venue != null) { venue.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * User contact message.
     */
    public static class MessageContact extends MessageContent {
        /**
         * Message content.
         */
        public Contact contact;

        /**
         * Default constructor.
         */
        public MessageContact() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param contact Message content.
         */
        public MessageContact(Contact contact) {
            this.contact = contact;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -512684966;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -512684966;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageContact").append(" {");
            shift += 2;
            appendLine(s, shift).append("contact = "); if (contact != null) { contact.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Message with a game.
     */
    public static class MessageGame extends MessageContent {
        /**
         * The game.
         */
        public Game game;

        /**
         * Default constructor.
         */
        public MessageGame() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param game The game.
         */
        public MessageGame(Game game) {
            this.game = game;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -69441162;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -69441162;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageGame").append(" {");
            shift += 2;
            appendLine(s, shift).append("game = "); if (game != null) { game.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * New group chat created.
     */
    public static class MessageGroupChatCreate extends MessageContent {
        /**
         * Title of created group chat.
         */
        public String title;
        /**
         * Parcticipants of created group chat.
         */
        public User[] members;

        /**
         * Default constructor.
         */
        public MessageGroupChatCreate() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param title Title of created group chat.
         * @param members Parcticipants of created group chat.
         */
        public MessageGroupChatCreate(String title, User[] members) {
            this.title = title;
            this.members = members;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 201696375;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * New channel chat created.
     */
    public static class MessageChannelChatCreate extends MessageContent {
        /**
         * Title of created channel chat.
         */
        public String title;

        /**
         * Default constructor.
         */
        public MessageChannelChatCreate() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param title Title of created channel chat.
         */
        public MessageChannelChatCreate(String title) {
            this.title = title;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 554984181;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Chat title changed.
     */
    public static class MessageChatChangeTitle extends MessageContent {
        /**
         * New chat title.
         */
        public String title;

        /**
         * Default constructor.
         */
        public MessageChatChangeTitle() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param title New chat title.
         */
        public MessageChatChangeTitle(String title) {
            this.title = title;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 748272449;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Chat photo changed.
     */
    public static class MessageChatChangePhoto extends MessageContent {
        /**
         * New chat photo.
         */
        public Photo photo;

        /**
         * Default constructor.
         */
        public MessageChatChangePhoto() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param photo New chat photo.
         */
        public MessageChatChangePhoto(Photo photo) {
            this.photo = photo;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 319630249;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 319630249;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageChatChangePhoto").append(" {");
            shift += 2;
            appendLine(s, shift).append("photo = "); if (photo != null) { photo.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Chat photo deleted.
     */
    public static class MessageChatDeletePhoto extends MessageContent {

        /**
         * Default constructor.
         */
        public MessageChatDeletePhoto() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -184374809;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Chat members added.
     */
    public static class MessageChatAddMembers extends MessageContent {
        /**
         * New chat member.
         */
        public User[] members;

        /**
         * Default constructor.
         */
        public MessageChatAddMembers() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param members New chat member.
         */
        public MessageChatAddMembers(User[] members) {
            this.members = members;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 423474768;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Chat member joined by invite link.
     */
    public static class MessageChatJoinByLink extends MessageContent {
        /**
         * Inviter of the member, i.e. creator of the chat.
         */
        public int inviterUserId;

        /**
         * Default constructor.
         */
        public MessageChatJoinByLink() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param inviterUserId Inviter of the member, i.e. creator of the chat.
         */
        public MessageChatJoinByLink(int inviterUserId) {
            this.inviterUserId = inviterUserId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1774397587;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Chat member deleted.
     */
    public static class MessageChatDeleteMember extends MessageContent {
        /**
         * Left or kicked chat member.
         */
        public User user;

        /**
         * Default constructor.
         */
        public MessageChatDeleteMember() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param user Left or kicked chat member.
         */
        public MessageChatDeleteMember(User user) {
            this.user = user;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 640851473;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 640851473;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("MessageChatDeleteMember").append(" {");
            shift += 2;
            appendLine(s, shift).append("user = "); if (user != null) { user.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Group chat is migrated to supergroup channel and deactivated.
     */
    public static class MessageChatMigrateTo extends MessageContent {
        /**
         * Identifier of the channel it is migrated to.
         */
        public int channelId;

        /**
         * Default constructor.
         */
        public MessageChatMigrateTo() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param channelId Identifier of the channel it is migrated to.
         */
        public MessageChatMigrateTo(int channelId) {
            this.channelId = channelId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -850335744;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Supergroup channel is created from group chat.
     */
    public static class MessageChatMigrateFrom extends MessageContent {
        /**
         * Title of created channel chat.
         */
        public String title;
        /**
         * Identifier of the group it is migrated from.
         */
        public int groupId;

        /**
         * Default constructor.
         */
        public MessageChatMigrateFrom() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param title Title of created channel chat.
         * @param groupId Identifier of the group it is migrated from.
         */
        public MessageChatMigrateFrom(String title, int groupId) {
            this.title = title;
            this.groupId = groupId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -2130688522;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Some message was pinned.
     */
    public static class MessagePinMessage extends MessageContent {
        /**
         * Identifier of the pinned message, can be identifier of the deleted message.
         */
        public int messageId;

        /**
         * Default constructor.
         */
        public MessagePinMessage() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param messageId Identifier of the pinned message, can be identifier of the deleted message.
         */
        public MessagePinMessage(int messageId) {
            this.messageId = messageId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 206180326;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * New high score was achieved in a game.
     */
    public static class MessageGameScore extends MessageContent {
        /**
         * Identifier of the message with the game, can be identifier of the deleted message.
         */
        public int gameMessageId;
        /**
         * Identifier of the game, may be different from the games presented in the message with the game.
         */
        public long gameId;
        /**
         * New score.
         */
        public int score;

        /**
         * Default constructor.
         */
        public MessageGameScore() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param gameMessageId Identifier of the message with the game, can be identifier of the deleted message.
         * @param gameId Identifier of the game, may be different from the games presented in the message with the game.
         * @param score New score.
         */
        public MessageGameScore(int gameMessageId, long gameId, int score) {
            this.gameMessageId = gameMessageId;
            this.gameId = gameId;
            this.score = score;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 535384543;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Unsupported message content.
     */
    public static class MessageUnsupported extends MessageContent {

        /**
         * Default constructor.
         */
        public MessageUnsupported() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1816726139;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * This class is an abstract base class.
     * Represent part of the message text which needs to be formatted in some unusual way.
     */
    public abstract static class MessageEntity extends TLObject {
    }

    /**
     * Mention of the user by his username.
     */
    public static class MessageEntityMention extends MessageEntity {
        /**
         * Offset of the entity in UTF-16 code points.
         */
        public int offset;
        /**
         * Length of the entity in UTF-16 code points.
         */
        public int length;

        /**
         * Default constructor.
         */
        public MessageEntityMention() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param offset Offset of the entity in UTF-16 code points.
         * @param length Length of the entity in UTF-16 code points.
         */
        public MessageEntityMention(int offset, int length) {
            this.offset = offset;
            this.length = length;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -100378723;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Hashtag beginning with #.
     */
    public static class MessageEntityHashtag extends MessageEntity {
        /**
         * Offset of the entity in UTF-16 code points.
         */
        public int offset;
        /**
         * Length of the entity in UTF-16 code points.
         */
        public int length;

        /**
         * Default constructor.
         */
        public MessageEntityHashtag() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param offset Offset of the entity in UTF-16 code points.
         * @param length Length of the entity in UTF-16 code points.
         */
        public MessageEntityHashtag(int offset, int length) {
            this.offset = offset;
            this.length = length;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1868782349;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Bot command beginning with /.
     */
    public static class MessageEntityBotCommand extends MessageEntity {
        /**
         * Offset of the entity in UTF-16 code points.
         */
        public int offset;
        /**
         * Length of the entity in UTF-16 code points.
         */
        public int length;

        /**
         * Default constructor.
         */
        public MessageEntityBotCommand() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param offset Offset of the entity in UTF-16 code points.
         * @param length Length of the entity in UTF-16 code points.
         */
        public MessageEntityBotCommand(int offset, int length) {
            this.offset = offset;
            this.length = length;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1827637959;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Url beginning with http.
     */
    public static class MessageEntityUrl extends MessageEntity {
        /**
         * Offset of the entity in UTF-16 code points.
         */
        public int offset;
        /**
         * Length of the entity in UTF-16 code points.
         */
        public int length;

        /**
         * Default constructor.
         */
        public MessageEntityUrl() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param offset Offset of the entity in UTF-16 code points.
         * @param length Length of the entity in UTF-16 code points.
         */
        public MessageEntityUrl(int offset, int length) {
            this.offset = offset;
            this.length = length;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1859134776;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Email.
     */
    public static class MessageEntityEmail extends MessageEntity {
        /**
         * Offset of the entity in UTF-16 code points.
         */
        public int offset;
        /**
         * Length of the entity in UTF-16 code points.
         */
        public int length;

        /**
         * Default constructor.
         */
        public MessageEntityEmail() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param offset Offset of the entity in UTF-16 code points.
         * @param length Length of the entity in UTF-16 code points.
         */
        public MessageEntityEmail(int offset, int length) {
            this.offset = offset;
            this.length = length;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1692693954;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Bold text.
     */
    public static class MessageEntityBold extends MessageEntity {
        /**
         * Offset of the entity in UTF-16 code points.
         */
        public int offset;
        /**
         * Length of the entity in UTF-16 code points.
         */
        public int length;

        /**
         * Default constructor.
         */
        public MessageEntityBold() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param offset Offset of the entity in UTF-16 code points.
         * @param length Length of the entity in UTF-16 code points.
         */
        public MessageEntityBold(int offset, int length) {
            this.offset = offset;
            this.length = length;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1117713463;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Italic text.
     */
    public static class MessageEntityItalic extends MessageEntity {
        /**
         * Offset of the entity in UTF-16 code points.
         */
        public int offset;
        /**
         * Length of the entity in UTF-16 code points.
         */
        public int length;

        /**
         * Default constructor.
         */
        public MessageEntityItalic() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param offset Offset of the entity in UTF-16 code points.
         * @param length Length of the entity in UTF-16 code points.
         */
        public MessageEntityItalic(int offset, int length) {
            this.offset = offset;
            this.length = length;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -2106619040;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Text needs to be formatted as inside of code HTML tag.
     */
    public static class MessageEntityCode extends MessageEntity {
        /**
         * Offset of the entity in UTF-16 code points.
         */
        public int offset;
        /**
         * Length of the entity in UTF-16 code points.
         */
        public int length;

        /**
         * Default constructor.
         */
        public MessageEntityCode() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param offset Offset of the entity in UTF-16 code points.
         * @param length Length of the entity in UTF-16 code points.
         */
        public MessageEntityCode(int offset, int length) {
            this.offset = offset;
            this.length = length;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 681706865;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Text needs to be formatted as inside of pre HTML tag.
     */
    public static class MessageEntityPre extends MessageEntity {
        /**
         * Offset of the entity in UTF-16 code points.
         */
        public int offset;
        /**
         * Length of the entity in UTF-16 code points.
         */
        public int length;

        /**
         * Default constructor.
         */
        public MessageEntityPre() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param offset Offset of the entity in UTF-16 code points.
         * @param length Length of the entity in UTF-16 code points.
         */
        public MessageEntityPre(int offset, int length) {
            this.offset = offset;
            this.length = length;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -953571395;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Text needs to be formatted as inside of pre and code HTML tags.
     */
    public static class MessageEntityPreCode extends MessageEntity {
        /**
         * Offset of the entity in UTF-16 code points.
         */
        public int offset;
        /**
         * Length of the entity in UTF-16 code points.
         */
        public int length;
        /**
         * Language of code as defined by sender.
         */
        public String language;

        /**
         * Default constructor.
         */
        public MessageEntityPreCode() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param offset Offset of the entity in UTF-16 code points.
         * @param length Length of the entity in UTF-16 code points.
         * @param language Language of code as defined by sender.
         */
        public MessageEntityPreCode(int offset, int length, String language) {
            this.offset = offset;
            this.length = length;
            this.language = language;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1764622354;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Text description showed instead of the url.
     */
    public static class MessageEntityTextUrl extends MessageEntity {
        /**
         * Offset of the entity in UTF-16 code points.
         */
        public int offset;
        /**
         * Length of the entity in UTF-16 code points.
         */
        public int length;
        /**
         * Url to be opened after link will be clicked.
         */
        public String url;

        /**
         * Default constructor.
         */
        public MessageEntityTextUrl() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param offset Offset of the entity in UTF-16 code points.
         * @param length Length of the entity in UTF-16 code points.
         * @param url Url to be opened after link will be clicked.
         */
        public MessageEntityTextUrl(int offset, int length, String url) {
            this.offset = offset;
            this.length = length;
            this.url = url;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1990644519;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Mention of the user by some text.
     */
    public static class MessageEntityMentionName extends MessageEntity {
        /**
         * Offset of the entity in UTF-16 code points.
         */
        public int offset;
        /**
         * Length of the entity in UTF-16 code points.
         */
        public int length;
        /**
         * Identifier of the mentioned user.
         */
        public int userId;

        /**
         * Default constructor.
         */
        public MessageEntityMentionName() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param offset Offset of the entity in UTF-16 code points.
         * @param length Length of the entity in UTF-16 code points.
         * @param userId Identifier of the mentioned user.
         */
        public MessageEntityMentionName(int offset, int length, int userId) {
            this.offset = offset;
            this.length = length;
            this.userId = userId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 892193368;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * This class is an abstract base class.
     * Contains information about initial sender of forwarded message.
     */
    public abstract static class MessageForwardInfo extends TLObject {
    }

    /**
     * Message is originally written by known user.
     */
    public static class MessageForwardedFromUser extends MessageForwardInfo {
        /**
         * Identifier of a user, who originally sent this message.
         */
        public int senderUserId;
        /**
         * Date when message was originally sent.
         */
        public int date;

        /**
         * Default constructor.
         */
        public MessageForwardedFromUser() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param senderUserId Identifier of a user, who originally sent this message.
         * @param date Date when message was originally sent.
         */
        public MessageForwardedFromUser(int senderUserId, int date) {
            this.senderUserId = senderUserId;
            this.date = date;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -721966663;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Message is orifinally a channel post.
     */
    public static class MessageForwardedPost extends MessageForwardInfo {
        /**
         * Identifier of a chat from which message is forwarded.
         */
        public long chatId;
        /**
         * User identifier of the original message sender, 0 if unknown.
         */
        public int senderUserId;
        /**
         * Date when message was originally sent.
         */
        public int date;
        /**
         * Message identifier of the message from which the message is forwarded, 0 if unknown.
         */
        public int messageId;

        /**
         * Default constructor.
         */
        public MessageForwardedPost() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Identifier of a chat from which message is forwarded.
         * @param senderUserId User identifier of the original message sender, 0 if unknown.
         * @param date Date when message was originally sent.
         * @param messageId Message identifier of the message from which the message is forwarded, 0 if unknown.
         */
        public MessageForwardedPost(long chatId, int senderUserId, int date, int messageId) {
            this.chatId = chatId;
            this.senderUserId = senderUserId;
            this.date = date;
            this.messageId = messageId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -163738338;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * This class is an abstract base class.
     * Contains information about sending state of the message.
     */
    public abstract static class MessageSendState extends TLObject {
    }

    /**
     * Message is incoming.
     */
    public static class MessageIsIncoming extends MessageSendState {

        /**
         * Default constructor.
         */
        public MessageIsIncoming() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -175134344;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Message is outgoing but is yet not delivered to the server.
     */
    public static class MessageIsBeingSent extends MessageSendState {

        /**
         * Default constructor.
         */
        public MessageIsBeingSent() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 302358579;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Message was synchronized with the server.
     */
    public static class MessageIsSuccessfullySent extends MessageSendState {

        /**
         * Default constructor.
         */
        public MessageIsSuccessfullySent() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -555213890;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Message is failed to send.
     */
    public static class MessageIsFailedToSend extends MessageSendState {

        /**
         * Default constructor.
         */
        public MessageIsFailedToSend() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -122502139;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Contains list of messages.
     */
    public static class Messages extends TLObject {
        /**
         * Approximate total count of found messages.
         */
        public int totalCount;
        /**
         * List of messages.
         */
        public Message[] messages;

        /**
         * Default constructor.
         */
        public Messages() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param totalCount Approximate total count of found messages.
         * @param messages List of messages.
         */
        public Messages(int totalCount, Message[] messages) {
            this.totalCount = totalCount;
            this.messages = messages;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1550441659;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Contains information about notification settings for chat or chats.
     */
    public static class NotificationSettings extends TLObject {
        /**
         * Time left before notifications will be unmuted.
         */
        public int muteFor;
        /**
         * Audio file name for notifications.
         */
        public String sound;
        /**
         * Display message text/media in notification.
         */
        public boolean showPreview;

        /**
         * Default constructor.
         */
        public NotificationSettings() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param muteFor Time left before notifications will be unmuted.
         * @param sound Audio file name for notifications.
         * @param showPreview Display message text/media in notification.
         */
        public NotificationSettings(int muteFor, String sound, boolean showPreview) {
            this.muteFor = muteFor;
            this.sound = sound;
            this.showPreview = showPreview;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1244785780;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * This class is an abstract base class.
     * Describes kinds of chat for which notification settings are applied.
     */
    public abstract static class NotificationSettingsScope extends TLObject {
    }

    /**
     * Notification settings applied to particular chat.
     */
    public static class NotificationSettingsForChat extends NotificationSettingsScope {
        /**
         * Chat identifier.
         */
        public long chatId;

        /**
         * Default constructor.
         */
        public NotificationSettingsForChat() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier.
         */
        public NotificationSettingsForChat(long chatId) {
            this.chatId = chatId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1920084409;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Notification settings applied to all private chats.
     */
    public static class NotificationSettingsForPrivateChats extends NotificationSettingsScope {

        /**
         * Default constructor.
         */
        public NotificationSettingsForPrivateChats() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 792026226;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Notification settings applied to all group chats.
     */
    public static class NotificationSettingsForGroupChats extends NotificationSettingsScope {

        /**
         * Default constructor.
         */
        public NotificationSettingsForGroupChats() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1019160145;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Notification settings applied to all chats.
     */
    public static class NotificationSettingsForAllChats extends NotificationSettingsScope {

        /**
         * Default constructor.
         */
        public NotificationSettingsForAllChats() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 2121050176;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Object of this type returns on successful function call for some functions.
     */
    public static class Ok extends TLObject {

        /**
         * Default constructor.
         */
        public Ok() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -722616727;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * This class is an abstract base class.
     * Represents value of an option.
     */
    public abstract static class OptionValue extends TLObject {
    }

    /**
     * Boolean option.
     */
    public static class OptionBoolean extends OptionValue {
        /**
         * Value of an option.
         */
        public boolean value;

        /**
         * Default constructor.
         */
        public OptionBoolean() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param value Value of an option.
         */
        public OptionBoolean(boolean value) {
            this.value = value;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 280624660;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Unknown option or option having default value.
     */
    public static class OptionEmpty extends OptionValue {

        /**
         * Default constructor.
         */
        public OptionEmpty() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1025799436;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Integer option.
     */
    public static class OptionInteger extends OptionValue {
        /**
         * Value of an option.
         */
        public int value;

        /**
         * Default constructor.
         */
        public OptionInteger() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param value Value of an option.
         */
        public OptionInteger(int value) {
            this.value = value;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1383938450;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * String option.
     */
    public static class OptionString extends OptionValue {
        /**
         * Value of an option.
         */
        public String value;

        /**
         * Default constructor.
         */
        public OptionString() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param value Value of an option.
         */
        public OptionString(String value) {
            this.value = value;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -841614037;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Describes photo.
     */
    public static class Photo extends TLObject {
        /**
         * Photo identifier, 0 for deleted photo.
         */
        public long id;
        /**
         * Available variants of photo of different sizes.
         */
        public PhotoSize[] sizes;

        /**
         * Default constructor.
         */
        public Photo() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Photo identifier, 0 for deleted photo.
         * @param sizes Available variants of photo of different sizes.
         */
        public Photo(long id, PhotoSize[] sizes) {
            this.id = id;
            this.sizes = sizes;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 811378213;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Photo description.
     */
    public static class PhotoSize extends TLObject {
        /**
         * Thumbnail type (see https://core.telegram.org/constructor/photoSize).
         */
        public String type;
        /**
         * Information about photo file.
         */
        public File photo;
        /**
         * Photo width.
         */
        public int width;
        /**
         * Photo height.
         */
        public int height;

        /**
         * Default constructor.
         */
        public PhotoSize() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param type Thumbnail type (see https://core.telegram.org/constructor/photoSize).
         * @param photo Information about photo file.
         * @param width Photo width.
         * @param height Photo height.
         */
        public PhotoSize(String type, File photo, int width, int height) {
            this.type = type;
            this.photo = photo;
            this.width = width;
            this.height = height;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -796190918;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -796190918;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("PhotoSize").append(" {");
            shift += 2;
            appendLine(s, shift).append("type = ").append(type);
            appendLine(s, shift).append("photo = "); if (photo != null) { photo.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("width = ").append(width);
            appendLine(s, shift).append("height = ").append(height);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Describes user profile photo.
     */
    public static class ProfilePhoto extends TLObject {
        /**
         * Photo identifier, 0 for empty photo. Can be used to find photo in list of userProfilePhotos.
         */
        public long id;
        /**
         * Small (160x160) user profile photo.
         */
        public File small;
        /**
         * Big (640x640) user profile photo.
         */
        public File big;

        /**
         * Default constructor.
         */
        public ProfilePhoto() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Photo identifier, 0 for empty photo. Can be used to find photo in list of userProfilePhotos.
         * @param small Small (160x160) user profile photo.
         * @param big Big (640x640) user profile photo.
         */
        public ProfilePhoto(long id, File small, File big) {
            this.id = id;
            this.small = small;
            this.big = big;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1954106867;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -1954106867;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ProfilePhoto").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("small = "); if (small != null) { small.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("big = "); if (big != null) { big.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * This class is an abstract base class.
     * Contains description of custom keyboard and actions with it for fast reply to bots.
     */
    public abstract static class ReplyMarkup extends TLObject {
    }

    /**
     * Instruct clients to hide keyboard after receiving this message. This kind of keyboard can't be received. Instead UpdateChatReplyMarkup with messageId == 0 will be send.
     */
    public static class ReplyMarkupHideKeyboard extends ReplyMarkup {
        /**
         * Keyboard is showed automatically only for mentioned users or replied to chat user, for incoming messages it is true if and only if keyboard needs to be automatically showed to current user.
         */
        public boolean personal;

        /**
         * Default constructor.
         */
        public ReplyMarkupHideKeyboard() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param personal Keyboard is showed automatically only for mentioned users or replied to chat user, for incoming messages it is true if and only if keyboard needs to be automatically showed to current user.
         */
        public ReplyMarkupHideKeyboard(boolean personal) {
            this.personal = personal;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1614435429;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Instruct clients to force reply to this message.
     */
    public static class ReplyMarkupForceReply extends ReplyMarkup {
        /**
         * Keyboard is showed automatically only for mentioned users or replied to chat user, for incoming messages it is true if and only if keyboard needs to be automatically showed to current user.
         */
        public boolean personal;

        /**
         * Default constructor.
         */
        public ReplyMarkupForceReply() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param personal Keyboard is showed automatically only for mentioned users or replied to chat user, for incoming messages it is true if and only if keyboard needs to be automatically showed to current user.
         */
        public ReplyMarkupForceReply(boolean personal) {
            this.personal = personal;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1880611604;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Contains custom keyboard layout for fast reply to bot.
     */
    public static class ReplyMarkupShowKeyboard extends ReplyMarkup {
        /**
         * List of rows of bot keyboard buttons.
         */
        public KeyboardButton[][] rows;
        /**
         * Do clients need to resize keyboard.
         */
        public boolean resizeKeyboard;
        /**
         * Do clients need to hide keyboard after use.
         */
        public boolean oneTime;
        /**
         * Keyboard is showed automatically only for mentioned users or replied to chat user, for incoming messages it is true if and only if keyboard needs to be automatically showed to current user.
         */
        public boolean personal;

        /**
         * Default constructor.
         */
        public ReplyMarkupShowKeyboard() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param rows List of rows of bot keyboard buttons.
         * @param resizeKeyboard Do clients need to resize keyboard.
         * @param oneTime Do clients need to hide keyboard after use.
         * @param personal Keyboard is showed automatically only for mentioned users or replied to chat user, for incoming messages it is true if and only if keyboard needs to be automatically showed to current user.
         */
        public ReplyMarkupShowKeyboard(KeyboardButton[][] rows, boolean resizeKeyboard, boolean oneTime, boolean personal) {
            this.rows = rows;
            this.resizeKeyboard = resizeKeyboard;
            this.oneTime = oneTime;
            this.personal = personal;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -840982893;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Contains inline keyboard layout.
     */
    public static class ReplyMarkupInlineKeyboard extends ReplyMarkup {
        /**
         * List of rows of inline keyboard buttons.
         */
        public InlineKeyboardButton[][] rows;

        /**
         * Default constructor.
         */
        public ReplyMarkupInlineKeyboard() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param rows List of rows of inline keyboard buttons.
         */
        public ReplyMarkupInlineKeyboard(InlineKeyboardButton[][] rows) {
            this.rows = rows;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -619317658;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * This class is an abstract base class.
     * Represents filter for content of searched messages.
     */
    public abstract static class SearchMessagesFilter extends TLObject {
    }

    /**
     * Return all found messages.
     */
    public static class SearchMessagesFilterEmpty extends SearchMessagesFilter {

        /**
         * Default constructor.
         */
        public SearchMessagesFilterEmpty() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -869395657;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Return only animation messages.
     */
    public static class SearchMessagesFilterAnimation extends SearchMessagesFilter {

        /**
         * Default constructor.
         */
        public SearchMessagesFilterAnimation() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -155713339;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Return only audio messages.
     */
    public static class SearchMessagesFilterAudio extends SearchMessagesFilter {

        /**
         * Default constructor.
         */
        public SearchMessagesFilterAudio() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 867505275;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Return only document messages.
     */
    public static class SearchMessagesFilterDocument extends SearchMessagesFilter {

        /**
         * Default constructor.
         */
        public SearchMessagesFilterDocument() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1526331215;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Return only photo messages.
     */
    public static class SearchMessagesFilterPhoto extends SearchMessagesFilter {

        /**
         * Default constructor.
         */
        public SearchMessagesFilterPhoto() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 925932293;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Return only video messages.
     */
    public static class SearchMessagesFilterVideo extends SearchMessagesFilter {

        /**
         * Default constructor.
         */
        public SearchMessagesFilterVideo() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 115538222;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Return only voice messages.
     */
    public static class SearchMessagesFilterVoice extends SearchMessagesFilter {

        /**
         * Default constructor.
         */
        public SearchMessagesFilterVoice() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1123427595;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Return only photo and video messages.
     */
    public static class SearchMessagesFilterPhotoAndVideo extends SearchMessagesFilter {

        /**
         * Default constructor.
         */
        public SearchMessagesFilterPhotoAndVideo() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1352130963;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Return only messages containing url.
     */
    public static class SearchMessagesFilterUrl extends SearchMessagesFilter {

        /**
         * Default constructor.
         */
        public SearchMessagesFilterUrl() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1828724341;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Return only messages containing chat photos.
     */
    public static class SearchMessagesFilterChatPhoto extends SearchMessagesFilter {

        /**
         * Default constructor.
         */
        public SearchMessagesFilterChatPhoto() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1247751329;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Represents a secret chat.
     */
    public static class SecretChat extends TLObject {
        /**
         * Secret chat identifier.
         */
        public int id;
        /**
         * Identifier of the interlocutor.
         */
        public int userId;
        /**
         * State of the secret chat, 0 - yet not created, 1 - active, 2 - closed.
         */
        public int state;
        /**
         * Current message ttl setting for the chat.
         */
        public int ttl;

        /**
         * Default constructor.
         */
        public SecretChat() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Secret chat identifier.
         * @param userId Identifier of the interlocutor.
         * @param state State of the secret chat, 0 - yet not created, 1 - active, 2 - closed.
         * @param ttl Current message ttl setting for the chat.
         */
        public SecretChat(int id, int userId, int state, int ttl) {
            this.id = id;
            this.userId = userId;
            this.state = state;
            this.ttl = ttl;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1575069994;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Gives full information about a secret chat.
     */
    public static class SecretChatFull extends TLObject {
        /**
         * General info about hte secret chat.
         */
        public SecretChat secretChat;
        /**
         * Hash of the current used key for comparison with the hash of the interlocutor key.
         */
        public String keyHash;

        /**
         * Default constructor.
         */
        public SecretChatFull() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param secretChat General info about hte secret chat.
         * @param keyHash Hash of the current used key for comparison with the hash of the interlocutor key.
         */
        public SecretChatFull(SecretChat secretChat, String keyHash) {
            this.secretChat = secretChat;
            this.keyHash = keyHash;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 230450236;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 230450236;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SecretChatFull").append(" {");
            shift += 2;
            appendLine(s, shift).append("secretChat = "); if (secretChat != null) { secretChat.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("keyHash = ").append(keyHash);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * This class is an abstract base class.
     * Notifies about activity in chat.
     */
    public abstract static class SendMessageAction extends TLObject {
    }

    /**
     * User typing message.
     */
    public static class SendMessageTypingAction extends SendMessageAction {

        /**
         * Default constructor.
         */
        public SendMessageTypingAction() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 381645902;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * User cancels typing.
     */
    public static class SendMessageCancelAction extends SendMessageAction {

        /**
         * Default constructor.
         */
        public SendMessageCancelAction() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -44119819;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * User records a video.
     */
    public static class SendMessageRecordVideoAction extends SendMessageAction {

        /**
         * Default constructor.
         */
        public SendMessageRecordVideoAction() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1584933265;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * User uploads a video.
     */
    public static class SendMessageUploadVideoAction extends SendMessageAction {
        /**
         * Upload progress in percents.
         */
        public int progress;

        /**
         * Default constructor.
         */
        public SendMessageUploadVideoAction() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param progress Upload progress in percents.
         */
        public SendMessageUploadVideoAction(int progress) {
            this.progress = progress;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -378127636;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * User records voice message.
     */
    public static class SendMessageRecordVoiceAction extends SendMessageAction {

        /**
         * Default constructor.
         */
        public SendMessageRecordVoiceAction() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1470755762;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * User uploads voice message.
     */
    public static class SendMessageUploadVoiceAction extends SendMessageAction {
        /**
         * Upload progress in percents.
         */
        public int progress;

        /**
         * Default constructor.
         */
        public SendMessageUploadVoiceAction() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param progress Upload progress in percents.
         */
        public SendMessageUploadVoiceAction(int progress) {
            this.progress = progress;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 64055712;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * User uploads a photo.
     */
    public static class SendMessageUploadPhotoAction extends SendMessageAction {
        /**
         * Upload progress in percents.
         */
        public int progress;

        /**
         * Default constructor.
         */
        public SendMessageUploadPhotoAction() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param progress Upload progress in percents.
         */
        public SendMessageUploadPhotoAction(int progress) {
            this.progress = progress;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -774682074;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * User uploads a document.
     */
    public static class SendMessageUploadDocumentAction extends SendMessageAction {
        /**
         * Upload progress in percents.
         */
        public int progress;

        /**
         * Default constructor.
         */
        public SendMessageUploadDocumentAction() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param progress Upload progress in percents.
         */
        public SendMessageUploadDocumentAction(int progress) {
            this.progress = progress;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1441998364;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * User sends geolocation.
     */
    public static class SendMessageGeoLocationAction extends SendMessageAction {

        /**
         * Default constructor.
         */
        public SendMessageGeoLocationAction() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 393186209;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * User chooses contact to send.
     */
    public static class SendMessageChooseContactAction extends SendMessageAction {

        /**
         * Default constructor.
         */
        public SendMessageChooseContactAction() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1653390447;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * User starts to play a game.
     */
    public static class SendMessageStartPlayGameAction extends SendMessageAction {

        /**
         * Default constructor.
         */
        public SendMessageStartPlayGameAction() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -2099820430;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Describes sticker.
     */
    public static class Sticker extends TLObject {
        /**
         * Identifier of sticker set to which the sticker belongs or 0 if none.
         */
        public long setId;
        /**
         * Sticker width as defined by sender.
         */
        public int width;
        /**
         * Sticker height as defined by sender.
         */
        public int height;
        /**
         * Emoji corresponding to the sticker.
         */
        public String emoji;
        /**
         * Rating of the sticker, non-negative. The rating is higher for often used stickers.
         */
        public double rating;
        /**
         * Sticker thumb in webp or jpeg format, nullable.
         */
        public @Nullable PhotoSize thumb;
        /**
         * File with sticker.
         */
        public File sticker;

        /**
         * Default constructor.
         */
        public Sticker() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param setId Identifier of sticker set to which the sticker belongs or 0 if none.
         * @param width Sticker width as defined by sender.
         * @param height Sticker height as defined by sender.
         * @param emoji Emoji corresponding to the sticker.
         * @param rating Rating of the sticker, non-negative. The rating is higher for often used stickers.
         * @param thumb Sticker thumb in webp or jpeg format, nullable.
         * @param sticker File with sticker.
         */
        public Sticker(long setId, int width, int height, String emoji, double rating, PhotoSize thumb, File sticker) {
            this.setId = setId;
            this.width = width;
            this.height = height;
            this.emoji = emoji;
            this.rating = rating;
            this.thumb = thumb;
            this.sticker = sticker;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -319806232;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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
            appendLine(s, shift).append("thumb = "); if (thumb != null) { thumb.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("sticker = "); if (sticker != null) { sticker.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Represents sticker set.
     */
    public static class StickerSet extends TLObject {
        /**
         * Sticker set identifier.
         */
        public long id;
        /**
         * Title of the sticker set.
         */
        public String title;
        /**
         * Name of the sticker set.
         */
        public String name;
        /**
         * Rating of the sticker, non-negative. The rating is higher for often used stickers.
         */
        public double rating;
        /**
         * True if sticker set is installed by logged in user.
         */
        public boolean isInstalled;
        /**
         * True if sticker set is enabled.
         */
        public boolean isEnabled;
        /**
         * True if sticker set is official and can't be uninstalled.
         */
        public boolean isOfficial;
        /**
         * List of stickers in this set.
         */
        public Sticker[] stickers;

        /**
         * Default constructor.
         */
        public StickerSet() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Sticker set identifier.
         * @param title Title of the sticker set.
         * @param name Name of the sticker set.
         * @param rating Rating of the sticker, non-negative. The rating is higher for often used stickers.
         * @param isInstalled True if sticker set is installed by logged in user.
         * @param isEnabled True if sticker set is enabled.
         * @param isOfficial True if sticker set is official and can't be uninstalled.
         * @param stickers List of stickers in this set.
         */
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

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1942998252;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Represents short information about sticker set.
     */
    public static class StickerSetInfo extends TLObject {
        /**
         * Sticker set identifier.
         */
        public long id;
        /**
         * Title of the sticker set.
         */
        public String title;
        /**
         * Name of the sticker set.
         */
        public String name;
        /**
         * Rating of the sticker, non-negative. The rating is higher for often used stickers.
         */
        public double rating;
        /**
         * True if sticker set is installed by logged in user.
         */
        public boolean isInstalled;
        /**
         * True if sticker set is enabled.
         */
        public boolean isEnabled;
        /**
         * True if sticker set is official and can't be uninstalled.
         */
        public boolean isOfficial;
        /**
         * Number of stickers in the set.
         */
        public int size;

        /**
         * Default constructor.
         */
        public StickerSetInfo() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Sticker set identifier.
         * @param title Title of the sticker set.
         * @param name Name of the sticker set.
         * @param rating Rating of the sticker, non-negative. The rating is higher for often used stickers.
         * @param isInstalled True if sticker set is installed by logged in user.
         * @param isEnabled True if sticker set is enabled.
         * @param isOfficial True if sticker set is official and can't be uninstalled.
         * @param size Number of stickers in the set.
         */
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

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1268445223;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Represents list of sticker sets.
     */
    public static class StickerSets extends TLObject {
        /**
         * List of sticker sets.
         */
        public StickerSetInfo[] sets;

        /**
         * Default constructor.
         */
        public StickerSets() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param sets List of sticker sets.
         */
        public StickerSets(StickerSetInfo[] sets) {
            this.sets = sets;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1141691090;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Represents list of stickers.
     */
    public static class Stickers extends TLObject {
        /**
         * Stickers.
         */
        public Sticker[] stickers;

        /**
         * Default constructor.
         */
        public Stickers() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param stickers Stickers.
         */
        public Stickers(Sticker[] stickers) {
            this.stickers = stickers;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1974859260;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * This class is an abstract base class.
     * Describes a way text should be parsed for MessageEntities, by default text is treated as is.
     */
    public abstract static class TextParseMode extends TLObject {
    }

    /**
     * Text should be parsed in markdown-style way.
     */
    public static class TextParseModeMarkdown extends TextParseMode {

        /**
         * Default constructor.
         */
        public TextParseModeMarkdown() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 969225580;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Text should be parsed in the HTML-style way.
     */
    public static class TextParseModeHTML extends TextParseMode {

        /**
         * Default constructor.
         */
        public TextParseModeHTML() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1660208627;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * This class is an abstract base class.
     * Contains notifications about data changes.
     */
    public abstract static class Update extends TLObject {
    }

    /**
     * New message received, maybe outcoming message sent from other device.
     */
    public static class UpdateNewMessage extends Update {
        /**
         * New message.
         */
        public Message message;
        /**
         * If true, notification about the message should be disabled.
         */
        public boolean disableNotification;

        /**
         * Default constructor.
         */
        public UpdateNewMessage() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param message New message.
         * @param disableNotification If true, notification about the message should be disabled.
         */
        public UpdateNewMessage(Message message, boolean disableNotification) {
            this.message = message;
            this.disableNotification = disableNotification;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1098732806;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 1098732806;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateNewMessage").append(" {");
            shift += 2;
            appendLine(s, shift).append("message = "); if (message != null) { message.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("disableNotification = ").append(disableNotification);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Message is successfully sent.
     */
    public static class UpdateMessageSendSucceeded extends Update {
        /**
         * Information about sent message. Usually only message identifier, date and content are changed, but some other fields may also change.
         */
        public Message message;
        /**
         * Previous temporary message identifier.
         */
        public int oldMessageId;

        /**
         * Default constructor.
         */
        public UpdateMessageSendSucceeded() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param message Information about sent message. Usually only message identifier, date and content are changed, but some other fields may also change.
         * @param oldMessageId Previous temporary message identifier.
         */
        public UpdateMessageSendSucceeded(Message message, int oldMessageId) {
            this.message = message;
            this.oldMessageId = oldMessageId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -272606842;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -272606842;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateMessageSendSucceeded").append(" {");
            shift += 2;
            appendLine(s, shift).append("message = "); if (message != null) { message.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("oldMessageId = ").append(oldMessageId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Message fails to send. Be aware that some being sent messages can be irrecoverably deleted and updateDeleteMessages will come instead of this update (or doesn't come at all if deletion was done by call to deleteMessages).
     */
    public static class UpdateMessageSendFailed extends Update {
        /**
         * Chat identifier.
         */
        public long chatId;
        /**
         * Message identifier.
         */
        public int messageId;
        /**
         * Error code.
         */
        public int errorCode;
        /**
         * Error message.
         */
        public String errorMessage;

        /**
         * Default constructor.
         */
        public UpdateMessageSendFailed() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier.
         * @param messageId Message identifier.
         * @param errorCode Error code.
         * @param errorMessage Error message.
         */
        public UpdateMessageSendFailed(long chatId, int messageId, int errorCode, String errorMessage) {
            this.chatId = chatId;
            this.messageId = messageId;
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1711539093;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Sent message gets new content.
     */
    public static class UpdateMessageContent extends Update {
        /**
         * Chat identifier.
         */
        public long chatId;
        /**
         * Message identifier.
         */
        public int messageId;
        /**
         * New message content.
         */
        public MessageContent newContent;

        /**
         * Default constructor.
         */
        public UpdateMessageContent() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier.
         * @param messageId Message identifier.
         * @param newContent New message content.
         */
        public UpdateMessageContent(long chatId, int messageId, MessageContent newContent) {
            this.chatId = chatId;
            this.messageId = messageId;
            this.newContent = newContent;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 561472729;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 561472729;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateMessageContent").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("messageId = ").append(messageId);
            appendLine(s, shift).append("newContent = "); if (newContent != null) { newContent.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Message was edited. Changes in the message content will come in a separate updateMessageContent.
     */
    public static class UpdateMessageEdited extends Update {
        /**
         * Chat identifier.
         */
        public long chatId;
        /**
         * Message identifier.
         */
        public int messageId;
        /**
         * Date the message was edited, unix time.
         */
        public int editDate;
        /**
         * New message reply markup, nullable.
         */
        public @Nullable ReplyMarkup replyMarkup;

        /**
         * Default constructor.
         */
        public UpdateMessageEdited() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier.
         * @param messageId Message identifier.
         * @param editDate Date the message was edited, unix time.
         * @param replyMarkup New message reply markup, nullable.
         */
        public UpdateMessageEdited(long chatId, int messageId, int editDate, ReplyMarkup replyMarkup) {
            this.chatId = chatId;
            this.messageId = messageId;
            this.editDate = editDate;
            this.replyMarkup = replyMarkup;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1312681878;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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
            appendLine(s, shift).append("replyMarkup = "); if (replyMarkup != null) { replyMarkup.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * View count of the message has changed.
     */
    public static class UpdateMessageViews extends Update {
        /**
         * Chat identifier.
         */
        public long chatId;
        /**
         * Message identifier.
         */
        public int messageId;
        /**
         * New value of view count.
         */
        public int views;

        /**
         * Default constructor.
         */
        public UpdateMessageViews() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier.
         * @param messageId Message identifier.
         * @param views New value of view count.
         */
        public UpdateMessageViews(long chatId, int messageId, int views) {
            this.chatId = chatId;
            this.messageId = messageId;
            this.views = views;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1812594738;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Some date about chat has been changed.
     */
    public static class UpdateChat extends Update {
        /**
         * New data about the chat.
         */
        public Chat chat;

        /**
         * Default constructor.
         */
        public UpdateChat() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chat New data about the chat.
         */
        public UpdateChat(Chat chat) {
            this.chat = chat;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1253621217;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -1253621217;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateChat").append(" {");
            shift += 2;
            appendLine(s, shift).append("chat = "); if (chat != null) { chat.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Top message of the chat has changed.
     */
    public static class UpdateChatTopMessage extends Update {
        /**
         * Chat identifier.
         */
        public long chatId;
        /**
         * New top message of the chat, nullable.
         */
        public @Nullable Message topMessage;

        /**
         * Default constructor.
         */
        public UpdateChatTopMessage() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier.
         * @param topMessage New top message of the chat, nullable.
         */
        public UpdateChatTopMessage(long chatId, Message topMessage) {
            this.chatId = chatId;
            this.topMessage = topMessage;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 281918326;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 281918326;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateChatTopMessage").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("topMessage = "); if (topMessage != null) { topMessage.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Order of the chat in the chat list has changed.
     */
    public static class UpdateChatOrder extends Update {
        /**
         * Chat identifier.
         */
        public long chatId;
        /**
         * New value of the order.
         */
        public long order;

        /**
         * Default constructor.
         */
        public UpdateChatOrder() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier.
         * @param order New value of the order.
         */
        public UpdateChatOrder(long chatId, long order) {
            this.chatId = chatId;
            this.order = order;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1977527814;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Title of chat was changed.
     */
    public static class UpdateChatTitle extends Update {
        /**
         * Chat identifier.
         */
        public long chatId;
        /**
         * New chat title.
         */
        public String title;

        /**
         * Default constructor.
         */
        public UpdateChatTitle() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier.
         * @param title New chat title.
         */
        public UpdateChatTitle(long chatId, String title) {
            this.chatId = chatId;
            this.title = title;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1931125386;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Chat photo was changed.
     */
    public static class UpdateChatPhoto extends Update {
        /**
         * Chat identifier.
         */
        public long chatId;
        /**
         * New chat photo, nullable.
         */
        public @Nullable ChatPhoto photo;

        /**
         * Default constructor.
         */
        public UpdateChatPhoto() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier.
         * @param photo New chat photo, nullable.
         */
        public UpdateChatPhoto(long chatId, ChatPhoto photo) {
            this.chatId = chatId;
            this.photo = photo;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 556185369;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 556185369;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateChatPhoto").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("photo = "); if (photo != null) { photo.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * User reads incoming messages from some other device.
     */
    public static class UpdateChatReadInbox extends Update {
        /**
         * Chat identifier.
         */
        public long chatId;
        /**
         * Identifier of last read incoming message.
         */
        public int lastReadInboxMessageId;
        /**
         * Number of unread messages left in chat.
         */
        public int unreadCount;

        /**
         * Default constructor.
         */
        public UpdateChatReadInbox() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier.
         * @param lastReadInboxMessageId Identifier of last read incoming message.
         * @param unreadCount Number of unread messages left in chat.
         */
        public UpdateChatReadInbox(long chatId, int lastReadInboxMessageId, int unreadCount) {
            this.chatId = chatId;
            this.lastReadInboxMessageId = lastReadInboxMessageId;
            this.unreadCount = unreadCount;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -58810942;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Some outcoming messages was read.
     */
    public static class UpdateChatReadOutbox extends Update {
        /**
         * Chat identifier.
         */
        public long chatId;
        /**
         * Identifier of last read outgoing message.
         */
        public int lastReadOutboxMessageId;

        /**
         * Default constructor.
         */
        public UpdateChatReadOutbox() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier.
         * @param lastReadOutboxMessageId Identifier of last read outgoing message.
         */
        public UpdateChatReadOutbox(long chatId, int lastReadOutboxMessageId) {
            this.chatId = chatId;
            this.lastReadOutboxMessageId = lastReadOutboxMessageId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 877103058;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Default chat reply markup has changed. It can happen because new message with reply markup has come or old reply markup was hidden by user.
     */
    public static class UpdateChatReplyMarkup extends Update {
        /**
         * Chat identifier.
         */
        public long chatId;
        /**
         * Identifier of the message from which reply markup need to be used or 0 if there is no default custom reply markup in the chat.
         */
        public int replyMarkupMessageId;

        /**
         * Default constructor.
         */
        public UpdateChatReplyMarkup() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier.
         * @param replyMarkupMessageId Identifier of the message from which reply markup need to be used or 0 if there is no default custom reply markup in the chat.
         */
        public UpdateChatReplyMarkup(long chatId, int replyMarkupMessageId) {
            this.chatId = chatId;
            this.replyMarkupMessageId = replyMarkupMessageId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 301018472;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Chat draft has changed.
     */
    public static class UpdateChatDraftMessage extends Update {
        /**
         * Chat identifier.
         */
        public long chatId;
        /**
         * New chat draftMessage, nullable.
         */
        public @Nullable DraftMessage draftMessage;

        /**
         * Default constructor.
         */
        public UpdateChatDraftMessage() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier.
         * @param draftMessage New chat draftMessage, nullable.
         */
        public UpdateChatDraftMessage(long chatId, DraftMessage draftMessage) {
            this.chatId = chatId;
            this.draftMessage = draftMessage;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 602781138;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 602781138;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateChatDraftMessage").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("draftMessage = "); if (draftMessage != null) { draftMessage.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Notification settings for some chats was updated.
     */
    public static class UpdateNotificationSettings extends Update {
        /**
         * Kinds of chats for which notification settings was updated.
         */
        public NotificationSettingsScope scope;
        /**
         * New notification settings.
         */
        public NotificationSettings notificationSettings;

        /**
         * Default constructor.
         */
        public UpdateNotificationSettings() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param scope Kinds of chats for which notification settings was updated.
         * @param notificationSettings New notification settings.
         */
        public UpdateNotificationSettings(NotificationSettingsScope scope, NotificationSettings notificationSettings) {
            this.scope = scope;
            this.notificationSettings = notificationSettings;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1767306883;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -1767306883;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateNotificationSettings").append(" {");
            shift += 2;
            appendLine(s, shift).append("scope = "); if (scope != null) { scope.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("notificationSettings = "); if (notificationSettings != null) { notificationSettings.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Some messages was deleted.
     */
    public static class UpdateDeleteMessages extends Update {
        /**
         * Chat identifier.
         */
        public long chatId;
        /**
         * Identifiers of deleted message.
         */
        public int[] messageIds;

        /**
         * Default constructor.
         */
        public UpdateDeleteMessages() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier.
         * @param messageIds Identifiers of deleted message.
         */
        public UpdateDeleteMessages(long chatId, int[] messageIds) {
            this.chatId = chatId;
            this.messageIds = messageIds;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 129908480;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Some chat activity.
     */
    public static class UpdateUserAction extends Update {
        /**
         * Chat identifier.
         */
        public long chatId;
        /**
         * Identifier of user doing action.
         */
        public int userId;
        /**
         * Action description.
         */
        public SendMessageAction action;

        /**
         * Default constructor.
         */
        public UpdateUserAction() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier.
         * @param userId Identifier of user doing action.
         * @param action Action description.
         */
        public UpdateUserAction(long chatId, int userId, SendMessageAction action) {
            this.chatId = chatId;
            this.userId = userId;
            this.action = action;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 223420164;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 223420164;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateUserAction").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("userId = ").append(userId);
            appendLine(s, shift).append("action = "); if (action != null) { action.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * User went online/offline.
     */
    public static class UpdateUserStatus extends Update {
        /**
         * User identifier.
         */
        public int userId;
        /**
         * New user status.
         */
        public UserStatus status;

        /**
         * Default constructor.
         */
        public UpdateUserStatus() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param userId User identifier.
         * @param status New user status.
         */
        public UpdateUserStatus(int userId, UserStatus status) {
            this.userId = userId;
            this.status = status;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 469489699;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 469489699;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateUserStatus").append(" {");
            shift += 2;
            appendLine(s, shift).append("userId = ").append(userId);
            appendLine(s, shift).append("status = "); if (status != null) { status.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Some data about a user has been changed.
     */
    public static class UpdateUser extends Update {
        /**
         * New data about the user.
         */
        public User user;

        /**
         * Default constructor.
         */
        public UpdateUser() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param user New data about the user.
         */
        public UpdateUser(User user) {
            this.user = user;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1183394041;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 1183394041;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateUser").append(" {");
            shift += 2;
            appendLine(s, shift).append("user = "); if (user != null) { user.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Some data about a group has been changed.
     */
    public static class UpdateGroup extends Update {
        /**
         * New data about the group.
         */
        public Group group;

        /**
         * Default constructor.
         */
        public UpdateGroup() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param group New data about the group.
         */
        public UpdateGroup(Group group) {
            this.group = group;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -301840552;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -301840552;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateGroup").append(" {");
            shift += 2;
            appendLine(s, shift).append("group = "); if (group != null) { group.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Some data about a channel has been changed.
     */
    public static class UpdateChannel extends Update {
        /**
         * New data about the channel.
         */
        public Channel channel;

        /**
         * Default constructor.
         */
        public UpdateChannel() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param channel New data about the channel.
         */
        public UpdateChannel(Channel channel) {
            this.channel = channel;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 492671396;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 492671396;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateChannel").append(" {");
            shift += 2;
            appendLine(s, shift).append("channel = "); if (channel != null) { channel.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Some data about a secret chat has been changed.
     */
    public static class UpdateSecretChat extends Update {
        /**
         * New data about the secret chat.
         */
        public SecretChat secretChat;

        /**
         * Default constructor.
         */
        public UpdateSecretChat() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param secretChat New data about the secret chat.
         */
        public UpdateSecretChat(SecretChat secretChat) {
            this.secretChat = secretChat;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1666903253;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -1666903253;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateSecretChat").append(" {");
            shift += 2;
            appendLine(s, shift).append("secretChat = "); if (secretChat != null) { secretChat.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Some data from channelFull has been changed.
     */
    public static class UpdateChannelFull extends Update {
        /**
         * New full information about the channel.
         */
        public ChannelFull channelFull;

        /**
         * Default constructor.
         */
        public UpdateChannelFull() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param channelFull New full information about the channel.
         */
        public UpdateChannelFull(ChannelFull channelFull) {
            this.channelFull = channelFull;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1140192938;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 1140192938;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateChannelFull").append(" {");
            shift += 2;
            appendLine(s, shift).append("channelFull = "); if (channelFull != null) { channelFull.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * User blocked/unblocked.
     */
    public static class UpdateUserBlocked extends Update {
        /**
         * User identifier.
         */
        public int userId;
        /**
         * Is user blacklisted by current user.
         */
        public boolean isBlocked;

        /**
         * Default constructor.
         */
        public UpdateUserBlocked() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param userId User identifier.
         * @param isBlocked Is user blacklisted by current user.
         */
        public UpdateUserBlocked(int userId, boolean isBlocked) {
            this.userId = userId;
            this.isBlocked = isBlocked;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1341545325;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * User has logged in from another device.
     */
    public static class UpdateNewAuthorization extends Update {
        /**
         * Date of logging in.
         */
        public int date;
        /**
         * Device used to log in.
         */
        public String device;
        /**
         * Location from where user logged in.
         */
        public String location;

        /**
         * Default constructor.
         */
        public UpdateNewAuthorization() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param date Date of logging in.
         * @param device Device used to log in.
         * @param location Location from where user logged in.
         */
        public UpdateNewAuthorization(int date, String device, String location) {
            this.date = date;
            this.device = device;
            this.location = location;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -176559980;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * File is partly downloaded/uploaded.
     */
    public static class UpdateFileProgress extends Update {
        /**
         * File identifier.
         */
        public int fileId;
        /**
         * Total file size (0 means unknown).
         */
        public int size;
        /**
         * Bytes downloaded/uploaded. Negative number means that download/upload has failed and was terminated.
         */
        public int ready;

        /**
         * Default constructor.
         */
        public UpdateFileProgress() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param fileId File identifier.
         * @param size Total file size (0 means unknown).
         * @param ready Bytes downloaded/uploaded. Negative number means that download/upload has failed and was terminated.
         */
        public UpdateFileProgress(int fileId, int size, int ready) {
            this.fileId = fileId;
            this.size = size;
            this.ready = ready;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1340921194;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * File is downloaded/uploaded.
     */
    public static class UpdateFile extends Update {
        /**
         * Synced file.
         */
        public File file;

        /**
         * Default constructor.
         */
        public UpdateFile() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param file Synced file.
         */
        public UpdateFile(File file) {
            this.file = file;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 114132831;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 114132831;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateFile").append(" {");
            shift += 2;
            appendLine(s, shift).append("file = "); if (file != null) { file.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Some option changed its value.
     */
    public static class UpdateOption extends Update {
        /**
         * Option name.
         */
        public String name;
        /**
         * New option value.
         */
        public OptionValue value;

        /**
         * Default constructor.
         */
        public UpdateOption() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param name Option name.
         * @param value New option value.
         */
        public UpdateOption(String name, OptionValue value) {
            this.name = name;
            this.value = value;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 900822020;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 900822020;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateOption").append(" {");
            shift += 2;
            appendLine(s, shift).append("name = ").append(name);
            appendLine(s, shift).append("value = "); if (value != null) { value.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Some stickers was updated. Need to drop stickers cache if have some.
     */
    public static class UpdateStickers extends Update {

        /**
         * Default constructor.
         */
        public UpdateStickers() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -456211753;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * List of saved animations was updated. Need to drop saved animations cache if have some.
     */
    public static class UpdateSavedAnimations extends Update {

        /**
         * Default constructor.
         */
        public UpdateSavedAnimations() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1517884047;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Bots only. New incoming inline query.
     */
    public static class UpdateNewInlineQuery extends Update {
        /**
         * Unique query identifier.
         */
        public long id;
        /**
         * Identifier of the user who sent the query.
         */
        public int senderUserId;
        /**
         * User location, provided by the client, nullable.
         */
        public @Nullable Location userLocation;
        /**
         * Text of the query.
         */
        public String query;
        /**
         * Offset of the first entry to return.
         */
        public String offset;

        /**
         * Default constructor.
         */
        public UpdateNewInlineQuery() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Unique query identifier.
         * @param senderUserId Identifier of the user who sent the query.
         * @param userLocation User location, provided by the client, nullable.
         * @param query Text of the query.
         * @param offset Offset of the first entry to return.
         */
        public UpdateNewInlineQuery(long id, int senderUserId, Location userLocation, String query, String offset) {
            this.id = id;
            this.senderUserId = senderUserId;
            this.userLocation = userLocation;
            this.query = query;
            this.offset = offset;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -820108313;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -820108313;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateNewInlineQuery").append(" {");
            shift += 2;
            appendLine(s, shift).append("id = ").append(id);
            appendLine(s, shift).append("senderUserId = ").append(senderUserId);
            appendLine(s, shift).append("userLocation = "); if (userLocation != null) { userLocation.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("query = ").append(query);
            appendLine(s, shift).append("offset = ").append(offset);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Bots only. User has chosen a result of the inline query.
     */
    public static class UpdateNewChosenInlineResult extends Update {
        /**
         * Identifier of the user who sent the query.
         */
        public int senderUserId;
        /**
         * User location, provided by the client, nullable.
         */
        public @Nullable Location userLocation;
        /**
         * Text of the query.
         */
        public String query;
        /**
         * Identifier of the chosen result.
         */
        public String resultId;
        /**
         * Identifier of the sent inline message, if known.
         */
        public String inlineMessageId;

        /**
         * Default constructor.
         */
        public UpdateNewChosenInlineResult() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param senderUserId Identifier of the user who sent the query.
         * @param userLocation User location, provided by the client, nullable.
         * @param query Text of the query.
         * @param resultId Identifier of the chosen result.
         * @param inlineMessageId Identifier of the sent inline message, if known.
         */
        public UpdateNewChosenInlineResult(int senderUserId, Location userLocation, String query, String resultId, String inlineMessageId) {
            this.senderUserId = senderUserId;
            this.userLocation = userLocation;
            this.query = query;
            this.resultId = resultId;
            this.inlineMessageId = inlineMessageId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -867559680;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -867559680;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UpdateNewChosenInlineResult").append(" {");
            shift += 2;
            appendLine(s, shift).append("senderUserId = ").append(senderUserId);
            appendLine(s, shift).append("userLocation = "); if (userLocation != null) { userLocation.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("query = ").append(query);
            appendLine(s, shift).append("resultId = ").append(resultId);
            appendLine(s, shift).append("inlineMessageId = ").append(inlineMessageId);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Bots only. New incoming callback query.
     */
    public static class UpdateNewCallbackQuery extends Update {
        /**
         * Unique query identifier.
         */
        public long id;
        /**
         * Identifier of the user who sent the query.
         */
        public int senderUserId;
        /**
         * Identifier of the chat, in which the query was sent.
         */
        public long chatId;
        /**
         * Identifier of the message, from which the query is originated.
         */
        public int messageId;
        /**
         * Identifier, uniquely corresponding to the chat a message was sent to.
         */
        public long chatInstance;
        /**
         * Query payload.
         */
        public CallbackQueryPayload payload;

        /**
         * Default constructor.
         */
        public UpdateNewCallbackQuery() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Unique query identifier.
         * @param senderUserId Identifier of the user who sent the query.
         * @param chatId Identifier of the chat, in which the query was sent.
         * @param messageId Identifier of the message, from which the query is originated.
         * @param chatInstance Identifier, uniquely corresponding to the chat a message was sent to.
         * @param payload Query payload.
         */
        public UpdateNewCallbackQuery(long id, int senderUserId, long chatId, int messageId, long chatInstance, CallbackQueryPayload payload) {
            this.id = id;
            this.senderUserId = senderUserId;
            this.chatId = chatId;
            this.messageId = messageId;
            this.chatInstance = chatInstance;
            this.payload = payload;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -469662806;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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
            appendLine(s, shift).append("payload = "); if (payload != null) { payload.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Bots only. New incoming callback query from message sent via bot.
     */
    public static class UpdateNewInlineCallbackQuery extends Update {
        /**
         * Unique query identifier.
         */
        public long id;
        /**
         * Identifier of the user who sent the query.
         */
        public int senderUserId;
        /**
         * Identifier of the inline message, from which the query is originated.
         */
        public String inlineMessageId;
        /**
         * Identifier, uniquely corresponding to the chat a message was sent to.
         */
        public long chatInstance;
        /**
         * Query payload.
         */
        public CallbackQueryPayload payload;

        /**
         * Default constructor.
         */
        public UpdateNewInlineCallbackQuery() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id Unique query identifier.
         * @param senderUserId Identifier of the user who sent the query.
         * @param inlineMessageId Identifier of the inline message, from which the query is originated.
         * @param chatInstance Identifier, uniquely corresponding to the chat a message was sent to.
         * @param payload Query payload.
         */
        public UpdateNewInlineCallbackQuery(long id, int senderUserId, String inlineMessageId, long chatInstance, CallbackQueryPayload payload) {
            this.id = id;
            this.senderUserId = senderUserId;
            this.inlineMessageId = inlineMessageId;
            this.chatInstance = chatInstance;
            this.payload = payload;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1564879277;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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
            appendLine(s, shift).append("payload = "); if (payload != null) { payload.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Represents user.
     */
    public static class User extends TLObject {
        /**
         * User identifier.
         */
        public int id;
        /**
         * User first name.
         */
        public String firstName;
        /**
         * User last name.
         */
        public String lastName;
        /**
         * User username.
         */
        public String username;
        /**
         * User's phone number.
         */
        public String phoneNumber;
        /**
         * User's online status.
         */
        public UserStatus status;
        /**
         * User profile photo, nullable.
         */
        public @Nullable ProfilePhoto profilePhoto;
        /**
         * Relationships from me to other user.
         */
        public LinkState myLink;
        /**
         * Relationships from other user to me.
         */
        public LinkState foreignLink;
        /**
         * True, if user is verified.
         */
        public boolean isVerified;
        /**
         * If non-empty, contains the reason, why access to this user must be restricted. Format of the string is &quot;{type}: {description}&quot;. {type} contains type of the restriction and at least one of the suffixes &quot;-all&quot;, &quot;-ios&quot;, &quot;-android&quot;, &quot;-wp&quot;, which describes platforms on which access should be restricted. For example, &quot;terms-ios-android&quot;. {description} contains human-readable description of the restriction, which can be showed to the user.
         */
        public String restrictionReason;
        /**
         * If false, the user is inaccessible and the only known information about it is inside this class. It can't be passed to any method except GetUser. Currently it can be false only for inaccessible authors of the channel posts.
         */
        public boolean haveAccess;
        /**
         * Type of the user.
         */
        public UserType type;

        /**
         * Default constructor.
         */
        public User() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param id User identifier.
         * @param firstName User first name.
         * @param lastName User last name.
         * @param username User username.
         * @param phoneNumber User's phone number.
         * @param status User's online status.
         * @param profilePhoto User profile photo, nullable.
         * @param myLink Relationships from me to other user.
         * @param foreignLink Relationships from other user to me.
         * @param isVerified True, if user is verified.
         * @param restrictionReason If non-empty, contains the reason, why access to this user must be restricted. Format of the string is &quot;{type}: {description}&quot;. {type} contains type of the restriction and at least one of the suffixes &quot;-all&quot;, &quot;-ios&quot;, &quot;-android&quot;, &quot;-wp&quot;, which describes platforms on which access should be restricted. For example, &quot;terms-ios-android&quot;. {description} contains human-readable description of the restriction, which can be showed to the user.
         * @param haveAccess If false, the user is inaccessible and the only known information about it is inside this class. It can't be passed to any method except GetUser. Currently it can be false only for inaccessible authors of the channel posts.
         * @param type Type of the user.
         */
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

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -551088334;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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
            appendLine(s, shift).append("status = "); if (status != null) { status.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("profilePhoto = "); if (profilePhoto != null) { profilePhoto.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("myLink = "); if (myLink != null) { myLink.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("foreignLink = "); if (foreignLink != null) { foreignLink.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("isVerified = ").append(isVerified);
            appendLine(s, shift).append("restrictionReason = ").append(restrictionReason);
            appendLine(s, shift).append("haveAccess = ").append(haveAccess);
            appendLine(s, shift).append("type = "); if (type != null) { type.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Gives full information about a user (except full list of profile photos).
     */
    public static class UserFull extends TLObject {
        /**
         * General info about the user.
         */
        public User user;
        /**
         * Is user blacklisted by current user.
         */
        public boolean isBlocked;
        /**
         * Short user description.
         */
        public String about;
        /**
         * Information about bot if user is a bot, nullable.
         */
        public @Nullable BotInfo botInfo;

        /**
         * Default constructor.
         */
        public UserFull() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param user General info about the user.
         * @param isBlocked Is user blacklisted by current user.
         * @param about Short user description.
         * @param botInfo Information about bot if user is a bot, nullable.
         */
        public UserFull(User user, boolean isBlocked, String about, BotInfo botInfo) {
            this.user = user;
            this.isBlocked = isBlocked;
            this.about = about;
            this.botInfo = botInfo;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -539848475;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -539848475;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("UserFull").append(" {");
            shift += 2;
            appendLine(s, shift).append("user = "); if (user != null) { user.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("isBlocked = ").append(isBlocked);
            appendLine(s, shift).append("about = ").append(about);
            appendLine(s, shift).append("botInfo = "); if (botInfo != null) { botInfo.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Contains part of the list of user photos.
     */
    public static class UserProfilePhotos extends TLObject {
        /**
         * Total number of user profile photos.
         */
        public int totalCount;
        /**
         * List of photos.
         */
        public Photo[] photos;

        /**
         * Default constructor.
         */
        public UserProfilePhotos() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param totalCount Total number of user profile photos.
         * @param photos List of photos.
         */
        public UserProfilePhotos(int totalCount, Photo[] photos) {
            this.totalCount = totalCount;
            this.photos = photos;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1425984405;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * This class is an abstract base class.
     * Describes last time user was online.
     */
    public abstract static class UserStatus extends TLObject {
    }

    /**
     * User status was newer changed.
     */
    public static class UserStatusEmpty extends UserStatus {

        /**
         * Default constructor.
         */
        public UserStatusEmpty() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 164646985;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * User is online.
     */
    public static class UserStatusOnline extends UserStatus {
        /**
         * Unix time when user's online status will expire.
         */
        public int expires;

        /**
         * Default constructor.
         */
        public UserStatusOnline() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param expires Unix time when user's online status will expire.
         */
        public UserStatusOnline(int expires) {
            this.expires = expires;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -306628279;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * User is offline.
     */
    public static class UserStatusOffline extends UserStatus {
        /**
         * Unix time user was online last time.
         */
        public int wasOnline;

        /**
         * Default constructor.
         */
        public UserStatusOffline() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param wasOnline Unix time user was online last time.
         */
        public UserStatusOffline(int wasOnline) {
            this.wasOnline = wasOnline;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 9203775;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * User was online recently.
     */
    public static class UserStatusRecently extends UserStatus {

        /**
         * Default constructor.
         */
        public UserStatusRecently() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -496024847;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * User is offline, but was online last week.
     */
    public static class UserStatusLastWeek extends UserStatus {

        /**
         * Default constructor.
         */
        public UserStatusLastWeek() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 129960444;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * User is offline, but was online last month.
     */
    public static class UserStatusLastMonth extends UserStatus {

        /**
         * Default constructor.
         */
        public UserStatusLastMonth() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 2011940674;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * This class is an abstract base class.
     * Allows to distinguish different kinds of users: general users, deleted users and bots.
     */
    public abstract static class UserType extends TLObject {
    }

    /**
     * General user.
     */
    public static class UserTypeGeneral extends UserType {

        /**
         * Default constructor.
         */
        public UserTypeGeneral() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -955149573;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Deleted user or deleted bot. There is no any information about it except userId. None of active action can be performed with deleted user.
     */
    public static class UserTypeDeleted extends UserType {

        /**
         * Default constructor.
         */
        public UserTypeDeleted() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1807729372;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Bot (see https://core.telegram.org/bots).
     */
    public static class UserTypeBot extends UserType {
        /**
         * If true, bot can be invited to group and supergroup chats.
         */
        public boolean canJoinGroupChats;
        /**
         * If true, bot can read all group or supergroup chat messages, not only addressed to him. In private chats bot always can read all messages.
         */
        public boolean canReadAllGroupChatMessages;
        /**
         * True, if bot supports inline queries.
         */
        public boolean isInline;
        /**
         * Placeholder for inline query.
         */
        public String inlineQueryPlaceholder;
        /**
         * If true, user location should be sent with every inline query to this bot.
         */
        public boolean needLocation;

        /**
         * Default constructor.
         */
        public UserTypeBot() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param canJoinGroupChats If true, bot can be invited to group and supergroup chats.
         * @param canReadAllGroupChatMessages If true, bot can read all group or supergroup chat messages, not only addressed to him. In private chats bot always can read all messages.
         * @param isInline True, if bot supports inline queries.
         * @param inlineQueryPlaceholder Placeholder for inline query.
         * @param needLocation If true, user location should be sent with every inline query to this bot.
         */
        public UserTypeBot(boolean canJoinGroupChats, boolean canReadAllGroupChatMessages, boolean isInline, String inlineQueryPlaceholder, boolean needLocation) {
            this.canJoinGroupChats = canJoinGroupChats;
            this.canReadAllGroupChatMessages = canReadAllGroupChatMessages;
            this.isInline = isInline;
            this.inlineQueryPlaceholder = inlineQueryPlaceholder;
            this.needLocation = needLocation;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -610455780;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Currently there is no any information about the user except userId. It can happens very-very rarely. None of active action can be performed with unknown user.
     */
    public static class UserTypeUnknown extends UserType {

        /**
         * Default constructor.
         */
        public UserTypeUnknown() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -724541123;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Contains list of users.
     */
    public static class Users extends TLObject {
        /**
         * Approximate total count of found users.
         */
        public int totalCount;
        /**
         * List of users.
         */
        public User[] users;

        /**
         * Default constructor.
         */
        public Users() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param totalCount Approximate total count of found users.
         * @param users List of users.
         */
        public Users(int totalCount, User[] users) {
            this.totalCount = totalCount;
            this.users = users;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 469005719;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Describes venue.
     */
    public static class Venue extends TLObject {
        /**
         * Venue location as defined by sender.
         */
        public Location location;
        /**
         * Venue name as defined by sender.
         */
        public String title;
        /**
         * Venue address as defined by sender.
         */
        public String address;
        /**
         * Provider of venue database as defined by sender. Only &quot;foursquare&quot; need to be supported currently.
         */
        public String provider;
        /**
         * Identifier of the venue in provider database as defined by sender.
         */
        public String id;

        /**
         * Default constructor.
         */
        public Venue() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param location Venue location as defined by sender.
         * @param title Venue name as defined by sender.
         * @param address Venue address as defined by sender.
         * @param provider Provider of venue database as defined by sender. Only &quot;foursquare&quot; need to be supported currently.
         * @param id Identifier of the venue in provider database as defined by sender.
         */
        public Venue(Location location, String title, String address, String provider, String id) {
            this.location = location;
            this.title = title;
            this.address = address;
            this.provider = provider;
            this.id = id;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -674687867;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -674687867;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("Venue").append(" {");
            shift += 2;
            appendLine(s, shift).append("location = "); if (location != null) { location.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("title = ").append(title);
            appendLine(s, shift).append("address = ").append(address);
            appendLine(s, shift).append("provider = ").append(provider);
            appendLine(s, shift).append("id = ").append(id);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Describes video file.
     */
    public static class Video extends TLObject {
        /**
         * Duration of the video in seconds as defined by sender.
         */
        public int duration;
        /**
         * Video width as defined by sender.
         */
        public int width;
        /**
         * Video height as defined by sender.
         */
        public int height;
        /**
         * Original name of a file as defined by sender.
         */
        public String fileName;
        /**
         * MIME type of a file as defined by sender.
         */
        public String mimeType;
        /**
         * Video thumb as defined by sender, nullable.
         */
        public @Nullable PhotoSize thumb;
        /**
         * File with the video.
         */
        public File video;

        /**
         * Default constructor.
         */
        public Video() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param duration Duration of the video in seconds as defined by sender.
         * @param width Video width as defined by sender.
         * @param height Video height as defined by sender.
         * @param fileName Original name of a file as defined by sender.
         * @param mimeType MIME type of a file as defined by sender.
         * @param thumb Video thumb as defined by sender, nullable.
         * @param video File with the video.
         */
        public Video(int duration, int width, int height, String fileName, String mimeType, PhotoSize thumb, File video) {
            this.duration = duration;
            this.width = width;
            this.height = height;
            this.fileName = fileName;
            this.mimeType = mimeType;
            this.thumb = thumb;
            this.video = video;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 839000879;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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
            appendLine(s, shift).append("thumb = "); if (thumb != null) { thumb.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("video = "); if (video != null) { video.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Describes voice record. Voice must be encoded with Opus codec and must be stored inside Ogg container.
     */
    public static class Voice extends TLObject {
        /**
         * Duration of the voice record in seconds as defined by sender.
         */
        public int duration;
        /**
         * Waveform representation of the voice in 5-bit format.
         */
        public byte[] waveform;
        /**
         * MIME type of a file as defined by sender.
         */
        public String mimeType;
        /**
         * File with the voice record.
         */
        public File voice;

        /**
         * Default constructor.
         */
        public Voice() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param duration Duration of the voice record in seconds as defined by sender.
         * @param waveform Waveform representation of the voice in 5-bit format.
         * @param mimeType MIME type of a file as defined by sender.
         * @param voice File with the voice record.
         */
        public Voice(int duration, byte[] waveform, String mimeType, File voice) {
            this.duration = duration;
            this.waveform = waveform;
            this.mimeType = mimeType;
            this.voice = voice;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -348096919;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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
            appendLine(s, shift).append("voice = "); if (voice != null) { voice.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Contains information about one wallpaper.
     */
    public static class Wallpaper extends TLObject {
        /**
         * Available variants of wallpaper of different sizes. These photos can be only downloaded and can't be sent in a message.
         */
        public PhotoSize[] sizes;
        /**
         * Main color of wallpaper in RGB24, should be treated as background color if no photos are specified.
         */
        public int color;

        /**
         * Default constructor.
         */
        public Wallpaper() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param sizes Available variants of wallpaper of different sizes. These photos can be only downloaded and can't be sent in a message.
         * @param color Main color of wallpaper in RGB24, should be treated as background color if no photos are specified.
         */
        public Wallpaper(PhotoSize[] sizes, int color) {
            this.sizes = sizes;
            this.color = color;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -803346388;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Contains list of wallpapers.
     */
    public static class Wallpapers extends TLObject {
        /**
         * List of wallpapers.
         */
        public Wallpaper[] wallpapers;

        /**
         * Default constructor.
         */
        public Wallpapers() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param wallpapers List of wallpapers.
         */
        public Wallpapers(Wallpaper[] wallpapers) {
            this.wallpapers = wallpapers;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 877926640;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Describes web page preview.
     */
    public static class WebPage extends TLObject {
        /**
         * Original URL of link.
         */
        public String url;
        /**
         * URL to display.
         */
        public String displayUrl;
        /**
         * Type of web page: article, photo, audio, video, document, profile, app or something other.
         */
        public String type;
        /**
         * Short name of the site (i.e. Google Docs or App Store).
         */
        public String siteName;
        /**
         * Title of the content.
         */
        public String title;
        /**
         * Description of the content.
         */
        public String description;
        /**
         * Image representing the content, nullable.
         */
        public @Nullable Photo photo;
        /**
         * Url to show embedded preview.
         */
        public String embedUrl;
        /**
         * MIME type of embedded preview, i.e. text/html or video/mp4.
         */
        public String embedType;
        /**
         * Width of embedded preview.
         */
        public int embedWidth;
        /**
         * Height of embedded preview.
         */
        public int embedHeight;
        /**
         * Duration of the content.
         */
        public int duration;
        /**
         * Author of the content.
         */
        public String author;
        /**
         * Preview as Animation if available, nullable.
         */
        public @Nullable Animation animation;
        /**
         * Preview as Audio if available, nullable.
         */
        public @Nullable Audio audio;
        /**
         * Preview as Document if available (currently only for small pdf files and zip archives), nullable.
         */
        public @Nullable Document document;
        /**
         * Preview as Sticker for small .webp files if available, nullable.
         */
        public @Nullable Sticker sticker;
        /**
         * Preview as Video if available, nullable.
         */
        public @Nullable Video video;
        /**
         * Preview as Voice if available, nullable.
         */
        public @Nullable Voice voice;

        /**
         * Default constructor.
         */
        public WebPage() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param url Original URL of link.
         * @param displayUrl URL to display.
         * @param type Type of web page: article, photo, audio, video, document, profile, app or something other.
         * @param siteName Short name of the site (i.e. Google Docs or App Store).
         * @param title Title of the content.
         * @param description Description of the content.
         * @param photo Image representing the content, nullable.
         * @param embedUrl Url to show embedded preview.
         * @param embedType MIME type of embedded preview, i.e. text/html or video/mp4.
         * @param embedWidth Width of embedded preview.
         * @param embedHeight Height of embedded preview.
         * @param duration Duration of the content.
         * @param author Author of the content.
         * @param animation Preview as Animation if available, nullable.
         * @param audio Preview as Audio if available, nullable.
         * @param document Preview as Document if available (currently only for small pdf files and zip archives), nullable.
         * @param sticker Preview as Sticker for small .webp files if available, nullable.
         * @param video Preview as Video if available, nullable.
         * @param voice Preview as Voice if available, nullable.
         */
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

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -268383594;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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
            appendLine(s, shift).append("photo = "); if (photo != null) { photo.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("embedUrl = ").append(embedUrl);
            appendLine(s, shift).append("embedType = ").append(embedType);
            appendLine(s, shift).append("embedWidth = ").append(embedWidth);
            appendLine(s, shift).append("embedHeight = ").append(embedHeight);
            appendLine(s, shift).append("duration = ").append(duration);
            appendLine(s, shift).append("author = ").append(author);
            appendLine(s, shift).append("animation = "); if (animation != null) { animation.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("audio = "); if (audio != null) { audio.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("document = "); if (document != null) { document.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("sticker = "); if (sticker != null) { sticker.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("video = "); if (video != null) { video.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("voice = "); if (voice != null) { voice.toStringBuilder(shift, s); } else { s.append("null"); }
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

    /**
     * Adds new member to chat. Members can't be added to private or secret chats. Member will not be added until chat state will be synchronized with the server. Member will not be added if application is killed before it can send request to the server.
     */
    public static class AddChatMember extends TLFunction {
        /**
         * Chat identifier.
         */
        public long chatId;
        /**
         * Identifier of the user to add.
         */
        public int userId;
        /**
         * Number of previous messages from chat to forward to new member, ignored for channel chats.
         */
        public int forwardLimit;

        /**
         * Default constructor.
         */
        public AddChatMember() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier.
         * @param userId Identifier of the user to add.
         * @param forwardLimit Number of previous messages from chat to forward to new member, ignored for channel chats.
         */
        public AddChatMember(long chatId, int userId, int forwardLimit) {
            this.chatId = chatId;
            this.userId = userId;
            this.forwardLimit = forwardLimit;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1252828345;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Adds many new members to the chat. Currently, available only for channels. Can't be used to join the channel. Member will not be added until chat state will be synchronized with the server. Member will not be added if application is killed before it can send request to the server.
     */
    public static class AddChatMembers extends TLFunction {
        /**
         * Chat identifier.
         */
        public long chatId;
        /**
         * Identifiers of the users to add.
         */
        public int[] userIds;

        /**
         * Default constructor.
         */
        public AddChatMembers() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier.
         * @param userIds Identifiers of the users to add.
         */
        public AddChatMembers(long chatId, int[] userIds) {
            this.chatId = chatId;
            this.userIds = userIds;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 693690362;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Adds chat to the list of recently found chats. The chat is added to the beginning of the list. If the chat is already in the list, at first it is removed from the list.
     */
    public static class AddRecentlyFoundChat extends TLFunction {
        /**
         * Identifier of the chat to add.
         */
        public long chatId;

        /**
         * Default constructor.
         */
        public AddRecentlyFoundChat() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Identifier of the chat to add.
         */
        public AddRecentlyFoundChat(long chatId) {
            this.chatId = chatId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 2127862625;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Manually adds new animation to the list of saved animations. New animation is added to the beginning of the list. If the animation is already in the list, at first it is removed from the list. Only video animations with MIME type &quot;video/mp4&quot; can be added to the list.
     */
    public static class AddSavedAnimation extends TLFunction {
        /**
         * Animation file to add. Only known to server animations (i. e. successfully sent via message) can be added to the list.
         */
        public InputFile animation;

        /**
         * Default constructor.
         */
        public AddSavedAnimation() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param animation Animation file to add. Only known to server animations (i. e. successfully sent via message) can be added to the list.
         */
        public AddSavedAnimation(InputFile animation) {
            this.animation = animation;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1538525088;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -1538525088;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("AddSavedAnimation").append(" {");
            shift += 2;
            appendLine(s, shift).append("animation = "); if (animation != null) { animation.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Sets result of the callback query. Bots only.
     */
    public static class AnswerCallbackQuery extends TLFunction {
        /**
         * Identifier of the callback query.
         */
        public long callbackQueryId;
        /**
         * Text of the answer.
         */
        public String text;
        /**
         * If true, an alert should be shown to the user instead of a toast.
         */
        public boolean showAlert;
        /**
         * Url to be opened.
         */
        public String url;

        /**
         * Default constructor.
         */
        public AnswerCallbackQuery() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param callbackQueryId Identifier of the callback query.
         * @param text Text of the answer.
         * @param showAlert If true, an alert should be shown to the user instead of a toast.
         * @param url Url to be opened.
         */
        public AnswerCallbackQuery(long callbackQueryId, String text, boolean showAlert, String url) {
            this.callbackQueryId = callbackQueryId;
            this.text = text;
            this.showAlert = showAlert;
            this.url = url;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 373886252;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Sets result of the inline query. Bots only.
     */
    public static class AnswerInlineQuery extends TLFunction {
        /**
         * Identifier of the inline query.
         */
        public long inlineQueryId;
        /**
         * Does result of the query can be cached only for specified user.
         */
        public boolean isPersonal;
        /**
         * Results of the query.
         */
        public InputInlineQueryResult[] results;
        /**
         * Allowed time to cache results of the query, defaults to 300.
         */
        public int cacheTime;
        /**
         * Offset for the next inline query, pass empty string if there is no more results.
         */
        public String nextOffset;
        /**
         * If non-empty, this text should be shown on the button, which opens private chat with the bot and sends bot start message with parameter switchPmParameter.
         */
        public String switchPmText;
        /**
         * Parameter for the bot start message.
         */
        public String switchPmParameter;

        /**
         * Default constructor.
         */
        public AnswerInlineQuery() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param inlineQueryId Identifier of the inline query.
         * @param isPersonal Does result of the query can be cached only for specified user.
         * @param results Results of the query.
         * @param cacheTime Allowed time to cache results of the query, defaults to 300.
         * @param nextOffset Offset for the next inline query, pass empty string if there is no more results.
         * @param switchPmText If non-empty, this text should be shown on the button, which opens private chat with the bot and sends bot start message with parameter switchPmParameter.
         * @param switchPmParameter Parameter for the bot start message.
         */
        public AnswerInlineQuery(long inlineQueryId, boolean isPersonal, InputInlineQueryResult[] results, int cacheTime, String nextOffset, String switchPmText, String switchPmParameter) {
            this.inlineQueryId = inlineQueryId;
            this.isPersonal = isPersonal;
            this.results = results;
            this.cacheTime = cacheTime;
            this.nextOffset = nextOffset;
            this.switchPmText = switchPmText;
            this.switchPmParameter = switchPmParameter;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1529641829;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Adds user to black list.
     */
    public static class BlockUser extends TLFunction {
        /**
         * User identifier.
         */
        public int userId;

        /**
         * Default constructor.
         */
        public BlockUser() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param userId User identifier.
         */
        public BlockUser(int userId) {
            this.userId = userId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -200788058;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Stops file downloading. If file already downloaded do nothing.
     */
    public static class CancelDownloadFile extends TLFunction {
        /**
         * Identifier of file to cancel download.
         */
        public int fileId;

        /**
         * Default constructor.
         */
        public CancelDownloadFile() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param fileId Identifier of file to cancel download.
         */
        public CancelDownloadFile(int fileId) {
            this.fileId = fileId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 18489866;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Changes about information of logged in user.
     */
    public static class ChangeAbout extends TLFunction {
        /**
         * New value of userFull.about, 0-255 characters.
         */
        public String about;

        /**
         * Default constructor.
         */
        public ChangeAbout() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param about New value of userFull.about, 0-255 characters.
         */
        public ChangeAbout(String about) {
            this.about = about;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1364842334;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Changes period of inactivity, after which the account of currently logged in user will be automatically deleted.
     */
    public static class ChangeAccountTtl extends TLFunction {
        /**
         * New account TTL.
         */
        public AccountTtl ttl;

        /**
         * Default constructor.
         */
        public ChangeAccountTtl() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param ttl New account TTL.
         */
        public ChangeAccountTtl(AccountTtl ttl) {
            this.ttl = ttl;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1079644217;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -1079644217;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChangeAccountTtl").append(" {");
            shift += 2;
            appendLine(s, shift).append("ttl = "); if (ttl != null) { ttl.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Changes information about the channel. Needs creator privileges in the broadcast channel or editor privileges in the supergroup channel.
     */
    public static class ChangeChannelAbout extends TLFunction {
        /**
         * Identifier of the channel.
         */
        public int channelId;
        /**
         * New value of about, 0-255 characters.
         */
        public String about;

        /**
         * Default constructor.
         */
        public ChangeChannelAbout() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param channelId Identifier of the channel.
         * @param about New value of about, 0-255 characters.
         */
        public ChangeChannelAbout(int channelId, String about) {
            this.channelId = channelId;
            this.about = about;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1985280026;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Changes username of the channel. Needs creator privileges in the channel.
     */
    public static class ChangeChannelUsername extends TLFunction {
        /**
         * Identifier of the channel.
         */
        public int channelId;
        /**
         * New value of username. Use empty string to remove username.
         */
        public String username;

        /**
         * Default constructor.
         */
        public ChangeChannelUsername() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param channelId Identifier of the channel.
         * @param username New value of username. Use empty string to remove username.
         */
        public ChangeChannelUsername(int channelId, String username) {
            this.channelId = channelId;
            this.username = username;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1681482857;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Changes chat draft message.
     */
    public static class ChangeChatDraftMessage extends TLFunction {
        /**
         * Chat identifier.
         */
        public long chatId;
        /**
         * New draft message, nullable.
         */
        public @Nullable DraftMessage draftMessage;

        /**
         * Default constructor.
         */
        public ChangeChatDraftMessage() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier.
         * @param draftMessage New draft message, nullable.
         */
        public ChangeChatDraftMessage(long chatId, DraftMessage draftMessage) {
            this.chatId = chatId;
            this.draftMessage = draftMessage;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1572219043;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 1572219043;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChangeChatDraftMessage").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("draftMessage = "); if (draftMessage != null) { draftMessage.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Changes status of the chat member, need appropriate privileges. In channel chats, user will be added to chat members if he is yet not a member and there is less than 200 members in the channel. Status will not be changed until chat state will be synchronized with the server. Status will not be changed if application is killed before it can send request to the server.
     */
    public static class ChangeChatMemberStatus extends TLFunction {
        /**
         * Chat identifier.
         */
        public long chatId;
        /**
         * Identifier of the user to edit status, bots can be editors in the channel chats.
         */
        public int userId;
        /**
         * New status of the member in the chat.
         */
        public ChatMemberStatus status;

        /**
         * Default constructor.
         */
        public ChangeChatMemberStatus() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier.
         * @param userId Identifier of the user to edit status, bots can be editors in the channel chats.
         * @param status New status of the member in the chat.
         */
        public ChangeChatMemberStatus(long chatId, int userId, ChatMemberStatus status) {
            this.chatId = chatId;
            this.userId = userId;
            this.status = status;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1725424647;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -1725424647;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChangeChatMemberStatus").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("userId = ").append(userId);
            appendLine(s, shift).append("status = "); if (status != null) { status.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Changes chat photo. Photo can't be changed for private chats. Photo will not change until change will be synchronized with the server. Photo will not be changed if application is killed before it can send request to the server. There will be update about change of the photo on success. Otherwise error will be returned.
     */
    public static class ChangeChatPhoto extends TLFunction {
        /**
         * Chat identifier.
         */
        public long chatId;
        /**
         * New chat photo. You can use zero InputFileId to delete photo. Files accessible only by HTTP URL are not acceptable.
         */
        public InputFile photo;

        /**
         * Default constructor.
         */
        public ChangeChatPhoto() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier.
         * @param photo New chat photo. You can use zero InputFileId to delete photo. Files accessible only by HTTP URL are not acceptable.
         */
        public ChangeChatPhoto(long chatId, InputFile photo) {
            this.chatId = chatId;
            this.photo = photo;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -452848448;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -452848448;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("ChangeChatPhoto").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("photo = "); if (photo != null) { photo.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Changes chat title. Title can't be changed for private chats. Title will not change until change will be synchronized with the server. Title will not be changed if application is killed before it can send request to the server. There will be update about change of the title on success. Otherwise error will be returned.
     */
    public static class ChangeChatTitle extends TLFunction {
        /**
         * Chat identifier.
         */
        public long chatId;
        /**
         * New title of a chat, 0-255 characters.
         */
        public String title;

        /**
         * Default constructor.
         */
        public ChangeChatTitle() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier.
         * @param title New title of a chat, 0-255 characters.
         */
        public ChangeChatTitle(long chatId, String title) {
            this.chatId = chatId;
            this.title = title;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -503002783;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Changes first and last names of logged in user. If something changes, updateUser will be sent.
     */
    public static class ChangeName extends TLFunction {
        /**
         * New value of user first name, 1-255 characters.
         */
        public String firstName;
        /**
         * New value of optional user last name, 0-255 characters.
         */
        public String lastName;

        /**
         * Default constructor.
         */
        public ChangeName() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param firstName New value of user first name, 1-255 characters.
         * @param lastName New value of optional user last name, 0-255 characters.
         */
        public ChangeName(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1341435471;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Changes user's phone number and sends authentication code to the new user's phone number. Returns authStateWaitCode with information about sent code on success.
     */
    public static class ChangePhoneNumber extends TLFunction {
        /**
         * New user's phone number in any reasonable format.
         */
        public String phoneNumber;
        /**
         * Pass True, if code can be sent via flash call to the specified phone number.
         */
        public boolean allowFlashCall;
        /**
         * Pass true, if the phone number is used on the current device. Ignored if allowFlashCall is False.
         */
        public boolean isCurrentPhoneNumber;

        /**
         * Default constructor.
         */
        public ChangePhoneNumber() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param phoneNumber New user's phone number in any reasonable format.
         * @param allowFlashCall Pass True, if code can be sent via flash call to the specified phone number.
         * @param isCurrentPhoneNumber Pass true, if the phone number is used on the current device. Ignored if allowFlashCall is False.
         */
        public ChangePhoneNumber(String phoneNumber, boolean allowFlashCall, boolean isCurrentPhoneNumber) {
            this.phoneNumber = phoneNumber;
            this.allowFlashCall = allowFlashCall;
            this.isCurrentPhoneNumber = isCurrentPhoneNumber;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1329283881;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Changes username of logged in user. If something changes, updateUser will be sent.
     */
    public static class ChangeUsername extends TLFunction {
        /**
         * New value of username. Use empty string to remove username.
         */
        public String username;

        /**
         * Default constructor.
         */
        public ChangeUsername() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param username New value of username. Use empty string to remove username.
         */
        public ChangeUsername(String username) {
            this.username = username;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 2015886676;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Check bot's authentication token to log in as a bot. Works only when authGetState returns authStateWaitPhoneNumber. Can be used instead of setAuthPhoneNumber and checkAuthCode to log in. Returns authStateOk on success.
     */
    public static class CheckAuthBotToken extends TLFunction {
        /**
         * Bot token.
         */
        public String token;

        /**
         * Default constructor.
         */
        public CheckAuthBotToken() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param token Bot token.
         */
        public CheckAuthBotToken(String token) {
            this.token = token;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1829581747;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Checks authentication code. Works only when authGetState returns authStateWaitCode. Returns authStateWaitPassword or authStateOk on success.
     */
    public static class CheckAuthCode extends TLFunction {
        /**
         * Verification code from SMS, Telegram message, voice call or flash call.
         */
        public String code;
        /**
         * User first name, if user is yet not registered, 1-255 characters.
         */
        public String firstName;
        /**
         * Optional user last name, if user is yet not registered, 0-255 characters.
         */
        public String lastName;

        /**
         * Default constructor.
         */
        public CheckAuthCode() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param code Verification code from SMS, Telegram message, voice call or flash call.
         * @param firstName User first name, if user is yet not registered, 1-255 characters.
         * @param lastName Optional user last name, if user is yet not registered, 0-255 characters.
         */
        public CheckAuthCode(String code, String firstName, String lastName) {
            this.code = code;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1636693521;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Checks password for correctness. Works only when authGetState returns authStateWaitPassword. Returns authStateOk on success.
     */
    public static class CheckAuthPassword extends TLFunction {
        /**
         * Password to check.
         */
        public String password;

        /**
         * Default constructor.
         */
        public CheckAuthPassword() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param password Password to check.
         */
        public CheckAuthPassword(String password) {
            this.password = password;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1138590405;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Checks authentication code sent to change user's phone number. Returns authStateOk on success.
     */
    public static class CheckChangePhoneNumberCode extends TLFunction {
        /**
         * Verification code from SMS, voice call or flash call.
         */
        public String code;

        /**
         * Default constructor.
         */
        public CheckChangePhoneNumberCode() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param code Verification code from SMS, voice call or flash call.
         */
        public CheckChangePhoneNumberCode(String code) {
            this.code = code;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1010764228;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Checks chat invite link for validness and returns information about the corresponding chat.
     */
    public static class CheckChatInviteLink extends TLFunction {
        /**
         * Invite link to check. Should begin with &quot;https://telegram.me/joinchat/&quot;.
         */
        public String inviteLink;

        /**
         * Default constructor.
         */
        public CheckChatInviteLink() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param inviteLink Invite link to check. Should begin with &quot;https://telegram.me/joinchat/&quot;.
         */
        public CheckChatInviteLink(String inviteLink) {
            this.inviteLink = inviteLink;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -496940997;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Chat is closed by the user. Many useful activities depends on chat being opened or closed.
     */
    public static class CloseChat extends TLFunction {
        /**
         * Chat identifier.
         */
        public long chatId;

        /**
         * Default constructor.
         */
        public CloseChat() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier.
         */
        public CloseChat(long chatId) {
            this.chatId = chatId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1996586409;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Returns existing chat corresponding to the known channel.
     */
    public static class CreateChannelChat extends TLFunction {
        /**
         * Channel identifier.
         */
        public int channelId;

        /**
         * Default constructor.
         */
        public CreateChannelChat() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param channelId Channel identifier.
         */
        public CreateChannelChat(int channelId) {
            this.channelId = channelId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 31132213;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Returns existing chat corresponding to the known group.
     */
    public static class CreateGroupChat extends TLFunction {
        /**
         * Group identifier.
         */
        public int groupId;

        /**
         * Default constructor.
         */
        public CreateGroupChat() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param groupId Group identifier.
         */
        public CreateGroupChat(int groupId) {
            this.groupId = groupId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -804136412;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Creates new channel chat and send corresponding messageChannelChatCreate, returns created chat.
     */
    public static class CreateNewChannelChat extends TLFunction {
        /**
         * Title of new channel chat, 0-255 characters.
         */
        public String title;
        /**
         * True, if supergroup chat should be created.
         */
        public boolean isSupergroup;
        /**
         * Information about the channel, 0-255 characters.
         */
        public String about;

        /**
         * Default constructor.
         */
        public CreateNewChannelChat() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param title Title of new channel chat, 0-255 characters.
         * @param isSupergroup True, if supergroup chat should be created.
         * @param about Information about the channel, 0-255 characters.
         */
        public CreateNewChannelChat(String title, boolean isSupergroup, String about) {
            this.title = title;
            this.isSupergroup = isSupergroup;
            this.about = about;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1547755747;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Creates new group chat and send corresponding messageGroupChatCreate, returns created chat.
     */
    public static class CreateNewGroupChat extends TLFunction {
        /**
         * Identifiers of users to add to the group.
         */
        public int[] userIds;
        /**
         * Title of new group chat, 0-255 characters.
         */
        public String title;

        /**
         * Default constructor.
         */
        public CreateNewGroupChat() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param userIds Identifiers of users to add to the group.
         * @param title Title of new group chat, 0-255 characters.
         */
        public CreateNewGroupChat(int[] userIds, String title) {
            this.userIds = userIds;
            this.title = title;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1513123868;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Creates new secret chat, returns created chat.
     */
    public static class CreateNewSecretChat extends TLFunction {
        /**
         * Identifier of a user to create secret chat with.
         */
        public int userId;

        /**
         * Default constructor.
         */
        public CreateNewSecretChat() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param userId Identifier of a user to create secret chat with.
         */
        public CreateNewSecretChat(int userId) {
            this.userId = userId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 391182939;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Returns existing chat corresponding to the given user.
     */
    public static class CreatePrivateChat extends TLFunction {
        /**
         * User identifier.
         */
        public int userId;

        /**
         * Default constructor.
         */
        public CreatePrivateChat() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param userId User identifier.
         */
        public CreatePrivateChat(int userId) {
            this.userId = userId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1204324690;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Returns existing chat corresponding to the known secret chat.
     */
    public static class CreateSecretChat extends TLFunction {
        /**
         * SecretChat identifier.
         */
        public int secretChatId;

        /**
         * Default constructor.
         */
        public CreateSecretChat() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param secretChatId SecretChat identifier.
         */
        public CreateSecretChat(int secretChatId) {
            this.secretChatId = secretChatId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -74300012;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Deletes the account of currently logged in user, deleting from the server all information associated with it. Account's phone number can be used to create new account, but only once in two weeks.
     */
    public static class DeleteAccount extends TLFunction {
        /**
         * Optional reason of account deletion.
         */
        public String reason;

        /**
         * Default constructor.
         */
        public DeleteAccount() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param reason Optional reason of account deletion.
         */
        public DeleteAccount(String reason) {
            this.reason = reason;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1203056508;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Revokes another authorization of logged in user.
     */
    public static class DeleteAuthorization extends TLFunction {
        /**
         * Authorization identifier.
         */
        public long authorizationId;

        /**
         * Default constructor.
         */
        public DeleteAuthorization() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param authorizationId Authorization identifier.
         */
        public DeleteAuthorization(long authorizationId) {
            this.authorizationId = authorizationId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -834637761;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Deletes channel along with all messages in corresponding chat. Releases channel username and removes all members. Needs creator privileges in the channel. Channels with more than 1000 members can't be deleted.
     */
    public static class DeleteChannel extends TLFunction {
        /**
         * Identifier of the channel.
         */
        public int channelId;

        /**
         * Default constructor.
         */
        public DeleteChannel() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param channelId Identifier of the channel.
         */
        public DeleteChannel(int channelId) {
            this.channelId = channelId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 251111194;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Deletes all messages in the chat. Can't be used for channel chats.
     */
    public static class DeleteChatHistory extends TLFunction {
        /**
         * Chat identifier.
         */
        public long chatId;
        /**
         * Pass true, if chat should be removed from the chat list.
         */
        public boolean removeFromChatList;

        /**
         * Default constructor.
         */
        public DeleteChatHistory() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier.
         * @param removeFromChatList Pass true, if chat should be removed from the chat list.
         */
        public DeleteChatHistory(long chatId, boolean removeFromChatList) {
            this.chatId = chatId;
            this.removeFromChatList = removeFromChatList;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 987906679;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Deletes default reply markup from chat. This method needs to be called after one-time keyboard or ForceReply reply markup has been used. UpdateChatReplyMarkup will be send if reply markup will be changed.
     */
    public static class DeleteChatReplyMarkup extends TLFunction {
        /**
         * Chat identifier.
         */
        public long chatId;
        /**
         * Message identifier of used keyboard.
         */
        public int messageId;

        /**
         * Default constructor.
         */
        public DeleteChatReplyMarkup() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier.
         * @param messageId Message identifier of used keyboard.
         */
        public DeleteChatReplyMarkup(long chatId, int messageId) {
            this.chatId = chatId;
            this.messageId = messageId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 959624272;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Deletes users from contacts list.
     */
    public static class DeleteContacts extends TLFunction {
        /**
         * Identifiers of users to be deleted.
         */
        public int[] userIds;

        /**
         * Default constructor.
         */
        public DeleteContacts() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param userIds Identifiers of users to be deleted.
         */
        public DeleteContacts(int[] userIds) {
            this.userIds = userIds;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 641913511;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Deletes messages. UpdateDeleteMessages will not be sent for messages deleted through that function.
     */
    public static class DeleteMessages extends TLFunction {
        /**
         * Chat identifier.
         */
        public long chatId;
        /**
         * Identifiers of messages to delete.
         */
        public int[] messageIds;

        /**
         * Default constructor.
         */
        public DeleteMessages() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier.
         * @param messageIds Identifiers of messages to delete.
         */
        public DeleteMessages(long chatId, int[] messageIds) {
            this.chatId = chatId;
            this.messageIds = messageIds;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1789583863;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Deletes profile photo. If something changes, updateUser will be sent.
     */
    public static class DeleteProfilePhoto extends TLFunction {
        /**
         * Identifier of profile photo to delete.
         */
        public long profilePhotoId;

        /**
         * Default constructor.
         */
        public DeleteProfilePhoto() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param profilePhotoId Identifier of profile photo to delete.
         */
        public DeleteProfilePhoto(long profilePhotoId) {
            this.profilePhotoId = profilePhotoId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -564878026;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Deletes chat from the list of recently found chats.
     */
    public static class DeleteRecentlyFoundChat extends TLFunction {
        /**
         * Identifier of the chat to delete.
         */
        public long chatId;

        /**
         * Default constructor.
         */
        public DeleteRecentlyFoundChat() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Identifier of the chat to delete.
         */
        public DeleteRecentlyFoundChat(long chatId) {
            this.chatId = chatId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1317692233;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Clears list of recently found chats.
     */
    public static class DeleteRecentlyFoundChats extends TLFunction {

        /**
         * Default constructor.
         */
        public DeleteRecentlyFoundChats() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -552660433;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Removes animation from the list of saved animations.
     */
    public static class DeleteSavedAnimation extends TLFunction {
        /**
         * Animation file to delete.
         */
        public InputFile animation;

        /**
         * Default constructor.
         */
        public DeleteSavedAnimation() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param animation Animation file to delete.
         */
        public DeleteSavedAnimation(InputFile animation) {
            this.animation = animation;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 2029723055;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 2029723055;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("DeleteSavedAnimation").append(" {");
            shift += 2;
            appendLine(s, shift).append("animation = "); if (animation != null) { animation.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Asynchronously downloads file from cloud. Updates updateFileProgress will notify about download progress. Update updateFile will notify about successful download.
     */
    public static class DownloadFile extends TLFunction {
        /**
         * Identifier of file to download.
         */
        public int fileId;

        /**
         * Default constructor.
         */
        public DownloadFile() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param fileId Identifier of file to download.
         */
        public DownloadFile(int fileId) {
            this.fileId = fileId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 888468545;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Bots only. Edits caption of an inline message content sent via bot.
     */
    public static class EditInlineMessageCaption extends TLFunction {
        /**
         * Inline message identifier.
         */
        public String inlineMessageId;
        /**
         * New message reply markup.
         */
        public ReplyMarkup replyMarkup;
        /**
         * New message content caption, 0-200 characters.
         */
        public String caption;

        /**
         * Default constructor.
         */
        public EditInlineMessageCaption() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param inlineMessageId Inline message identifier.
         * @param replyMarkup New message reply markup.
         * @param caption New message content caption, 0-200 characters.
         */
        public EditInlineMessageCaption(String inlineMessageId, ReplyMarkup replyMarkup, String caption) {
            this.inlineMessageId = inlineMessageId;
            this.replyMarkup = replyMarkup;
            this.caption = caption;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 676019578;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 676019578;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("EditInlineMessageCaption").append(" {");
            shift += 2;
            appendLine(s, shift).append("inlineMessageId = ").append(inlineMessageId);
            appendLine(s, shift).append("replyMarkup = "); if (replyMarkup != null) { replyMarkup.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("caption = ").append(caption);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Bots only. Edits reply markup of an inline message sent via bot.
     */
    public static class EditInlineMessageReplyMarkup extends TLFunction {
        /**
         * Inline message identifier.
         */
        public String inlineMessageId;
        /**
         * New message reply markup.
         */
        public ReplyMarkup replyMarkup;

        /**
         * Default constructor.
         */
        public EditInlineMessageReplyMarkup() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param inlineMessageId Inline message identifier.
         * @param replyMarkup New message reply markup.
         */
        public EditInlineMessageReplyMarkup(String inlineMessageId, ReplyMarkup replyMarkup) {
            this.inlineMessageId = inlineMessageId;
            this.replyMarkup = replyMarkup;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -67565858;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -67565858;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("EditInlineMessageReplyMarkup").append(" {");
            shift += 2;
            appendLine(s, shift).append("inlineMessageId = ").append(inlineMessageId);
            appendLine(s, shift).append("replyMarkup = "); if (replyMarkup != null) { replyMarkup.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Bots only. Edits text of an inline text or game message sent via bot.
     */
    public static class EditInlineMessageText extends TLFunction {
        /**
         * Inline message identifier.
         */
        public String inlineMessageId;
        /**
         * New message reply markup.
         */
        public ReplyMarkup replyMarkup;
        /**
         * New text content of the message. Should be of type InputMessageText.
         */
        public InputMessageContent inputMessageContent;

        /**
         * Default constructor.
         */
        public EditInlineMessageText() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param inlineMessageId Inline message identifier.
         * @param replyMarkup New message reply markup.
         * @param inputMessageContent New text content of the message. Should be of type InputMessageText.
         */
        public EditInlineMessageText(String inlineMessageId, ReplyMarkup replyMarkup, InputMessageContent inputMessageContent) {
            this.inlineMessageId = inlineMessageId;
            this.replyMarkup = replyMarkup;
            this.inputMessageContent = inputMessageContent;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -855457307;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -855457307;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("EditInlineMessageText").append(" {");
            shift += 2;
            appendLine(s, shift).append("inlineMessageId = ").append(inlineMessageId);
            appendLine(s, shift).append("replyMarkup = "); if (replyMarkup != null) { replyMarkup.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("inputMessageContent = "); if (inputMessageContent != null) { inputMessageContent.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Edits message content caption. Non-bots can edit message in a limited period of time. Returns edited message after edit is complete server side.
     */
    public static class EditMessageCaption extends TLFunction {
        /**
         * Chat the message belongs to.
         */
        public long chatId;
        /**
         * Identifier of the message.
         */
        public int messageId;
        /**
         * New message reply markup, only for bots.
         */
        public ReplyMarkup replyMarkup;
        /**
         * New message content caption, 0-200 characters.
         */
        public String caption;

        /**
         * Default constructor.
         */
        public EditMessageCaption() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat the message belongs to.
         * @param messageId Identifier of the message.
         * @param replyMarkup New message reply markup, only for bots.
         * @param caption New message content caption, 0-200 characters.
         */
        public EditMessageCaption(long chatId, int messageId, ReplyMarkup replyMarkup, String caption) {
            this.chatId = chatId;
            this.messageId = messageId;
            this.replyMarkup = replyMarkup;
            this.caption = caption;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 702735393;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 702735393;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("EditMessageCaption").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("messageId = ").append(messageId);
            appendLine(s, shift).append("replyMarkup = "); if (replyMarkup != null) { replyMarkup.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("caption = ").append(caption);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Bots only. Edits message reply markup. Returns edited message after edit is complete server side.
     */
    public static class EditMessageReplyMarkup extends TLFunction {
        /**
         * Chat the message belongs to.
         */
        public long chatId;
        /**
         * Identifier of the message.
         */
        public int messageId;
        /**
         * New message reply markup.
         */
        public ReplyMarkup replyMarkup;

        /**
         * Default constructor.
         */
        public EditMessageReplyMarkup() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat the message belongs to.
         * @param messageId Identifier of the message.
         * @param replyMarkup New message reply markup.
         */
        public EditMessageReplyMarkup(long chatId, int messageId, ReplyMarkup replyMarkup) {
            this.chatId = chatId;
            this.messageId = messageId;
            this.replyMarkup = replyMarkup;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1344470531;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -1344470531;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("EditMessageReplyMarkup").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("messageId = ").append(messageId);
            appendLine(s, shift).append("replyMarkup = "); if (replyMarkup != null) { replyMarkup.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Edits text of text or game message. Non-bots can edit message in a limited period of time. Returns edited message after edit is complete server side.
     */
    public static class EditMessageText extends TLFunction {
        /**
         * Chat the message belongs to.
         */
        public long chatId;
        /**
         * Identifier of the message.
         */
        public int messageId;
        /**
         * New message reply markup, only for bots.
         */
        public ReplyMarkup replyMarkup;
        /**
         * New text content of the message. Should be of type InputMessageText.
         */
        public InputMessageContent inputMessageContent;

        /**
         * Default constructor.
         */
        public EditMessageText() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat the message belongs to.
         * @param messageId Identifier of the message.
         * @param replyMarkup New message reply markup, only for bots.
         * @param inputMessageContent New text content of the message. Should be of type InputMessageText.
         */
        public EditMessageText(long chatId, int messageId, ReplyMarkup replyMarkup, InputMessageContent inputMessageContent) {
            this.chatId = chatId;
            this.messageId = messageId;
            this.replyMarkup = replyMarkup;
            this.inputMessageContent = inputMessageContent;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1316368529;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 1316368529;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("EditMessageText").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("messageId = ").append(messageId);
            appendLine(s, shift).append("replyMarkup = "); if (replyMarkup != null) { replyMarkup.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("inputMessageContent = "); if (inputMessageContent != null) { inputMessageContent.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Generates new chat invite link, previously generated link is revoked. Available for group and channel chats. Only creator of the chat can export chat invite link.
     */
    public static class ExportChatInviteLink extends TLFunction {
        /**
         * Chat identifier.
         */
        public long chatId;

        /**
         * Default constructor.
         */
        public ExportChatInviteLink() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier.
         */
        public ExportChatInviteLink(long chatId) {
            this.chatId = chatId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1549493828;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Forwards previously sent messages. Returns forwarded messages in the same order as message identifiers passed in messageIds. If message can't be forwarded, null will be returned instead of the message. UpdateChatTopMessage will not be sent, so returned messages should be used to update chat top message.
     */
    public static class ForwardMessages extends TLFunction {
        /**
         * Identifier of a chat to forward messages.
         */
        public long chatId;
        /**
         * Identifier of a chat to forward from.
         */
        public long fromChatId;
        /**
         * Identifiers of messages to forward.
         */
        public int[] messageIds;
        /**
         * Pass true, to disable notification about the message in up to date clients, bots only.
         */
        public boolean disableNotification;
        /**
         * Pass true, if the message is sent from background.
         */
        public boolean fromBackground;

        /**
         * Default constructor.
         */
        public ForwardMessages() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Identifier of a chat to forward messages.
         * @param fromChatId Identifier of a chat to forward from.
         * @param messageIds Identifiers of messages to forward.
         * @param disableNotification Pass true, to disable notification about the message in up to date clients, bots only.
         * @param fromBackground Pass true, if the message is sent from background.
         */
        public ForwardMessages(long chatId, long fromChatId, int[] messageIds, boolean disableNotification, boolean fromBackground) {
            this.chatId = chatId;
            this.fromChatId = fromChatId;
            this.messageIds = messageIds;
            this.disableNotification = disableNotification;
            this.fromBackground = fromBackground;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 716825012;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Returns period of inactivity, after which the account of currently logged in user will be automatically deleted.
     */
    public static class GetAccountTtl extends TLFunction {

        /**
         * Default constructor.
         */
        public GetAccountTtl() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -443905161;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Returns current authorization state, offline request.
     */
    public static class GetAuthState extends TLFunction {

        /**
         * Default constructor.
         */
        public GetAuthState() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1193342487;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Returns all authorizations of logged in user.
     */
    public static class GetAuthorizations extends TLFunction {

        /**
         * Default constructor.
         */
        public GetAuthorizations() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1232858954;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Returns users blocked by the current user.
     */
    public static class GetBlockedUsers extends TLFunction {
        /**
         * Number of users to skip in result, must be non-negative.
         */
        public int offset;
        /**
         * Maximum number of users to return, can't be greater than 100.
         */
        public int limit;

        /**
         * Default constructor.
         */
        public GetBlockedUsers() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param offset Number of users to skip in result, must be non-negative.
         * @param limit Maximum number of users to return, can't be greater than 100.
         */
        public GetBlockedUsers(int offset, int limit) {
            this.offset = offset;
            this.limit = limit;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 9255073;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Sends callback query to a bot and returns answer to it. Unavailable for bots.
     */
    public static class GetCallbackQueryAnswer extends TLFunction {
        /**
         * Identifier of the chat with a message.
         */
        public long chatId;
        /**
         * Identifier of the message, from which the query is originated.
         */
        public int messageId;
        /**
         * Query payload.
         */
        public CallbackQueryPayload payload;

        /**
         * Default constructor.
         */
        public GetCallbackQueryAnswer() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Identifier of the chat with a message.
         * @param messageId Identifier of the message, from which the query is originated.
         * @param payload Query payload.
         */
        public GetCallbackQueryAnswer(long chatId, int messageId, CallbackQueryPayload payload) {
            this.chatId = chatId;
            this.messageId = messageId;
            this.payload = payload;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 693485097;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 693485097;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetCallbackQueryAnswer").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("messageId = ").append(messageId);
            appendLine(s, shift).append("payload = "); if (payload != null) { payload.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Returns information about a channel by its identifier, offline request if current user is not a bot.
     */
    public static class GetChannel extends TLFunction {
        /**
         * Channel identifier.
         */
        public int channelId;

        /**
         * Default constructor.
         */
        public GetChannel() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param channelId Channel identifier.
         */
        public GetChannel(int channelId) {
            this.channelId = channelId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1117537550;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Returns full information about a channel by its identifier, cached for at most 1 minute.
     */
    public static class GetChannelFull extends TLFunction {
        /**
         * Channel identifier.
         */
        public int channelId;

        /**
         * Default constructor.
         */
        public GetChannelFull() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param channelId Channel identifier.
         */
        public GetChannelFull(int channelId) {
            this.channelId = channelId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -704893497;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Returns information about channel members or kicked from channel users. Can be used only if channelFull-&gt;canGetMembers == true.
     */
    public static class GetChannelMembers extends TLFunction {
        /**
         * Identifier of the channel.
         */
        public int channelId;
        /**
         * Kind of channel users to return, defaults to channelMembersRecent.
         */
        public ChannelMembersFilter filter;
        /**
         * Number of channel users to skip.
         */
        public int offset;
        /**
         * Maximum number of users be returned, can't be greater than 200.
         */
        public int limit;

        /**
         * Default constructor.
         */
        public GetChannelMembers() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param channelId Identifier of the channel.
         * @param filter Kind of channel users to return, defaults to channelMembersRecent.
         * @param offset Number of channel users to skip.
         * @param limit Maximum number of users be returned, can't be greater than 200.
         */
        public GetChannelMembers(int channelId, ChannelMembersFilter filter, int offset, int limit) {
            this.channelId = channelId;
            this.filter = filter;
            this.offset = offset;
            this.limit = limit;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 2116707652;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 2116707652;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetChannelMembers").append(" {");
            shift += 2;
            appendLine(s, shift).append("channelId = ").append(channelId);
            appendLine(s, shift).append("filter = "); if (filter != null) { filter.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("offset = ").append(offset);
            appendLine(s, shift).append("limit = ").append(limit);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Returns information about a chat by its identifier, offline request if current user is not a bot.
     */
    public static class GetChat extends TLFunction {
        /**
         * Chat identifier.
         */
        public long chatId;

        /**
         * Default constructor.
         */
        public GetChat() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier.
         */
        public GetChat(long chatId) {
            this.chatId = chatId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1645526841;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Returns messages in a chat. Automatically calls openChat. Returns result in reverse chronological order, i.e. in order of decreasing message.messageId.
     */
    public static class GetChatHistory extends TLFunction {
        /**
         * Chat identifier.
         */
        public long chatId;
        /**
         * Identifier of the message near which we need a history, you can use 0 to get results from the beginning, i.e. from oldest to newest.
         */
        public int fromMessageId;
        /**
         * Specify 0 to get results exactly from fromMessageId or negative offset to get specified message and some newer messages.
         */
        public int offset;
        /**
         * Maximum number of messages to be returned, should be positive and can't be greater than 100. If offset is negative, limit must be greater than -offset. There may be less than limit messages returned even the end of the history is not reached.
         */
        public int limit;

        /**
         * Default constructor.
         */
        public GetChatHistory() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier.
         * @param fromMessageId Identifier of the message near which we need a history, you can use 0 to get results from the beginning, i.e. from oldest to newest.
         * @param offset Specify 0 to get results exactly from fromMessageId or negative offset to get specified message and some newer messages.
         * @param limit Maximum number of messages to be returned, should be positive and can't be greater than 100. If offset is negative, limit must be greater than -offset. There may be less than limit messages returned even the end of the history is not reached.
         */
        public GetChatHistory(long chatId, int fromMessageId, int offset, int limit) {
            this.chatId = chatId;
            this.fromMessageId = fromMessageId;
            this.offset = offset;
            this.limit = limit;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 851842897;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Returns information about one participant of the chat.
     */
    public static class GetChatMember extends TLFunction {
        /**
         * Chat identifier.
         */
        public long chatId;
        /**
         * User identifier.
         */
        public int userId;

        /**
         * Default constructor.
         */
        public GetChatMember() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier.
         * @param userId User identifier.
         */
        public GetChatMember(long chatId, int userId) {
            this.chatId = chatId;
            this.userId = userId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1885026956;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Returns list of chats in the right order, chats are sorted by (order, chatId) in decreasing order. For example, to get list of chats from the beginning, the offsetOrder should be equal 2^63 - 1.
     */
    public static class GetChats extends TLFunction {
        /**
         * Chat order to return chats from.
         */
        public long offsetOrder;
        /**
         * Chat identifier to return chats from.
         */
        public long offsetChatId;
        /**
         * Maximum number of chats to be returned.
         */
        public int limit;

        /**
         * Default constructor.
         */
        public GetChats() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param offsetOrder Chat order to return chats from.
         * @param offsetChatId Chat identifier to return chats from.
         * @param limit Maximum number of chats to be returned.
         */
        public GetChats(long offsetOrder, long offsetChatId, int limit) {
            this.offsetOrder = offsetOrder;
            this.offsetChatId = offsetChatId;
            this.limit = limit;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1867515173;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Returns information about a file, offline request.
     */
    public static class GetFile extends TLFunction {
        /**
         * Identifier of the file to get.
         */
        public int fileId;

        /**
         * Default constructor.
         */
        public GetFile() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param fileId Identifier of the file to get.
         */
        public GetFile(int fileId) {
            this.fileId = fileId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -225569621;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Returns information about a file by its persistent id, offline request.
     */
    public static class GetFilePersistent extends TLFunction {
        /**
         * Persistent identifier of the file to get.
         */
        public String persistentFileId;

        /**
         * Default constructor.
         */
        public GetFilePersistent() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param persistentFileId Persistent identifier of the file to get.
         */
        public GetFilePersistent(String persistentFileId) {
            this.persistentFileId = persistentFileId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1202286332;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Returns game high scores and some part of the score table around of the specified user in the game. Bots only.
     */
    public static class GetGameHighScores extends TLFunction {
        /**
         * Chat a message with the game belongs to.
         */
        public long chatId;
        /**
         * Identifier of the message.
         */
        public int messageId;
        /**
         * User identifie.
         */
        public int userId;

        /**
         * Default constructor.
         */
        public GetGameHighScores() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat a message with the game belongs to.
         * @param messageId Identifier of the message.
         * @param userId User identifie.
         */
        public GetGameHighScores(long chatId, int messageId, int userId) {
            this.chatId = chatId;
            this.messageId = messageId;
            this.userId = userId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1287568611;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Returns information about a group by its identifier, offline request if current user is not a bot.
     */
    public static class GetGroup extends TLFunction {
        /**
         * Group identifier.
         */
        public int groupId;

        /**
         * Default constructor.
         */
        public GetGroup() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param groupId Group identifier.
         */
        public GetGroup(int groupId) {
            this.groupId = groupId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1642068863;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Returns full information about a group by its identifier.
     */
    public static class GetGroupFull extends TLFunction {
        /**
         * Group identifier.
         */
        public int groupId;

        /**
         * Default constructor.
         */
        public GetGroupFull() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param groupId Group identifier.
         */
        public GetGroupFull(int groupId) {
            this.groupId = groupId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1459161427;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Returns game high scores and some part of the score table around of the specified user in the game. Bots only.
     */
    public static class GetInlineGameHighScores extends TLFunction {
        /**
         * Inline message identifier.
         */
        public String inlineMessageId;
        /**
         * User identifier.
         */
        public int userId;

        /**
         * Default constructor.
         */
        public GetInlineGameHighScores() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param inlineMessageId Inline message identifier.
         * @param userId User identifier.
         */
        public GetInlineGameHighScores(String inlineMessageId, int userId) {
            this.inlineMessageId = inlineMessageId;
            this.userId = userId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1458552156;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Sends inline query to a bot and returns its results. Unavailable for bots.
     */
    public static class GetInlineQueryResults extends TLFunction {
        /**
         * Identifier of the bot send query to.
         */
        public int botUserId;
        /**
         * Identifier of the chat, where the query is sent.
         */
        public long chatId;
        /**
         * User location, only if needed.
         */
        public Location userLocation;
        /**
         * Text of the query.
         */
        public String query;
        /**
         * Offset of the first entry to return.
         */
        public String offset;

        /**
         * Default constructor.
         */
        public GetInlineQueryResults() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param botUserId Identifier of the bot send query to.
         * @param chatId Identifier of the chat, where the query is sent.
         * @param userLocation User location, only if needed.
         * @param query Text of the query.
         * @param offset Offset of the first entry to return.
         */
        public GetInlineQueryResults(int botUserId, long chatId, Location userLocation, String query, String offset) {
            this.botUserId = botUserId;
            this.chatId = chatId;
            this.userLocation = userLocation;
            this.query = query;
            this.offset = offset;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 992897566;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 992897566;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetInlineQueryResults").append(" {");
            shift += 2;
            appendLine(s, shift).append("botUserId = ").append(botUserId);
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("userLocation = "); if (userLocation != null) { userLocation.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("query = ").append(query);
            appendLine(s, shift).append("offset = ").append(offset);
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Returns current logged in user.
     */
    public static class GetMe extends TLFunction {

        /**
         * Default constructor.
         */
        public GetMe() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -191516033;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Returns information about a message.
     */
    public static class GetMessage extends TLFunction {
        /**
         * Identifier of the chat, message belongs to.
         */
        public long chatId;
        /**
         * Identifier of the message to get.
         */
        public int messageId;

        /**
         * Default constructor.
         */
        public GetMessage() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Identifier of the chat, message belongs to.
         * @param messageId Identifier of the message to get.
         */
        public GetMessage(long chatId, int messageId) {
            this.chatId = chatId;
            this.messageId = messageId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1209218520;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Returns information about messages. If message is not found, returns null on the corresponding position of the result.
     */
    public static class GetMessages extends TLFunction {
        /**
         * Identifier of the chat, messages belongs to.
         */
        public long chatId;
        /**
         * Identifiers of the messages to get.
         */
        public int[] messageIds;

        /**
         * Default constructor.
         */
        public GetMessages() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Identifier of the chat, messages belongs to.
         * @param messageIds Identifiers of the messages to get.
         */
        public GetMessages(long chatId, int[] messageIds) {
            this.chatId = chatId;
            this.messageIds = messageIds;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1391199071;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Returns notification settings for given scope.
     */
    public static class GetNotificationSettings extends TLFunction {
        /**
         * Scope to return information about notification settings.
         */
        public NotificationSettingsScope scope;

        /**
         * Default constructor.
         */
        public GetNotificationSettings() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param scope Scope to return information about notification settings.
         */
        public GetNotificationSettings(NotificationSettingsScope scope) {
            this.scope = scope;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 907144391;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 907144391;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("GetNotificationSettings").append(" {");
            shift += 2;
            appendLine(s, shift).append("scope = "); if (scope != null) { scope.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Returns value of an option by its name. See list of available options on https://core.telegram.org/tdlib/options.
     */
    public static class GetOption extends TLFunction {
        /**
         * Name of the option.
         */
        public String name;

        /**
         * Default constructor.
         */
        public GetOption() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param name Name of the option.
         */
        public GetOption(String name) {
            this.name = name;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1572495746;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Returns up to 20 recently used inline bots in the order of the last usage.
     */
    public static class GetRecentInlineBots extends TLFunction {

        /**
         * Default constructor.
         */
        public GetRecentInlineBots() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1437823548;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Returns saved animations.
     */
    public static class GetSavedAnimations extends TLFunction {

        /**
         * Default constructor.
         */
        public GetSavedAnimations() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 7051032;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Returns information about sticker set by its identifier.
     */
    public static class GetStickerSet extends TLFunction {
        /**
         * Identifier of the sticker set.
         */
        public long setId;

        /**
         * Default constructor.
         */
        public GetStickerSet() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param setId Identifier of the sticker set.
         */
        public GetStickerSet(long setId) {
            this.setId = setId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1684803767;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Returns list of installed sticker sets.
     */
    public static class GetStickerSets extends TLFunction {
        /**
         * If true, returns only enabled sticker sets.
         */
        public boolean onlyEnabled;

        /**
         * Default constructor.
         */
        public GetStickerSets() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param onlyEnabled If true, returns only enabled sticker sets.
         */
        public GetStickerSets(boolean onlyEnabled) {
            this.onlyEnabled = onlyEnabled;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -700104885;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Returns stickers corresponding to given emoji.
     */
    public static class GetStickers extends TLFunction {
        /**
         * String representation of emoji. If empty, returns all known stickers.
         */
        public String emoji;

        /**
         * Default constructor.
         */
        public GetStickers() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param emoji String representation of emoji. If empty, returns all known stickers.
         */
        public GetStickers(String emoji) {
            this.emoji = emoji;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1712531528;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Returns user that can be contacted to get support.
     */
    public static class GetSupportUser extends TLFunction {

        /**
         * Default constructor.
         */
        public GetSupportUser() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1733497700;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Returns information about a user by its identifier, offline request if current user is not a bot.
     */
    public static class GetUser extends TLFunction {
        /**
         * User identifier.
         */
        public int userId;

        /**
         * Default constructor.
         */
        public GetUser() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param userId User identifier.
         */
        public GetUser(int userId) {
            this.userId = userId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -501534519;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Returns full information about a user by its identifier.
     */
    public static class GetUserFull extends TLFunction {
        /**
         * User identifier.
         */
        public int userId;

        /**
         * Default constructor.
         */
        public GetUserFull() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param userId User identifier.
         */
        public GetUserFull(int userId) {
            this.userId = userId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1977480168;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Returns profile photos of the user. Result of this query can't be invalidated, so it must be used with care.
     */
    public static class GetUserProfilePhotos extends TLFunction {
        /**
         * User identifier.
         */
        public int userId;
        /**
         * Photos to skip, must be non-negative.
         */
        public int offset;
        /**
         * Maximum number of photos to be returned, can't be greater than 100.
         */
        public int limit;

        /**
         * Default constructor.
         */
        public GetUserProfilePhotos() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param userId User identifier.
         * @param offset Photos to skip, must be non-negative.
         * @param limit Maximum number of photos to be returned, can't be greater than 100.
         */
        public GetUserProfilePhotos(int userId, int offset, int limit) {
            this.userId = userId;
            this.offset = offset;
            this.limit = limit;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1810450184;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Returns background wallpapers.
     */
    public static class GetWallpapers extends TLFunction {

        /**
         * Default constructor.
         */
        public GetWallpapers() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 2097518555;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Get web page preview by text of the message. Do not call this function to often.
     */
    public static class GetWebPagePreview extends TLFunction {
        /**
         * Message text.
         */
        public String messageText;

        /**
         * Default constructor.
         */
        public GetWebPagePreview() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param messageText Message text.
         */
        public GetWebPagePreview(String messageText) {
            this.messageText = messageText;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1322216444;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Imports chat invite link, adds current user to a chat if possible. Member will not be added until chat state will be synchronized with the server. Member will not be added if application is killed before it can send request to the server.
     */
    public static class ImportChatInviteLink extends TLFunction {
        /**
         * Invite link to import. Should begin with &quot;https://telegram.me/joinchat/&quot;.
         */
        public String inviteLink;

        /**
         * Default constructor.
         */
        public ImportChatInviteLink() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param inviteLink Invite link to import. Should begin with &quot;https://telegram.me/joinchat/&quot;.
         */
        public ImportChatInviteLink(String inviteLink) {
            this.inviteLink = inviteLink;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1824153031;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Adds new contacts/edits existing contacts, contacts user identifiers are ignored. Returns list of corresponding users in the same order as input contacts. If contact doesn't registered in Telegram, user with id == 0 will be returned.
     */
    public static class ImportContacts extends TLFunction {
        /**
         * List of contacts to import/edit.
         */
        public Contact[] contacts;

        /**
         * Default constructor.
         */
        public ImportContacts() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param contacts List of contacts to import/edit.
         */
        public ImportContacts(Contact[] contacts) {
            this.contacts = contacts;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1365609265;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Creates new channel supergroup chat from existing group chat and send corresponding messageChatMigrateTo and messageChatMigrateFrom. Available only if member limit for group was reached. Deactivates group.
     */
    public static class MigrateGroupChatToChannelChat extends TLFunction {
        /**
         * Group chat identifier.
         */
        public long chatId;

        /**
         * Default constructor.
         */
        public MigrateGroupChatToChannelChat() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Group chat identifier.
         */
        public MigrateGroupChatToChannelChat(long chatId) {
            this.chatId = chatId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1374124771;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Chat is opened by the user. Many useful activities depends on chat being opened or closed. For example, in channels all updates are received only for opened chats.
     */
    public static class OpenChat extends TLFunction {
        /**
         * Chat identifier.
         */
        public long chatId;

        /**
         * Default constructor.
         */
        public OpenChat() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier.
         */
        public OpenChat(long chatId) {
            this.chatId = chatId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1638354005;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Message content is opened, for example the user has opened a photo, a video, a document, a location or a venue or have listened to an audio or a voice message.
     */
    public static class OpenMessageContent extends TLFunction {
        /**
         * Chat identifier of the message.
         */
        public long chatId;
        /**
         * Identifier of the message with opened content.
         */
        public int messageId;

        /**
         * Default constructor.
         */
        public OpenMessageContent() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier of the message.
         * @param messageId Identifier of the message with opened content.
         */
        public OpenMessageContent(long chatId, int messageId) {
            this.chatId = chatId;
            this.messageId = messageId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -318527532;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Pins a message in a supergroup channel chat. Needs editor privileges in the channel.
     */
    public static class PinChannelMessage extends TLFunction {
        /**
         * Identifier of the channel.
         */
        public int channelId;
        /**
         * Identifier of the new pinned message.
         */
        public int messageId;
        /**
         * True, if there should be no notification about the pinned message.
         */
        public boolean disableNotification;

        /**
         * Default constructor.
         */
        public PinChannelMessage() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param channelId Identifier of the channel.
         * @param messageId Identifier of the new pinned message.
         * @param disableNotification True, if there should be no notification about the pinned message.
         */
        public PinChannelMessage(int channelId, int messageId, boolean disableNotification) {
            this.channelId = channelId;
            this.messageId = messageId;
            this.disableNotification = disableNotification;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -2024026179;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Recovers password with recover code sent to email. Works only when authGetState returns authStateWaitPassword. Returns authStateOk on success.
     */
    public static class RecoverAuthPassword extends TLFunction {
        /**
         * Recover code to check.
         */
        public String recoverCode;

        /**
         * Default constructor.
         */
        public RecoverAuthPassword() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param recoverCode Recover code to check.
         */
        public RecoverAuthPassword(String recoverCode) {
            this.recoverCode = recoverCode;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 130965839;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Requests to send recover code to email. Works only when authGetState returns authStateWaitPassword. Returns authStateWaitPassword on success.
     */
    public static class RequestAuthPasswordRecovery extends TLFunction {

        /**
         * Default constructor.
         */
        public RequestAuthPasswordRecovery() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1561685090;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Resends authentication code to the user. Works only when authGetState returns authStateWaitCode and nextCodeType of result is not null. Returns authStateWaitCode on success.
     */
    public static class ResendAuthCode extends TLFunction {

        /**
         * Default constructor.
         */
        public ResendAuthCode() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -2105917663;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Resends authentication code sent to change user's phone number. Wotks only if in previously received authStateWaitCode nextCodeType was not null. Returns authStateWaitCode on success.
     */
    public static class ResendChangePhoneNumberCode extends TLFunction {

        /**
         * Default constructor.
         */
        public ResendChangePhoneNumberCode() {
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -866825426;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Logs out user. If force == false, begins to perform soft log out, returns authStateLoggingOut after completion. If force == true then succeeds almost immediately without cleaning anything at the server, but returns error with code 401 and description &quot;Unauthorized&quot;.
     */
    public static class ResetAuth extends TLFunction {
        /**
         * If true, just delete all local data. Session will remain in list of active sessions.
         */
        public boolean force;

        /**
         * Default constructor.
         */
        public ResetAuth() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param force If true, just delete all local data. Session will remain in list of active sessions.
         */
        public ResetAuth(boolean force) {
            this.force = force;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -78661379;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Searches for messages with given words in the chat. Returns result in reverse chronological order, i. e. in order of decreasimg messageId. Doesn't work in secret chats.
     */
    public static class SearchChatMessages extends TLFunction {
        /**
         * Chat identifier to search in.
         */
        public long chatId;
        /**
         * Query to search for.
         */
        public String query;
        /**
         * Identifier of the message from which we need a history, you can use 0 to get results from beginning.
         */
        public int fromMessageId;
        /**
         * Maximum number of messages to be returned, can't be greater than 100.
         */
        public int limit;
        /**
         * Filter for content of searched messages.
         */
        public SearchMessagesFilter filter;

        /**
         * Default constructor.
         */
        public SearchChatMessages() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier to search in.
         * @param query Query to search for.
         * @param fromMessageId Identifier of the message from which we need a history, you can use 0 to get results from beginning.
         * @param limit Maximum number of messages to be returned, can't be greater than 100.
         * @param filter Filter for content of searched messages.
         */
        public SearchChatMessages(long chatId, String query, int fromMessageId, int limit, SearchMessagesFilter filter) {
            this.chatId = chatId;
            this.query = query;
            this.fromMessageId = fromMessageId;
            this.limit = limit;
            this.filter = filter;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -12337489;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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
            appendLine(s, shift).append("filter = "); if (filter != null) { filter.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Searches for specified query in the title and username of known chats, offline request. Returns chats in the order of them in the chat list.
     */
    public static class SearchChats extends TLFunction {
        /**
         * Query to search for, if query is empty, returns up to 20 recently found chats.
         */
        public String query;
        /**
         * Maximum number of chats to be returned.
         */
        public int limit;

        /**
         * Default constructor.
         */
        public SearchChats() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param query Query to search for, if query is empty, returns up to 20 recently found chats.
         * @param limit Maximum number of chats to be returned.
         */
        public SearchChats(String query, int limit) {
            this.query = query;
            this.limit = limit;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 344296595;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Searches for specified query in the first name, last name and username of the known user contacts.
     */
    public static class SearchContacts extends TLFunction {
        /**
         * Query to search for, can be empty to return all contacts.
         */
        public String query;
        /**
         * Maximum number of users to be returned.
         */
        public int limit;

        /**
         * Default constructor.
         */
        public SearchContacts() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param query Query to search for, can be empty to return all contacts.
         * @param limit Maximum number of users to be returned.
         */
        public SearchContacts(String query, int limit) {
            this.query = query;
            this.limit = limit;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 511929675;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Searches for messages in all chats except secret. Returns result in reverse chronological order, i. e. in order of decreasing (date, chatId, messageId).
     */
    public static class SearchMessages extends TLFunction {
        /**
         * Query to search for.
         */
        public String query;
        /**
         * Date of the message to search from, you can use 0 or any date in the future to get results from the beginning.
         */
        public int offsetDate;
        /**
         * Chat identifier of the last found message or 0 for the first request.
         */
        public long offsetChatId;
        /**
         * Message identifier of the last found message or 0 for the first request.
         */
        public int offsetMessageId;
        /**
         * Maximum number of messages to be returned, can't be greater than 100.
         */
        public int limit;

        /**
         * Default constructor.
         */
        public SearchMessages() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param query Query to search for.
         * @param offsetDate Date of the message to search from, you can use 0 or any date in the future to get results from the beginning.
         * @param offsetChatId Chat identifier of the last found message or 0 for the first request.
         * @param offsetMessageId Message identifier of the last found message or 0 for the first request.
         * @param limit Maximum number of messages to be returned, can't be greater than 100.
         */
        public SearchMessages(String query, int offsetDate, long offsetChatId, int offsetMessageId, int limit) {
            this.query = query;
            this.offsetDate = offsetDate;
            this.offsetChatId = offsetChatId;
            this.offsetMessageId = offsetMessageId;
            this.limit = limit;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -65689397;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Searches public chat by its username. Currently only private and channel chats can be public. Returns chat if found, otherwise some error is returned.
     */
    public static class SearchPublicChat extends TLFunction {
        /**
         * Username to be resolved.
         */
        public String username;

        /**
         * Default constructor.
         */
        public SearchPublicChat() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param username Username to be resolved.
         */
        public SearchPublicChat(String username) {
            this.username = username;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 857135533;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Searches public chats by prefix of their username. Currently only private and channel (including supergroup) chats can be public. Returns meaningful number of results. Returns nothing if length of the searched username prefix is less than 5. Excludes private chats with contacts from the results.
     */
    public static class SearchPublicChats extends TLFunction {
        /**
         * Prefix of the username to search.
         */
        public String usernamePrefix;

        /**
         * Default constructor.
         */
        public SearchPublicChats() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param usernamePrefix Prefix of the username to search.
         */
        public SearchPublicChats(String usernamePrefix) {
            this.usernamePrefix = usernamePrefix;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 505050556;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Searches sticker set by its short name.
     */
    public static class SearchStickerSet extends TLFunction {
        /**
         * Name of the sticker set.
         */
        public String name;

        /**
         * Default constructor.
         */
        public SearchStickerSet() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param name Name of the sticker set.
         */
        public SearchStickerSet(String name) {
            this.name = name;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1157930222;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Invites bot to a chat (if it is not in the chat) and send /start to it. Bot can't be invited to a private chat other than chat with the bot. Bots can't be invited to broadcast channel chats. Returns sent message. UpdateChatTopMessage will not be sent, so returned message should be used to update chat top message.
     */
    public static class SendBotStartMessage extends TLFunction {
        /**
         * Identifier of the bot.
         */
        public int botUserId;
        /**
         * Identifier of the chat.
         */
        public long chatId;
        /**
         * Hidden parameter sent to bot for deep linking (https://api.telegram.org/bots#deep-linking).
         */
        public String parameter;

        /**
         * Default constructor.
         */
        public SendBotStartMessage() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param botUserId Identifier of the bot.
         * @param chatId Identifier of the chat.
         * @param parameter Hidden parameter sent to bot for deep linking (https://api.telegram.org/bots#deep-linking).
         */
        public SendBotStartMessage(int botUserId, long chatId, String parameter) {
            this.botUserId = botUserId;
            this.chatId = chatId;
            this.parameter = parameter;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -952608730;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Sends notification about user activity in a chat.
     */
    public static class SendChatAction extends TLFunction {
        /**
         * Chat identifier.
         */
        public long chatId;
        /**
         * Action description.
         */
        public SendMessageAction action;

        /**
         * Default constructor.
         */
        public SendChatAction() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier.
         * @param action Action description.
         */
        public SendChatAction(long chatId, SendMessageAction action) {
            this.chatId = chatId;
            this.action = action;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 346586363;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 346586363;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SendChatAction").append(" {");
            shift += 2;
            appendLine(s, shift).append("chatId = ").append(chatId);
            appendLine(s, shift).append("action = "); if (action != null) { action.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Sends result of the inline query as a message. Returns sent message. UpdateChatTopMessage will not be sent, so returned message should be used to update chat top message. Always clears chat draft message.
     */
    public static class SendInlineQueryResultMessage extends TLFunction {
        /**
         * Chat to send message.
         */
        public long chatId;
        /**
         * Identifier of a message to reply to or 0.
         */
        public int replyToMessageId;
        /**
         * Pass true, to disable notification about the message in up to date clients, bots only.
         */
        public boolean disableNotification;
        /**
         * Pass true, if the message is sent from background.
         */
        public boolean fromBackground;
        /**
         * Identifier of the inline query.
         */
        public long queryId;
        /**
         * Identifier of the inline result.
         */
        public String resultId;

        /**
         * Default constructor.
         */
        public SendInlineQueryResultMessage() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat to send message.
         * @param replyToMessageId Identifier of a message to reply to or 0.
         * @param disableNotification Pass true, to disable notification about the message in up to date clients, bots only.
         * @param fromBackground Pass true, if the message is sent from background.
         * @param queryId Identifier of the inline query.
         * @param resultId Identifier of the inline result.
         */
        public SendInlineQueryResultMessage(long chatId, int replyToMessageId, boolean disableNotification, boolean fromBackground, long queryId, String resultId) {
            this.chatId = chatId;
            this.replyToMessageId = replyToMessageId;
            this.disableNotification = disableNotification;
            this.fromBackground = fromBackground;
            this.queryId = queryId;
            this.resultId = resultId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1342090907;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Sends a message. Returns sent message. UpdateChatTopMessage will not be sent, so returned message should be used to update chat top message.
     */
    public static class SendMessage extends TLFunction {
        /**
         * Chat to send message.
         */
        public long chatId;
        /**
         * Identifier of a message to reply to or 0.
         */
        public int replyToMessageId;
        /**
         * Pass true, to disable notification about the message in up to date clients, bots only.
         */
        public boolean disableNotification;
        /**
         * Pass true, if the message is sent from background.
         */
        public boolean fromBackground;
        /**
         * Markup for replying to message, available only for bots.
         */
        public ReplyMarkup replyMarkup;
        /**
         * Content of a message to send.
         */
        public InputMessageContent inputMessageContent;

        /**
         * Default constructor.
         */
        public SendMessage() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat to send message.
         * @param replyToMessageId Identifier of a message to reply to or 0.
         * @param disableNotification Pass true, to disable notification about the message in up to date clients, bots only.
         * @param fromBackground Pass true, if the message is sent from background.
         * @param replyMarkup Markup for replying to message, available only for bots.
         * @param inputMessageContent Content of a message to send.
         */
        public SendMessage(long chatId, int replyToMessageId, boolean disableNotification, boolean fromBackground, ReplyMarkup replyMarkup, InputMessageContent inputMessageContent) {
            this.chatId = chatId;
            this.replyToMessageId = replyToMessageId;
            this.disableNotification = disableNotification;
            this.fromBackground = fromBackground;
            this.replyMarkup = replyMarkup;
            this.inputMessageContent = inputMessageContent;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 777848191;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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
            appendLine(s, shift).append("replyMarkup = "); if (replyMarkup != null) { replyMarkup.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("inputMessageContent = "); if (inputMessageContent != null) { inputMessageContent.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Returns Ok after specified amount of the time passed.
     */
    public static class SetAlarm extends TLFunction {
        /**
         * Number of seconds before that function returns.
         */
        public double seconds;

        /**
         * Default constructor.
         */
        public SetAlarm() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param seconds Number of seconds before that function returns.
         */
        public SetAlarm(double seconds) {
            this.seconds = seconds;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -873497067;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Sets user's phone number and sends authentication code to the user. Works only when authGetState returns authStateWaitPhoneNumber. If phone number is not recognized or another error has happened, returns an error. Otherwise returns authStateWaitCode.
     */
    public static class SetAuthPhoneNumber extends TLFunction {
        /**
         * User's phone number in any reasonable format.
         */
        public String phoneNumber;
        /**
         * Pass True, if code can be sent via flash call to the specified phone number.
         */
        public boolean allowFlashCall;
        /**
         * Pass true, if the phone number is used on the current device. Ignored if allowFlashCall is False.
         */
        public boolean isCurrentPhoneNumber;

        /**
         * Default constructor.
         */
        public SetAuthPhoneNumber() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param phoneNumber User's phone number in any reasonable format.
         * @param allowFlashCall Pass True, if code can be sent via flash call to the specified phone number.
         * @param isCurrentPhoneNumber Pass true, if the phone number is used on the current device. Ignored if allowFlashCall is False.
         */
        public SetAuthPhoneNumber(String phoneNumber, boolean allowFlashCall, boolean isCurrentPhoneNumber) {
            this.phoneNumber = phoneNumber;
            this.allowFlashCall = allowFlashCall;
            this.isCurrentPhoneNumber = isCurrentPhoneNumber;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 270883354;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Updates game score of the specified user in the game. Bots only.
     */
    public static class SetGameScore extends TLFunction {
        /**
         * Chat a message with the game belongs to.
         */
        public long chatId;
        /**
         * Identifier of the message.
         */
        public int messageId;
        /**
         * True, if message should be edited.
         */
        public boolean editMessage;
        /**
         * User identifier.
         */
        public int userId;
        /**
         * New score.
         */
        public int score;

        /**
         * Default constructor.
         */
        public SetGameScore() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat a message with the game belongs to.
         * @param messageId Identifier of the message.
         * @param editMessage True, if message should be edited.
         * @param userId User identifier.
         * @param score New score.
         */
        public SetGameScore(long chatId, int messageId, boolean editMessage, int userId, int score) {
            this.chatId = chatId;
            this.messageId = messageId;
            this.editMessage = editMessage;
            this.userId = userId;
            this.score = score;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 395584402;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Updates game score of the specified user in the game. Bots only.
     */
    public static class SetInlineGameScore extends TLFunction {
        /**
         * Inline message identifier.
         */
        public String inlineMessageId;
        /**
         * True, if message should be edited.
         */
        public boolean editMessage;
        /**
         * User identifier.
         */
        public int userId;
        /**
         * New score.
         */
        public int score;

        /**
         * Default constructor.
         */
        public SetInlineGameScore() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param inlineMessageId Inline message identifier.
         * @param editMessage True, if message should be edited.
         * @param userId User identifier.
         * @param score New score.
         */
        public SetInlineGameScore(String inlineMessageId, boolean editMessage, int userId, int score) {
            this.inlineMessageId = inlineMessageId;
            this.editMessage = editMessage;
            this.userId = userId;
            this.score = score;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1403858858;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Changes notification settings for given scope.
     */
    public static class SetNotificationSettings extends TLFunction {
        /**
         * Scope to change notification settings.
         */
        public NotificationSettingsScope scope;
        /**
         * New notification settings for given scope.
         */
        public NotificationSettings notificationSettings;

        /**
         * Default constructor.
         */
        public SetNotificationSettings() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param scope Scope to change notification settings.
         * @param notificationSettings New notification settings for given scope.
         */
        public SetNotificationSettings(NotificationSettingsScope scope, NotificationSettings notificationSettings) {
            this.scope = scope;
            this.notificationSettings = notificationSettings;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -134430483;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return -134430483;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SetNotificationSettings").append(" {");
            shift += 2;
            appendLine(s, shift).append("scope = "); if (scope != null) { scope.toStringBuilder(shift, s); } else { s.append("null"); }
            appendLine(s, shift).append("notificationSettings = "); if (notificationSettings != null) { notificationSettings.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Sets value of an option. See list of available options on https://core.telegram.org/tdlib/options. Only writable options can be set.
     */
    public static class SetOption extends TLFunction {
        /**
         * Name of the option.
         */
        public String name;
        /**
         * New value of the option.
         */
        public OptionValue value;

        /**
         * Default constructor.
         */
        public SetOption() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param name Name of the option.
         * @param value New value of the option.
         */
        public SetOption(String name, OptionValue value) {
            this.name = name;
            this.value = value;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 2114670322;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
        public int getConstructor() {
            return 2114670322;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("SetOption").append(" {");
            shift += 2;
            appendLine(s, shift).append("name = ").append(name);
            appendLine(s, shift).append("value = "); if (value != null) { value.toStringBuilder(shift, s); } else { s.append("null"); }
            shift -= 2;
            appendLine(s, shift).append("}");
        }
    }

    /**
     * Uploads new profile photo for logged in user. Photo will not change until change will be synchronized with the server. Photo will not be changed if application is killed before it can send request to the server. If something changes, updateUser will be sent.
     */
    public static class SetProfilePhoto extends TLFunction {
        /**
         * Path to new profile photo.
         */
        public String photoPath;

        /**
         * Default constructor.
         */
        public SetProfilePhoto() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param photoPath Path to new profile photo.
         */
        public SetProfilePhoto(String photoPath) {
            this.photoPath = photoPath;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 508736004;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    public static class TestForceGetDifference extends TLFunction {

        public TestForceGetDifference() {
        }

        public static final int CONSTRUCTOR = -172129547;

        @Override
        public int getConstructor() {
            return -172129547;
        }

        @Override
        void toStringBuilder(int shift, StringBuilder s) {
            s.append("TestForceGetDifference").append(" {");
            shift += 2;
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

    /**
     * Gives or revokes right to invite new members to all current members of the channel. Needs creator privileges in the channel. Available only for supergroups.
     */
    public static class ToggleChannelInvites extends TLFunction {
        /**
         * Identifier of the channel.
         */
        public int channelId;
        /**
         * New value of anyoneCanInvite.
         */
        public boolean anyoneCanInvite;

        /**
         * Default constructor.
         */
        public ToggleChannelInvites() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param channelId Identifier of the channel.
         * @param anyoneCanInvite New value of anyoneCanInvite.
         */
        public ToggleChannelInvites(int channelId, boolean anyoneCanInvite) {
            this.channelId = channelId;
            this.anyoneCanInvite = anyoneCanInvite;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1454440041;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Enables or disables sender signature on sent messages in the channel. Needs creator privileges in the channel. Not available for supergroups.
     */
    public static class ToggleChannelSignMessages extends TLFunction {
        /**
         * Identifier of the channel.
         */
        public int channelId;
        /**
         * New value of signMessages.
         */
        public boolean signMessages;

        /**
         * Default constructor.
         */
        public ToggleChannelSignMessages() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param channelId Identifier of the channel.
         * @param signMessages New value of signMessages.
         */
        public ToggleChannelSignMessages(int channelId, boolean signMessages) {
            this.channelId = channelId;
            this.signMessages = signMessages;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -329182347;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Gives or revokes all members of the group editor rights. Needs creator privileges in the group.
     */
    public static class ToggleGroupEditors extends TLFunction {
        /**
         * Identifier of the group.
         */
        public int groupId;
        /**
         * New value of anyoneCanEdit.
         */
        public boolean anyoneCanEdit;

        /**
         * Default constructor.
         */
        public ToggleGroupEditors() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param groupId Identifier of the group.
         * @param anyoneCanEdit New value of anyoneCanEdit.
         */
        public ToggleGroupEditors(int groupId, boolean anyoneCanEdit) {
            this.groupId = groupId;
            this.anyoneCanEdit = anyoneCanEdit;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 970466546;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Removes user from black list.
     */
    public static class UnblockUser extends TLFunction {
        /**
         * User identifier.
         */
        public int userId;

        /**
         * Default constructor.
         */
        public UnblockUser() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param userId User identifier.
         */
        public UnblockUser(int userId) {
            this.userId = userId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -50809642;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Removes pinned message in the supergroup channel. Needs editor privileges in the channel.
     */
    public static class UnpinChannelMessage extends TLFunction {
        /**
         * Identifier of the channel.
         */
        public int channelId;

        /**
         * Default constructor.
         */
        public UnpinChannelMessage() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param channelId Identifier of the channel.
         */
        public UnpinChannelMessage(int channelId) {
            this.channelId = channelId;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = -1650033548;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Installs/uninstalls or enables/archives sticker set. Official sticker set can't be uninstalled, but it can be archived.
     */
    public static class UpdateStickerSet extends TLFunction {
        /**
         * Identifier of the sticker set.
         */
        public long setId;
        /**
         * New value of isInstalled.
         */
        public boolean isInstalled;
        /**
         * New value of isEnabled.
         */
        public boolean isEnabled;

        /**
         * Default constructor.
         */
        public UpdateStickerSet() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param setId Identifier of the sticker set.
         * @param isInstalled New value of isInstalled.
         * @param isEnabled New value of isEnabled.
         */
        public UpdateStickerSet(long setId, boolean isInstalled, boolean isEnabled) {
            this.setId = setId;
            this.isInstalled = isInstalled;
            this.isEnabled = isEnabled;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 64609373;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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

    /**
     * Messages are viewed by the user. Many useful activities depends on message being viewed. For example, marking messages as read, incrementing of view counter, updating of view counter, removing of deleted messages in channels.
     */
    public static class ViewMessages extends TLFunction {
        /**
         * Chat identifier.
         */
        public long chatId;
        /**
         * Identifiers of viewed messages.
         */
        public int[] messageIds;

        /**
         * Default constructor.
         */
        public ViewMessages() {
        }

        /**
         * Constructor for initialization of all fields.
         *
         * @param chatId Chat identifier.
         * @param messageIds Identifiers of viewed messages.
         */
        public ViewMessages(long chatId, int[] messageIds) {
            this.chatId = chatId;
            this.messageIds = messageIds;
        }

        /**
         * Identifier uniquely determining TL-type of the object.
         */
        public static final int CONSTRUCTOR = 1572261384;

        @Override
        /**
         * @return this.CONSTRUCTOR
         */
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
