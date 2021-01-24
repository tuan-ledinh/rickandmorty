package com.tuan.rickandmorty.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.Excludes
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.io.InputStream

@Excludes(OkHttpLibraryGlideModule::class)
@GlideModule
class GlideAppModule : AppGlideModule() {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface GlideAppModuleEntrypoint {
        fun getOkHttpClient(): OkHttpClient
    }

    override fun isManifestParsingEnabled() = false

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)

        val entrypoint = EntryPointAccessors.fromApplication(
            context.applicationContext,
            GlideAppModuleEntrypoint::class.java
        )

        registry.replace(
            GlideUrl::class.java,
            InputStream::class.java,
            OkHttpUrlLoader.Factory(entrypoint.getOkHttpClient())
        )
    }
}