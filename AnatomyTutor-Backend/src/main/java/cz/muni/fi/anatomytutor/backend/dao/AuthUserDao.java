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

import cz.muni.fi.anatomytutor.api.dto.SocialNetwork;
import cz.muni.fi.anatomytutor.backend.model.AuthUser;

/**
 * DAO interface - for operations on the persistence layer on AuthUser entities.
 *
 * @author Jan Kucera
 */
public interface AuthUserDao extends GenericDao<AuthUser> {

    /*
     * Finds user with given email.
     * 
     * @param email Email given at registration (must be unique).
     * @return AuthUser with given email.
     * @throws IllegalArgumentException if parameter is null or invalid
     */
    AuthUser getByEmail(String email);

    /*
     * Verify, if given email is already used by another user
     * @param email
     * @return true if email is alreadz used, false if not
     */
    boolean isEmailAlreadyUsed(String email);

    /**
     * Get user who has already log via this social network with this user id
     * from social network
     *
     * @param socialNetwork
     * @param idInSocialNetwork user id in the social network
     * @return user linked to token for appropriate social network
     */
    AuthUser getUserByIdInSocialNetwork(SocialNetwork socialNetwork, String idInSocialNetwork);
}
