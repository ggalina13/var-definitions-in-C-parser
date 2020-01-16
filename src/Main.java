
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws IOException {
        //String s = "int a, **b, c; double k;";
        final int FIRST_TEST = 1, LAST_TEST = 20;
        for (int testNum = FIRST_TEST; testNum <= LAST_TEST; testNum++){
            String curPath = "Tests/test" + testNum + ".txt";
            try {
                String curTestString = Files.readString(Paths.get(curPath));
                new Parser().parse(curTestString + "$").print(testNum);
                System.out.println("Test" + testNum + " completed successfully");
            }
            catch (AssertionError e){
                System.out.println("Error in test" + testNum + " " +  e);
            }
            catch (NoSuchFileException e){
                System.out.println("File " + curPath + " doesn't exist " + e);
            }
            catch (AccessDeniedException e){
                System.out.println("Can't write to" + curPath + " " + e);
            }
            catch (ParseException e){
                System.out.println("Error in test" + testNum + " " +  e);
            }
        }
        /*BufferedWriter writer = Files.newBufferedWriter(Paths.get("Tests/test20.txt"), StandardCharsets.UTF_8);
        writer.write("int ");
        for (int i = 0; i < 50; i++){
            for (int j = 0; j <= i; j++)
                writer.write("*");
            writer.write("a" + i + ", ");
        }
        writer.flush();*/
        //Tree tree = new Parser().parse(s);
        //tree.print();
    }
}
