package me.lazy_assedninja.plugins

object AndroidX {
    val appCompat = "androidx.appcompat:appcompat:${DependenciesVersions.appCompat}"
    val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${DependenciesVersions.constraintLayout}"
    val navigationFragmentKotlin =
        "androidx.navigation:navigation-fragment-ktx:${DependenciesVersions.navigation}"
    val navigationUIKotlin =
        "androidx.navigation:navigation-ui-ktx:${DependenciesVersions.navigation}"
    val recyclerview = "androidx.recyclerview:recyclerview:${DependenciesVersions.recyclerview}"
    val core = "androidx.core:core-ktx:${DependenciesVersions.core}"
    val lifecycleJava8 =
        "androidx.lifecycle:lifecycle-common-java8:${DependenciesVersions.lifecycle}"
    val lifecycleLiveData =
        "androidx.lifecycle:lifecycle-livedata-ktx:${DependenciesVersions.lifecycle}"
    val lifecycleViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${DependenciesVersions.lifecycle}"
    val lifecycleExtensions =
        "androidx.lifecycle:lifecycle-extensions:${DependenciesVersions.lifecycle_arch}"
    val roomCompiler = "androidx.room:room-compiler:${DependenciesVersions.room}"
    val roomKotlin = "androidx.room:room-ktx:${DependenciesVersions.room}"
    val documentFile = "androidx.documentfile:documentfile:${DependenciesVersions.documentFile}"

    val archCoreTesting = "androidx.arch.core:core-testing:${DependenciesVersions.archCore}"
    val testJunit = "androidx.test.ext:junit:${DependenciesVersions.testJunit}"
    val testEspresso = "androidx.test.espresso:espresso-core:${DependenciesVersions.testEspresso}"
}