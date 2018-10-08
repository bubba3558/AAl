/** Martyna Kania rysowanie spirali*/
package model;

import view.ViewController;

import java.util.*;

public class PointSet {
    private ArrayList<Point> allPoint;
    private ArrayList<Point> spiral = new ArrayList<>();


    public PointSet(ArrayList<Point> points) {
        allPoint = new ArrayList<>(points);
    }

    public static int findTheClosest(Point lastInSpiral, ArrayList<Point> convexHull) {
        int index = 0;
        double minDistance = Math.abs(lastInSpiral.x - convexHull.get(0).x) + Math.abs(lastInSpiral.y - convexHull.get(0).y);

        for (int i = 1; i < convexHull.size(); ++i) {
            double distance = Math.abs(lastInSpiral.x - convexHull.get(i).x) + Math.abs(lastInSpiral.y - convexHull.get(i).y);
            if (distance < minDistance) {
                minDistance = distance;
                index = i;
            }
        }
        return index;
    }

    public void getSpiralByPermutation() {
        spiral = NaiveAlgorithm.createSpiral(allPoint);
    }

    public void getSpiralJarvisAlgorithm() {
        spiral = JarvisAlgorithm.createSpiral(allPoint);
    }

    public void getSpiralGrahamAlgorithm() {
        spiral = (new Graham()).createSpiral(allPoint);
    }

    private void printSpiral() {
        for (Point point : spiral)
            System.out.println(point);
    }

    public void print() {
        for (Point point : allPoint)
            System.out.println(point);
    }

    public void showSpiral() {
        if (spiral == null) {
            ViewController.setText("It's impossible to create a spiral");
        } else {
            ViewController.setPointList(spiral);
        }
    }

    public static int getCurveDirection(Point p0, Point p1, Point p2) {
        double dx0 = p1.getX() - p0.getX();
        double dx1 = p2.getX() - p1.getX();
        double dy0 = p1.getY() - p0.getY();
        double dy1 = p2.getY() - p1.getY();
        if (dx0 * dy1 - dx1 * dy0 > 0.001) {
            return 1;
        } else if (dx0 * dy1 - dx1 * dy0 < -0.01) {
            return -1;
        } else {
            return 0;
        }
    }

    public static boolean linesCrossing(Point p1, Point q1, Point p2, Point q2) {
        int o1 = getCurveDirection(p1, q1, p2);
        int o2 = getCurveDirection(p1, q1, q2);
        int o3 = getCurveDirection(p2, q2, p1);
        int o4 = getCurveDirection(p2, q2, q1);
        if (o1 != o2 && o3 != o4)
            return true;
        return false;
    }

}
