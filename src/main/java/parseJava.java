public class parseJava {

    public String parseString(String str) {
        String s = "";
        if (str.contains(" ")) {
            if (Character.isLetter(str.charAt(0))) {
                String[] parts = str.split("(?<=\\D)(?=\\d)");
                String s1 = parts[0];
                s = s1.substring(0, s1.length() - 1);
            } else {
                String[] parts = str.split("(?<=\\d)(?=\\D)");
                String s1 = parts[1];
                s = s1.substring(1);
            }
        } else {
            if (Character.isLetter(str.charAt(0))) {
                String[] parts = str.split("(?<=\\D)(?=\\d)");
                s = parts[0];
            } else {
                String[] parts = str.split("(?<=\\d)(?=\\D)");
                s = parts[1];
            }
        }
        s = s.substring(0,1).toUpperCase() + s.substring(1);
        return s;
    }

    public int parseInt(String str) {
        int x = 0;
        if (str.contains(" ")) {
            if (Character.isLetter(str.charAt(0))) {
                String[] parts = str.split("(?<=\\D)(?=\\d)");
                /*String s1 = parts[1];
                s = s1.substring(0, s1.length() -1);*/
                x = Integer.parseInt(parts[1]);
            } else {
                String[] parts = str.split("(?<=\\d)(?=\\D)");
                /*String s1 = parts[1];
                s = s1.substring(1);*/
                x = Integer.parseInt(parts[0]);
            }
        } else {
            if (Character.isLetter(str.charAt(0))) {
                String[] parts = str.split("(?<=\\D)(?=\\d)");
                x = Integer.parseInt(parts[1]);
            } else {
                String[] parts = str.split("(?<=\\d)(?=\\D)");
                x = Integer.parseInt(parts[0]);
            }
        }
        return x;
    }
}
