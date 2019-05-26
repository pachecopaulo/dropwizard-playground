package com.dropwizard.study.resource

import com.dropwizard.study.StudyApplication
import com.dropwizard.study.api.CompanyRequest
import io.dropwizard.testing.ResourceHelpers
import io.dropwizard.testing.junit.DropwizardAppRule
import org.junit.Assert.assertEquals
import org.junit.ClassRule
import org.junit.Test
import javax.ws.rs.client.Client
import javax.ws.rs.client.Entity.entity
import javax.ws.rs.core.MediaType.APPLICATION_JSON
import javax.ws.rs.core.Response
import org.junit.BeforeClass
import io.dropwizard.client.JerseyClientBuilder
import io.dropwizard.testing.ConfigOverride
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(DropwizardExtensionsSupport::class)
class CompanyResourceTest {

    companion object {
        private lateinit var baseURL: String
        private lateinit var wsClient: Client
        private val configPath = ResourceHelpers.resourceFilePath("config-test.yml")

        @ClassRule
        @JvmField
        val RULE = DropwizardAppRule(
            StudyApplication::class.java,
            configPath,
            ConfigOverride.config("database.url", "jdbc:h2:", "")
        )

        @BeforeClass
        @JvmStatic
        fun setup() {
            baseURL = "http://localhost:${RULE.localPort}"
            wsClient = JerseyClientBuilder(RULE.environment).build("test client")
            RULE.newApplication().run("db","migrate", configPath)
        }
    }

    @Test
    fun `create company`() {
        val response = wsClient.target("$baseURL/v1/company/create")
            .request()
            .post(entity(CompanyRequest("Sample", "Description"), APPLICATION_JSON), Response::class.java)

        assertEquals(200, response.status)
    }
}