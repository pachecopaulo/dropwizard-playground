package com.dropwizard.study.dao

import com.dropwizard.study.model.company.Company
import io.dropwizard.hibernate.AbstractDAO
import org.hibernate.SessionFactory

class CompanyDAO(sessionFactory: SessionFactory?) : AbstractDAO<Company>(sessionFactory) {

    fun findById(id: Long): Company? = get(id)
    fun create(company: Company): Company = persist(company)
}
