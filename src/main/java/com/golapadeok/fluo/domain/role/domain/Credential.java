package com.golapadeok.fluo.domain.role.domain;

import lombok.Getter;

@Getter
public enum Credential {

//    ENTER("그룹 접속 권한", "그룹에 접속할 수 있습니다."),
//    USER_INVITE("그룹 초대 권한", "그룹에 유저를 초대할 수 있습니다."),
//    USER_DELETE("그룹원 추방", "그룹원을 추방시킬 수 있습니다."),
//    ROLE_CREATE("역할 생성", "워크스페이스에 역할을 생성할 수 있습니다."),
//    ROLE_UPDATE_CREDENTIAL("역할의 권한 목록 수정", "역할의 권한 목록을 수정할 수 있습니다."),
//    GROUP_DELETE("그룹 삭제", "워크스페이스를 삭제할 수 있습니다."),
//    GIVE_USER_ROLE("유저에 역할 부여", "유저에게 역할을 부여할 수 있습니다.");

    DELETE_WORKSPACE("워크스페이스 삭제 권한", "워크스페이스를 삭제할 수 있습니다."),
    INVITE_MEMBER("그룹원 초대 권한", "외부 사람을 워크스페이스로 초대할 수 있습니다."),
    REMOVE_MEMBER("그룹원 추방 권한", "그룹원을 워크스페이스에서 추방할 수 있습니다."),
    CREATE_ROLE("역할 생성 권한", "역할을 생성할 수 있습니다."),
    MODIFY_ROLE("역할 수정 권한", "역할을 수정할 수 있습니다."), // 권한 목록 수정 포함
    DELETE_ROLE("역할 삭제 권한", "역할을 삭제할 수 있습니다."),
    ASSIGN_ROLE("그룹원 역할 부여 권한", "그룹원에게 역할을 부여할 수 있습니다."), // 유저에게 역할 부여
    CREATE_TASK("업무 생성 권한", "업무를 생성할 수 있습니다."),
    MODIFY_TASK("업무 수정 권한", "업무를 수정할 수 있습니다."),
    DELETE_TASK("업무 삭제 권한", "업무를 삭제할 수 있습니다.");

    private final String name;
    private final String description;

    Credential(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
