import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private KdNode root;
    private int n;
    private static class KdNode {
        Point2D val;
        KdNode left;
        KdNode right;
    }

    // construct an empty set of points
    public KdTree() {
        root = null;
        n = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // number of points in the set
    public int size() {
        return n;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        validate(p);
        KdNode pNode = new KdNode();
        pNode.val = p;
        if (root == null) {
            root = pNode;
            n++;
            return;
        }
        KdNode cur = root;
        boolean checkX = true;
        while (true) {
            if (p.compareTo(cur.val) == 0) {
                return;
            }
            double curV, pV;
            curV = (checkX) ? cur.val.x() : cur.val.y();
            pV = (checkX) ? p.x() : p.y();
            if (pV < curV) {
                if (cur.left != null) {
                    cur = cur.left;
                } else {
                    cur.left = pNode;
                    break;
                }
            } else {
                if (cur.right != null) {
                    cur = cur.right;
                } else {
                    cur.right = pNode;
                    break;
                }
            }
            checkX = !checkX;
        }
        n++;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        validate(p);
        KdNode cur = root;
        boolean checkX = true;
        while (cur != null) {
            if (cur.val.equals(p)) return true;
            double curV, pV;
            curV = (checkX) ? cur.val.x() : cur.val.y();
            pV = (checkX) ? p.x() : p.y();
            cur = (pV < curV) ? cur.left : cur.right;
            checkX = !checkX;
        }
        return false;
    }

    // draw all points to standard draw
    public void draw() {
        draw(root, true, new RectHV(0, 0, 1, 1));
    }

    private void draw(KdNode cur, boolean vert, RectHV rect) {
        if (cur == null) return;
        cur.val.draw();
        StdDraw.setPenRadius();
        if (vert) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(cur.val.x(), rect.ymin(), cur.val.x(), rect.ymax());
            StdDraw.setPenColor();
            draw(cur.right, false, new RectHV(cur.val.x(), rect.ymin(), rect.xmax(), rect.ymax()));
            draw(cur.left, false, new RectHV(rect.xmin(), rect.ymin(), cur.val.x(), rect.ymax()));
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(rect.xmin(), cur.val.y(), rect.xmax(), cur.val.y());
            StdDraw.setPenColor();
            draw(cur.right, true, new RectHV(rect.xmin(), cur.val.y(), rect.xmax(), rect.ymax()));
            draw(cur.left, true, new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), cur.val.y()));
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        validate(rect);
        if (isEmpty()) return null;
        Queue<Point2D> res = new Queue<>();
        range(rect, root, true, res, new RectHV(0, 0, 1, 1));
        return res;
    }

    private void range(RectHV rect, KdNode p, boolean vert, Queue<Point2D> res, RectHV rectNode) {
        if (p == null) return;
        if (!rect.intersects(rectNode)) return;
        if (rect.contains(p.val)) res.enqueue(p.val);
        if (vert) {
            range(rect, p.right, false, res, new RectHV(p.val.x(), rectNode.ymin(), rectNode.xmax(), rectNode.ymax()));
            range(rect, p.left, false, res, new RectHV(rectNode.xmin(), rectNode.ymin(), p.val.x(), rectNode.ymax()));
        } else {
            range(rect, p.right, true, res, new RectHV(rectNode.xmin(), p.val.y(), rectNode.xmax(), rectNode.ymax()));
            range(rect, p.left, true, res, new RectHV(rectNode.xmin(), rectNode.ymin(), rectNode.xmax(), p.val.y()));
       }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        validate(p);
        if (isEmpty()) return null;

        return nearest(p, root, true, new RectHV(0, 0, 1, 1), root.val);
    }

    private void validate(Object obj) {
        if (obj == null) throw new IllegalArgumentException();
    }

    private Point2D nearest(Point2D p, KdNode cur, boolean vert, RectHV rect, Point2D cand) {
        if (cur == null) return cand;
        if (rect.distanceSquaredTo(p) > p.distanceSquaredTo(cand)) return cand;
        if (p.distanceSquaredTo(cur.val) < p.distanceSquaredTo(cand)) cand = cur.val;
        if (vert) {
            if (p.compareTo(cur.val) > 0) {
                cand = nearest(p, cur.right, false, new RectHV(cur.val.x(), rect.ymin(), rect.xmax(), rect.ymax()), cand);
                cand = nearest(p, cur.left, false, new RectHV(rect.xmin(), rect.ymin(), cur.val.x(), rect.ymax()), cand);
            } else if (p.compareTo(cur.val) < 0) {
                cand = nearest(p, cur.left, false, new RectHV(rect.xmin(), rect.ymin(), cur.val.x(), rect.ymax()), cand);
                cand = nearest(p, cur.right, false, new RectHV(cur.val.x(), rect.ymin(), rect.xmax(), rect.ymax()), cand);
            }
        } else {
            if (p.compareTo(cur.val) > 0) {
                cand = nearest(p, cur.right, true, new RectHV(rect.xmin(), cur.val.y(), rect.xmax(), rect.ymax()), cand);
                cand = nearest(p, cur.left, true, new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), cur.val.y()), cand);
            } else if (p.compareTo(cur.val) < 0) {
                cand = nearest(p, cur.left, true, new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), cur.val.y()), cand);
                cand = nearest(p, cur.right, true, new RectHV(rect.xmin(), cur.val.y(), rect.xmax(), rect.ymax()), cand);
            }
        }
        return cand;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        return;
    }
}
