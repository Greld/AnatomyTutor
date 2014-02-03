package cz.muni.fi.anatomytutor.backend.service;

import cz.muni.fi.anatomytutor.api.UserService;
import cz.muni.fi.anatomytutor.api.dto.AuthUserDto;
import cz.muni.fi.anatomytutor.api.dto.SocialNetwork;
import cz.muni.fi.anatomytutor.backend.dao.AuthUserDao;
import cz.muni.fi.anatomytutor.backend.model.AuthUser;
import cz.muni.fi.anatomytutor.backend.service.convert.AuthUserConvert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User service for all operations on UserStats DTO.
 *
 * @author Jan Kucera (Greld)
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    // concrete implementation injected by setter from Spring
    @Autowired
    private AuthUserDao authUserDao;

    /*
     @Override
     public Long register(AuthUserDto user) {
     if (user == null) {
     IllegalArgumentException iaex = new IllegalArgumentException("Invalid user in parameter: null");
     log.error("UserServiceImpl.register() called on null parameter: AuthUserDto user", iaex);
     throw iaex;
     }
     final AuthUserDto userDto = user;
     AuthUser entity = AuthUserConvert.fromDtoToEntity(user);
     Long entityId = null;
     try {
     entityId = authUserDao.create(entity);
     } catch (Exception ex) {
     throw new RecoverableDataAccessException("Operation 'register' failed." + ex.getMessage(), ex);
     }
     return entityId;
     }
     */
    @Override
    public AuthUserDto getUserByIdInSocialNetwork(SocialNetwork socialNetwork, String idInSocialNetwork) {
        if (socialNetwork == null) {
            throw new IllegalArgumentException("socialNetwork is null");
        }
        if (idInSocialNetwork == null) {
            throw new IllegalArgumentException("IdInSocialNetwork is null");
        }
        try {
            AuthUser entity = authUserDao.getUserByIdInSocialNetwork(socialNetwork, idInSocialNetwork);
            AuthUserDto dto = AuthUserConvert.fromEntityToDto(entity);
            return dto;
        } catch (Exception ex) {
            throw new RecoverableDataAccessException("Operation 'getById' failed." + ex.getMessage(), ex);
        }
    }

    @Override
    public void update(AuthUserDto dto) {
        if (dto == null || dto.getId() == null) {
            IllegalArgumentException iaex = new IllegalArgumentException("Cannot update user that"
                    + " doesn't exist. Use create instead.");
            log.error("UserServiceImpl.update() called on non-existent entity", iaex);
            throw iaex;
        } else {
            try {
                AuthUser entity = AuthUserConvert.fromDtoToEntity(dto);
                authUserDao.update(entity);
            } catch (Exception ex) {
                throw new RecoverableDataAccessException("Operation 'update' failed." + ex.getMessage(), ex);
            }
        }
    }

    @Override
    public void remove(AuthUserDto dto) {
        if (dto == null || dto.getId() == null) {
            IllegalArgumentException iaex = new IllegalArgumentException("Cannot remove user that"
                    + " doesn't exist.");
            log.error("UserServiceImpl.remove() called on non-existent entity", iaex);
            throw iaex;
        } else {
            try {
                authUserDao.remove(dto.getId());
            } catch (Exception ex) {
                throw new RecoverableDataAccessException("Operation 'remove' failed." + ex.getMessage(), ex);
            }
        }
    }

    @Override
    public AuthUserDto getById(Long id) {
        if (id == null) {
            IllegalArgumentException iaex = new IllegalArgumentException("Invalid id in parameter: null");
            log.error("UserServiceImpl.getById() called on null parameter: Long id", iaex);
            throw iaex;
        }
        try {
            AuthUser entity = authUserDao.get(id);
            AuthUserDto dto = AuthUserConvert.fromEntityToDto(entity);
            return dto;
        } catch (Exception ex) {
            throw new RecoverableDataAccessException("Operation 'getById' failed." + ex.getMessage(), ex);
        }
    }

    @Override
    public AuthUserDto getByEmail(String email) {
        if (email == null) {
            IllegalArgumentException iaex = new IllegalArgumentException("Invalid email in parameter: null");
            log.error("UserServiceImpl.getByEmail() called on null parameter: String email", iaex);
            throw iaex;
        }
        try {
            AuthUser entity = authUserDao.getByEmail(email);
            if (entity == null) {
                return null;
            }
            AuthUserDto dto = AuthUserConvert.fromEntityToDto(entity);
            return dto;
        } catch (Exception ex) {
            throw new RecoverableDataAccessException("Operation 'getByEmail' failed." + ex.getMessage(), ex);
        }
    }

    public void setAuthUserDao(AuthUserDao authUserDao) {
        this.authUserDao = authUserDao;
    }

}
