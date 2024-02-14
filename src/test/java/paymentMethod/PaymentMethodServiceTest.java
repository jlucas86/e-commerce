package paymentMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.sql.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    // update

    // delete

    // helper

}
