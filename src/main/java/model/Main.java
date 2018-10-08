/** Martyna Kania rysowanie spirali*/
package model;

import view.ViewController;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;


public class Main {
    static long tMed = 0;
    static long asymptoteMed = 0;
    static final int BENCHMARKLITERATION = 8;
    static final int averageIterations = 5;
    public static Integer startCount = 0;

    public static void main(String[] args) {
        String fileName = null;
        if (args.length < 3) {
            System.out.println("To little arguments");
            return;
        }
        char method = args[0].charAt(0);
        char mode = args[1].charAt(0);
        if (mode == 'N' || mode == 'B') {
            startCount = Integer.parseInt(args[2]);
        } else if (mode == 'F') {
            fileName = args[2];
        } else {
            System.out.println("Choose one of mode: B, N, F");
            return;
        }
        if (method != 'N' && method != 'J' && method != 'G') {
            System.out.println("Choose one of algorithm: G, N, J");
            return;
        }
            PointSet spiral = runMode(mode, method, fileName);
        if (mode == 'N' || mode == 'F') {
            spiral.showSpiral();
            ViewController.main(args);
        }
    }

    public static ArrayList<Point> prepare(String fileName, int pointCount) {

        if (fileName != null && !fileName.isEmpty()) {
            try {
                return loadFromFile(fileName);
            } catch (IOException e) {
                System.out.println("File can not be open.");
                return null;
            }
        } else {
            return randPoints(pointCount, 1000);
        }
    }

    private static PointSet runMode(char mode, char method, String fileName) {
        PointSet set = null;
        if (mode == 'N' || mode == 'F') {
            int pointCount = startCount;
            set = new PointSet(prepare(fileName, pointCount));
            generateSpiralByChosenStrategy(set, method);
            return set;
        }
        if (mode == 'B') {
            int pointCount = startCount;
            long time = 0, startTime, endTime;
            ArrayList<Long> times = new ArrayList<>();
            warmUp(method);
            System.out.println("benchmark start");
            for (int i = 0; i < BENCHMARKLITERATION; ++i) {
                System.out.println("step " + i + " point count " + pointCount);
                pointCount = getNextPointCount(method, pointCount);
                for (int j = 0; j < averageIterations; ++j) {
                    set = new PointSet(randPoints(pointCount, 1000));
                    startTime = System.currentTimeMillis();
                    generateSpiralByChosenStrategy(set, method);
                    endTime = System.currentTimeMillis();
                    time += endTime - startTime;
                }
                time /= averageIterations;
                times.add(time);
            }
            printTable(times, method);
        }
        return set;
    }

    public static ArrayList<Point> loadFromFile(String fileName) throws IOException {
        Path path = FileSystems.getDefault().getPath("../coordinatesFiles", fileName);
        ArrayList<Point> pointList = new ArrayList<>();
        Files.lines(path).forEach(line -> pointList.add(new Point(line)));
        return pointList;
    }

    private static ArrayList<Point> randPoints(int quantity, int range) {
        int x, y;
        Random rand = new Random();
        ArrayList<Point> pointList = new ArrayList<>();
        for (int i = 0; i < quantity; ++i) {
            x = (rand.nextInt(range) + 1) / 10;
            y = (rand.nextInt(range) + 1) / 10;
            pointList.add(new Point(x, y));
        }
        return pointList;
    }

    private static void generateSpiralByChosenStrategy(PointSet set, char methodNumber) {
        switch (methodNumber) {
            case 'N':
                set.getSpiralByPermutation();
                break;
            case 'J':
                set.getSpiralJarvisAlgorithm();
                break;
            case 'G':
                set.getSpiralGrahamAlgorithm();
                break;
            default:
                System.out.println("First argument is incorrect, give method symBol {N,J,G}");
                return;
        }
    }

    private static int getNextPointCount(char method, int pointCount) {
        if (method == 'N') {
            pointCount += 1;
        } else
            pointCount += startCount;
        return pointCount;
    }

    private static void printTable(ArrayList<Long> times, char method) {
        System.out.println("points" + '\t' + "time" + '\t' + "qn" + '\t' + "Tn");
        int pointCount = startCount;
        count_tn(times, method);
        double qn, tmp;
        for (Long time : times) {
            pointCount = getNextPointCount(method, pointCount);
            tmp = (tMed * count_asymptote(method, pointCount));
            qn = time * asymptoteMed / tmp;
            DecimalFormat dec = new DecimalFormat("#0.00");
            System.out.println(Integer.toString(pointCount) + '\t' + Long.toString(time) + '\t' + dec.format(qn) + '\t' + Long.toString(count_asymptote(method, pointCount)));
        }
    }

    private static void count_tn(ArrayList<Long> times, char method) {
        int pointCount = startCount;
        int i;
        for (i = 0; i < BENCHMARKLITERATION / 2; ++i) {
            pointCount = getNextPointCount(method, pointCount);
        }
        asymptoteMed = count_asymptote(method, pointCount);
        tMed = times.get(i - 1);
    }

    private static long count_asymptote(char method, int pointCount) {
        if (method == 'N') {
            return factorial(pointCount) * pointCount * pointCount;
        } else if (method == 'J') {
            return pointCount * pointCount;
        } else
            return (long) (pointCount * Math.log(pointCount));
    }

    private static long factorial(int x) {
        long factorial = 1;
        for (int i = 2; i <= x; ++i) {
            factorial *= i;
        }
        return factorial;
    }

    private static void warmUp(char method) {//basically do nothing
        System.out.println("Warm up");
        int iteration = BENCHMARKLITERATION/3+1, pointCount = 0;
        for (int j = 0; j < iteration; ++j) {
            pointCount = startCount;
            for (int i = 0; i < 3; ++i) {
                pointCount = getNextPointCount(method, pointCount);
            }
            PointSet set = new PointSet(randPoints(pointCount, 1000));
            long startTime = System.currentTimeMillis();
            generateSpiralByChosenStrategy(set, method);
            System.currentTimeMillis();
            long time = System.currentTimeMillis() - startTime;//t
            count_asymptote(method, pointCount);
        }
    }
}

