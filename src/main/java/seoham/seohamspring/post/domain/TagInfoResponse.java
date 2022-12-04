package seoham.seohamspring.post.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TagInfoResponse {

    private int tagIdx;
    private String tagName;
    private String tagColor;

}
