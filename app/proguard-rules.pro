-optimizationpasses 5
-dontpreverify
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
# Ping++
-dontwarn com.alipay.**
-keep class com.alipay.** {*;}
-dontwarn  com.ta.utdid2.**
-keep class com.ta.utdid2.** {*;}
-dontwarn  com.ut.device.**
-keep class com.ut.device.** {*;}
-dontwarn  com.tencent.**
-keep class com.tencent.** {*;}
-dontwarn  com.unionpay.**
-keep class com.unionpay.** {*;}
-dontwarn com.pingplusplus.**
-keep class com.pingplusplus.** {*;}
-dontwarn com.baidu.**
-keep class com.baidu.** {*;}
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
# Bugtags
-keepattributes LineNumberTable,SourceFile
-keep class com.bugtags.library.** {*;}
-dontwarn com.bugtags.library.**
-keep class io.bugtags.** {*;}
-dontwarn io.bugtags.**
-dontwarn org.apache.http.**
-dontwarn android.net.http.AndroidHttpClient
# 保护注解
-keepattributes *Annotation*
# 保持哪些类不被混淆
-keep public @interface *
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
#如果有引用v4包可以添加下面这行
-keep public class * extends android.support.v4.app.Fragment
#忽略警告
-ignorewarning
# v4  v7
-dontwarn android.support.**
# 环信
-keep class com.hyphenate.** {*;}
-keep class org.jivesoftware.** {*;}
-keep class org.apache.** {*;}
-dontwarn  com.hyphenate.**
-keep class com.bhtc.huajuan.utils.EaseSmileUtils {*;}
#保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
#保持自定义控件类不被混淆
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
#保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
  #保持jsonUtil解析混淆
  static java.util.Map filedMap;
  public void init();
}
#不混淆所有的GlideModule。
-keep public class * implements com.bumptech.glide.module.GlideModule
#保持 Serializable 不被混淆
-keepnames class * implements java.io.Serializable
#保持 Serializable 不被混淆并且enum 类也不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
    #保持jsonUtil解析混淆
    static java.util.Map filedMap;
    public void init();
}
#保持所有的bean不混淆、event不混淆
-keep class com.bhtc.huajuan.bean.**{*;}
-keep class com.bhtc.huajuan.event.**{*;}
-keep class com.bhtc.huajuan.module.mine.bean.**{*;}
-keep class com.bhtc.huajuan.module.recommend.bean.**{*;}
-keep class com.bhtc.huajuan.module.live.bean.**{*;}
#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}
# 避免ClassCastException
-keepattributes *Annotation*
-keepattributes JavascriptInterface
-keepattributes Signature
# EventBus 部分不被混淆
-keep class org.greenrobot.eventbus.** {*;}
-keepclassmembers class ** {
    public void onEvent*(**);
    void onEvent*(**);
}
# sharesdk
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-dontwarn cn.sharesdk.**
-dontwarn **.R$*
# 个推
-dontwarn com.igexin.**
-keep class com.igexin.**{*;}
# 友盟
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
#保持Emum类型继承者
-keepclassmembers class * extends java.lang.Enum {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
    #保持jsonUtil解析混淆
    com.bhtc.huajuan.utils.jsonutils.TypeEnum *;
}
#okhttputils
-dontwarn com.zhy.http.**
-keep class com.zhy.http.**{*;}
#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}
#okio
-dontwarn okio.**
-keep class okio.**{*;}
# ActionBarSherlock
-keep class android.support.** { *; }
-keep interface android.support.** { *; }
-keep class com.actionbarsherlock.** { *; }
-keep interface com.actionbarsherlock.** { *; }
# Keep line numbers to alleviate debugging stack traces
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable
-keepclassmembers class * implements java.io.Serializable

#七牛播放
-keep class com.pili.pldroid.player.** { *; }
-keep class tv.danmaku.ijk.media.player.** {*;}
#camera 360
-keep public class us.pinguo.pgskinprettifyengine.**{*;}
-keep public class us.pinguo.prettifyengine.entity.**{*;}
