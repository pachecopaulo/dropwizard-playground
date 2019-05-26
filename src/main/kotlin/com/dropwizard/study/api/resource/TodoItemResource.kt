package com.dropwizard.study.api.resource

import com.codahale.metrics.annotation.Timed
import com.dropwizard.study.api.view.CreateTodoItem
import com.dropwizard.study.api.view.TodoItemView
import com.dropwizard.study.dao.TodoItemDAO
import com.dropwizard.study.model.todo.TodoItem
import io.dropwizard.hibernate.UnitOfWork
import java.util.Optional
import javax.validation.Valid
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType.APPLICATION_JSON

@Path("/todo")
@Produces(APPLICATION_JSON)
class TodoItemResource(private val todoItemDAO: TodoItemDAO) {

    @GET
    @Path("/{id}")
    @Timed
    @UnitOfWork
    fun findItem(@PathParam("id") id: Long): Optional<TodoItemView> {
        val item = todoItemDAO.findById(id)
                ?: return Optional.empty()

        return Optional.of(TodoItemView(item))
    }

    @POST
    @Path("/create")
    @Timed
    @UnitOfWork
    fun createItem(@Valid createItem: CreateTodoItem): TodoItemView {
        val todoItem = TodoItem(createItem.title, createItem.description)

        return TodoItemView(todoItemDAO.create(todoItem))
    }
}
