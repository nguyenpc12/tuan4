import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DirectoryToXML {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập đường dẫn thư mục:");
        String directoryPath = scanner.nextLine();
        File directory = new File(directoryPath);

        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Đường dẫn không tồn tại hoặc không phải là thư mục.");
            return;
        }

        try {
            FileWriter writer = new FileWriter("directory_tree.xml");
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<directory_tree>\n");
            writeXMLForDirectory(directory, writer, 1);
            writer.write("</directory_tree>");
            writer.close();
            System.out.println("Cây thư mục đã được lưu trong tệp directory_tree.xml");
        } catch (IOException e) {
            System.out.println("Đã xảy ra lỗi khi ghi vào tệp.");
            e.printStackTrace();
        }
    }

    private static void writeXMLForDirectory(File directory, FileWriter writer, int depth) throws IOException {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    writeIndent(writer, depth);
                    writer.write("<directory name=\"" + file.getName() + "\">\n");
                    writeXMLForDirectory(file, writer, depth + 1);
                    writeIndent(writer, depth);
                    writer.write("</directory>\n");
                } else {
                    writeIndent(writer, depth);
                    writer.write("<file name=\"" + file.getName() + "\" />\n");
                }
            }
        }
    }

    private static void writeIndent(FileWriter writer, int depth) throws IOException {
        for (int i = 0; i < depth; i++) {
            writer.write("    ");
        }
    }
}
