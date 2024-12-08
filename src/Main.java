import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String []args) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        CountryMap countryMap=new CountryMap();

        System.out.println("Please enter the name of the file you want to open: ");
        String fileName = sc.nextLine();

        countryMap.readMap(fileName);


    }
}
