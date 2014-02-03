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
import java.util.List;

/**
 *
 * @author Jan Kucera
 */
public class QuestionDto implements Serializable {

    private Long pictureId;
    private String pictureName;
    private String pictureSvg;
    private QuestionType questionType;
    private List<OptionDto> options;
    private OptionDto question;
    private OptionDto answer;

    public Long getPictureId() {
        return pictureId;
    }

    public void setPictureId(Long pictureId) {
        this.pictureId = pictureId;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getPictureSvg() {
        return pictureSvg;
    }

    public void setPictureSvg(String pictureSvg) {
        this.pictureSvg = pictureSvg;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public List<OptionDto> getOptions() {
        return options;
    }

    public void setOptions(List<OptionDto> options) {
        this.options = options;
    }

    public OptionDto getQuestion() {
        return question;
    }

    public void setQuestion(OptionDto question) {
        this.question = question;
    }

    public OptionDto getAnswer() {
        return answer;
    }

    public void setAnswer(OptionDto answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "QuestionDto{" + "pictureId=" + pictureId + ", pictureName=" + pictureName + ", pictureSvg=" + pictureSvg + ", questionType=" + questionType + ", options=" + options + ", question=" + question + ", answer=" + answer + '}';
    }

}
