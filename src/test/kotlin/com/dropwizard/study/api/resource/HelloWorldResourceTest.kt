package com.dropwizard.study.api.resource

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.dropwizard.testing.junit.ResourceTestRule
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.equalTo
import org.junit.ClassRule
import org.junit.Test

class HelloWorldResourceTest {

    companion object {

        @ClassRule
        @JvmField
        val resources: ResourceTestRule = ResourceTestRule.builder()
                .setMapper(ObjectMapper().registerModule(KotlinModule()))
                .addResource(HelloWorldResource())
                .build()
    }

    @Test
    fun hello_withName() {
        val response = resources.target("/hello-world")
                .queryParam("name", "John")
                .request().get()

        assertThat("Response should be 200 (Ok)", response.status, `is`(equalTo(200)))

        val saying = response.readEntity(Saying::class.java)
        assertThat("Name should be used in greeting", saying.content, `is`(equalTo("Hello, John!")))
    }

    @Test
    fun hello_withoutName() {
        val response = resources.target("/hello-world")
                .request().get()

        assertThat("Response should be 200 (Ok)", response.status, `is`(equalTo(200)))

        val saying = response.readEntity(Saying::class.java)
        assertThat("Default greeting should be used", saying.content, `is`(equalTo("Hello, world!")))
    }
}