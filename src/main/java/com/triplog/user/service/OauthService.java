package com.triplog.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.triplog.user.domain.OauthInfo;
import com.triplog.user.domain.User;
import com.triplog.user.domain.UserVibe;
import com.triplog.user.domain.enums.OauthProvider;
import com.triplog.user.domain.enums.Vibe;
import com.triplog.user.dto.LoginRequest;
import com.triplog.user.dto.SignupRequest;
import com.triplog.user.jwt.JwtToken;
import com.triplog.user.jwt.JwtUtil;
import com.triplog.user.repository.UserRepository;
import com.triplog.user.repository.UserVibeRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class OauthService {

    private final UserRepository userRepository;
    private final UserVibeRepository userVibeRepository;
    private final JwtUtil jwtUtil;

    @Value("${oauth.kakao.auth.client}")
    private String clientId;

    @Value("${oauth.kakao.auth.redirect}")
    private String RedirectUrl;

    public JwtToken kakaoLogin(String code) {
        String accessToken = getKakaoAccessToken(code);
        String oauthId = extractOauthId(accessToken);

        return userRepository.findByOauthInfo(new OauthInfo(oauthId, OauthProvider.KAKAO))
                .map(user -> JwtToken.builder()
                        .accessToken(jwtUtil.createAccessToken(user.getNickname()))
                        .build())
                .orElse(JwtToken.builder().accessToken("").build()); // 회원가입 필요
    }

    private String getKakaoAccessToken(String code){
        HttpHeaders headers=new HttpHeaders();
        headers.add("Content-Type","application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body=new LinkedMultiValueMap<>();
        body.add("grant_type","authorization_code");
        body.add("client_id",clientId);
        body.add("redirect_uri",RedirectUrl);
        body.add("code",code);

        HttpEntity<MultiValueMap<String,String>> request=new HttpEntity<>(body,headers);
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<String> response=restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                request,
                String.class
        );

        String responseBody=response.getBody();
        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode jsonNode=null;
        try{
            jsonNode=objectMapper.readTree(responseBody);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
        return jsonNode.get("access_token").asText();
    }

    //카카오 회원 정보를 데이터베이스에 저장하는 메서드
    private JsonNode getKakaoUserInfo(String accessToken){
        HttpHeaders headers=new HttpHeaders();
        headers.add("Authorization","Bearer "+accessToken);
        headers.add("Content-Type","application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String,String>> request=new HttpEntity<>(headers);
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<String> response=restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                request,
                String.class
        );

        String responseBody=response.getBody();
        ObjectMapper objectMapper=new ObjectMapper();
        try{
            return objectMapper.readTree(responseBody);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }

    public JwtToken kakaoSignup(SignupRequest request) {
        String accessToken = getKakaoAccessToken(request.code());
        JsonNode userInfo = getKakaoUserInfo(accessToken);

        String oauthId = userInfo.get("id").asText();
        JsonNode kakaoAccount = userInfo.get("kakao_account");
        String email = kakaoAccount.get("email").asText();

        if (userRepository.existsByOauthInfo(new OauthInfo(oauthId, OauthProvider.KAKAO))) {
            throw new IllegalArgumentException("이미 가입된 사용자입니다.");
        }

        User user = User.builder()
                .email(email)
                .accessToken(accessToken)
                .oauthInfo(new OauthInfo(oauthId, OauthProvider.KAKAO))
                .nickname(request.nickname())
                .birthYear(request.birthYear())
                .gender(request.gender())
                .build();

        userRepository.save(user);

        for (String vibeDesc : request.Vibe()) {
            Vibe vibeEnum = Vibe.fromDescription(vibeDesc);

            UserVibe userVibe = UserVibe.builder()
                    .user(user)
                    .vibe(vibeEnum)
                    .build();

            userVibeRepository.save(userVibe);
        }
        return JwtToken.builder()
                .accessToken(jwtUtil.createAccessToken(user.getAccessToken()))
                .build();
    }


    private String extractOauthId(String accessToken) {
        JsonNode userInfo = getKakaoUserInfo(accessToken);
        return userInfo.get("id").asText();
    }
}
