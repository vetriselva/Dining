package  com.vgoups.dining.dto;

import com.vgoups.dining.validation.DiningTableValidation.UniqueNameValidate;
import jakarta.validation.constraints.*;
        import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDiningTableRequest {
    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name must be at most 50 characters")
    private String name;

    @NotNull(message = "Member count is required")
    @Min(value = 1, message = "Minimum 1 member required")
    @Max(value = 20, message = "Maximum 20 members allowed")
    private Integer memberCount;

    @NotNull(message = "Status must be specified")
    private Boolean status;

}