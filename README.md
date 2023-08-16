# TrajectoryPlotterUtils
轨迹绘图仪工具

用于绘制人员/车辆等轨迹
1:支持两种坐标差值算法
  a:根据原始坐标,按照恒定距离生成新的坐标系,效果比较平滑(推荐)
  b:根据原始坐标,使用 Catmull-Rom 插值算法生成新的坐标系
2:可调参数系
  a: 轨迹线宽度
  b: 轨迹线颜色
  c: 轨迹线边宽度
  d: 轨迹线边颜色
  e: 起点和结束点圆的颜色（废弃⚠️）
  f: 轨迹线箭头大小
  g: 轨迹线箭头颜色
  h: 开始结束坐标图片 向上偏移的像素数值(用于调整图片位置)
  
效果:
<img width="502" alt="image" src="https://github.com/haojiapin/TrajectoryPlotterUtils/assets/76199410/b674ff3d-fc09-440e-a847-6151110a90d2">
