# Specifies the number of optimization passes to be performed. ex. 0 ~ 7
-optimizationpasses 5

# Not to generate mixed-case class names while obfuscating.
-dontusemixedcaseclassnames

# Write out some more information during processing.
-verbose

# Print any warnings about unresolved references and other important problems,
# but to continue processing in any case.
#-ignorewarning

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
-keepattributes SourceFile,LineNumberTable

# Preserved generics, exceptoins, annotation, InnerClass, permittedSubclasses, EnclosingMethod,
# Deprecated for debugging stack traces.
-keepattributes Signature,Exceptions,*Annotation*,InnerClasses,PermittedSubclasses,EnclosingMethod,Deprecated

# Preserved Android component
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
-keep class me.lazy_assedninja.sample.vo.** { *; }


# Retrofit
# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Keep annotation default values (e.g., retrofit2.http.Field.encoded).
-keepattributes AnnotationDefault

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

# Keep generic signature of Call (R8 full mode strips signatures from non-kept items).
-keep,allowobfuscation,allowshrinking interface retrofit2.Call


# Print Information
# To output a full report of all the rules that R8 applies.
-printconfiguration full-r8-config.txt

# Exhaustively list classes and class members matched by the various -keep options
-printseeds seeds.txt

# List dead code of the input class files.
-printusage dead_code.txt

# Print the mapping from old names to new names for classes
# and class members that have been renamed.
-printmapping mapping.txt
