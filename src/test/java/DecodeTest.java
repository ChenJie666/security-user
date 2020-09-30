import cn.hutool.core.codec.Base64;
import org.junit.Test;

import java.nio.CharBuffer;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/30 15:04
 */
public class DecodeTest {

    @Test
    public void test(){
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzMSJdLCJ1c2VyX25hbWUiOiIzNyIsInNjb3BlIjpbIlJPTEVfQURNSU4iLCJST0xFX0FQUExJQ0FUSU9OIl0sImNyZSI6MTYwMTQzNzgxNCwiZXhwIjoxNjAxNDQ1MDE0LCJhdXRob3JpdGllcyI6WyIvY29udGVudHMvIiwiL2NvbnRlbnRzL3ZpZXcvKioiLCIvdXNlcnMvIiwiL3VzZXJzL3VwZGF0ZS8qKiIsIi9jb250ZW50cy91cGRhdGUvKioiLCJST0xFX2FkbWluIiwiL3VzZXJzL3ZpZXcvKioiLCIvdXNlcnMvaW5zZXJ0LyoqIiwiL2NvbnRlbnRzL2RlbGV0ZS8qKiIsIi9jb250ZW50cy9pbnNlcnQvKioiLCIvdXNlcnMvZGVsZXRlLyoqIiwiLyJdLCJqdGkiOiJkNWFhNTlmNi1hNDBlLTRlMDUtOTY5My03ZWUwYjhkMDFhMTUiLCJjbGllbnRfaWQiOiJjMSJ9.aA3UbzqhkjeEaHXBo7lWDGLfqN96_ZZH47sok25rR9GT2FL46IjMlJGgq5lWN-58ry44PPpu-AvTkmQqc6ynjxfptA4AvVJvXUS8vEVaTfzGJQXhbfEdj_DSvbiOpahg9qEpauAO1q6lJ2vYi7D-bimfZSjXxJL8kTyIC5nUprUQ5GNHpHXWJKh8B4WRULp2yszLc5wRgb_RZHaJD8_ts4uHnNHATos5vUgScCgnlxDP0Wju4JTECHU_ZmeT0SoD2m_YjHwt0o4HkE-qlDmlqqDw5XvFzIdWSkk9JfGqOIWo_uQVt1nEzShrVKy0wgE8W_YvYBGhtvjiQT5HCFxq7Q";

        int firstPeriod = token.indexOf('.');
        int lastPeriod = token.lastIndexOf('.');
        if (firstPeriod <= 0 || lastPeriod <= firstPeriod) {
            throw new IllegalArgumentException("JWT must have 3 tokens");
        }
        CharBuffer buffer = CharBuffer.wrap(token, 0, firstPeriod);

        buffer.limit(lastPeriod).position(firstPeriod + 1);

        byte[] decode = Base64.decode(buffer);
        String s = new String(decode);
        System.out.println(s);
    }

}
