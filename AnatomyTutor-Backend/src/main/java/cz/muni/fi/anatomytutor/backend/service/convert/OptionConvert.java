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
package cz.muni.fi.anatomytutor.backend.service.convert;

import cz.muni.fi.anatomytutor.api.dto.OptionDto;
import cz.muni.fi.anatomytutor.backend.model.Picture;
import cz.muni.fi.anatomytutor.backend.model.Term;
import cz.muni.fi.anatomytutor.backend.model.TermInPicture;

/**
 *
 * @author Jan Kucera
 */
public class OptionConvert {

    public static TermInPicture fromDtoToEntity(OptionDto dto, Picture picture) {
        if (dto == null) {
            throw new IllegalArgumentException("OptionConvert: fromDtoToEntity: null parameter!");
        }
        Term term = new Term();
        term.setId(dto.getTermId());
        term.setName(dto.getTermName());

        TermInPicture termInPicture = new TermInPicture();
        termInPicture.setTerm(term);
        termInPicture.setId(dto.getTermInPictureId());
        termInPicture.setIdInPicture(dto.getIdInPicture());
        termInPicture.setPicture(picture);
        return termInPicture;
    }

    public static OptionDto fromEntityToDto(TermInPicture entity) {
        if (entity == null) {
            throw new IllegalArgumentException("OptionConvert: fromEntityToDto: null parameter!");
        }
        OptionDto optionDto = new OptionDto();
        optionDto.setIdInPicture(entity.getIdInPicture());
        optionDto.setTermId(entity.getTerm().getId());
        optionDto.setTermName(entity.getTerm().getName());
        optionDto.setTermInPictureId(entity.getId());
        return optionDto;
    }
}
