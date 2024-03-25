package com.golapadeok.fluo.domain.role.dto.response;

import com.golapadeok.fluo.domain.role.domain.Credential;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Stream;

@Builder
@AllArgsConstructor
@Getter
public class CredentialResponse {

    private String name;
    private String description;

    public static List<CredentialResponse> toItemList() {
        return Stream.of(Credential.values())
                .map(credential -> new CredentialResponse(credential.getName(), credential.getDescription()))
                .toList();
    }
}
