import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private final int n;
    private final LineSegment[] slopeSegments;
    public BruteCollinearPoints(Point[] points) {
        if (points == null || points.length < 4) throw new IllegalArgumentException();
        for (Point i : points) if (i == null) throw new IllegalArgumentException();
        int count = 0;
        Point[][] collinearPoints = new Point[points.length][4];
        for (int p = 0; p < points.length - 3; p++) {
            for (int q = p+1; q < points.length - 2; q++) {
                for (int r = q+1; r < points.length - 1; r++) {
                    for (int s = r+1; s < points.length; s++) {
                        if (points[p].compareTo(points[r]) == 0 ||
                                points[p].compareTo(points[q]) == 0 ||
                                points[p].compareTo(points[s]) == 0 ||
                                points[q].compareTo(points[r]) == 0 ||
                                points[q].compareTo(points[s]) == 0 ||
                                points[r].compareTo(points[s]) == 0) {
                            throw new IllegalArgumentException();
                        } else if (points[p].slopeTo(points[q]) == points[p].slopeTo(points[r]) &&
                            points[p].slopeTo(points[q]) == points[p].slopeTo(points[s])) {
                            collinearPoints[count] = new Point[]{points[p], points[q], points[r], points[s]};
                            count++;
                        }
                    }
                }
            }
        }
        n = count;
        slopeSegments = new LineSegment[n];
        for (int i = 0; i < n; i++) {
            Point min = collinearPoints[i][0];
            Point max = collinearPoints[i][0];
            for (int j = 1; j < 4; j++) {
                if (collinearPoints[i][j].compareTo(min) < 0) {
                    min = collinearPoints[i][j];
                } else if (collinearPoints[i][j].compareTo(max) > 0) {
                    max = collinearPoints[i][j];
                }
            }
            slopeSegments[i] = new LineSegment(min, max);
        }
    }

    public int numberOfSegments() {
        return n;
    }

    public LineSegment[] segments() {
        return slopeSegments.clone();
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