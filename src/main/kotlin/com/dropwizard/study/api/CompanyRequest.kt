package com.dropwizard.study.api

import com.dropwizard.study.core.Company
import com.fasterxml.jackson.annotation.JsonProperty

data class CompanyRequest(
    @JsonProperty
    val name: String,

    @JsonProperty
    val description: String
)
    fun CompanyRequest.toEntity() =
        Company(name, description)
