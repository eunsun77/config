# ReSpark backend-config

MSA 공통 보안·예외 라이브러리. `gateway`, `backend-user` 등에서 의존한다.

## 한 일

cakequake용 `spark-config`를 ReSpark `backend-config`로 재작성함.

- 패키지 `com.cakequake.*` → `com.respark.common.*`로 변경
- Java 17 / Spring Boot 3 기준 Gradle 라이브러리 골격 추가 (`build.gradle`, `settings.gradle`)
- `TokenValidator`, `AuthPrincipal` 도입 → `Member`, `Shop`, `JWTUtil` 등 cakequake 도메인 의존 제거
- JWT 필터·`CustomUserDetails`를 위 인터페이스 기준으로 개편 (역할: `MEMBER` / `ADMIN` / `SUPER_ADMIN`)
- Security 공개 경로를 ReSpark 기준으로 정리
  - 인증: 회원가입·로그인·소셜·이메일·비밀번호 찾기·refresh
  - GET만 공개: 약관·상품·카테고리·메인
  - `/api/v1/admin/**` → `ADMIN`, `SUPER_ADMIN`
- CORS·쿠키 도메인·Secure·SameSite를 `respark.security.*` 설정으로 외부화
- `ErrorCode`를 회원·상품·거래·결제·채팅·정산·관리자 등 ReSpark 도메인으로 재작성
- `GlobalExceptionHandler` 정리 (검증·JWT·외부 API·예상치 못한 예외)
- Spring Boot AutoConfiguration 등록 (`ResparkCommonAutoConfiguration`)
- 설정 예시 `respark-security-example.yml` 추가

### 삭제한 것

- cakequake 패키지·매장/케이크/장바구니/쿠폰/사업자 관련 ErrorCode
- `WebMvcConfig` (nginx 로컬 업로드 경로 + CORS 이중 설정)
- `JWTClaimProvider` (ShopRepository·SELLER 의존) → claim 생성은 `backend-user`에서 담당
- Security 체인 내 `oauth2Login` → 소셜은 `/api/v1/auth/social/**`에서 직접 처리

## 포함

| 패키지 | 역할 |
| --- | --- |
| `com.respark.common.config` | SecurityFilterChain, CORS, Cookie, 공개 경로 상수 |
| `com.respark.common.exception` | `ErrorCode`, `BusinessException`, `GlobalExceptionHandler` |
| `com.respark.common.security` | JWT 필터, `CustomUserDetails`, 인증 진입점 |

## 서비스에서 구현할 빈

```java
@Component
public class JwtTokenValidator implements TokenValidator {
    @Override
    public AuthPrincipal validate(String accessToken) {
        // JWT 파싱 후 memberUuid / email / nickname / roles 반환
        // ACTIVE가 아니면 BusinessException(MEMBER_SUSPENDED | MEMBER_WITHDRAWN)
    }
}
```

## 설정

`src/main/resources/respark-security-example.yml` 참고.

```yaml
respark:
  security:
    cookie-domain: ""
    cookie-secure: false
    cookie-same-site: Lax
    allowed-origins:
      - http://localhost:3000
```

## 삭제·이전된 것

- cakequake 패키지·도메인·매장/케이크 ErrorCode
- `WebMvcConfig` (로컬 nginx 업로드 + CORS 이중 설정)
- `JWTClaimProvider` (Shop 의존) → `backend-user`에서 claim 생성
- OAuth2 Login 필터 체인 내장 → 소셜은 `/api/v1/auth/social/**`에서 직접 처리

## 의존

```gradle
implementation project(':backend-config')
// or
implementation 'com.respark:backend-config:0.0.1-SNAPSHOT'
```
