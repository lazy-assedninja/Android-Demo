package me.lazy_assedninja.sample.di

import dagger.Module
import me.lazy_assedninja.sample.ui.index.MainComponent

// This module tells a Component which are its SubComponents
@Module(subcomponents = [MainComponent::class])
class SubComponentModule