package service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class UserServiceTest {
    @Test
    void 쿼리파라미터_정보로_유저객체를_저장한다() {
        final UserService userService = new UserService();
        final Map<String, String> testUserMap = generateTestUserOfId("testId");
        
        userService.createUser(testUserMap);
        
        assertNotNull(userService.getUser("testId"));
    }
    
    @Test
    void 이미_존재하는_유저일_경우_예외가_발생한다() {
        final UserService userService = new UserService();
        
        final Map<String, String> testUserMap1 = generateTestUserOfId("test");
        userService.createUser(testUserMap1);
        
        final Map<String, String> testUserMap2 = generateTestUserOfId("test");
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(testUserMap2));
    }
    
    private Map<String, String> generateTestUserOfId(final String id) {
        Map<String, String> testUserMap = new HashMap<>();
        testUserMap.put("userId", id);
        testUserMap.put("password", "testPassword");
        testUserMap.put("name", "testName");
        testUserMap.put("email", "testEmail@email.com");
        return testUserMap;
    }
}
