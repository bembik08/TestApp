package com.geekbrains.tests.automator

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.UiDevice
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 23)
class InitialTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val packageName = context.packageName

    @Test
    fun device_NotNull(){
        val uiDevice : UiDevice = UiDevice.getInstance(getInstrumentation())
        Assert.assertNotNull(uiDevice)
    }
   @Test
    fun appPackage_NotNull(){
        Assert.assertNotNull(packageName)
    }
}