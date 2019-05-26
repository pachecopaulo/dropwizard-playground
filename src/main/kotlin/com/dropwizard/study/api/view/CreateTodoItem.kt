package com.dropwizard.study.api.view

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.validator.constraints.NotEmpty

class CreateTodoItem {

        @JsonProperty
        @NotEmpty
        val title: String

        @JsonProperty
        val description: String?

        @JsonCreator
        constructor(
                @JsonProperty("title") title: String,
                @JsonProperty("description") description: String?
        ) {
                this.title = title
                this.description = description
        }
}
