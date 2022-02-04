-keep public class * {
    public protected *;
}

-keepparameternames
-renamesourcefileattribute SourceFile
-keepattributes Signature, Exceptions, *Annotation*, InnerClasses, PermittedSubclasses, EnclosingMethod,
                Deprecated, SourceFile, LineNumberTable

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}