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
package cz.muni.fi.anatomytutor.backend.service;

import cz.muni.fi.anatomytutor.api.CategoryService;
import cz.muni.fi.anatomytutor.api.dto.CategoryDto;
import cz.muni.fi.anatomytutor.backend.dao.CategoryDao;
import cz.muni.fi.anatomytutor.backend.model.Category;
import cz.muni.fi.anatomytutor.backend.service.convert.CategoryConvert;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jan Kucera
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    final static Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<CategoryDto> getAll() {
        List<Category> categories = categoryDao.getAll();
        List<CategoryDto> dtos = new ArrayList<>();
        for (Category category : categories) {
            dtos.add(CategoryConvert.fromEntityToDto(category));
        }
        return dtos;
    }

    @Override
    public CategoryDto getByUrlName(String urlName) {
        return CategoryConvert.fromEntityToDto(categoryDao.getByUrlName(urlName));
    }

}
