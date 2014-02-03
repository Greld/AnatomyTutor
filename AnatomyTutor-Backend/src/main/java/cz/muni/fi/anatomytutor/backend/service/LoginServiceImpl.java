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

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;
import cz.muni.fi.anatomytutor.api.LoginService;
import cz.muni.fi.anatomytutor.api.dto.AuthUserDto;
import cz.muni.fi.anatomytutor.api.dto.SocialNetwork;
import cz.muni.fi.anatomytutor.api.exception.LoginUnsuccessException;
import cz.muni.fi.anatomytutor.backend.dao.AuthUserDao;
import cz.muni.fi.anatomytutor.backend.model.AuthUser;
import cz.muni.fi.anatomytutor.backend.service.convert.AuthUserConvert;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
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
public class LoginServiceImpl implements LoginService {
    
    final static Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);
    @Autowired
    private AuthUserDao authUserDao;
    
    @Override
    public AuthUserDto loginViaFacebook(String code, String appUrl) throws LoginUnsuccessException {
        String urlStr = "https://graph.facebook.com/oauth/access_token?client_id="
                + LoginService.FB_APP_ID
                + "&redirect_uri=" + appUrl + "&client_secret="
                + LoginService.FB_APP_SECRET + "&code="
                + code;
        log.debug(urlStr);
        String tokenLine;
        try {
            URL url = new URL(urlStr);
            //get token from url
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            tokenLine = br.readLine();
        } catch (MalformedURLException ex) {
            throw new LoginUnsuccessException("Bad URL for recieve access token", ex);
        } catch (IOException ex) {
            throw new LoginUnsuccessException("IO error during getting access token", ex);
        }
        String[] tokens = tokenLine.split("&");
        log.debug(tokens[0]);
        log.debug(tokens[1]);
        
        String[] oAuthTokenS = tokens[0].split("=");
        String token = oAuthTokenS[1];
        log.debug(token);
        JSONObject json;
        JSONObject jsonObject;
        String appId;
        String userIdOnFb;
        try {
            log.debug("pred");
            jsonObject = debugToken(token);
            log.debug(jsonObject.toString());
            json = jsonObject.getJSONObject("data");
            log.debug(json.toString());
            appId = json.getString("app_id");
            userIdOnFb = json.getString("user_id");
        } catch (MalformedURLException ex) {
            throw new LoginUnsuccessException("Bad URL for debug access token", ex);
        } catch (IOException ex) {
            throw new LoginUnsuccessException("IO error during debuging access token", ex);
        } catch (JSONException ex) {
            throw new LoginUnsuccessException("Error during parsing JSON from debug access token", ex);
        }
        if (!LoginService.FB_APP_ID.equals(appId)) {
            throw new LoginUnsuccessException("Bad application id");
        }
        AuthUser authUser = authUserDao.getUserByIdInSocialNetwork(SocialNetwork.FACEBOOK, userIdOnFb);
        log.debug("authUser: " + authUser);
        authUser = syncUserWithFB(authUser, token);
        return AuthUserConvert.fromEntityToDto(authUser);
    }
    
    private JSONObject debugToken(String token) throws MalformedURLException, IOException, JSONException {
        String urlStr = "https://graph.facebook.com/debug_token?input_token="
                + token
                + "&access_token="
                + LoginService.FB_APP_TOKEN;
        log.debug(urlStr);
        String response;
        URL url = new URL(urlStr);
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        response = br.readLine();
        log.debug("response: " + response);
        JSONObject result = new JSONObject(response);
        log.debug("result: " + result.toString());
        return result;
    }
    
    private AuthUser syncUserWithFB(AuthUser authUser, String token) {
        FacebookClient fbClient = new DefaultFacebookClient(token);
        User fbUser = fbClient.fetchObject("/me", User.class);
        if (authUser == null) {
            authUser = new AuthUser();
            authUser.setIdInSocialNetwork(fbUser.getId());
            authUser.setSocialNetwork(SocialNetwork.FACEBOOK);
        }
        /* aktualizace udaju podle facebooku */
        authUser.setAccessToken(token);
        if (fbUser.getEmail() != null) {
            authUser.setEmail(fbUser.getEmail());
        }
        if (fbUser.getName() != null) {
            authUser.setName(fbUser.getName());
        }
        if (authUser.getId() == null) {
            authUserDao.create(authUser);
        } else {
            authUserDao.update(authUser);
        }
        return authUser;
    }
    
}
