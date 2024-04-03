package com.golapadeok.fluo.domain.tag.dto;

import com.golapadeok.fluo.domain.tag.domain.Tag;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Getter
public class TagDto {
    private final String tagId;
    private final String name;
    private final String colorCode;

    public TagDto(Long tagId, String name, String colorCode) {
        this.tagId = tagId.toString();
        this.name = name;
        this.colorCode = colorCode;
    }

    public TagDto(String tagId, String name, String colorCode) {
        this.tagId = tagId;
        this.name = name;
        this.colorCode = colorCode;
    }

    public static TagDto of(Tag tag) {
        if (tag == null)
            return null;

        return new TagDto(tag.getId().toString(), tag.getTagName(), tag.getColorCode().getColor());
    }

    public static List<TagDto> of(List<Tag> tags) {
        Iterator<Tag> iterator = tags.iterator();
        List<TagDto> results = new ArrayList<>();
        while (iterator.hasNext()) {
            Tag tag = iterator.next();
            results.add(new TagDto(tag.getId().toString(), tag.getTagName(), tag.getColorCode().getColor()));
        }

        return Collections.unmodifiableList(results);
    }
}
