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
package cz.muni.fi.anatomytutor.api;

import cz.muni.fi.anatomytutor.api.dto.AuthUserDto;
import cz.muni.fi.anatomytutor.api.dto.QuestionDto;

/**
 *
 * @author Jan Kucera
 */
public interface PracticeService {

    /**
     * Return new question for given user.
     *
     * @param user
     * @return new question for given user.
     */
    QuestionDto getQuestion(AuthUserDto user);

    /**
     * Save given answer of given user
     *
     * @param user who anwered
     * @param answer
     */
    void saveAnswer(AuthUserDto user, QuestionDto answer);
}
