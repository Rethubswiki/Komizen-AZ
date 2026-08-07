# ProGuard rules for Komizen-AZ
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes Exceptions
-keepclassmembers class * extends java.lang.Enum {
    <fields>;
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
# Retrofit
-keep class retrofit2.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
# Gson
-keep class com.komizen.az.data.model.** { *; }
# Room
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.paging.**
