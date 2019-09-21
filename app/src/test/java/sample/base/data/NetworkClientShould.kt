package sample.base.data

import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.Test
import retrofit2.Retrofit

class NetworkClientShould {

    interface ApiService

    @Test
    fun createInstanceOfService() {
        val client = NetworkClient(
            Retrofit.Builder()
                .baseUrl("http://examp.le")
                .build()
        )

        val apiService = client.create(ApiService::class)

        assertNotNull(apiService)
        assertThat(apiService, instanceOf(ApiService::class.java))
    }
}