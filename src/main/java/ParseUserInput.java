public class ParseUserInput {

    public int parseInput(String str) {
        //String userPartString = "";
        int userPartNumber;
        if (str.contains(" ")) {
            if (Character.isLetter(str.charAt(0))) {
                String[] parts = str.split("(?<=\\D)(?=\\d)");
                String s1 = parts[0];
                //userPartString = s1.substring(0, s1.length() - 1);git
                userPartNumber = Integer.parseInt(parts[1]);
            } else {
                String[] parts = str.split("(?<=\\d)(?=\\D)");
                String s1 = parts[1];
                //userPartString = s1.substring(1);
                userPartNumber = Integer.parseInt(parts[0]);
            }
        } else {
            if (Character.isLetter(str.charAt(0))) {
                String[] parts = str.split("(?<=\\D)(?=\\d)");
                //userPartString = parts[0];
                userPartNumber = Integer.parseInt(parts[1]);
            } else {
                String[] parts = str.split("(?<=\\d)(?=\\D)");
                //userPartString = parts[1];
                userPartNumber = Integer.parseInt(parts[0]);
            }
        }
        return userPartNumber;
    }

}
