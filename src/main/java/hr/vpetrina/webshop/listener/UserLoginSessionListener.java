package hr.vpetrina.webshop.listener;

import hr.vpetrina.webshop.dto.UserLoginDto;
import hr.vpetrina.webshop.model.User;
import hr.vpetrina.webshop.service.UserLoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Slf4j
@Component
@AllArgsConstructor
public class UserLoginSessionListener implements HttpSessionAttributeListener {

    private UserLoginService userLoginService;
    private HttpServletRequest request;

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        if ("user".equals(event.getName()) && event.getValue() instanceof User user) {
            UserLoginDto loginDto = new UserLoginDto(
                    user,
                    new Date(System.currentTimeMillis()),
                    userLoginService.getIpAddress(request)
            );
            userLoginService.insert(loginDto);
        }
    }
}
