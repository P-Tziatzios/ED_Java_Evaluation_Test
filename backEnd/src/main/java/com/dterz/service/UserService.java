package com.dterz.service;

import com.dterz.dtos.UserDTO;
import com.dterz.mappers.UserMapper;
import com.dterz.model.User;
import com.dterz.model.UserPrincipal;
import com.dterz.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    /**
     * Gets all the available Users currently in the System
     *
     * @return List<UserDTO>
     */
    public List<UserDTO> getAllUsers() {
        List<User> userList = userRepository.findAll();
        return mapper.entityListToDTOList(userList);
    }

    /**
     * Gets a User by its id
     *
     * @param id the id of the User requested
     * @return UserDTO
     */
    public UserDTO getUserById(long id) {
        User user = userRepository.findById(id).orElse(null);
        return mapper.entityToDto(user);
    }

    /**
     * Updates the User with the data from the Front end
     *
     * @param dto the updated User
     * @return UserDTO
     */
    public UserDTO updateUser(UserDTO dto) {
        User user = userRepository.findById(dto.getId()).orElse(null);
        if (user != null) {
            mapper.dtoToEntity(dto, user);
        } else {
            user = mapper.dtoToEntity(dto);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPass(encoder.encode(dto.getPass()));
        }
        userRepository.save(user);
        return mapper.entityToDto(user);
    }

    /**
     * Deletes an User from the Database
     *
     * @param id the id of the User to delete
     */
    public void deleteUser(long id) {
        userRepository.delete(id);
    }

    /**
     * Creates and returns a new empty User Object
     *
     * @return UserDTO
     */
    public UserDTO createDraftUser() {
        User draftUser = new User();
        return mapper.entityToDto(draftUser);
    }

    /**
     * Gets a User by its Username
     *
     * @param userName the userName of the User requested
     * @return User
     */
    @Override
    public UserPrincipal loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("User does not exist");
        }
        return new UserPrincipal(user);
    }
}

