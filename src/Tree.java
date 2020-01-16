import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tree {
    private final String node;

    private final List<Tree> children;

    Tree(String node, Tree... children) {
        this.node = node;
        this.children = Arrays.asList(children);
    }

    Tree(String node) {
        this.node = node;
        this.children = new ArrayList<>();
    }
    void print(Integer testNum){
        String outPath = "TestsOut/test" + testNum + ".txt";
        Path output = Paths.get(outPath);
        if (output.getParent() != null) {
            try {
                Files.createDirectories(output.getParent());
            } catch (IOException e) {
                System.out.println("Can't create output file");
                return;
            }
        }
        try (BufferedWriter writer = Files.newBufferedWriter(output, StandardCharsets.UTF_8)) {
            printTree(writer, 0);
        } catch (IOException e) {
            System.out.println("Can't write to file" + outPath + e.getMessage());
        }
    }
    void printTree(BufferedWriter writer, Integer level) throws IOException{
        for (int i = 0; i < level; i++){
            writer.write("________");
        }
        writer.write(node);
        writer.newLine();
        if (!children.isEmpty()){
            for (Tree child : children) {
                child.printTree(writer, level + 1);
            }
        }
    }
}
