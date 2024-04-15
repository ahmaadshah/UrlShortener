package service;

import com.HosseiniAhmad.URLShorterner.dto.user.UserRegistrationRequestDto;
import com.HosseiniAhmad.URLShorterner.dto.user.UserResponseDto;
import com.HosseiniAhmad.URLShorterner.mapper.UserMapper;
import com.HosseiniAhmad.URLShorterner.model.entity.user.Role;
import com.HosseiniAhmad.URLShorterner.model.entity.user.User;
import com.HosseiniAhmad.URLShorterner.repository.UserRepository;
import com.HosseiniAhmad.URLShorterner.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        Mockito.reset(userRepository);
    }

    @Test
    public void testCreateUser() {
        UserRegistrationRequestDto requestDto = new UserRegistrationRequestDto();
        requestDto.setUsername("testUser");
        requestDto.setEmail("test@example.com");

        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setEmail("test@example.com");

        UserResponseDto userResponseDto = new UserResponseDto(1L, "test@example.com", "testUser", Role.ROLE_ADMIN, null);
        when(userMapper.toUser(any())).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toUserResponseDto(user)).thenReturn(userResponseDto);

        UserResponseDto result = userService.createUser(requestDto);
        assertEquals(userResponseDto, result);
        verify(userRepository, times(1)).save(any(User.class));
    }


}