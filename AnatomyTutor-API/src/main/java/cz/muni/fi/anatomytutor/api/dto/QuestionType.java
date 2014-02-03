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
package cz.muni.fi.anatomytutor.api.dto;

import java.io.Serializable;

/**
 *
 * @author Jan Kucera
 */
public enum QuestionType implements Serializable {

    YOU_POINT_AT_MAP(1, "Kde se nachází tato oblast?"),
    I_POINT_AT_MAP(2, "Jak se nazývá zvýrazněná oblast?");

    private final String textVar;
    private final int id;

    private QuestionType(int id, String textVar) {
        this.id = id;
        this.textVar = textVar;
    }

    public String getTextVar() {
        return textVar;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return textVar;
    }
}
