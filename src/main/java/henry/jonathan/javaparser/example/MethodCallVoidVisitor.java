package henry.jonathan.javaparser.example;

import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * @author henry.jonathan
 * @since
 */
public class MethodCallVoidVisitor extends VoidVisitorAdapter<Void> {
  @Override
  public void visit(MethodCallExpr n, Void arg) {
    System.out.println("Method call: " + n.getName());
    super.visit(n, arg);
  }
}
