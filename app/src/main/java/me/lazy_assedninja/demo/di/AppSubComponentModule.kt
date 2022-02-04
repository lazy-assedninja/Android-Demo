package me.lazy_assedninja.demo.di

import dagger.Module
import me.lazy_assedninja.demo.ui.index.MainComponent

/**
 * This module tells a Component which are its SubComponents
 */
@Module(subcomponents = [MainComponent::class])
class AppSubComponentModule