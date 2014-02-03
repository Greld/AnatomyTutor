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

import cz.muni.fi.anatomytutor.backend.dao.PictureDao;
import cz.muni.fi.anatomytutor.backend.model.Picture;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jan Kucera
 */
@Repository
public class PictureDaoImplJPA extends GenericDaoImpl<Picture> implements PictureDao {

    final static Logger log = LoggerFactory.getLogger(PictureDaoImplJPA.class);

    @PersistenceContext
    private EntityManager em;

    public PictureDaoImplJPA() {
        super(Picture.class);
    }

    public PictureDaoImplJPA(EntityManager em) {
        super(Picture.class);
        this.em = em;
    }
}
