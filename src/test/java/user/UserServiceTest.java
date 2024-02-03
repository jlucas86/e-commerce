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
import org.mockito.InjectMocks;
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
        UserInfo user = setUpUser("email", "username", "password", null, null);

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

    @Test
    void canNotAddUserInvalidPassword() {

        // given
        UserInfo user = setUpUser("email", "username", "pas", null, null);

        // when
        // userInfoService.addUser(user);

        // then

        Exception e = assertThrows(InvalidPassword.class, () -> {
            userInfoService.addUser(user);
        }, "Password is too short");
        assertEquals(e.getMessage(), "Password is too short");

    }

    @Test
    void canNotAddUserUsernameAlreadyExists() {

        // given
        UserInfo user = setUpUser("email", "username", "pas", null, null);

        BDDMockito.given(userInfoRepository.existsByUsername(user.getUsername())).willReturn(true);

        // when
        // userInfoService.addUser(user);

        // then

        Exception e = assertThrows(UsernameAlreadyExists.class, () -> {
            userInfoService.addUser(user);
        }, "Username username already exists");
        assertEquals(e.getMessage(), "Username username already exists");

    }

    @Test
    void canNotAddUserEmailAlreadyExists() {

        // given
        UserInfo user = setUpUser("email", "username", "pas", null, null);

        BDDMockito.given(userInfoRepository.existsByEmail(user.getEmail())).willReturn(true);

        // when
        // userInfoService.addUser(user);

        // then

        Exception e = assertThrows(EmailAlreadyExists.class, () -> {
            userInfoService.addUser(user);
        }, "Email email already exists");
        assertEquals(e.getMessage(), "Email email already exists");

    }

    // get

    @Test
    void canGetUser() {

        // given
        UserInfo user = setUpUser("email", "username", "***********", null, null);

        BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));
        user.setPassword("password");
        // when
        UserInfo u = userInfoService.getUser(user.getUsername());
        user.setPassword("***********");
        // then

        assertEquals(u, user);

    }

    @Test
    void canNotGetUserInvalidUser() {

        // given
        UserInfo user = setUpUser("email", "username", "password", null, null);

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
        UserInfo user = setUpUser("email", "username", "password", null, null);

        // when
        userInfoService.updateUser(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // then
        ArgumentCaptor<UserInfo> userArgumentCaptor = ArgumentCaptor.forClass(UserInfo.class);

        verify(userInfoRepository).save(userArgumentCaptor.capture());

        UserInfo capturedUser = userArgumentCaptor.getValue();
        assertEquals(capturedUser, user);
    }

    @Test
    void canNotUpdateUserInvalidPassword() {

        // given
        UserInfo user = setUpUser("email", "username", "pas", null, null);

        // when
        // userInfoService.addUser(user);

        // then

        Exception e = assertThrows(InvalidPassword.class, () -> {
            userInfoService.updateUser(user);
        }, "Password is too short");
        assertEquals(e.getMessage(), "Password is too short");

    }

    @Test
    void canNotUpdateUserUsernameAlreadyExists() {

        // given
        UserInfo user = setUpUser("email", "username", "pas", null, null);

        BDDMockito.given(userInfoRepository.existsByUsername(user.getUsername())).willReturn(true);

        // when
        // userInfoService.addUser(user);

        // then

        Exception e = assertThrows(UsernameAlreadyExists.class, () -> {
            userInfoService.updateUser(user);
        }, "Username username already exists");
        assertEquals(e.getMessage(), "Username username already exists");

    }

    @Test
    void canNotUpdateUserEmailAlreadyExists() {

        // given
        UserInfo user = setUpUser("email", "username", "pas", null, null);

        BDDMockito.given(userInfoRepository.existsByEmail(user.getEmail())).willReturn(true);

        // when
        // userInfoService.addUser(user);

        // then

        Exception e = assertThrows(EmailAlreadyExists.class, () -> {
            userInfoService.updateUser(user);
        }, "Email email already exists");
        assertEquals(e.getMessage(), "Email email already exists");
    }

    // delete

    @Test
    void canDeleteUser() {
        UserInfo user = setUpUser("email", "username", "password", null, null);

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
        UserInfo user = setUpUser("email", "username", "pas", null, null);

        BDDMockito.given(userInfoRepository.existsByUsername(user.getUsername())).willReturn(false);

        // when

        // then

        Exception e = assertThrows(UsernameNotFoundException.class, () -> {
            userInfoService.deleteUser(user);
        }, "Username username not found");
        assertEquals(e.getMessage(), "Username username not found");
    }

    // helper
    UserInfo setUpUser(String email, String username, String password, Set<Role> roles, Set<Store> stores) {
        UserInfo user = new UserInfo();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        if (roles == null) {
            roles = new HashSet<>();
        }
        user.setRoles(roles);
        if (stores == null) {
            stores = new HashSet<>();
        }
        user.setStores(stores);
        return user;
    }

}
