package paymentMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.exceptions.PaymentMethodDoesNotExist;
import com.example.exceptions.UserDoesNotOwnPaymentMethod;
import com.example.payment.Payment;
import com.example.paymentMethod.PaymentMethod;
import com.example.paymentMethod.PaymentMethodRepository;
import com.example.paymentMethod.PaymentMethodService;
import com.example.userInfo.UserInfo;
import com.example.userInfo.UserInfoRepository;

@ExtendWith(MockitoExtension.class)
public class PaymentMethodServiceTest {

    @Mock
    private PaymentMethodRepository paymentMethodRepository;

    @Mock
    private UserInfoRepository userInfoRepository;

    PaymentMethodService paymentMethodService;

    @BeforeEach
    void setUp() {
        paymentMethodService = new PaymentMethodService(paymentMethodRepository, userInfoRepository);
    }

    // add
    @Test
    void canAddPaymentMethod() {
        // given
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Date date = new Date(0);
        PaymentMethod paymentMethod = new PaymentMethod(1, "bob", "12345678901234", date, null, user, null, null);

        BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));

        // when
        paymentMethodService.addPaymentMethod(user.getUsername(), paymentMethod);

        // then
        ArgumentCaptor<String> usernameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userInfoRepository).findByUsername(usernameArgumentCaptor.capture());
        String capturedUsername = usernameArgumentCaptor.getValue();
        assertEquals(capturedUsername, user.getUsername());

        ArgumentCaptor<PaymentMethod> paymentMethodArgumentCaptor = ArgumentCaptor.forClass(PaymentMethod.class);
        verify(paymentMethodRepository).save(paymentMethodArgumentCaptor.capture());
        PaymentMethod capturedPaymentMethod = paymentMethodArgumentCaptor.getValue();
        assertEquals(capturedPaymentMethod, paymentMethod);
    }

    // get

    @Test
    void canGetPaymentMethod() {
        // given
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Date date = new Date(0);
        PaymentMethod paymentMethod = new PaymentMethod(1, "bob", "1234567890123456", date, null, user, null, null);
        PaymentMethod p = null;

        BDDMockito.given(paymentMethodRepository.existsById(paymentMethod.getId())).willReturn(true);
        BDDMockito.given(paymentMethodRepository.findById(paymentMethod.getId()))
                .willReturn(Optional.of(paymentMethod));
        BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));

        // when
        try {
            p = paymentMethodService.getPaymentMethod(user.getUsername(), paymentMethod.getId());
        } catch (Exception e) {
            fail();
        }
        paymentMethod.setCardNumber("************" + paymentMethod.getCardNumber().substring(12));
        paymentMethod.setCvc("***");

        // then
        ArgumentCaptor<Integer> paymentMethodIdExistsArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(paymentMethodRepository).existsById(paymentMethodIdExistsArgumentCaptor.capture());
        Integer capturedPaymentMethodIdExists = paymentMethodIdExistsArgumentCaptor.getValue();
        assertEquals(capturedPaymentMethodIdExists, paymentMethod.getId());

        ArgumentCaptor<Integer> paymentMethodIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(paymentMethodRepository, atLeast(2)).findById(paymentMethodIdArgumentCaptor.capture());
        Integer capturedPaymentMethodId = paymentMethodIdArgumentCaptor.getValue();
        assertEquals(capturedPaymentMethodId, paymentMethod.getId());

        ArgumentCaptor<String> usernameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userInfoRepository).findByUsername(usernameArgumentCaptor.capture());
        String capturedUsername = usernameArgumentCaptor.getValue();
        assertEquals(capturedUsername, user.getUsername());

        assertEquals(p, paymentMethod);
    }

    @Test
    void canGetPaymentMethods() {
        // given
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Date date = new Date(0);
        PaymentMethod paymentMethod = new PaymentMethod(1, "bob", "1234567890123456", date, null, user, null, null);
        PaymentMethod paymentMethod1 = new PaymentMethod(2, "bob", "1234567890123456", date, null, user, null, null);
        PaymentMethod paymentMethod2 = new PaymentMethod(3, "bob", "1234567890123456", date, null, user, null, null);
        PaymentMethod paymentMethod3 = new PaymentMethod(4, "bob", "1234567890123456", date, null, user, null, null);

        List<PaymentMethod> paymentMethods = new ArrayList<>();
        List<PaymentMethod> p = null;
        paymentMethods.add(paymentMethod);
        paymentMethods.add(paymentMethod1);
        paymentMethods.add(paymentMethod2);
        paymentMethods.add(paymentMethod3);

        BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));
        BDDMockito.given(paymentMethodRepository.findAllByUserId(user.getId())).willReturn(paymentMethods);

        // when
        try {
            p = paymentMethodService.getPaymentMethods(user.getUsername());
        } catch (Exception e) {
            fail();
        }
        for (PaymentMethod pHold : paymentMethods) {
            pHold.setCardNumber("************" + paymentMethod.getCardNumber().substring(12));
            pHold.setCvc("***");
        }

        // then

        ArgumentCaptor<String> usernameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userInfoRepository).findByUsername(usernameArgumentCaptor.capture());
        String capturedUsername = usernameArgumentCaptor.getValue();
        assertEquals(capturedUsername, user.getUsername());

        ArgumentCaptor<Integer> userIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(paymentMethodRepository).findAllByUserId(userIdArgumentCaptor.capture());
        Integer capturedUserId = userIdArgumentCaptor.getValue();
        assertEquals(capturedUserId, user.getId());

        assertEquals(p, paymentMethods);
    }

    // update

    @Test
    void canUpdatePaymentMethod() {
        // given
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Date date = new Date(0);
        PaymentMethod paymentMethod = new PaymentMethod(1, "bob", "1234567890123456", date, null, user, null, null);
        PaymentMethod p = null;

        BDDMockito.given(paymentMethodRepository.existsById(paymentMethod.getId())).willReturn(true);
        BDDMockito.given(paymentMethodRepository.findById(paymentMethod.getId()))
                .willReturn(Optional.of(paymentMethod));
        BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));

        // when
        try {
            paymentMethodService.updatePaymentMethod(user.getUsername(), paymentMethod);
        } catch (Exception e) {
            fail();
        }
        paymentMethod.setCardNumber("************" + paymentMethod.getCardNumber().substring(12));
        paymentMethod.setCvc("***");

        // then
        ArgumentCaptor<Integer> paymentMethodIdExistsArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(paymentMethodRepository).existsById(paymentMethodIdExistsArgumentCaptor.capture());
        Integer capturedPaymentMethodIdExists = paymentMethodIdExistsArgumentCaptor.getValue();
        assertEquals(capturedPaymentMethodIdExists, paymentMethod.getId());

        ArgumentCaptor<Integer> paymentMethodIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(paymentMethodRepository, atLeast(2)).findById(paymentMethodIdArgumentCaptor.capture());
        Integer capturedPaymentMethodId = paymentMethodIdArgumentCaptor.getValue();
        assertEquals(capturedPaymentMethodId, paymentMethod.getId());

        ArgumentCaptor<String> usernameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userInfoRepository).findByUsername(usernameArgumentCaptor.capture());
        String capturedUsername = usernameArgumentCaptor.getValue();
        assertEquals(capturedUsername, user.getUsername());

        ArgumentCaptor<PaymentMethod> paymentMethoArgumentCaptor = ArgumentCaptor.forClass(PaymentMethod.class);
        verify(paymentMethodRepository).save(paymentMethoArgumentCaptor.capture());
        PaymentMethod capturedPaymentMethod = paymentMethoArgumentCaptor.getValue();
        assertEquals(capturedPaymentMethod, paymentMethod);

        // assertEquals(p, paymentMethod);
    }

    // delete

    // helper

    @Test
    void canVerify() {
        // given
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Date date = new Date(0);
        PaymentMethod paymentMethod = new PaymentMethod(1, "bob", "12345678901234", date, null, user, null, null);

        BDDMockito.given(paymentMethodRepository.existsById(paymentMethod.getId())).willReturn(true);
        BDDMockito.given(paymentMethodRepository.findById(paymentMethod.getId()))
                .willReturn(Optional.of(paymentMethod));
        BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));

        // when
        try {
            paymentMethodService.verify(user.getUsername(), paymentMethod.getId());
        } catch (Exception e) {
            fail();
        }

        // then
        ArgumentCaptor<Integer> paymentMethodIdExistsArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(paymentMethodRepository).existsById(paymentMethodIdExistsArgumentCaptor.capture());
        Integer capturedPaymentMethodIdExists = paymentMethodIdExistsArgumentCaptor.getValue();
        assertEquals(capturedPaymentMethodIdExists, paymentMethod.getId());

        ArgumentCaptor<Integer> paymentMethodIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(paymentMethodRepository).findById(paymentMethodIdArgumentCaptor.capture());
        Integer capturedPaymentMethodId = paymentMethodIdArgumentCaptor.getValue();
        assertEquals(capturedPaymentMethodId, paymentMethod.getId());

        ArgumentCaptor<String> usernameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userInfoRepository).findByUsername(usernameArgumentCaptor.capture());
        String capturedUsername = usernameArgumentCaptor.getValue();
        assertEquals(capturedUsername, user.getUsername());
    }

    @Test
    void canNotVerifyPaymentMethodDoesNotExist() {
        // given
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Date date = new Date(0);
        PaymentMethod paymentMethod = new PaymentMethod(1, "bob", "12345678901234", date, null, user, null, null);

        BDDMockito.given(paymentMethodRepository.existsById(paymentMethod.getId())).willReturn(false);

        // when
        // then
        Exception e = assertThrows(PaymentMethodDoesNotExist.class, () -> {
            paymentMethodService.verify(user.getUsername(), paymentMethod.getId());
        }, "PaymentMethod " + paymentMethod.getId() + " not found");

        assertEquals(e.getMessage(), "PaymentMethod " + paymentMethod.getId() + " not found");
    }

    @Test
    void canNotVerify() {
        // given
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        UserInfo user1 = new UserInfo(2, "email1", "username1", "password", null, null);
        Date date = new Date(0);
        PaymentMethod paymentMethod = new PaymentMethod(1, "bob", "12345678901234", date, null, user, null, null);

        BDDMockito.given(paymentMethodRepository.existsById(paymentMethod.getId())).willReturn(true);
        BDDMockito.given(paymentMethodRepository.findById(paymentMethod.getId()))
                .willReturn(Optional.of(paymentMethod));
        BDDMockito.given(userInfoRepository.findByUsername(user1.getUsername())).willReturn(Optional.of(user1));

        // when
        // then
        Exception e = assertThrows(UserDoesNotOwnPaymentMethod.class, () -> {
            paymentMethodService.verify(user1.getUsername(), paymentMethod.getId());
        }, "User " + user1.getUsername() + " does not own paymentMethod " + paymentMethod.getId());

        assertEquals(e.getMessage(),
                "User " + user1.getUsername() + " does not own paymentMethod " + paymentMethod.getId());
    }

}
