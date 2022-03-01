package com.company.lesson45;

import com.company.server.*;
import com.sun.net.httpserver.HttpExchange;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.*;

import static java.util.stream.Collectors.joining;

public class Lesson45Server extends BasicServer {
    private final static Configuration freemarker = initFreeMarker();
    public static final String MAIL = "userEmail";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    private List<User> userList = new ArrayList<>();


    public Lesson45Server(String host, int port) throws IOException {
        super(host, port);
        registerGet("/sample", this::freemarkerSampleHandler);
        registerGet("/books", this::freemarkerBookHandler);
        registerGet("/user", this::freemarkerUserHandler);
        registerGet("/info", this::freemarkerInfoHandler);

        registerGet("/login", this::loginGet);
        registerPost("/login", this::loginPost);

        registerGet("/register", this::RegisterGetRequest);
        registerPost("/register", this::RegisterPost);
        registerGet("/session", this::sessionHandler);
        registerGet("/profiles", this::ProfilesGetHandler);
        registerGet("/infoPage", this::InfoPageHandler);
        registerGet("/errorLogin", this::ErrorLoginHandler);
    }


    private void ErrorLoginHandler(HttpExchange exchange) {
        Path path = makeFilePath("errorLogin.html");
        sendFile(exchange, path, ContentType.TEXT_HTML);
    }

    private void InfoPageHandler(HttpExchange exchange) {
        renderTemplate(exchange,"infoPage.html", getPageDataModel());
    }

    private InfoPage getPageDataModel() {
        return new InfoPage();
    }

    private void ProfilesGetHandler(HttpExchange exchange) {
        Path path = makeFilePath("profiles.html");
        sendFile(exchange, path,ContentType.TEXT_HTML);
    }

    protected void registerPost(String route, RouteHandler handler) {
        getRoutes().put("POST " + route, handler);
    }

