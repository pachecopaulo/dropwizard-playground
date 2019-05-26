package com.dropwizard.study.api.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class CompanyDTO(
    @JsonProperty
    val name: String,

    @JsonProperty
    val content: String
)
