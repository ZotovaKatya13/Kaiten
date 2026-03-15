package constants;

public class Methods  {
    public static String deleteColumnEndpoint(Constants constants, String boardId, String columnId) {
        return constants.BOARDS + "/" + boardId + "/" + constants.COLUMNS + "/" + columnId;
    }
}
