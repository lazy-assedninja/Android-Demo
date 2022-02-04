package me.lazy_assedninja.plugins

@Suppress("unused")
object ThirdParty {

    const val GLIDE = "com.github.bumptech.glide:glide:${DependenciesVersions.GLIDE}"
    const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:${DependenciesVersions.GLIDE}"
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${DependenciesVersions.RETROFIT}"
    const val RETROFIT_GSON = "com.squareup.retrofit2:converter-gson:${DependenciesVersions.RETROFIT}"
    const val OKHTTP_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${DependenciesVersions.OKHTTP}"
    const val TIMBER = "com.jakewharton.timber:timber:${DependenciesVersions.TIMBER}"

    // Test
    const val JUNIT = "junit:junit:${DependenciesVersions.JUNIT}"
    const val MOCKITO_CORE = "org.mockito:mockito-core:${DependenciesVersions.MOCKITO}"
    const val MOCKITO_ANDROID = "org.mockito:mockito-android:${DependenciesVersions.MOCKITO}"
    const val MOCK_WEB_SERVER = "com.squareup.okhttp3:mockwebserver:${DependenciesVersions.MOCK_WEB_SERVER}"
}