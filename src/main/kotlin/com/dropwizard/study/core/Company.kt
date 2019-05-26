package com.dropwizard.study.core

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "company")
data class Company(
    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "description")
    val description: String?,

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    val id: Long = 0
)