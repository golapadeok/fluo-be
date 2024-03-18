package com.golapadeok.fluo.domain.role.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.golapadeok.fluo.domain.role.domain.Credential;
import com.golapadeok.fluo.domain.role.dto.response.BaseResponse;
import com.golapadeok.fluo.domain.role.dto.response.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoleService {

    private final ObjectMapper objectMapper;

    public BaseResponse getAllCredential(){
        return new BaseResponse(Item.toItemList());
    }

}
