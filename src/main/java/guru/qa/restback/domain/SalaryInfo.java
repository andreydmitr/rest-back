package guru.qa.restback.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalaryInfo {
    @Schema(description = "USER ID")
    @NonNull Long userId;

    @Schema(description = "SALARY IN USD")
    Double salaryUsd;

    @Schema(description = "RETURN INFO")
    ReturnInfo returnInfo;
}