    private void loginPost(HttpExchange exchange) {
        String raw = getBody(exchange);
        // преобразуем данные в формате form-urlencoded,
        // обратно в читаемый вид.
        Map<String, String> parsed = Utils.parseUrlEncoded(raw, "&");
        // отправим данные обратно пользователю,
        // что бы показать, что мы обработали запрос
        if (isValidUser(parsed)){
            renderTemplate(exchange, "profiles.html", parsed);
            return;
        }
        redirect303(exchange,"/errorLogin");

    }
    private void RegisterPost(HttpExchange exchange) {
        String cType = getContentType(exchange);
        String raw = getBody(exchange);
        // преобразуем данные в формате form-urlencoded,
        // обратно в читаемый вид.
        Map<String, String> parsed = Utils.parseUrlEncoded(raw, "&");
        // отправим данные обратно пользователю,
        // что бы показать, что мы обработали запрос
        if (isUserExist(parsed.get(MAIL))){
            renderTemplate(exchange, "error.html", parsed);
            return;
        }
        User user= registrationUser(parsed);
        renderTemplate(exchange,"infoPage.html", user);

    }
    private User registrationUser(Map<String, String> parsed) {
        User newUser = User.makeUser(parsed.get(NAME),parsed.get(MAIL), parsed.get(LOGIN), parsed.get(PASSWORD));
        userList.add(newUser);
        return newUser;
    }
    private boolean isValidUser(Map<String, String> parsed) {
        Optional<User> user = getUserByLogin(parsed.get(LOGIN));
        if (!user.isPresent()) {
            return false;
        }
        if (!user.get().getPassword().equals(parsed.get(PASSWORD))) {
            return false;
        }


        return true;
    }
    private Optional<User> getUserByLogin(String login) {
        for(User user: userList) {
            if(user.getLogin().equals(login)) {
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }

    private boolean isUserExist(String email) {
        return userList.stream().anyMatch(user -> user.getEmail().equals(email));
    }

    public static String getContentType(HttpExchange exchange) {
        return exchange.getRequestHeaders()
                .getOrDefault("Content-Type", List.of(""))
                .get(0);
    }

    protected String getBody(HttpExchange exchange) {
        InputStream input = exchange.getRequestBody();
        Charset utf8 = StandardCharsets.UTF_8;
        InputStreamReader isr = new InputStreamReader(input, utf8);
        // сейчас мы предполагаем, что клиент
        // отправляет текстовые данные
        try (BufferedReader reader = new BufferedReader(isr)) {
            return reader.lines().collect(joining(""));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    private static Configuration initFreeMarker() {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
            // путь к каталогу в котором у нас хранятся шаблоны
            // это может быть совершенно другой путь, чем тот, откуда сервер берёт файлы
            // которые отправляет пользователю
            cfg.setDirectoryForTemplateLoading(new File("data"));

            // прочие стандартные настройки о них читать тут
            // https://freemarker.apache.org/docs/pgui_quickstart_createconfiguration.html
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
            cfg.setFallbackOnNullLoopVariable(false);
            return cfg;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    protected void redirect303(HttpExchange exchange, String path) {
        try {
            exchange.getResponseHeaders().add("Location", path);
            exchange.sendResponseHeaders(303, 0);
            exchange.getResponseBody().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void freemarkerSampleHandler(HttpExchange exchange) {
        renderTemplate(exchange, "sample.html", getSampleDataModel());
    }

    private void freemarkerBookHandler(HttpExchange exchange) {
        renderTemplate(exchange, "books.ftl", getBooks());
    }

    private void freemarkerUserHandler(HttpExchange exchange) {
        renderTemplate(exchange, "user.ftl", getDataModelUser());
    }

    private void freemarkerInfoHandler(HttpExchange exchange) {
        renderTemplate(exchange, "info.ftl", getDataModelInfo());

    }
    private void loginGet(HttpExchange exchange) {
        // запрос идёт на /login, но мы вернём страничку
        // из файла login.ftl
        Path path = makeFilePath("login.ftl");
        sendFile(exchange, path, ContentType.TEXT_HTML);
    }

    private void RegisterGetRequest(HttpExchange exchange) {
        Path path = makeFilePath("register.ftl");
        sendFile(exchange,path,ContentType.TEXT_HTML);
    }

    protected void renderTemplate(HttpExchange exchange, String templateFile, Object dataModel) {
    try {
        // загружаем шаблон из файла по имени.
        // шаблон должен находится по пути, указанном в конфигурации
        Template temp = freemarker.getTemplate(templateFile);

        // freemarker записывает преобразованный шаблон в объект класса writer
        // а наш сервер отправляет клиенту массивы байт
        // по этому нам надо сделать "мост" между этими двумя системами

        // создаём поток который сохраняет всё, что в него будет записано в байтовый массив
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // создаём объект, который умеет писать в поток и который подходит для freemarker
        try (OutputStreamWriter writer = new OutputStreamWriter(stream)) {

            // обрабатываем шаблон заполняя его данными из модели
            // и записываем результат в объект "записи"
            temp.process(dataModel, writer);
            writer.flush();

            // получаем байтовый поток
            var data = stream.toByteArray();

            // отправляем результат клиенту
            sendByteData(exchange, ResponseCodes.OK, ContentType.TEXT_HTML, data);
        }
    } catch (IOException | TemplateException e) {
        e.printStackTrace();
    }
}

    private SampleDataModel getSampleDataModel() {
        // возвращаем экземпляр тестовой модели-данных
        // которую freemarker будет использовать для наполнения шаблона
        return new SampleDataModel();
    }
    private Book getBooks() {
        // возвращаем экземпляр тестовой модели-данных
        // которую freemarker будет использовать для наполнения шаблона
        return new Book();
    }

    private User getDataModelUser() {
        // возвращаем экземпляр тестовой модели-данных
        // которую freemarker будет использовать для наполнения шаблона
        return new User();
    }

    private InfoDataModel getDataModelInfo() {
        // возвращаем экземпляр тестовой модели-данных
        // которую freemarker будет использовать для наполнения шаблона
        return new InfoDataModel();
    }

    private RegisterDataModel getRegisterDataModel() {
        // возвращаем экземпляр тестовой модели-данных
        // которую freemarker будет использовать для наполнения шаблона
        return new RegisterDataModel();
    }

//    private ProfileDataModels getProfileDataModel() {
//        // возвращаем экземпляр тестовой модели-данных
//        // которую freemarker будет использовать для наполнения шаблона
//        return new ProfileDataModels();

    private void setCookie(HttpExchange exchange, Cookie response) {
        exchange.getResponseHeaders()
                .add("Set-Cookie", response.toString());
    }

    private String getCookies(HttpExchange exchange) {
        return exchange.getRequestHeaders().getOrDefault("Cookie", List.of("")).get(0);
    }

    private void sessionHandler(HttpExchange exchange) {
        Map<String, Object> data = new HashMap<>();
        String name = "times";

// получим cookie от клиента
        String cookieStr = getCookies(exchange);
        Map<String, String> cookies = Cookie.parse(cookieStr);
// если есть значение, то преобразуем в число
// если нет значения, то установим его в "0"
        String cookieValue = cookies.getOrDefault(name, "0");
        int times = Integer.parseInt(cookieValue) + 1;
// создадим cookie с новым значением
        Cookie response = Cookie.of(name, times);
// обязательно его отправим обратно на клиент
        setCookie(exchange, response);
// отрендерим страницу
        data.put(name, times);
        data.put("cookies", cookies);

        renderTemplate(exchange, "cookies.html", data);
    }


    }


