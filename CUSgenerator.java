import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader; 

public class CUSgenerator {
    int frames;
    String inputFileName;
    String outputFileName;
    String folderPath;
    public static void main(String[] args) {
        CUSgenerator generator = new CUSgenerator();
        generator.folderPath = System.getProperty("user.dir");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the csv file with custom motion files to generate (do not include the .csv suffix): ");
        generator.inputFileName = sc.next();
        System.out.println("Enter the name of the output custom motion files (a numeric suffix will be added to the end to distinguish each trial): ");
        generator.outputFileName = sc.next();
        sc.close();

        double[][] inputFile = generator.readInputFile();
        generator.generateFile2(inputFile);


        /*Scanner sc = new Scanner(System.in);
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
        generator.generateFile(); */
    }

    /*public void generateFile() {
        double[][] line = new double[frames][7];
        line[0] = new double[]{1, startingXPos, startingYPos, startingZPos, startingXRot, startingYRot, startingZRot};
        for (int i=1; i<frames; i++) {
            double newXPos = startingXPos + (startingVelocity * timePerFrame * i) + (0.5 * acceleration * Math.pow((timePerFrame * i), 2));
            line[i] = new double[]{i+1, newXPos, startingYPos, startingZPos, startingXRot, startingYRot, startingZRot};
        }

        try {
            BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(folderPath + "/" + inputFileName + ".cus"));
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
    }*/

    public void generateFile2(double[][] inputFile) {
        double acceleration = 0;
        double startingVelocity = 0;
        double startingXPos = 0;
        double startingYPos = 0;
        double startingZPos = 0;
        double startingXRot = 0;
        double startingYRot = 0;
        double startingZRot = 0;
        int frames = 0;
        double timePerFrame = 0;

        for (int b=0; b<inputFile.length; b++) {
            startingXPos = inputFile[b][0];
            startingYPos = inputFile[b][1];
            startingZPos = inputFile[b][2];
            startingXRot = inputFile[b][3];
            startingYRot = inputFile[b][4];
            startingZRot = inputFile[b][5];
            startingVelocity = inputFile[b][6];
            acceleration = inputFile[b][7];
            frames = (int) inputFile[b][8];
            timePerFrame = inputFile[b][9];

            double[][] line = new double[frames][7];
        line[0] = new double[]{1, startingXPos, startingYPos, startingZPos, startingXRot, startingYRot, startingZRot};
        for (int z=1; z<frames; z++) {
            double newXPos = startingXPos + (startingVelocity * timePerFrame * z) + (0.5 * acceleration * Math.pow((timePerFrame * z), 2));
            line[z] = new double[]{z+1, newXPos, startingYPos, startingZPos, startingXRot, startingYRot, startingZRot};
        }

            try {
                int trialNum = b+1;
                BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(folderPath + "/" + outputFileName + "-" + trialNum + ".cus"));
                for (int i=0; i<line.length; i++) {
                    for (int j=0; j<7; j++) {
                        writer.write(line[i][j] + " ");
                    }
                    writer.write("\n");
                }
                writer.close();
            } catch (Exception e) {
                System.out.println("Error while generating file: "+ e.getMessage());
            }
        } 
    }

    public double[][] readInputFile() {
        int lines = 0;
        int columns = 0;
        double[][] ret = null;
        String line;
        try {
            BufferedReader reader1 = new BufferedReader(new FileReader(folderPath + "/" + inputFileName + ".csv"));
            line = reader1.readLine();
            String[] content = line.split(",");
            columns = content.length;
            lines++;
            while ((line = reader1.readLine()) != null) lines++;
            reader1.close();
            ret = new double[lines][columns];

            BufferedReader reader2 = new BufferedReader(new FileReader(folderPath + "/" + inputFileName + ".csv"));
            int row = 0;
            while ((line = reader2.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= columns) {
                    for (int j=0; j<columns; j++) {
                    ret[row][j] = Double.parseDouble(values[j]);
                    }
                    row++;
                }
            }
            for (double[] rows : ret) {
                for (double value : rows) {
                    System.out.print(value + " ");
                }
                System.out.println();
            }
            reader2.close();
        } catch (Exception e) {
            System.out.println("Error while reading input file: " + e.getMessage());
        }
        return ret;
    }
}