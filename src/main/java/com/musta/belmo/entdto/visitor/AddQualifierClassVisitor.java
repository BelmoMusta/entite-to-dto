package com.musta.belmo.entdto.visitor;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;

public class AddQualifierClassVisitor extends GenericVisitorAdapter<ClassOrInterfaceDeclaration, CompilationUnit> {
	@Override
	public ClassOrInterfaceDeclaration visit(ClassOrInterfaceDeclaration classOrInterfaceDeclaration, CompilationUnit destination) {
        if(classOrInterfaceDeclaration.isAbstract() || classOrInterfaceDeclaration.isAnnotationPresent("Qualifier")){
        	return null;
		}
		SingleMemberAnnotationExpr qualifierAnnotaion = new SingleMemberAnnotationExpr();
		String nameAsString = classOrInterfaceDeclaration.getNameAsString();
		String qualifier = nameAsString.substring(0, 1).toLowerCase() + nameAsString.substring(1);
		qualifierAnnotaion.setMemberValue(new StringLiteralExpr(qualifier));
		qualifierAnnotaion.setName("Qualifier");
		classOrInterfaceDeclaration.addAnnotation(qualifierAnnotaion);
        destination.addImport("org.springframework.beans.factory.annotation.Qualifier");
		super.visit(classOrInterfaceDeclaration, destination);
       return classOrInterfaceDeclaration;
	}
}
