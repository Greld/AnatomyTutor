package cz.muni.fi.anatomytutor.web;

import cz.muni.fi.anatomytutor.api.CategoryService;
import cz.muni.fi.anatomytutor.api.PracticeService;
import cz.muni.fi.anatomytutor.api.dto.AuthUserDto;
import cz.muni.fi.anatomytutor.api.dto.CategoryDto;
import cz.muni.fi.anatomytutor.api.dto.QuestionDto;
import java.util.List;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.ajax.JavaScriptResolution;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jan Kucera
 */
@UrlBinding("/practice/{$event}/{urlName}")
public class PracticeActionBean extends BaseActionBean {

    final static Logger log = LoggerFactory.getLogger(PracticeActionBean.class);
    private AuthUserDto user;
    private CategoryDto category;
    private List<CategoryDto> categories;
    @SpringBean
    private PracticeService practiceService;
    @SpringBean
    private CategoryService categoryService;
    private String urlName;

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    @DefaultHandler
    public Resolution start() {
        user = getSessionUser();
        categories = categoryService.getAll();
        log.debug("PracticeActionBean show: " + urlName);
        if (urlName != null) {
            category = categoryService.getByUrlName(urlName);
            log.debug("categorie in url: " + category.toString());
        }
        return new ForwardResolution("/practice.jsp");
    }

    @HandlesEvent("getQuestion")
    public Resolution getQuestion() {
        QuestionDto question = practiceService.getQuestion(user);
        log.debug(question.toString());
        return new JavaScriptResolution(question);
    }

    public AuthUserDto getUser() {
        return user;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

}
