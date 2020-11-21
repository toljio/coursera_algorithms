import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FastCollinearPoints {
    private final LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {
        final List<LineSegment> tmpList = new LinkedList<>();
        if (points == null) throw new IllegalArgumentException();

        Point[] sortedPoints = points.clone();

        Arrays.sort(sortedPoints);
        for (int i = 0; i < sortedPoints.length; i++) {
            if (sortedPoints[i] == null || i > 0 && sortedPoints[i].compareTo(sortedPoints[i - 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }

        for (int i = 0; i < sortedPoints.length - 3; i++) {
            Point[] sortedSlope = sortedPoints.clone();
            Arrays.sort(sortedSlope, sortedPoints[i].slopeOrder());

            int left = 1;
            Point p = sortedSlope[0];
            for (int right = 2; right <= sortedSlope.length; right++) {
                if (right < sortedSlope.length && Double.compare(p.slopeTo(sortedSlope[left]), p.slopeTo(sortedSlope[right])) == 0) {
                    continue;
                } else if (right - left >= 3 && p.compareTo(sortedSlope[left]) < 0) {
                    tmpList.add(new LineSegment(p, sortedSlope[right-1]));
                }
                left = right;
            }
        }
        lineSegments = tmpList.toArray(new LineSegment[tmpList.size()]);
    }
    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        return lineSegments.clone();
    }


    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}