package com.roberta.movilepay_android_interview.ui.fragment


import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.roberta.movilepay_android_interview.R
import com.roberta.movilepay_android_interview.cards.FileReader
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListCardsFragmentTest{
    private val server = MockWebServer()

    @Before
    fun setUp() {
        server.start(8080)
        IdlingRegistry.getInstance().register( OkHttp3IdlingResource.create("okhttp", getClient()) as IdlingResource)

    }

    fun getClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Test
    fun must_display_list_when_api_return_success(){
        server.dispatcher = object : Dispatcher() {
            override  fun  dispatch (request: RecordedRequest) : MockResponse {
                return MockResponse()
                    .setResponseCode( 200 )
                    .setBody(FileReader.readStringFromFile( "success_response.json" ))
            }
        }

        launchFragmentInContainer<ListCardsFragment>()

            Espresso.onView(withId(R.id.cards_list_recyclerview))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    @After
    fun tearDown(){
        server.close()
    }
}