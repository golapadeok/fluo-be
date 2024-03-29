package com.golapadeok.fluo.domain.workspace.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Random;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InvitationCodeGenerator {
    private static final Random random = new Random();
    private static final int LENGTH = 6;

    public static String generator() {
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < LENGTH; i++) {
            int index = random.nextInt(3);

            switch (index) {
                case 0:
                    //소문자
                    key.append((char)((random.nextInt(26)) + 97));
                    break;
                case 1:
                    //대문자
                    key.append((char)((random.nextInt(26)) + 65));
                    break;
                case 2:
                    //숫자 0 ~ 9
                    key.append((random.nextInt(10)));
                    break;
                default:
                    break;
            }
        }
        return key.toString();
    }
}
