package ma.api.my_app.user.dto;

import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
        String username,

        String avatarUrl
) {
}
