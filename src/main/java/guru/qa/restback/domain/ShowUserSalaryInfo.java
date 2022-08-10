package guru.qa.restback.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowUserSalaryInfo {
    String name;
    String serName;
    Date birthDay;
    Double salaryUsd;

    @Schema(description = "RETURN INFO")
    ReturnInfo returnInfo;
}
