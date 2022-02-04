package me.lazy_assedninja.demo.ui.demo.di

import javax.inject.Scope

/**
 * Applies to a class containing an injectable constructor and governs how the injector reuses
 * instances of the type.
 */
@Scope
@MustBeDocumented
@Retention(value = AnnotationRetention.RUNTIME)
annotation class DemoScope