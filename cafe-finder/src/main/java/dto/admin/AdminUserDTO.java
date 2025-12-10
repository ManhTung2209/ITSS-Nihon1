package dto.admin;

import com.itss.cafe_finder.model.enums.UserRoleType;
import com.itss.cafe_finder.model.enums.UserStatusType;
import lombok.Data;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
public class AdminUserDTO {
    private Long id;
    private String name;
    private String email;
    private UserRoleType roleType;
    private UserStatusType status;
    private LocalDate dob;
    private ZonedDateTime updatedOn;
}

