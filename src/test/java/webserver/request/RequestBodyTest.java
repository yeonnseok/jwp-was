package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static controller.support.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static support.Constants.*;

public class RequestBodyTest {
    private static final String POST_REQUEST_BODY =
            "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
    private static final int BODY_CONTENT_LENGTH = 93;

    @DisplayName("HttpRequestBody 생성")
    @Test
    void of() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(POST_REQUEST_BODY.getBytes())));
        RequestBody requestBody = RequestBody.of(br, BODY_CONTENT_LENGTH);

        assertThat(requestBody.getParameterValue(USER_ID)).isEqualTo(TEST_USER_ID);
        assertThat(requestBody.getParameterValue(USER_PASSWORD)).isEqualTo(TEST_USER_PASSWORD);
        assertThat(requestBody.getParameterValue(USER_NAME)).isEqualTo(TEST_USER_NAME);
        assertThat(requestBody.getParameterValue(USER_EMAIL)).isEqualTo(TEST_USER_EMAIL);
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> requestBody.getParameterValue("NotContains"));
    }
}
