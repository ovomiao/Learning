# 约束布局

## 宽高问题

* `wrap_content` 
* `0dp` -> 填充约束空间

## 设置控件偏移

* 前提：控件需要完整的水平或垂直约束
    * UI 调节
        * ![UI 调节](./images/cl_1.png)
    * 使用 XML 代码调节精确值
        * `layout_constraintHorizontal_bias` 水平偏移
        * `layout_constraintVertical_bias`  垂直偏移

## 多控件操作

* 先选中
* 参考下图

## 适配技巧

* 适配图片
  * 可以通过两条辅助线使用百分比限定一个目标高度区域，给 ImageView 四周添加上约束，并设定 ImageView 高度为 0dp，让它填满垂直约束