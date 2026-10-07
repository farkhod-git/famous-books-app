package com.farkhod.famousbooksapp.security.jwt.properties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PROTECTED)
@Getter
@Setter
public abstract class JwtProperties {
    String secretKey;
    long expiry;
}
