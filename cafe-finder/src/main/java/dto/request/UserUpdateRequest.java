package dto.request;

import com.itss.cafe_finder.model.enums.UserRoleType;
import com.itss.cafe_finder.model.enums.UserStatusType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserUpdateRequest {

    private String name;

    private String password;

    private String email;

}