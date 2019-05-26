package com.dropwizard.study.model.todo

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "TODO_ITEM")
data class TodoItem(
    @Column(name = "TITLE", nullable = false)
    val title: String,

    @Column(name = "DESCRIPTION")
    val description: String?,

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "TODO_ITEM_ID")
    val id: Long = 0
)