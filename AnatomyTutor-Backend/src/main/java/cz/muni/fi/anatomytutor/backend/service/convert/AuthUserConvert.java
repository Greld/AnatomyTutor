package cz.muni.fi.anatomytutor.backend.service.convert;

import cz.muni.fi.anatomytutor.api.dto.AuthUserDto;
import cz.muni.fi.anatomytutor.backend.model.AuthUser;

/**
 * Conversion between AuthUser DTO and entity back and forth.
 *
 * @author Jan Kucera
 */
public class AuthUserConvert {

    public static AuthUser fromDtoToEntity(AuthUserDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("AuthUserConvert: fromDtoToEntity: null parameter!");
        }
        AuthUser authUser = new AuthUser();
        authUser.setId(dto.getId());
        authUser.setName(dto.getName());
        authUser.setEmail(dto.getEmail());
        return authUser;
    }

    public static AuthUserDto fromEntityToDto(AuthUser entity) {
        if (entity == null) {
            throw new IllegalArgumentException("AuthUserConvert: fromEntityToDto: null parameter!");
        }
        AuthUserDto authUserDto = new AuthUserDto();
        authUserDto.setId(entity.getId());
        authUserDto.setName(entity.getName());
        authUserDto.setEmail(entity.getEmail());
        return authUserDto;
    }
}
