package com.oovetest.webDemo.tag.domain;

public enum TagGroupCode {
    GENRE,          // 類型
    TONE,           // 氛圍
    RELATIONSHIP,   // 角色關係
    MEDIUM;         // 媒介(小說、漫畫等)

    /*
    GENRE 主要放「故事核心類型」：戀愛、推理、奇幻、科幻…
    TONE 放「作品給人的氛圍」：搞笑、歡樂、黑暗、BE…
    RELATIONSHIP 放角色互動模式：兄妹、師徒、戀人、敵對…
    MEDIUM 放媒介：小說、漫畫、動畫、網路劇等 
    */
    public static TagGroupCode from(String code) {
        try {
            return TagGroupCode.valueOf(code);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid TagGroup code: " + code);
        }
    }
    
}
