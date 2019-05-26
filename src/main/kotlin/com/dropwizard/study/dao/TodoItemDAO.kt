package com.dropwizard.study.dao

import com.dropwizard.study.model.todo.TodoItem
import io.dropwizard.hibernate.AbstractDAO
import org.hibernate.SessionFactory

class TodoItemDAO(sessionFactory: SessionFactory?) : AbstractDAO<TodoItem>(sessionFactory) {

    fun findById(id: Long): TodoItem? = get(id)
    fun create(item: TodoItem): TodoItem = persist(item)
}
