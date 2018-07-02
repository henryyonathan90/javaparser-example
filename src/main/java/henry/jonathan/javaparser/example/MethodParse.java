package henry.jonathan.javaparser.example;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;

/**
 * @author henry.jonathan
 * @since
 */
public class MethodParse {

  public static void main(String[] args) throws Exception {
    CompilationUnit cu = JavaParser.parse(new File(PlainParse.class.getClassLoader()
        .getResource("MemberFindServiceImplTest.java")
        .toURI()));

    ClassOrInterfaceDeclaration classOrInterfaceDeclaration =
        cu.getClassByName("MemberFindServiceImplTest").get();

    classOrInterfaceDeclaration.getMethods().stream().forEach(method -> {
      System.out.println(method.getName() + "============");
      method.accept(new MethodVisitor(), null);
    });
  }

  private static class MethodVisitor extends VoidVisitorAdapter<Void> {
    @Override
    public void visit(MethodCallExpr n, Void arg) {
      System.out.println(n.getName());
      //      System.out.println(arg.toString());
      super.visit(n, arg);
    }
  }
}
