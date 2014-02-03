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

import cz.muni.fi.anatomytutor.api.PracticeService;
import cz.muni.fi.anatomytutor.api.dto.AuthUserDto;
import cz.muni.fi.anatomytutor.api.dto.OptionDto;
import cz.muni.fi.anatomytutor.api.dto.QuestionDto;
import cz.muni.fi.anatomytutor.api.dto.QuestionType;
import cz.muni.fi.anatomytutor.backend.dao.AnswerDao;
import cz.muni.fi.anatomytutor.backend.dao.PictureDao;
import cz.muni.fi.anatomytutor.backend.dao.TermInPictureDao;
import cz.muni.fi.anatomytutor.backend.model.Answer;
import cz.muni.fi.anatomytutor.backend.model.Picture;
import cz.muni.fi.anatomytutor.backend.model.TermInPicture;
import cz.muni.fi.anatomytutor.backend.service.convert.AuthUserConvert;
import cz.muni.fi.anatomytutor.backend.service.convert.OptionConvert;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
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
public class PracticeServiceImpl implements PracticeService {

    final static Logger log = LoggerFactory.getLogger(PracticeServiceImpl.class);
    // concrete implementation injected by setter from Spring
    @Autowired
    private TermInPictureDao termInPictureDao;
    @Autowired
    private PictureDao pictureDao;
    @Autowired
    private AnswerDao answerDao;

    @Override
    public QuestionDto getQuestion(AuthUserDto user) {
        /* Vyber obrázek */
        Picture picture = pictureDao.get(1L);

        /* Vyber pojem na obrázku */
        TermInPicture termInPicture = termInPictureDao.get((new Random()).nextInt(18) + 1L);

        QuestionDto dto = new QuestionDto();
        dto.setPictureId(picture.getId());
        dto.setPictureName(picture.getName());
        dto.setPictureSvg(picture.getSvg());

        OptionDto correctOption = new OptionDto();
        correctOption.setIdInPicture(termInPicture.getIdInPicture());
        correctOption.setTermId(termInPicture.getTerm().getId());
        correctOption.setTermName(termInPicture.getTerm().getName());
        dto.setQuestion(correctOption);
        dto.setQuestionType(QuestionType.YOU_POINT_AT_MAP);
        dto.setOptions(new ArrayList<OptionDto>());
        return dto;
    }

    @Override
    public void saveAnswer(AuthUserDto user, QuestionDto answer) {
        Answer answerEntity = new Answer();
        answerEntity.setAuthor(AuthUserConvert.fromDtoToEntity(user));
        Picture picture = new Picture();
        picture.setId(answer.getPictureId());
        picture.setName(answer.getPictureName());
        picture.setSvg(answer.getPictureSvg());
        answerEntity.setQuestionTerm(OptionConvert.fromDtoToEntity(answer.getQuestion(), picture));
        answerEntity.setAnswerTerm(OptionConvert.fromDtoToEntity(answer.getAnswer(), picture));
        Long now = System.currentTimeMillis();
        answerEntity.setAnswerTime(new Time(now));
        answerEntity.setAnswerDate(new Date(now));
        answerDao.create(answerEntity);
    }

}
