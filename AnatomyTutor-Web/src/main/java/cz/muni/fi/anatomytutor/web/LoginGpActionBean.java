/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.anatomytutor.web;

import cz.muni.fi.anatomytutor.api.dto.AuthUserDto;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jan Kucera
 */
@UrlBinding("/loginGP/{$event}")
public class LoginGpActionBean extends BaseActionBean {

    final static org.slf4j.Logger log = LoggerFactory.getLogger(LoginFbActionBean.class);

    private AuthUserDto user;

    @DefaultHandler
    public Resolution gpLoginDialog() {
        log.debug("login via google play start");
        this.getContext().getMessages().add(0, new LocalizableMessage("notImplementedYet"));
        return new ForwardResolution("index.jsp");
    }
}
