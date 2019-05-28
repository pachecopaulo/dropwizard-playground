package com.dropwizard.study.resource

import com.dropwizard.study.core.Company
import com.dropwizard.study.dao.CompanyDAO
import com.dropwizard.study.resources.CompanyResource
import javax.ws.rs.core.MediaType.APPLICATION_JSON
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.ClassRule
import org.junit.Test
import io.dropwizard.testing.junit.ResourceTestRule
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyLong
import javax.ws.rs.client.Entity

@ExtendWith(DropwizardExtensionsSupport::class)
class CompanyResourceTest {

    companion object {
        private val companyDAO = mock<CompanyDAO>()

        @ClassRule
        @JvmField
        val wsClient = ResourceTestRule
            .builder()
            .addResource(CompanyResource(companyDAO))
            .build()!!
    }

    @Test
    fun `find company by id`() {
        whenever(companyDAO.findById(anyLong())).thenReturn(Company("Sample", "Test"))

        val url = "/v1/company/find/10"
        val response = wsClient
            .target(url)
            .request()
            .get(Company::class.java)

        assertNotNull(response)
        assertEquals(response.name, "Sample")
        assertEquals(response.description, "Test")
        verify(companyDAO).findById(anyLong())
    }

    @Test
    fun `create company`() {
        whenever(companyDAO.create(any())).thenReturn(Company("Sample", "Test"))

        val request = """{"name": "Just a title3","description": "Just a description"}"""
        val response = wsClient
            .target("/v1/company/create")
            .request()
            .post(Entity.entity(request, APPLICATION_JSON))

        assertEquals(200, response.status)
        verify(companyDAO).create(any())
    }
}