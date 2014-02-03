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

import cz.muni.fi.anatomytutor.backend.dao.impl.AuthUserDaoImplJPA;
import cz.muni.fi.anatomytutor.backend.model.AuthUser;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.*;
import org.hamcrest.text.IsEqualIgnoringCase;

/**
 *
 * @author Jan Kucera
 */
public class UserDaoTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private EntityManager em2;
    private AuthUserDao userDao;
    final static Logger log = LoggerFactory.getLogger(UserDaoTest.class);

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
        userDao = new AuthUserDaoImplJPA(em);
    }

    @After
    public void tearDown() {
        em.close();
        if (em2 != null && em2.isOpen()) {
            em2.close();
        }
        userDao = null;
    }

    @Test
    public void testCreate() {
        AuthUser user = new AuthUser();
        user.setName("Greld");
        user.setEmail("neco@email.com");

        em.getTransaction().begin();
        user = userDao.create(user);
        em.getTransaction().commit();
        assertFalse("User was not created.", user.getId() == null);

        try {
            userDao.create(null);
            fail("No exception thrown with null argument");
        } catch (IllegalArgumentException iae) {
        }

    }

    @Test
    public void testGet() {
        AuthUser user = new AuthUser();
        user.setName("Greld");
        user.setEmail("neco@email.com");
        Long userId;
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            userId = user.getId();
        } catch (Exception ex) {
            throw new RuntimeException("internal integrity error", ex);
        }
        assertNotNull("internal integrity error", userId);

        em2 = emf.createEntityManager();              // kvuli cache lvl 1
        AuthUserDao userDao2 = new AuthUserDaoImplJPA(em2);
        AuthUser testUser = userDao2.get(userId);
        assertEquals(userId, testUser.getId());

        try {
            userDao2.get(userId + 1);
            fail("Should throw exception when non-existent id is entered.");
        } catch (IllegalArgumentException iae) {
        }

    }

    @Test
    public void testUpdate() {
        AuthUser user = new AuthUser();
        user.setName("Greld");
        user.setEmail("neco@email.com");

        Long userId;
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            userId = user.getId();
        } catch (Exception ex) {
            throw new RuntimeException("internal integrity error", ex);
        }
        assertNotNull("internal integrity error", userId);

        user.setName("New name");
        em.getTransaction().begin();
        userDao.update(user);
        em.getTransaction().commit();

        em2 = emf.createEntityManager();              // to avoid returning result from cache lvl 1
        AuthUserDao userDao2 = new AuthUserDaoImplJPA(em2);
        AuthUser testUser = em2.find(AuthUser.class, userId);
        assertThat(testUser.getName(), IsEqualIgnoringCase.equalToIgnoringCase(user.getName()));

        user.setId(userId + 1);
        try {
            userDao.update(user);
            fail("Should throw exception when non-existent id is entered.");
        } catch (IllegalArgumentException iae) {
            try {
                userDao.update(null);
                fail("Should throw exception when null argument is entered.");
            } catch (IllegalArgumentException iaex) {
            }
        }

    }

    @Test
    public void testRemove() {
        AuthUser user = new AuthUser();
        user.setName("Greld");
        user.setEmail("neco@email.com");

        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();

        em2 = emf.createEntityManager();              // to avoid returning result from cache lvl 1
        AuthUserDao userDao2 = new AuthUserDaoImplJPA(em2);
        AuthUser testUser = em2.find(AuthUser.class, user.getId());
        // veryfying, that it is indeed in the database now:
        assertNotNull(testUser);

        em.getTransaction().begin();
        userDao.remove(user.getId());
        em.getTransaction().commit();

        em2.clear();                    // preco nefunguje aj em2.flush (obalene v transakcii)?
        assertNull(em2.find(AuthUser.class, user.getId()));
    }
}
