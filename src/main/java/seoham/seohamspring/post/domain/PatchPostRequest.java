package seoham.seohamspring.post.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchPostRequest {

    private String sender; //보낸이
    private int date; //날짜
    private int tagIdx; //태그
    private String content; //편지 내용
    private int letterIdx; //편지지 번호

    public PatchPostRequest(){

    }
}
