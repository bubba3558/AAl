/** Martyna Kania rysowanie spirali*/
package model;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.swap;

public class NaiveAlgorithm {

    public static ArrayList<Point> createSpiral(ArrayList<Point> pointList) {
        ArrayList<Point> list=new ArrayList(pointList);
        prepareToAlgorithm(list);
        int i=0;
        do {
            if (isSpiralCorrect(list)) {
                return list;
            }
            list = nextPermutation(list);
        } while (list != null);
        return null;
    }

    private static void prepareToAlgorithm(ArrayList<Point> list) {
        {
            list.sort((point0, point1) -> point0.compareTo(point1));
        }
    }

    private static ArrayList<Point> nextPermutation(final ArrayList<Point> c) {
        int first = getFirst(c);
        if (first == -1) return null; // no greater permutation
        int toSwap = c.size() - 1;
        while (c.get(first).compareTo(c.get(toSwap)) >= 0)
            --toSwap;
        swap(c, first++, toSwap);
        toSwap = c.size() - 1;
        while (first < toSwap)
            swap(c, first++, toSwap--);
        return c;
    }

    private static int getFirst(final List<Point> c) {
        for (int i = c.size() - 2; i >= 0; --i)
            if (c.get(i).compareTo(c.get(i + 1)) < 0)
                return i;
        return -1;
    }

    private static boolean isSpiralCorrect(ArrayList<Point> list) {
        if (list.size() < 4) {//line of 1-3 points is always a correct spiral, nothing to do
            return true;
        }
        int prevDirection = getCurveDirection(list, 0, 1, 2);
        for (int i = 1; i < list.size() - 1; ++i) {
            int direction = getCurveDirection(list, i - 1, i, i + 1);
            if (direction * prevDirection < 0) {
                return false;
            }
            if (direction * prevDirection == 0) {
                if (!correctOrderInLine(list, i)) {
                    return false;
                }
            }
            for (int j = i+1; j < list.size() - 1; ++j) {
                if (PointSet.linesCrossing(list.get(i - 1), list.get(i), list.get(j), list.get(j+1))) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean correctOrderInLine(List<Point> list, int centerPoint) {
        double x0 = list.get(centerPoint - 1).getX();
        double y0 = list.get(centerPoint - 1).getY();
        double x1 = list.get(centerPoint).getX();
        double y1 = list.get(centerPoint).getY();
        double x2 = list.get(centerPoint + 1).getX();
        double y2 = list.get(centerPoint + 1).getY();
        if (Math.abs(x1 - x0) + Math.abs(y1 - y0) > Math.abs(x2 - x0) + Math.abs(y2 - y0)) {
            return false;
        }
        if (Math.abs(x2 - x1) + Math.abs(y2 - y1) > Math.abs(x2 - x0) + Math.abs(y2 - y0)) {
            return false;
        }
        return true;
    }

    private static int getCurveDirection(List<Point> list, int index0, int index1, int index2) {
        return PointSet.getCurveDirection(list.get(index0), list.get(index1), list.get(index2));
    }

}
