package com.golapadeok.fluo.domain.social.converter;

import com.golapadeok.fluo.domain.social.domain.SocialType;
import org.springframework.core.convert.converter.Converter;

public class SocialConverter implements Converter<String, SocialType> {
    @Override
    public SocialType convert(String source) {
        return SocialType.fromName(source);
    }
}
