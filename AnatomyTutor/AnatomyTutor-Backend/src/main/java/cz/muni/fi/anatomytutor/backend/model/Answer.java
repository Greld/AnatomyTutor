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
package cz.muni.fi.anatomytutor.backend.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Jan Kucera
 */
@Entity
public class Answer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date answerDate;
    private Time answerTime;
    private Integer duration;
    @ManyToOne
    private AuthUser author;
    @ManyToOne
    private Term questionArea;
    @ManyToOne
    private Term answerArea;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(Date answerDate) {
        this.answerDate = answerDate;
    }

    public Time getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Time answerTime) {
        this.answerTime = answerTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public AuthUser getAuthor() {
        return author;
    }

    public void setAuthor(AuthUser author) {
        this.author = author;
    }

    public Term getQuestionArea() {
        return questionArea;
    }

    public void setQuestionArea(Term questionArea) {
        this.questionArea = questionArea;
    }

    public Term getAnswerArea() {
        return answerArea;
    }

    public void setAnswerArea(Term answerArea) {
        this.answerArea = answerArea;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Answer other = (Answer) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Answer{" + "answerDate=" + answerDate + ", answerTime=" + answerTime + ", duration=" + duration + ", author=" + author + ", questionArea=" + questionArea + ", answerArea=" + answerArea + '}';
    }

}
