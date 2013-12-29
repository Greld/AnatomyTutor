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

import cz.muni.fi.anatomytutor.backend.model.Language;

/**
 *
 * @author Jan Kucera
 */
public interface LanguageDao {
    /*  Create new language
     * @throws IllegalArgumentException if parameter is null or invalid
     */

    String create(Language language);

    /* Return language by abbreviation
     * @throws IllegalArgumentException if parameter is null or invalid
     */
    Language get(String abbreviation);

    /* Update the language
     * @throws IllegalArgumentException if parameter is null, invalid or non-existent in the DB
     */
    void update(Language language);

    /* Remove language with given abbreviation
     * @throws IllegalArgumentException if parameter is null or invalid. Does not throw this exception if
     * parameter is valid but given entity is nonexistent. 
     */
    void remove(String abbreviation);
}
