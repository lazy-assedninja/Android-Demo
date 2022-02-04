# Specifies the number of optimization passes to be performed. ex. 0 ~ 7
-optimizationpasses 5

# Not to generate mixed-case class names while obfuscating.
-dontusemixedcaseclassnames

# Write out some more information during processing.
-verbose

# Not to shrink the input.
-dontshrink

# Not to optimize the input class files.
-dontoptimize

# Repackage all class files that are renamed, by moving them into the single given package.
# Without argument or with an empty string (''), the package is removed completely.
-repackageclasses ''

# The access modifiers of classes and class members may be broadened during processing.
-allowaccessmodification

# Specifies the optimizations to be enabled and disabled, at a more fine-grained level.
# Not performs peephole optimizations for arithmetic instructions.
-optimizations !code/simplification/arithmetic

# Preserve the line number information for debugging stack traces.
-keepattributes SourceFile, LineNumberTable

# Preserved generics, exceptoins, annotation, InnerClass, permittedSubclasses, EnclosingMethod,
# Deprecated for debugging stack traces.
-keepattributes Signature, Exceptions, *Annotation*, InnerClasses, PermittedSubclasses, EnclosingMethod,
                Deprecated


# Android Component
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.content.Context {
    public void *(android.view.View);
    public void *(android.view.MenuItem);
}

-keepclassmembers class * implements android.os.Parcelable {
    static ** CREATOR;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# Preserved data class
-keep class me.lazy_assedninja.demo.vo.** { *; }

# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { <fields>; }

# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Prevent R8 from leaving Data object members always null
-keepclassmembers, allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}


# To output a full report of all the rules that R8 applies.
-printconfiguration full-r8-config.txt

# Exhaustively list classes and class members matched by the various -keep options
-printseeds seeds.txt

# List dead code of the input class files.
-printusage dead_code.txt

# Print the mapping from old names to new names for classes
# and class members that have been renamed.
-printmapping mapping.txt
