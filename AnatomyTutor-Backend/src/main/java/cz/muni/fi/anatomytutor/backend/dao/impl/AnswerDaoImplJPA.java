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
import org.springframework.stereotype.Repository;

/**
 * JPA/Hibernate DAO implementation - for operations on the persistence layer on
 * Answer entities.
 *
 * @author Jan Kucera
 */
@Repository
public class AnswerDaoImplJPA extends GenericDaoImpl<Answer> implements AnswerDao {

    final static Logger log = LoggerFactory.getLogger(AnswerDaoImplJPA.class);
    // injected from Spring
    @PersistenceContext
    private EntityManager em;

    public AnswerDaoImplJPA() {
        super(Answer.class);
    }

    public AnswerDaoImplJPA(EntityManager em) {
        super(Answer.class);
        this.em = em;
    }

}
