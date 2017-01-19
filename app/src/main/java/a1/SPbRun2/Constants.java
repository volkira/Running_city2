package a1.SPbRun2;

/**
 * Created by FreeWind on 17.01.2017.
 */

public class Constants {
    public static class URL {
        // работает с эмулятором
        //private static final String HOST = "http://10.0.2.2:8080";
        // подключение реального устройства
        private static final String HOST = "http://192.168.1.226:8080";
        public static final String GET_QUESTION = HOST + "/quest/nextQuestion";
        public static final String GET_PROFILE = HOST + "/profile";
        public static final String POST_NEW_PROFILE = HOST + "/create";
    }
}
