/** Martyna Kania rysowanie spirali*/
package model;

import java.util.ArrayList;
import java.util.Collections;

public class Graham {
    private ArrayList<Point> convexHull;
    private ArrayList<Point> outsideSpiral;
    private ArrayList<Point> spiral = new ArrayList<>();

    public ArrayList<Point> createSpiral(ArrayList<Point> list) {
        outsideSpiral=list;
        do {
            prepareToAlgorithm(outsideSpiral, outsideSpiral.get(JarvisAlgorithm.countTheLowestIndex(outsideSpiral)));
            outsideSpiral = new ArrayList<>();
            for (int i = 0; i < convexHull.size() - 2; ) {
                if (PointSet.getCurveDirection(convexHull.get(i), convexHull.get(i + 1), convexHull.get(i + 2)) < 0) {
                    outsideSpiral.add(convexHull.remove(i + 1));
                    if (i > 0) i = i - 1;
                } else if (PointSet.getCurveDirection(convexHull.get(i), convexHull.get(i + 1), convexHull.get(i + 2)) == 0) {
                    if (convexHull.get(i).countDistanceTo(convexHull.get(i + 2)) < convexHull.get(i + 1).countDistanceTo(convexHull.get(i + 2))) {
                        Collections.swap(convexHull, i, i + 1);
                        if (i > 0) i = i - 1;
                    } else
                        ++i;
                } else ++i;
            }
            joinConvexToSpiral();
        } while (outsideSpiral.size() > 1);
        return spiral;
    }

    private void prepareToAlgorithm(ArrayList<Point> list, Point theLowest) {
        sortListByAngle(list, theLowest);
        deleteDuplicates(list);
        convexHull = new ArrayList<>();
        Point start = outsideSpiral.remove(JarvisAlgorithm.countTheLowestIndex(outsideSpiral));
        convexHull.add(start);
        convexHull.addAll(outsideSpiral);
    }

    private static double countCosines(Point p1, Point p2, Point p3) {
        double p12 = p1.countDistanceTo(p2);
        double p13 = p1.countDistanceTo(p3);
        double p23 = p2.countDistanceTo(p3);

        return (p12 + p23 - p13) / (2 * Math.sqrt(p12) * Math.sqrt(p23));
    }
    /** own sort method for benchmark use*/
    private static void mySortListByAngle(ArrayList<Point> list, Point referencePoint) {
        for(int i=0; i<list.size(); ++i){
            for(int j=0; j<list.size()-i; ++j){
                Point p1=list.get(i), p2=list.get(j);
                if(order(p1,p2,referencePoint)==-1){
                    Collections.swap(list,i,j);
                }
            }
        }
    }
    private static int order(Point p1, Point p2, Point referencePoint){
        Point inLineToReference = new Point(referencePoint.x + 1, referencePoint.y);
        if (countCosines(inLineToReference, referencePoint, p2) - countCosines(inLineToReference, referencePoint, p1) < -0.000001) {
            return -1;
        } else if (countCosines(inLineToReference, referencePoint, p2) - countCosines(inLineToReference, referencePoint, p1) > 0.000001) {
            return 1;
        } else if (p1.countDistanceTo(referencePoint) - p2.countDistanceTo(referencePoint) < -0.000001) { //the same angles compere distance^2
            return -1;
        } else if (p1.countDistanceTo(referencePoint) - p2.countDistanceTo(referencePoint) > 0.000001) {
            return 1;
        } else return 0; //the same distance and angle
    };

    private static void sortListByAngle(ArrayList<Point> list, Point referencePoint) {
        list.sort(((p1, p2) -> order(p1, p2, referencePoint)));
    }

    private static void deleteDuplicates(ArrayList<Point> list) {
        for (int i = 0; i < list.size() - 1; ++i) {
            if (list.get(i).equals(list.get(i + 1))) {
                list.remove(i);
                --i;
            }
        }
    }

    private static int addModulo(int number, int value, int module) {
        return (number + value) % module;
    }
    private  void joinConvexToSpiral(){
        if (spiral.size() > 0) {
            int last = spiral.size() - 1;
            int closest = PointSet.findTheClosest(spiral.get(last), convexHull);
            int nextToClosest = addModulo(closest, 1, convexHull.size());
            while (PointSet.getCurveDirection(spiral.get(last), convexHull.get(closest), convexHull.get(nextToClosest)) < 0) {
                closest = nextToClosest;
                nextToClosest = addModulo(closest, 1, convexHull.size());
            }
            for (int i = closest; i < convexHull.size(); ++i) {
                spiral.add(convexHull.get(i));
            }
            for (int i = 0; i < closest; ++i) {
                spiral.add(convexHull.get(i));
            }
        } else
            spiral.addAll(convexHull);
    }
}