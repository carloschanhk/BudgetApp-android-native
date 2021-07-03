package com.example.budget.module

import android.app.Activity
import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.budget.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped

@InstallIn(ActivityComponent::class)
@Module
object NavigationModule {
    @Provides
    @ActivityScoped
    fun provideNavController(@ActivityContext context: Context): NavController{
        return Navigation.findNavController(context as Activity,R.id.nav_graph)
    }
}