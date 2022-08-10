package guru.qa.restback.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    @Schema(description = "USER ID")
    @NonNull Long userId;

    @Schema(description = "USER NAME")
    String name;
    String serName;
    Date birthDay;
    @Schema(description = "RETURN INFO")
    ReturnInfo returnInfo;
}
