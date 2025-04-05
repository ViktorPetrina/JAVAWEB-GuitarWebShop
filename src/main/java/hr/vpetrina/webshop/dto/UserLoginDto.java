package hr.vpetrina.webshop.dto;

import hr.vpetrina.webshop.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class UserLoginDto {
    private Integer id;
    private User user;
    private Date loginDate;
    private String ipAddress;

    public UserLoginDto(User user, Date loginDate, String ipAddress) {
        this.user = user;
        this.loginDate = loginDate;
        this.ipAddress = ipAddress;
    }
}
