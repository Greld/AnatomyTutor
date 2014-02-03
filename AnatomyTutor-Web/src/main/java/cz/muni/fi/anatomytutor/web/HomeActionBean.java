/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.anatomytutor.web;

import cz.muni.fi.anatomytutor.api.CategoryService;
import cz.muni.fi.anatomytutor.api.dto.AuthUserDto;
import cz.muni.fi.anatomytutor.api.dto.CategoryDto;
import java.util.List;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jan Kucera
 */
@UrlBinding("/home")
public class HomeActionBean extends BaseActionBean {

    final static org.slf4j.Logger log = LoggerFactory.getLogger(HomeActionBean.class);
    private AuthUserDto user;
    private List<CategoryDto> categories;
    @SpringBean
    private CategoryService categoryService;

    @DefaultHandler
    public Resolution goHome() {
        log.debug("I am at home");
        user = getSessionUser();
        categories = categoryService.getAll();
        return new ForwardResolution("/home.jsp");
    }

    public AuthUserDto getUser() {
        return user;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

}
