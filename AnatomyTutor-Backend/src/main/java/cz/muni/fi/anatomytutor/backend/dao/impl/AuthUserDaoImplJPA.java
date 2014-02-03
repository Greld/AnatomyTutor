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

import cz.muni.fi.anatomytutor.api.dto.SocialNetwork;
import cz.muni.fi.anatomytutor.backend.dao.AuthUserDao;
import cz.muni.fi.anatomytutor.backend.model.AuthUser;
import javax.persistence.EntityManager;
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
public class AuthUserDaoImplJPA extends GenericDaoImpl<AuthUser> implements AuthUserDao {

    final static Logger log = LoggerFactory.getLogger(AuthUserDaoImplJPA.class);

    @PersistenceContext
    private EntityManager em;

    public AuthUserDaoImplJPA() {
        super(AuthUser.class);
    }

    public AuthUserDaoImplJPA(EntityManager em) {
        super(AuthUser.class);
        this.em = em;
    }

    @Override
    public AuthUser getByEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Invalid email: null");
        }
        if (em.createQuery("SELECT tbl.id FROM AuthUser tbl WHERE tbl.email = "
                + ":givenEmail", Long.class).setParameter("givenEmail", email).getResultList().size() < 1) {
            return null;
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
    public AuthUser getUserByIdInSocialNetwork(SocialNetwork socialNetwork, String idInSocialNetwork) {
        if (socialNetwork == null) {
            throw new IllegalArgumentException("Invalid socialNetwork: null");
        }
        if (idInSocialNetwork == null) {
            throw new IllegalArgumentException("Invalid idInSocialNetwork: null");
        }
        if (em.createQuery("SELECT tbl.id FROM AuthUser tbl WHERE tbl.socialNetwork = :socialNetwork "
                + "and tbl.idInSocialNetwork = :givenId", Long.class)
                .setParameter("socialNetwork", socialNetwork)
                .setParameter("givenId", idInSocialNetwork)
                .getResultList().size() < 1) {
            return null;
        }
        return em.createQuery("SELECT tbl FROM AuthUser tbl "
                + "WHERE  tbl.socialNetwork = :socialNetwork and tbl.idInSocialNetwork = :givenId", AuthUser.class)
                .setParameter("socialNetwork", socialNetwork)
                .setParameter("givenId", idInSocialNetwork)
                .getSingleResult();
    }

}
