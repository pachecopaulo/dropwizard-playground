package com.dropwizard.study.api.resource

import com.dropwizard.study.api.view.TodoItemView
import com.dropwizard.study.dao.TodoItemDAO
import com.dropwizard.study.model.todo.TodoItem
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.dropwizard.testing.junit.ResourceTestRule
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.ClassRule
import org.junit.Test
import org.mockito.Mockito.*

class TodoItemResourceTest {

    companion object {

        @JvmStatic
        private val todoItemDAO = mock(TodoItemDAO::class.java)

        @ClassRule
        @JvmField
        val resources: ResourceTestRule = ResourceTestRule.builder()
                .setMapper(ObjectMapper().registerModule(KotlinModule()))
                .addResource(TodoItemResource(todoItemDAO))
                .build()
    }

    @After
    fun tearDown() {
        // Reset mocks between tests
        reset(todoItemDAO)
    }

    @Test
    fun findItem_valid() {
        val item = TodoItem("Trash", "Take out the trash")
        doReturn(item).`when`(todoItemDAO).findById(1L)

        val response = resources.target("/todo/1")
            .request().get()

        assertThat(response.status, `is`(equalTo(200)))

        val responseItemView = response.readEntity(TodoItemView::class.java)
        val expectedView = TodoItemView(1L, "Trash", "Take out the trash")
        assertThat("Returned view should equal the database entity", responseItemView, `is`(equalTo(expectedView)))
    }

    @Test
    fun findItem_invalid() {
        val response = resources.target("/todo/2")
                .request().get()
        assertThat(response.status, `is`(equalTo(404)))
    }
}