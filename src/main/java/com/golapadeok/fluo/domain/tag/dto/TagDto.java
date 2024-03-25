package com.golapadeok.fluo.domain.tag.dto;

import com.golapadeok.fluo.domain.tag.domain.Tag;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Getter
public class TagDto {
    private String id;
    private String tagName;
    private String colorCode;

    private TagDto(String id, String tagName, String colorCode) {
        this.id = id;
        this.tagName = tagName;
        this.colorCode = colorCode;
    }

    public static List<TagDto> of(List<Tag> tags) {
        Iterator<Tag> iterator = tags.iterator();
        List<TagDto> results = new ArrayList<>();
        while (iterator.hasNext()) {
            Tag tag = iterator.next();
            results.add(new TagDto(tag.getId().toString(), tag.getTagName(), tag.getColorCode()));
        }

        return Collections.unmodifiableList(results);
    }
}
