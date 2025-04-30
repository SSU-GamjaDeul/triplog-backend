package com.triplog.user.domain.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Vibe {

    WARM("따뜻한"),
    COMFORTABLE("편안한"),
    MELANCHOLIC("아늑한"),
    CALM("잔잔한"),
    JOYFUL("즐거운"),
    NOISY("시끄러운"),
    EXCITED("흥겨운"),
    DREAMY("몽환적인"),
    ROMANTIC("로맨틱한"),
    SENSUAL("감성적인"),
    BEAUTIFUL("아름다운"),
    DARK("음산한"),
    UNCOMFORTABLE("불편한"),
    HEAVY("무거운"),
    INTENSE("격렬한"),
    FORMAL("격식있는"),
    ETC("기타"),
    NONE("해당없음");

    private final String description;
}
