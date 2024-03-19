package com.golapadeok.fluo.domain.role.dto.response;

import com.golapadeok.fluo.domain.role.domain.Credential;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public class CredentialAllResponse {

    private String name;
    private String description;

    public static List<CredentialAllResponse> toItemList() {
        return Stream.of(Credential.values())
                .map(credential -> new CredentialAllResponse(credential.getName(), credential.getDescription()))
                .toList();
    }
}
