package com.respark.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // ----- 400 Bad Request (600~) -----
    INVALID_REQUEST(400, 600, "잘못된 요청입니다"),
    INVALID_EMAIL(400, 601, "이메일 형식이 올바르지 않습니다"),
    INVALID_PASSWORD(400, 602, "비밀번호는 10~20자이며 숫자·대문자·소문자·기호(!,~,-,*,^) 중 3종류 이상이어야 합니다"),
    INVALID_NICKNAME(400, 603, "닉네임이 유효하지 않습니다"),
    INVALID_OTP(400, 604, "인증번호 형식이 올바르지 않습니다"),
    MISSING_REQUIRED_FIELD(400, 605, "필수 항목이 누락되었습니다"),
    INVALID_PRODUCT_TITLE(400, 606, "상품명은 필수입니다"),
    INVALID_PRODUCT_DESCRIPTION(400, 607, "상품 설명은 최소 10자 이상이어야 합니다"),
    INVALID_PRICE(400, 608, "가격은 0달러 이상 100만 달러 이하여야 합니다"),
    INVALID_CATEGORY(400, 609, "카테고리가 유효하지 않습니다"),
    INVALID_MEDIA_TYPE(400, 610, "지원하지 않는 미디어 형식입니다"),
    INVALID_MEDIA_COUNT(400, 611, "사진은 최소 1개, 사진·동영상 합계 최대 10개까지 첨부할 수 있습니다"),
    INVALID_SALE_STATUS(400, 612, "판매 상태가 유효하지 않습니다"),
    INVALID_ACCOUNT(400, 613, "계좌 정보가 유효하지 않습니다"),
    INVALID_ADDRESS(400, 614, "배송지 정보가 유효하지 않습니다"),
    INVALID_PHONE(400, 615, "전화번호 형식이 올바르지 않습니다"),
    INVALID_REVIEW_RATING(400, 616, "별점은 1~5의 정수여야 합니다"),
    INVALID_CANCEL_REASON(400, 617, "거래 취소 사유는 10자 이상이어야 합니다"),
    INVALID_PAYMENT_METHOD(400, 618, "결제 수단이 유효하지 않습니다"),
    TERMS_NOT_AGREED(400, 619, "필수 약관에 동의해야 합니다"),
    INVALID_PAGE_REQUEST(400, 620, "page, size는 1 이상이어야 합니다"),
    INVALID_SOCIAL_PROVIDER(400, 621, "지원하지 않는 소셜 로그인입니다"),
    INVALID_LANGUAGE(400, 622, "지원하지 않는 언어입니다"),
    INVALID_STATUS_TRANSITION(400, 623, "요청한 상태로 변경할 수 없습니다"),
    SOLD_PRODUCT_IMMUTABLE(400, 624, "판매 완료 상품은 판매 상태 외 항목을 수정할 수 없습니다"),
    DEFAULT_ADDRESS_REQUIRED(400, 625, "기본 배송지는 삭제할 수 없습니다"),
    ACCOUNT_LIMIT_EXCEEDED(400, 626, "계좌는 최대 5개까지 등록할 수 있습니다"),
    ADDRESS_LIMIT_EXCEEDED(400, 627, "배송지는 최대 10개까지 등록할 수 있습니다"),
    REVIEW_MEDIA_LIMIT_EXCEEDED(400, 628, "리뷰 사진·동영상은 최대 2개까지 첨부할 수 있습니다"),
    CUSTOMS_NUMBER_REQUIRED(400, 629, "통관번호가 필요합니다"),
    INVALID_ADMIN_CREDENTIAL_FORMAT(400, 630, "관리자 계정 형식이 올바르지 않습니다"),

    // ----- 401 Unauthorized (800~) -----
    INVALID_CREDENTIALS(401, 800, "이메일 또는 비밀번호가 올바르지 않습니다"),
    MISSING_JWT(401, 801, "인증 토큰이 없거나 형식이 올바르지 않습니다"),
    INVALID_JWT(401, 802, "유효하지 않은 인증 토큰입니다"),
    EXPIRED_JWT(401, 803, "만료된 인증 토큰입니다"),
    INVALID_REFRESH_TOKEN(401, 804, "refresh 토큰이 유효하지 않습니다"),
    EXPIRED_REFRESH_TOKEN(401, 805, "만료된 refresh 토큰입니다"),
    INVALID_EMAIL_OTP(401, 806, "잘못된 인증번호입니다"),
    EXPIRED_EMAIL_OTP(401, 807, "만료된 인증번호입니다"),
    MISSING_COOKIE(401, 808, "인증 쿠키가 없습니다"),
    INVALID_ADMIN_CREDENTIALS(401, 809, "아이디 또는 비밀번호가 올바르지 않습니다"),

    // ----- 403 Forbidden (900~) -----
    FORBIDDEN(403, 900, "권한이 없습니다"),
    MEMBER_SUSPENDED(403, 901, "정지된 회원입니다"),
    MEMBER_WITHDRAWN(403, 902, "탈퇴한 회원입니다"),
    NOT_PRODUCT_OWNER(403, 903, "해당 상품의 판매자만 접근할 수 있습니다"),
    NOT_TRADE_PARTICIPANT(403, 904, "거래 당사자만 접근할 수 있습니다"),
    CHAT_BLOCKED_FOR_SUSPENDED(403, 905, "정지된 유저는 채팅방을 생성할 수 없습니다"),
    ADMIN_REQUIRED(403, 906, "관리자 권한이 필요합니다"),
    SUPER_ADMIN_REQUIRED(403, 907, "슈퍼 관리자 권한이 필요합니다"),
    ALREADY_LOGGED_OUT(403, 908, "이미 로그아웃된 토큰입니다"),
    TRADE_CANCEL_NOT_ALLOWED(403, 909, "현재 단계에서는 거래 취소를 할 수 없습니다"),
    PRODUCT_RESERVED_DELETE_FORBIDDEN(403, 910, "예약 중인 상품은 삭제할 수 없습니다"),

    // ----- 404 Not Found (1000~) -----
    NOT_FOUND(404, 1000, "요청한 리소스를 찾을 수 없습니다"),
    MEMBER_NOT_FOUND(404, 1001, "회원을 찾을 수 없습니다"),
    PRODUCT_NOT_FOUND(404, 1002, "상품을 찾을 수 없습니다"),
    CATEGORY_NOT_FOUND(404, 1003, "카테고리를 찾을 수 없습니다"),
    TRADE_NOT_FOUND(404, 1004, "거래를 찾을 수 없습니다"),
    PAYMENT_NOT_FOUND(404, 1005, "결제를 찾을 수 없습니다"),
    SETTLEMENT_NOT_FOUND(404, 1006, "정산을 찾을 수 없습니다"),
    CHAT_ROOM_NOT_FOUND(404, 1007, "채팅방을 찾을 수 없습니다"),
    REVIEW_NOT_FOUND(404, 1008, "리뷰를 찾을 수 없습니다"),
    ADDRESS_NOT_FOUND(404, 1009, "배송지를 찾을 수 없습니다"),
    ACCOUNT_NOT_FOUND(404, 1010, "계좌를 찾을 수 없습니다"),
    WISH_NOT_FOUND(404, 1011, "찜 내역을 찾을 수 없습니다"),
    TERMS_NOT_FOUND(404, 1012, "약관을 찾을 수 없습니다"),
    ADMIN_NOT_FOUND(404, 1013, "관리자를 찾을 수 없습니다"),
    NOTICE_NOT_FOUND(404, 1014, "공지사항을 찾을 수 없습니다"),
    SHIPMENT_NOT_FOUND(404, 1015, "배송 정보를 찾을 수 없습니다"),
    MEMBERSHIP_NOT_FOUND(404, 1016, "멤버십 정보를 찾을 수 없습니다"),

    // ----- 409 Conflict (700~) -----
    ALREADY_EXIST_EMAIL(409, 700, "이미 가입된 이메일입니다"),
    ALREADY_EXIST_NICKNAME(409, 701, "이미 사용 중인 닉네임입니다"),
    ALREADY_EXIST_CHAT_ROOM(409, 702, "이미 동일한 채팅방이 존재합니다"),
    ALREADY_WISHED(409, 703, "이미 찜한 상품입니다"),
    ALREADY_REVIEWED(409, 704, "이미 리뷰를 작성했습니다"),
    ALREADY_PAID(409, 705, "이미 결제된 거래입니다"),
    ALREADY_SETTLED(409, 706, "이미 정산이 완료되었습니다"),
    ALREADY_CANCELED(409, 707, "이미 취소된 거래입니다"),
    CATEGORY_HAS_PRODUCTS(409, 708, "등록된 상품이 있어 카테고리를 삭제할 수 없습니다"),
    CATEGORY_HAS_CHILDREN(409, 709, "하위 카테고리가 있어 삭제할 수 없습니다"),
    DUPLICATE_CATEGORY_NAME(409, 710, "동일한 상위 카테고리 아래 중복된 이름입니다"),
    ACTIVE_TRADE_EXISTS(409, 711, "진행 중인 거래가 있습니다"),

    // ----- 429 Too Many Requests -----
    TOO_MANY_OTP_REQUESTS(429, 1100, "인증번호는 잠시 후 다시 요청할 수 있습니다"),
    CHAT_RATE_LIMITED(429, 1101, "채팅 전송이 일시 정지되었습니다"),

    // ----- 500 / 502 -----
    INTERNAL_SERVER_ERROR(500, 1500, "서버 내부 오류입니다"),
    EXTERNAL_API_ERROR(502, 1501, "외부 API 호출에 실패했습니다"),
    SOCIAL_AUTH_FAILED(502, 1502, "소셜 인증에 실패했습니다"),
    AI_PRICE_QUOTE_FAILED(502, 1503, "시세 조회 AI 처리에 실패했습니다"),
    TRANSLATION_FAILED(502, 1504, "번역 API 호출에 실패했습니다"),
    PAYMENT_GATEWAY_ERROR(502, 1505, "결제 서비스와 연결할 수 없습니다"),
    S3_UPLOAD_FAILED(502, 1506, "파일 업로드에 실패했습니다");

    private final int httpStatus;
    private final int code;
    private final String message;

    ErrorCode(int httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
