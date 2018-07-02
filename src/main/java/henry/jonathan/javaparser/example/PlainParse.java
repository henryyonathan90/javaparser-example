package henry.jonathan.javaparser.example;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.File;

/**
 * @author henry.jonathan
 * @since
 */
public class PlainParse {

  public static void main(String[] args) throws Exception {
    CompilationUnit cu = JavaParser.parse(new File(PlainParse.class.getClassLoader()
        .getResource("MemberFindServiceImpl.java")
        .toURI()));
    System.out.println(cu.toString());
  }

}
