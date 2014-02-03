package cz.muni.fi.anatomytutor.web;

import cz.muni.fi.anatomytutor.api.UserService;
import cz.muni.fi.anatomytutor.api.dto.AuthUserDto;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Stripes ActionBean for handling user profile operations.
 *
 * @author Jan Kučera
 */
@UrlBinding("/myprofile/{$event}")
public class ProfileActionBean extends BaseActionBean {

    @ValidateNestedProperties(value = {
        @Validate(on = "save", field = "name", required = true),
        @Validate(on = "save", field = "age", required = true),
        @Validate(on = "save", field = "sex", required = true),
        @Validate(on = "save", field = "weightCategory", required = true)
    })
    private AuthUserDto user;
    @SpringBean
    private UserService userService;
    final static Logger log = LoggerFactory.getLogger(ProfileActionBean.class);

    public void setUser(AuthUserDto user) {
        this.user = user;
    }

    public AuthUserDto getUser() {
        return user;
    }
    /*
     @DefaultHandler
     public Resolution show() {
     log.debug("show()");
     user = userService.getById(1L);
     return new ForwardResolution("/profile/show.jsp");
     }
     */

    @DefaultHandler
    public Resolution show() {
        log.debug("show()");
        user = userService.getById(1L);
        return new ForwardResolution("/profile/show.jsp");
    }

    public Resolution edit() {
        log.debug("edit() user {}", user);
        return new ForwardResolution("/profile/edit.jsp");
    }

    public Resolution save() {
        log.debug("save() user {}", user);
        userService.update(user);
        return new RedirectResolution(this.getClass(), "show");
    }

    public Resolution cancel() {
        return new RedirectResolution(this.getClass(), "show");
    }

}
