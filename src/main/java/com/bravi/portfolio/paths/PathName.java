package com.bravi.portfolio.paths;

public final class PathName {

    private PathName() {
    }

    private static final String BASE_PATH = "/api/v1";

    public static final String PERSONA = BASE_PATH + "/persona";

    public static final String CONTACT = BASE_PATH + "/contact";

    public static final String JOB = BASE_PATH + "/job";

    public static final String TECHNOLOGY = BASE_PATH + "/technology";

    public static final String ABOUT = BASE_PATH + "/about";

    public static final String PROJECT = BASE_PATH + "/project";

    public static final String EDUCATION = BASE_PATH + "/education";

    public static final String LOGIN = "/login";

    public static final String PATH_ID = "/{id}";


}
