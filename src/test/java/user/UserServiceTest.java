package user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.exceptions.EmailAlreadyExists;
import com.example.exceptions.InvalidPassword;
import com.example.exceptions.UsernameAlreadyExists;
import com.example.role.Role;
import com.example.role.RoleRepository;
import com.example.store.Store;
import com.example.userInfo.UserInfo;
import com.example.userInfo.UserInfoRepository;
import com.example.userInfo.UserInfoService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserInfoRepository userInfoRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    // @InjectMocks
    UserInfoService userInfoService;

    @BeforeEach
    void setUp() {
        userInfoService = new UserInfoService(userInfoRepository, passwordEncoder, roleRepository);
    }

    // add

    @Test
    void canAddUser() throws UsernameAlreadyExists, EmailAlreadyExists, InvalidPassword {

        // given
        Set<Role> roles = new HashSet<>();
        Set<Store> stores = new HashSet<>();
        UserInfo user = new UserInfo(null, "email", "username", "password", roles, stores);

        // when
        userInfoService.addUser(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // then
        ArgumentCaptor<UserInfo> userArgumentCaptor = ArgumentCaptor.forClass(UserInfo.class);

        verify(userInfoRepository).save(userArgumentCaptor.capture());

        UserInfo capturedUser = userArgumentCaptor.getValue();
        assertEquals(capturedUser, user);

        // invalid email
        // UserInfo user2 = setUp("email", "username1", "password", null, null);
        // // invalid password
        // UserInfo user3 = setUp("email1", "username2", "pas", null, null);
        // // invaid username
        // UserInfo user4 = setUp("email2", "username", "password", null, null);
        // // when

    }

    // get

    @Test
    void canGetUser() {

        // given
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);

        BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));
        user.setPassword("password");
        // when
        UserInfo u = userInfoService.getUser(user.getUsername());
        user.setPassword("***********");
        // then
        ArgumentCaptor<String> userArgumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(userInfoRepository).findByUsername(userArgumentCaptor.capture());
        String hold = userArgumentCaptor.getValue();
        assertEquals(hold, user.getUsername());
        assertEquals(u, user);

    }

    @Test
    void canNotGetUserInvalidUser() {

        // given
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);

        BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.empty());

        // when
        // then
        Exception e = assertThrows(UsernameNotFoundException.class, () -> {
            userInfoService.getUser(user.getUsername());
        }, "Username username already exists");
        assertEquals(e.getMessage(), "Username username already exists");

    }

    // update

    @Test
    void canUpdateUser() throws UsernameAlreadyExists, EmailAlreadyExists, InvalidPassword {

        // given
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);

        // when
        userInfoService.updateUser(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // then
        ArgumentCaptor<UserInfo> userArgumentCaptor = ArgumentCaptor.forClass(UserInfo.class);

        verify(userInfoRepository).save(userArgumentCaptor.capture());

        UserInfo capturedUser = userArgumentCaptor.getValue();
        assertEquals(capturedUser, user);
    }

    // delete

    @Test
    void canDeleteUser() {
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);

        BDDMockito.given(userInfoRepository.existsByUsername(user.getUsername())).willReturn(true);
        // when
        userInfoService.deleteUser(user);

        // then
        ArgumentCaptor<UserInfo> userArgumentCaptor = ArgumentCaptor.forClass(UserInfo.class);

        verify(userInfoRepository).delete(userArgumentCaptor.capture());

        UserInfo capturedUser = userArgumentCaptor.getValue();
        assertEquals(capturedUser, user);
    }

    @Test
    void canNotDeleteUserUsernameNotFound() {

        // given
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);

        BDDMockito.given(userInfoRepository.existsByUsername(user.getUsername())).willReturn(false);

        // when
        // then
        Exception e = assertThrows(UsernameNotFoundException.class, () -> {
            userInfoService.deleteUser(user);
        }, "Username username not found");
        assertEquals(e.getMessage(), "Username username not found");
    }

    // helper

    @Test
    void canValidateUerInfo() {
        UserInfo user = new UserInfo(1, "email", "username", "passsword", null, null);
        Boolean v = null;

        BDDMockito.given(userInfoRepository.existsByUsername(user.getUsername())).willReturn(false);
        BDDMockito.given(userInfoRepository.existsByEmail(user.getEmail())).willReturn(false);

        // when
        try {
            v = userInfoService.validateUserInfo(user);
        } catch (Exception e) {
            // TODO: handle exception
        }

        // then
        assertEquals(true, v);
    }

    @Test
    void canNotValidateUserInfoUsernameAlreadyExists() {

        // given
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);

        BDDMockito.given(userInfoRepository.existsByUsername(user.getUsername())).willReturn(true);

        // when
        // then
        Exception e = assertThrows(UsernameAlreadyExists.class, () -> {
            userInfoService.validateUserInfo(user);
        }, "Username username already exists");
        assertEquals(e.getMessage(), "Username username already exists");
    }

    @Test
    void canNotValidateUserInfoEmailAlreadyExists() {

        // given
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);

        BDDMockito.given(userInfoRepository.existsByUsername(user.getUsername())).willReturn(false);
        BDDMockito.given(userInfoRepository.existsByEmail(user.getEmail())).willReturn(true);

        // when
        // then
        Exception e = assertThrows(EmailAlreadyExists.class, () -> {
            userInfoService.validateUserInfo(user);
        }, "Email email already exists");
        assertEquals(e.getMessage(), "Email email already exists");
    }

    @Test
    void canNotValidateUserInfoPasswordTooShort() {

        // given
        UserInfo user = new UserInfo(1, "email", "username", "pas", null, null);

        BDDMockito.given(userInfoRepository.existsByUsername(user.getUsername())).willReturn(false);
        BDDMockito.given(userInfoRepository.existsByEmail(user.getEmail())).willReturn(false);

        // when
        // then
        Exception e = assertThrows(InvalidPassword.class, () -> {
            userInfoService.validateUserInfo(user);
        }, "Password is too short");
        assertEquals(e.getMessage(), "Password is too short");
    }

    @Test
    void canNotValidateUserInfoPasswordTooLong() {

        // given
        String password = "sdjdyrdirjtidirycnkrielsdjflksjdflk";
        UserInfo user = new UserInfo(1, "email", "username", password, null, null);

        BDDMockito.given(userInfoRepository.existsByUsername(user.getUsername())).willReturn(false);
        BDDMockito.given(userInfoRepository.existsByEmail(user.getEmail())).willReturn(false);

        // when
        // then
        Exception e = assertThrows(InvalidPassword.class, () -> {
            userInfoService.validateUserInfo(user);
        }, "Password is too long");
        assertEquals(e.getMessage(), "Password is too long");
    }
}
