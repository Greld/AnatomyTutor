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

import cz.muni.fi.anatomytutor.backend.dao.AreaDao;
import cz.muni.fi.anatomytutor.backend.model.Area;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JPA/Hibernate DAO implementation - for operations on the persistence layer on
 * Answer entities.
 *
 * @author Jan Kucera
 */
public class AreaDaoImplJPA implements AreaDao {

    final static Logger log = LoggerFactory.getLogger(AreaDaoImplJPA.class);
    // injected from Spring
    @PersistenceContext
    private EntityManager em;

    public AreaDaoImplJPA() {
    }

    public AreaDaoImplJPA(EntityManager em) {
        this.em = em;
    }

    public Long create(Area area) {
        if (area == null) {
            throw new IllegalArgumentException("Invalid area: null");
        }
        Area createdArea = em.merge(area);
        return createdArea.getId();
    }

    public Area get(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid id: null");
        }
        if (em.createQuery("SELECT tbl.id FROM Area tbl WHERE tbl.id = "
                + ":givenId", Long.class).setParameter("givenId", id).getResultList().size() < 1) {
            throw new IllegalArgumentException("Invalid id: nonexistent");
        }
        return em.createQuery("SELECT tbl FROM Area tbl "
                + "WHERE tbl.id = :givenId", Area.class).setParameter("givenId", id).getSingleResult();
    }

    public void update(Area area) {
        if (area == null || area.getId() == null) {
            throw new IllegalArgumentException("Invalid area: null or with no id.");
        } else if (em.createQuery("SELECT tbl.id FROM Area tbl WHERE tbl.id = "
                + ":givenId", Long.class).setParameter("givenId", area.getId()).getResultList().size() < 1) {
            throw new IllegalArgumentException("Invalid area: nonexistent");
        }
        em.merge(area);
    }

    public void remove(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid id: null");
        }
        Area area = em.find(Area.class, id);
        if (area == null) {
            log.error("Removing area with id " + id + " is already not in DB.");
        }
        em.remove(area);
    }

}
