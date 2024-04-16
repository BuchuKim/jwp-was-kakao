package webserver.view;

import java.io.IOException;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

public class ViewResolver {
    private static final String VIEW_PREFIX = "/templates/";
    private static final String VIEW_SUFFIX = ".html";
    private static final Handlebars HANDLEBARS;

    static {
        TemplateLoader templateLoader = new ClassPathTemplateLoader("/templates");
        templateLoader.setPrefix(VIEW_PREFIX);
        templateLoader.setSuffix(VIEW_SUFFIX);
        HANDLEBARS = new Handlebars(templateLoader);
    }

    public static String resolve(final String viewName, final Object model) throws IOException {
        Template template = HANDLEBARS.compile(viewName);
        return template.apply(model);
    }

    public static String resolve(final String viewName) throws IOException {
        Template template = HANDLEBARS.compile(viewName);
        return template.apply(null);
    }
}
