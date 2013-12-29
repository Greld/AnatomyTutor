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
package cz.muni.fi.anatomytutor.backend.dao;

import cz.muni.fi.anatomytutor.backend.dao.impl.LanguageDaoImplJPA;
import cz.muni.fi.anatomytutor.backend.model.Language;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hamcrest.text.IsEqualIgnoringCase;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jan Kucera
 */
public class LanguageDaoTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private EntityManager em2;
    private LanguageDao languageDao;
    final static Logger log = LoggerFactory.getLogger(LanguageDaoTest.class);

    @BeforeClass
    public static void setUpOnce() {
        emf = Persistence.createEntityManagerFactory("TestPU");
    }

    @AfterClass
    public static void tearDownOnce() {
        if (emf != null) {
            emf.close();
        }
    }

    @Before
    public void setUp() {   // doporucuje sa per-method inicializaciu robit v setUp radsej ako v Konstruktore
        em = emf.createEntityManager();
        languageDao = new LanguageDaoImplJPA(em);
    }

    @After
    public void tearDown() {
        em.close();
        if (em2 != null && em2.isOpen()) {
            em2.close();
        }
        languageDao = null;
    }

    @Test
    public void testCreate() {
        Language language = new Language();
        language.setName("Čeština");
        language.setAbbreviation("cz");

        em.getTransaction().begin();
        String languageAbbr = languageDao.create(language);
        em.getTransaction().commit();
        assertFalse("Language was not created.", languageAbbr == null);

        try {
            languageDao.create(null);
            fail("No exception thrown with null argument");
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    public void testGet() {
        Language language = new Language();
        language.setName("Deutsch");
        language.setAbbreviation("de");
        String languageAbbr;
        try {
            em.getTransaction().begin();
            em.persist(language);
            em.getTransaction().commit();
            languageAbbr = language.getAbbreviation();
        } catch (Exception ex) {
            throw new RuntimeException("internal integrity error", ex);
        }
        assertNotNull("internal integrity error", languageAbbr);

        em2 = emf.createEntityManager();              // kvuli cache lvl 1
        LanguageDao languageDao2 = new LanguageDaoImplJPA(em2);
        Language testLanguage = languageDao2.get(languageAbbr);
        assertEquals(languageAbbr, testLanguage.getAbbreviation());

        try {
            languageDao2.get("qq");
            fail("Should throw exception when non-existent abbreviation is entered.");
        } catch (IllegalArgumentException iae) {
        }

    }

    @Test
    public void testUpdate() {
        Language language = new Language();
        language.setName("English");
        language.setAbbreviation("en");

        String languageAbbr;
        try {
            em.getTransaction().begin();
            em.persist(language);
            em.getTransaction().commit();
            languageAbbr = language.getAbbreviation();
        } catch (Exception ex) {
            throw new RuntimeException("internal integrity error", ex);
        }
        assertNotNull("internal integrity error", languageAbbr);

        language.setName("New name");
        em.getTransaction().begin();
        languageDao.update(language);
        em.getTransaction().commit();

        em2 = emf.createEntityManager();              // to avoid returning result from cache lvl 1
        LanguageDao languageDao2 = new LanguageDaoImplJPA(em2);
        Language testLanguage = em2.find(Language.class, languageAbbr);
        assertThat(testLanguage.getName(), IsEqualIgnoringCase.equalToIgnoringCase(language.getName()));

        language.setAbbreviation("qq");
        try {
            languageDao.update(language);
            fail("Should throw exception when non-existent abbreviation is entered.");
        } catch (IllegalArgumentException iae) {
            try {
                languageDao.update(null);
                fail("Should throw exception when null argument is entered.");
            } catch (IllegalArgumentException iaex) {
            }
        }

    }

    @Test
    public void testRemove() {
        Language language = new Language();
        language.setName("Polski");
        language.setAbbreviation("po");

        em.getTransaction().begin();
        em.persist(language);
        em.getTransaction().commit();

        em2 = emf.createEntityManager();              // to avoid returning result from cache lvl 1
        LanguageDao languageDao2 = new LanguageDaoImplJPA(em2);
        Language testLanguage = em2.find(Language.class, language.getAbbreviation());
        // veryfying, that it is indeed in the database now:
        assertNotNull(testLanguage);

        em.getTransaction().begin();
        languageDao.remove(language.getAbbreviation());
        em.getTransaction().commit();

        em2.clear();                    // preco nefunguje aj em2.flush (obalene v transakcii)?
        assertNull(em2.find(Language.class, language.getAbbreviation()));

    }
}
