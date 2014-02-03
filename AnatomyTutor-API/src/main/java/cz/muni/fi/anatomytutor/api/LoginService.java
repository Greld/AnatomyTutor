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
package cz.muni.fi.anatomytutor.api;

import cz.muni.fi.anatomytutor.api.dto.AuthUserDto;
import cz.muni.fi.anatomytutor.api.exception.LoginUnsuccessException;

/**
 *
 * @author Jan Kucera
 */
public interface LoginService {

    public static final String FB_APP_ID = "1400732600177056";
    public static final String FB_APP_SECRET = "82c1805ded48b115a071b70a18cc5cb7";
    public static final String FB_APP_TOKEN = "1400732600177056|1h_vzRCrJuaMe7kBSADQXmd0DC4";
    //A comma separated list of Permissions to request from the person using your app.
    public static final String FB_PERMISSIONS = "email";

    /**
     * Login guest via Facebook API
     *
     * @param code code recieved from FB login dialog
     * @param appUrl url from where is this request processed
     * @return user
     * @throws cz.muni.fi.anatomytutor.api.exception.LoginUnsuccessException
     */
    AuthUserDto loginViaFacebook(String code, String appUrl) throws LoginUnsuccessException;
}
