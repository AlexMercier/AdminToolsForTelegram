-dontobfuscate
-optimizationpasses 2
-optimizations !code/simplification/arithmetic
-dontwarn
-ignorewarnings



# Appodeal
-keep class com.appodeal.** { *; }
-keep class org.nexage.** { *; }
-keepattributes EnclosingMethod, InnerClasses, Signature, JavascriptInterface

# Amazon
-keep class com.amazon.** { *; }
-dontwarn com.amazon.**

# Mopub
-keep public class com.mopub.**
-keepclassmembers class com.mopub.** { public *; }
-keep class * extends com.mopub.mobileads.CustomEventBanner {}
-keep class * extends com.mopub.mobileads.CustomEventInterstitial {}
-keep class * extends com.mopub.nativeads.CustomEventNative {}
-keep class * extends com.mopub.mobileads.CustomEventRewardedVideo {}
-dontwarn com.mopub.volley.toolbox.**

# Applovin
-keep class com.applovin.** { *; }
-dontwarn com.applovin.**

# Facebook
-keep class com.facebook.ads.** { *; }
-dontwarn com.facebook.ads.**

# Chartboost
-keep class com.chartboost.** { *; }
-dontwarn com.chartboost.**

# Unity Ads
-keepattributes JavascriptInterface
-keepattributes SourceFile,LineNumberTable
-keep class com.unity3d.** { *; }

# Yandex
-keep class com.yandex.metrica.** { *; }
-dontwarn com.yandex.metrica.**
-keep class com.yandex.mobile.ads.** { *; }
-dontwarn com.yandex.mobile.ads.**
-keepattributes *Annotation*

# StartApp
-keep class com.startapp.** { *;}
-dontwarn com.startapp.**
-keepattributes Exceptions, InnerClasses, Signature, Deprecated, SourceFile, LineNumberTable, *Annotation*, EnclosingMethod

# Flurry
-keep class com.flurry.** { *; }
-dontwarn com.flurry.**
-keepattributes *Annotation*,EnclosingMethod,Signature
-keepclasseswithmembers class * {
  public <init>(android.content.Context, android.util.AttributeSet, int);
}

# Avocarrot
-keep class com.avocarrot.** { *; }
-keepclassmembers class com.avocarrot.** { *; }
-dontwarn com.avocarrot.**
-keep public class * extends android.view.View {
  public <init>(android.content.Context);
  public <init>(android.content.Context, android.util.AttributeSet);
  public <init>(android.content.Context, android.util.AttributeSet, int);
  public void set*(...);
}

# Adcolony
-keep class com.jirbo.adcolony.** { *;}
-keep class com.immersion.** { *;}
-dontnote com.immersion.**
-dontwarn android.webkit.**
-dontwarn com.jirbo.adcolony.**

# Vungle
-keep class com.vungle.** { public *; }
-keep class javax.inject.*
-keepattributes *Annotation*, Signature
-keep class dagger.*
-dontwarn com.vungle.**

# MyTarget
-keep class com.my.target.** { *; }
-dontwarn com.my.target.**
-keep class ru.mail.android.mytarget.** { *; }
-dontwarn ru.mail.android.mytarget.**

#Cheetah Mobile
-keep class com.cmcm.** { *; }
-dontwarn com.cmcm.**

# Tapjoy
-keep class com.tapjoy.** { *; }
-dontwarn com.tapjoy.**

# Revmob
-keep class com.revmob.** { *; }
-dontwarn com.revmob.**

# Admob
-keep class com.google.android.gms.ads.** { *; }

# Google
-keep class com.google.android.gms.common.GooglePlayServicesUtil {*;}
-keep class com.google.android.gms.ads.identifier.** { *; }
-dontwarn com.google.android.gms.**

# Legacy
-keep class org.apache.http.** { *; }
-dontwarn org.apache.http.**
-dontwarn android.net.http.**

# Google Play Services library
-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}
-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
  public static final *** NULL;
}
-keepnames class * implements android.os.Parcelable
-keepclassmembers class * implements android.os.Parcelable {
  public static final *** CREATOR;
}
-keep @interface android.support.annotation.Keep
-keep @android.support.annotation.Keep class *
-keepclasseswithmembers class * {
  @android.support.annotation.Keep <fields>;
}
-keepclasseswithmembers class * {
  @android.support.annotation.Keep <methods>;
}
-keep @interface com.google.android.gms.common.annotation.KeepName
-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
  @com.google.android.gms.common.annotation.KeepName *;
}
-keep @interface com.google.android.gms.common.util.DynamiteApi
-keep public @com.google.android.gms.common.util.DynamiteApi class * {
  public <fields>;
  public <methods>;
}
-keep class com.google.android.gms.common.GooglePlayServicesNotAvailableException {*;}
-keep class com.google.android.gms.common.GooglePlayServicesRepairableException {*;}

# Google Play Services library 9.0.0 only
-dontwarn android.security.NetworkSecurityPolicy
-keep public @com.google.android.gms.common.util.DynamiteApi class * { *; }

# support-v4
-keep class android.support.v4.app.Fragment { *; }
-keep class android.support.v4.app.FragmentActivity { *; }
-keep class android.support.v4.app.FragmentManager { *; }
-keep class android.support.v4.app.FragmentTransaction { *; }
-keep class android.support.v4.content.LocalBroadcastManager { *; }
-keep class android.support.v4.util.LruCache { *; }
-keep class android.support.v4.view.PagerAdapter { *; }
-keep class android.support.v4.view.ViewPager { *; }
-keep class android.support.v4.content.ContextCompat { *; }

# support-v7-recyclerview
-keep class android.support.v7.widget.RecyclerView { *; }
-keep class android.support.v7.widget.LinearLayoutManager { *; }
