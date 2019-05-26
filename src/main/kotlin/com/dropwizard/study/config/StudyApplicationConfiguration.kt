package com.dropwizard.study.config

import io.dropwizard.Configuration
import io.dropwizard.db.DataSourceFactory
import javax.validation.Valid
import javax.validation.constraints.NotNull


class StudyApplicationConfiguration : Configuration() {
    @Valid
    @NotNull
    val database = DataSourceFactory()
}
