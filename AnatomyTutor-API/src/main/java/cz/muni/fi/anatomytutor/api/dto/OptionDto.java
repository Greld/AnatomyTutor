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
import java.util.Objects;

/**
 *
 * @author Jan Kucera
 */
public class OptionDto implements Serializable {

    private Long termInPictureId;
    private Long termId;
    private String termName;
    private Integer idInPicture;

    public Long getTermInPictureId() {
        return termInPictureId;
    }

    public void setTermInPictureId(Long termInPictureId) {
        this.termInPictureId = termInPictureId;
    }

    public Long getTermId() {
        return termId;
    }

    public void setTermId(Long termId) {
        this.termId = termId;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public Integer getIdInPicture() {
        return idInPicture;
    }

    public void setIdInPicture(Integer idInPicture) {
        this.idInPicture = idInPicture;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.termId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OptionDto other = (OptionDto) obj;
        if (!Objects.equals(this.termId, other.termId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "OptionDto{" + "termId=" + termId + ", termName=" + termName + ", idInPicture=" + idInPicture + '}';
    }

}
