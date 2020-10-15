package quickstart

/** note
 * 同 java main 方法，编译后形成 public static void main ...
 * scala 在编译源码时，会生成两个字节码文件，静态 main 方法执行另外一个字节码文件中的成员 main 方法
 * scala 是完全面向对象的语言，没有静态方法，只能通过模拟生成静态方法，编译时将当前类生成一个特殊的类：xxx$ ,然后创建对象来调用这个对象的 main 方法
 * 一般情况下，我们将 xxx$ 类的对象，称为伴生对象
 * 伴生对象中的属性和方法，都可以通过类名来直接访问，同 java 中的静态方法，只是和 java 的实现方式不同
 * 伴生对象的语法规则，使用 object 声明
 *
 * public static void main(String[] args) { 方法体 }
 * scala 中没有 public 关键字，默认所有的访问权限都是公共的
 * scala 中没有 void 关键字，使用特殊的对象模拟：Unit
 * scala 中声明方法采用关键字 def
 * scala 中参数列表声明方式 参数: 类型
 * scala 中方法返回值类型写在参数列表后面，通过冒号( : )连接
 * scala 中方法声明和方法体之间通过等号( = )连接
 */
object HelloWorldScala {

  def main(args: Array[String]): Unit = {
    println("hello world")
  }
}
