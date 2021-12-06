package com.geekbrains.tests

import android.content.Context
import android.os.Build
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.view.search.MainActivity
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class MainActivityTests {
    private lateinit var scenario: ActivityScenario<MainActivity>
    private lateinit var context: Context
    @Before
    fun setUp(){
        scenario = ActivityScenario.launch(MainActivity::class.java)
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun isActivityIsNotNull(){
        scenario.onActivity {
            Assert.assertNotNull(it)
        }
    }

    @Test
    fun detailsActivityIsVisibleTest(){
        scenario.onActivity {
            val detailsButton = it.findViewById<Button>(R.id.toDetailsActivityButton)
            Assert.assertTrue(detailsButton.isVisible)
        }
    }
    @Test
    fun searchEditTextIsVisibleTest(){
        scenario.onActivity {
            val searchEditText = it.findViewById<EditText>(R.id.searchEditText)
            Assert.assertTrue(searchEditText.isVisible)
        }
    }
    @Test
    fun searchEditTextTest(){
        scenario.onActivity {
            val searchEditText = it.findViewById<EditText>(R.id.searchEditText)
            searchEditText.setText("text", TextView.BufferType.EDITABLE)
            Assert.assertEquals("text", searchEditText.text.toString())
        }
    }

    @After
    fun close(){
        scenario.close()
    }
}