package com.freeefly.restapiprac.controller.common;

import com.freeefly.restapiprac.service.social.KakaoService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/social/login")
public class SocialController {
    private final KakaoService kakaoService;
    private final Environment environment;
    private final Gson gson;
    private final RestTemplate restTemplate;

    @Value("${spring.social.kakao.url.login}")
    private String kakaoLoginUrl;
    @Value("${spring.url.base}")
    private String baseUrl;
    @Value("${spring.social.kakao.client_id}")
    private String kakaoClientId;
    @Value("${spring.social.kakao.redirect}")
    private String kakaoRedirect;

    @GetMapping
    public String socialLogin(Model model) {
        StringBuilder loginUrl = new StringBuilder();
        loginUrl.append(kakaoLoginUrl)
                .append("?client_id=").append(kakaoClientId)
                .append("&response_type=code")
                .append("&redirect_uri=").append(baseUrl).append(kakaoRedirect);
        model.addAttribute("loginUrl", loginUrl.toString());
        return "social/login";
    }

    @GetMapping(value = "/kakao")
    public String redirectKakao(
            Model model,
            @RequestParam String code) {
        log.info("code = {}", code);
        model.addAttribute("authInfo", kakaoService.getKakaoTokenInfo(code));
        return "social/redirectKakao";
    }




}
