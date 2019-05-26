package com.dropwizard.study.resources

import com.codahale.metrics.annotation.Timed
import com.dropwizard.study.api.CompanyRequest
import com.dropwizard.study.api.toEntity
import com.dropwizard.study.dao.CompanyDAO
import io.dropwizard.hibernate.UnitOfWork
import javax.validation.Valid
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType.APPLICATION_JSON

@Path("/v1/company")
@Produces(APPLICATION_JSON)
class CompanyResource(private val companyDAO: CompanyDAO) {

    @GET
    @Path("/find/{id}")
    @Timed
    fun findCompany(@PathParam("id") id: Long) =
        companyDAO.findById(id)

    @POST
    @Path("/create")
    @Timed
    @UnitOfWork
    fun createCompany(@Valid request: CompanyRequest) =
        companyDAO.create(request.toEntity())
}