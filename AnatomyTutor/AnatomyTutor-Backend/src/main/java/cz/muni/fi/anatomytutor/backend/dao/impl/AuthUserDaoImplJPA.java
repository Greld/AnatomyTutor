/*
 * Copyright (C) 2013 Jan Kucera
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package cz.muni.fi.anatomytutor.backend.dao.impl;

import cz.muni.fi.anatomytutor.backend.dao.AuthUserDao;
import cz.muni.fi.anatomytutor.backend.model.AuthUser;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * JPA/Hibernate DAO implementation - for operations on the persistence layer on
 * AuthUser entities.
 *
 * @author Jan Kucera
 */
@Repository
public class AuthUserDaoImplJPA implements AuthUserDao {

    final static Logger log = LoggerFactory.getLogger(AuthUserDaoImplJPA.class);
    // injected from Spring
    @PersistenceContext
    private EntityManager em;

    public AuthUserDaoImplJPA() {
    }

    public AuthUserDaoImplJPA(EntityManager em) {
        this.em = em;
    }

    @Override
    public AuthUser getByEmail(String email) throws NoResultException {
        if (email == null) {
            throw new IllegalArgumentException("Invalid email: null");
        }
        if (em.createQuery("SELECT tbl.id FROM AuthUser tbl WHERE tbl.email = "
                + ":givenEmail", Long.class).setParameter("givenEmail", email).getResultList().size() < 1) {
            throw new IllegalArgumentException("Invalid email: nonexistent");
        }
        return em.createQuery("SELECT tbl FROM AuthUser tbl "
                + "WHERE tbl.email = :givenEmail", AuthUser.class).setParameter("givenEmail", email).getSingleResult();
    }

    @Override
    public boolean isEmailAlreadyUsed(String email) {
        TypedQuery<Long> query;
        AuthUser returnedUser;
        query = em.createQuery("SELECT COUNT(tbl.email) FROM AuthUser tbl "
                + " WHERE tbl.email = :uemail", Long.class);
        query.setParameter("uemail", email);
        Long result = query.getSingleResult();
        if (result == 0) {
            return false;
        } else if (result == 1) {
            return true;
        }
        throw new RuntimeException("There are more than one identical email in the database.");
    }

    @Override
    public Long create(AuthUser user) {
        if (user == null) {
            throw new IllegalArgumentException("Invalid user: null");
        }
        AuthUser createdUser = em.merge(user);
        return createdUser.getId();
    }

    @Override
    public AuthUser get(Long id) throws NoResultException {
        if (id == null) {
            throw new IllegalArgumentException("Invalid id: null");
        }
        if (em.createQuery("SELECT tbl.id FROM AuthUser tbl WHERE tbl.id = "
                + ":givenId", Long.class).setParameter("givenId", id).getResultList().size() < 1) {
            throw new IllegalArgumentException("Invalid id: nonexistent");
        }
        return em.createQuery("SELECT tbl FROM AuthUser tbl "
                + "WHERE tbl.id = :givenId", AuthUser.class).setParameter("givenId", id).getSingleResult();
    }

    @Override
    public void update(AuthUser user) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("Invalid user: null or with no id.");
        } else if (em.createQuery("SELECT tbl.id FROM AuthUser tbl WHERE tbl.id = "
                + ":givenId", Long.class).setParameter("givenId", user.getId()).getResultList().size() < 1) {
            throw new IllegalArgumentException("Invalid user: nonexistent");
        }
        em.merge(user);
    }

    @Override
    public void remove(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid id: null");
        }
        AuthUser user = em.find(AuthUser.class, id);
        if (user == null) {
            log.error("Removing user with id " + id + " is already not in DB.");
        }
        em.remove(user);
    }

}
