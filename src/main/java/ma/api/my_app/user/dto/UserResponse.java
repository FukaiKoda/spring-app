package ma.api.my_app.user.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        String email,
        String avatarUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
