import java.util.*;
public class Main {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    String string = scan.next();
    string = string + ('a'-'A');
    System.out.println(string);
    scan.close();
  }
}