package com.remind.memorylog.domain.member.service;

import java.util.List;
import java.util.Random;

public class NicknameGenerator {

    private static final List<String> NICKNAMES = List.of(
            "삐진감자", "심쿵절임", "서운깍두", "사과맛분노", "토라진감자", "억울햄버거",
            "갑분싸라다", "방전딸기잼", "말없군고구마", "연락단무지", "이별예정떡",
            "안읽닭강정", "울먹소금빵", "토라비빔면");

    private static final Random random = new Random();

    public static String generate() {
        return NICKNAMES.get(random.nextInt(NICKNAMES.size()));
    }
}
