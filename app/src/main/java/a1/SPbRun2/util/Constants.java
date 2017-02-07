package a1.SPbRun2.util;

/**
 * Created by FreeWind on 17.01.2017.
 */

public class Constants {
    public static class URL {
        // работает с эмулятором
        //private static final String HOST = "http://10.0.2.2:8080";
        // подключение реального устройства
        //private static final String HOST = "http://172.20.212.230:8080";
        private static final String HOST = "http://192.168.1.226:8080";
        // в сети
//        private static final String HOST = "https://cityquestserver.herokuapp.com";
        public static final String GET_QUESTION = HOST + "/quest/nextQuestion";
        public static final String GET_PROFILE = HOST + "/profile";
        public static final String POST_NEW_PROFILE = HOST + "/create";
        public static final String GET_QUEST_LIST = HOST + "/quest/list";
        public static final String GET_NEXT_QUESTION = HOST + "/quest/nextQuestion";
        public static final String GET_HINT = HOST + "/quest/hint";
        public static final String POST_ANSWER = HOST + "/quest/answer";
        public static final String GET_STATISTIC = HOST + "/quest/getStatistic";

        public static final int QUEST_LOADER_ID = 1;
        public static final int QUESTION_LOADER_ID = 2;
        public static final int HINT_LOADER_ID = 3;
        public static final int ANSWER_LOADER_ID = 4;
    }
}
