package com.musta.belmo.entdto.visitor;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;

public class AddComponenetClassVisitor extends GenericVisitorAdapter<ClassOrInterfaceDeclaration, CompilationUnit> {
	@Override
	public ClassOrInterfaceDeclaration visit(ClassOrInterfaceDeclaration classOrInterfaceDeclaration, CompilationUnit destination) {
        if(classOrInterfaceDeclaration.isAbstract() || classOrInterfaceDeclaration.isAnnotationPresent("Component")){
        	return null;
		}
  
		MarkerAnnotationExpr componentAnnotation = new MarkerAnnotationExpr();
		componentAnnotation.setName("Component");
		
		classOrInterfaceDeclaration.addAnnotation(componentAnnotation);
        destination.addImport("org.springframework.stereotype.Component");
		super.visit(classOrInterfaceDeclaration, destination);
       return classOrInterfaceDeclaration;
	}
}
