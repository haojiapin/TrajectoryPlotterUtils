package org.jeecg.common.util;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;

/**
 * 地图轨迹生成工具
 */

public class TrajectoryPlotterUtils {
    public static void main(String[] args) {
        ArrayList<Point> points = new ArrayList<>();
        //✈️机场1
        points.add(new Point(1900, 2900));
        points.add(new Point(1930, 1600));
        points.add(new Point(3900, 1600));
        points.add(new Point(3900, 1300));
        points.add(new Point(3350, 1100));
        points.add(new Point(1950, 1100));
        points.add(new Point(1960, 800));

        //✈️机场2
//        points.add(new Point(1000, 1000));
//        points.add(new Point(1930, 1000));
//        points.add(new Point(1900, 2800));
//        points.add(new Point(2300, 1000));//凸角
//        points.add(new Point(2500, 1000));//凸角
//        points.add(new Point(2900, 2800));
//        points.add(new Point(2900, 1000));
//        points.add(new Point(3900, 1000));

        //坐标算法1
        ArrayList<Point> points1 = generatePointsWithGap(points, 150);//坐标间距
        //坐标算法2
//        ArrayList<Point> points2 = interpolateCoordinates(points, 5);

        BufferedImage backgroundImage = loadImage("/Users/jasper/Desktop/123.jpg");//背景图
        BufferedImage startImage = loadImage("/Users/jasper/Desktop/起点.png"); //轨迹开始坐标图片
        BufferedImage endImage = loadImage("/Users/jasper/Desktop/终点.png");  //轨迹结束坐标图片

        int lineWidth = 50;                                   //线宽度
        Color lineColor = new Color(74, 142, 248);    //线的颜色
        int lineEdgeWidth = 15;                               //线边宽度
        Color lineEdgeColor = new Color(46, 105, 223);//线边的颜色
        Color circleColor = new Color(255, 255, 255); //起点和结束点圆的颜色（废弃⚠️）
        int arrowSize = 10;                                   //箭头大小
        Color arrowColor = new Color(255, 255, 255);  //箭头的颜色
        int yOffset = -75;                                    //开始结束坐标图片 向上偏移的像素数值


        BufferedImage image = drawTrajectoryOnImage(points1, backgroundImage, lineWidth, lineColor, lineEdgeWidth, lineEdgeColor, circleColor, arrowSize, arrowColor, startImage, endImage, yOffset);
        saveImage(image, "/Users/jasper/Desktop/456.png");
    }

    /**
     * 生成新的坐标
     * (通过现有坐标生成更密集的坐标点)
     *
     * @param originalPoints 原始坐标
     * @param gapLength      坐标距离
     * @return
     */
    private static ArrayList<Point> generatePointsWithGap(ArrayList<Point> originalPoints, int gapLength) {
        ArrayList<Point> newPoints = new ArrayList<>();

        for (int i = 1; i < originalPoints.size(); i++) {
            Point p1 = originalPoints.get(i - 1);
            Point p2 = originalPoints.get(i);

            double distance = p1.distance(p2);
            int segments = (int) (distance / gapLength);

            double dx = (p2.x - p1.x) / segments;
            double dy = (p2.y - p1.y) / segments;

            for (int j = 0; j < segments; j++) {
                int x = (int) (p1.x + j * dx);
                int y = (int) (p1.y + j * dy);
                newPoints.add(new Point(x, y));
            }
        }

        return newPoints;
    }

    /**
     * 生成新的坐标
     * 使用 Catmull-Rom 插值算法
     *
     * @param originalPoints
     * @param gapLength
     * @return
     */
    public static ArrayList<Point> interpolateCoordinates(ArrayList<Point> originalPoints, int gapLength) {
        ArrayList<Point> interpolatedCoordinates = new ArrayList<>();

        for (int i = 0; i < originalPoints.size() - 1; i++) {
            Point p0 = (i > 0) ? originalPoints.get(i - 1) : originalPoints.get(i);
            Point p1 = originalPoints.get(i);
            Point p2 = originalPoints.get(i + 1);
            Point p3 = (i < originalPoints.size() - 2) ? originalPoints.get(i + 2) : originalPoints.get(i + 1);

            for (int j = 0; j <= gapLength; j++) {
                double t = j / (double) (gapLength + 1);

                int xInterpolated = (int) (0.5 * ((2 * p1.x) +
                        (-p0.x + p2.x) * t +
                        (2 * p0.x - 5 * p1.x + 4 * p2.x - p3.x) * t * t +
                        (-p0.x + 3 * p1.x - 3 * p2.x + p3.x) * t * t * t));
                int yInterpolated = (int) (0.5 * ((2 * p1.y) +
                        (-p0.y + p2.y) * t +
                        (2 * p0.y - 5 * p1.y + 4 * p2.y - p3.y) * t * t +
                        (-p0.y + 3 * p1.y - 3 * p2.y + p3.y) * t * t * t));

                interpolatedCoordinates.add(new Point(xInterpolated, yInterpolated));
            }
        }

        // 添加最后一个点
        interpolatedCoordinates.add(new Point(originalPoints.get(originalPoints.size() - 1)));

        return interpolatedCoordinates;
    }

