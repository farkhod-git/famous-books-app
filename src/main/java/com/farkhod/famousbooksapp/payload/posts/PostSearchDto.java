package com.farkhod.famousbooksapp.payload.posts;

import com.farkhod.famousbooksapp.enums.SearchType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostSearchDto {
    int page = 0;
    int size = 20;
    String search;
    SearchType searchType;
}
