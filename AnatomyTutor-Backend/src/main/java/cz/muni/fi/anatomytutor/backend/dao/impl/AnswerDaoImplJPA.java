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

import cz.muni.fi.anatomytutor.backend.dao.AnswerDao;
import cz.muni.fi.anatomytutor.backend.model.Answer;
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
public class AnswerDaoImplJPA implements AnswerDao {

    final static Logger log = LoggerFactory.getLogger(AnswerDaoImplJPA.class);
    // injected from Spring
    @PersistenceContext
    private EntityManager em;

    public AnswerDaoImplJPA() {
    }

    public AnswerDaoImplJPA(EntityManager em) {
        this.em = em;
    }

    public Long create(Answer answer) {
        if (answer == null) {
            throw new IllegalArgumentException("Invalid answer: null");
        }
        Answer createdAnswer = em.merge(answer);
        return createdAnswer.getId();
    }

    public Answer get(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid id: null");
        }
        if (em.createQuery("SELECT tbl.id FROM Answer tbl WHERE tbl.id = "
                + ":givenId", Long.class).setParameter("givenId", id).getResultList().size() < 1) {
            throw new IllegalArgumentException("Invalid id: nonexistent");
        }
        return em.createQuery("SELECT tbl FROM Answer tbl "
                + "WHERE tbl.id = :givenId", Answer.class).setParameter("givenId", id).getSingleResult();
    }

    public void update(Answer answer) {
        if (answer == null || answer.getId() == null) {
            throw new IllegalArgumentException("Invalid answer: null or with no id.");
        } else if (em.createQuery("SELECT tbl.id FROM Answer tbl WHERE tbl.id = "
                + ":givenId", Long.class).setParameter("givenId", answer.getId()).getResultList().size() < 1) {
            throw new IllegalArgumentException("Invalid answer: nonexistent");
        }
        em.merge(answer);
    }

    public void remove(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid id: null");
        }
        Answer answer = em.find(Answer.class, id);
        if (answer == null) {
            log.error("Removing answer with id " + id + " is already not in DB.");
        }
        em.remove(answer);
    }

}
