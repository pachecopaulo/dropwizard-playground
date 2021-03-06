package com.dropwizard.study

import com.dropwizard.study.resources.CompanyResource
import com.dropwizard.study.dao.CompanyDAO
import com.dropwizard.study.core.Company
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.dropwizard.Application
import io.dropwizard.db.DataSourceFactory
import io.dropwizard.hibernate.HibernateBundle
import io.dropwizard.migrations.MigrationsBundle
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment

class StudyApplication : Application<StudyApplicationConfiguration>() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            StudyApplication().run(*args)
        }
    }

    private val hibernateBundle = object : HibernateBundle<StudyApplicationConfiguration>(Company::class.java) {
        override fun getDataSourceFactory(configurationStudy: StudyApplicationConfiguration): DataSourceFactory =
            configurationStudy.database
    }

    override fun getName(): String = "Kotlin Todo App"

    override fun initialize(bootstrap: Bootstrap<StudyApplicationConfiguration>) {
        // Allow support for data classes
        bootstrap.objectMapper.registerModule(KotlinModule())

        bootstrap.addBundle(object : MigrationsBundle<StudyApplicationConfiguration>() {
            override fun getDataSourceFactory(configurationStudy: StudyApplicationConfiguration): DataSourceFactory =
                configurationStudy.database
        })

        bootstrap.addBundle(hibernateBundle)
    }

    override fun run(configurationStudy: StudyApplicationConfiguration, environment: Environment) {
        val companyDAO = CompanyDAO(hibernateBundle.sessionFactory)

        environment.jersey().register(CompanyResource(companyDAO))
    }
}
