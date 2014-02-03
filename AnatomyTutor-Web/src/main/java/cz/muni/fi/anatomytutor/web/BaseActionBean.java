package cz.muni.fi.anatomytutor.web;

import cz.muni.fi.anatomytutor.api.dto.AuthUserDto;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import org.apache.taglibs.standard.functions.Functions;

/**
 * Base actionBean implementing the required methods for setting and getting
 * context.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
public abstract class BaseActionBean implements ActionBean {

    private ActionBeanContext context;

    @Override
    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    @Override
    public ActionBeanContext getContext() {
        return context;
    }

    public static String escapeHTML(String s) {
        return Functions.escapeXml(s);
    }

    protected AuthUserDto getSessionUser() {
        return (AuthUserDto) getContext().getRequest().getSession().getAttribute("user");
    }

    protected void setSessionUser(AuthUserDto user) {
        getContext().getRequest().getSession().setAttribute("user", user);
    }
}
