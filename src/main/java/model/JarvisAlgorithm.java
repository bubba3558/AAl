/** Martyna Kania rysowanie spirali*/
package model;

import java.util.ArrayList;

public class JarvisAlgorithm {

    public static ArrayList<Point> createSpiral(ArrayList<Point> pointList) {
        ArrayList<Point> list=new ArrayList(pointList);
        ArrayList<Point> spiral = new ArrayList<>();
        spiral.add(list.remove(countTheLowestIndex(list)));
        while (list.size() > 0) {
            int last = spiral.size() - 1;
            int candidate = 0;
            for (int i = 1; i < list.size(); i++) {
                if (PointSet.getCurveDirection(spiral.get(last), list.get(candidate), list.get(i)) > 0) {
                    candidate = i;
                }
            }
            spiral.add(list.remove(candidate));
        }
        return spiral;
    }

    public static int countTheLowestIndex(ArrayList<Point> list) {
        int lowestPointIndex = 0;
        Point lowest = list.get(0);
        for (int i = 1; i < list.size(); ++i) {
            if (list.get(i).getY() < lowest.getY() || (list.get(i).getY() == lowest.getY() && list.get(i).getX() < lowest.getX())) {
                lowest = list.get(i);
                lowestPointIndex = i;
            }
        }
        return lowestPointIndex;
    }
}
