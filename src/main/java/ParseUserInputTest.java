

public class ParseUserInputTest {

    private final String partString;
    private final int partInt;

    public ParseUserInputTest(String pieceString, int pieceInt) {
        this.partString = pieceString;
        this.partInt = pieceInt;
    }

    public String getPartString() {
        return partString;
    }

    public int getPartInt() {
        return partInt;
    }

    public static ParseUserInputTest parseUserInputTest(String str) {
        String userPartString = "";
        int userPartNumber = 0;
            if (str.contains(" ")) {
                if (Character.isLetter(str.charAt(0))) {
                    String[] parts = str.split("(?<=\\D)(?=\\d)");
                    String s1 = parts[0];
                    userPartString = s1.substring(0, s1.length() - 1);
                    userPartNumber = Integer.parseInt(parts[1]);
                } else {
                    String[] parts = str.split("(?<=\\d)(?=\\D)");
                    String s1 = parts[1];
                    userPartString = s1.substring(1);
                    userPartNumber = Integer.parseInt(parts[0]);
                }
            } else {
                if (Character.isLetter(str.charAt(0))) {
                    String[] parts = str.split("(?<=\\D)(?=\\d)");
                    userPartString = parts[0];
                    userPartNumber = Integer.parseInt(parts[1]);
                } else {
                    String[] parts = str.split("(?<=\\d)(?=\\D)");
                    userPartString = parts[1];
                    userPartNumber = Integer.parseInt(parts[0]);
                }
            }
            userPartString = userPartString.substring(0,1).toUpperCase() + userPartString.substring(1);

        return new ParseUserInputTest(userPartString, userPartNumber);
    }

}

/*
public class uuuu {

    //private ParseUserInput parse;

    */
/*public void mm() {
        ParseUserInput parseUserInput = ParseUserInput.parseUserInput("prod123");
        String s = parseUserInput.getPartString();
        int x = parseUserInput.getPartInt();
    }*//*


    public static void main(String[] args) {
        ParseUserInput parseUserInput = ParseUserInput.parseUserInput("prod123");
        String s = parseUserInput.getPartString();
        int x = parseUserInput.getPartInt();


    }
}*/
