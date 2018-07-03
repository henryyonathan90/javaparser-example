package henry.jonathan.javaparser.example;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * @author henry.jonathan
 * @since
 */
public class MethodDeclareVoidVisitor extends VoidVisitorAdapter<MethodCallVoidVisitor> {
  @Override
  public void visit(MethodDeclaration n, MethodCallVoidVisitor methodCallVoidVisitor) {
    System.out.println("Method Declare: " + n.getName());
    n.accept(methodCallVoidVisitor, null);
    super.visit(n, methodCallVoidVisitor);
  }
}

