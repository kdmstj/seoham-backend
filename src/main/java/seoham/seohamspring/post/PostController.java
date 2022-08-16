package seoham.seohamspring.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import seoham.seohamspring.config.BaseException;
import seoham.seohamspring.config.BaseResponse;
import seoham.seohamspring.config.BaseResponseStatus;
import seoham.seohamspring.post.domain.*;

import static seoham.seohamspring.config.BaseResponseStatus.*;

@Controller
@RequestMapping("/posts") // /posts 경로로 들어오는 경우 아래의 Method들로 분기될 수 있도록 설정
public class PostController {

    @Autowired
    private final PostService postService;

    //private final JwtService jwtService;


    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    /*
     * 편지 작성 페이지
     */

    @ResponseBody
    @PostMapping("/new")
    public BaseResponse<CreatePostResponse> createPost(@RequestBody CreatePostRequest createPostRequest){
        if (createPostRequest.getContent().length() >450) {//게시물 길이
            return new BaseResponse<>(POST_POSTS_INVALID_CONTENT);

        }
        try{
            CreatePostResponse createPostResponse = postService.createPost(createPostRequest);
            return new BaseResponse<>(createPostResponse);
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /*
    편지 수정 페이지
     */
    @ResponseBody
    @PatchMapping("/edit/{postIdx}")
    public BaseResponse<PatchPostResponse> modifyPost(@PathVariable ("postIdx") int postIdx, @RequestBody PatchPostRequest patchPostRequest){
        if (patchPostRequest.getContent().length() >450) {//게시물 길이
            return new BaseResponse<>(POST_POSTS_INVALID_CONTENT);
        }
        try{
            PatchPostResponse patchPostResponse = postService.modifyPost(patchPostRequest.getUserIdx(),postIdx,patchPostRequest);
            return new BaseResponse<>(patchPostResponse);
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }




    /*
     * 편지 삭제 페이지
     */
    @ResponseBody
    @DeleteMapping("/delete/{postIdx}")
    public BaseResponse<DeletePostResponse> deletePost(@PathVariable ("postIdx") int postIdx){
        try{
            DeletePostResponse deletePostResponse = postService.deletePost(postIdx);
            return new BaseResponse<>(deletePostResponse);
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /*
    *게시물 상세 조회 페이지
     */
    /*
    @GetMapping("/{postIdx}")
    public String detail(@PathVariable("postIdx") int postIdx, Model model) {
        Optional<Post> post = postService.findByPostIdx(postIdx);
        model.addAttribute("post", post);

        return "posts/detail";
    }

     */

    /*
    게시물 태그 목록 조회 페이지
     */
    /*
    @GetMapping("/tags")
    public String tagList(Model model) {
        List<Tag> tags = postService.TagList();
        model.addAttribute("tags",tags);
        return "posts/tagList";
    }

     */

    /*
    태그별 편지 조회
     */
    /*
    @GetMapping("/tags/{tagIdx}")
    public String listByTag(@PathVariable("tagIdx") int tagIdx,Model model){
        Optional<Post> postsByTag = postService.findByTag(tagIdx);

        return "posts/listByTag";
    }

     */


    /*
    날짜별 편지 조회
     */
    /*
    @GetMapping("/date")
    public String listBydate(){
        return null;
    }

     */

    /*
    게시물 보낸이 목록 조회 페이지
     */
    /*
    @GetMapping("/senders")
    public String senderList(Model model) {
        List<Sender> senders = postService.SenderList();
        model.addAttribute("senders",senders);
        return "posts/senderList";
    }

     */

    /*
    보낸이별 편지 조회
     */
    /*
    @GetMapping("/tags/{sender}")
    public String listBySender(@PathVariable("sender") String sender,Model model){
        Optional<Post> postsBySender = postService.findBySender(sender);
        return "posts/listBySender";
    }

     */

    /*
    태그 정보 추가
     */
    @ResponseBody
    @PostMapping("/tags/new")
    public BaseResponse<CreateTagResponse> createTag(@RequestBody CreateTagRequest createTagRequest){
        if (createTagRequest.getTagName().length() > 20) {//태그 길이
            return new BaseResponse<>(POST_TAGS_INVALID_CONTENT);
        }
        try{
            CreateTagResponse createTagResponse = postService.createTag(createTagRequest.getUserIdx(), createTagRequest);
            return new BaseResponse<>(createTagResponse);
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /*
    태그 정보 수정
     */
    @ResponseBody
    @PatchMapping("/tags/edit/{tagIdx}")
    public BaseResponse<PatchTagResponse> modifyTag(@PathVariable ("tagIdx") int tagIdx, @RequestBody PatchTagRequest patchTagRequest){
        if (patchTagRequest.getTagName().length() >20) {//게시물 길이
            return new BaseResponse<>(POST_TAGS_INVALID_CONTENT);
        }
        try{
            PatchTagResponse patchTagResponse = postService.modifyTag(patchTagRequest.getUserIdx(),tagIdx,patchTagRequest);
            return new BaseResponse<>(patchTagResponse);
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /*
    태그 정보 삭제
     */
    @ResponseBody
    @DeleteMapping("/tags/delete/{tagIdx}")
    public BaseResponse<DeleteTagResponse> deleteTag(@PathVariable ("tagIdx") int tagIdx){
        try{
            DeleteTagResponse deleteTagResponse = postService.deleteTag(tagIdx);
            return new BaseResponse<>(deleteTagResponse);
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /*
    보낸이 정보 수정
     */
    @ResponseBody
    @PatchMapping("/senders/edit/{sender}")
    public BaseResponse<PatchSenderResponse> modifySender(@PathVariable ("sender") String originalSender,@RequestBody PatchSenderRequest patchSenderRequest){
        if (patchSenderRequest.getChangedSender().length() >20) {//게시물 길이
            return new BaseResponse<>(POST_SENDER_INVALID_CONTENT);
        }
        try{
            PatchSenderResponse patchSenderResponse = postService.modifySender(patchSenderRequest.getUserIdx(),originalSender,patchSenderRequest);
            return new BaseResponse<>(patchSenderResponse);
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }


    /*
    보낸이 정보 삭제
     */
    @ResponseBody
    @DeleteMapping("/senders/delete/{sender}")
    public BaseResponse<DeleteSenderResponse> deleteSender(@PathVariable ("sender") String sender, @RequestBody DeleteSenderRequest deleteSenderRequest){
        try{
            DeleteSenderResponse deleteSenderResponse = postService.deleteSender(sender, deleteSenderRequest);
            return new BaseResponse<>(deleteSenderResponse);
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}
