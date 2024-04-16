package webserver.view;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import controller.dto.UserListDto;
import model.User;

public class ViewResolverTest {
    @Test
    void 유저목록_뷰를_동적으로_구성한다() throws IOException {
        // given
        final TemplateLoader templateLoader = new ClassPathTemplateLoader("/templates");
        templateLoader.setSuffix(".html");

        Handlebars handlebars = new Handlebars(templateLoader);
        Template template = handlebars.compile("user/list");

        // when
        List<User> users = List.of(
            new User("user1", "password", "유저1", "test1@email.com"),
            new User("user2", "password", "유저2", "test2@email.com"),
            new User("user3", "password", "유저3", "test3@email.com"));
        String userHtml = ViewResolver.resolve("user/list", new UserListDto(users));

        // then
        users.forEach(user -> {
            assertAll(
                () -> assertThat(userHtml).contains(user.getUserId()),
                () -> assertThat(userHtml).contains(user.getName()),
                () -> assertThat(userHtml).contains(user.getEmail()));
        });
    }
}
