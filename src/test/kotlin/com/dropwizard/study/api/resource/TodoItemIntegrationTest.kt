package com.dropwizard.study.api.resource

import com.dropwizard.study.config.StudyApplication
import com.dropwizard.study.config.StudyApplicationConfiguration
import com.dropwizard.study.api.view.CreateTodoItem
import com.dropwizard.study.api.view.TodoItemView
import io.dropwizard.client.JerseyClientBuilder
import io.dropwizard.testing.ResourceHelpers
import io.dropwizard.testing.junit.DropwizardAppRule
import org.glassfish.jersey.client.ClientProperties
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.BeforeClass
import org.junit.ClassRule
import org.junit.Test
import javax.ws.rs.client.Client
import javax.ws.rs.client.Entity


class TodoItemIntegrationTest {

    companion object {

        @ClassRule
        @JvmField
        val RULE = DropwizardAppRule<StudyApplicationConfiguration>(
                StudyApplication::class.java,
                ResourceHelpers.resourceFilePath("config.yml")
        )

        @BeforeClass
        @JvmStatic
        fun setUp() {
            client = JerseyClientBuilder(RULE.environment).build("test client")
            client.property(ClientProperties.CONNECT_TIMEOUT, 2000)
            client.property(ClientProperties.READ_TIMEOUT, 2000)
        }

        @JvmStatic
        lateinit var client: Client
    }

    @Test
    fun create_validRoundTrip() {
        // Create a TodoItem
        val createRequest = CreateTodoItem("Trash", "Take out the Trash")
        val createResponse = client.target("http://localhost:${RULE.localPort}/todo/create")
                .request()
                .post(Entity.json(createRequest))
        assertThat("/create response should be 200", createResponse.status, `is`(equalTo(200)))

        val createdTodoItem = createResponse.readEntity(TodoItemView::class.java)

        assertThat("Created item should have id set", createdTodoItem.id, not(nullValue()))
        assertThat("Created item title should be correct",
                createdTodoItem.title,
                `is`(equalTo("Trash")))
        assertThat("Created item description should be correct",
                createdTodoItem.description,
                `is`(equalTo("Take out the Trash")))

        // Find the created item
        val fetchedItem = client.target("http://localhost:${RULE.localPort}/todo/${createdTodoItem.id}")
                .request().get()
                .readEntity(TodoItemView::class.java)

        assertThat("Fetched item should equal the created entity", fetchedItem, `is`(equalTo(createdTodoItem)))
    }

    @Test
    fun create_invalidTitle() {
        // Create a TodoItem
        val createRequest = CreateTodoItem("", "Take out the Trash")
        val createResponse = client.target("http://localhost:${RULE.localPort}/todo/create")
                .request()
                .post(Entity.json(createRequest))
        assertThat("/create response should be 422 (unprocessable entity)", createResponse.status, `is`(equalTo(422)))
    }
}