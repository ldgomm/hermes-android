package com.premierdarkcoffee.sales.hermes

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import dagger.hilt.android.HiltAndroidApp
import java.io.File

@HiltAndroidApp
class HermesApp : Application(), ImageLoaderFactory {

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this).memoryCachePolicy(CachePolicy.ENABLED).memoryCache {
            MemoryCache.Builder(this).maxSizePercent(0.5) // Use up to 50% of available memory
                .build()
        }.diskCachePolicy(CachePolicy.ENABLED).diskCache {
            DiskCache.Builder().directory(File(filesDir, "image_cache")) // More persistent directory
                .maxSizeBytes(50L * 1024 * 1024) // Set a fixed size, e.g., 50MB
                .build()
        }.logger(DebugLogger()).build()
    }
}
