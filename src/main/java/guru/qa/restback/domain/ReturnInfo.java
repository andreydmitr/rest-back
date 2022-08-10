package guru.qa.restback.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnInfo {
    public enum codes {

        Ok(200),
        ErrorNotFound(400);

        private final int id;
        codes(int id) { this.id = id; }
        //public int getValue() { return id; }
    }

    @Schema(description = "RETURN TEXT")
    @Builder.Default
    String text="Ok";

    @Schema(description = "RETURN CODE")
    @Builder.Default
    codes code= codes.Ok;
}