    /**
     * 读区图像
     *
     * @param filePath
     * @return
     */
    private static BufferedImage loadImage(String filePath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


    /**
     * 在图像上绘制轨迹
     * 包含开始图片和结束图片
     * 只定义开始图片和结束图片偏移
     * 带箭头
     *
     * @param points          坐标点
     * @param backgroundImage 背景图
     * @param lineWidth       线粗细
     * @param lineColor       线颜色
     * @param lineEdgeWidth   线框粗细
     * @param lineEdgeColor   线框颜色
     * @param circleColor     圆颜色
     * @param arrowSize       箭头大小
     * @param arrowColor      箭头颜色
     * @param startImage      开始图片
     * @param endImage        结束图片
     * @param yOffset         开始结束图片位置（上下）偏移量
     * @return
     */
    private static BufferedImage drawTrajectoryOnImage(ArrayList<Point> points, BufferedImage backgroundImage, int lineWidth, Color lineColor, int lineEdgeWidth, Color lineEdgeColor, Color circleColor, int arrowSize, Color arrowColor, BufferedImage startImage, BufferedImage endImage, int yOffset) {
        // 创建一个新的图像，大小与背景图像相同
        BufferedImage image = new BufferedImage(backgroundImage.getWidth(), backgroundImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        // 在图像上绘制背景图像
        g2.drawImage(backgroundImage, 0, 0, null);
        // 设置抗锯齿渲染
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //描边(先画)
        if (points.size() > 1) {
            for (int i = 1; i < points.size(); i++) {
                Point p1 = points.get(i - 1);
                Point p2 = points.get(i);
                // 绘制描边线
                g2.setStroke(new BasicStroke(lineWidth + lineEdgeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.setColor(lineEdgeColor);
                g2.draw(new Line2D.Double(p1.x, p1.y, p2.x, p2.y));
            }
        }

        //画线和图案
        if (points.size() > 1) {
            for (int i = 1; i < points.size(); i++) {
                Point p1 = points.get(i - 1);
                Point p2 = points.get(i);

                // 计算连接线的角度和长度
                double angle = Math.atan2(p2.y - p1.y, p2.x - p1.x);

                // 绘制实际线
                g2.setStroke(new BasicStroke(lineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.setColor(lineColor);
                g2.draw(new Line2D.Double(p1.x, p1.y, p2.x, p2.y));

                // 绘制连接线两端的圆
                int circleRadius = lineWidth / 2;
                int circleDiameter = lineWidth;
                g2.setColor(lineColor);
                g2.fillOval((int) (p1.x - circleRadius), (int) (p1.y - circleRadius), circleDiameter, circleDiameter);
                g2.fillOval((int) (p2.x - circleRadius), (int) (p2.y - circleRadius), circleDiameter, circleDiameter);

                // 计算箭头位置
                int arrowX = (int) ((p2.x + p1.x) / 2 + (arrowSize * Math.cos(angle + Math.PI)));
                int arrowY = (int) ((p2.y + p1.y) / 2 + (arrowSize * Math.sin(angle + Math.PI)));

                // 绘制箭头
                drawArrow(g2, arrowX, arrowY, arrowSize, angle, arrowColor, lineWidth);
            }
        }

        // 绘制起点和终点的圆
//        if (points.size() > 0) {
//            Point startPoint = points.get(0);
//            Point endPoint = points.get(points.size() - 1);
//
//            g2.setColor(circleColor);
//            int circleRadius = lineWidth / 2 + 20;
//            int circleDiameter = circleRadius * 2;
//
//            g2.fillOval((int) (startPoint.x - circleRadius), (int) (startPoint.y - circleRadius), circleDiameter, circleDiameter);
//            g2.fillOval((int) (endPoint.x - circleRadius), (int) (endPoint.y - circleRadius), circleDiameter, circleDiameter);
//
//        }

        // 绘制起点图片
        if (startImage != null && points.size() > 0) {
            Point startPoint = points.get(0);
            g2.drawImage(startImage, startPoint.x - startImage.getWidth() / 2, startPoint.y - startImage.getHeight() / 2 + yOffset, null);
        }

        // 绘制终点图片
        if (endImage != null && points.size() > 0) {
            Point endPoint = points.get(points.size() - 1);
            g2.drawImage(endImage, endPoint.x - endImage.getWidth() / 2, endPoint.y - endImage.getHeight() / 2 + yOffset, null);
        }

        g2.dispose();
        return image;
    }


    /**
     * 绘制V形箭头
     *
     * @param g2
     * @param x          坐标
     * @param y          坐标
     * @param size       箭头长度
     * @param angle      角度
     * @param arrowColor 箭头颜色
     * @param lineWidth  线宽度
     */
    private static void drawArrow(Graphics2D g2, int x, int y, int size, double angle, Color arrowColor, int lineWidth) {
        int[] arrowXPoints = {x, x + size, x};
        int[] arrowYPoints = {y - size, y, y + size};//箭头更尖锐

        AffineTransform transform = new AffineTransform();
        transform.rotate(angle, x, y);

        int[] transformedArrowXPoints = new int[arrowXPoints.length];
        int[] transformedArrowYPoints = new int[arrowYPoints.length];

        for (int i = 0; i < arrowXPoints.length; i++) {
            Point2D transformedPoint = transform.transform(new Point2D.Double(arrowXPoints[i], arrowYPoints[i]), null);
            transformedArrowXPoints[i] = (int) transformedPoint.getX();
            transformedArrowYPoints[i] = (int) transformedPoint.getY();
        }

        g2.setColor(arrowColor);
        g2.setStroke(new BasicStroke(lineWidth / 6)); // 设置线的粗细（通过主线宽度算出箭头线宽度）
        g2.drawPolyline(transformedArrowXPoints, transformedArrowYPoints, 3);
    }


    // 保存图像
    private static void saveImage(BufferedImage image, String filePath) {
        try {
            javax.imageio.ImageIO.write(image, "PNG", new File(filePath));
            System.out.println("Image saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
