package com.farkhod.famousbooksapp.payload.posts;

import com.farkhod.famousbooksapp.enums.BookGenreEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.util.Set;
import java.util.UUID;

public record CreatePostDto(@NotBlank(message = "book name cannot be blank")
                            String bookName,
                            @Length(min = 2)
                            String author,
                            @Length(min = 2)
                            String opinion,
                            @Positive
                            Integer pages,
                            BookGenreEnum genre,
                            @Positive
                            Short year,
                            @NotNull(message = "reader score cannot be null")
                            Integer readerScore,
                            @NotNull(message = "cover id cannot be null")
                            UUID coverId,
                            Set<UUID> photoIds) {

}
