package com.geekbrains.tests.automator

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import com.geekbrains.tests.RES_TOTAL_COUNT
import com.geekbrains.tests.SEARCH_BUTTON_TEXT_VALUE
import com.geekbrains.tests.SEARCH_EDIT_TEXT_VALUE
import com.geekbrains.tests.TEST_RESULT_NUMBER_ZERO
import com.geekbrains.tests.TEST_VALUE_TO_SEARCH
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class BehaviorTests {
    private val uiDevice: UiDevice = UiDevice.getInstance(getInstrumentation())
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val packageName = context.packageName

    @Before
    fun setUp() {
        uiDevice.pressHome()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        context.startActivity(intent)
        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), TIMEOUT)
    }

    @Test
    fun mainActivity_isStarted() {
        val editText = uiDevice.findObject(By.res(packageName, SEARCH_EDIT_TEXT_VALUE))
        Assert.assertNotNull(editText)
    }

    @Test
    fun search_Positive() {
        val editText = uiDevice.findObject(By.res(packageName, SEARCH_EDIT_TEXT_VALUE))
        editText.text = TEST_VALUE_TO_SEARCH
        val searchButton = uiDevice.findObject(By.res(packageName, SEARCH_BUTTON_TEXT_VALUE))
        searchButton.click()
        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, RES_TOTAL_COUNT)),
                TIMEOUT
            )
        Assert.assertEquals(changedText.text.toString(), "Number of results: 687")
    }

    @Test
    fun open_DetailsScreen() {
        val detailsScreen = uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))
        detailsScreen.click()
        val changedText = uiDevice.wait(
            Until.findObject(By.res(packageName, RES_TOTAL_COUNT)), TIMEOUT
        )
        Assert.assertEquals(changedText.text, TEST_RESULT_NUMBER_ZERO)
    }

    @Test
    fun empty_field(){
        val searchButton = uiDevice.findObject(By.res(packageName, SEARCH_BUTTON_TEXT_VALUE))
        searchButton.click()
        val changedText = uiDevice.wait(
            Until.findObject(By.res(packageName, RES_TOTAL_COUNT)), TIMEOUT
        )
        Assert.assertNull(changedText)
    }

    @Test
    fun test_Result_DetailsScreen(){
        val editText = uiDevice.findObject(By.res(packageName, SEARCH_EDIT_TEXT_VALUE))
        editText.text = TEST_VALUE_TO_SEARCH
        val searchButton = uiDevice.findObject(By.res(packageName, SEARCH_BUTTON_TEXT_VALUE))
        searchButton.click()
        val detailsScreen = uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))
        detailsScreen.click()
        val changedText = uiDevice.wait(
            Until.findObject(By.res(packageName, RES_TOTAL_COUNT)), TIMEOUT
        )
        Assert.assertEquals(changedText.text, TEST_RESULT_NUMBER_ZERO)
    }

    companion object {
        private const val TIMEOUT = 5000L
    }
}