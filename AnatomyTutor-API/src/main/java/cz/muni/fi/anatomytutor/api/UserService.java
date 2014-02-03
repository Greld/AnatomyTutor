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
package cz.muni.fi.anatomytutor.api;

import cz.muni.fi.anatomytutor.api.dto.AuthUserDto;
import cz.muni.fi.anatomytutor.api.dto.SocialNetwork;

/**
 * User service interface for operations on User DTO.
 *
 * @author Jan Kucera
 */
public interface UserService {

    /**
     * Update user
     *
     * @param user
     */
    void update(AuthUserDto user);

    /**
     * Remove user
     *
     * @param user
     */
    void remove(AuthUserDto user);

    /**
     * Find user by id
     *
     * @param id
     * @return user with given id.
     */
    AuthUserDto getById(Long id);

    /**
     * Find user by email
     *
     * @param email
     * @return user with given email.
     */
    AuthUserDto getByEmail(String email);

    /**
     * Find user by id in appropriate social network
     *
     * @param socialNetwork social network to which user is log in
     * @param idInSocialNetwork user id in social network
     * @return user
     */
    AuthUserDto getUserByIdInSocialNetwork(SocialNetwork socialNetwork, String idInSocialNetwork);
}
