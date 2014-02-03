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

import cz.muni.fi.anatomytutor.api.dto.CategoryDto;
import cz.muni.fi.anatomytutor.backend.model.Category;

/**
 *
 * @author Jan Kucera
 */
public class CategoryConvert {

    public static Category fromDtoToEntity(CategoryDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("CategoryConvert: fromDtoToEntity: null parameter!");
        }
        Category entity = new Category();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setUrlName(dto.getUrlName());
        return entity;
    }

    public static CategoryDto fromEntityToDto(Category entity) {
        if (entity == null) {
            return null;
        }
        CategoryDto dto = new CategoryDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setUrlName(entity.getUrlName());
        return dto;
    }
}
