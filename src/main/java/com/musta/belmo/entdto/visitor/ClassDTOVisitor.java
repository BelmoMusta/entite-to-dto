package com.musta.belmo.entdto.visitor;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;

public class ClassDTOVisitor extends GenericVisitorAdapter<ClassOrInterfaceDeclaration, CompilationUnit> {
	@Override
	public ClassOrInterfaceDeclaration visit(ClassOrInterfaceDeclaration classOrInterfaceDeclaration, CompilationUnit destination) {
        if(classOrInterfaceDeclaration.isAbstract()){
        	return null;
		}
		final String className = classOrInterfaceDeclaration.getNameAsString();
		final String destDTOClassName = className + "DTO";
		final ClassOrInterfaceDeclaration aClass = destination.addClass(destDTOClassName);
        aClass.addMarkerAnnotation("Getter");
        aClass.addMarkerAnnotation("Setter");
        destination.addImport("lombok.Getter");
        destination.addImport("lombok.Setter");
		super.visit(classOrInterfaceDeclaration, destination);
       return aClass;
	}
}
