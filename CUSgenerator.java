import java.util.Scanner;
import java.io.BufferedWriter;

public class CUSgenerator {
    int frames;
    double acceleration;
    double startingVelocity;
    double startingXPos;
    double startingYPos;
    double startingZPos;
    double startingXRot;
    double startingYRot;
    double startingZRot;
    String fileName;
    String folderPath;
    double timePerFrame;
    public static void main(String[] args) {
        CUSgenerator generator = new CUSgenerator();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of frames to generate: ");
        generator.frames = sc.nextInt();
        System.out.println("Enter the acceleration rate: ");
        generator.acceleration = sc.nextDouble();
        System.out.println("Enter the starting velocity: ");
        generator.startingVelocity = sc.nextDouble();
        System.out.println("Enter the time per frame: ");
        generator.timePerFrame = sc.nextDouble();
        System.out.println("Enter the starting X,Y,Z position separated by commas (for example: 5,10,15): ");
        String[] startingPos = sc.next().split(",");
        generator.startingXPos = Double.parseDouble(startingPos[0]);
        generator.startingYPos = Double.parseDouble(startingPos[1]);
        generator.startingZPos = Double.parseDouble(startingPos[2]);
        System.out.println("Enter the starting X,Y,Z rotation separated by commas (for example: 0,1,2): ");
        String[] startingRot = sc.next().split(",");
        generator.startingXRot = Double.parseDouble(startingRot[0]);
        generator.startingYRot = Double.parseDouble(startingRot[1]);
        generator.startingZRot = Double.parseDouble(startingRot[2]);
        System.out.println("Enter the desired file name: ");
        generator.fileName = sc.next();
        generator.folderPath = System.getProperty("user.dir");
        System.out.println("path = "+ generator.folderPath); // delete
        sc.close();
        generator.generateFile();
    }

    public void generateFile() {
        double[][] line = new double[frames][7];
        line[0] = new double[]{1, startingXPos, startingYPos, startingZPos, startingXRot, startingYRot, startingZRot};
        for (int i=1; i<frames; i++) {
            double newXPos = startingXPos + (startingVelocity * timePerFrame * i) + (0.5 * acceleration * Math.pow((timePerFrame * i), 2));
            line[i] = new double[]{i+1, newXPos, startingYPos, startingZPos, startingXRot, startingYRot, startingZRot};
        }

        try {
            BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(folderPath + "/" + fileName + ".cus"));
            for (int i=0; i<line.length; i++) {
                for (int j=0; j<7; j++) {
                    writer.write(line[i][j] + " ");
                }
                writer.write("\n");
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Error while generating file: " + e.getMessage());
        }
    }
}