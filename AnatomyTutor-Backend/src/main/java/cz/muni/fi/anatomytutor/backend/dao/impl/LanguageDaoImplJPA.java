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

import cz.muni.fi.anatomytutor.backend.dao.LanguageDao;
import cz.muni.fi.anatomytutor.backend.model.Language;
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
public class LanguageDaoImplJPA implements LanguageDao {

    final static Logger log = LoggerFactory.getLogger(LanguageDaoImplJPA.class);
    // injected from Spring
    @PersistenceContext
    private EntityManager em;

    public LanguageDaoImplJPA() {
    }

    public LanguageDaoImplJPA(EntityManager em) {
        this.em = em;
    }

    public String create(Language language) {
        if (language == null) {
            throw new IllegalArgumentException("Invalid language: null");
        }
        Language createdLanguage = em.merge(language);
        return createdLanguage.getAbbreviation();
    }

    public Language get(String abbreviation) {
        if (abbreviation == null) {
            throw new IllegalArgumentException("Invalid abbreviation: null");
        }
        if (em.createQuery("SELECT tbl.abbreviation FROM Language tbl WHERE tbl.abbreviation = "
                + ":givenAbbr", String.class).setParameter("givenAbbr", abbreviation).getResultList().size() < 1) {
            throw new IllegalArgumentException("Invalid abbreviation: nonexistent");
        }
        return em.createQuery("SELECT tbl FROM Language tbl "
                + "WHERE tbl.abbreviation = :givenAbbr", Language.class).setParameter("givenAbbr", abbreviation).getSingleResult();
    }

    public void update(Language language) {
        if (language == null || language.getAbbreviation() == null) {
            throw new IllegalArgumentException("Invalid language: null or with no abbreviation.");
        } else if (em.createQuery("SELECT tbl.abbreviation FROM Language tbl WHERE tbl.abbreviation = "
                + ":givenAbbr", String.class).setParameter("givenAbbr", language.getAbbreviation()).getResultList().size() < 1) {
            throw new IllegalArgumentException("Invalid language: nonexistent");
        }
        em.merge(language);
    }

    public void remove(String abbreviation) {
        if (abbreviation == null) {
            throw new IllegalArgumentException("Invalid abbreviation: null");
        }
        Language language = em.find(Language.class, abbreviation);
        if (language == null) {
            log.error("Removing language with abbreviation " + abbreviation + " is already not in DB.");
        }
        em.remove(language);
    }

}
