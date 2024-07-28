package com.contact.scm.entities;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.Nullable;

@ReadingConverter
public class ProviderConverter implements Converter<String, Providers>{

    @Override
    @Nullable
    public Providers convert(final String source) {
        return Providers.valueOf(source.toUpperCase());
    }

}
