import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        String r = "1234,,,657,,,58,T2,";
        // Using split with a negative limit to include trailing empty strings
        String[] data = r.split(",",-1);

        // Check if the last element is empty and remove it if it is

        System.out.println(data);
    }
}
