package com.ssafy.beconofstock.authentication.provider;

import java.util.Map;

public interface OAuthUserInfo {

    String getProviderId();

    String getProvider();

    String getName();
    String getProfileImg();
    Map<String, Object> getAttributes();
}
