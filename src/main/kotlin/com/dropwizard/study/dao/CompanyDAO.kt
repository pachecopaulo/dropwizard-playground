package com.dropwizard.study.dao

import com.dropwizard.study.core.Company
import io.dropwizard.hibernate.AbstractDAO
import org.hibernate.SessionFactory

open class CompanyDAO(sessionFactory: SessionFactory?) : AbstractDAO<Company>(sessionFactory) {

    fun findById(id: Long): Company? = get(id)
    open fun create(company: Company): Company = persist(company)
}
