/*
 * Copyright (C) 2014 Jan Kucera
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

import cz.muni.fi.anatomytutor.backend.dao.CategoryDao;
import cz.muni.fi.anatomytutor.backend.model.Category;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDaoImplJPA extends GenericDaoImpl<Category> implements CategoryDao {

    final static Logger log = LoggerFactory.getLogger(CategoryDaoImplJPA.class);

    @PersistenceContext
    private EntityManager em;

    public CategoryDaoImplJPA() {
        super(Category.class);
    }

    public CategoryDaoImplJPA(EntityManager em) {
        super(Category.class);
        this.em = em;
    }

    @Override
    public List<Category> getAll() {
        return em.createQuery("SELECT tbl FROM Category tbl", Category.class).getResultList();
    }

    @Override
    public Category getByUrlName(String urlName) {
        if (urlName == null) {
            throw new IllegalArgumentException("Invalid urlName: null");
        }
        if (em.createQuery("SELECT tbl.id FROM Category tbl WHERE tbl.urlName = "
                + ":urlName", Long.class).setParameter("urlName", urlName).getResultList().size() < 1) {
            return null;
        }
        return em.createQuery("SELECT tbl FROM Category tbl "
                + "WHERE tbl.urlName = :urlName", Category.class).setParameter("urlName", urlName).getSingleResult();
    }

}
