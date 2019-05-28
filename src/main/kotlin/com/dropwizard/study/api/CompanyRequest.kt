package com.dropwizard.study.api

import com.dropwizard.study.core.Company

data class CompanyRequest(
    val name: String = "",
    val description: String? = null
)
    fun CompanyRequest.toEntity() = Company(name, description)
