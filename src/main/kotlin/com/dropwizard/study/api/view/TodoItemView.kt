package com.dropwizard.study.api.view

import com.dropwizard.study.model.todo.TodoItem
import com.fasterxml.jackson.annotation.JsonProperty

data class TodoItemView(
    @JsonProperty val id: Long,
    @JsonProperty val title: String,
    @JsonProperty val description: String?
) {
    constructor(todoItem: TodoItem) : this(todoItem.id, todoItem.title, todoItem.description)
}
