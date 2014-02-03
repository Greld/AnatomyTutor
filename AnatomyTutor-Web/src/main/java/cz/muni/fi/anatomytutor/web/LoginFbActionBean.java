package cz.muni.fi.anatomytutor.web;

import cz.muni.fi.anatomytutor.api.LoginService;
import cz.muni.fi.anatomytutor.api.dto.AuthUserDto;
import cz.muni.fi.anatomytutor.api.exception.LoginUnsuccessException;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jan Kucera
 */
@UrlBinding("/loginFB/{$event}")
public class LoginFbActionBean extends BaseActionBean {

    final static org.slf4j.Logger log = LoggerFactory.getLogger(LoginFbActionBean.class);

    private AuthUserDto user;
    @SpringBean
    private LoginService loginService;

    @DefaultHandler
    public Resolution fbLoginDialog() {
        log.debug("fbLoginDialog");
        HttpServletResponse response = getContext().getResponse();
        HttpServletRequest request = getContext().getRequest();
        HttpSession session = request.getSession();
        final String url = "https://www.facebook.com/dialog/oauth?client_id=" + LoginService.FB_APP_ID
                + "&redirect_uri=" + request.getRequestURL().toString() + "/after&state="
                + request.getSession() + "&scope=" + LoginService.FB_PERMISSIONS;
        return new Resolution() {
            @Override
            public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
                response.sendRedirect(url);
            }
        };
    }

    @HandlesEvent("after")
    public Resolution fbAfterLogin() {
        HttpServletRequest request = getContext().getRequest();
        try {
            user = loginService.loginViaFacebook(request.getParameter("code"), request.getRequestURL().toString());
        } catch (LoginUnsuccessException ex) {
            log.error("Login via FB failed: " + ex.getMessage());
            this.getContext().getValidationErrors().addGlobalError(new LocalizableError("login.failed"));
            return new ForwardResolution("/index.jsp");
        }
        log.debug("Login user: " + user);

        if (user != null) {
            setSessionUser(user);
        } else {
            this.getContext().getValidationErrors().addGlobalError(new LocalizableError("login.failed"));
            return new ForwardResolution("/index.jsp");
        }
        return new RedirectResolution(HomeActionBean.class).flash(this);
    }

    @HandlesEvent("logout")
    public Resolution logout() {
        setSessionUser(null);
        return new ForwardResolution("/index.jsp");
    }

    public AuthUserDto getUser() {
        return user;
    }

}
