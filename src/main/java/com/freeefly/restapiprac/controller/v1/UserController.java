package com.freeefly.restapiprac.controller.v1;

import com.freeefly.restapiprac.advice.exception.UserNotFoundException;
import com.freeefly.restapiprac.entity.User;
import com.freeefly.restapiprac.model.CommonResult;
import com.freeefly.restapiprac.model.ListResult;
import com.freeefly.restapiprac.model.SingleResult;
import com.freeefly.restapiprac.repository.UserRepository;
import com.freeefly.restapiprac.service.ResponseService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Api(tags = {"1. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class UserController {
    private final UserRepository userRepository;
    private final ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 조회", notes = "모든 회원을 조회한다.")
    @GetMapping(value = "/users")
    public ListResult<User> findAllUsers() {
        return responseService.getListResult(userRepository.findAll());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 단건 조회", notes = "userId로 회원을 조회한다.")
    @GetMapping(value = "/user/{msrl}")
    public SingleResult<User> findUserById(
            @ApiParam(value = "회원 ID", required = true) @PathVariable("msrl") Long msrl,
            @ApiParam(value = "언어", defaultValue = "ko") @RequestParam(value = "lang", defaultValue = "ko") String lang
    ) {
        return responseService.getSingleResult(userRepository.findById(msrl).orElseThrow(() -> new UserNotFoundException()));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 입력", notes = "회원을 입력한다.")
    @PostMapping(value = "/user")
    public SingleResult<User> save(
            @ApiParam(value = "회원 아이디", required = true) @RequestParam(value = "uid") String uid,
            @ApiParam(value = "회원 이름", required = true) @RequestParam(value = "name") String name
    ) {
        User user = User.builder()
                .uid(uid)
                .name(name)
                .build();
        return responseService.getSingleResult(userRepository.save(user));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 수정", notes = "회원정보를 수정한다")
    @PutMapping(value = "/user")
    public SingleResult<User> modify(
            @ApiParam(value = "회원번호", required = true) @RequestParam Long msrl,
            @ApiParam(value = "회원아이디", required = true) @RequestParam String uid,
            @ApiParam(value = "회원이름", required = true) @RequestParam String name) {
        User user = User.builder()
                .msrl(msrl)
                .uid(uid)
                .name(name)
                .build();
        return responseService.getSingleResult(userRepository.save(user));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 삭제", notes = "userId로 회원정보를 삭제한다")
    @DeleteMapping(value = "/user/{msrl}")
    public CommonResult delete(
            @ApiParam(value = "회원 번호", required = true) @PathVariable Long msrl) {
        Optional<User> optionalUser = userRepository.findById(msrl);
        userRepository.delete(optionalUser.orElseThrow((() -> new UserNotFoundException())));
        // 성공 결과 정보만 필요한 경우 getSuccessResult()를 이용 하여 결과를 출력 한다.
        return responseService.getSuccessResult();
    }


}
