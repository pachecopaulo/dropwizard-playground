package com.dropwizard.study.dao

import com.dropwizard.study.model.todo.TodoItem
import io.dropwizard.testing.junit.DAOTestRule
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TodoItemDAOTest {

    @Rule @JvmField
    var database: DAOTestRule = DAOTestRule.newBuilder()
        .addEntityClass(TodoItem::class.java)
        .build()

    private lateinit var dao: TodoItemDAO

    @Before
    fun beforeTest() {
        dao = TodoItemDAO(database.sessionFactory)
    }

    /**
     * Tests the relationship between create and findById
     */
    @Test
    fun roundTrip() {
        val item = TodoItem("Trash", "Take out the Trash")
        val id: Long = database.inTransaction(
            fun(): Long? { return dao.create(item).id }
        )!!

        val fetchedItem = dao.findById(id)

        assertThat(
            "Correct database entity should be fetched",
            fetchedItem,
            equalTo(TodoItem( "Trash", "Take out the Trash").copy(id = id)))
    }

    @Test
    fun create_valid() {
        val item = TodoItem("Trash", "Take out the Trash")

        val createdItem: TodoItem = database.inTransaction(
                fun(): TodoItem { return dao.create(item) }
        )

        assertThat("Created item should have non-null id",
                createdItem.id,
                not(nullValue()))
        assertThat("Title should be set properly",
                createdItem.title,
                equalTo(item.title))
        assertThat("Description should be set properly",
                createdItem.description,
                equalTo(item.description))
    }
}