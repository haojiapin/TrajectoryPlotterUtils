# Trajectory Plotter Tool
# This project is aimed at creating a tool for plotting trajectories of entities such as personnel, vehicles, and more.

## 1: Dual Coordinate Interpolation Algorithms
  * Generate a new coordinate system by evenly spacing points based on the original coordinates, resulting in a smoother trajectory (recommended).
  * Utilize the Catmull-Rom interpolation algorithm to create a refined coordinate system from the given original coordinates.
## 2: Configurable Parameters
  * Trajectory line width
  * Trajectory line color
  * Trajectory line edge width
  * Trajectory line edge color
  * Deprecated: Circle color for start and end points (⚠️)
  * Trajectory arrow size
  * Trajectory arrow color
  * Pixel offset for adjusting the vertical position of start and end coordinate images
## Achieved Effect:
This tool allows users to generate visually appealing trajectories with customizable features. The dual coordinate interpolation algorithms provide options for achieving different levels of smoothness. The configurable parameters enable users to tailor the visual aspects of the trajectories, including line width, colors, and arrow styles. Please note that the circle color for start and end points is deprecated due to design considerations. The tool also accommodates adjustments for the vertical position of coordinate images to enhance the visual presentation.


# 轨迹绘图仪工具

## 用于绘制人员/车辆等轨迹 
### 1:支持两种坐标差值算法 
  * 根据原始坐标,按照恒定距离生成新的坐标系,效果比较平滑(推荐)  
  * 根据原始坐标,使用 Catmull-Rom 插值算法生成新的坐标系  
### 2:可调参数系  
  * 轨迹线宽度  
  * 轨迹线颜色  
  * 轨迹线边宽度  
  * 轨迹线边颜色  
  * 起点和结束点圆的颜色（废弃⚠️）  
  * 轨迹线箭头大小  
  * 轨迹线箭头颜色  
  * 开始结束坐标图片 向上偏移的像素数值(用于调整图片位置)  
  
### 效果:  
<img width="502" alt="image" src="https://github.com/haojiapin/TrajectoryPlotterUtils/assets/76199410/b674ff3d-fc09-440e-a847-6151110a90d2">
<img width="561" alt="image" src="https://github.com/haojiapin/TrajectoryPlotterUtils/assets/76199410/be7f5613-3b84-47d8-bbfb-b4a22bf0585a">

