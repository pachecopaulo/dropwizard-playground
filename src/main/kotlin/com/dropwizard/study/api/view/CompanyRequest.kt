package com.dropwizard.study.api.view

import com.dropwizard.study.model.company.Company
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.validator.constraints.NotEmpty

data class CompanyRequest(
    @JsonProperty
    @NotEmpty
    val name: String,

    @JsonProperty
    @NotEmpty
    val description: String
)
    fun CompanyRequest.toEntity() =
        Company(this.name, this.description)
