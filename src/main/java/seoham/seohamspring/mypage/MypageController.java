package seoham.seohamspring.mypage;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import seoham.seohamspring.config.BaseException;
import seoham.seohamspring.config.BaseResponse;
import seoham.seohamspring.mypage.domain.PatchNicknameReq;
import seoham.seohamspring.mypage.domain.PatchPasswordReq;
import seoham.seohamspring.mypage.domain.PostCheckNicknameReq;
import seoham.seohamspring.mypage.domain.PostCheckPasswordReq;
import seoham.seohamspring.util.JwtService;

import static seoham.seohamspring.config.BaseResponseStatus.*;

@Controller
@RequestMapping("/mypage")
@Api(tags = "mypage")
public class MypageController {

    @Autowired
    private final MypageService mypageService;

    private final JwtService jwtService;

    @Autowired
    public MypageController(MypageService mypageService, JwtService jwtService) {
        this.mypageService = mypageService;
        this.jwtService = jwtService;
    }

    //닉네임 중복검사
    @ResponseBody
    @PostMapping("/nickname/check")
    public BaseResponse<Integer> checkNickname(@Validated @RequestBody PostCheckNicknameReq postCheckNicknameReq) {
        if (postCheckNicknameReq.getNewNickname() == null) {
            return new BaseResponse<>(POST_MYPAGE_EMPTY_NICKNAME);
        }

        try {
            return new BaseResponse<>(mypageService.chekcNickname(postCheckNicknameReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }

    //비밀번호 확인
    @ResponseBody
    @PostMapping("/password/check")
    public BaseResponse<Integer> checkPassword(@Validated @RequestBody PostCheckPasswordReq postCheckPasswordReq) {
        if (postCheckPasswordReq == null) {
            return new BaseResponse<>(POST_MYPAGE_EMPTY_PASSWORD);
        }

        try {
            int userIdx = jwtService.getUserIdx();
            return new BaseResponse<>(mypageService.checkPassword(postCheckPasswordReq, userIdx));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


    //닉네임 수정
    @ResponseBody
    @PatchMapping("/nickname/modify")
    public BaseResponse<String> modifyNickname(@Validated @RequestBody PatchNicknameReq patchNicknameReq) {
        if (patchNicknameReq.getNewNickname() == null) {
            return new BaseResponse<>(PATCH_MYPAGE_EMPTY_NICKNAME);
        }

        try {
            int userIdx = jwtService.getUserIdx();
            return new BaseResponse<>(mypageService.modifyNickname(patchNicknameReq, userIdx));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }


    //비밀번호 수정
    @ResponseBody
    @PatchMapping("/password/modify")
    public BaseResponse<String> modifyPassword(@RequestBody PatchPasswordReq patchPasswordReq) {
        if (patchPasswordReq.getNewPassword() == null) {
            return new BaseResponse<>(PATCH_MYPAGE_EMPTY_PASSWORD);
        }

        try {
            int userIdx = jwtService.getUserIdx();
            return new BaseResponse<>(mypageService.modifyPassword(patchPasswordReq, userIdx));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }




    //회원 탈퇴
    @ResponseBody
    @DeleteMapping("/delete")
    public BaseResponse<String> deleteUser() {
        try {
            int userIdx = jwtService.getUserIdx();
            return new BaseResponse<>(mypageService.deleteUser(userIdx));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }



}
