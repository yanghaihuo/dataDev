package quickstart

import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment}
// _ 代表把当前目录下的包都导入, 类似 java 中的 *
import org.apache.flink.api.scala._

object BatchWordCount {
  def main(args: Array[String]): Unit = {
    /**
     * 1、env: 设置运行环境
     * 2、source: 读取数据源(文本文件)
     * 3、deal: 逻辑处理
     * 4、sink: 数据存储
     */
    val env: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment
    val dsB: DataSet[String] = env.readTextFile("src/main/resources/quickstart/words.txt")
    val wordCnt: AggregateDataSet[(String, Int)] = dsB
      .flatMap(_.split(" "))
      .map((_, 1))
      .groupBy(0)
      .sum(1)
    // org.apache.flink.core.fs.FileSystem.WriteMode.OVERWRITE: 支持覆写
    wordCnt.writeAsText("src/main/resources/quickstart/output/batch_result", org.apache.flink.core.fs.FileSystem.WriteMode.OVERWRITE)

    // 问题(待解决): 如果不加下面这行代码, 执行上述代码不会将处理结果写入到指定输出的目录中, 这是为什么？
    wordCnt.print()
  }
}
